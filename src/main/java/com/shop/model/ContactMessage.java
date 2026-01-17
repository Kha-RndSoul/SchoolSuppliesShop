package com.shop.model;

import java.sql.Timestamp;

/**
 * Model class for contact_messages table
 * Represents a contact message from customer or guest
 */
public class ContactMessage {

    // Fields matching database columns
    private int id;
    private int customerId; // Nullable - guest can send message
    private String fullName;
    private String email;
    private String phone;
    private String subject;
    private String message;
    private String status; // NEW, READ, REPLIED
    private String adminReply;
    private String ipAddress;
    private Timestamp createdAt;
    private Timestamp repliedAt;

    // Constructors

    /**
     * Default constructor
     */
    public ContactMessage() {
    }

    /**
     * Constructor for new message from form (without ID)
     */
    public ContactMessage(int customerId, String fullName, String email, String phone,
                          String subject, String message, String ipAddress) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.subject = subject;
        this.message = message;
        this.ipAddress = ipAddress;
        this.status = "NEW"; // Default status
    }

    /**
     * Full constructor with all fields
     */
    public ContactMessage(int id, int customerId, String fullName, String email,
                          String phone, String subject, String message, String status,
                          String adminReply, String ipAddress, Timestamp createdAt, Timestamp repliedAt) {
        this.id = id;
        this.customerId = customerId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.subject = subject;
        this.message = message;
        this.status = status;
        this.adminReply = adminReply;
        this.ipAddress = ipAddress;
        this.createdAt = createdAt;
        this.repliedAt = repliedAt;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdminReply() {
        return adminReply;
    }

    public void setAdminReply(String adminReply) {
        this.adminReply = adminReply;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getRepliedAt() {
        return repliedAt;
    }

    public void setRepliedAt(Timestamp repliedAt) {
        this.repliedAt = repliedAt;
    }


    // Utility methods

    @Override
    public String toString() {
        return "ContactMessage{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", subject='" + subject + '\'' +
                ", status='" + status + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", createdAt=" + createdAt +
                ", repliedAt=" + repliedAt +
                '}';
    }
}