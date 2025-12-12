package com.shop.model;


public class Banner {

    private int bannerId;
    private String title;
    private String imageUrl;
    private boolean status; // true = active, false = inactive

    // Constructors

    public Banner() {
    }

    //Constructor để tạo banner mới
    public Banner(String title, String imageUrl, boolean status) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.status = status;
    }

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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    // Kiểm tra banner có đang hoạt động hay không
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