package com.shop.dao.product;

import com.shop.model.ProductImage;
import org.jdbi.v3.sqlobject. config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer. Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement. GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement. SqlUpdate;

import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(ProductImage.class)
public interface ProductImageDAO {

    // Danh sách hình ảnh theo sản phẩm
    @SqlQuery("SELECT image_id, product_id, image_url, is_primary, created_at FROM product_images WHERE product_id = : productId ORDER BY is_primary DESC, created_at ASC")
    List<ProductImage> getByProductId(@Bind("productId") int productId);

    // Insert
    @SqlUpdate("INSERT INTO product_images (product_id, image_url, is_primary) VALUES (:productId, :imageUrl, :isPrimary)")
    @GetGeneratedKeys
    int insert(@BindBean ProductImage image);

    // Update
    @SqlUpdate("UPDATE product_images SET image_url = :imageUrl, is_primary = :isPrimary WHERE image_id = :imageId")
    void update(@BindBean ProductImage image);

    // Đặt hình ảnh chính
    @SqlUpdate("UPDATE product_images SET is_primary = : isPrimary WHERE image_id = :id")
    void togglePrimary(@Bind("id") int id, @Bind("isPrimary") boolean isPrimary);

    // Xóa ảnh chính của sản phẩm
    @SqlUpdate("UPDATE product_images SET is_primary = 0 WHERE product_id = :productId")
    void clearPrimaryFlag(@Bind("productId") int productId);

    // Delete
    @SqlUpdate("DELETE FROM product_images WHERE image_id = : id")
    void delete(@Bind("id") int id);

    // Đếm số hình ảnh của sản phẩm
    @SqlQuery("SELECT COUNT(image_id) FROM product_images WHERE product_id = :productId")
    int countByProduct(@Bind("productId") int productId);
}
