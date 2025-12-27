package com.shop.services;

import com.shop.dao.order.CartItemDAO;
import com.shop.dao.product.ProductDAO;
import com.shop.model.CartItem;
import com.shop.model.Product;

import java.util.List;

public class CartService {

    private final CartItemDAO cartItemDAO;
    private final ProductDAO productDAO;

    /**
     * Constructor - khởi tạo DAO
     */
    public CartService() {
        this.cartItemDAO = new CartItemDAO();
        this.productDAO = new ProductDAO();
    }

    /**
     * Thêm sản phẩm vào giỏ hàng
     */
    public CartItem addToCart(int customerId, int productId, int quantity) throws Exception {
        if (customerId <= 0) {
            throw new Exception("Customer không hợp lệ");
        }

        if (productId <= 0) {
            throw new Exception("Sản phẩm không hợp lệ");
        }

        if (quantity <= 0) {
            throw new Exception("Số lượng phải lớn hơn 0");
        }

        // Check sản phẩm tồn tại
        Product product = productDAO.getProduct(productId);
        if (product == null) {
            throw new Exception("Sản phẩm không tồn tại");
        }

        // Check stock đủ không
        if (product.getStockQuantity() < quantity) {
            throw new Exception("Số lượng trong kho không đủ. Còn lại: " + product.getStockQuantity());
        }

        // Check product đã có trong cart chưa
        CartItem existingItem = cartItemDAO.getByCustomerIdAndProductId(customerId, productId);

        if (existingItem != null) {
            // Đã có trong giỏ >> cập nhật quantity
            int newQuantity = existingItem.getQuantity() + quantity;

            // Check stock cho tổng quantity mới
            if (product.getStockQuantity() < newQuantity) {
                throw new Exception("Số lượng trong kho không đủ. Còn lại: " + product.getStockQuantity());
            }

            cartItemDAO.updateQuantity(customerId, productId, newQuantity);
            existingItem.setQuantity(newQuantity);
            return existingItem;

        } else {
            // Chưa có >> thêm mới
            CartItem cartItem = new CartItem();
            cartItem.setCustomerId(customerId);
            cartItem.setProductId(productId);
            cartItem.setQuantity(quantity);

            cartItemDAO.insertCartItem(cartItem);
            return cartItem;
        }
    }

    /**
     * Cập nhật số lượng sản phẩm trong giỏ
     */
    public boolean updateQuantity(int cartItemId, int quantity, int customerId) throws Exception {
        if (quantity <= 0) {
            throw new Exception("Số lượng phải lớn hơn 0");
        }

        CartItem cartItem = cartItemDAO.getCartItemById(cartItemId);
        if (cartItem == null) {
            throw new Exception("Sản phẩm không có trong giỏ hàng");
        }

        // Check owner - chỉ owner mới được sửa
        if (cartItem.getCustomerId() != customerId) {
            throw new Exception("Bạn không có quyền thao tác");
        }

        // Check stock đủ không
        Product product = productDAO.getProduct(cartItem.getProductId());
        if (product == null) {
            throw new Exception("Sản phẩm không tồn tại");
        }

        if (product.getStockQuantity() < quantity) {
            throw new Exception("Số lượng trong kho không đủ. Còn lại: " + product.getStockQuantity());
        }

        cartItemDAO.updateQuantity(customerId, cartItem.getProductId(), quantity);
        return true;
    }

    /**
     * Tăng số lượng sản phẩm trong giỏ
     */
    public boolean incrementQuantity(int customerId, int productId, int amount) throws Exception {
        if (amount <= 0) {
            throw new Exception("Số lượng tăng phải lớn hơn 0");
        }

        // Get cart item hiện tại
        CartItem cartItem = cartItemDAO.getByCustomerIdAndProductId(customerId, productId);
        if (cartItem == null) {
            throw new Exception("Sản phẩm không có trong giỏ hàng");
        }

        // Check stock
        Product product = productDAO.getProduct(productId);
        if (product == null) {
            throw new Exception("Sản phẩm không tồn tại");
        }

        int newQuantity = cartItem.getQuantity() + amount;
        if (product.getStockQuantity() < newQuantity) {
            throw new Exception("Số lượng trong kho không đủ. Còn lại: " + product.getStockQuantity());
        }

        cartItemDAO.incrementQuantity(customerId, productId, amount);
        return true;
    }

    /**
     * Giảm số lượng sản phẩm trong giỏ
     */
    public boolean decrementQuantity(int customerId, int productId, int amount) throws Exception {
        if (amount <= 0) {
            throw new Exception("Số lượng giảm phải lớn hơn 0");
        }

        CartItem cartItem = cartItemDAO.getByCustomerIdAndProductId(customerId, productId);
        if (cartItem == null) {
            throw new Exception("Sản phẩm không có trong giỏ hàng");
        }

        int newQuantity = cartItem.getQuantity() - amount;
        if (newQuantity <= 0) {
            cartItemDAO.deleteByCustomerIdAndProductId(customerId, productId);
            return true;
        }

        cartItemDAO.decrementQuantity(customerId, productId, amount);
        return true;
    }

    /**
     * Xóa 1 sản phẩm khỏi giỏ hàng
     */
    public boolean removeFromCart(int cartItemId, int customerId) throws Exception {
        CartItem cartItem = cartItemDAO.getCartItemById(cartItemId);
        if (cartItem == null) {
            throw new Exception("Sản phẩm không có trong giỏ hàng");
        }

        // Check owner
        if (cartItem.getCustomerId() != customerId) {
            throw new Exception("Bạn không có quyền thao tác");
        }

        cartItemDAO.deleteCartItem(cartItemId);
        return true;
    }

    /**
     * Xóa sản phẩm theo productId
     */
    public boolean removeByProductId(int customerId, int productId) {
        cartItemDAO.deleteByCustomerIdAndProductId(customerId, productId);
        return true;
    }

    /**
     * Xóa toàn bộ giỏ hàng - gọi sau khi checkout thành công
     */
    public boolean clearCart(int customerId) {
        if (customerId <= 0) {
            return false;
        }
        cartItemDAO.clearCart(customerId);
        return true;
    }

    /**
     * Lấy all items trong giỏ hàng của customer
     */
    public List<CartItem> getCartItems(int customerId) {
        if (customerId <= 0) {
            return List.of();
        }
        return cartItemDAO.getByCustomerId(customerId);
    }

    /**
     * Lấy 1 cart item theo ID
     */
    public CartItem getById(int cartItemId) {
        if (cartItemId <= 0) {
            return null;
        }
        return cartItemDAO.getCartItemById(cartItemId);
    }

    /**
     * Tính tổng tiền giỏ hàng - tính thủ công
     */
    public double calculateTotal(int customerId) {
        if (customerId <= 0) {
            return 0;
        }

        List<CartItem> cartItems = cartItemDAO.getByCustomerId(customerId);
        double total = 0;
        for (CartItem item : cartItems) {
            Product product = productDAO.getProduct(item.getProductId());
            if (product != null) {
                double price = getProductPrice(product);
                total += price * item.getQuantity();
            }
        }

        return total;
    }

    /**
     * Đếm số loại sản phẩm trong giỏ - hiển thị badge
     */
    public int getCartCount(int customerId) {
        if (customerId <= 0) {
            return 0;
        }
        return cartItemDAO.countItemsByCustomerId(customerId);
    }

    /**
     * Đếm tổng số lượng sản phẩm trong giỏ
     */
    public int getTotalQuantity(int customerId) {
        if (customerId <= 0) {
            return 0;
        }
        return cartItemDAO.getTotalQuantityByCustomerId(customerId);
    }

    /**
     * Kiểm tra giỏ hàng có trống không
     */
    public boolean isCartEmpty(int customerId) {
        if (customerId <= 0) {
            return true;
        }
        return cartItemDAO.isCartEmpty(customerId);
    }

    /**
     * Validate giỏ hàng trước khi đặt hàng
     */
    public void validateCartForCheckout(int customerId) throws Exception {
        List<CartItem> cartItems = cartItemDAO.getByCustomerId(customerId);

        // Check giỏ hàng trống
        if (cartItems.isEmpty()) {
            throw new Exception("Giỏ hàng trống");
        }

        // Check từng sản phẩm
        for (CartItem item : cartItems) {
            Product product = productDAO.getProduct(item.getProductId());

            // Check sản phẩm tồn tại
            if (product == null) {
                throw new Exception("Sản phẩm ID " + item.getProductId() + " không tồn tại");
            }

            // Check stock đủ
            if (product.getStockQuantity() < item.getQuantity()) {
                throw new Exception("Sản phẩm '" + product.getProductName()
                        + "' không đủ số lượng. Còn lại: " + product.getStockQuantity());
            }
        }
    }

    /**
     * Lấy giá sản phẩm - ưu tiên giá sale
     */
    private double getProductPrice(Product product) {
        if (product.getSalePrice() > 0) {
            return product.getSalePrice();
        }
        return product.getPrice();
    }
}