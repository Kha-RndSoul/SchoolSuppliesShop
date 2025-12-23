package com.shop.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class PaymentTransaction {

    // Fields matching database columns
    private int transactionId;               // ✅ Đổi từ Long sang int
    private int orderId;                     // ✅ Đổi từ Long sang int
    private String paymentMethod;            // COD, BANK_TRANSFER, MOMO, VNPAY, etc.
    private String paymentStatus;            // ✅ Đổi từ 'status' thành 'paymentStatus'
    private BigDecimal amount;
    private String transactionNote;          // ✅ Đổi từ 'transactionCode' (không có trong ERD)
    private Timestamp paidAt;                // ✅ Thêm field mới
    private Timestamp createdAt;

    // Constructors

    public PaymentTransaction() {
    }

    // Constructor for creating new transaction (without ID and timestamps)
    public PaymentTransaction(int orderId, String paymentMethod, String paymentStatus,
                              BigDecimal amount, String transactionNote) {
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.amount = amount;
        this.transactionNote = transactionNote;
    }

    // Full constructor
    public PaymentTransaction(int transactionId, int orderId, String paymentMethod,
                              String paymentStatus, BigDecimal amount, String transactionNote,
                              Timestamp paidAt, Timestamp createdAt) {
        this.transactionId = transactionId;
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.amount = amount;
        this.transactionNote = transactionNote;
        this.paidAt = paidAt;
        this.createdAt = createdAt;
    }

    // Getters and Setters

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransactionNote() {
        return transactionNote;
    }

    public void setTransactionNote(String transactionNote) {
        this.transactionNote = transactionNote;
    }

    public Timestamp getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(Timestamp paidAt) {
        this.paidAt = paidAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "PaymentTransaction{" +
                "transactionId=" + transactionId +
                ", orderId=" + orderId +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", amount=" + amount +
                ", transactionNote='" + transactionNote + '\'' +
                ", paidAt=" + paidAt +
                ", createdAt=" + createdAt +
                '}';
    }
}