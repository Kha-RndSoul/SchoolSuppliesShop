package com.shop.services;

import com.shop.dao.product.ProductDAO;
import com.shop.model.Product;
import java.util.*;

public class ProductService {

    private ProductDAO productDAO;
    public ProductService() {
        this.productDAO = new ProductDAO();
    }
    /**
     * Lấy tất cả sản phẩm kèm hình ảnh
     * @return Danh sách tất cả sản phẩm đang active
     */
    public List<Map<String, Object>> getAllProductsWithImage() {
        return productDAO.getListWithImage();
    }
    /**
     * Lấy sản phẩm bán chạy nhất
     * @param limit Số lượng sản phẩm cần lấy
     * @return Danh sách sản phẩm best seller
     */
    public List<Map<String, Object>> getBestSellers(int limit) {
        return productDAO.getBestSellersWithImage(limit);
    }
    /**
     * Lấy sản phẩm theo category ID
     * @param categoryId ID của danh mục
     * @return Danh sách sản phẩm thuộc danh mục
     */
    public List<Map<String, Object>> getByCategory(int categoryId) {
        return productDAO.getByCategoryIdWithImage(categoryId);
    }
    /**
     * Lấy sản phẩm theo 1 brand ID
     * @param brandId ID của thương hiệu
     * @return Danh sách sản phẩm thuộc thương hiệu
     */
    public List<Map<String, Object>> getByBrand(int brandId) {
        return productDAO.getByBrandIdWithImage(brandId);
    }
    /**
     * Lấy sản phẩm theo nhiều brand ID
     * Sử dụng khi người dùng chọn nhiều checkbox brand
     * @param brandIds Danh sách các brand ID được chọn
     * @return Danh sách sản phẩm thuộc các brand đã chọn
     */
    public List<Map<String, Object>> getByBrandIds(List<Integer> brandIds) {
        // Kiểm tra danh sách có rỗng không
        if (brandIds == null || brandIds.isEmpty()) {
            return new ArrayList<>();
        }
        return productDAO.getByBrandIdsWithImage(brandIds);
    }
    /**
     * Lấy sản phẩm theo NHIỀU brand ID VÀ category ID
     * Sử dụng khi người dùng:
     * - Đang xem 1 category
     * - Và chọn thêm bộ lọc brand
     * @param brandIds Danh sách các brand ID được chọn
     * @param categoryId ID của category đang xem
     * @return Danh sách sản phẩm thuộc các brand VÀ category
     */
    public List<Map<String, Object>> getByBrandIdsAndCategory(List<Integer> brandIds, Integer categoryId) {
        if (brandIds == null || brandIds.isEmpty()) {
            return new ArrayList<>();
        }
        return productDAO.getByBrandIdsWithImage(brandIds, categoryId);
    }
    /**
     * Tìm kiếm sản phẩm theo từ khóa
     * @param keyword Từ khóa tìm kiếm
     * @return Danh sách sản phẩm có tên chứa từ khóa
     */
    public List<Map<String, Object>> search(String keyword) {
        // Kiểm tra keyword có rỗng không
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return productDAO.searchWithImage(keyword);
    }
    /**
     * Lấy chi tiết 1 sản phẩm theo ID
     * @param id ID của sản phẩm
     * @return Thông tin chi tiết sản phẩm
     */
    public Map<String, Object> getProductById(int id) {
        return productDAO.getProductByIdWithImage(id);
    }

  //Thêm sản phẩm mới
    public void addProduct(Product product) {
        productDAO.insertProduct(product);
    }
    //Cập nhật sản phẩm
    public void updateProduct(Product product) {
        productDAO.updateProduct(product);
    }
    //Xóa sản phẩm
    public void deleteProduct(int id) {
        productDAO.deleteProduct(id);
    }
    /**
     * Bật/tắt trạng thái sản phẩm
     * @param id ID của sản phẩm
     * @param isActive Trạng thái mới (true = active, false = inactive)
     */
    public void toggleStatus(int id, boolean isActive) {
        productDAO.toggleStatus(id, isActive);
    }
    /**
     * Giảm số lượng tồn kho (khi có đơn hàng)
     * @param id ID của sản phẩm
     * @param quantity Số lượng cần giảm
     */
    public void decreaseStock(int id, int quantity) {
        productDAO.decreaseStock(id, quantity);
    }
    /**
     * Tăng số lượng tồn kho (khi nhập hàng)
     * @param id ID của sản phẩm
     * @param quantity Số lượng cần tăng
     */
    public void increaseStock(int id, int quantity) {
        productDAO.increaseStock(id, quantity);
    }
    /**
     * Đếm tổng số sản phẩm
     * @return Số lượng sản phẩm trong database
     */
    public int countProducts() {
        return productDAO.count();
    }
}