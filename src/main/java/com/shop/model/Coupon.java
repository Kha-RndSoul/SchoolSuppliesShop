package com.shop.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Coupon {
    private int id;
    private String couponCode;
    private String imageUrl;
    private String discountType;
    private BigDecimal discountValue;
    private BigDecimal minOrderAmount;
    private int maxUses;
    private int usedCount;
    private Timestamp startDate;
    private Timestamp endDate;
    private boolean isActive;
    private Timestamp createdAt;

    // Constructor mặc định
    public Coupon() {}

    // Constructor cho tạo coupon mới (không có ID)
    public Coupon(String couponCode, String imageUrl, String discountType,
                  BigDecimal discountValue, BigDecimal minOrderAmount, int maxUses,
                  Timestamp startDate, Timestamp endDate, boolean isActive) {
        this.couponCode = couponCode;
        this.imageUrl = imageUrl;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.minOrderAmount = minOrderAmount;
        this.maxUses = maxUses;
        this.usedCount = 0;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
    }

    // Constructor đầy đủ (có ID)
    public Coupon(int id, String couponCode, String imageUrl, String discountType,
                  BigDecimal discountValue, BigDecimal minOrderAmount, int maxUses,
                  int usedCount, Timestamp startDate, Timestamp endDate,
                  boolean isActive, Timestamp createdAt) {
        this.id = id;
        this.couponCode = couponCode;
        this.imageUrl = imageUrl;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.minOrderAmount = minOrderAmount;
        this.maxUses = maxUses;
        this.usedCount = usedCount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public BigDecimal getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(BigDecimal minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public int getMaxUses() {
        return maxUses;
    }

    public void setMaxUses(int maxUses) {
        this.maxUses = maxUses;
    }

    public int getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(int usedCount) {
        this.usedCount = usedCount;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        this.isActive = active;
    }
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", couponCode='" + couponCode + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", discountType='" + discountType + '\'' +
                ", discountValue=" + discountValue +
                ", minOrderAmount=" + minOrderAmount +
                ", maxUses=" + maxUses +
                ", usedCount=" + usedCount +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                '}';
    }
}