package com.shop.dao.order;

import com.shop.dao.support.BaseDao;
import com.shop.model.CartItem;
import org.jdbi.v3.core.statement.PreparedBatch;
import java.sql.Timestamp;

import java.util.*;

public class CartItemDAO extends BaseDao {

    static Map<Integer, CartItem> data = new HashMap<>();
    static {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        data.put(1, new CartItem(1, 1, 1, 2, now, now));
        data.put(2, new CartItem(2, 1, 2, 5, now, now));
        data.put(3, new CartItem(3, 2, 3, 1, now, now));
        data.put(4, new CartItem(4, 2, 4, 3, now, now));
        data.put(5, new CartItem(5, 3, 5, 2, now, now));
    }

    public List<CartItem> getListCartItem() {
        return new ArrayList<>(data.values());
    }

    public CartItem getCartItem(int id) {
        return data.get(id);
    }

    public List<CartItem> getList() {
        return get().withHandle(h ->
                h. createQuery("SELECT id, customer_id, product_id, quantity, created_at, updated_at FROM cart_items")
                        .mapToBean(CartItem.class)
                        .list()
        );
    }

    public CartItem getCartItemById(int id) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, customer_id, product_id, quantity, created_at, updated_at FROM cart_items WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(CartItem.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public List<CartItem> getByCustomerId(int customerId) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, customer_id, product_id, quantity, created_at, updated_at FROM cart_items WHERE customer_id = :customerId ORDER BY created_at DESC")
                        .bind("customerId", customerId)
                        .mapToBean(CartItem.class)
                        .list()
        );
    }

    public CartItem getByCustomerIdAndProductId(int customerId, int productId) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, customer_id, product_id, quantity, created_at, updated_at FROM cart_items WHERE customer_id = :customerId AND product_id = :productId")
                        .bind("customerId", customerId)
                        .bind("productId", productId)
                        .mapToBean(CartItem.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public boolean existsInCart(int customerId, int productId) {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(id) > 0 FROM cart_items WHERE customer_id = :customerId AND product_id = :productId")
                        .bind("customerId", customerId)
                        .bind("productId", productId)
                        .mapTo(Boolean.class)
                        .one()
        );
    }

    public void insert(List<CartItem> cartItems) {
        get().useHandle(h -> {
            PreparedBatch batch = h.prepareBatch(
                    "INSERT INTO cart_items (id, customer_id, product_id, quantity, created_at, updated_at) VALUES (:id, :customerId, :productId, :quantity, NOW(), NOW())"
            );
            cartItems.forEach(item -> batch.bindBean(item).add());
            batch.execute();
        });
    }

    public void insertCartItem(CartItem cartItem) {
        get().useHandle(h -> {
            h.createUpdate("INSERT INTO cart_items (customer_id, product_id, quantity, created_at, updated_at) VALUES (:customerId, :productId, :quantity, NOW(), NOW())")
                    .bindBean(cartItem)
                    .execute();
        });
    }

    public void updateCartItem(CartItem cartItem) {
        get().useHandle(h -> {
            h.createUpdate("UPDATE cart_items SET quantity = :quantity, updated_at = NOW() WHERE id = :id")
                    .bindBean(cartItem)
                    .execute();
        });
    }

    public void updateQuantity(int customerId, int productId, int quantity) {
        get().useHandle(h -> {
            h. createUpdate("UPDATE cart_items SET quantity = :quantity, updated_at = NOW() WHERE customer_id = :customerId AND product_id = :productId")
                    .bind("customerId", customerId)
                    .bind("productId", productId)
                    .bind("quantity", quantity)
                    .execute();
        });
    }

    public void incrementQuantity(int customerId, int productId, int amount) {
        get().useHandle(h -> {
            h. createUpdate("UPDATE cart_items SET quantity = quantity + :amount, updated_at = NOW() WHERE customer_id = :customerId AND product_id = :productId")
                    .bind("customerId", customerId)
                    .bind("productId", productId)
                    .bind("amount", amount)
                    .execute();
        });
    }

    public void decrementQuantity(int customerId, int productId, int amount) {
        get().useHandle(h -> {
            h.createUpdate("UPDATE cart_items SET quantity = quantity - :amount, updated_at = NOW() WHERE customer_id = :customerId AND product_id = :productId AND quantity > :amount")
                    .bind("customerId", customerId)
                    .bind("productId", productId)
                    .bind("amount", amount)
                    .execute();
        });
    }

    public void deleteCartItem(int id) {
        get().useHandle(h -> {
            h.createUpdate("DELETE FROM cart_items WHERE id = :id")
                    .bind("id", id)
                    .execute();
        });
    }

    public void deleteByCustomerIdAndProductId(int customerId, int productId) {
        get().useHandle(h -> {
            h.createUpdate("DELETE FROM cart_items WHERE customer_id = :customerId AND product_id = :productId")
                    .bind("customerId", customerId)
                    .bind("productId", productId)
                    .execute();
        });
    }

    public void clearCart(int customerId) {
        get().useHandle(h -> {
            h.createUpdate("DELETE FROM cart_items WHERE customer_id = :customerId")
                    .bind("customerId", customerId)
                    .execute();
        });
    }

    public int countItemsByCustomerId(int customerId) {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(id) FROM cart_items WHERE customer_id = :customerId")
                        .bind("customerId", customerId)
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public int getTotalQuantityByCustomerId(int customerId) {
        Integer total = get().withHandle(h ->
                h.createQuery("SELECT COALESCE(SUM(quantity), 0) FROM cart_items WHERE customer_id = :customerId")
                        .bind("customerId", customerId)
                        .mapTo(Integer.class)
                        .one()
        );
        return total != null ? total : 0;
    }

    public boolean isCartEmpty(int customerId) {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(id) = 0 FROM cart_items WHERE customer_id = :customerId")
                        .bind("customerId", customerId)
                        .mapTo(Boolean.class)
                        .one()
        );
    }

    public static void main(String[] args) {
        CartItemDAO dao = new CartItemDAO();
        System.out.println("=== INSERT DUMMY DATA ===");
        List<CartItem> items = dao.getListCartItem();
        dao.insert(items);
        System.out.println("✅ Inserted " + items.size() + " cart items");

        System. out.println("\n=== GET BY CUSTOMER ID 1 ===");
        dao.getByCustomerId(1).forEach(System.out::println);
    }
}