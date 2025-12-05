package com.shop.model;

/**
 * Model class for orders table
 * Represents an order in the system
 */
public class Order {

    // Fields matching database columns
    private int orderId;
    private int customerId;
    private String orderCode;
    private String orderStatus;
    private double totalAmount;

    // Constructors

    /**
     * Default constructor
     */
    public Order() {
    }

    /**
     * Constructor for creating new order (without ID)
     */
    public Order(int customerId, String orderCode, String orderStatus, double totalAmount) {
        this.customerId = customerId;
        this.orderCode = orderCode;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
    }

    /**
     * Full constructor with all fields
     */
    public Order(int orderId, int customerId, String orderCode, String orderStatus, double totalAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderCode = orderCode;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    // toString method

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
