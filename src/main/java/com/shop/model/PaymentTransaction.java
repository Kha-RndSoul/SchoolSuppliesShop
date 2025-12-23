package com.shop.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class PaymentTransaction {

    private int id;
    private int orderId;
    private String paymentMethod;
    private String paymentStatus;
    private BigDecimal amount;
    private String transactionNote;
    private Timestamp paidAt;
    private Timestamp createdAt;



    public PaymentTransaction() {
    }


    public PaymentTransaction(int orderId, String paymentMethod, String paymentStatus,
                              BigDecimal amount, String transactionNote) {
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.amount = amount;
        this.transactionNote = transactionNote;
    }


    public PaymentTransaction(int id, int orderId, String paymentMethod,
                              String paymentStatus, BigDecimal amount, String transactionNote,
                              Timestamp paidAt, Timestamp createdAt) {
        this.id = id;
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.amount = amount;
        this.transactionNote = transactionNote;
        this.paidAt = paidAt;
        this.createdAt = createdAt;
    }

    // Getters and Setters

    public int getId() {
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
                "id=" + id +
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