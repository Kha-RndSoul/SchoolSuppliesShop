package com.shop.services;

import com.shop.dao.support.BannerDAO;
import com.shop.model.Banner;
import java.util.List;

public class BannerService {
    private final BannerDAO bannerDAO;
    public BannerService() {
        this.bannerDAO = new BannerDAO();
    }
    /**
     * Lấy danh sách tất cả banner (Admin)
     */
    public List<Banner> getAllBanners() {
        return bannerDAO.getAllBanners();
    }
    /**
     * Lấy banner theo ID
     */
    public Banner getBannerById(int id) {
        return bannerDAO.getBannerById(id);
    }
    /**
     * Lấy danh sách banner đang active (Trang chủ)
     */
    public List<Banner> getActiveBanners() {
        return bannerDAO.getActiveBanners();
    }
    /**
     * Thêm banner mới
     */
    public boolean createBanner(Banner banner) {
        if (banner == null) {
            throw new IllegalArgumentException("Banner không được null");
        }
        if (banner.getTitle() == null || banner.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Tiêu đề banner không được rỗng");
        }
        if (banner.getImageUrl() == null || banner.getImageUrl().trim().isEmpty()) {
            throw new IllegalArgumentException("URL hình ảnh không được rỗng");
        }
        return bannerDAO.insertBanner(banner);
    }
    /**
     * Cập nhật thông tin banner
     */
    public boolean updateBanner(Banner banner) {
        if (banner == null) {
            throw new IllegalArgumentException("Banner không được null");
        }
        if (banner.getId() <= 0) {
            throw new IllegalArgumentException("ID banner không hợp lệ");
        }
        // Kiểm tra tồn tại trước khi update
        Banner existingBanner = bannerDAO.getBannerById(banner.getId());
        if (existingBanner == null) {
            throw new IllegalArgumentException("Không tìm thấy banner với ID: " + banner.getId());
        }
        if (banner.getTitle() == null || banner.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Tiêu đề banner không được rỗng");
        }
        if (banner.getImageUrl() == null || banner.getImageUrl().trim().isEmpty()) {
            throw new IllegalArgumentException("URL hình ảnh không được rỗng");
        }
        return bannerDAO.updateBanner(banner);
    }
    /**
     * Bật/tắt trạng thái banner (Dùng cho Toggle Switch AJAX)
     */
    public boolean toggleBannerStatus(int id, boolean status) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID banner không hợp lệ");
        }

        return bannerDAO.updateStatus(id, status);
    }
    public boolean deleteBanner(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID banner không hợp lệ");
        }
        return bannerDAO.deleteBanner(id);
    }
/**
 * Đếm tổng số banner
 */
public int getTotalBanners() {
    return bannerDAO.count();
}
    /**
     * Đếm số banner đang active
     */
    public int getActiveBannersCount() {
        return bannerDAO.getActiveBanners().size();
    }
    /**
     * Kiểm tra banner có tồn tại không
     */
    public boolean bannerExists(int id) {
        return bannerDAO.getBannerById(id) != null;
    }
}