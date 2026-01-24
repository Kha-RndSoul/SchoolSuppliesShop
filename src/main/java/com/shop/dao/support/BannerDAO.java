package com.shop.dao.support;

import com.shop.model.Banner;
import java.util.List;
public class BannerDAO extends BaseDao {

    // 1. Lấy danh sách tất cả banner (Dùng cho Admin Dashboard)
    public List<Banner> getAllBanners() {
        return get().withHandle(h ->
                h.createQuery("SELECT id, title, image_url, status FROM banners ORDER BY id DESC")
                        .mapToBean(Banner.class)
                        .list()
        );
    }
    // 2. Lấy danh sách banner đang bật (status = 1) (Dùng cho trang chủ Index.jsp)
    public List<Banner> getActiveBanners() {
        return get().withHandle(h ->
                h.createQuery("SELECT id, title, image_url, status FROM banners WHERE status = 1 ORDER BY id DESC")
                        .mapToBean(Banner.class)
                        .list()
        );
    }
    // 3. Lấy chi tiết 1 banner
    public Banner getBannerById(int id) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, title, image_url, status FROM banners WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(Banner.class)
                        .findOne()
                        .orElse(null)
        );
    }
    // 4. Thêm mới banner
    public boolean insertBanner(Banner banner) {
        int rows = get().withHandle(h ->
                h.createUpdate("INSERT INTO banners (title, image_url, status) VALUES (:title, :imageUrl, :status)")
                        .bindBean(banner)
                        .execute()
        );
        return rows > 0;
    }
    // 5. Cập nhật thông tin banner
    public boolean updateBanner(Banner banner) {
        int rows = get().withHandle(h ->
                h.createUpdate("UPDATE banners SET title = :title, image_url = :imageUrl, status = :status WHERE id = :id")
                        .bindBean(banner)
                        .execute()
        );
        return rows > 0;
    }
    // 6. Cập nhật trạng thái ON/OFF
    public boolean updateStatus(int id, boolean status) {
        int rows = get().withHandle(h ->
                h.createUpdate("UPDATE banners SET status = :status WHERE id = :id")
                        .bind("id", id)
                        .bind("status", status)
                        .execute()
        );
        return rows > 0; // Trả về true nếu có dòng được update
    }
    // 7. Xóa banner
    public boolean deleteBanner(int id) {
        int rows = get().withHandle(h ->
                h.createUpdate("DELETE FROM banners WHERE id = :id")
                        .bind("id", id)
                        .execute()
        );
        return rows > 0;
    }
    // 8. Đếm tổng số banner
    public int count() {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(*) FROM banners")
                        .mapTo(Integer.class)
                        .one()
        );
    }

    // Test nhanh (Optional)
    public static void main(String[] args) {
        BannerDAO dao = new BannerDAO();
        System.out.println("Danh sách banners: " + dao.getAllBanners().size());
    }
}