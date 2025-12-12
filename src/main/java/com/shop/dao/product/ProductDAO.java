package com.shop.dao.product;

import com.shop.model.Product;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org. jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject. statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(Product.class)
public interface ProductDAO {

    // Danh sách tất cả sản phẩm
    @SqlQuery("SELECT product_id, product_name, description, category_id, brand_id, price, sale_price, stock_quantity, sold_count, image_url, is_active, created_at, updated_at FROM products")
    List<Product> getAll();

    // Danh sách sản phẩm theo id
    @SqlQuery("SELECT product_id, product_name, description, category_id, brand_id, price, sale_price, stock_quantity, sold_count, image_url, is_active, created_at, updated_at FROM products WHERE product_id = :id")
    Optional<Product> getById(@Bind("id") int id);

    // Danh sách sản phẩm đang hoạt động
    @SqlQuery("SELECT product_id, product_name, description, category_id, brand_id, price, sale_price, stock_quantity, sold_count, image_url, is_active, created_at, updated_at FROM products WHERE is_active = 1")
    List<Product> getActive();

    // Danh sách sản phẩm theo danh mục
    @SqlQuery("SELECT product_id, product_name, description, category_id, brand_id, price, sale_price, stock_quantity, sold_count, image_url, is_active, created_at, updated_at FROM products WHERE category_id = :categoryId")
    List<Product> getByCategoryId(@Bind("categoryId") int categoryId);

    // Danh sách sản phẩm theo thương hiệu
    @SqlQuery("SELECT product_id, product_name, description, category_id, brand_id, price, sale_price, stock_quantity, sold_count, image_url, is_active, created_at, updated_at FROM products WHERE brand_id = : brandId")
    List<Product> getByBrandId(@Bind("brandId") int brandId);

    // Tìm kiếm sản phẩm
    @SqlQuery("SELECT product_id, product_name, description, category_id, brand_id, price, sale_price, stock_quantity, sold_count, image_url, is_active, created_at, updated_at FROM products WHERE product_name LIKE CONCAT('%', :keyword, '%') OR description LIKE CONCAT('%', :keyword, '%')")
    List<Product> search(@Bind("keyword") String keyword);

    // Lọc theo khoảng giá
    @SqlQuery("SELECT product_id, product_name, description, category_id, brand_id, price, sale_price, stock_quantity, sold_count, image_url, is_active, created_at, updated_at FROM products WHERE price BETWEEN :minPrice AND :maxPrice")
    List<Product> getByPriceRange(@Bind("minPrice") double minPrice, @Bind("maxPrice") double maxPrice);

    // Sản phẩm bán chạy
    @SqlQuery("SELECT product_id, product_name, description, category_id, brand_id, price, sale_price, stock_quantity, sold_count, image_url, is_active, created_at, updated_at FROM products ORDER BY sold_count DESC LIMIT : limit")
    List<Product> getBestSellers(@Bind("limit") int limit);

    // Insert
    @SqlUpdate("INSERT INTO products (product_name, description, category_id, brand_id, price, sale_price, stock_quantity, image_url, is_active) VALUES (:productName, :description, :categoryId, :brandId, :price, :salePrice, :stockQuantity, :imageUrl, :isActive)")
    @GetGeneratedKeys
    int insert(@BindBean Product product);

    // Update
    @SqlUpdate("UPDATE products SET product_name = :productName, description = : description, category_id = :categoryId, brand_id = :brandId, price = :price, sale_price = :salePrice, stock_quantity = :stockQuantity, image_url = :imageUrl, is_active = :isActive WHERE product_id = :productId")
    void update(@BindBean Product product);

    // Thay đổi trạng thái
    @SqlUpdate("UPDATE products SET is_active = :isActive WHERE product_id = :id")
    void toggleStatus(@Bind("id") int id, @Bind("isActive") boolean isActive);

    // Giảm tồn kho và tăng sold_count
    @SqlUpdate("UPDATE products SET stock_quantity = stock_quantity - :quantity, sold_count = sold_count + :quantity WHERE product_id = :id")
    void decreaseStock(@Bind("id") int id, @Bind("quantity") int quantity);

    // Tăng tồn kho
    @SqlUpdate("UPDATE products SET stock_quantity = stock_quantity + :quantity WHERE product_id = :id")
    void increaseStock(@Bind("id") int id, @Bind("quantity") int quantity);

    // Delete
    @SqlUpdate("DELETE FROM products WHERE product_id = :id")
    void delete(@Bind("id") int id);

    // Đếm tổng số sản phẩm
    @SqlQuery("SELECT COUNT(product_id) FROM products")
    int count();

    // Đếm sản phẩm đang hoạt động
    @SqlQuery("SELECT COUNT(product_id) FROM products WHERE is_active = 1")
    int countActive();

    // Kiểm tra tồn kho
    @SqlQuery("SELECT stock_quantity FROM products WHERE product_id = :id")
    int getStockQuantity(@Bind("id") int id);
}
