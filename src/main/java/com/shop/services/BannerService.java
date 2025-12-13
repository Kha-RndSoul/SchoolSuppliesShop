package com.shop.services;

import com.shop.dao.support.BannerDAO;
import com.shop.model.Banner;
import com.shop.util.JdbiConnection;
import com.shop.util.ValidationUtil;
import org.jdbi.v3.core. Jdbi;

import java.util.List;
import java.util.Optional;


public class BannerService {

    private final Jdbi jdbi;
    private final BannerDAO bannerDAO;


    public BannerService() {
        this.jdbi = JdbiConnection.getJdbi();
        this.bannerDAO = jdbi.onDemand(BannerDAO.class);
    }

    //Lấy tất cả banners
    public List<Banner> getAll() {
        return bannerDAO.getAll();
    }

    //Lấy banner theo ID
    public Optional<Banner> getById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Banner ID phải lớn hơn 0");
        }
        return bannerDAO.getById(id);
    }

    //Lấy tất cả banners đang active
    public List<Banner> getActive() {
        return bannerDAO.getActive();
    }

    //Thêm banner mới
    public int create(Banner banner) {
        // Validate title
        if (ValidationUtil.isEmpty(banner.getTitle())) {
            throw new IllegalArgumentException("Tiêu đề banner không được để trống");
        }

        if (! ValidationUtil.isValidLength(banner.getTitle(), 3, 200)) {
            throw new IllegalArgumentException("Tiêu đề banner phải từ 3-200 ký tự");
        }

        // Validate image URL
        if (ValidationUtil.isEmpty(banner.getImageUrl())) {
            throw new IllegalArgumentException("Ảnh banner không được để trống");
        }

        if (!ValidationUtil.isValidUrl(banner. getImageUrl())) {
            throw new IllegalArgumentException("URL ảnh banner không hợp lệ");
        }

        // Làm sạch dữ liệu đầu vào
        banner.setTitle(ValidationUtil.sanitize(banner.getTitle()));
        banner.setImageUrl(ValidationUtil.sanitize(banner.getImageUrl()));

        // Insert vào database
        return bannerDAO.insert(banner);
    }


    // Cập nhật banner (Admin)
    public void update(Banner banner) {
        // Validate ID
        if (banner.getBannerId() <= 0) {
            throw new IllegalArgumentException("Banner ID không hợp lệ");
        }

        // Check banner tồn tại
        Optional<Banner> existing = bannerDAO.getById(banner.getBannerId());
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Banner không tồn tại");
        }

        // Validate title
        if (ValidationUtil.isEmpty(banner.getTitle())) {
            throw new IllegalArgumentException("Tiêu đề banner không được để trống");
        }

        if (!ValidationUtil.isValidLength(banner.getTitle(), 3, 200)) {
            throw new IllegalArgumentException("Tiêu đề banner phải từ 3-200 ký tự");
        }

        // Kiểm tra URL ảnh
        if (ValidationUtil.isEmpty(banner.getImageUrl())) {
            throw new IllegalArgumentException("Ảnh banner không được để trống");
        }

        if (!ValidationUtil.isValidUrl(banner.getImageUrl())) {
            throw new IllegalArgumentException("URL ảnh banner không hợp lệ");
        }

        // Làm sạch dữ liệu đầu vào
        banner.setTitle(ValidationUtil.sanitize(banner.getTitle()));
        banner.setImageUrl(ValidationUtil. sanitize(banner.getImageUrl()));

        // Update DB
        bannerDAO.update(banner);
    }

    // Thay đổi trạng thái banner (Admin)
    public void toggleStatus(int id, boolean status) {
        // Validate ID
        if (id <= 0) {
            throw new IllegalArgumentException("Banner ID phải lớn hơn 0");
        }

        // Check banner tồn tại
        Optional<Banner> existing = bannerDAO.getById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Banner không tồn tại");
        }

        // Update trạng thái
        bannerDAO.toggleStatus(id, status);
    }

    // Xóa banner (Admin)
    public void delete(int id) {
        // Validate ID
        if (id <= 0) {
            throw new IllegalArgumentException("Banner ID phải lớn hơn 0");
        }

        // Check banner tồn tại
        Optional<Banner> existing = bannerDAO.getById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Banner không tồn tại");
        }

        // Xóa banner
        bannerDAO.delete(id);
    }


    // Đếm số banners
    public int count() {
        return bannerDAO.count();
    }

    // Đếm số banners đang active
    public int countActive() {
        return bannerDAO.countActive();
    }
}