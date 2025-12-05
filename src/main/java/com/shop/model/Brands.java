package com.shop.model;

public class Brands {

    // Attributes
    private int brandId;
    private String brandName;
    private String logoUrl;
    // Constructors

    // Constructor rỗng
    public Brands() {
    }

    // Constructor đầy đủ
    public Brands(int brandId, String brandName, String logoUrl) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.logoUrl = logoUrl;
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
    // toString
    @Override
    public String toString() {
        return "Brands{" +
                "brandId=" + brandId +
                ", brandName='" + brandName + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                '}';
    }
}