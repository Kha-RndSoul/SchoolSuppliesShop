package com.shop.services;

import com.shop.dao.product.ProductReviewDAO;
import com.shop.model.ProductReview;
import java.util.List;

public class ProductReviewService {

    private final ProductReviewDAO productReviewDAO;

    public ProductReviewService() {
        this.productReviewDAO = new ProductReviewDAO();
    }
// Lấy tất cả đánh giá
    public List<ProductReview> getAllReviews() {
        return productReviewDAO.getList();
    }

// Lấy đánh giá theo Product ID
    public List<ProductReview> getReviewsByProductId(int productId) {
        if (productId <= 0) throw new IllegalArgumentException("Product ID không hợp lệ");
        return productReviewDAO.getByProductId(productId);
    }
// Lấy đánh giá theo Customer ID
    public List<ProductReview> getReviewsByCustomerId(int customerId) {
        if (customerId <= 0) throw new IllegalArgumentException("Customer ID không hợp lệ");
        return productReviewDAO.getByCustomerId(customerId);
    }
// Lấy đánh giá theo ID
    public ProductReview getReviewById(int id) {
        if (id <= 0) throw new IllegalArgumentException("Review ID không hợp lệ");
        ProductReview review = productReviewDAO.getReviewById(id);
        if (review == null) throw new IllegalArgumentException("Không tìm thấy đánh giá với ID: " + id);
        return review;
    }
// Tạo đánh giá mới
    public void createReview(ProductReview review) {
        if (review == null) throw new IllegalArgumentException("ProductReview không được null");
        if (review.getProductId() <= 0) throw new IllegalArgumentException("Product ID không hợp lệ");
        if (review.getCustomerId() <= 0) throw new IllegalArgumentException("Customer ID không hợp lệ");
        if (review.getRating() < 1 || review.getRating() > 5) throw new IllegalArgumentException("Rating phải từ 1-5");
        if (review.getComment() == null || review.getComment().trim().isEmpty()) throw new IllegalArgumentException("Nội dung đánh giá không được rỗng");
        productReviewDAO.insertReview(review);
    }
// Cập nhật đánh giá
    public void updateReview(ProductReview review) {
        if (review == null) throw new IllegalArgumentException("ProductReview không được null");
        if (review.getId() <= 0) throw new IllegalArgumentException("Review ID không hợp lệ");
        ProductReview existing = productReviewDAO.getReviewById(review.getId());
        if (existing == null) throw new IllegalArgumentException("Không tìm thấy đánh giá với ID:  " + review.getId());
        if (review.getRating() < 1 || review.getRating() > 5) throw new IllegalArgumentException("Rating phải từ 1-5");
        productReviewDAO.updateReview(review);
    }
// Duyệt đánh giá
    public void approveReview(int id) {
        if (id <= 0) throw new IllegalArgumentException("Review ID không hợp lệ");
        ProductReview existing = productReviewDAO.getReviewById(id);
        if (existing == null) throw new IllegalArgumentException("Không tìm thấy đánh giá với ID: " + id);
        productReviewDAO.toggleStatus(id, true);
    }
// Từ chối đánh giá
    public void rejectReview(int id) {
        if (id <= 0) throw new IllegalArgumentException("Review ID không hợp lệ");
        ProductReview existing = productReviewDAO.getReviewById(id);
        if (existing == null) throw new IllegalArgumentException("Không tìm thấy đánh giá với ID: " + id);
        productReviewDAO.toggleStatus(id, false);
    }
// Xóa đánh giá
    public void deleteReview(int id) {
        if (id <= 0) throw new IllegalArgumentException("Review ID không hợp lệ");
        ProductReview existing = productReviewDAO.getReviewById(id);
        if (existing == null) throw new IllegalArgumentException("Không tìm thấy đánh giá với ID: " + id);
        productReviewDAO.deleteReview(id);
    }
// Tính điểm đánh giá trung bình cho sản phẩm
    public double getAverageRating(int productId) {
        if (productId <= 0) throw new IllegalArgumentException("Product ID không hợp lệ");
        return productReviewDAO.getAverageRating(productId);
    }
// Đếm tổng số đánh giá cho sản phẩm
    public int getReviewCount(int productId) {
        if (productId <= 0) throw new IllegalArgumentException("Product ID không hợp lệ");
        return productReviewDAO.countByProduct(productId);
    }
    // Lấy đánh giá đã duyệt theo Product ID
    public List<ProductReview> getByProductId(int productId) {
        return productReviewDAO.getByProductId(productId);
    }
}
