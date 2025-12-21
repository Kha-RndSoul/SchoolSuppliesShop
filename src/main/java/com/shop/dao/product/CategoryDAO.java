package com.shop.dao.product;

import com.shop.dao.support.BaseDao;
import com.shop.model.Category;
import org.jdbi.v3.core.statement.PreparedBatch;
import java.util.*;

public class CategoryDAO extends BaseDao {

    // Dummy data
    static Map<Integer, Category> data = new HashMap<>();
    static {
        data.put(1, new Category(1, "Vở & Sổ", "/images/categories/vo-so.jpg"));
        data.put(2, new Category(2, "Bút & Viết", "/images/categories/but-viet.jpg"));
        data.put(3, new Category(3, "Balo & Túi", "/images/categories/balo. jpg"));
        data.put(4, new Category(4, "Dụng cụ học tập", "/images/categories/dung-cu. jpg"));
        data.put(5, new Category(5, "Đồ dùng văn phòng", "/images/categories/van-phong.jpg"));
    }
    // Lấy list từ dummy data
    public List<Category> getListCategory() {
        return new ArrayList<>(data.values());
    }

    public Category getCategory(int id) {
        return data.get(id);
    }
    // Lấy list từ DB
    public List<Category> getList() {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT category_id, category_name, image_url, created_at FROM categories"
                        )
                        .mapToBean(Category.class)
                        .list()
        );
    }
    // Lấy 1 category từ DB
    public Category getCategoryById(int id) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT category_id, category_name, image_url, created_at FROM categories WHERE category_id = :id"
                        )
                        .bind("id", id)
                        .mapToBean(Category.class)
                        .findOne()
                        .orElse(null)
        );
    }
    // Chèn nhiều
    public void insert(List<Category> categories) {
        get().useHandle(h -> {
            PreparedBatch batch = h.prepareBatch(
                    "INSERT INTO categories (category_id, category_name, image_url) VALUES (:categoryId, :categoryName, :imageUrl)"
            );
            categories.forEach(c -> batch.bindBean(c).add());
            batch.execute();
        });
    }
    // Thêm mới
    public void insertCategory(Category category) {
        get().useHandle(h -> {
            h.createUpdate(
                            "INSERT INTO categories (category_name, image_url) VALUES (:categoryName, :imageUrl)"
                    )
                    .bindBean(category)
                    .execute();
        });
    }
    // Update
    public void updateCategory(Category category) {
        get().useHandle(h -> {
            h. createUpdate(
                            "UPDATE categories SET category_name = :categoryName, image_url = :imageUrl WHERE category_id = :categoryId"
                    )
                    .bindBean(category)
                    .execute();
        });
    }
    // Xóa
    public void deleteCategory(int id) {
        get().useHandle(h -> {
            h.createUpdate("DELETE FROM categories WHERE category_id = :id")
                    .bind("id", id)
                    .execute();
        });
    }
    // Đếm số category
    public int count() {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(category_id) FROM categories")
                        .mapTo(Integer.class)
                        .one()
        );
    }
    // Đếm số sản phẩm trong category
    public int countProducts(int categoryId) {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(product_id) FROM products WHERE category_id = :categoryId")
                        .bind("categoryId", categoryId)
                        .mapTo(Integer.class)
                        .one()
        );
    }
    // Main test
    public static void main(String[] args) {
        CategoryDAO dao = new CategoryDAO();
        System.out.println(" Thêm DUMMY DATA ");
        List<Category> categories = dao.getListCategory();

        dao.insert(categories);
        System.out.println(" Inserted " + categories.size() + " categories");

        System.out.println(" Hiển thị tất cả categories từ DB ");
        dao.getList().forEach(System.out::println);
    }
}