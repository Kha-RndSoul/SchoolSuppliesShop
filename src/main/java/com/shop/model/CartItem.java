package com.shop.model;

import java.sql.Timestamp;

public class CartItem {
    private int cartItemId;
    private int customerId;
    private int productId;
    private int quantity;
    private Timestamp addedAt;
    private Timestamp updatedAt;


    public CartItem() {}


    public CartItem(int customerId, int productId, int quantity) {
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public CartItem(int cartItemId, int customerId, int productId, int quantity,
                    Timestamp addedAt, Timestamp updatedAt) {
        this.cartItemId = cartItemId;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.addedAt = addedAt;
        this.updatedAt = updatedAt;
    }


    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Timestamp addedAt) {
        this.addedAt = addedAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "cartItemId=" + cartItemId +
                ", customerId=" + customerId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", addedAt=" + addedAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}