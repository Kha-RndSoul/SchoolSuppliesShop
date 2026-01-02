package com.shop.dao.product;

import com.shop.dao.support.BaseDao;
import com.shop.model.ProductImage;
import org.jdbi.v3.core.statement.PreparedBatch;
import java.util.*;

public class ProductImageDAO extends BaseDao {

    static Map<Integer, ProductImage> data = new HashMap<>();
    static {
        data.put(1, new ProductImage(1, 1, "/images/products/vo-campus-1.jpg", true));
        data.put(2, new ProductImage(2, 1, "/images/products/vo-campus-2.jpg", false));

        data.put(3, new ProductImage(3, 2, "/images/products/but-tl027-1.jpg", true));

        data.put(4, new ProductImage(4, 3, "/images/products/balo-bitis-1.jpg", true));
        data.put(5, new ProductImage(5, 3, "/images/products/balo-bitis-2.jpg", false));
    }

    public List<ProductImage> getListProductImage() {
        return new ArrayList<>(data.values());
    }

    public ProductImage getProductImage(int id) {
        return data.get(id);
    }
// Lấy danh sách hình ảnh của một sản phẩm theo productId
    public List<ProductImage> getByProductId(int productId) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT id, product_id AS productId, image_url, is_primary, created_at FROM product_images " +
                                        "WHERE product_id = :productId ORDER BY is_primary DESC, created_at ASC"
                        )
                        .bind("productId", productId)
                        .mapToBean(ProductImage.class)
                        .list()
        );
    }
// Thêm hình ảnh sản phẩm
    public void insertImage(ProductImage image) {
        get().useHandle(h -> {
            h.createUpdate("INSERT INTO product_images (product_id, image_url, is_primary) VALUES (:productId, :imageUrl, :isPrimary)")
                    .bindBean(image)
                    .execute();
        });
    }
// Thêm nhiều hình ảnh sản phẩm
    public void insert(List<ProductImage> images) {
        get().useHandle(h -> {
            PreparedBatch batch = h.prepareBatch("INSERT INTO product_images (product_id, image_url, is_primary) VALUES (:productId, :imageUrl, :isPrimary)");
            images.forEach(img -> batch.bindBean(img).add());
            batch.execute();
        });
    }
    // Cập nhật hình ảnh sản phẩm
    public void updateImage(ProductImage image) {
        get().useHandle(h -> {
            h.createUpdate("UPDATE product_images SET image_url = :imageUrl, is_primary = :isPrimary WHERE id = :id")
                    .bindBean(image)
                    .execute();
        });
    }
// Chuyển đổi trạng thái hình ảnh chính
    public void togglePrimary(int id, boolean isPrimary) {
        get().useHandle(h -> {
            h.createUpdate("UPDATE product_images SET is_primary = :isPrimary WHERE id = :id")
                    .bind("id", id)
                    .bind("isPrimary", isPrimary)
                    .execute();
        });
    }
// Xóa tag ảnh chính của một ảnh sản phẩm
    public void clearPrimaryFlag(int productId) {
        get().useHandle(h -> {
            h.createUpdate("UPDATE product_images SET is_primary = 0 WHERE product_id = :productId")
                    .bind("productId", productId)
                    .execute();
        });
    }
// Xóa hình ảnh sản phẩm
    public void deleteImage(int id) {
        get().useHandle(h -> {
            h.createUpdate("DELETE FROM product_images WHERE id = :id")
                    .bind("id", id)
                    .execute();
        });
    }
// Đếm số lượng hình ảnh của một sản phẩm
    public int countByProduct(int productId) {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(id) FROM product_images WHERE product_id = :productId")
                        .bind("productId", productId)
                        .mapTo(Integer.class)
                        .one()
        );
    }
// Test
    public static void main(String[] args) {
        ProductImageDAO dao = new ProductImageDAO();
        System.out.println(" INSERT DUMMY DATA ");
        List<ProductImage> images = dao.getListProductImage();
        dao.insertImage(images.get(0));
        System.out.println(" Inserted " + images.size() + " product images");

        System.out.println("\n GET BY PRODUCT ID 1 ");
        dao.getByProductId(1).forEach(System.out::println);
    }
}