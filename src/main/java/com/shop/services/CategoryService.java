package com.shop.services;

import com.shop.dao.product.CategoryDAO;
import com.shop.model.Category;
import java.util.List;

public class CategoryService {

    private final CategoryDAO categoryDAO;

    public CategoryService() {
        this.categoryDAO = new CategoryDAO();
    }
// Lấy tất cả danh mục
    public List<Category> getAllCategories() {
        return categoryDAO.getList();
    }
// Lấy danh mục theo ID
    public Category getCategoryById(int id) {
        if (id <= 0) throw new IllegalArgumentException("Category ID không hợp lệ");
        Category category = categoryDAO.getCategoryById(id);
        if (category == null) throw new IllegalArgumentException("Không tìm thấy danh mục với ID: " + id);
        return category;
    }
// Tạo danh mục mới
    public void createCategory(Category category) {
        if (category == null) throw new IllegalArgumentException("Category không được null");
        if (category.getCategoryName() == null || category.getCategoryName().trim().isEmpty()) throw new IllegalArgumentException("Tên danh mục không được rỗng");
        if (category.getImageUrl() == null || category.getImageUrl().trim().isEmpty()) throw new IllegalArgumentException("URL hình ảnh không được rỗng");
        categoryDAO.insertCategory(category);
    }
// Cập nhật danh mục
    public void updateCategory(Category category) {
        if (category == null) throw new IllegalArgumentException("Category không được null");
        if (category.getId() <= 0) throw new IllegalArgumentException("Category ID không hợp lệ");
        Category existing = categoryDAO.getCategoryById(category.getId());
        if (existing == null) throw new IllegalArgumentException("Không tìm thấy danh mục với ID: " + category.getId());
        if (category.getCategoryName() == null || category.getCategoryName().trim().isEmpty()) throw new IllegalArgumentException("Tên danh mục không được rỗng");
        if (category.getImageUrl() == null || category.getImageUrl().trim().isEmpty()) throw new IllegalArgumentException("URL hình ảnh không được rỗng");
        categoryDAO.updateCategory(category);
    }
// Xóa danh mục
    public void deleteCategory(int id) {
        if (id <= 0) throw new IllegalArgumentException("Category ID không hợp lệ");
        Category existing = categoryDAO.getCategoryById(id);
        if (existing == null) throw new IllegalArgumentException("Không tìm thấy danh mục với ID:  " + id);
        int productCount = categoryDAO.countProducts(id);
        if (productCount > 0) throw new IllegalArgumentException("Không thể xóa danh mục đang có " + productCount + " sản phẩm");
        categoryDAO.deleteCategory(id);
    }
// Đếm tổng số danh mục
    public int getTotalCategories() {
        return categoryDAO.count();
    }
// Đếm số sản phẩm trong danh mục
    public int getProductCount(int categoryId) {
        if (categoryId <= 0) throw new IllegalArgumentException("Category ID không hợp lệ");
        return categoryDAO.countProducts(categoryId);
    }
// Kiểm tra sự tồn tại của danh mục
    public boolean categoryExists(int id) {
        try {
            getCategoryById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}