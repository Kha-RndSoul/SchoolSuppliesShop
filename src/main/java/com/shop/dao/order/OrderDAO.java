package com.shop.dao.order;

import com.shop.dao.support.BaseDao;
import com.shop.model.Order;
import org.jdbi.v3.core.statement.PreparedBatch;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class OrderDAO extends BaseDao {

    public List<Order> getList() {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT id, customer_id, order_code, order_status, payment_method, payment_status, " +
                                        "total_amount, shipping_name, shipping_phone, shipping_address, " +
                                        "note, created_at, updated_at " +
                                        "FROM orders ORDER BY created_at DESC"
                        )
                        .mapToBean(Order.class)
                        .list()
        );
    }

    public Order getOrderById(int id) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT id, customer_id, order_code, order_status, payment_method, payment_status, " +
                                        "total_amount, shipping_name, shipping_phone, shipping_address, " +
                                        "note, created_at, updated_at " +
                                        "FROM orders WHERE id = :id"
                        )
                        .bind("id", id)
                        .mapToBean(Order.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public List<Order> getByCustomerId(int customerId) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT id, customer_id, order_code, order_status, payment_method, payment_status, " +
                                        "total_amount, shipping_name, shipping_phone, shipping_address, " +
                                        "note, created_at, updated_at " +
                                        "FROM orders WHERE customer_id = :customerId ORDER BY created_at DESC"
                        )
                        .bind("customerId", customerId)
                        .mapToBean(Order.class)
                        .list()
        );
    }

    public List<Order> getByStatus(String status) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT id, customer_id, order_code, order_status, payment_method, payment_status, " +
                                        "total_amount, shipping_name, shipping_phone, shipping_address, " +
                                        "note, created_at, updated_at " +
                                        "FROM orders WHERE order_status = :status ORDER BY created_at DESC"
                        )
                        .bind("status", status)
                        .mapToBean(Order.class)
                        .list()
        );
    }

    public Order getLatestByCustomerId(int customerId) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT id, customer_id, order_code, order_status, payment_method, payment_status, " +
                                        "total_amount, shipping_name, shipping_phone, shipping_address, " +
                                        "note, created_at, updated_at " +
                                        "FROM orders WHERE customer_id = :customerId " +
                                        "ORDER BY created_at DESC LIMIT 1"
                        )
                        .bind("customerId", customerId)
                        .mapToBean(Order.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public List<Order> search(String keyword) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT id, customer_id, order_code, order_status, payment_method, payment_status, " +
                                        "total_amount, shipping_name, shipping_phone, shipping_address, " +
                                        "note, created_at, updated_at " +
                                        "FROM orders WHERE shipping_address LIKE CONCAT('%', :keyword, '%') " +
                                        "OR note LIKE CONCAT('%', :keyword, '%') ORDER BY created_at DESC"
                        )
                        .bind("keyword", keyword)
                        .mapToBean(Order.class)
                        .list()
        );
    }

    public void insert(List<Order> orders) {
        get().useHandle(h -> {
            PreparedBatch batch = h.prepareBatch(
                    "INSERT INTO orders " +
                            "(id, customer_id, order_code, order_status, payment_method, payment_status, " +
                            "total_amount, shipping_name, shipping_phone, shipping_address, note, created_at) " +
                            "VALUES (:id, :customerId, :orderCode, :orderStatus, :paymentMethod, :paymentStatus, " +
                            ":totalAmount, :shippingName, :shippingPhone, :shippingAddress, :note, NOW())"
            );
            orders.forEach(o -> batch.bindBean(o).add());
            batch.execute();
        });
    }

    public int insertOrder(Order order) {
        return get().withHandle(h ->
                h.createUpdate(
                                "INSERT INTO orders " +
                                        "(customer_id, order_code, order_status, payment_method, payment_status, " +
                                        "total_amount, shipping_name, shipping_phone, shipping_address, note, created_at) " +
                                        "VALUES (:customerId, :orderCode, :orderStatus, :paymentMethod, :paymentStatus, " +
                                        ":totalAmount, :shippingName, :shippingPhone, :shippingAddress, :note, NOW())"
                        )
                        .bindBean(order)
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public void updateOrder(Order order) {
        get().useHandle(h -> {
            h.createUpdate(
                            "UPDATE orders SET order_status = :orderStatus, payment_method = :paymentMethod, " +
                                    "payment_status = :paymentStatus, total_amount = :totalAmount, " +
                                    "shipping_name = :shippingName, shipping_phone = :shippingPhone, " +
                                    "shipping_address = :shippingAddress, note = :note, updated_at = NOW() " +
                                    "WHERE id = :id"
                    )
                    .bindBean(order)
                    .execute();
        });
    }

    public void updateStatus(int id, String status) {
        get().useHandle(h -> {
            h.createUpdate(
                            "UPDATE orders SET order_status = :status, updated_at = NOW() WHERE id = :id"
                    )
                    .bind("id", id)
                    .bind("status", status)
                    .execute();
        });
    }

    public void deleteOrder(int id) {
        get().useHandle(h -> {
            h.createUpdate("DELETE FROM orders WHERE id = :id")
                    .bind("id", id)
                    .execute();
        });
    }

    public int countAll() {
        return get().withHandle(h -> h.createQuery("SELECT COUNT(id) FROM orders")
                .mapTo(Integer.class)
                .one()
        );
    }

    public int countByStatus(String status) {
        return get().withHandle(h -> h.createQuery(
                                "SELECT COUNT(id) FROM orders WHERE order_status = :status"
                        )
                        .bind("status", status)
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public double getTotalRevenue() {
        Double revenue = get().withHandle(h -> h.createQuery(
                                "SELECT COALESCE(SUM(total_amount), 0) FROM orders WHERE order_status = 'COMPLETED'"
                        )
                        .mapTo(Double.class)
                        .one()
        );
        return revenue != null ? revenue : 0.0;
    }

    public double getRevenueByMonth(int month, int year) {
        Double revenue = get().withHandle(h -> h.createQuery(
                                "SELECT COALESCE(SUM(total_amount), 0) FROM orders " +
                                        "WHERE order_status = 'COMPLETED' " +
                                        "AND MONTH(created_at) = :month AND YEAR(created_at) = :year"
                        )
                        .bind("month", month)
                        .bind("year", year)
                        .mapTo(Double.class)
                        .one()
        );
        return revenue != null ? revenue : 0.0;
    }

    // ============================================
    // METHODS MỚI CHO THỐNG KÊ DASHBOARD
    // ============================================

    /**
     * Lấy doanh thu theo khoảng thời gian (chỉ đơn COMPLETED)
     */
    public double getRevenueByDateRange(LocalDateTime start, LocalDateTime end) {
        Double revenue = get().withHandle(h -> h.createQuery(
                                "SELECT COALESCE(SUM(total_amount), 0) FROM orders " +
                                        "WHERE order_status = 'COMPLETED' " +
                                        "AND created_at >= :start AND created_at <= :end"
                        )
                        .bind("start", Timestamp.valueOf(start))
                        .bind("end", Timestamp.valueOf(end))
                        .mapTo(Double.class)
                        .one()
        );
        return revenue != null ? revenue : 0.0;
    }

    /**
     * Đếm số đơn hàng theo khoảng thời gian (không tính CANCELLED)
     */
    public int countOrdersByDateRange(LocalDateTime start, LocalDateTime end) {
        return get().withHandle(h -> h.createQuery(
                                "SELECT COUNT(*) FROM orders " +
                                        "WHERE order_status != 'CANCELLED' " +
                                        "AND created_at >= :start AND created_at <= :end"
                        )
                        .bind("start", Timestamp.valueOf(start))
                        .bind("end", Timestamp.valueOf(end))
                        .mapTo(Integer.class)
                        .one()
        );
    }

    /**
     * Đếm số đơn hàng theo trạng thái và khoảng thời gian
     */
    public int countOrdersByStatusAndDateRange(String status, LocalDateTime start, LocalDateTime end) {
        return get().withHandle(h -> h.createQuery(
                                "SELECT COUNT(*) FROM orders " +
                                        "WHERE order_status = :status " +
                                        "AND created_at >= :start AND created_at <= :end"
                        )
                        .bind("status", status)
                        .bind("start", Timestamp.valueOf(start))
                        .bind("end", Timestamp.valueOf(end))
                        .mapTo(Integer.class)
                        .one()
        );
    }
    /**
     * Lấy đơn hàng gần nhất kèm tên khách hàng (JOIN)
     */
    public static List<Map<String, Object>> getRecentOrdersWithCustomer(int limit) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT o.id, o.customer_id, o.order_code, o.order_status, " +
                                        "o.total_amount, o.created_at, " +
                                        "COALESCE(c.full_name, o.shipping_name, 'Khách vãng lai') as customer_name " +
                                        "FROM orders o " +
                                        "LEFT JOIN customers c ON o.customer_id = c.id " +
                                        "ORDER BY o.created_at DESC LIMIT :limit"
                        )
                        .bind("limit", limit)
                        .mapToMap()
                        .list()
        );
    }
    /**
     * Lấy danh sách đơn hàng của một khách hàng cụ thể
     */
    public List<Order> getOrdersByCustomerId(int customerId) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT id, customer_id, order_code, order_status, payment_method, payment_status, " +
                                        "total_amount, shipping_name, shipping_phone, shipping_address, " +
                                        "note, created_at, updated_at " +
                                        "FROM orders " +
                                        "WHERE customer_id = :customerId " +
                                        "ORDER BY created_at DESC"
                        )
                        .bind("customerId", customerId)
                        .mapToBean(Order.class)
                        .list()
        );
    }
}