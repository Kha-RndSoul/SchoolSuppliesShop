package com.shop.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderCoupon {

    // Fields matching database columns
    private int orderCouponId;               // ✅ Đổi từ Long sang int
    private int orderId;                     // ✅ Đổi từ Long sang int
    private int couponId;                    // ✅ Đổi từ Long sang int
    private BigDecimal discountAmount;
    private Timestamp createdAt;

    // Constructors

    public OrderCoupon() {
    }

    // Constructor for creating new order coupon (without ID and timestamp)
    public OrderCoupon(int orderId, int couponId, BigDecimal discountAmount) {
        this.orderId = orderId;
        this.couponId = couponId;
        this.discountAmount = discountAmount;
    }

    // Full constructor
    public OrderCoupon(int orderCouponId, int orderId, int couponId,
                       BigDecimal discountAmount, Timestamp createdAt) {
        this.orderCouponId = orderCouponId;
        this.orderId = orderId;
        this.couponId = couponId;
        this.discountAmount = discountAmount;
        this.createdAt = createdAt;
    }

    // Getters and Setters

    public int getOrderCouponId() {
        return orderCouponId;
    }

    public void setOrderCouponId(int orderCouponId) {
        this.orderCouponId = orderCouponId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "OrderCoupon{" +
                "orderCouponId=" + orderCouponId +
                ", orderId=" + orderId +
                ", couponId=" + couponId +
                ", discountAmount=" + discountAmount +
                ", createdAt=" + createdAt +
                '}';
    }
}