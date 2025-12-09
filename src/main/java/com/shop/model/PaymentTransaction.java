package com. shop.model;

import java.math.BigDecimal;
import java.sql.Timestamp;


public class PaymentTransaction {

    // Fields matching database columns
    private Long transactionId;
    private Long orderId;
    private String paymentMethod; // COD, BANK_TRANSFER, MOMO, VNPAY, etc.
    private String transactionCode;
    private BigDecimal amount;
    private String status; // PENDING, SUCCESS, FAILED, REFUNDED
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructors


    public PaymentTransaction() {
    }


    public PaymentTransaction(Long orderId, String paymentMethod, String transactionCode,
                              BigDecimal amount, String status) {
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this. transactionCode = transactionCode;
        this.amount = amount;
        this. status = status;
    }

    public PaymentTransaction(Long transactionId, Long orderId, String paymentMethod,
                              String transactionCode, BigDecimal amount, String status,
                              Timestamp createdAt, Timestamp updatedAt) {
        this.transactionId = transactionId;
        this. orderId = orderId;
        this. paymentMethod = paymentMethod;
        this.transactionCode = transactionCode;
        this.amount = amount;
        this.status = status;
        this. createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this. status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this. updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "PaymentTransaction{" +
                "transactionId=" + transactionId +
                ", orderId=" + orderId +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", transactionCode='" + transactionCode + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}