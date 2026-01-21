package com.shop.dao.product;

import com.shop.dao.support.BaseDao;
import com.shop.model.Product;
import org.jdbi.v3.core.statement.PreparedBatch;
import java.util.*;

public class ProductDAO extends BaseDao {

    private Map<String, Object> mapProductRow(java.sql.ResultSet rs) throws java.sql.SQLException {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", rs.getInt("id"));
        map.put("productName", rs.getString("productName"));
        map.put("description", rs.getString("description"));
        map.put("categoryId", rs.getInt("categoryId"));
        map.put("brandId", rs.getInt("brandId"));
        map.put("price", rs.getDouble("price"));
        map.put("salePrice", rs.getDouble("salePrice"));
        map.put("stockQuantity", rs.getInt("stockQuantity"));
        map.put("soldCount", rs.getInt("soldCount"));
        map.put("isActive", rs.getBoolean("isActive"));
        map.put("createdAt", rs.getTimestamp("createdAt"));
        map.put("updatedAt", rs.getTimestamp("updatedAt"));
        map.put("brandName", rs.getString("brandName"));
        map.put("categoryName", rs.getString("categoryName"));
        map.put("averageRating", rs.getDouble("averageRating"));
        map.put("imageUrl", rs.getString("imageUrl"));
        return map;
    }
    // Lấy tất cả sản phẩm kèm hình ảnh
    public List<Map<String, Object>> getListWithImage() {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT " +
                                        "  p.id, " +
                                        "  p.product_name AS productName, " +
                                        "  p.description, " +
                                        "  p.category_id AS categoryId, " +
                                        "  p.brand_id AS brandId, " +
                                        "  p.price, " +
                                        "  p.sale_price AS salePrice, " +
                                        "  p.stock_quantity AS stockQuantity, " +
                                        "  p.sold_count AS soldCount, " +
                                        "  p.is_active AS isActive, " +
                                        "  p.created_at AS createdAt, " +
                                        "  p.updated_at AS updatedAt, " +
                                        "  b.brand_name AS brandName, " +
                                        "  c.category_name AS categoryName, " +
                                        "  COALESCE(AVG(pr.rating), 0) AS averageRating, " +
                                        "  pi.image_url AS imageUrl " +
                                        "FROM products p " +
                                        "LEFT JOIN brands b ON p.brand_id = b.id " +
                                        "LEFT JOIN categories c ON p.category_id = c.id " +
                                        "LEFT JOIN product_reviews pr ON p.id = pr.product_id AND pr.status = 1 " +
                                        "LEFT JOIN product_images pi ON p.id = pi.product_id AND pi.is_primary = 1 " +
                                        "WHERE p.is_active = 1 " +
                                        "GROUP BY p.id, p.product_name, p.description, p.category_id, p.brand_id, " +
                                        "         p.price, p.sale_price, p.stock_quantity, p.sold_count, p.is_active, " +
                                        "         p.created_at, p.updated_at, b.brand_name, c.category_name, pi.image_url"
                        )
                        .map((rs, ctx) -> mapProductRow(rs))
                        .list()
        );
    }
     // Lấy danh sách sản phẩm bán chạy nhất
    public List<Map<String, Object>> getBestSellersWithImage(int limit) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT " +
                                        "  p.id, " +
                                        "  p.product_name AS productName, " +
                                        "  p.description, " +
                                        "  p.category_id AS categoryId, " +
                                        "  p.brand_id AS brandId, " +
                                        "  p.price, " +
                                        "  p.sale_price AS salePrice, " +
                                        "  p.stock_quantity AS stockQuantity, " +
                                        "  p.sold_count AS soldCount, " +
                                        "  p.is_active AS isActive, " +
                                        "  p.created_at AS createdAt, " +
                                        "  p.updated_at AS updatedAt, " +
                                        "  b.brand_name AS brandName, " +
                                        "  c.category_name AS categoryName, " +
                                        "  COALESCE(AVG(pr.rating), 0) AS averageRating, " +
                                        "  pi.image_url AS imageUrl " +
                                        "FROM products p " +
                                        "LEFT JOIN brands b ON p.brand_id = b.id " +
                                        "LEFT JOIN categories c ON p.category_id = c.id " +
                                        "LEFT JOIN product_reviews pr ON p.id = pr.product_id AND pr.status = 1 " +
                                        "LEFT JOIN product_images pi ON p.id = pi.product_id AND pi.is_primary = 1 " +
                                        "WHERE p.is_active = 1 " +
                                        "GROUP BY p.id, p.product_name, p.description, p.category_id, p.brand_id, " +
                                        "         p.price, p.sale_price, p.stock_quantity, p.sold_count, p.is_active, " +
                                        "         p.created_at, p.updated_at, b.brand_name, c.category_name, pi.image_url " +
                                        "ORDER BY p.sold_count DESC " +
                                        "LIMIT :limit"
                        )
                        .bind("limit", limit)
                        .map((rs, ctx) -> mapProductRow(rs))
                        .list()
        );
    }
   //Lấy sản phẩm theo category ID
    public List<Map<String, Object>> getByCategoryIdWithImage(int categoryId) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT " +
                                        "  p.id, " +
                                        "  p.product_name AS productName, " +
                                        "  p.description, " +
                                        "  p.category_id AS categoryId, " +
                                        "  p.brand_id AS brandId, " +
                                        "  p.price, " +
                                        "  p.sale_price AS salePrice, " +
                                        "  p.stock_quantity AS stockQuantity, " +
                                        "  p.sold_count AS soldCount, " +
                                        "  p.is_active AS isActive, " +
                                        "  p.created_at AS createdAt, " +
                                        "  p.updated_at AS updatedAt, " +
                                        "  b.brand_name AS brandName, " +
                                        "  c.category_name AS categoryName, " +
                                        "  COALESCE(AVG(pr.rating), 0) AS averageRating, " +
                                        "  pi.image_url AS imageUrl " +
                                        "FROM products p " +
                                        "LEFT JOIN brands b ON p.brand_id = b.id " +
                                        "LEFT JOIN categories c ON p.category_id = c.id " +
                                        "LEFT JOIN product_reviews pr ON p.id = pr.product_id AND pr.status = 1 " +
                                        "LEFT JOIN product_images pi ON p.id = pi.product_id AND pi.is_primary = 1 " +
                                        "WHERE p.is_active = 1 AND p.category_id = :categoryId " +
                                        "GROUP BY p.id, p.product_name, p.description, p.category_id, p.brand_id, " +
                                        "         p.price, p.sale_price, p.stock_quantity, p.sold_count, p.is_active, " +
                                        "         p.created_at, p.updated_at, b.brand_name, c.category_name, pi.image_url"
                        )
                        .bind("categoryId", categoryId)
                        .map((rs, ctx) -> mapProductRow(rs))
                        .list()
        );
    }
    //Lấy sản phẩm theo brand ID
    public List<Map<String, Object>> getByBrandIdWithImage(int brandId) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT " +
                                        "  p.id, " +
                                        "  p.product_name AS productName, " +
                                        "  p.description, " +
                                        "  p.category_id AS categoryId, " +
                                        "  p.brand_id AS brandId, " +
                                        "  p.price, " +
                                        "  p.sale_price AS salePrice, " +
                                        "  p.stock_quantity AS stockQuantity, " +
                                        "  p.sold_count AS soldCount, " +
                                        "  p.is_active AS isActive, " +
                                        "  p.created_at AS createdAt, " +
                                        "  p.updated_at AS updatedAt, " +
                                        "  b.brand_name AS brandName, " +
                                        "  c.category_name AS categoryName, " +
                                        "  COALESCE(AVG(pr.rating), 0) AS averageRating, " +
                                        "  pi.image_url AS imageUrl " +
                                        "FROM products p " +
                                        "LEFT JOIN brands b ON p.brand_id = b.id " +
                                        "LEFT JOIN categories c ON p.category_id = c.id " +
                                        "LEFT JOIN product_reviews pr ON p.id = pr.product_id AND pr.status = 1 " +
                                        "LEFT JOIN product_images pi ON p.id = pi.product_id AND pi.is_primary = 1 " +
                                        "WHERE p.is_active = 1 AND p.brand_id = :brandId " +
                                        "GROUP BY p.id, p.product_name, p.description, p.category_id, p.brand_id, " +
                                        "         p.price, p.sale_price, p.stock_quantity, p.sold_count, p.is_active, " +
                                        "         p.created_at, p.updated_at, b.brand_name, c.category_name, pi.image_url"
                        )
                        .bind("brandId", brandId)
                        .map((rs, ctx) -> mapProductRow(rs))
                        .list()
        );
    }
    //Lấy sản phẩm theo nhiều brand ID có lọc category
    public List<Map<String, Object>> getByBrandIdsWithImage(List<Integer> brandIds, Integer categoryId) {
        if (brandIds == null || brandIds.isEmpty()) {
            return new ArrayList<>();
        }
        return get().withHandle(h -> {
            StringBuilder sql = new StringBuilder(
                    "SELECT " +
                            "  p.id, " +
                            "  p.product_name AS productName, " +
                            "  p.description, " +
                            "  p.category_id AS categoryId, " +
                            "  p.brand_id AS brandId, " +
                            "  p.price, " +
                            "  p.sale_price AS salePrice, " +
                            "  p.stock_quantity AS stockQuantity, " +
                            "  p.sold_count AS soldCount, " +
                            "  p.is_active AS isActive, " +
                            "  p.created_at AS createdAt, " +
                            "  p.updated_at AS updatedAt, " +
                            "  b.brand_name AS brandName, " +
                            "  c.category_name AS categoryName, " +
                            "  COALESCE(AVG(pr.rating), 0) AS averageRating, " +
                            "  pi.image_url AS imageUrl " +
                            "FROM products p " +
                            "LEFT JOIN brands b ON p.brand_id = b.id " +
                            "LEFT JOIN categories c ON p.category_id = c.id " +
                            "LEFT JOIN product_reviews pr ON p.id = pr.product_id AND pr.status = 1 " +
                            "LEFT JOIN product_images pi ON p.id = pi.product_id AND pi.is_primary = 1 " +
                            "WHERE p.is_active = 1 "
            );
            // Thêm điều kiện: brand_id IN (?, ?, ?)
            sql.append("AND p.brand_id IN (");
            for (int i = 0; i < brandIds.size(); i++) {
                sql.append(":brandId").append(i);
                if (i < brandIds.size() - 1) {
                    sql.append(", ");
                }
            }
            sql.append(") ");
            // Thêm điều kiện lọc theo category nếu có
            if (categoryId != null && categoryId > 0) {
                sql.append("AND p.category_id = :categoryId ");
            }
            // GROUP BY để tính rating trung bình
            sql.append(
                    "GROUP BY p.id, p.product_name, p.description, p.category_id, p.brand_id, " +
                            "         p.price, p.sale_price, p.stock_quantity, p.sold_count, p.is_active, " +
                            "         p.created_at, p.updated_at, b.brand_name, c.category_name, pi.image_url"
            );
            // Tạo query từ SQL đã build
            var query = h.createQuery(sql.toString());
            // Bind từng brandId vào câu query
            for (int i = 0; i < brandIds.size(); i++) {
                query.bind("brandId" + i, brandIds.get(i));
            }
            // Bind categoryId
            if (categoryId != null && categoryId > 0) {
                query.bind("categoryId", categoryId);
            }
            // Thực thi query và trả về kết quả
            return query.map((rs, ctx) -> mapProductRow(rs)).list();
        });
    }
  // Lấy sản phẩm theo nhiều brand ID (không lọc category)
    public List<Map<String, Object>> getByBrandIdsWithImage(List<Integer> brandIds) {
        return getByBrandIdsWithImage(brandIds, null);
    }
    //tim kiếm sản phẩm theo từ khóa
    public List<Map<String, Object>> searchWithImage(String keyword) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT " +
                                        "  p.id, " +
                                        "  p.product_name AS productName, " +
                                        "  p.description, " +
                                        "  p.category_id AS categoryId, " +
                                        "  p.brand_id AS brandId, " +
                                        "  p.price, " +
                                        "  p.sale_price AS salePrice, " +
                                        "  p.stock_quantity AS stockQuantity, " +
                                        "  p.sold_count AS soldCount, " +
                                        "  p.is_active AS isActive, " +
                                        "  p.created_at AS createdAt, " +
                                        "  p.updated_at AS updatedAt, " +
                                        "  b.brand_name AS brandName, " +
                                        "  c.category_name AS categoryName, " +
                                        "  COALESCE(AVG(pr.rating), 0) AS averageRating, " +
                                        "  pi.image_url AS imageUrl " +
                                        "FROM products p " +
                                        "LEFT JOIN brands b ON p.brand_id = b.id " +
                                        "LEFT JOIN categories c ON p.category_id = c.id " +
                                        "LEFT JOIN product_reviews pr ON p.id = pr.product_id AND pr.status = 1 " +
                                        "LEFT JOIN product_images pi ON p.id = pi.product_id AND pi.is_primary = 1 " +
                                        "WHERE p.is_active = 1 AND p.product_name LIKE :keyword " +
                                        "GROUP BY p.id, p.product_name, p.description, p.category_id, p.brand_id, " +
                                        "         p.price, p.sale_price, p.stock_quantity, p.sold_count, p.is_active, " +
                                        "         p.created_at, p.updated_at, b.brand_name, c.category_name, pi.image_url"
                        )
                        .bind("keyword", "%" + keyword + "%")
                        .map((rs, ctx) -> mapProductRow(rs))
                        .list()
        );
    }
    // Lấy chi tiết sản phẩm theo ID kèm hình ảnh
    public Map<String, Object> getProductByIdWithImage(int id) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT " +
                                        "  p.id, " +
                                        "  p.product_name AS productName, " +
                                        "  p.description, " +
                                        "  p.category_id AS categoryId, " +
                                        "  p.brand_id AS brandId, " +
                                        "  p.price, " +
                                        "  p.sale_price AS salePrice, " +
                                        "  p.stock_quantity AS stockQuantity, " +
                                        "  p.sold_count AS soldCount, " +
                                        "  p.is_active AS isActive, " +
                                        "  p.created_at AS createdAt, " +
                                        "  p.updated_at AS updatedAt, " +
                                        "  b.brand_name AS brandName, " +
                                        "  c.category_name AS categoryName, " +
                                        "  COALESCE(AVG(pr.rating), 0) AS averageRating, " +
                                        "  pi.image_url AS imageUrl " +
                                        "FROM products p " +
                                        "LEFT JOIN brands b ON p.brand_id = b.id " +
                                        "LEFT JOIN categories c ON p.category_id = c.id " +
                                        "LEFT JOIN product_reviews pr ON p.id = pr.product_id AND pr.status = 1 " +
                                        "LEFT JOIN product_images pi ON p.id = pi.product_id AND pi.is_primary = 1 " +
                                        "WHERE p.id = :id " +
                                        "GROUP BY p.id, p.product_name, p.description, p.category_id, p.brand_id, " +
                                        "         p.price, p.sale_price, p.stock_quantity, p.sold_count, p.is_active, " +
                                        "         p.created_at, p.updated_at, b.brand_name, c.category_name, pi.image_url"
                        )
                        .bind("id", id)
                        .map((rs, ctx) -> mapProductRow(rs))
                        .findOne()
                        .orElse(null)
        );
    }
//Thêm nhiều sản phẩm
    public void insert(List<Product> products) {
        get().useHandle(h -> {
            PreparedBatch batch = h.prepareBatch(
                    "INSERT INTO products (id, product_name, description, category_id, brand_id, price, sale_price, stock_quantity, sold_count, is_active) " +
                            "VALUES (:id, :productName, :description, :categoryId, :brandId, :price, :salePrice, :stockQuantity, :soldCount, :isActive)"
            );
            products.forEach(p -> batch.bindBean(p).add());
            batch.execute();
        });
    }
    // Thêm sản phẩm mới - TRẢ VỀ ID
    public int insertProduct(Product product) {
        return get().withHandle(h ->
                h.createUpdate(
                                "INSERT INTO products (product_name, description, category_id, brand_id, price, sale_price, stock_quantity, sold_count, is_active) " +
                                        "VALUES (:productName, :description, :categoryId, :brandId, :price, :salePrice, :stockQuantity, :soldCount, :isActive)"
                        )
                        .bind("productName", product.getProductName())
                        .bind("description", product.getDescription())
                        .bind("categoryId", product.getCategoryId())
                        .bind("brandId", product.getBrandId())
                        .bind("price", product.getPrice())
                        .bind("salePrice", product.getSalePrice())
                        .bind("stockQuantity", product.getStockQuantity())
                        .bind("soldCount", product.getSoldCount())
                        .bind("isActive", product.getIsActive())
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(Integer.class)
                        .one()
        );
    }
    // Cập nhật sản phẩm
    public void updateProduct(Product product) {
        get().useHandle(h ->
                h.createUpdate(
                                "UPDATE products SET product_name = :productName, description = :description, category_id = :categoryId, " +
                                        "brand_id = :brandId, price = :price, sale_price = :salePrice, stock_quantity = :stockQuantity, " +
                                        "is_active = :isActive WHERE id = :id"
                        )
                        .bindBean(product)
                        .execute()
        );
    }
    // Xóa sản phẩm
    public void deleteProduct(int id) {
        get().useHandle(h ->
                h.createUpdate("DELETE FROM products WHERE id = :id")
                        .bind("id", id)
                        .execute()
        );
    }
// Bật/tắt trạng thái sản phẩm
    public void toggleStatus(int id, boolean isActive) {
        get().useHandle(h ->
                h.createUpdate("UPDATE products SET is_active = :isActive WHERE id = :id")
                        .bind("id", id)
                        .bind("isActive", isActive)
                        .execute()
        );
    }
    // Giảm số lượng tồn kho (khi có đơn hàng)
    public void decreaseStock(int id, int quantity) {
        get().useHandle(h ->
                h.createUpdate("UPDATE products SET stock_quantity = stock_quantity - :quantity, sold_count = sold_count + :quantity WHERE id = :id")
                        .bind("id", id)
                        .bind("quantity", quantity)
                        .execute()
        );
    }
    // Tăng số lượng tồn kho (khi nhập hàng)
    public void increaseStock(int id, int quantity) {
        get().useHandle(h ->
                h.createUpdate("UPDATE products SET stock_quantity = stock_quantity + :quantity WHERE id = :id")
                        .bind("id", id)
                        .bind("quantity", quantity)
                        .execute()
        );
    }
    // Đếm tổng số sản phẩm
    public int count() {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(id) FROM products")
                        .mapTo(Integer.class)
                        .one()
        );
    }
}