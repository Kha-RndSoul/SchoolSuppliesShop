package com.shop.dao.product;

import com.shop.dao.support.BaseDao;
import com.shop.model.ProductReview;
import org.jdbi. v3.core.statement.PreparedBatch;

import java. util.*;

public class ProductReviewDAO extends BaseDao {

    static Map<Integer, ProductReview> data = new HashMap<>();
    static {
        data.put(1, new ProductReview(1, 1, 1, 5, "Sản phẩm rất tốt, chất lượng cao", true));
        data.put(2, new ProductReview(2, 1, 2, 4, "Giao hàng nhanh, đóng gói cẩn thận", true));
        data.put(3, new ProductReview(3, 2, 1, 5, "Bút viết trơn, giá rẻ", true));
        data.put(4, new ProductReview(4, 3, 3, 4, "Balo đẹp, nhiều ngăn tiện dụng", true));
        data.put(5, new ProductReview(5, 2, 2, 3, "Tạm được, giá hơi cao", false));
    }

    public List<ProductReview> getListReview() {
        return new ArrayList<>(data.values());
    }

    public ProductReview getReview(int id) {
        return data.get(id);
    }

    public List<ProductReview> getList() {
        return get().withHandle(h ->
                h. createQuery("SELECT review_id, product_id, customer_id, rating, comment, status, created_at FROM product_reviews")
                        .mapToBean(ProductReview.class)
                        .list()
        );
    }

    public ProductReview getReviewById(int id) {
        return get().withHandle(h ->
                h.createQuery("SELECT review_id, product_id, customer_id, rating, comment, status, created_at FROM product_reviews WHERE review_id = : id")
                        .bind("id", id)
                        .mapToBean(ProductReview.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public List<ProductReview> getApproved() {
        return get().withHandle(h ->
                h. createQuery("SELECT review_id, product_id, customer_id, rating, comment, status, created_at FROM product_reviews WHERE status = 1")
                        .mapToBean(ProductReview.class)
                        .list()
        );
    }

    public List<ProductReview> getByProductId(int productId) {
        return get().withHandle(h ->
                h.createQuery("SELECT review_id, product_id, customer_id, rating, comment, status, created_at FROM product_reviews WHERE product_id = : productId ORDER BY created_at DESC")
                        .bind("productId", productId)
                        .mapToBean(ProductReview.class)
                        .list()
        );
    }

    public List<ProductReview> getApprovedByProductId(int productId) {
        return get().withHandle(h ->
                h.createQuery("SELECT review_id, product_id, customer_id, rating, comment, status, created_at FROM product_reviews WHERE product_id = : productId AND status = 1 ORDER BY created_at DESC")
                        .bind("productId", productId)
                        .mapToBean(ProductReview.class)
                        .list()
        );
    }

    public List<ProductReview> getByCustomerId(int customerId) {
        return get().withHandle(h ->
                h.createQuery("SELECT review_id, product_id, customer_id, rating, comment, status, created_at FROM product_reviews WHERE customer_id = :customerId ORDER BY created_at DESC")
                        .bind("customerId", customerId)
                        .mapToBean(ProductReview.class)
                        .list()
        );
    }

    public double getAverageRating(int productId) {
        Double avg = get().withHandle(h ->
                h.createQuery("SELECT AVG(rating) FROM product_reviews WHERE product_id = : productId AND status = 1")
                        .bind("productId", productId)
                        .mapTo(Double. class)
                        .one()
        );
        return avg != null ? avg : 0.0;
    }

    public void insert(List<ProductReview> reviews) {
        get().useHandle(h -> {
            PreparedBatch batch = h.prepareBatch(
                    "INSERT INTO product_reviews (review_id, product_id, customer_id, rating, comment, status) VALUES (:reviewId, :productId, :customerId, :rating, : comment, :status)"
            );
            reviews.forEach(r -> batch.bindBean(r).add());
            batch.execute();
        });
    }

    public void insertReview(ProductReview review) {
        get().useHandle(h -> {
            h.createUpdate("INSERT INTO product_reviews (product_id, customer_id, rating, comment, status) VALUES (:productId, :customerId, : rating, :comment, :status)")
                    .bindBean(review)
                    .execute();
        });
    }

    public void updateReview(ProductReview review) {
        get().useHandle(h -> {
            h.createUpdate("UPDATE product_reviews SET rating = :rating, comment = :comment, status = :status WHERE review_id = :reviewId")
                    .bindBean(review)
                    .execute();
        });
    }

    public void toggleStatus(int id, boolean status) {
        get().useHandle(h -> {
            h.createUpdate("UPDATE product_reviews SET status = :status WHERE review_id = :id")
                    .bind("id", id)
                    .bind("status", status)
                    .execute();
        });
    }

    public void deleteReview(int id) {
        get().useHandle(h -> {
            h. createUpdate("DELETE FROM product_reviews WHERE review_id = :id")
                    .bind("id", id)
                    .execute();
        });
    }

    public int countByProduct(int productId) {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(review_id) FROM product_reviews WHERE product_id = :productId AND status = 1")
                        .bind("productId", productId)
                        .mapTo(Integer. class)
                        .one()
        );
    }

    public static void main(String[] args) {
        ProductReviewDAO dao = new ProductReviewDAO();
        System.out.println("=== INSERT DUMMY DATA ===");
        List<ProductReview> reviews = dao.getListReview();
        dao.insert(reviews);
        System.out.println("✅ Inserted " + reviews.size() + " reviews");

        System.out.println("\n=== GET FROM DB ===");
        dao.getList().forEach(System.out::println);
    }
}