package com.shop.services;

import com.shop.dao.order.OrderDAO;
import com. shop.dao.order.OrderDetailDAO;
import com.shop.dao. order.CartItemDAO;
import com.shop.dao.order.CouponDAO;
import com.shop. dao.order.OrderCouponDAO;
import com.shop. dao.product.ProductDAO;
import com.shop. dao.support.CustomerDAO;
import com.shop.model. Order;
import com.shop.model. OrderDetail;
import com.shop.model.CartItem;
import com.shop.model. Coupon;
import com.shop. model.OrderCoupon;
import com.shop. model.Product;
import com.shop.util.JdbiConnection;
import org. jdbi.v3.core.Jdbi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java. util.List;
import java.util. Optional;
import java.util. UUID;

public class OrderService {

    private final Jdbi jdbi;
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

    // Constructor - khởi tạo kết nối và các DAO
    public OrderService() {
        this.jdbi = JdbiConnection.getJdbi();
        this.orderDAO = jdbi.onDemand(OrderDAO.class);
        this.orderDetailDAO = jdbi.onDemand(OrderDetailDAO.class);
        this.cartItemDAO = jdbi.onDemand(CartItemDAO.class);
        this.couponDAO = jdbi.onDemand(CouponDAO.class);
        this.orderCouponDAO = jdbi.onDemand(OrderCouponDAO.class);
        this.productDAO = jdbi.onDemand(ProductDAO.class);
        this.customerDAO = jdbi.onDemand(CustomerDAO.class);
    }




    // Tạo đơn hàng từ giỏ hàng
    public Order createOrder(int customerId, String couponCode) throws Exception {
        if (customerId <= 0) {
            throw new Exception("Customer không hợp lệ");
        }

        if (customerDAO.getById(customerId).isEmpty()) {
            throw new Exception("Customer không tồn tại");
        }
        List<CartItem> cartItems = cartItemDAO. getByCustomerId(customerId);
        if (cartItems. isEmpty()) {
            throw new Exception("Giỏ hàng trống");
        }
        for (CartItem item : cartItems) {
            Optional<Product> product = productDAO.getById(item.getProductId().intValue());

            if (product. isEmpty()) {
                throw new Exception("Sản phẩm ID " + item.getProductId() + " không tồn tại");
            }

            if (product. get().getStockQuantity() < item.getQuantity()) {
                throw new Exception("Sản phẩm '" + product.get().getProductName()
                        + "' không đủ số lượng. Còn lại: " + product.get().getStockQuantity());
            }
        }
        //Tính subtotal
        double subtotal = calculateSubtotal(cartItems);

        //Xử lý coupon nếu có
        double discountAmount = 0;
        Coupon coupon = null;

        if (couponCode != null && ! couponCode.trim().isEmpty()) {
            coupon = validateAndGetCoupon(couponCode, customerId);
            if (coupon != null && coupon.getDiscountValue() != null) {
                discountAmount = calculateDiscount(coupon, subtotal);
            }
        }

        //Tính total amount
        double totalAmount = subtotal - discountAmount;
        if (totalAmount < 0) {
            totalAmount = 0;
        }

        // Tạo Order object
        Order order = new Order();
        order.setCustomerId((long) customerId);
        order.setOrderCode(generateOrderCode());
        order.setOrderStatus(STATUS_PENDING);
        order.setTotalAmount(BigDecimal.valueOf(totalAmount));

        // Insert Order vào database
        int orderId = orderDAO. insert(order);
        order.setOrderId((long) orderId);

        // Tạo OrderDetails cho từng cart item
        for (CartItem item :  cartItems) {
            Optional<Product> product = productDAO.getById(item.getProductId().intValue());

            if (product.isPresent()) {
                OrderDetail detail = new OrderDetail();
                detail.setOrderId((long) orderId);
                detail.setProductId(item.getProductId());
                detail.setQuantity(item.getQuantity());

                double unitPrice = getProductPrice(product.get());
                double itemSubtotal = unitPrice * item.getQuantity();
                detail.setSubtotal(BigDecimal.valueOf(itemSubtotal));

                orderDetailDAO.insert(detail);
            }
        }

        //Lưu coupon nếu có
        if (coupon != null) {
            OrderCoupon orderCoupon = new OrderCoupon();
            orderCoupon.setOrderId((long) orderId);
            orderCoupon.setCouponId(coupon.getCouponId());
            orderCoupon.setCouponCode(coupon.getCouponCode());
            orderCoupon.setDiscountAmount(BigDecimal.valueOf(discountAmount));

            orderCouponDAO.insert(orderCoupon);
            couponDAO.incrementUsedCount(coupon. getCouponId().intValue());
        }

        //Trừ stock cho tất cả sản phẩm
        for (CartItem item :  cartItems) {
            productDAO.decreaseStock(item.getProductId().intValue(), item.getQuantity());
        }

        //Clear giỏ hàng
        cartItemDAO.clearCart(customerId);

        return order;
    }

    // Tạo mã đơn hàng unique
    private String generateOrderCode() {
        return "ORD-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
    }

    // Lấy đơn hàng theo ID
    public Optional<Order> getById(int orderId) {
        if (orderId <= 0) {
            return Optional.empty();
        }
        return orderDAO.getById(orderId);
    }

    // Lấy đơn hàng theo ID - có kiểm tra owner
    public Optional<Order> getById(int orderId, int customerId) throws Exception {
        Optional<Order> order = orderDAO.getById(orderId);

        if (order. isEmpty()) {
            return Optional.empty();
        }

        if (! order.get().getCustomerId().equals((long) customerId)) {
            throw new Exception("Bạn không có quyền xem đơn hàng này");
        }

        return order;
    }

    // Lấy all đơn hàng của customer
    public List<Order> getByCustomer(int customerId) {
        if (customerId <= 0) {
            return List.of();
        }
        return orderDAO.getByCustomerId(customerId);
    }

    // Lấy đơn hàng theo trạng thái - cho admin
    public List<Order> getByStatus(String status) {
        if (status == null || status. trim().isEmpty()) {
            return List. of();
        }
        return orderDAO.getByStatus(status);
    }

    // Lấy all đơn hàng - cho admin
    public List<Order> getAll() {
        return orderDAO.getAll();
    }

    // Lấy chi tiết đơn hàng
    public List<OrderDetail> getOrderDetails(int orderId) {
        if (orderId <= 0) {
            return List.of();
        }
        return orderDetailDAO.getByOrderId(orderId);
    }

    // Lấy đơn hàng mới nhất của customer
    public Optional<Order> getLatestByCustomer(int customerId) {
        if (customerId <= 0) {
            return Optional.empty();
        }
        return orderDAO. getLatestByCustomerId(customerId);
    }

    // Cập nhật trạng thái đơn hàng - cho admin
    public boolean updateStatus(int orderId, String newStatus) throws Exception {
        Optional<Order> orderOpt = orderDAO.getById(orderId);

        if (orderOpt.isEmpty()) {
            throw new Exception("Đơn hàng không tồn tại");
        }

        Order order = orderOpt.get();
        String currentStatus = order.getOrderStatus();

        if (! isValidStatusTransition(currentStatus, newStatus)) {
            throw new Exception("Không thể chuyển trạng thái từ "
                    + currentStatus + " sang " + newStatus);
        }

        // Hủy đơn -> hoàn lại stock
        if (STATUS_CANCELLED.equals(newStatus)) {
            restoreStock(orderId);
        }

        return orderDAO.updateStatus(orderId, newStatus);
    }

    // Validate chuyển đổi trạng thái
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

    // Hủy đơn hàng - cho customer
    public boolean cancelOrder(int orderId, int customerId) throws Exception {
        Optional<Order> orderOpt = orderDAO.getById(orderId);

        if (orderOpt. isEmpty()) {
            throw new Exception("Đơn hàng không tồn tại");
        }

        Order order = orderOpt. get();

        // Check owner
        if (! order.getCustomerId().equals((long) customerId)) {
            throw new Exception("Bạn không có quyền hủy đơn hàng này");
        }

        // Chỉ hủy được đơn PENDING
        if (! STATUS_PENDING. equals(order.getOrderStatus())) {
            throw new Exception("Chỉ có thể hủy đơn hàng đang chờ xử lý");
        }

        // Hoàn lại stock
        restoreStock(orderId);

        return orderDAO. updateStatus(orderId, STATUS_CANCELLED);
    }

    // Hoàn lại stock khi hủy đơn
    private void restoreStock(int orderId) {
        List<OrderDetail> details = orderDetailDAO. getByOrderId(orderId);

        for (OrderDetail detail : details) {
            productDAO.increaseStock(detail.getProductId().intValue(), detail.getQuantity());
        }
    }

    // Tính tổng doanh thu - đơn hàng COMPLETED
    public double calculateRevenue() {
        return orderDAO.getTotalRevenue();
    }

    // Tính doanh thu theo tháng
    public double getRevenueByMonth(int month, int year) {
        return orderDAO.getRevenueByMonth(month, year);
    }

    // Đếm đơn hàng theo trạng thái
    public int countByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return 0;
        }
        return orderDAO.countByStatus(status);
    }

    // Đếm tổng số đơn hàng
    public int countAll() {
        return orderDAO.countAll();
    }

    // Đếm đơn hàng của customer
    public int countByCustomer(int customerId) {
        if (customerId <= 0) {
            return 0;
        }
        return orderDAO.getByCustomerId(customerId).size();
    }

    // Tìm kiếm đơn hàng
    public List<Order> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAll();
        }
        String sanitizedKeyword = "%" + keyword.trim() + "%";
        return orderDAO. search(sanitizedKeyword);
    }

    // Lấy đơn hàng trong khoảng thời gian
    public List<Order> getByDateRange(String startDate, String endDate) {
        if (startDate == null || endDate == null) {
            return List.of();
        }
        return orderDAO.getByDateRange(startDate, endDate);
    }


    // Tính subtotal từ cart items
    private double calculateSubtotal(List<CartItem> cartItems) {
        double subtotal = 0;

        for (CartItem item : cartItems) {
            Optional<Product> product = productDAO.getById(item.getProductId().intValue());

            if (product.isPresent()) {
                double price = getProductPrice(product.get());
                subtotal += price * item.getQuantity();
            }
        }

        return subtotal;
    }

    // Lấy giá sản phẩm - ưu tiên sale price
    private double getProductPrice(Product product) {
        // salePrice là double primitive, check > 0
        if (product. getSalePrice() > 0) {
            return product.getSalePrice();
        }
        return product.getPrice();
    }

    // Validate và lấy coupon (phiên bản đơn giản)
    private Coupon validateAndGetCoupon(String couponCode, int customerId) throws Exception {
        Optional<Coupon> couponOpt = couponDAO.getByCode(couponCode. trim());

        if (couponOpt.isEmpty()) {
            throw new Exception("Mã giảm giá không tồn tại");
        }

        Coupon coupon = couponOpt.get();

        // Check valid - còn hạn và còn lượt sử dụng
        if (! couponDAO.isValidCoupon(couponCode)) {
            throw new Exception("Mã giảm giá đã hết hạn hoặc đã hết lượt sử dụng");
        }

        // Check customer đã dùng coupon này chưa
        if (orderCouponDAO.hasCustomerUsedCoupon(customerId, coupon.getCouponId().intValue())) {
            throw new Exception("Bạn đã sử dụng mã giảm giá này rồi");
        }

        return coupon;
    }

    // Tính số tiền giảm giá (phiên bản đơn giản)
    private double calculateDiscount(Coupon coupon, double subtotal) {
        double discount = 0;

        if (coupon.getDiscountValue() == null) {
            return 0;
        }

        String discountType = coupon.getDiscountType();
        double discountValue = coupon.getDiscountValue();

        if ("PERCENT".equals(discountType)) {
            // Giảm theo phần trăm
            discount = subtotal * discountValue / 100;
        } else if ("FIXED". equals(discountType)) {
            // Giảm số tiền cố định
            discount = discountValue;
        }

        // Không giảm quá subtotal
        if (discount > subtotal) {
            discount = subtotal;
        }

        return discount;
    }
}