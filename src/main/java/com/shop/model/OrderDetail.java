package com.shop.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderDetail {
    private int orderDetailId;               // ✅ Đổi từ Long sang int
    private int orderId;                     // ✅ Đổi từ Long sang int
    private int productId;                   // ✅ Đổi từ Long sang int
    private String productName;              // ✅ Thêm field mới - lưu tên sản phẩm tại thời điểm đặt
    private BigDecimal price;                // ✅ Thêm field mới - giá tại thời điểm đặt
    private int quantity;                    // ✅ Đổi từ Integer sang int
    private BigDecimal subtotal;
    private Timestamp createdAt;             // ✅ Thêm field mới

    // Default constructor
    public OrderDetail() {}

    // Constructor for creating new order detail (without ID and timestamp)
    public OrderDetail(int orderId, int productId, String productName,
                       BigDecimal price, int quantity, BigDecimal subtotal) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    // Full constructor
    public OrderDetail(int orderDetailId, int orderId, int productId, String productName,
                       BigDecimal price, int quantity, BigDecimal subtotal, Timestamp createdAt) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderDetailId=" + orderDetailId +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", subtotal=" + subtotal +
                ", createdAt=" + createdAt +
                '}';
    }
}