package com.shop.services;

import com.shop.dao.product.ProductDAO;
import com.shop.model.Product;
import java.util.List;
import java.util.Map;

public class ProductService {

    private final ProductDAO productDAO;

    public ProductService() {
        this.productDAO = new ProductDAO();
    }
// Lấy tất cả sản phẩm kèm hình ảnh
    public List<Map<String, Object>> getAllProductsWithImage() {
        return productDAO.getListWithImage();
    }
// Lấy sản phẩm bán chạy nhất
    public List<Map<String, Object>> getBestSellers(int limit) {
        if (limit <= 0) throw new IllegalArgumentException("Limit phải lớn hơn 0");
        return productDAO.getBestSellersWithImage(limit);
    }
// Lấy sản phẩm theo thương hiệu
    public List<Map<String, Object>> getByBrand(int brandId) {
        if (brandId <= 0) throw new IllegalArgumentException("Brand ID không hợp lệ");
        return productDAO.getByBrandIdWithImage(brandId);
    }
// Tìm kiếm sản phẩm theo từ khóa
    public List<Map<String, Object>> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) throw new IllegalArgumentException("Từ khóa không được rỗng");
        return productDAO.searchWithImage(keyword.trim());
    }
// Lấy sản phẩm theo ID
    public Map<String, Object> getProductById(int id) {
        if (id <= 0) throw new IllegalArgumentException("Product ID không hợp lệ");
        Map<String, Object> product = productDAO.getProductByIdWithImage(id);
        if (product == null || product.isEmpty()) throw new IllegalArgumentException("Không tìm thấy sản phẩm với ID: " + id);
        return product;
    }
// Tạo sản phẩm mới
    public void createProduct(Product product) {
        validateProduct(product);
        if (product.getStockQuantity() < 0) throw new IllegalArgumentException("Số lượng tồn kho không được âm");
        productDAO.insertProduct(product);
    }
// Cập nhật sản phẩm
    public void updateProduct(Product product) {
        if (product.getId() <= 0) throw new IllegalArgumentException("Product ID không hợp lệ");
        validateProduct(product);
        productDAO.updateProduct(product);
    }
// Xóa sản phẩm
    public void deleteProduct(int id) {
        if (id <= 0) throw new IllegalArgumentException("Product ID không hợp lệ");
        productDAO.deleteProduct(id);
    }
// Bật/Tắt trạng thái sản phẩm
    public void toggleStatus(int id, boolean isActive) {
        if (id <= 0) throw new IllegalArgumentException("Product ID không hợp lệ");
        productDAO.toggleStatus(id, isActive);
    }
// Giảm số lượng tồn kho
    public void decreaseStock(int id, int quantity) {
        if (id <= 0) throw new IllegalArgumentException("Product ID không hợp lệ");
        if (quantity <= 0) throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
        productDAO.decreaseStock(id, quantity);
    }
// Tăng số lượng tồn kho
    public void increaseStock(int id, int quantity) {
        if (id <= 0) throw new IllegalArgumentException("Product ID không hợp lệ");
        if (quantity <= 0) throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
        productDAO.increaseStock(id, quantity);
    }
// Đếm tổng số sản phẩm
    public int getTotalProducts() {
        return productDAO.count();
    }
// Kiểm tra sản phẩm tồn tại
    public boolean productExists(int id) {
        try {
            getProductById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
// Phương thức kiểm tra tính hợp lệ của sản phẩm
    private void validateProduct(Product product) {
        if (product == null) throw new IllegalArgumentException("Product không được null");
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) throw new IllegalArgumentException("Tên sản phẩm không được rỗng");
        if (product.getProductName().length() < 3 || product.getProductName().length() > 200) throw new IllegalArgumentException("Tên sản phẩm phải từ 3-200 ký tự");
        if (product.getCategoryId() <= 0) throw new IllegalArgumentException("Category ID không hợp lệ");
        if (product.getBrandId() <= 0) throw new IllegalArgumentException("Brand ID không hợp lệ");
        if (product.getPrice() <= 0) throw new IllegalArgumentException("Giá sản phẩm phải lớn hơn 0");
        if (product.getSalePrice() < 0) throw new IllegalArgumentException("Giá khuyến mãi không được âm");
        if (product.getSalePrice() > 0 && product.getSalePrice() >= product.getPrice()) throw new IllegalArgumentException("Giá khuyến mãi phải nhỏ hơn giá gốc");
    }
}