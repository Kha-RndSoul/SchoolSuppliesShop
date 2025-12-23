package com.shop.dao.product;

import com.shop.dao.support.BaseDao;
import com.shop.model.Product;
import org.jdbi.v3.core.statement.PreparedBatch;
import java.util.*;

public class ProductDAO extends BaseDao {

    static Map<Integer, Product> data = new HashMap<>();
    static {
        data.put(1, new Product(1, "Vở Campus 200 trang", "Vở kẻ ngang chất lượng", 1, 3, 15000.0, 12000.0, 100, 0, true));
        data.put(2, new Product(2, "Bút bi Thiên Long TL-027", "Bút bi xanh mực đen", 2, 1, 5000.0, 4000.0, 200, 50, true));
        data.put(3, new Product(3, "Balo Bitis DBB003300", "Balo học sinh cao cấp", 3, 2, 350000.0, 299000.0, 50, 10, true));
        data.put(4, new Product(4, "Thước kẻ 30cm", "Thước kẻ nhựa trong", 4, 4, 8000.0, 3000.0, 150, 0, true));
        data.put(5, new Product(5, "Tập vẽ Hồng Hà A4", "Tập vẽ 100 tờ", 4, 5, 25000.0, 22000.0, 80, 5, true));
        data.put(6, new Product(6, "Bút chì 2B Thiên Long", "Hộp 12 cây", 2, 1, 18000.0, 2000.0, 120, 20, true));
        data.put(7, new Product(7, "Kéo văn phòng Điểm 10", "Kéo inox 18cm", 4, 4, 12000.0, 10000.0, 90, 8, true));
    }

    public List<Product> getListProduct() { return new ArrayList<>(data.values()); }
    public Product getProduct(int id) { return data.get(id); }
// Lấy list sản phẩm với hình ảnh, thương hiệu, danh mục và đánh giá trung bình
    public List<Map<String, Object>> getListWithImage() {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT id, p.product_name AS productName, p.description, p.category_id AS categoryId, p.brand_id AS brandId, " +
                                        "p.price, p.sale_price AS salePrice, p.stock_quantity AS stockQuantity, p. sold_count AS soldCount, p.is_active AS isActive, " +
                                        "p.created_at AS createdAt, p.updated_at AS updatedAt, b.brand_name AS brandName, c.category_name AS categoryName, " +
                                        "COALESCE(AVG(pr.rating), 0) AS averageRating, pi.image_url AS imageUrl " +
                                        "FROM products p " +
                                        "LEFT JOIN brands b ON p.brand_id = b.id " +
                                        "LEFT JOIN categories c ON p.category_id = c.id " +
                                        "LEFT JOIN product_reviews pr ON p.id = pr.product_id AND pr.status = 1 " +
                                        "LEFT JOIN product_images pi ON p.id = pi.product_id AND pi.is_primary = 1 " +
                                        "WHERE p.is_active = 1 " +
                                        "GROUP BY p.id, b.brand_name, c.category_name, pi.image_url"
                        )
                        .mapToMap()
                        .list()
        );
    }
// Lấy sản phẩm bán chạy nhất với hình ảnh, thương hiệu, danh mục và đánh giá trung bình
    public List<Map<String, Object>> getBestSellersWithImage(int limit) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT p.id, p.product_name AS productName, p.description, p.category_id AS categoryId, p.brand_id AS brandId, " +
                                        "p.price, p.sale_price AS salePrice, p.stock_quantity AS stockQuantity, p.sold_count AS soldCount, p.is_active AS isActive, " +
                                        "p.created_at AS createdAt, p.updated_at AS updatedAt, b.brand_name AS brandName, c.category_name AS categoryName, " +
                                        "COALESCE(AVG(pr.rating), 0) AS averageRating, pi.image_url AS imageUrl " +
                                        "FROM products p " +
                                        "LEFT JOIN brands b ON p.brand_id = b.id " +
                                        "LEFT JOIN categories c ON p.category_id = c.id " +
                                        "LEFT JOIN product_reviews pr ON p.id = pr. product_id AND pr.status = 1 " +
                                        "LEFT JOIN product_images pi ON p.id = pi.product_id AND pi.is_primary = 1 " +
                                        "WHERE p.is_active = 1 " +
                                        "GROUP BY p.id, b.brand_name, c.category_name, pi.image_url " +
                                        "ORDER BY p.sold_count DESC LIMIT : limit"
                        )
                        .bind("limit", limit)
                        .mapToMap()
                        .list()
        );
    }
// Lấy sản phẩm theo danh mục với hình ảnh và thương hiệu
    public List<Map<String, Object>> getByCategoryIdWithImage(int categoryId) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT p.id, p.product_name AS productName, p.description, p.category_id AS categoryId, p.brand_id AS brandId, " +
                                        "p.price, p.sale_price AS salePrice, p.stock_quantity AS stockQuantity, p. sold_count AS soldCount, p.is_active AS isActive, " +
                                        "p.created_at AS createdAt, p.updated_at AS updatedAt, b.brand_name AS brandName, pi.image_url AS imageUrl " +
                                        "FROM products p " +
                                        "LEFT JOIN brands b ON p.brand_id = b.id " +
                                        "LEFT JOIN product_images pi ON p.id = pi.product_id AND pi. is_primary = 1 " +
                                        "WHERE p.category_id = :categoryId AND p.is_active = 1"
                        )
                        .bind("categoryId", categoryId)
                        .mapToMap()
                        .list()
        );
    }
// Lấy sản phẩm theo thương hiệu với hình ảnh và thương hiệu
    public List<Map<String, Object>> getByBrandIdWithImage(int brandId) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT p.id, p.product_name AS productName, p.description, p.category_id AS categoryId, p.brand_id AS brandId, " +
                                        "p.price, p.sale_price AS salePrice, p. stock_quantity AS stockQuantity, p.sold_count AS soldCount, p.is_active AS isActive, " +
                                        "p.created_at AS createdAt, p.updated_at AS updatedAt, b.brand_name AS brandName, pi.image_url AS imageUrl " +
                                        "FROM products p " +
                                        "LEFT JOIN brands b ON p. brand_id = b.id " +
                                        "LEFT JOIN product_images pi ON p.id = pi.product_id AND pi.is_primary = 1 " +
                                        "WHERE p.brand_id = :brandId AND p. is_active = 1"
                        )
                        .bind("brandId", brandId)
                        .mapToMap()
                        .list()
        );
    }
// Tìm kiếm sản phẩm với hình ảnh và thương hiệu
    public List<Map<String, Object>> searchWithImage(String keyword) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT p.id, p.product_name AS productName, p.description, p.category_id AS categoryId, p.brand_id AS brandId, " +
                                        "p. price, p.sale_price AS salePrice, p.stock_quantity AS stockQuantity, p. sold_count AS soldCount, p.is_active AS isActive, " +
                                        "p.created_at AS createdAt, p.updated_at AS updatedAt, b.brand_name AS brandName, pi.image_url AS imageUrl " +
                                        "FROM products p " +
                                        "LEFT JOIN brands b ON p.brand_id = b.id " +
                                        "LEFT JOIN product_images pi ON p.id = pi.product_id AND pi.is_primary = 1 " +
                                        "WHERE (p.product_name LIKE CONCAT('%', :keyword, '%') OR p.description LIKE CONCAT('%', :keyword, '%')) " +
                                        "AND p.is_active = 1"
                        )
                        .bind("keyword", keyword)
                        .mapToMap()
                        .list()
        );
    }
// Lấy sản phẩm theo ID với hình ảnh và thương hiệu
    public Map<String, Object> getProductByIdWithImage(int id) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT p.id, p.product_name AS productName, p.description, p. category_id AS categoryId, p.brand_id AS brandId, " +
                                        "p.price, p.sale_price AS salePrice, p.stock_quantity AS stockQuantity, p.sold_count AS soldCount, p. is_active AS isActive, " +
                                        "p.created_at AS createdAt, p. updated_at AS updatedAt, b.brand_name AS brandName, c.category_name AS categoryName, pi.image_url AS imageUrl " +
                                        "FROM products p " +
                                        "LEFT JOIN brands b ON p.brand_id = b.id " +
                                        "LEFT JOIN categories c ON p.category_id = c.id " +
                                        "LEFT JOIN product_images pi ON p.id = pi.product_id AND pi.is_primary = 1 " +
                                        "WHERE p.id = :id"
                        )
                        .bind("id", id)
                        .mapToMap()
                        .one()
        );
    }

    public void insert(List<Product> products) {
        get().useHandle(h -> {
            PreparedBatch batch = h.prepareBatch("INSERT INTO products (id, product_name, description, category_id, brand_id, price, sale_price, stock_quantity, sold_count, is_active) VALUES (:id, :productName, :description, :categoryId, : brandId, :price, :salePrice, :stockQuantity, :soldCount, :isActive)");
            products.forEach(p -> batch.bindBean(p).add());
            batch.execute();
        });
    }

    public void insertProduct(Product product) {
        get().useHandle(h -> h.createUpdate("INSERT INTO products (product_name, description, category_id, brand_id, price, sale_price, stock_quantity, sold_count, is_active) VALUES (:productName, :description, :categoryId, :brandId, :price, : salePrice, :stockQuantity, :soldCount, :isActive)")
                .bindBean(product).execute());
    }

    public void updateProduct(Product product) {
        get().useHandle(h -> h.createUpdate("UPDATE products SET product_name = :productName, description = :description, category_id = :categoryId, brand_id = :brandId, price = :price, sale_price = :salePrice, stock_quantity = :stockQuantity, is_active = :isActive WHERE id = :id")
                .bindBean(product).execute());
    }

    public void deleteProduct(int id) {
        get().useHandle(h -> h.createUpdate("DELETE FROM products WHERE id = :id").bind("id", id).execute());
    }

    public void toggleStatus(int id, boolean isActive) {
        get().useHandle(h -> h.createUpdate("UPDATE products SET is_active = :isActive WHERE id = :id")
                .bind("id", id).bind("isActive", isActive).execute());
    }

    public void decreaseStock(int id, int quantity) {
        get().useHandle(h -> h.createUpdate("UPDATE products SET stock_quantity = stock_quantity - :quantity, sold_count = sold_count + : quantity WHERE id = :id")
                .bind("id", id).bind("quantity", quantity).execute());
    }

    public void increaseStock(int id, int quantity) {
        get().useHandle(h -> h.createUpdate("UPDATE products SET stock_quantity = stock_quantity + :quantity WHERE id = :id")
                .bind("id", id).bind("quantity", quantity).execute());
    }

    public int count() {
        return get().withHandle(h -> h.createQuery("SELECT COUNT(id) FROM products").mapTo(Integer.class).one());
    }

    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();
        try {
            System.out.println(" TEST ");
            int count = dao.count();
            System.out.println("Current: " + count);
            if (count == 0) {
                dao.insert(dao.getListProduct());
                System.out.println(" Inserted " + dao.count());
            }
            dao.getBestSellersWithImage(5).forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}