package com.shop.model;

import java.sql.Timestamp;

public class Brand {
    private int id;
    private String brandName;
    private Timestamp createdAt;

    // Constructor rỗng
    public Brand() {
    }

    // Constructor đầy đủ
    public Brand(int id, String brandName) {
        this.id = id;
        this.brandName = brandName;
    }

    // Constructor với timestamp
    public Brand(int id, String brandName, Timestamp createdAt) {
        this.id = id;
        this.brandName = brandName;
        this.createdAt = createdAt;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", brandName='" + brandName + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}