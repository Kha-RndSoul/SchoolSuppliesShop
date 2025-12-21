package com.shop.dao.order;

import com.shop.dao.support.BaseDao;
import com.shop. model.OrderDetail;
import org.jdbi.v3.core.statement.PreparedBatch;

import java.math.BigDecimal;
import java.util.*;

public class OrderDetailDAO extends BaseDao {

    static Map<Integer, OrderDetail> data = new HashMap<>();
    static {
        data.put(1, new OrderDetail(
                1L,                 // orderDetailId
                1L,                 // orderId
                1L,                 // productId
                2,                  // quantity
                BigDecimal.valueOf(24000)
        ));

        data.put(2, new OrderDetail(
                2L,
                1L,
                2L,
                10,
                BigDecimal.valueOf(40000)
        ));

        data.put(3, new OrderDetail(
                3L,
                2L,
                3L,
                1,
                BigDecimal.valueOf(299000)
        ));

        data.put(4, new OrderDetail(
                4L,
                3L,
                4L,
                5,
                BigDecimal.valueOf(40000)
        ));

        data.put(5, new OrderDetail(
                5L,
                3L,
                5L,
                3,
                BigDecimal.valueOf(66000)
        ));
    }

    public List<OrderDetail> getListOrderDetail() {
        return new ArrayList<>(data.values());
    }

    public OrderDetail getOrderDetail(int id) {
        return data.get(id);
    }

    public List<OrderDetail> getList() {
        return get().withHandle(h ->
                h. createQuery("SELECT id, order_id, product_id, product_name, quantity, unit_price, subtotal FROM order_details")
                        .mapToBean(OrderDetail.class)
                        .list()
        );
    }

    public OrderDetail getOrderDetailById(int id) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, order_id, product_id, product_name, quantity, unit_price, subtotal FROM order_details WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(OrderDetail.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public List<OrderDetail> getByOrderId(int orderId) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, order_id, product_id, product_name, quantity, unit_price, subtotal FROM order_details WHERE order_id = :orderId")
                        .bind("orderId", orderId)
                        .mapToBean(OrderDetail.class)
                        .list()
        );
    }

    public List<OrderDetail> getByProductId(int productId) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, order_id, product_id, product_name, quantity, unit_price, subtotal FROM order_details WHERE product_id = :productId")
                        .bind("productId", productId)
                        .mapToBean(OrderDetail.class)
                        .list()
        );
    }

    public OrderDetail getByOrderIdAndProductId(int orderId, int productId) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, order_id, product_id, product_name, quantity, unit_price, subtotal FROM order_details WHERE order_id = :orderId AND product_id = :productId")
                        .bind("orderId", orderId)
                        .bind("productId", productId)
                        .mapToBean(OrderDetail.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public void insert(List<OrderDetail> orderDetails) {
        get().useHandle(h -> {
            PreparedBatch batch = h.prepareBatch(
                    "INSERT INTO order_details (id, order_id, product_id, product_name, quantity, unit_price, subtotal) VALUES (:id, :orderId, :productId, : productName, :quantity, :unitPrice, :subtotal)"
            );
            orderDetails.forEach(od -> batch.bindBean(od).add());
            batch.execute();
        });
    }

    public void insertOrderDetail(OrderDetail orderDetail) {
        get().useHandle(h -> {
            h.createUpdate("INSERT INTO order_details (order_id, product_id, product_name, quantity, unit_price, subtotal) VALUES (:orderId, :productId, :productName, :quantity, :unitPrice, :subtotal)")
                    .bindBean(orderDetail)
                    .execute();
        });
    }

    public void insertBatch(List<OrderDetail> orderDetails) {
        get().useHandle(h -> {
            PreparedBatch batch = h.prepareBatch(
                    "INSERT INTO order_details (order_id, product_id, product_name, quantity, unit_price, subtotal) VALUES (:orderId, :productId, :productName, :quantity, :unitPrice, :subtotal)"
            );
            orderDetails.forEach(od -> batch.bindBean(od).add());
            batch.execute();
        });
    }

    public void updateOrderDetail(OrderDetail orderDetail) {
        get().useHandle(h -> {
            h.createUpdate("UPDATE order_details SET quantity = :quantity, unit_price = :unitPrice, subtotal = :subtotal WHERE id = :id")
                    .bindBean(orderDetail)
                    .execute();
        });
    }

    public void deleteOrderDetail(int id) {
        get().useHandle(h -> {
            h.createUpdate("DELETE FROM order_details WHERE id = :id")
                    .bind("id", id)
                    .execute();
        });
    }

    public void deleteByOrderId(int orderId) {
        get().useHandle(h -> {
            h.createUpdate("DELETE FROM order_details WHERE order_id = :orderId")
                    .bind("orderId", orderId)
                    .execute();
        });
    }

    public int countByOrderId(int orderId) {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(id) FROM order_details WHERE order_id = : orderId")
                        .bind("orderId", orderId)
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public double getTotalByOrderId(int orderId) {
        Double total = get().withHandle(h ->
                h. createQuery("SELECT COALESCE(SUM(subtotal), 0) FROM order_details WHERE order_id = :orderId")
                        .bind("orderId", orderId)
                        .mapTo(Double.class)
                        .one()
        );
        return total != null ? total : 0.0;
    }

    public int getTotalSoldByProductId(int productId) {
        Integer total = get().withHandle(h ->
                h.createQuery("SELECT COALESCE(SUM(quantity), 0) FROM order_details WHERE product_id = :productId")
                        .bind("productId", productId)
                        .mapTo(Integer.class)
                        .one()
        );
        return total != null ? total : 0;
    }

    public static void main(String[] args) {
        OrderDetailDAO dao = new OrderDetailDAO();
        System.out.println("=== INSERT DUMMY DATA ===");
        List<OrderDetail> details = dao.getListOrderDetail();
        dao.insert(details);
        System.out.println("✅ Inserted " + details.size() + " order details");

        System.out. println("\n=== GET BY ORDER ID 1 ===");
        dao.getByOrderId(1).forEach(System.out::println);
    }
}