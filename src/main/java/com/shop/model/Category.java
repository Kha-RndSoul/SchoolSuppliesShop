package com.shop.model;

import java.sql. Timestamp;

public class Category {
    private int categoryId;
    private String categoryName;
    private String imageUrl;
    private Timestamp createdAt;

    // Constructor rỗng
    public Category() {
    }
    // Constructor đầy đủ
    public Category(int categoryId, String categoryName, String imageUrl) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.imageUrl = imageUrl;
    }

    // Constructor với timestamp
    public Category(int categoryId, String categoryName, String imageUrl, Timestamp createdAt) {
        this.categoryId = categoryId;
        this. categoryName = categoryName;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }
    // Getters and Setters
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}