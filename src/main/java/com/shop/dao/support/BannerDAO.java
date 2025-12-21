package com.shop.dao. support;

import com.shop.model.Banner;
import org.jdbi.v3.core.statement.PreparedBatch;

import java.util.*;

public class BannerDAO extends BaseDao {

    static Map<Integer, Banner> data = new HashMap<>();
    static {
        data.put(1, new Banner(1, "Khuyến mãi mùa hè", "/images/banners/summer-sale.jpg", true));
        data.put(2, new Banner(2, "Giảm giá 50% toàn bộ balo", "/images/banners/balo-sale.jpg", true));
        data.put(3, new Banner(3, "Vở mới về - Giá tốt", "/images/banners/notebook-promo.jpg", true));
        data.put(4, new Banner(4, "Back to School 2024", "/images/banners/back-to-school.jpg", false));
    }

    public List<Banner> getListBanner() {
        return new ArrayList<>(data.values());
    }

    public Banner getBanner(int id) {
        return data.get(id);
    }

    public List<Banner> getList() {
        return get().withHandle(h ->
                h.createQuery("SELECT id, title, image_url, status FROM banners")
                        .mapToBean(Banner.class)
                        .list()
        );
    }

    public Banner getBannerById(int id) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, title, image_url, status FROM banners WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(Banner.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public List<Banner> getActive() {
        return get().withHandle(h ->
                h. createQuery("SELECT id, title, image_url, status FROM banners WHERE status = 1")
                        .mapToBean(Banner.class)
                        .list()
        );
    }

    public void insert(List<Banner> banners) {
        get().useHandle(h -> {
            PreparedBatch batch = h.prepareBatch(
                    "INSERT INTO banners (id, title, image_url, status) VALUES (:id, :title, :imageUrl, :status)"
            );
            banners.forEach(b -> batch.bindBean(b).add());
            batch.execute();
        });
    }

    public void insertBanner(Banner banner) {
        get().useHandle(h -> {
            h.createUpdate("INSERT INTO banners (title, image_url, status) VALUES (:title, :imageUrl, : status)")
                    .bindBean(banner)
                    .execute();
        });
    }

    public void updateBanner(Banner banner) {
        get().useHandle(h -> {
            h.createUpdate("UPDATE banners SET title = :title, image_url = : imageUrl, status = :status WHERE id = :id")
                    .bindBean(banner)
                    .execute();
        });
    }

    public void toggleStatus(int id, boolean status) {
        get().useHandle(h -> {
            h.createUpdate("UPDATE banners SET status = :status WHERE id = :id")
                    .bind("id", id)
                    .bind("status", status)
                    .execute();
        });
    }

    public void deleteBanner(int id) {
        get().useHandle(h -> {
            h.createUpdate("DELETE FROM banners WHERE id = : id")
                    .bind("id", id)
                    .execute();
        });
    }

    public int count() {
        return get().withHandle(h ->
                h. createQuery("SELECT COUNT(*) FROM banners")
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public int countActive() {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(*) FROM banners WHERE status = 1")
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public static void main(String[] args) {
        BannerDAO dao = new BannerDAO();
        List<Banner> banners = dao.getListBanner();
        dao.insert(banners);
        System.out.println(" Inserted " + banners.size() + " banners");

        dao.getActive().forEach(System.out::println);
    }
}