package com.shop.services;

import com.shop.dao.order.OrderDAO;
import com.shop.dao.order.OrderDetailDAO;
import com.shop.dao.order.CartItemDAO;
import com.shop.dao.order.CouponDAO;
import com.shop.dao.order.OrderCouponDAO;
import com.shop.dao.product.ProductDAO;
import com.shop.dao.support.CustomerDAO;
import com.shop.model.Order;
import com.shop.model.OrderDetail;
import com.shop.model.CartItem;
import com.shop.model.Coupon;
import com.shop.model.OrderCoupon;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OrderService {

    private final OrderDAO orderDAO;
    private final OrderDetailDAO orderDetailDAO;
    private final CartItemDAO cartItemDAO;
    private final CouponDAO couponDAO;
    private final OrderCouponDAO orderCouponDAO;
    private final ProductDAO productDAO;
    private final CustomerDAO customerDAO;

    // Các trạng thái đơn hàng
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_CONFIRMED = "CONFIRMED";
    public static final String STATUS_SHIPPING = "SHIPPING";
    public static final String STATUS_DELIVERED = "DELIVERED";
    public static final String STATUS_CANCELLED = "CANCELLED";


    public OrderService() {
        this.orderDAO = new OrderDAO();
        this.orderDetailDAO = new OrderDetailDAO();
        this.cartItemDAO = new CartItemDAO();
        this.couponDAO = new CouponDAO();
        this.orderCouponDAO = new OrderCouponDAO();
        this.productDAO = new ProductDAO();
        this.customerDAO = new CustomerDAO();
    }


    public Order createOrder(int customerId, String couponCode) throws Exception {
        if (customerId <= 0) {
            throw new Exception("Customer không hợp lệ");
        }

        if (customerDAO.getCustomerById(customerId) == null) {
            throw new Exception("Customer không tồn tại");
        }

        List<CartItem> cartItems = cartItemDAO.getByCustomerId(customerId);
        if (cartItems == null || cartItems.isEmpty()) {
            throw new Exception("Giỏ hàng trống");
        }

        // Kiểm tra tồn kho (dựa trên productMap)
        for (CartItem item : cartItems) {
            Map<String, Object> productMap = productDAO.getProductByIdWithImage(item.getProductId());

            if (productMap == null) {
                throw new Exception("Sản phẩm ID " + item.getProductId() + " không tồn tại");
            }

            int stockQty = getIntFromMap(productMap, "stockQuantity", 0);
            if (stockQty < item.getQuantity()) {
                String pname = getStringFromMap(productMap, "productName", "Sản phẩm");
                throw new Exception("Sản phẩm '" + pname + "' không đủ số lượng. Còn lại: " + stockQty);
            }
        }

        // Tính subtotal
        double subtotal = calculateSubtotal(cartItems);

        // Xử lý coupon nếu có
        double discountAmount = 0;
        Coupon coupon = null;

        if (couponCode != null && !couponCode.trim().isEmpty()) {
            coupon = validateAndGetCoupon(couponCode, customerId);
            if (coupon != null && coupon.getDiscountValue() != null) {
                discountAmount = calculateDiscount(coupon, subtotal);
            }
        }

        // Tính total amount
        double totalAmount = subtotal - discountAmount;
        if (totalAmount < 0) {
            totalAmount = 0;
        }

        // Tạo Order object
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setOrderCode(generateOrderCode());
        order.setOrderStatus(STATUS_PENDING);
        order.setTotalAmount(BigDecimal.valueOf(totalAmount));

        // Insert Order vào database
        int orderId = orderDAO.insertOrder(order);

        // Tạo OrderDetails cho từng cart item (dùng productMap)
        for (CartItem item : cartItems) {
            Map<String, Object> productMap = productDAO.getProductByIdWithImage(item.getProductId());

            if (productMap != null) {
                OrderDetail detail = new OrderDetail();
                detail.setOrderId(orderId);
                detail.setProductId(item.getProductId());
                detail.setQuantity(item.getQuantity());
                detail.setProductName(getStringFromMap(productMap, "productName", "Sản phẩm"));

                double unitPrice = getProductPriceFromMap(productMap);
                double itemSubtotal = unitPrice * item.getQuantity();
                detail.setPrice(BigDecimal.valueOf(unitPrice));
                detail.setSubtotal(BigDecimal.valueOf(itemSubtotal));

                orderDetailDAO.insertOrderDetail(detail);
            }
        }

        // Lưu coupon nếu có
        if (coupon != null) {
            OrderCoupon orderCoupon = new OrderCoupon();
            orderCoupon.setOrderId(orderId);
            orderCoupon.setCouponId(coupon.getId());
            orderCoupon.setDiscountAmount(BigDecimal.valueOf(discountAmount));

            orderCouponDAO.insertOrderCoupon(orderCoupon);
            couponDAO.incrementUsedCount(coupon.getId());
        }

        // Trừ stock cho tất cả sản phẩm
        for (CartItem item : cartItems) {
            productDAO.decreaseStock(item.getProductId(), item.getQuantity());
        }

        // Clear giỏ hàng
        cartItemDAO.clearCart(customerId);

        // Trả về order (có id)
        return orderDAO.getOrderById(orderId);
    }


    public Order createOrder(int customerId,
                             String couponCode,
                             int shippingFee,
                             String shippingName,
                             String shippingPhone,
                             String shippingAddr,
                             String paymentMethod,
                             String shippingType,
                             String email) throws Exception {

        if (customerId <= 0) {
            throw new Exception("Customer không hợp lệ");
        }

        if (customerDAO.getCustomerById(customerId) == null) {
            throw new Exception("Customer không tồn tại");
        }

        List<CartItem> cartItems = cartItemDAO.getByCustomerId(customerId);
        if (cartItems == null || cartItems.isEmpty()) {
            throw new Exception("Giỏ hàng trống");
        }

        // Kiểm tra tồn kho trước khi tạo đơn
        for (CartItem item : cartItems) {
            Map<String, Object> productMap = productDAO.getProductByIdWithImage(item.getProductId());
            if (productMap == null) {
                throw new Exception("Sản phẩm ID " + item.getProductId() + " không tồn tại");
            }
            int stockQty = getIntFromMap(productMap, "stockQuantity", 0);
            if (stockQty < item.getQuantity()) {
                throw new Exception("Sản phẩm '" + getStringFromMap(productMap, "productName", "Sản phẩm")
                        + "' không đủ số lượng. Còn lại: " + stockQty);
            }
        }

        double subtotal = calculateSubtotal(cartItems);

        // Xử lý coupon
        double discountAmount = 0;
        Coupon coupon = null;
        if (couponCode != null && !couponCode.trim().isEmpty()) {
            coupon = validateAndGetCoupon(couponCode, customerId);
            if (coupon != null && coupon.getDiscountValue() != null) {
                discountAmount = calculateDiscount(coupon, subtotal);
            }
        }

        // Tính tổng: subtotal - discount + shippingFee
        double totalAmount = subtotal - discountAmount + (double) shippingFee;
        if (totalAmount < 0) totalAmount = 0;

        // Tạo Order object đầy đủ thông tin
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setOrderCode(generateOrderCode());
        order.setOrderStatus(STATUS_PENDING);
        order.setPaymentMethod(paymentMethod != null ? paymentMethod.toUpperCase() : "COD");
        order.setPaymentStatus("UNPAID");
        order.setTotalAmount(BigDecimal.valueOf(totalAmount));
        order.setShippingName(shippingName);
        order.setShippingPhone(shippingPhone);
        order.setShippingAddress(shippingAddr);
        String note = (email != null && !email.isEmpty() ? "Email: " + email + " | " : "") + "Shipping: " + (shippingType != null ? shippingType : "standard");
        order.setNote(note);

        // Insert order -> lấy orderId
        int orderId = orderDAO.insertOrder(order);
        if (orderId <= 0) {
            throw new Exception("Không thể tạo đơn hàng (DB).");
        }
        order.setId(orderId);

        // Tạo OrderDetail cho từng item (dùng productMap)
        for (CartItem item : cartItems) {
            Map<String, Object> productMap = productDAO.getProductByIdWithImage(item.getProductId());
            if (productMap == null) continue;

            OrderDetail detail = new OrderDetail();
            detail.setOrderId(orderId);
            detail.setProductId(item.getProductId());
            detail.setProductName(getStringFromMap(productMap, "productName", "Sản phẩm"));
            detail.setQuantity(item.getQuantity());

            double unitPrice = getProductPriceFromMap(productMap);
            detail.setPrice(BigDecimal.valueOf(unitPrice));
            detail.setSubtotal(BigDecimal.valueOf(unitPrice * item.getQuantity()));

            orderDetailDAO.insertOrderDetail(detail);
        }

        // Lưu coupon nếu có
        if (coupon != null) {
            OrderCoupon orderCoupon = new OrderCoupon();
            orderCoupon.setOrderId(orderId);
            orderCoupon.setCouponId(coupon.getId());
            orderCoupon.setDiscountAmount(BigDecimal.valueOf(discountAmount));
            orderCouponDAO.insertOrderCoupon(orderCoupon);
            couponDAO.incrementUsedCount(coupon.getId());
        }

        // Trừ stock
        for (CartItem item : cartItems) {
            productDAO.decreaseStock(item.getProductId(), item.getQuantity());
        }

        // Clear giỏ hàng DB cho customer
        cartItemDAO.clearCart(customerId);

        // Trả về order đầy đủ từ DB
        return orderDAO.getOrderById(orderId);
    }

    /**
     * Lấy đơn hàng theo ID
     */
    public Order getById(int orderId) {
        if (orderId <= 0) {
            return null;
        }
        return orderDAO.getOrderById(orderId);
    }

    /**
     * Lấy đơn hàng theo ID - có kiểm tra owner
     */
    public Order getById(int orderId, int customerId) throws Exception {
        Order order = orderDAO.getOrderById(orderId);

        if (order == null) {
            return null;
        }

        if (order.getCustomerId() != customerId) {
            throw new Exception("Bạn không có quyền xem đơn hàng này");
        }

        return order;
    }

    /**
     * Lấy all đơn hàng của customer
     */
    public List<Order> getByCustomer(int customerId) {
        if (customerId <= 0) {
            return List.of();
        }
        return orderDAO.getByCustomerId(customerId);
    }

    /**
     * Lấy đơn hàng theo trạng thái - cho admin
     */
    public List<Order> getByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return List.of();
        }
        return orderDAO.getByStatus(status);
    }

    /**
     * Lấy all đơn hàng - cho admin
     */
    public List<Order> getAll() {
        return orderDAO.getList();
    }

    /**
     * Lấy chi tiết đơn hàng
     */
    public List<OrderDetail> getOrderDetails(int orderId) {
        if (orderId <= 0) {
            return List.of();
        }
        return orderDetailDAO.getByOrderId(orderId);
    }

    /**
     * Lấy đơn hàng mới nhất của customer
     */
    public Order getLatestByCustomer(int customerId) {
        if (customerId <= 0) {
            return null;
        }
        return orderDAO.getLatestByCustomerId(customerId);
    }

    /**
     * Cập nhật trạng thái đơn hàng - cho admin
     */
    public boolean updateStatus(int orderId, String newStatus) throws Exception {
        Order order = orderDAO.getOrderById(orderId);

        if (order == null) {
            throw new Exception("Đơn hàng không tồn tại");
        }

        String currentStatus = order.getOrderStatus();

        if (!isValidStatusTransition(currentStatus, newStatus)) {
            throw new Exception("Không thể chuyển trạng thái từ " + currentStatus + " sang " + newStatus);
        }

        // Hủy đơn -> hoàn lại stock
        if (STATUS_CANCELLED.equals(newStatus)) {
            restoreStock(orderId);
        }

        orderDAO.updateStatus(orderId, newStatus);
        return true;
    }

    /**
     * Hủy đơn hàng - cho customer
     */
    public boolean cancelOrder(int orderId, int customerId) throws Exception {
        Order order = orderDAO.getOrderById(orderId);

        if (order == null) {
            throw new Exception("Đơn hàng không tồn tại");
        }

        // Check owner
        if (order.getCustomerId() != customerId) {
            throw new Exception("Bạn không có quyền hủy đơn hàng này");
        }

        // Chỉ hủy được đơn PENDING
        if (!STATUS_PENDING.equals(order.getOrderStatus())) {
            throw new Exception("Chỉ có thể hủy đơn hàng đang chờ xử lý");
        }

        // Hoàn lại stock
        restoreStock(orderId);

        orderDAO.updateStatus(orderId, STATUS_CANCELLED);
        return true;
    }

    /**
     * Tìm kiếm đơn hàng
     */
    public List<Order> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAll();
        }
        return orderDAO.search(keyword.trim());
    }

    /**
     * Tính tổng doanh thu - đơn hàng COMPLETED
     */
    public double calculateRevenue() {
        return orderDAO.getTotalRevenue();
    }

    /**
     * Tính doanh thu theo tháng
     */
    public double getRevenueByMonth(int month, int year) {
        return orderDAO.getRevenueByMonth(month, year);
    }

    /**
     * Đếm đơn hàng theo trạng thái
     */
    public int countByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return 0;
        }
        return orderDAO.countByStatus(status);
    }

    /**
     * Đếm tổng số đơn hàng
     */
    public int countAll() {
        return orderDAO.countAll();
    }

    /**
     * Đếm đơn hàng của customer
     */
    public int countByCustomer(int customerId) {
        if (customerId <= 0) {
            return 0;
        }
        return orderDAO.getByCustomerId(customerId).size();
    }


    /**
     * Tạo mã đơn hàng unique
     */
    private String generateOrderCode() {
        return "ORD-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
    }

    /**
     * Validate chuyển đổi trạng thái
     */
    private boolean isValidStatusTransition(String from, String to) {
        // PENDING >> CONFIRMED, CANCELLED
        if (STATUS_PENDING.equals(from)) {
            return STATUS_CONFIRMED.equals(to) || STATUS_CANCELLED.equals(to);
        }

        // CONFIRMED >> SHIPPING, CANCELLED
        if (STATUS_CONFIRMED.equals(from)) {
            return STATUS_SHIPPING.equals(to) || STATUS_CANCELLED.equals(to);
        }

        // SHIPPING >> DELIVERED
        if (STATUS_SHIPPING.equals(from)) {
            return STATUS_DELIVERED.equals(to);
        }

        // DELIVERED, CANCELLED >> không chuyển được
        return false;
    }

    /**
     * Hoàn lại stock khi hủy đơn
     */
    private void restoreStock(int orderId) {
        List<OrderDetail> details = orderDetailDAO.getByOrderId(orderId);

        for (OrderDetail detail : details) {
            productDAO.increaseStock(detail.getProductId(), detail.getQuantity());
        }
    }

    /**
     * Tính subtotal từ cart items
     */
    private double calculateSubtotal(List<CartItem> cartItems) {
        double subtotal = 0;

        for (CartItem item : cartItems) {
            Map<String, Object> productMap = productDAO.getProductByIdWithImage(item.getProductId());

            if (productMap != null) {
                double price = getProductPriceFromMap(productMap);
                subtotal += price * item.getQuantity();
            }
        }

        return subtotal;
    }

    /**
     * Lấy giá sản phẩm từ Map - ưu tiên sale price
     */
    private double getProductPriceFromMap(Map<String, Object> productMap) {
        double sale = getDoubleFromMap(productMap, "salePrice", 0.0);
        if (sale > 0) return sale;
        return getDoubleFromMap(productMap, "price", 0.0);
    }

    /**
     * Tính số tiền giảm giá
     */
    private double calculateDiscount(Coupon coupon, double subtotal) {
        double discount = 0;

        if (coupon.getDiscountValue() == null) {
            return 0;
        }

        String discountType = coupon.getDiscountType();
        double discountValue = coupon.getDiscountValue().doubleValue();

        if ("PERCENT".equals(discountType)) {
            // Giảm theo phần trăm
            discount = subtotal * discountValue / 100;
        } else if ("FIXED".equals(discountType)) {
            // Giảm số tiền cố định
            discount = discountValue;
        }

        // Không giảm quá subtotal
        if (discount > subtotal) {
            discount = subtotal;
        }

        return discount;
    }

    /**
     * Validate và lấy coupon
     */
    private Coupon validateAndGetCoupon(String couponCode, int customerId) throws Exception {
        if (couponCode == null || couponCode.trim().isEmpty()) {
            throw new Exception("Mã giảm giá không được rỗng");
        }

        Coupon coupon = couponDAO.getByCode(couponCode.trim());
        if (coupon == null) {
            throw new Exception("Mã giảm giá không tồn tại");
        }

        // Check valid - còn hạn và còn lượt sử dụng
        if (!couponDAO.isValidCoupon(couponCode.trim())) {
            throw new Exception("Mã giảm giá đã hết hạn hoặc đã hết lượt sử dụng");
        }

        // Check customer đã dùng coupon này chưa
        if (orderCouponDAO.hasCustomerUsedCoupon(customerId, coupon.getId())) {
            throw new Exception("Bạn đã sử dụng mã giảm giá này rồi");
        }

        return coupon;
    }

    /**
     * Helpers for map extraction
     */
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

    /**
     * Lấy đơn hàng gần nhất kèm thông tin khách hàng - cho dashboard
     */
    public List<Map<String, Object>> getRecentOrdersWithCustomer(int limit) {
        if (limit <= 0) {
            limit = 5;
        }
        return OrderDAO.getRecentOrdersWithCustomer(limit);
    }
}