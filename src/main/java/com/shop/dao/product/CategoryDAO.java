package com.shop.dao.product;

import com.shop.model.Category;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org. jdbi.v3.sqlobject. customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject. statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject. statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(Category.class)
public interface CategoryDAO {

    // Danh sách tất cả danh mục
    @SqlQuery("SELECT category_id, category_name, image_url, created_at FROM categories")
    List<Category> getAll();

    // Danh sách danh mục theo id
    @SqlQuery("SELECT category_id, category_name, image_url, created_at FROM categories WHERE category_id = :id")
    Optional<Category> getById(@Bind("id") int id);

    // Insert
    @SqlUpdate("INSERT INTO categories (category_name, image_url) VALUES (:categoryName, :imageUrl)")
    @GetGeneratedKeys
    int insert(@BindBean Category category);

    // Update
    @SqlUpdate("UPDATE categories SET category_name = :categoryName, image_url = :imageUrl WHERE category_id = :categoryId")
    void update(@BindBean Category category);

    // Delete
    @SqlUpdate("DELETE FROM categories WHERE category_id = :id")
    void delete(@Bind("id") int id);

    // Đếm số lượng danh mục
    @SqlQuery("SELECT COUNT(category_id) FROM categories")
    int count();

    // Đếm số sản phẩm trong danh mục
    @SqlQuery("SELECT COUNT(product_id) FROM products WHERE category_id = :id")
    int countProducts(@Bind("id") int id);
}
