package com.shop.dao.support;

import com.shop.model.Banner;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org. jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi. v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi. v3.sqlobject.statement.SqlUpdate;

import java. util.List;
import java. util.Optional;

@RegisterBeanMapper(Banner.class)
public interface BannerDAO {

    // Danh sách tất cả banner
    @SqlQuery("SELECT id, title, image_url, status FROM banners")
    List<Banner> getAll();

    // Danh sách banner theo id
    @SqlQuery("SELECT id, title, image_url, status FROM banners WHERE id = : id")
    Optional<Banner> getById(@Bind("id") int id);

    // Danh sách banner đang hoạt động
    @SqlQuery("SELECT id, title, image_url, status FROM banners WHERE status = 1")
    List<Banner> getActive();

    // Insert
    @SqlUpdate("INSERT INTO banners (title, image_url, status) VALUES (:title, :imageUrl, : status)")
    @GetGeneratedKeys
    int insert(@BindBean Banner banner);

    // Update
    @SqlUpdate("UPDATE banners SET title = :title, image_url = :imageUrl, status = :status WHERE id = :id")
    void update(@BindBean Banner banner);

    // Thay đổi trạng thái
    @SqlUpdate("UPDATE banners SET status = :status WHERE id = :id")
    void toggleStatus(@Bind("id") int id, @Bind("status") boolean status);

    // Delete
    @SqlUpdate("DELETE FROM banners WHERE id = :id")
    void delete(@Bind("id") int id);

    // Đếm số lượng banner
    @SqlQuery("SELECT COUNT(*) FROM banners")
    int count();

    // Đếm số lượng banner đang hoạt động
    @SqlQuery("SELECT COUNT(*) FROM banners WHERE status = 1")
    int countActive();
}
