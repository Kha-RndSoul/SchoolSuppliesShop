package com. shop.dao.order;

import com. shop.model. Coupon;
import org.jdbi. v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org. jdbi.v3.sqlobject. statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util. List;
import java.util.Optional;

@RegisterBeanMapper(Coupon.class)
public interface CouponDAO {

    /// Lấy all coupon
    @SqlQuery("Select id, code, description, discount_type, discount_value, " +
            "min_order_amount, max_discount_amount, max_uses, used_count, " +
            "start_date, expiry_date, is_active, created_at, updated_at " +
            "From coupons " +
            "Order by created_at Desc")
    List<Coupon> getAll();

    /// Lấy coupon theo ID
    @SqlQuery("Select id, code, description, discount_type, discount_value, " +
            "min_order_amount, max_discount_amount, max_uses, used_count, " +
            "start_date, expiry_date, is_active, created_at, updated_at " +
            "From coupons " +
            "Where id = :id")
    Optional<Coupon> getById(@Bind("id") int id);

    /// Thêm coupon mới
    @SqlUpdate("Insert into coupons " +
            "(code, description, discount_type, discount_value, min_order_amount, " +
            "max_discount_amount, max_uses, used_count, start_date, expiry_date, is_active, created_at) " +
            "Values (:code, :description, :discountType, :discountValue, : minOrderAmount, " +
            ": maxDiscountAmount, :maxUses, 0, :startDate, :expiryDate, :isActive, NOW())")
    @GetGeneratedKeys
    int insert(@BindBean Coupon coupon);

    /// Cập nhật coupon
    @SqlUpdate("Update coupons Set " +
            "code = :code, " +
            "description = :description, " +
            "discount_type = :discountType, " +
            "discount_value = :discountValue, " +
            "min_order_amount = :minOrderAmount, " +
            "max_discount_amount = : maxDiscountAmount, " +
            "max_uses = : maxUses, " +
            "start_date = :startDate, " +
            "expiry_date = :expiryDate, " +
            "is_active = :isActive, " +
            "updated_at = NOW() " +
            "Where id = : id")
    boolean update(@BindBean Coupon coupon);

    /// Xóa coupon
    @SqlUpdate("Delete From coupons Where id = : id")
    boolean delete(@Bind("id") int id);


    /// Lấy coupon theo mã code
    @SqlQuery("Select id, code, description, discount_type, discount_value, " +
            "min_order_amount, max_discount_amount, max_uses, used_count, " +
            "start_date, expiry_date, is_active, created_at, updated_at " +
            "From coupons " +
            "Where code = :code")
    Optional<Coupon> getByCode(@Bind("code") String code);

    /// Check mã coupon có tồn tại không
    @SqlQuery("Select Count(id) > 0 From coupons Where code = :code")
    boolean existsByCode(@Bind("code") String code);

    /// Lấy all coupon đang hoạt động
    @SqlQuery("Select id, code, description, discount_type, discount_value, " +
            "min_order_amount, max_discount_amount, max_uses, used_count, " +
            "start_date, expiry_date, is_active, created_at, updated_at " +
            "From coupons " +
            "Where is_active = true " +
            "And expiry_date >= Curdate() " +
            "And (max_uses Is Null Or used_count < max_uses) " +
            "Order by expiry_date Asc")
    List<Coupon> getActiveCoupons();

    /// Lấy all coupon đã hết hạn
    @SqlQuery("Select id, code, description, discount_type, discount_value, " +
            "min_order_amount, max_discount_amount, max_uses, used_count, " +
            "start_date, expiry_date, is_active, created_at, updated_at " +
            "From coupons " +
            "Where expiry_date < Curdate() " +
            "Order by expiry_date Desc")
    List<Coupon> getExpiredCoupons();

    /// Cập nhật trạng thái active
    @SqlUpdate("Update coupons " +
            "Set is_active = : isActive, updated_at = NOW() " +
            "Where id = :id")
    boolean updateActiveStatus(@Bind("id") int id, @Bind("isActive") boolean isActive);

    /// Tăng số lần sử dụng coupon
    @SqlUpdate("Update coupons " +
            "Set used_count = used_count + 1, updated_at = NOW() " +
            "Where id = :id")
    boolean incrementUsedCount(@Bind("id") int id);

    /// Check coupon có hợp lệ để sử dụng không
    @SqlQuery("Select Count(id) > 0 " +
            "From coupons " +
            "Where code = :code " +
            "And is_active = true " +
            "And start_date <= Curdate() " +
            "And expiry_date >= Curdate() " +
            "And (max_uses Is Null Or used_count < max_uses)")
    boolean isValidCoupon(@Bind("code") String code);

    /// Lấy coupon theo loại giảm giá - PERCENT hoặc FIXED
    @SqlQuery("Select id, code, description, discount_type, discount_value, " +
            "min_order_amount, max_discount_amount, max_uses, used_count, " +
            "start_date, expiry_date, is_active, created_at, updated_at " +
            "From coupons " +
            "Where discount_type = : discountType And is_active = true " +
            "Order by discount_value Desc")
    List<Coupon> getByDiscountType(@Bind("discountType") String discountType);

    /// Đếm tổng số coupon
    @SqlQuery("Select Count(id) From coupons")
    int countAll();

    /// Đếm số coupon đang hoạt động
    @SqlQuery("Select Count(id) " +
            "From coupons " +
            "Where is_active = true And expiry_date >= Curdate()")
    int countActive();
}