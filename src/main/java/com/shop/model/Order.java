package com.shop.model;

import java.math.BigDecimal;

public class Order {
    private Long orderId;
    private Long customerId;
    private String orderCode;
    private String orderStatus;
    private BigDecimal totalAmount;

    // Default constructor
    public Order() {}

    // Parameterized constructor
    public Order(Long orderId, Long customerId, String orderCode, String orderStatus, BigDecimal totalAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderCode = orderCode;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customerId=" + customerId +
                ", orderCode='" + orderCode + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}