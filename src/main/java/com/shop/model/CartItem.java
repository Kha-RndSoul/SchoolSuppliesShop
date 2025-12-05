package com.shop.model;

public class CartItem {
    private Long cartItemId;
    private Long customerId;
    private Long productId;
    private Integer quantity;

    // Default constructor
    public CartItem() {}

    // Parameterized constructor
    public CartItem(Long cartItemId, Long customerId, Long productId, Integer quantity) {
        this.cartItemId = cartItemId;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "cartItemId=" + cartItemId +
                ", customerId=" + customerId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}