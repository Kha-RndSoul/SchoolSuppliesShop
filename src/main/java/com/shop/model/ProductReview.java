package com.shop.model;

import java.sql.Timestamp;

public class ProductReview {
    private int reviewId;
    private int productId;
    private int customerId;
    private int rating;
    private String comment;
    private boolean status;
    private Timestamp createdAt;

    // Constructor rỗng
    public ProductReview() {
    }

    // Constructor cơ bản
    public ProductReview(int reviewId, int productId, int customerId, int rating, String comment, boolean status) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.customerId = customerId;
        this.rating = rating;
        this.comment = comment;
        this.status = status;
    }

    // Constructor đầy đủ
    public ProductReview(int reviewId, int productId, int customerId, int rating,
                         String comment, boolean status, Timestamp createdAt) {
        this.reviewId = reviewId;
        this.productId = productId;
        this. customerId = customerId;
        this.rating = rating;
        this.comment = comment;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ProductReview{" +
                "reviewId=" + reviewId +
                ", productId=" + productId +
                ", customerId=" + customerId +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}