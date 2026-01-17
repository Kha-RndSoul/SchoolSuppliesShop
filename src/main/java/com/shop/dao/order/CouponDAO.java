package com.shop.dao.order;

import com.shop.dao.support.BaseDao;
import com.shop.model.Coupon;
import org.jdbi.v3.core.statement.PreparedBatch;
import java.util.*;

public class CouponDAO extends BaseDao {

    /**
     * Lấy danh sách tất cả coupons
     */
    public List<Coupon> getList() {
        return get().withHandle(h ->
                h.createQuery("SELECT id, coupon_code, image_url, discount_type, discount_value, " +
                                "min_order_amount, max_uses, used_count, start_date, end_date, " +
                                "is_active, created_at " +
                                "FROM coupons ORDER BY created_at DESC")
                        .mapToBean(Coupon.class)
                        .list()
        );
    }

    /**
     * Lấy coupon theo ID
     */
    public Coupon getCouponById(int id) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, coupon_code, image_url, discount_type, discount_value, " +
                                "min_order_amount, max_uses, used_count, start_date, end_date, " +
                                "is_active, created_at " +
                                "FROM coupons WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(Coupon.class)
                        .findOne()
                        .orElse(null)
        );
    }

    /**
     * Lấy coupon theo code
     */
    public Coupon getByCode(String code) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, coupon_code, image_url, discount_type, discount_value, " +
                                "min_order_amount, max_uses, used_count, start_date, end_date, " +
                                "is_active, created_at " +
                                "FROM coupons WHERE coupon_code = :code")
                        .bind("code", code)
                        .mapToBean(Coupon.class)
                        .findOne()
                        .orElse(null)
        );
    }

    /**
     * Kiểm tra coupon code đã tồn tại chưa
     */
    public boolean existsByCode(String code) {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(id) > 0 FROM coupons WHERE coupon_code = :code")
                        .bind("code", code)
                        .mapTo(Boolean.class)
                        .one()
        );
    }

    /**
     * Lấy danh sách coupons đang active và còn hạn
     */
    public List<Coupon> getActiveCoupons() {
        return get().withHandle(h ->
                h.createQuery("SELECT id, coupon_code, image_url, discount_type, discount_value, " +
                                "min_order_amount, max_uses, used_count, start_date, end_date, " +
                                "is_active, created_at " +
                                "FROM coupons " +
                                "WHERE is_active = true " +
                                "  AND start_date <= CURRENT_TIMESTAMP " +
                                "  AND end_date >= CURRENT_TIMESTAMP " +
                                "  AND (max_uses = 0 OR used_count < max_uses) " +
                                "ORDER BY end_date ASC")
                        .mapToBean(Coupon.class)
                        .list()
        );
    }

    /**
     * Lấy top N coupons theo số lượng sử dụng (phổ biến nhất)
     * Mặc định lấy 4 coupons
     */
    public List<Coupon> getTopUsedCoupons(int limit) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, coupon_code, image_url, discount_type, discount_value, " +
                                "min_order_amount, max_uses, used_count, start_date, end_date, " +
                                "is_active, created_at " +
                                "FROM coupons " +
                                "WHERE is_active = true " +
                                "  AND start_date <= CURRENT_TIMESTAMP " +
                                "  AND end_date >= CURRENT_TIMESTAMP " +
                                "ORDER BY used_count DESC " +
                                "LIMIT :limit")
                        .bind("limit", limit)
                        .mapToBean(Coupon.class)
                        .list()
        );
    }

    /**
     * Lấy top 4 coupons phổ biến nhất (overload method)
     */
    public List<Coupon> getTopUsedCoupons() {
        return getTopUsedCoupons(4);
    }

    /**
     * Kiểm tra coupon còn hợp lệ không
     */
    public boolean isValidCoupon(String code) {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(id) > 0 FROM coupons " +
                                "WHERE coupon_code = :code " +
                                "  AND is_active = true " +
                                "  AND start_date <= CURRENT_TIMESTAMP " +
                                "  AND end_date >= CURRENT_TIMESTAMP " +
                                "  AND (max_uses = 0 OR used_count < max_uses)")
                        .bind("code", code)
                        .mapTo(Boolean.class)
                        .one()
        );
    }

    /**
     * Insert nhiều coupons (batch)
     */
    public void insertBatch(List<Coupon> coupons) {
        get().useHandle(h -> {
            PreparedBatch batch = h.prepareBatch(
                    "INSERT INTO coupons " +
                            "(coupon_code, image_url, discount_type, discount_value, min_order_amount, " +
                            "max_uses, used_count, start_date, end_date, is_active) " +
                            "VALUES (:couponCode, :imageUrl, :discountType, :discountValue, :minOrderAmount, " +
                            ":maxUses, :usedCount, :startDate, :endDate, :isActive)"
            );
            coupons.forEach(c -> batch.bindBean(c).add());
            batch.execute();
        });
    }

    /**
     * Insert coupon mới
     */
    public void insertCoupon(Coupon coupon) {
        get().useHandle(h -> {
            h.createUpdate("INSERT INTO coupons " +
                            "(coupon_code, image_url, discount_type, discount_value, min_order_amount, " +
                            "max_uses, used_count, start_date, end_date, is_active) " +
                            "VALUES (:couponCode, :imageUrl, :discountType, :discountValue, :minOrderAmount, " +
                            ":maxUses, 0, :startDate, :endDate, :isActive)")
                    .bindBean(coupon)
                    .execute();
        });
    }

    /**
     * Update coupon
     */
    public void updateCoupon(Coupon coupon) {
        get().useHandle(h -> {
            h.createUpdate("UPDATE coupons SET " +
                            "coupon_code = :couponCode, " +
                            "image_url = :imageUrl, " +
                            "discount_type = :discountType, " +
                            "discount_value = :discountValue, " +
                            "min_order_amount = :minOrderAmount, " +
                            "max_uses = :maxUses, " +
                            "start_date = :startDate, " +
                            "end_date = :endDate, " +
                            "is_active = :isActive " +
                            "WHERE id = :id")
                    .bindBean(coupon)
                    .execute();
        });
    }

    /**
     * Cập nhật trạng thái active
     */
    public void updateActiveStatus(int id, boolean isActive) {
        get().useHandle(h -> {
            h.createUpdate("UPDATE coupons SET is_active = :isActive WHERE id = :id")
                    .bind("id", id)
                    .bind("isActive", isActive)
                    .execute();
        });
    }

    /**
     * Tăng số lượt sử dụng
     */
    public void incrementUsedCount(int id) {
        get().useHandle(h -> {
            h.createUpdate("UPDATE coupons SET used_count = used_count + 1 WHERE id = :id")
                    .bind("id", id)
                    .execute();
        });
    }

    /**
     * Xóa coupon
     */
    public void deleteCoupon(int id) {
        get().useHandle(h -> {
            h.createUpdate("DELETE FROM coupons WHERE id = :id")
                    .bind("id", id)
                    .execute();
        });
    }

    /**
     * Đếm tổng số coupons
     */
    public int countAll() {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(id) FROM coupons")
                        .mapTo(Integer.class)
                        .one()
        );
    }

    /**
     * Đếm số coupons đang active
     */
    public int countActive() {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(id) FROM coupons " +
                                "WHERE is_active = true " +
                                "  AND start_date <= CURRENT_TIMESTAMP " +
                                "  AND end_date >= CURRENT_TIMESTAMP")
                        .mapTo(Integer.class)
                        .one()
        );
    }
}