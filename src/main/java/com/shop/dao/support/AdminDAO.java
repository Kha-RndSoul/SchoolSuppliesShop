package com.shop.dao.support;

import com.shop.model.Admin;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org. jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi. v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi. v3.sqlobject.statement.SqlUpdate;

import java. util.List;
import java. util.Optional;

@RegisterBeanMapper(Admin.class)
public interface AdminDAO {

    //Danh sách tất cả admin
    @SqlQuery("SELECT id, username, email, password, full_name, role, is_active, created_at " +
            "FROM admins ORDER BY created_at DESC")
    List<Admin> getAll();

    // Danh sách admin theo id
    @SqlQuery("SELECT id, username, email, password, full_name, role, is_active, created_at " +
            "FROM admins WHERE id = :id")
    Optional<Admin> getById(@Bind("id") int id);

    // Danh sách admin theo username
    @SqlQuery("SELECT id, username, email, password, full_name, role, is_active, created_at " +
            "FROM admins WHERE username = :username")
    Optional<Admin> getByUsername(@Bind("username") String username);

    // Danh sách admin theo email
    @SqlQuery("SELECT id, username, email, password, full_name, role, is_active, created_at " +
            "FROM admins WHERE email = :email")
    Optional<Admin> getByEmail(@Bind("email") String email);

    // Đăng nhập
    @SqlQuery("SELECT id, username, email, password, full_name, role, is_active, created_at " +
            "FROM admins WHERE username = :username AND password = :password AND is_active = 1")
    Optional<Admin> login(@Bind("username") String username, @Bind("password") String password);

    // Insert
    @SqlUpdate("INSERT INTO admins (username, email, password, full_name, role, is_active) " +
            "VALUES (:username, : email, :password, :fullName, :role, : isActive)")
    @GetGeneratedKeys
    int insert(@BindBean Admin admin);

    // Update
    @SqlUpdate("UPDATE admins SET email = :email, full_name = :fullName, role = :role, is_active = :isActive " +
            "WHERE id = : id")
    void update(@BindBean Admin admin);

    // Update mật khẩu
    @SqlUpdate("UPDATE admins SET password = :password WHERE id = :id")
    void updatePassword(@Bind("id") int id, @Bind("password") String password);

    // Thay đổi trạng thái
    @SqlUpdate("UPDATE admins SET is_active = :isActive WHERE id = :id")
    void toggleActive(@Bind("id") int id, @Bind("isActive") boolean isActive);

    // Delete
    @SqlUpdate("DELETE FROM admins WHERE id = :id")
    void delete(@Bind("id") int id);

    // Danh sách admin theo vai trò
    @SqlQuery("SELECT id, username, email, password, full_name, role, is_active, created_at " +
            "FROM admins WHERE role = :role ORDER BY created_at DESC")
    List<Admin> getByRole(@Bind("role") String role);

    // Đếm số lượng admin(đang active)
    @SqlQuery("SELECT COUNT(*) FROM admins WHERE is_active = 1")
    int countActive();
}
