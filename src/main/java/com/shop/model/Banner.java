package com.shop.model;

/**
 * Model class for banners table
 * Represents a banner/slider on homepage
 */
public class Banner {

    // Fields matching database columns
    private int bannerId;
    private String title;
    private String imageUrl;
    private boolean status; // true = active, false = inactive

    // Constructors

    /**
     * Default constructor
     */
    public Banner() {
    }

    /**
     * Constructor for creating new banner (without ID)
     */
    public Banner(String title, String imageUrl, boolean status) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    /**
     * Full constructor with all fields
     */
    public Banner(int bannerId, String title, String imageUrl, boolean status) {
        this.bannerId = bannerId;
        this. title = title;
        this. imageUrl = imageUrl;
        this.status = status;
    }

    // Getters and Setters

    public int getBannerId() {
        return bannerId;
    }

    public void setBannerId(int bannerId) {
        this.bannerId = bannerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this. title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    // Utility methods

    /**
     * Check if banner is active
     */
    public boolean isActive() {
        return this.status;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "bannerId=" + bannerId +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", status=" + status +
                '}';
    }
}