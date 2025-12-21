package com.shop.services;

import com.shop.dao.product.CategoryDAO;
import com. shop.model.Category;
import com.shop.util.JdbiConnection;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.Optional;

public class CategoryService {
    private final Jdbi jdbi;
    private final CategoryDAO categoryDAO;

    public CategoryService() {
        this.jdbi = JdbiConnection.getJdbi();
        this.categoryDAO = jdbi.onDemand(CategoryDAO.class);
    }
    // Lấy tất cả danh mục
    public List<Category> getAll() {
        return categoryDAO.getAll();
    }
    // Lấy danh mục theo ID
    public Optional<Category> getById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Category ID phải lớn hơn 0");
        }
        return categoryDAO.getById(id);
    }
    // Thêm danh mục mới
    public int create(Category category) {
        validateCategory(category);
        return categoryDAO.insert(category);
    }
    // Cập nhật danh mục
    public void update(Category category) {
        validateCategory(category);
        if (category.getCategoryId() <= 0) {
            throw new IllegalArgumentException("Category ID phải lớn hơn 0");
        }
        categoryDAO.update(category);
    }
    // Xóa danh mục
    public void delete(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Category ID phải lớn hơn 0");
        }
        // Kiểm tra danh mục có sản phẩm không
        int productCount = categoryDAO.countProducts(id);
        if (productCount > 0) {
            throw new IllegalStateException("Không thể xóa danh mục đang có " + productCount + " sản phẩm");
        }
        categoryDAO.delete(id);
    }
    // Đếm số danh mục
    public int count() {
        return categoryDAO.count();
    }
    // Đếm số sản phẩm trong danh mục
    public int countProducts(int categoryId) {
        if (categoryId <= 0) {
            throw new IllegalArgumentException("Category ID phải lớn hơn 0");
        }
        return categoryDAO.countProducts(categoryId);
    }
    // Validate danh mục
    private void validateCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category không được null");
        }
        if (category.getCategoryName() == null || category.getCategoryName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên danh mục không được rỗng");
        }
    }
}
