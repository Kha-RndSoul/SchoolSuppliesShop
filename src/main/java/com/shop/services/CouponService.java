package com.shop.services;

import com.shop.dao.order.CouponDAO;
import com.shop.model.Coupon;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.List;

/**
 * Service class for Coupon business logic
 */
public class CouponService {

    private final CouponDAO couponDAO;

    /**
     * Constructor
     */
    public CouponService() {
        this.couponDAO = new CouponDAO();
    }

    /**
     * Lấy danh sách tất cả coupons
     */
    public List<Coupon> getAllCoupons() {
        return couponDAO.getList();
    }

    /**
     * Lấy coupon theo ID
     */
    public Coupon getCouponById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID coupon không hợp lệ");
        }
        return couponDAO.getCouponById(id);
    }

    /**
     * Lấy coupon theo code
     */
    public Coupon getCouponByCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã coupon không được rỗng");
        }
        return couponDAO.getByCode(code.trim().toUpperCase());
    }

    /**
     * Lấy danh sách coupons đang active
     */
    public List<Coupon> getActiveCoupons() {
        return couponDAO.getActiveCoupons();
    }

    /**
     * Lấy top N coupons phổ biến nhất
     */
    public List<Coupon> getTopUsedCoupons(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
        }
        return couponDAO.getTopUsedCoupons(limit);
    }

    /**
     * Lấy top 4 coupons phổ biến nhất (mặc định)
     */
    public List<Coupon> getTopUsedCoupons() {
        return couponDAO.getTopUsedCoupons(4);
    }

    /**
     * Kiểm tra coupon có hợp lệ không
     */
    public boolean isValidCoupon(String code) {
        if (code == null || code.trim().isEmpty()) {
            return false;
        }
        return couponDAO.isValidCoupon(code.trim().toUpperCase());
    }

    /**
     * Validate và apply coupon cho đơn hàng
     */
    public Coupon validateAndApplyCoupon(String code, BigDecimal orderAmount) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã coupon không được rỗng");
        }
        if (orderAmount == null || orderAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Giá trị đơn hàng không hợp lệ");
        }

        // Get coupon
        Coupon coupon = couponDAO.getByCode(code.trim().toUpperCase());
        if (coupon == null) {
            throw new IllegalArgumentException("Mã coupon không tồn tại");
        }

        // Check active
        if (!coupon.isActive()) {
            throw new IllegalArgumentException("Mã coupon đã bị vô hiệu hóa");
        }

        // Check date range
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (now.before(coupon.getStartDate())) {
            throw new IllegalArgumentException("Mã coupon chưa đến ngày áp dụng");
        }
        if (now.after(coupon.getEndDate())) {
            throw new IllegalArgumentException("Mã coupon đã hết hạn");
        }

        // Check min order amount
        if (orderAmount.compareTo(coupon.getMinOrderAmount()) < 0) {
            throw new IllegalArgumentException(
                    "Đơn hàng tối thiểu " + coupon.getMinOrderAmount() + "đ để sử dụng mã này"
            );
        }

        // Check usage limit
        if (coupon.getMaxUses() > 0 && coupon.getUsedCount() >= coupon.getMaxUses()) {
            throw new IllegalArgumentException("Mã coupon đã hết lượt sử dụng");
        }

        return coupon;
    }

    /**
     * Tính discount amount từ coupon
     */
    public BigDecimal calculateDiscount(Coupon coupon, BigDecimal orderAmount) {
        if (coupon == null || orderAmount == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal discount;

        if ("PERCENTAGE".equals(coupon.getDiscountType())) {
            // Percentage discount: orderAmount * (discountValue / 100)
            discount = orderAmount.multiply(coupon.getDiscountValue())
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        } else if ("FIXED_AMOUNT".equals(coupon.getDiscountType())) {
            // Fixed amount discount
            discount = coupon.getDiscountValue();
        } else {
            throw new IllegalArgumentException("Loại discount không hợp lệ: " + coupon.getDiscountType());
        }

        // Discount không được vượt quá order amount
        if (discount.compareTo(orderAmount) > 0) {
            discount = orderAmount;
        }

        return discount;
    }

    /**
     * Tạo coupon mới
     */
    public void createCoupon(Coupon coupon) {
        // Validation
        if (coupon == null) {
            throw new IllegalArgumentException("Thông tin coupon không được null");
        }
        if (coupon.getCouponCode() == null || coupon.getCouponCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã coupon không được rỗng");
        }
        if (coupon.getDiscountType() == null || coupon.getDiscountType().trim().isEmpty()) {
            throw new IllegalArgumentException("Loại giảm giá không được rỗng");
        }
        if (coupon.getDiscountValue() == null || coupon.getDiscountValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Giá trị giảm giá phải lớn hơn 0");
        }
        if (coupon.getStartDate() == null || coupon.getEndDate() == null) {
            throw new IllegalArgumentException("Ngày bắt đầu và kết thúc không được rỗng");
        }
        if (coupon.getEndDate().before(coupon.getStartDate())) {
            throw new IllegalArgumentException("Ngày kết thúc phải sau ngày bắt đầu");
        }

        // Check duplicate code
        if (couponDAO.existsByCode(coupon.getCouponCode().toUpperCase())) {
            throw new IllegalArgumentException("Mã coupon đã tồn tại");
        }

        // Normalize coupon code to uppercase
        coupon.setCouponCode(coupon.getCouponCode().toUpperCase());

        // Validate imageUrl (optional)
        if (coupon.getImageUrl() != null && !coupon.getImageUrl().trim().isEmpty()) {
            if (!isValidImageUrl(coupon.getImageUrl())) {
                throw new IllegalArgumentException("URL ảnh không hợp lệ");
            }
        }

        couponDAO.insertCoupon(coupon);
    }

    /**
     * Cập nhật coupon
     */
    public void updateCoupon(Coupon coupon) {
        if (coupon == null) {
            throw new IllegalArgumentException("Thông tin coupon không được null");
        }
        if (coupon.getId() <= 0) {
            throw new IllegalArgumentException("ID coupon không hợp lệ");
        }

        Coupon existingCoupon = couponDAO.getCouponById(coupon.getId());
        if (existingCoupon == null) {
            throw new IllegalArgumentException("Không tìm thấy coupon với ID: " + coupon.getId());
        }

        // Validation tương tự createCoupon
        if (coupon.getCouponCode() == null || coupon.getCouponCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã coupon không được rỗng");
        }
        if (coupon.getDiscountValue() == null || coupon.getDiscountValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Giá trị giảm giá phải lớn hơn 0");
        }

        // Validate imageUrl
        if (coupon.getImageUrl() != null && !coupon.getImageUrl().trim().isEmpty()) {
            if (!isValidImageUrl(coupon.getImageUrl())) {
                throw new IllegalArgumentException("URL ảnh không hợp lệ");
            }
        }

        couponDAO.updateCoupon(coupon);
    }

    /**
     * Xóa coupon
     */
    public void deleteCoupon(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID coupon không hợp lệ");
        }

        Coupon existingCoupon = couponDAO.getCouponById(id);
        if (existingCoupon == null) {
            throw new IllegalArgumentException("Không tìm thấy coupon với ID: " + id);
        }

        couponDAO.deleteCoupon(id);
    }

    /**
     * Kích hoạt/Vô hiệu hóa coupon
     */
    public void toggleCouponStatus(int id, boolean isActive) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID coupon không hợp lệ");
        }

        Coupon existingCoupon = couponDAO.getCouponById(id);
        if (existingCoupon == null) {
            throw new IllegalArgumentException("Không tìm thấy coupon với ID: " + id);
        }

        couponDAO.updateActiveStatus(id, isActive);
    }

    /**
     * Sử dụng coupon (tăng used_count)
     */
    public void useCoupon(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID coupon không hợp lệ");
        }

        Coupon coupon = couponDAO.getCouponById(id);
        if (coupon == null) {
            throw new IllegalArgumentException("Không tìm thấy coupon với ID: " + id);
        }

        // Check if can still be used
        if (coupon.getMaxUses() > 0 && coupon.getUsedCount() >= coupon.getMaxUses()) {
            throw new IllegalArgumentException("Coupon đã hết lượt sử dụng");
        }

        couponDAO.incrementUsedCount(id);
    }

    /**
     * Đếm tổng số coupons
     */
    public int getTotalCoupons() {
        return couponDAO.countAll();
    }

    /**
     * Đếm số coupons đang active
     */
    public int getActiveCouponsCount() {
        return couponDAO.countActive();
    }

    /**
     * Validate image URL
     * Chấp nhận cả relative path và absolute URL
     */
    private boolean isValidImageUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }

        String lowerUrl = url.toLowerCase().trim();

        // Check 1: Relative path (bắt đầu bằng /)
        if (lowerUrl.startsWith("/")) {
            // Relative path: /assets/images/coupon.jpg
            return lowerUrl.matches("^/.*\\.(jpg|jpeg|png|gif|webp|svg|bmp)$");
        }

        // Check 2: Absolute URL (http/https)
        if (lowerUrl.startsWith("http://") || lowerUrl.startsWith("https://")) {
            // Absolute URL: https://example.com/coupon.jpg
            return lowerUrl.matches("^https?://.*\\.(jpg|jpeg|png|gif|webp|svg|bmp)$");
        }

        // Check 3: Relative path không bắt đầu bằng / (assets/images/coupon.jpg)
        if (lowerUrl.matches("^[a-z0-9_\\-/]+\\.(jpg|jpeg|png|gif|webp|svg|bmp)$")) {
            return true;
        }

        return false;
    }
}