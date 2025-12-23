package com.shop.dao.order;

import com.shop.dao.support.BaseDao;
import com.shop.model.Order;
import org.jdbi.v3.core.statement.PreparedBatch;

import java.math.BigDecimal;
import java.util.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
public class OrderDAO extends BaseDao {

    static Map<Integer, Order> data = new HashMap<>();
    static {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        data.put(1, new Order(1, 1, "ORD-001", "PENDING", "COD", "PENDING", new BigDecimal("500000"), "Nguyễn Văn A", "0983123123", "123 Đường ABC, TP.XYZ", "", now, now));
        data.put(2, new Order(2, 2, "ORD-002", "PROCESSING", "BANKING", "PAID", new BigDecimal("350000"), "Trần Thị B", "0905999999", "456 Đường DEF, TP.XYZ", "Không lấy túi nhựa", now, now));
        data.put(3, new Order(3, 1, "ORD-003", "COMPLETED", "COD", "PAID", new BigDecimal("750000"), "Nguyễn Văn A", "0983123123", "123 Đường ABC, TP.XYZ", "Giao nhanh", now, now));
        data.put(4, new Order(4, 3, "ORD-004", "CANCELLED", "COD", "FAILED", new BigDecimal("220000"), "Lê Minh C", "0912999888", "789 Đường GHI, TP.XYZ", "Khách hủy do không liên lạc", now, now));
    }

    public List<Order> getListOrder() {
        return new ArrayList<>(data.values());
    }

    public Order getOrder(int id) {
        return data.get(id);
    }

    public List<Order> getList() {
        return get().withHandle(h ->
                h.createQuery("SELECT id, customer_id, total_amount, status, shipping_address, shipping_phone, payment_method, note, created_at, updated_at FROM orders ORDER BY created_at DESC")
                        .mapToBean(Order.class)
                        .list()
        );
    }

    public Order getOrderById(int id) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, customer_id, total_amount, status, shipping_address, shipping_phone, payment_method, note, created_at, updated_at FROM orders WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(Order. class)
                        .findOne()
                        .orElse(null)
        );
    }

    public List<Order> getByCustomerId(int customerId) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, customer_id, total_amount, status, shipping_address, shipping_phone, payment_method, note, created_at, updated_at FROM orders WHERE customer_id = :customerId ORDER BY created_at DESC")
                        .bind("customerId", customerId)
                        .mapToBean(Order. class)
                        .list()
        );
    }

    public List<Order> getByStatus(String status) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, customer_id, total_amount, status, shipping_address, shipping_phone, payment_method, note, created_at, updated_at FROM orders WHERE status = :status ORDER BY created_at DESC")
                        .bind("status", status)
                        .mapToBean(Order.class)
                        .list()
        );
    }

    public Order getLatestByCustomerId(int customerId) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, customer_id, total_amount, status, shipping_address, shipping_phone, payment_method, note, created_at, updated_at FROM orders WHERE customer_id = :customerId ORDER BY created_at DESC LIMIT 1")
                        .bind("customerId", customerId)
                        .mapToBean(Order.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public List<Order> search(String keyword) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, customer_id, total_amount, status, shipping_address, shipping_phone, payment_method, note, created_at, updated_at FROM orders WHERE shipping_address LIKE CONCAT('%', :keyword, '%') OR note LIKE CONCAT('%', :keyword, '%') ORDER BY created_at DESC")
                        .bind("keyword", keyword)
                        .mapToBean(Order. class)
                        .list()
        );
    }

    public void insert(List<Order> orders) {
        get().useHandle(h -> {
            PreparedBatch batch = h.prepareBatch(
                    "INSERT INTO orders (id, customer_id, total_amount, status, shipping_address, shipping_phone, payment_method, note, created_at) VALUES (:id, :customerId, :totalAmount, :status, :shippingAddress, :shippingPhone, :paymentMethod, : note, NOW())"
            );
            orders.forEach(o -> batch.bindBean(o).add());
            batch.execute();
        });
    }

    public int insertOrder(Order order) {
        return get().withHandle(h ->
                h.createUpdate("INSERT INTO orders (customer_id, total_amount, status, shipping_address, shipping_phone, payment_method, note, created_at) VALUES (:customerId, :totalAmount, :status, :shippingAddress, :shippingPhone, :paymentMethod, :note, NOW())")
                        .bindBean(order)
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public void updateOrder(Order order) {
        get().useHandle(h -> {
            h.createUpdate("UPDATE orders SET total_amount = :totalAmount, status = :status, shipping_address = :shippingAddress, shipping_phone = :shippingPhone, payment_method = :paymentMethod, note = :note, updated_at = NOW() WHERE id = :id")
                    .bindBean(order)
                    .execute();
        });
    }

    public void updateStatus(int id, String status) {
        get().useHandle(h -> {
            h. createUpdate("UPDATE orders SET status = :status, updated_at = NOW() WHERE id = :id")
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
        return get().withHandle(h ->
                h. createQuery("SELECT COUNT(id) FROM orders")
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public int countByStatus(String status) {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(id) FROM orders WHERE status = :status")
                        .bind("status", status)
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public double getTotalRevenue() {
        Double revenue = get().withHandle(h ->
                h.createQuery("SELECT COALESCE(SUM(total_amount), 0) FROM orders WHERE status = 'COMPLETED'")
                        .mapTo(Double. class)
                        .one()
        );
        return revenue != null ? revenue : 0.0;
    }

    public double getRevenueByMonth(int month, int year) {
        Double revenue = get().withHandle(h ->
                h.createQuery("SELECT COALESCE(SUM(total_amount), 0) FROM orders WHERE status = 'COMPLETED' AND MONTH(created_at) = :month AND YEAR(created_at) = :year")
                        .bind("month", month)
                        .bind("year", year)
                        .mapTo(Double.class)
                        .one()
        );
        return revenue != null ? revenue : 0.0;
    }

    public static void main(String[] args) {
        OrderDAO dao = new OrderDAO();
        System.out. println("=== INSERT DUMMY DATA ===");
        List<Order> orders = dao.getListOrder();
        dao.insert(orders);
        System.out.println("✅ Inserted " + orders.size() + " orders");

        System.out. println("\n=== GET BY STATUS PENDING ===");
        dao.getByStatus("PENDING").forEach(System.out::println);
    }
}