package com.shop.services;

import com.shop.dao.product.BrandDAO;
import com.shop.model.Brand;
import java.util.List;

public class BrandService {

    private final BrandDAO brandDAO;

    public BrandService() {
        this.brandDAO = new BrandDAO();
    }
// Lấy tất cả thương hiệu
    public List<Brand> getAllBrands() {
        return brandDAO.getList();
    }
// Lấy thương hiệu theo ID
    public Brand getBrandById(int id) {
        if (id <= 0) throw new IllegalArgumentException("Brand ID không hợp lệ");
        Brand brand = brandDAO.getBrandById(id);
        if (brand == null) throw new IllegalArgumentException("Không tìm thấy thương hiệu với ID: " + id);
        return brand;
    }
// Tạo thương hiệu mới
    public void createBrand(Brand brand) {
        if (brand == null) throw new IllegalArgumentException("Brand không được null");
        if (brand.getBrandName() == null || brand.getBrandName().trim().isEmpty()) throw new IllegalArgumentException("Tên thương hiệu không được rỗng");
        brandDAO.insertBrand(brand);
    }
// Cập nhật thương hiệu
    public void updateBrand(Brand brand) {
        if (brand == null) throw new IllegalArgumentException("Brand không được null");
        if (brand.getId() <= 0) throw new IllegalArgumentException("Brand ID không hợp lệ");
        Brand existing = brandDAO.getBrandById(brand.getId());
        if (existing == null) throw new IllegalArgumentException("Không tìm thấy thương hiệu với ID: " + brand.getId());
        if (brand.getBrandName() == null || brand.getBrandName().trim().isEmpty()) throw new IllegalArgumentException("Tên thương hiệu không được rỗng");
        brandDAO.updateBrand(brand);
    }
// Xóa thương hiệu
    public void deleteBrand(int id) {
        if (id <= 0) throw new IllegalArgumentException("Brand ID không hợp lệ");
        Brand existing = brandDAO.getBrandById(id);
        if (existing == null) throw new IllegalArgumentException("Không tìm thấy thương hiệu với ID:  " + id);
        int productCount = brandDAO.countProducts(id);
        if (productCount > 0) throw new IllegalArgumentException("Không thể xóa thương hiệu đang có " + productCount + " sản phẩm");
        brandDAO.deleteBrand(id);
    }
// Đếm tổng số thương hiệu
    public int getTotalBrands() {
        return brandDAO.count();
    }
// Đếm số sản phẩm trong thương hiệu
    public int getProductCount(int brandId) {
        if (brandId <= 0) throw new IllegalArgumentException("Brand ID không hợp lệ");
        return brandDAO.countProducts(brandId);
    }
// Kiểm tra sự tồn tại của thương hiệu
    public boolean brandExists(int id) {
        try {
            getBrandById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}