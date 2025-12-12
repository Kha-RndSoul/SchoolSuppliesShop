package com.shop.model;

public class Product {
    // Attributes
    private int productId;
    private String productName;
    private double price;
    private double salePrice;
    private int stockQuantity;
    // Constructors

    // Constructor rỗng
    public Product() {
    }

    // Constructor đầy đủ
    public Product(int productId, String productName, double price, double salePrice, int stockQuantity) {
        this. productId = productId;
        this.productName = productName;
        this.price = price;
        this.salePrice = salePrice;
        this. stockQuantity = stockQuantity;
    }

    // Getters and Setters
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
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public double getSalePrice() {
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
    // toString
    @Override
    public String toString() {
        return "Products{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", salePrice=" + salePrice +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
}