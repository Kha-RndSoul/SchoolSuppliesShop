package com.shop.dao.product;

import com.shop.dao.support.BaseDao;
import com.shop.model.Category;
import org.jdbi.v3.core.statement.PreparedBatch;
import java.util.*;

public class CategoryDAO extends BaseDao {


    // Lấy tất cả category từ DB
    public List<Category> getList() {
        return get().withHandle(h ->
                h.createQuery("SELECT id, category_name, image_url, created_at FROM categories")
                        .map((rs, ctx) -> {
                            Category category = new Category();
                            category.setId(rs.getInt("id"));
                            category.setCategoryName(rs.getString("category_name"));
                            category.setImageUrl(rs.getString("image_url"));
                            category.setCreatedAt(rs.getTimestamp("created_at"));
                            return category;
                        })
                        .list()
        );
    }
    // Lấy category theo ID
    public Category getCategoryById(int id) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, category_name, image_url, created_at FROM categories WHERE id = :id")
                        .bind("id", id)
                        .map((rs, ctx) -> {
                            Category category = new Category();
                            category.setId(rs.getInt("id"));
                            category.setCategoryName(rs.getString("category_name"));
                            category.setImageUrl(rs.getString("image_url"));
                            category.setCreatedAt(rs.getTimestamp("created_at"));
                            return category;
                        })
                        .findOne()
                        .orElse(null)
        );
    }
// Thêm nhiều category
    public void insert(List<Category> categories) {
        get().useHandle(h -> {
            PreparedBatch batch = h.prepareBatch("INSERT INTO categories (id, category_name, image_url) VALUES (:id, :categoryName, :imageUrl)");
            categories.forEach(c -> batch.bindBean(c).add());
            batch.execute();
        });
    }
// Thêm category
    public void insertCategory(Category category) {
        get().useHandle(h -> h.createUpdate("INSERT INTO categories (category_name, image_url) VALUES (:categoryName, :imageUrl)")
                .bindBean(category).execute());
    }

    public void updateCategory(Category category) {
        get().useHandle(h -> h.createUpdate("UPDATE categories SET category_name = :categoryName, image_url = :imageUrl WHERE id = :id")
                .bindBean(category).execute());
    }

    public void deleteCategory(int id) {
        get().useHandle(h -> h.createUpdate("DELETE FROM categories WHERE id = :id").bind("id", id).execute());
    }

    public int count() {
        return get().withHandle(h -> h.createQuery("SELECT COUNT(id) FROM categories").mapTo(Integer.class).one());
    }

    public int countProducts(int id) {
        return get().withHandle(h -> h.createQuery("SELECT COUNT(id) FROM products WHERE id = :id")
                .bind("id", id).mapTo(Integer.class).one());
    }


}