package com.shop.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderCoupon {

    private int id;
    private int orderId;
    private int couponId;
    private BigDecimal discountAmount;
    private Timestamp createdAt;

    public OrderCoupon() { }


    public OrderCoupon(int orderId, int couponId, BigDecimal discountAmount) {
        this.orderId = orderId;
        this.couponId = couponId;
        this.discountAmount = discountAmount;
    }


    public OrderCoupon(int id, int orderId, int couponId,
                       BigDecimal discountAmount, Timestamp createdAt) {
        this.id = id;
        this.orderId = orderId;
        this.couponId = couponId;
        this.discountAmount = discountAmount;
        this.createdAt = createdAt;
    }

    // Getters and Setters

    public int getId() {  // Đổi getter/setter thành id
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                "id=" + id +
                ", orderId=" + orderId +
                ", couponId=" + couponId +
                ", discountAmount=" + discountAmount +
                ", createdAt=" + createdAt +
                '}';
    }
}