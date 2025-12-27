package com.shop.services;

import com.shop.dao.product.ProductImageDAO;
import com.shop.model.ProductImage;
import java.util.List;

public class ProductImageService {

    private final ProductImageDAO productImageDAO;

    public ProductImageService() {
        this.productImageDAO = new ProductImageDAO();
    }
// Lấy tất cả hình ảnh của sản phẩm theo ID sản phẩm
    public List<ProductImage> getImagesByProductId(int productId) {
        if (productId <= 0) throw new IllegalArgumentException("Product ID không hợp lệ");
        return productImageDAO.getByProductId(productId);
    }
// Thêm hình ảnh mới cho sản phẩm
    public void addImage(ProductImage image) {
        if (image == null) throw new IllegalArgumentException("ProductImage không được null");
        if (image.getProductId() <= 0) throw new IllegalArgumentException("Product ID không hợp lệ");
        if (image.getImageUrl() == null || image.getImageUrl().trim().isEmpty()) throw new IllegalArgumentException("URL hình ảnh không được rỗng");
        if (image.isPrimary()) {
            productImageDAO.clearPrimaryFlag(image.getProductId());
        }
        productImageDAO.insertImage(image);
    }
// Cập nhật thông tin hình ảnh sản phẩm
    public void updateImage(ProductImage image) {
        if (image == null) throw new IllegalArgumentException("ProductImage không được null");
        if (image.getId() <= 0) throw new IllegalArgumentException("Image ID không hợp lệ");
        if (image.getImageUrl() == null || image.getImageUrl().trim().isEmpty()) throw new IllegalArgumentException("URL hình ảnh không được rỗng");
        if (image.isPrimary()) {
            productImageDAO.clearPrimaryFlag(image.getProductId());
        }
        productImageDAO.updateImage(image);
    }
// Đặt hình ảnh chính cho sản phẩm
    public void setPrimaryImage(int imageId, int productId) {
        if (imageId <= 0) throw new IllegalArgumentException("Image ID không hợp lệ");
        if (productId <= 0) throw new IllegalArgumentException("Product ID không hợp lệ");
        productImageDAO.clearPrimaryFlag(productId);
        productImageDAO.togglePrimary(imageId, true);
    }
// Xóa hình ảnh sản phẩm
    public void deleteImage(int id) {
        if (id <= 0) throw new IllegalArgumentException("Image ID không hợp lệ");
        productImageDAO.deleteImage(id);
    }
// Đếm tổng số hình ảnh của sản phẩm
    public int getImageCount(int productId) {
        if (productId <= 0) throw new IllegalArgumentException("Product ID không hợp lệ");
        return productImageDAO.countByProduct(productId);
    }
}