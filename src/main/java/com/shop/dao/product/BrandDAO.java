package com.shop.dao.product;

import com.shop.dao.support.BaseDao;
import com.shop.model.Brand;
import org.jdbi.v3.core.statement.PreparedBatch;
import java.util.*;

public class BrandDAO extends BaseDao {

    static Map<Integer, Brand> data = new HashMap<>();
    static {
        data.put(1, new Brand(1, "Thiên Long", "/images/brands/thien-long.png"));
        data.put(2, new Brand(2, "Bitis", "/images/brands/bitis.png"));
        data.put(3, new Brand(3, "Campus", "/images/brands/campus. png"));
        data.put(4, new Brand(4, "Điểm 10", "/images/brands/diem10.png"));
        data.put(5, new Brand(5, "Hồng Hà", "/images/brands/hong-ha.png"));
        data.put(6, new Brand(6, "Bizner", "/images/brands/bizner.png"));
        data.put(7, new Brand(7, "Stabilo", "/images/brands/stabilo.png"));
    }

    public List<Brand> getListBrand() {
        return new ArrayList<>(data.values());
    }

    public Brand getBrand(int id) {
        return data. get(id);
    }

    public List<Brand> getList() {
        return get().withHandle(h ->
                h. createQuery("SELECT brand_id, brand_name, logo_url, created_at FROM brands")
                        .mapToBean(Brand.class)
                        .list()
        );
    }

    public Brand getBrandById(int id) {
        return get().withHandle(h ->
                h.createQuery("SELECT brand_id, brand_name, logo_url, created_at FROM brands WHERE brand_id = : id")
                        .bind("id", id)
                        .mapToBean(Brand.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public void insert(List<Brand> brands) {
        get().useHandle(h -> {
            PreparedBatch batch = h.prepareBatch(
                    "INSERT INTO brands (brand_id, brand_name, logo_url) VALUES (:brandId, :brandName, :logoUrl)"
            );
            brands. forEach(b -> batch.bindBean(b).add());
            batch.execute();
        });
    }

    public void insertBrand(Brand brand) {
        get().useHandle(h -> {
            h.createUpdate("INSERT INTO brands (brand_name, logo_url) VALUES (:brandName, :logoUrl)")
                    .bindBean(brand)
                    .execute();
        });
    }

    public void updateBrand(Brand brand) {
        get().useHandle(h -> {
            h.createUpdate("UPDATE brands SET brand_name = :brandName, logo_url = :logoUrl WHERE brand_id = :brandId")
                    .bindBean(brand)
                    .execute();
        });
    }

    public void deleteBrand(int id) {
        get().useHandle(h -> {
            h.createUpdate("DELETE FROM brands WHERE brand_id = :id")
                    .bind("id", id)
                    .execute();
        });
    }

    public int count() {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(brand_id) FROM brands")
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public int countProducts(int brandId) {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(product_id) FROM products WHERE brand_id = :brandId")
                        .bind("brandId", brandId)
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public static void main(String[] args) {
        BrandDAO dao = new BrandDAO();
        System.out.println(" Thêm DUMMY DATA ");
        List<Brand> brands = dao.getListBrand();
        dao.insert(brands);
        System.out.println(" Inserted " + brands.size() + " brands");

        System.out.println("Hiên thị tất cả brands từ DB:");
        dao.getList().forEach(System.out::println);
    }
}
