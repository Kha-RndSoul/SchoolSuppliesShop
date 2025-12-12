package com.shop.dao. order;

import com.shop.model. OrderCoupon;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org. jdbi.v3.sqlobject. customizer.Bind;
import org. jdbi.v3.sqlobject. customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi. v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java. util.List;
import java.util. Optional;

@RegisterBeanMapper(OrderCoupon.class)
public interface OrderCouponDAO {

    /// Lấy all order_coupon
    @SqlQuery("Select id, order_id, coupon_id, coupon_code, discount_amount, created_at " +
            "From order_coupons")
    List<OrderCoupon> getAll();

    /// Lấy order_coupon theo ID
    @SqlQuery("Select id, order_id, coupon_id, coupon_code, discount_amount, created_at " +
            "From order_coupons " +
            "Where id = :id")
    Optional<OrderCoupon> getById(@Bind("id") int id);

    /// Thêm order_coupon mới - áp dụng coupon cho đơn hàng
    @SqlUpdate("Insert into order_coupons " +
            "(order_id, coupon_id, coupon_code, discount_amount, created_at) " +
            "Values (:orderId, :couponId, :couponCode, :discountAmount, NOW())")
    @GetGeneratedKeys
    int insert(@BindBean OrderCoupon orderCoupon);

    /// Cập nhật order_coupon
    @SqlUpdate("Update order_coupons " +
            "Set discount_amount = :discountAmount " +
            "Where id = :id")
    boolean update(@BindBean OrderCoupon orderCoupon);

    /// Xóa order_coupon
    @SqlUpdate("Delete From order_coupons Where id = : id")
    boolean delete(@Bind("id") int id);


    /// Lấy order_coupon theo order_id
    @SqlQuery("Select id, order_id, coupon_id, coupon_code, discount_amount, created_at " +
            "From order_coupons " +
            "Where order_id = :orderId")
    Optional<OrderCoupon> getByOrderId(@Bind("orderId") int orderId);

    /// Lấy all đơn hàng đã sử dụng 1 coupon
    @SqlQuery("Select id, order_id, coupon_id, coupon_code, discount_amount, created_at " +
            "From order_coupons " +
            "Where coupon_id = :couponId")
    List<OrderCoupon> getByCouponId(@Bind("couponId") int couponId);

    /// Check đơn hàng đã áp dụng coupon chưa
    @SqlQuery("Select Count(id) > 0 " +
            "From order_coupons " +
            "Where order_id = :orderId")
    boolean hasOrderUsedCoupon(@Bind("orderId") int orderId);

    /// Check coupon đã được sử dụng cho đơn hàng này chưa
    @SqlQuery("Select Count(id) > 0 " +
            "From order_coupons " +
            "Where order_id = :orderId And coupon_id = :couponId")
    boolean isCouponAppliedToOrder(@Bind("orderId") int orderId, @Bind("couponId") int couponId);

    /// Xóa coupon khỏi đơn hàng
    @SqlUpdate("Delete From order_coupons Where order_id = :orderId")
    boolean deleteByOrderId(@Bind("orderId") int orderId);

    /// Đếm số lần coupon đã được sử dụng
    @SqlQuery("Select Count(id) From order_coupons Where coupon_id = : couponId")
    int countUsagesByCouponId(@Bind("couponId") int couponId);

    /// Tính tổng số tiền đã giảm giá bởi 1 coupon
    @SqlQuery("Select Coalesce(Sum(discount_amount), 0) " +
            "From order_coupons " +
            "Where coupon_id = : couponId")
    double getTotalDiscountByCouponId(@Bind("couponId") int couponId);

    /// Lấy order_coupon theo coupon_code
    @SqlQuery("Select id, order_id, coupon_id, coupon_code, discount_amount, created_at " +
            "From order_coupons " +
            "Where coupon_code = :couponCode")
    List<OrderCoupon> getByCouponCode(@Bind("couponCode") String couponCode);

    /// Check khách hàng đã sử dụng coupon này chưa - thông qua order
    @SqlQuery("Select Count(oc.id) > 0 " +
            "From order_coupons oc " +
            "Join orders o On oc.order_id = o.id " +
            "Where o.customer_id = :customerId And oc.coupon_id = :couponId")
    boolean hasCustomerUsedCoupon(@Bind("customerId") int customerId, @Bind("couponId") int couponId);
}