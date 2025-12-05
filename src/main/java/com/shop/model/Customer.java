package com.shop. model;

import java.sql.Timestamp;

/**
 * Model class for customers table
 * Represents a customer in the system
 */
public class Customer {

    // Fields matching database columns
    private int customerId;
    private String email;
    private String passwordHash;
    private String fullName;
    private String phone;
    private String address;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructors

    /**
     * Default constructor
     */
    public Customer() {
    }

    /**
     * Constructor for new customer registration (without ID)
     */
    public Customer(String email, String passwordHash, String fullName, String phone, String address) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
    }

    /**
     * Full constructor with all fields
     */
    public Customer(int customerId, String email, String passwordHash, String fullName,
                    String phone, String address, Timestamp createdAt, Timestamp updatedAt) {
        this.customerId = customerId;
        this.email = email;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this. phone = phone;
        this. address = address;
        this. createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this. customerId = customerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this. address = address;
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
        this.updatedAt = updatedAt;
    }

    // Utility methods

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}