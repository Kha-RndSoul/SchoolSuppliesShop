package com.shop.model;

/**
 * Model class for cart_items table
 * Represents items in a customer's shopping cart
 */
public class CartItem {

    // Fields matching database columns
    private int cartItemId;
    private int customerId;
    private int productId;
    private int quantity;

    // Constructors

    /**
     * Default constructor
     */
    public CartItem() {
    }

    /**
     * Constructor for creating new cart item (without ID)
     */
    public CartItem(int customerId, int productId, int quantity) {
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
    }

    /**
     * Full constructor with all fields
     */
    public CartItem(int cartItemId, int customerId, int productId, int quantity) {
        this.cartItemId = cartItemId;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters and Setters

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

    // toString method

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
