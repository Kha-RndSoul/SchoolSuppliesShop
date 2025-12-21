package com.shop.model;

import java.math.BigDecimal;
import java.sql.Timestamp;


public class OrderCoupon {

    // Fields matching database columns
    private Long orderCouponId;
    private Long orderId;
    private Long couponId;
    private String couponCode;
    private double discountAmount;
    private Timestamp appliedAt;

    // Constructors


    public OrderCoupon() {
    }


    public OrderCoupon(Long orderId, Long couponId, String couponCode, double discountAmount) {
        this.orderId = orderId;
        this.couponId = couponId;
        this.couponCode = couponCode;
        this. discountAmount = discountAmount;
    }


    public OrderCoupon(Long orderCouponId, Long orderId, Long couponId,
                       String couponCode, double discountAmount, Timestamp appliedAt) {
        this.orderCouponId = orderCouponId;
        this.orderId = orderId;
        this.couponId = couponId;
        this.couponCode = couponCode;
        this.discountAmount = discountAmount;
        this. appliedAt = appliedAt;
    }


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

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
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