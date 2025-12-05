package com.shop.model;

import java.sql.Timestamp;

/**
 * Model class for admins table
 * Represents an admin user in the system
 */
public class Admin {

    // Fields matching database columns
    private int adminId;
    private String username;
    private String email;
    private String passwordHash;
    private String fullName;
    private String role; // SUPER_ADMIN, ADMIN, STAFF
    private boolean isActive;
    private Timestamp createdAt;

    // Constructors

    /**
     * Default constructor
     */
    public Admin() {
    }

    /**
     * Constructor for creating new admin (without ID)
     */
    public Admin(String username, String email, String passwordHash, String fullName, String role) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.role = role;
        this.isActive = true; // Default active
    }

    /**
     * Full constructor with all fields
     */
    public Admin(int adminId, String username, String email, String passwordHash,
                 String fullName, String role, boolean isActive, Timestamp createdAt) {
        this. adminId = adminId;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.role = role;
        this.isActive = isActive;
        this. createdAt = createdAt;
    }

    // Getters and Setters

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this. adminId = adminId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this. role = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    // Utility methods

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", role='" + role + '\'' +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                '}';
    }
}