package com.shop.model;


public class Banner {

    private int id;
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

    public Banner(int id, String title, String imageUrl, boolean status) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    // Getters and Setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    // Kiểm tra banner có đang hoạt động hay không
    public boolean isActive() {
        return this.status;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", status=" + status +
                '}';
    }
}