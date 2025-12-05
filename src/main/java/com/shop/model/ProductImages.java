package com.shop.model;

public class ProductImages {

    // Attributes
    private int imageId;
    private int productId;
    private String imageUrl;
    private boolean isPrimary;
    // Constructors

    // Constructor rỗng
    public ProductImages() {
    }

    // Constructor đầy đủ
    public ProductImages(int imageId, int productId, String imageUrl, boolean isPrimary) {
        this. imageId = imageId;
        this.productId = productId;
        this.imageUrl = imageUrl;
        this.isPrimary = isPrimary;
    }
    // Getters and Setters
    public int getImageId() {
        return imageId;
    }
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public boolean isPrimary() {
        return isPrimary;
    }
    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }
    // toString
    @Override
    public String toString() {
        return "ProductImages{" +
                "imageId=" + imageId +
                ", productId=" + productId +
                ", imageUrl='" + imageUrl + '\'' +
                ", isPrimary=" + isPrimary +
                '}';
    }
}
