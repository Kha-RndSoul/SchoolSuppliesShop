package com.shop.dao.product;

import com.shop.model.Category;
import org.jdbi.v3.sqlobject. config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer. Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement. GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement. SqlUpdate;

import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(Category.class)
public interface CategoryDAO {

    // Danh sách tất cả danh mục
    @SqlQuery("SELECT * FROM categories")
    List<Category> getAll();

    // Lấy danh mục theo ID
    @SqlQuery("SELECT * FROM categories WHERE id = : id")
    Optional<Category> getById(@Bind("id") int id);

    // Lấy danh mục cha (parent_id = NULL)
    @SqlQuery("SELECT * FROM categories WHERE parent_id IS NULL")
    List<Category> getRootCategories();

    // Lấy danh mục con theo parent_id
    @SqlQuery("SELECT * FROM categories WHERE parent_id = :parentId")
    List<Category> getByParentId(@Bind("parentId") int parentId);

    // Insert
    @SqlUpdate("INSERT INTO categories (name, description, parent_id) VALUES (:name, :description, :parentId)")
    @GetGeneratedKeys
    int insert(@BindBean Category category);

    // Update
    @SqlUpdate("UPDATE categories SET name = :name, description = :description, parent_id = :parentId WHERE id = : id")
    void update(@BindBean Category category);

    // Delete
    @SqlUpdate("DELETE FROM categories WHERE id = :id")
    void delete(@Bind("id") int id);

    // Đếm số lượng danh mục
    @SqlQuery("SELECT COUNT(*) FROM categories")
    int count();

    // Kiểm tra danh mục có sản phẩm không
    @SqlQuery("SELECT COUNT(*) FROM products WHERE category_id = :id")
    int countProducts(@Bind("id") int id);
}
