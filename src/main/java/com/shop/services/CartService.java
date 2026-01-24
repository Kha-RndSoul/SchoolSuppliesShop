package com.shop.services;

import com.shop.dao.order.CartItemDAO;
import com.shop.dao.product.ProductDAO;
import com.shop.model.CartItem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CartService {

    private final CartItemDAO cartItemDAO;
    private final ProductDAO productDAO;

    public CartService() {
        this.cartItemDAO = new CartItemDAO();
        this.productDAO = new ProductDAO();
    }
    public CartItem addToCart(int customerId, int productId, int quantity) throws Exception {
        if (customerId <= 0) throw new Exception("Customer không hợp lệ");
        if (productId <= 0) throw new Exception("Sản phẩm không hợp lệ");
        if (quantity <= 0) throw new Exception("Số lượng phải lớn hơn 0");

        Map<String, Object> productMap = productDAO.getProductByIdWithImage(productId);
        if (productMap == null) throw new Exception("Sản phẩm không tồn tại");

        int stock = getIntFromMap(productMap, "stockQuantity", 0);
        if (stock < quantity) throw new Exception("Số lượng trong kho không đủ. Còn lại: " + stock);

        CartItem existingItem = cartItemDAO.getByCustomerIdAndProductId(customerId, productId);

        if (existingItem != null) {
            // Sản phẩm đã có trong giỏ -> Cộng dồn số lượng
            int newQuantity = existingItem.getQuantity() + quantity;
            if (stock < newQuantity) throw new Exception("Số lượng trong kho không đủ. Còn lại: " + stock);

            cartItemDAO.updateQuantity(customerId, productId, newQuantity);
            existingItem.setQuantity(newQuantity);
            return existingItem;
        } else {
            // Sản phẩm chưa có -> Tạo mới
            CartItem cartItem = new CartItem();
            cartItem.setCustomerId(customerId);
            cartItem.setProductId(productId);
            cartItem.setQuantity(quantity);
            cartItemDAO.insertCartItem(cartItem);
            return cartItem;
        }
    }
    public boolean updateQuantity(int cartItemId, int quantity, int customerId) throws Exception {
        if (quantity <= 0) throw new Exception("Số lượng phải lớn hơn 0");

        CartItem cartItem = cartItemDAO.getCartItemById(cartItemId);
        if (cartItem == null) throw new Exception("Sản phẩm không có trong giỏ hàng");
        if (cartItem.getCustomerId() != customerId) throw new Exception("Bạn không có quyền thao tác");
        Map<String, Object> productMap = productDAO.getProductByIdWithImage(cartItem.getProductId());
        if (productMap == null) throw new Exception("Sản phẩm không tồn tại");

        int stock = getIntFromMap(productMap, "stockQuantity", 0);
        if (stock < quantity) throw new Exception("Số lượng trong kho không đủ. Còn lại: " + stock);

        cartItemDAO.updateQuantity(customerId, cartItem.getProductId(), quantity);
        return true;
    }
    public boolean incrementQuantity(int customerId, int productId, int amount) throws Exception {
        if (amount <= 0) throw new Exception("Số lượng tăng phải lớn hơn 0");
        CartItem cartItem = cartItemDAO.getByCustomerIdAndProductId(customerId, productId);
        if (cartItem == null) throw new Exception("Sản phẩm không có trong giỏ hàng");
        Map<String, Object> productMap = productDAO.getProductByIdWithImage(productId);
        if (productMap == null) throw new Exception("Sản phẩm không tồn tại");
        int stock = getIntFromMap(productMap, "stockQuantity", 0);
        int newQuantity = cartItem.getQuantity() + amount;
        if (stock < newQuantity) throw new Exception("Số lượng trong kho không đủ. Còn lại: " + stock);
        cartItemDAO.incrementQuantity(customerId, productId, amount);
        return true;
    }
    public boolean decrementQuantity(int customerId, int productId, int amount) throws Exception {
        if (amount <= 0) throw new Exception("Số lượng giảm phải lớn hơn 0");

        CartItem cartItem = cartItemDAO.getByCustomerIdAndProductId(customerId, productId);
        if (cartItem == null) throw new Exception("Sản phẩm không có trong giỏ hàng");

        int newQuantity = cartItem.getQuantity() - amount;
        // Nếu giảm xuống <= 0 thì xóa luôn sản phẩm khỏi giỏ
        if (newQuantity <= 0) {
            cartItemDAO.deleteByCustomerIdAndProductId(customerId, productId);
            return true;
        }

        cartItemDAO.decrementQuantity(customerId, productId, amount);
        return true;
    }
    public boolean removeFromCart(int cartItemId, int customerId) throws Exception {
        CartItem cartItem = cartItemDAO.getCartItemById(cartItemId);
        if (cartItem == null) throw new Exception("Sản phẩm không có trong giỏ hàng");
        if (cartItem.getCustomerId() != customerId) throw new Exception("Bạn không có quyền thao tác");

        cartItemDAO.deleteCartItem(cartItemId);
        return true;
    }
    public boolean removeByProductId(int customerId, int productId) {
        cartItemDAO.deleteByCustomerIdAndProductId(customerId, productId);
        return true;
    }
    public boolean clearCart(int customerId) {
        if (customerId <= 0) return false;
        cartItemDAO.clearCart(customerId);
        return true;
    }
    public List<CartItem> getCartItems(int customerId) {
        if (customerId <= 0) return List.of();
        return cartItemDAO.getByCustomerId(customerId);
    }
    public CartItem getById(int cartItemId) {
        if (cartItemId <= 0) return null;
        return cartItemDAO.getCartItemById(cartItemId);
    }
    public double calculateTotal(int customerId) {
        if (customerId <= 0) return 0;
        List<CartItem> cartItems = cartItemDAO.getByCustomerId(customerId);
        double total = 0;

        for (CartItem item : cartItems) {
            Map<String, Object> productMap = productDAO.getProductByIdWithImage(item.getProductId());
            if (productMap != null) {
                double price = getProductPriceFromMap(productMap);
                total += price * item.getQuantity();
            }
        }
        return total;
    }
    /**
     * Hàm lấy tổng số lượng sản phẩm trong giỏ (để hiển thị lên badge header)
     */
    public int getCartCount(int customerId) {
        if (customerId <= 0) return 0;
        return cartItemDAO.getTotalQuantityByCustomerId(customerId);
    }
    public int getTotalQuantity(int customerId) {
        return getCartCount(customerId);
    }
    public boolean isCartEmpty(int customerId) {
        if (customerId <= 0) return true;
        return cartItemDAO.isCartEmpty(customerId);
    }
    public void validateCartForCheckout(int customerId) throws Exception {
        List<CartItem> cartItems = cartItemDAO.getByCustomerId(customerId);
        if (cartItems.isEmpty()) throw new Exception("Giỏ hàng trống");
        for (CartItem item : cartItems) {
            Map<String, Object> productMap = productDAO.getProductByIdWithImage(item.getProductId());
            if (productMap == null) throw new Exception("Sản phẩm ID " + item.getProductId() + " không tồn tại");

            int stock = getIntFromMap(productMap, "stockQuantity", 0);
            if (stock < item.getQuantity()) {
                throw new Exception("Sản phẩm '" + getStringFromMap(productMap, "productName", "Sản phẩm") + "' không đủ số lượng. Còn lại: " + stock);
            }
        }
    }

    public List<Map<String, Object>> buildCartViewForUser(int customerId) {
        List<Map<String, Object>> view = new ArrayList<>();
        if (customerId <= 0) return view;
        List<CartItem> items = cartItemDAO.getByCustomerId(customerId);
        if (items == null || items.isEmpty()) return view;
        for (CartItem ci : items) {
            int pid = ci.getProductId();
            Map<String, Object> prod = productDAO.getProductByIdWithImage(pid);
            Map<String, Object> item = new LinkedHashMap<>();
            // Cung cấp cả CartItemID  và ProductID
            item.put("id", pid);
            item.put("cartItemId", ci.getId());
            item.put("productName", prod != null ? prod.get("productName") : "Sản phẩm (Đã ẩn)");
            item.put("imageUrl", prod != null ? prod.get("imageUrl") : null);
            item.put("price", prod != null ? prod.get("price") : 0);
            item.put("salePrice", prod != null ? prod.get("salePrice") : 0);
            item.put("quantity", ci.getQuantity());

            view.add(item);
        }

        return view;
    }
    // --- Helper Methods ---
    private int getIntFromMap(Map<String, Object> m, String key, int defaultVal) {
        if (m == null) return defaultVal;
        Object o = m.get(key);
        if (o instanceof Number) return ((Number) o).intValue();
        try { return o != null ? Integer.parseInt(o.toString()) : defaultVal; } catch (Exception e) { return defaultVal; }
    }

    private double getDoubleFromMap(Map<String, Object> m, String key, double defaultVal) {
        if (m == null) return defaultVal;
        Object o = m.get(key);
        if (o instanceof Number) return ((Number) o).doubleValue();
        try { return o != null ? Double.parseDouble(o.toString()) : defaultVal; } catch (Exception e) { return defaultVal; }
    }

    private String getStringFromMap(Map<String, Object> m, String key, String defaultVal) {
        if (m == null) return defaultVal;
        Object o = m.get(key);
        return o != null ? o.toString() : defaultVal;
    }

    private double getProductPriceFromMap(Map<String, Object> productMap) {
        double sale = getDoubleFromMap(productMap, "salePrice", 0.0);
        if (sale > 0) return sale;
        return getDoubleFromMap(productMap, "price", 0.0);
    }
}