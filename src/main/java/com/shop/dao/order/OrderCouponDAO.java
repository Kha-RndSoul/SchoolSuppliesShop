package com.shop.dao.order;

import com.shop.dao.support.BaseDao;
import com.shop.model.OrderCoupon;
import org.jdbi.v3.core.statement.PreparedBatch;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.*;

public class OrderCouponDAO extends BaseDao {

    static Map<Integer, OrderCoupon> data = new HashMap<>();
    static {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        data.put(1, new OrderCoupon(1, 1, 1, new BigDecimal("50000"), now));
        data.put(2, new OrderCoupon(2, 3, 2, new BigDecimal("50000"), now));
    }

    public List<OrderCoupon> getListOrderCoupon() {
        return new ArrayList<>(data.values());
    }

    public OrderCoupon getOrderCoupon(int id) {
        return data.get(id);
    }

    public List<OrderCoupon> getList() {
        return get().withHandle(h ->
                h. createQuery("SELECT id, order_id, coupon_id, coupon_code, discount_amount, created_at FROM order_coupons")
                        .mapToBean(OrderCoupon.class)
                        .list()
        );
    }

    public OrderCoupon getOrderCouponById(int id) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, order_id, coupon_id, coupon_code, discount_amount, created_at FROM order_coupons WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(OrderCoupon.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public OrderCoupon getByOrderId(int orderId) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, order_id, coupon_id, coupon_code, discount_amount, created_at FROM order_coupons WHERE order_id = :orderId")
                        .bind("orderId", orderId)
                        .mapToBean(OrderCoupon.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public List<OrderCoupon> getByCouponId(int couponId) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, order_id, coupon_id, coupon_code, discount_amount, created_at FROM order_coupons WHERE coupon_id = :couponId")
                        .bind("couponId", couponId)
                        .mapToBean(OrderCoupon.class)
                        .list()
        );
    }

    public List<OrderCoupon> getByCouponCode(String couponCode) {
        return get().withHandle(h ->
                h. createQuery("SELECT id, order_id, coupon_id, coupon_code, discount_amount, created_at FROM order_coupons WHERE coupon_code = : couponCode")
                        .bind("couponCode", couponCode)
                        .mapToBean(OrderCoupon.class)
                        .list()
        );
    }

    public boolean hasOrderUsedCoupon(int orderId) {
        return get().withHandle(h ->
                h. createQuery("SELECT COUNT(id) > 0 FROM order_coupons WHERE order_id = :orderId")
                        .bind("orderId", orderId)
                        .mapTo(Boolean.class)
                        .one()
        );
    }

    public boolean isCouponAppliedToOrder(int orderId, int couponId) {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(id) > 0 FROM order_coupons WHERE order_id = :orderId AND coupon_id = :couponId")
                        .bind("orderId", orderId)
                        .bind("couponId", couponId)
                        .mapTo(Boolean. class)
                        .one()
        );
    }

    public boolean hasCustomerUsedCoupon(int customerId, int couponId) {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(oc.id) > 0 FROM order_coupons oc JOIN orders o ON oc.order_id = o.id WHERE o. customer_id = :customerId AND oc.coupon_id = :couponId")
                        .bind("customerId", customerId)
                        .bind("couponId", couponId)
                        .mapTo(Boolean.class)
                        .one()
        );
    }

    public void insert(List<OrderCoupon> orderCoupons) {
        get().useHandle(h -> {
            PreparedBatch batch = h.prepareBatch(
                    "INSERT INTO order_coupons (id, order_id, coupon_id, coupon_code, discount_amount, created_at) VALUES (:id, :orderId, :couponId, :couponCode, : discountAmount, NOW())"
            );
            orderCoupons.forEach(oc -> batch.bindBean(oc).add());
            batch.execute();
        });
    }

    public void insertOrderCoupon(OrderCoupon orderCoupon) {
        get().useHandle(h -> {
            h.createUpdate("INSERT INTO order_coupons (order_id, coupon_id, coupon_code, discount_amount, created_at) VALUES (:orderId, :couponId, :couponCode, :discountAmount, NOW())")
                    .bindBean(orderCoupon)
                    .execute();
        });
    }

    public void updateOrderCoupon(OrderCoupon orderCoupon) {
        get().useHandle(h -> {
            h.createUpdate("UPDATE order_coupons SET discount_amount = :discountAmount WHERE id = :id")
                    .bindBean(orderCoupon)
                    .execute();
        });
    }

    public void deleteOrderCoupon(int id) {
        get().useHandle(h -> {
            h.createUpdate("DELETE FROM order_coupons WHERE id = :id")
                    .bind("id", id)
                    .execute();
        });
    }

    public void deleteByOrderId(int orderId) {
        get().useHandle(h -> {
            h.createUpdate("DELETE FROM order_coupons WHERE order_id = :orderId")
                    .bind("orderId", orderId)
                    .execute();
        });
    }

    public int countUsagesByCouponId(int couponId) {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(id) FROM order_coupons WHERE coupon_id = :couponId")
                        .bind("couponId", couponId)
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public double getTotalDiscountByCouponId(int couponId) {
        Double total = get().withHandle(h ->
                h.createQuery("SELECT COALESCE(SUM(discount_amount), 0) FROM order_coupons WHERE coupon_id = :couponId")
                        .bind("couponId", couponId)
                        .mapTo(Double.class)
                        .one()
        );
        return total != null ? total : 0.0;
    }

    public static void main(String[] args) {
        OrderCouponDAO dao = new OrderCouponDAO();
        System.out.println("=== INSERT DUMMY DATA ===");
        List<OrderCoupon> orderCoupons = dao.getListOrderCoupon();
        dao.insert(orderCoupons);
        System.out.println("✅ Inserted " + orderCoupons.size() + " order coupons");

        System.out.println("\n=== GET FROM DB ===");
        dao.getList().forEach(System.out::println);
    }
}