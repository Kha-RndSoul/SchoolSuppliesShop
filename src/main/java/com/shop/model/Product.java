package com.shop.model;

import java.sql.Timestamp;

public class Product {
    private int productId;
    private String productName;
    private String description;
    private int categoryId;
    private int brandId;
    private double price;
    private double salePrice;
    private int stockQuantity;
    private int soldCount;
    private boolean isActive;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // private String imageUrl;
    public Product() {
    }
    // Constructor CƠ BẢN
    public Product(int productId, String productName, String description, int categoryId,
                   int brandId, double price, double salePrice, int stockQuantity,
                   int soldCount, boolean isActive) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.price = price;
        this.salePrice = salePrice;
        this.stockQuantity = stockQuantity;
        this.soldCount = soldCount;
        this.isActive = isActive;
    }

    // Getters and Setters (BỎ getImageUrl/setImageUrl)
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getSoldCount() {
        return soldCount;
    }

    public void setSoldCount(int soldCount) {
        this.soldCount = soldCount;
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

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", categoryId=" + categoryId +
                ", brandId=" + brandId +
                ", price=" + price +
                ", salePrice=" + salePrice +
                ", stockQuantity=" + stockQuantity +
                ", soldCount=" + soldCount +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}