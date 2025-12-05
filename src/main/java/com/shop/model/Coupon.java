package com.shop.model;

public class Coupon {
    private Long couponId;
    private String couponCode;
    private String discountType;
    private Double discountValue;

    // Default constructor
    public Coupon() {}

    // Parameterized constructor
    public Coupon(Long couponId, String couponCode, String discountType, Double discountValue) {
        this.couponId = couponId;
        this.couponCode = couponCode;
        this.discountType = discountType;
        this.discountValue = discountValue;
    }

    // Getters and Setters
    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "couponId=" + couponId +
                ", couponCode='" + couponCode + '\'' +
                ", discountType='" + discountType + '\'' +
                ", discountValue=" + discountValue +
                '}';
    }
}