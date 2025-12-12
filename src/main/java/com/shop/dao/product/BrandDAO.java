package com.shop.dao.product;

import com.shop.model.Brand;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org. jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject. statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(Brand.class)
public interface BrandDAO {

    // Danh sách tất cả thương hiệu
    @SqlQuery("SELECT brand_id, brand_name, logo_url, created_at FROM brands")
    List<Brand> getAll();

    // Danh sách thương hiệu theo id
    @SqlQuery("SELECT brand_id, brand_name, logo_url, created_at FROM brands WHERE brand_id = :id")
    Optional<Brand> getById(@Bind("id") int id);

    // Insert
    @SqlUpdate("INSERT INTO brands (brand_name, logo_url) VALUES (:brandName, :logoUrl)")
    @GetGeneratedKeys
    int insert(@BindBean Brand brand);

    // Update
    @SqlUpdate("UPDATE brands SET brand_name = :brandName, logo_url = :logoUrl WHERE brand_id = :brandId")
    void update(@BindBean Brand brand);

    // Delete
    @SqlUpdate("DELETE FROM brands WHERE brand_id = :id")
    void delete(@Bind("id") int id);

    // Đếm số lượng thương hiệu
    @SqlQuery("SELECT COUNT(brand_id) FROM brands")
    int count();

    // Đếm số sản phẩm của thương hiệu
    @SqlQuery("SELECT COUNT(product_id) FROM products WHERE brand_id = :id")
    int countProducts(@Bind("id") int id);
}
