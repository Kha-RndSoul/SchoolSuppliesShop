package com.shop.dao.order;

import com.shop.dao.support.BaseDao;
import com.shop.model.Coupon;
import org.jdbi.v3.core.statement.PreparedBatch;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;

public class CouponDAO extends BaseDao {

    static Map<Integer, Coupon> data = new HashMap<>();
    static {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        data.put(1, new Coupon(1, "WELCOME10", "PERCENT", new BigDecimal("10"), new BigDecimal("0"), 100, 0, now, null, true, now));
        data.put(2, new Coupon(2, "SUMMER50K", "FIXED", new BigDecimal("50000"), new BigDecimal("200000"), 50, 0, now, null, true, now));
        data.put(3, new Coupon(3, "FREESHIP", "FIXED", new BigDecimal("30000"), new BigDecimal("100000"), 200, 0, now, null, true, now));
    }

    public List<Coupon> getListCoupon() {
        return new ArrayList<>(data.values());
    }

    public Coupon getCoupon(int id) {
        return data.get(id);
    }

    public List<Coupon> getList() {
        return get().withHandle(h ->
                h.createQuery("SELECT id, code, description, discount_type, discount_value, min_order_amount, max_discount_amount, max_uses, used_count, start_date, expiry_date, is_active, created_at, updated_at FROM coupons ORDER BY created_at DESC")
                        .mapToBean(Coupon.class)
                        .list()
        );
    }

    public Coupon getCouponById(int id) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, code, description, discount_type, discount_value, min_order_amount, max_discount_amount, max_uses, used_count, start_date, expiry_date, is_active, created_at, updated_at FROM coupons WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(Coupon.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public Coupon getByCode(String code) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, code, description, discount_type, discount_value, min_order_amount, max_discount_amount, max_uses, used_count, start_date, expiry_date, is_active, created_at, updated_at FROM coupons WHERE code = :code")
                        .bind("code", code)
                        .mapToBean(Coupon.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public boolean existsByCode(String code) {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(id) > 0 FROM coupons WHERE code = :code")
                        .bind("code", code)
                        .mapTo(Boolean.class)
                        .one()
        );
    }

    public List<Coupon> getActiveCoupons() {
        return get().withHandle(h ->
                h.createQuery("SELECT id, code, description, discount_type, discount_value, min_order_amount, max_discount_amount, max_uses, used_count, start_date, expiry_date, is_active, created_at, updated_at FROM coupons WHERE is_active = true AND expiry_date >= CURDATE() AND (max_uses IS NULL OR used_count < max_uses) ORDER BY expiry_date ASC")
                        .mapToBean(Coupon.class)
                        .list()
        );
    }

    public boolean isValidCoupon(String code) {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(id) > 0 FROM coupons WHERE code = :code AND is_active = true AND start_date <= CURDATE() AND expiry_date >= CURDATE() AND (max_uses IS NULL OR used_count < max_uses)")
                        .bind("code", code)
                        .mapTo(Boolean.class)
                        .one()
        );
    }

    public void insert(List<Coupon> coupons) {
        get().useHandle(h -> {
            PreparedBatch batch = h.prepareBatch(
                    "INSERT INTO coupons (id, code, description, discount_type, discount_value, min_order_amount, max_discount_amount, max_uses, used_count, start_date, expiry_date, is_active, created_at) VALUES (:id, :code, :description, :discountType, :discountValue, :minOrderAmount, :maxDiscountAmount, :maxUses, 0, :startDate, :expiryDate, :isActive, NOW())"
            );
            coupons.forEach(c -> batch.bindBean(c).add());
            batch.execute();
        });
    }

    public void insertCoupon(Coupon coupon) {
        get().useHandle(h -> {
            h.createUpdate("INSERT INTO coupons (code, description, discount_type, discount_value, min_order_amount, max_discount_amount, max_uses, used_count, start_date, expiry_date, is_active, created_at) VALUES (:code, :description, :discountType, :discountValue, :minOrderAmount, :maxDiscountAmount, :maxUses, 0, :startDate, :expiryDate, :isActive, NOW())")
                    .bindBean(coupon)
                    .execute();
        });
    }

    public void updateCoupon(Coupon coupon) {
        get().useHandle(h -> {
            h.createUpdate("UPDATE coupons SET code = :code, description = :description, discount_type = :discountType, discount_value = :discountValue, min_order_amount = :minOrderAmount, max_discount_amount = :maxDiscountAmount, max_uses = :maxUses, start_date = :startDate, expiry_date = :expiryDate, is_active = :isActive, updated_at = NOW() WHERE id = :id")
                    .bindBean(coupon)
                    .execute();
        });
    }

    public void updateActiveStatus(int id, boolean isActive) {
        get().useHandle(h -> {
            h.createUpdate("UPDATE coupons SET is_active = :isActive, updated_at = NOW() WHERE id = :id")
                    .bind("id", id)
                    .bind("isActive", isActive)
                    .execute();
        });
    }

    public void incrementUsedCount(int id) {
        get().useHandle(h -> {
            h.createUpdate("UPDATE coupons SET used_count = used_count + 1, updated_at = NOW() WHERE id = :id")
                    .bind("id", id)
                    .execute();
        });
    }

    public void deleteCoupon(int id) {
        get().useHandle(h -> {
            h.createUpdate("DELETE FROM coupons WHERE id = :id")
                    .bind("id", id)
                    .execute();
        });
    }

    public int countAll() {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(id) FROM coupons")
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public int countActive() {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(id) FROM coupons WHERE is_active = true AND expiry_date >= CURDATE()")
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public static void main(String[] args) {
        CouponDAO dao = new CouponDAO();
        System.out.println("=== INSERT DUMMY DATA ===");
        List<Coupon> coupons = dao.getListCoupon();
        dao.insert(coupons);
        System.out.println("Inserted " + coupons.size() + " coupons");

        System.out.println("\n=== GET ACTIVE COUPONS ===");
        dao.getActiveCoupons().forEach(System.out::println);
    }
}