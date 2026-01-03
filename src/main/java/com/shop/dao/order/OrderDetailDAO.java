package com.shop.dao.order;

import com.shop.dao.support.BaseDao;
import com.shop.model.OrderDetail;
import org.jdbi.v3.core.statement.PreparedBatch;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.*;

public class OrderDetailDAO extends BaseDao {

    static Map<Integer, OrderDetail> data = new HashMap<>();

    static {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        data.put(1, new OrderDetail(1, 1, 1, "Product 1",
                new BigDecimal("12000"), 2, new BigDecimal("24000"), now));
        data.put(2, new OrderDetail(2, 1, 2, "Product 2",
                new BigDecimal("4000"), 10, new BigDecimal("40000"), now));
        data.put(3, new OrderDetail(3, 2, 3, "Product 3",
                new BigDecimal("299000"), 1, new BigDecimal("299000"), now));
        data.put(4, new OrderDetail(4, 3, 4, "Product 4",
                new BigDecimal("8000"), 5, new BigDecimal("40000"), now));
        data.put(5, new OrderDetail(5, 3, 5, "Product 5",
                new BigDecimal("22000"), 3, new BigDecimal("66000"), now));
    }

    public List<OrderDetail> getListOrderDetail() {
        return new ArrayList<>(data.values());
    }

    public OrderDetail getOrderDetail(int id) {
        return data.get(id);
    }

    public List<OrderDetail> getList() {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT id, order_id, product_id, product_name, " +
                                        "quantity, unit_price, subtotal FROM order_details"
                        )
                        .mapToBean(OrderDetail.class)
                        .list()
        );
    }

    public OrderDetail getOrderDetailById(int id) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT id, order_id, product_id, product_name, " +
                                        "quantity, unit_price, subtotal FROM order_details WHERE id = :id"
                        )
                        .bind("id", id)
                        .mapToBean(OrderDetail.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public List<OrderDetail> getByOrderId(int orderId) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT id, order_id, product_id, product_name, " +
                                        "quantity, unit_price, subtotal FROM order_details WHERE order_id = :orderId"
                        )
                        .bind("orderId", orderId)
                        .mapToBean(OrderDetail.class)
                        .list()
        );
    }

    public List<OrderDetail> getByProductId(int productId) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT id, order_id, product_id, product_name, " +
                                        "quantity, unit_price, subtotal FROM order_details WHERE product_id = :productId"
                        )
                        .bind("productId", productId)
                        .mapToBean(OrderDetail.class)
                        .list()
        );
    }

    public OrderDetail getByOrderIdAndProductId(int orderId, int productId) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT id, order_id, product_id, product_name, " +
                                        "quantity, unit_price, subtotal FROM order_details " +
                                        "WHERE order_id = :orderId AND product_id = :productId"
                        )
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
                    "INSERT INTO order_details " +
                            "(id, order_id, product_id, product_name, quantity, unit_price, subtotal) " +
                            "VALUES (:id, :orderId, :productId, :productName, :quantity, :unitPrice, :subtotal)"
            );
            orderDetails.forEach(od -> batch.bindBean(od).add());
            batch.execute();
        });
    }

    public void insertOrderDetail(OrderDetail orderDetail) {
        get().useHandle(h -> {
            h.createUpdate(
                            "INSERT INTO order_details " +
                                    "(order_id, product_id, product_name, quantity, unit_price, subtotal) " +
                                    "VALUES (:orderId, :productId, :productName, :quantity, :unitPrice, :subtotal)"
                    )
                    .bindBean(orderDetail)
                    .execute();
        });
    }

    public void insertBatch(List<OrderDetail> orderDetails) {
        get().useHandle(h -> {
            PreparedBatch batch = h.prepareBatch(
                    "INSERT INTO order_details " +
                            "(order_id, product_id, product_name, quantity, unit_price, subtotal) " +
                            "VALUES (:orderId, :productId, :productName, :quantity, :unitPrice, :subtotal)"
            );
            orderDetails.forEach(od -> batch.bindBean(od).add());
            batch.execute();
        });
    }

    public void updateOrderDetail(OrderDetail orderDetail) {
        get().useHandle(h -> {
            h.createUpdate(
                            "UPDATE order_details SET quantity = :quantity, " +
                                    "unit_price = :unitPrice, subtotal = :subtotal WHERE id = :id"
                    )
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
                h.createQuery(
                                "SELECT COUNT(id) FROM order_details WHERE order_id = :orderId"
                        )
                        .bind("orderId", orderId)
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public double getTotalByOrderId(int orderId) {
        Double total = get().withHandle(h ->
                h.createQuery(
                                "SELECT COALESCE(SUM(subtotal), 0) FROM order_details WHERE order_id = :orderId"
                        )
                        .bind("orderId", orderId)
                        .mapTo(Double.class)
                        .one()
        );
        return total != null ? total : 0.0;
    }

    public int getTotalSoldByProductId(int productId) {
        Integer total = get().withHandle(h ->
                h.createQuery(
                                "SELECT COALESCE(SUM(quantity), 0) FROM order_details WHERE product_id = :productId"
                        )
                        .bind("productId", productId)
                        .mapTo(Integer.class)
                        .one()
        );
        return total != null ? total : 0;
    }

    public static void main(String[] args) {
        OrderDetailDAO dao = new OrderDetailDAO();
        System.out.println("INSERT DUMMY DATA");
        List<OrderDetail> details = dao.getListOrderDetail();
        dao.insert(details);
        System.out.println("Inserted " + details.size() + " order details");

        System.out.println("\nGET BY ORDER ID 1");
        dao.getByOrderId(1).forEach(System.out::println);
    }
}
