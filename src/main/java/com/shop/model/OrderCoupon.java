package com.shop.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Model class for order_coupons table
 * Represents the relationship between orders and applied coupons
 */
public class OrderCoupon {

    // Fields matching database columns
    private Long orderCouponId;
    private Long orderId;
    private Long couponId;
    private String couponCode;
    private BigDecimal discountAmount;
    private Timestamp appliedAt;

    // Constructors

    /**
     * Default constructor
     */
    public OrderCoupon() {
    }

    /**
     * Constructor for creating new order coupon (without ID)
     */
    public OrderCoupon(Long orderId, Long couponId, String couponCode, BigDecimal discountAmount) {
        this.orderId = orderId;
        this.couponId = couponId;
        this.couponCode = couponCode;
        this. discountAmount = discountAmount;
    }

    /**
     * Full constructor with all fields
     */
    public OrderCoupon(Long orderCouponId, Long orderId, Long couponId,
                       String couponCode, BigDecimal discountAmount, Timestamp appliedAt) {
        this.orderCouponId = orderCouponId;
        this.orderId = orderId;
        this.couponId = couponId;
        this.couponCode = couponCode;
        this.discountAmount = discountAmount;
        this. appliedAt = appliedAt;
    }

    // Getters and Setters

    public Long getOrderCouponId() {
        return orderCouponId;
    }

    public void setOrderCouponId(Long orderCouponId) {
        this.orderCouponId = orderCouponId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

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

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Timestamp getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(Timestamp appliedAt) {
        this. appliedAt = appliedAt;
    }

    @Override
    public String toString() {
        return "OrderCoupon{" +
                "orderCouponId=" + orderCouponId +
                ", orderId=" + orderId +
                ", couponId=" + couponId +
                ", couponCode='" + couponCode + '\'' +
                ", discountAmount=" + discountAmount +
                ", appliedAt=" + appliedAt +
                '}';
    }
}