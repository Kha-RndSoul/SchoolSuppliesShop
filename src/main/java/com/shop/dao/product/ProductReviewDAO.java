package com.shop.dao.product;

import com.shop.model. ProductReview;
import org. jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi. v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject. statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject. statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(ProductReview. class)
public interface ProductReviewDAO {

    // Danh sách tất cả đánh giá
    @SqlQuery("SELECT review_id, product_id, customer_id, rating, comment, status, created_at FROM product_reviews")
    List<ProductReview> getAll();

    // Danh sách đánh giá theo id
    @SqlQuery("SELECT review_id, product_id, customer_id, rating, comment, status, created_at FROM product_reviews WHERE review_id = :id")
    Optional<ProductReview> getById(@Bind("id") int id);

    // Danh sách đánh giá đã duyệt
    @SqlQuery("SELECT review_id, product_id, customer_id, rating, comment, status, created_at FROM product_reviews WHERE status = 1")
    List<ProductReview> getApproved();

    // Danh sách đánh giá theo sản phẩm
    @SqlQuery("SELECT review_id, product_id, customer_id, rating, comment, status, created_at FROM product_reviews WHERE product_id = :productId ORDER BY created_at DESC")
    List<ProductReview> getByProductId(@Bind("productId") int productId);

    // Danh sách đánh giá đã duyệt theo sản phẩm
    @SqlQuery("SELECT review_id, product_id, customer_id, rating, comment, status, created_at FROM product_reviews WHERE product_id = :productId AND status = 1 ORDER BY created_at DESC")
    List<ProductReview> getApprovedByProductId(@Bind("productId") int productId);

    // Danh sách đánh giá theo khách hàng
    @SqlQuery("SELECT review_id, product_id, customer_id, rating, comment, status, created_at FROM product_reviews WHERE customer_id = :customerId ORDER BY created_at DESC")
    List<ProductReview> getByCustomerId(@Bind("customerId") int customerId);

    // Lấy rating trung bình
    @SqlQuery("SELECT AVG(rating) FROM product_reviews WHERE product_id = :productId AND status = 1")
    Double getAverageRating(@Bind("productId") int productId);

    // Insert
    @SqlUpdate("INSERT INTO product_reviews (product_id, customer_id, rating, comment, status) VALUES (:productId, :customerId, :rating, : comment, :status)")
    @GetGeneratedKeys
    int insert(@BindBean ProductReview review);

    // Update
    @SqlUpdate("UPDATE product_reviews SET rating = :rating, comment = :comment, status = :status WHERE review_id = :reviewId")
    void update(@BindBean ProductReview review);

    // Thay đổi trạng thái duyệt
    @SqlUpdate("UPDATE product_reviews SET status = : status WHERE review_id = :id")
    void toggleStatus(@Bind("id") int id, @Bind("status") boolean status);

    // Delete
    @SqlUpdate("DELETE FROM product_reviews WHERE review_id = :id")
    void delete(@Bind("id") int id);

    // Đếm số đánh giá của sản phẩm
    @SqlQuery("SELECT COUNT(review_id) FROM product_reviews WHERE product_id = :productId AND status = 1")
    int countByProduct(@Bind("productId") int productId);
}
