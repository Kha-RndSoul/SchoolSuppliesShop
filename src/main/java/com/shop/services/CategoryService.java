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
    public List<Category> getAll() {
        try {
            return categoryDAO.getList();
        } catch (Exception e) {
            System.err.println(" Error in getAll: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Không thể lấy danh sách danh mục", e);
        }
    }

    // Lấy danh mục theo ID
    public Category getCategoryById(int id) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("Category ID phải lớn hơn 0");
            }
            Category category = categoryDAO.getCategoryById(id);

            if (category == null) {
                throw new RuntimeException("Không tìm thấy danh mục với ID: " + id);
            }
            return category;

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            System.err.println(" Error in getCategoryById: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Không thể lấy thông tin danh mục", e);
        }
    }

    // Đếm số danh mục
    public int count() {
        try {
            return categoryDAO.count();
        } catch (Exception e) {
            System.err.println(" Error in count: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }
    // Đếm số sản phẩm trong danh mục
    public int countProducts(int categoryId) {
        try {
            if (categoryId <= 0) {
                throw new IllegalArgumentException("Category ID phải lớn hơn 0");
            }
            return categoryDAO.countProducts(categoryId);
        } catch (Exception e) {
            System.err.println(" Error in countProducts: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }
}