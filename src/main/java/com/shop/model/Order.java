package com.shop.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Order {
    private int id;
    private int customerId;
    private String orderCode;
    private String orderStatus;
    private String paymentMethod;
    private String paymentStatus;
    private BigDecimal totalAmount;
    private String shippingName;
    private String shippingPhone;
    private String shippingAddress;
    private String note;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Order() {}

    private Order(Builder builder) {
        this.id = builder.id;
        this.customerId = builder.customerId;
        this.orderCode = builder.orderCode;
        this.orderStatus = builder.orderStatus;
        this.paymentMethod = builder.paymentMethod;
        this.paymentStatus = builder.paymentStatus;
        this.totalAmount = builder.totalAmount;
        this.shippingName = builder.shippingName;
        this.shippingPhone = builder.shippingPhone;
        this.shippingAddress = builder.shippingAddress;
        this.note = builder.note;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    public static class Builder {
        // Required fields
        private int customerId;
        private String orderCode;
        private String orderStatus;
        private String paymentMethod;
        private String paymentStatus;
        private BigDecimal totalAmount;
        private String shippingName;
        private String shippingPhone;
        private String shippingAddress;

        // Optional fields
        private int id;
        private String note;
        private Timestamp createdAt;
        private Timestamp updatedAt;

        public Builder(int customerId, String orderCode, String orderStatus,
                       String paymentMethod, String paymentStatus,
                       BigDecimal totalAmount, String shippingName,
                       String shippingPhone, String shippingAddress) {
            this.customerId = customerId;
            this.orderCode = orderCode;
            this.orderStatus = orderStatus;
            this.paymentMethod = paymentMethod;
            this.paymentStatus = paymentStatus;
            this.totalAmount = totalAmount;
            this.shippingName = shippingName;
            this.shippingPhone = shippingPhone;
            this.shippingAddress = shippingAddress;
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder note(String note) {
            this.note = note;
            return this;
        }

        public Builder createdAt(Timestamp createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(Timestamp updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public String getOrderCode() { return orderCode; }
    public void setOrderCode(String orderCode) { this.orderCode = orderCode; }

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public String getShippingName() { return shippingName; }
    public void setShippingName(String shippingName) { this.shippingName = shippingName; }

    public String getShippingPhone() { return shippingPhone; }
    public void setShippingPhone(String shippingPhone) { this.shippingPhone = shippingPhone; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", orderCode='" + orderCode + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", totalAmount=" + totalAmount +
                ", shippingName='" + shippingName + '\'' +
                ", shippingPhone='" + shippingPhone + '\'' +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", note='" + note + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}