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
     * Lấy danh sách tất cả banner
     */
    public List<Banner> getAllBanners() {
        return bannerDAO.getList();
    }

    /**
     * Lấy banner theo ID
     */
    public Banner getBannerById(int id) {
        return bannerDAO.getBannerById(id);
    }

    /**
     * Lấy danh sách banner đang active
     */
    public List<Banner> getActiveBanners() {
        return bannerDAO.getActive();
    }

    /**
     * Thêm banner mới
     */
    public void createBanner(Banner banner) {
        if (banner == null) {
            throw new IllegalArgumentException("Banner không được null");
        }
        if (banner.getTitle() == null || banner.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Tiêu đề banner không được rỗng");
        }
        if (banner.getImageUrl() == null || banner.getImageUrl().trim().isEmpty()) {
            throw new IllegalArgumentException("URL hình ảnh không được rỗng");
        }
        bannerDAO.insertBanner(banner);
    }
    /**
     * Cập nhật thông tin banner
     */
    public void updateBanner(Banner banner) {
        if (banner == null) {
            throw new IllegalArgumentException("Banner không được null");
        }
        if (banner.getId() <= 0) {
            throw new IllegalArgumentException("ID banner không hợp lệ");
        }

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

        bannerDAO.updateBanner(banner);
    }

    /**
     * Bật/tắt trạng thái banner
     */
    public void toggleBannerStatus(int id, boolean status) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID banner không hợp lệ");
        }

        Banner existingBanner = bannerDAO.getBannerById(id);
        if (existingBanner == null) {
            throw new IllegalArgumentException("Không tìm thấy banner với ID: " + id);
        }

        bannerDAO.toggleStatus(id, status);
    }

    /**
     * Xóa banner
     */
    public void deleteBanner(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID banner không hợp lệ");
        }

        Banner existingBanner = bannerDAO.getBannerById(id);
        if (existingBanner == null) {
            throw new IllegalArgumentException("Không tìm thấy banner với ID: " + id);
        }

        bannerDAO.deleteBanner(id);
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
        return bannerDAO.countActive();
    }

    /**
     * Kiểm tra banner có tồn tại không
     */
    public boolean bannerExists(int id) {
        return bannerDAO.getBannerById(id) != null;
    }

    /**
     * Kích hoạt banner
     */
    public void activateBanner(int id) {
        toggleBannerStatus(id, true);
    }

    /**
     * Vô hiệu hóa banner
     */
    public void deactivateBanner(int id) {
        toggleBannerStatus(id, false);
    }
}