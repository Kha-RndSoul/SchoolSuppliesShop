package com.shop.model;

import java.sql.Timestamp;

public class Brand {
    private int brandId;
    private String brandName;
    private String logoUrl;
    private Timestamp createdAt;

    // Constructor rỗng
    public Brand() {
    }

    // Constructor đầy đủ
    public Brand(int brandId, String brandName, String logoUrl) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.logoUrl = logoUrl;
    }

    // Constructor với timestamp
    public Brand(int brandId, String brandName, String logoUrl, Timestamp createdAt) {
        this.brandId = brandId;
        this.brandName = brandName;
        this. logoUrl = logoUrl;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
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
                "brandId=" + brandId +
                ", brandName='" + brandName + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}