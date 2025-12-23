package com.shop.model;

import java.sql.Timestamp;

public class Brand {
    private int id;
    private String brandName;
    private String logoUrl;
    private Timestamp createdAt;

    // Constructor rỗng
    public Brand() {
    }

    // Constructor đầy đủ
    public Brand(int id, String brandName, String logoUrl) {
        this.id = id;
        this.brandName = brandName;
        this.logoUrl = logoUrl;
    }

    // Constructor với timestamp
    public Brand(int id, String brandName, String logoUrl, Timestamp createdAt) {
        this.id = id;
        this.brandName = brandName;
        this.logoUrl = logoUrl;
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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
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
                ", logoUrl='" + logoUrl + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}