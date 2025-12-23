package com.shop.services;

import com. shop.dao.order.CartItemDAO;
import com. shop.dao.product.ProductDAO;
import com.shop. model.CartItem;
import com.shop.model.Product;
import com.shop.util.JdbiConnection;
import org. jdbi.v3.core.Jdbi;

import java.util.List;
import java.util. Optional;

public class CartService {

    private final Jdbi jdbi;
    private final CartItemDAO cartItemDAO;
    private final ProductDAO productDAO;

    /// Constructor - khởi tạo kết nối và DAO
    public CartService() {
        this. jdbi = JdbiConnection.getJdbi();
        this.cartItemDAO = jdbi. onDemand(CartItemDAO.class);
        this.productDAO = jdbi.onDemand(ProductDAO.class);
    }


    /// Thêm sản phẩm vào giỏ hàng
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
        Optional<Product> product = productDAO.getById(productId);
        if (product.isEmpty()) {
            throw new Exception("Sản phẩm không tồn tại");
        }

        // Check stock đủ không
        if (product.get().getStockQuantity() < quantity) {
            throw new Exception("Số lượng trong kho không đủ.  Còn lại: "
                    + product.get().getStockQuantity());
        }

        // Check product đã có trong cart chưa
        Optional<CartItem> existingItem = cartItemDAO
                .getByCustomerIdAndProductId(customerId, productId);

        if (existingItem.isPresent()) {
            // Đã có trong giỏ >> cập nhật quantity
            int newQuantity = existingItem.get().getQuantity() + quantity;

            // Check stock cho tổng quantity mới
            if (product.get().getStockQuantity() < newQuantity) {
                throw new Exception("Số lượng trong kho không đủ. Còn lại: "
                        + product. get().getStockQuantity());
            }

            cartItemDAO.updateQuantity(customerId, productId, newQuantity);
            existingItem.get().setQuantity(newQuantity);
            return existingItem. get();

        } else {
            // Chưa có >> thêm mới
            CartItem cartItem = new CartItem();
            cartItem. setCustomerId((long) customerId);
            cartItem. setProductId((long) productId);
            cartItem. setQuantity(quantity);

            int newId = cartItemDAO.insert(cartItem);
            cartItem.setCartItemId((long) newId);

            return cartItem;
        }
    }

    /// Cập nhật số lượng sản phẩm trong giỏ
    public boolean updateQuantity(int cartItemId, int quantity, int customerId) throws Exception {
        if (quantity <= 0) {
            throw new Exception("Số lượng phải lớn hơn 0");
        }

        Optional<CartItem> cartItem = cartItemDAO.getById(cartItemId);
        if (cartItem.isEmpty()) {
            throw new Exception("Sản phẩm không có trong giỏ hàng");
        }

        // Check owner - chỉ owner mới được sửa
        if (! cartItem.get().getCustomerId().equals((long) customerId)) {
            throw new Exception("Bạn không có quyền thao tác");
        }

        // Check stock đủ không
        Optional<Product> product = productDAO.getById(cartItem.get().getProductId().intValue());
        if (product.isEmpty()) {
            throw new Exception("Sản phẩm không tồn tại");
        }

        if (product. get().getStockQuantity() < quantity) {
            throw new Exception("Số lượng trong kho không đủ. Còn lại: "
                    + product.get().getStockQuantity());
        }

        return cartItemDAO.updateQuantity(
                cartItem.get().getCustomerId().intValue(),
                cartItem. get().getProductId().intValue(),
                quantity
        );
    }

    /// Tăng số lượng sản phẩm trong giỏ
    public boolean incrementQuantity(int customerId, int productId, int amount) throws Exception {
        if (amount <= 0) {
            throw new Exception("Số lượng tăng phải lớn hơn 0");
        }

        // Get cart item hiện tại
        Optional<CartItem> cartItem = cartItemDAO
                .getByCustomerIdAndProductId(customerId, productId);
        if (cartItem.isEmpty()) {
            throw new Exception("Sản phẩm không có trong giỏ hàng");
        }

        // Check stock
        Optional<Product> product = productDAO. getById(productId);
        if (product.isEmpty()) {
            throw new Exception("Sản phẩm không tồn tại");
        }

        int newQuantity = cartItem.get().getQuantity() + amount;
        if (product.get().getStockQuantity() < newQuantity) {
            throw new Exception("Số lượng trong kho không đủ. Còn lại: "
                    + product. get().getStockQuantity());
        }

        return cartItemDAO.incrementQuantity(customerId, productId, amount);
    }

    /// Giảm số lượng sản phẩm trong giỏ
    public boolean decrementQuantity(int customerId, int productId, int amount) throws Exception {
        if (amount <= 0) {
            throw new Exception("Số lượng giảm phải lớn hơn 0");
        }

        Optional<CartItem> cartItem = cartItemDAO
                .getByCustomerIdAndProductId(customerId, productId);
        if (cartItem. isEmpty()) {
            throw new Exception("Sản phẩm không có trong giỏ hàng");
        }

        int newQuantity = cartItem. get().getQuantity() - amount;
        if (newQuantity <= 0) {
            // Quantity <= 0 thì xóa luôn khỏi giỏ
            return cartItemDAO.deleteByCustomerIdAndProductId(customerId, productId);
        }

        return cartItemDAO.decrementQuantity(customerId, productId, amount);
    }
    
    /// Xóa 1 sản phẩm khỏi giỏ hàng
    public boolean removeFromCart(int cartItemId, int customerId) throws Exception {
        Optional<CartItem> cartItem = cartItemDAO.getById(cartItemId);
        if (cartItem.isEmpty()) {
            throw new Exception("Sản phẩm không có trong giỏ hàng");
        }

        // Check owner
        if (!cartItem.get().getCustomerId().equals((long) customerId)) {
            throw new Exception("Bạn không có quyền thao tác");
        }

        return cartItemDAO.delete(cartItemId);
    }

    /// Xóa sản phẩm theo productId
    public boolean removeByProductId(int customerId, int productId) {
        return cartItemDAO.deleteByCustomerIdAndProductId(customerId, productId);
    }

    /// Xóa toàn bộ giỏ hàng - gọi sau khi checkout thành công
    public boolean clearCart(int customerId) {
        if (customerId <= 0) {
            return false;
        }
        return cartItemDAO.clearCart(customerId);
    }


    // ==================== LẤY THÔNG TIN GIỎ HÀNG ====================

    /// Lấy all items trong giỏ hàng của customer
    public List<CartItem> getCartItems(int customerId) {
        if (customerId <= 0) {
            return List.of();
        }
        return cartItemDAO.getByCustomerId(customerId);
    }

    /// Lấy giỏ hàng kèm thông tin sản phẩm - dùng để hiển thị
    public List<CartItem> getCartWithProductInfo(int customerId) {
        if (customerId <= 0) {
            return List.of();
        }
        return cartItemDAO. getCartWithProductInfo(customerId);
    }

    /// Lấy 1 cart item theo ID
    public Optional<CartItem> getById(int cartItemId) {
        if (cartItemId <= 0) {
            return Optional.empty();
        }
        return cartItemDAO.getById(cartItemId);
    }


    // ==================== TÍNH TOÁN ====================

    /// Tính tổng tiền giỏ hàng - tính thủ công
    public double calculateTotal(int customerId) {
        if (customerId <= 0) {
            return 0;
        }

        List<CartItem> cartItems = cartItemDAO.getByCustomerId(customerId);
        double total = 0;
        for (CartItem item : cartItems) {
            Optional<Product> product = productDAO.getById(item.getProductId().intValue());
            if (product.isPresent()) {
                double price = getProductPrice(product.get());
                total += price * item.getQuantity();
            }
        }

        return total;
    }

    /// Tính tổng tiền - lấy từ database (JOIN query)
    public double getCartTotal(int customerId) {
        if (customerId <= 0) {
            return 0;
        }
        return cartItemDAO.getCartTotal(customerId);
    }

    /// Lấy giá sản phẩm - ưu tiên giá sale
    private double getProductPrice(Product product) {
        // salePrice là double primitive, kiểm tra > 0 thay vì != null
        if (product.getSalePrice() > 0) {
            return product.getSalePrice();
        }
        return product.getPrice();
    }


    // ==================== THỐNG KÊ ====================

    /// Đếm số loại sản phẩm trong giỏ - hiển thị badge
    public int getCartCount(int customerId) {
        if (customerId <= 0) {
            return 0;
        }
        return cartItemDAO.countItemsByCustomerId(customerId);
    }

    /// Đếm tổng số lượng sản phẩm trong giỏ
    public int getTotalQuantity(int customerId) {
        if (customerId <= 0) {
            return 0;
        }
        return cartItemDAO.getTotalQuantityByCustomerId(customerId);
    }

    /// Kiểm tra giỏ hàng có trống không
    public boolean isCartEmpty(int customerId) {
        if (customerId <= 0) {
            return true;
        }
        return cartItemDAO.isCartEmpty(customerId);
    }

    /// Validate giỏ hàng trước khi đặt hàng
    public void validateCartForCheckout(int customerId) throws Exception {
        List<CartItem> cartItems = cartItemDAO.getByCustomerId(customerId);

        // Check giỏ hàng trống
        if (cartItems. isEmpty()) {
            throw new Exception("Giỏ hàng trống");
        }

        // Check từng sản phẩm
        for (CartItem item : cartItems) {
            Optional<Product> product = productDAO.getById(item.getProductId().intValue());

            // Check sản phẩm tồn tại
            if (product. isEmpty()) {
                throw new Exception("Sản phẩm ID " + item.getProductId() + " không tồn tại");
            }

            // Check stock đủ
            if (product.get().getStockQuantity() < item.getQuantity()) {
                throw new Exception("Sản phẩm '" + product.get().getProductName()
                        + "' không đủ số lượng.  Còn lại: " + product.get().getStockQuantity());
            }
        }
    }

    /// Xóa các cart items có sản phẩm không còn tồn tại
    public int removeInvalidItems() {
        return cartItemDAO.removeInvalidItems();
    }

    /// Xóa các cart items quá hạn - mặc định 30 ngày
    public int removeExpiredItems(int days) {
        if (days <= 0) {
            days = 30;
        }
        return cartItemDAO.removeExpiredItems(days);
    }
}