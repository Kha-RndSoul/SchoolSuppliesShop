package com.shop.dao. product;

import com.shop.dao.support.BaseDao;
import com.shop.model.Category;
import org.jdbi.v3.core.statement.PreparedBatch;
import java.util.*;

public class CategoryDAO extends BaseDao {

    static Map<Integer, Category> data = new HashMap<>();
    static {
        data.put(1, new Category(1, "Vở & Sổ", "/images/categories/vo-so.jpg"));
        data.put(2, new Category(2, "Bút & Viết", "/images/categories/but-viet.jpg"));
        data.put(3, new Category(3, "Balo & Túi", "/images/categories/balo. jpg"));
        data.put(4, new Category(4, "Dụng cụ học tập", "/images/categories/dung-cu. jpg"));
        data.put(5, new Category(5, "Đồ dùng văn phòng", "/images/categories/van-phong.jpg"));
    }

    public List<Category> getListCategory() { return new ArrayList<>(data.values()); }
    public Category getCategory(int id) { return data.get(id); }
// Lấy tất cả category từ DB
    public List<Category> getList() {
        return get().withHandle(h ->
                h.createQuery("SELECT id, category_name AS categoryName, image_url AS imageUrl, created_at AS createdAt FROM categories")
                        .mapToBean(Category.class)
                        .list()
        );
    }
// Lấy category theo ID
    public Category getCategoryById(int id) {
        return get().withHandle(h ->
                h. createQuery("SELECT id, category_name AS categoryName, image_url AS imageUrl, created_at AS createdAt FROM categories WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(Category.class)
                        .findOne()
                        . orElse(null)
        );
    }
// Thêm nhiều category
    public void insert(List<Category> categories) {
        get().useHandle(h -> {
            PreparedBatch batch = h.prepareBatch("INSERT INTO categories (id, category_name, image_url) VALUES (:id, :categoryName, : imageUrl)");
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

    public static void main(String[] args) {
        CategoryDAO dao = new CategoryDAO();
        try {
            System.out.println(" TEST ");
            int count = dao.count();
            System.out.println(" Hiện tại: " + count);
            if (count == 0) {
                dao.insert(dao.getListCategory());
                System.out. println(" Inserted " + dao.count());
            }
            dao.getList().forEach(System.out:: println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}