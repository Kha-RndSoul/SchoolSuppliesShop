package com.shop.dao.product;

import com.shop.dao.support.BaseDao;
import com.shop.model.Brand;
import org.jdbi.v3.core.statement.PreparedBatch;
import java.util.*;

public class BrandDAO extends BaseDao {

    static Map<Integer, Brand> data = new HashMap<>();
    static {
        data.put(1, new Brand(1, "Thiên Long"));
        data.put(2, new Brand(2, "Bitis"));
        data.put(3, new Brand(3, "Campus"));
        data.put(4, new Brand(4, "Điểm 10"));
        data.put(5, new Brand(5, "Hồng Hà"));
        data.put(6, new Brand(6, "Bizner"));
        data.put(7, new Brand(7, "Stabilo"));
    }

    public List<Brand> getListBrand() {
        return new ArrayList<>(data.values());
    }

    public Brand getBrand(int id) {
        return data.get(id);
    }
// Lấy tất cả brand từ DB
    public List<Brand> getList() {
        return get().withHandle(h ->
                h.createQuery("SELECT id, brand_name AS brandName, created_at FROM brands")
                        .mapToBean(Brand.class)
                        .list()
        );
    }
// Lấy brand theo ID
    public Brand getBrandById(int id) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, brand_name AS brandName, created_at FROM brands WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(Brand.class)
                        .findOne()
                        .orElse(null)
        );
    }
// Thêm thương hiệu
    public void insertBrand(Brand brand) {
        get().useHandle(h -> {
            h.createUpdate("INSERT INTO brands (brand_name) VALUES (:brandName)")
                    .bindBean(brand)
                    .execute();
        });
    }
    // Thêm nhiều thương hiệu
    public void insert(List<Brand> brands) {
        get().useHandle(h -> {
            PreparedBatch batch = h.prepareBatch("INSERT INTO brands (brand_name) VALUES (:brandName)");
            brands.forEach(b -> batch.bindBean(b).add());
            batch.execute();
        });
    }
// Cập nhập thương hiệu
    public void updateBrand(Brand brand) {
        get().useHandle(h -> {
            h.createUpdate("UPDATE brands SET brand_name = :brandName WHERE brand_id = :brandId")
                    .bindBean(brand)
                    .execute();
        });
    }
// Xóa thương hiệu
    public void deleteBrand(int id) {
        get().useHandle(h -> {
            h.createUpdate("DELETE FROM brands WHERE id = :id")
                    .bind("id", id)
                    .execute();
        });
    }
// Đếm số lượng thương hiệu
    public int count() {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(id) FROM brands")
                        .mapTo(Integer.class)
                        .one()
        );
    }
// Đếm số lượng sản phẩm theo thương hiệu
    public int countProducts(int id) {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(id) FROM products WHERE id = :id")
                        .bind("id", id)
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public static void main(String[] args) {
        BrandDAO dao = new BrandDAO();
        System.out.println(" Thêm DUMMY DATA ");
        List<Brand> brands = dao.getListBrand();
        dao.insertBrand(brands.get(0));
        System.out.println(" Inserted " + brands.size() + " brands");

        System.out.println("Hiên thị tất cả brands từ DB:");
        dao.getList().forEach(System.out::println);
    }
}
