package com.shop.model;

/**
 * Model class for coupons table
 * Represents a discount coupon in the system
 */
public class Coupon {

    // Fields matching database columns
    private int couponId;
    private String couponCode;
    private String discountType;
    private double discountValue;

    // Constructors

    /**
     * Default constructor
     */
    public Coupon() {
    }

    /**
     * Constructor for creating new coupon (without ID)
     */
    public Coupon(String couponCode, String discountType, double discountValue) {
        this.couponCode = couponCode;
        this.discountType = discountType;
        this.discountValue = discountValue;
    }

    /**
     * Full constructor with all fields
     */
    public Coupon(int couponId, String couponCode, String discountType, double discountValue) {
        this.couponId = couponId;
        this.couponCode = couponCode;
        this.discountType = discountType;
        this.discountValue = discountValue;
    }

    // Getters and Setters

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
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

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    // toString method

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
