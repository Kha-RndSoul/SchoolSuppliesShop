package com.shop.dao.support;

import com. shop.model.Admin;
import org.jdbi.v3.core.statement.PreparedBatch;

import java.util.*;

public class AdminDAO extends BaseDao {

    static Map<Integer, Admin> data = new HashMap<>();
    static {
        data. put(1, new Admin(1, "admin", "admin@shop.com", "admin123", "Quản Trị Viên", "SUPER_ADMIN", true,null));
        data.put(2, new Admin(2, "manager", "manager@shop.com", "manager123", "Nguyễn Văn Manager", "MANAGER", true,null));
        data.put(3, new Admin(3, "staff", "staff@shop.com", "staff123", "Trần Thị Staff", "STAFF", true,null));
    }

    public List<Admin> getListAdmin() {
        return new ArrayList<>(data.values());
    }

    public Admin getAdmin(int id) {
        return data.get(id);
    }

    public List<Admin> getList() {
        return get().withHandle(h ->
                h.createQuery("SELECT id, username, email, password, full_name, role, is_active, created_at FROM admins ORDER BY created_at DESC")
                        .mapToBean(Admin.class)
                        .list()
        );
    }

    public Admin getAdminById(int id) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, username, email, password, full_name, role, is_active, created_at FROM admins WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(Admin.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public Admin getByUsername(String username) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, username, email, password, full_name, role, is_active, created_at FROM admins WHERE username = :username")
                        .bind("username", username)
                        .mapToBean(Admin.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public Admin getByEmail(String email) {
        return get().withHandle(h ->
                h. createQuery("SELECT id, username, email, password, full_name, role, is_active, created_at FROM admins WHERE email = : email")
                        .bind("email", email)
                        .mapToBean(Admin.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public Admin login(String username, String password) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, username, email, password, full_name, role, is_active, created_at FROM admins WHERE username = :username AND password = :password AND is_active = 1")
                        .bind("username", username)
                        .bind("password", password)
                        .mapToBean(Admin.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public List<Admin> getByRole(String role) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, username, email, password, full_name, role, is_active, created_at FROM admins WHERE role = :role ORDER BY created_at DESC")
                        .bind("role", role)
                        .mapToBean(Admin.class)
                        .list()
        );
    }

    public void insert(List<Admin> admins) {
        get().useHandle(h -> {
            PreparedBatch batch = h.prepareBatch(
                    "INSERT INTO admins (id, username, email, password, full_name, role, is_active) VALUES (:id, :username, :email, :password, :fullName, :role, :isActive)"
            );
            admins.forEach(a -> batch. bindBean(a).add());
            batch.execute();
        });
    }

    public void insertAdmin(Admin admin) {
        get().useHandle(h -> {
            h.createUpdate("INSERT INTO admins (username, email, password, full_name, role, is_active) VALUES (:username, :email, :password, : fullName, :role, :isActive)")
                    .bindBean(admin)
                    .execute();
        });
    }

    public void updateAdmin(Admin admin) {
        get().useHandle(h -> {
            h.createUpdate("UPDATE admins SET email = :email, full_name = :fullName, role = :role, is_active = :isActive WHERE id = :id")
                    .bindBean(admin)
                    .execute();
        });
    }

    public void updatePassword(int id, String password) {
        get().useHandle(h -> {
            h. createUpdate("UPDATE admins SET password = :password WHERE id = :id")
                    .bind("id", id)
                    .bind("password", password)
                    .execute();
        });
    }

    public void toggleActive(int id, boolean isActive) {
        get().useHandle(h -> {
            h.createUpdate("UPDATE admins SET is_active = :isActive WHERE id = :id")
                    .bind("id", id)
                    .bind("isActive", isActive)
                    .execute();
        });
    }

    public void deleteAdmin(int id) {
        get().useHandle(h -> {
            h.createUpdate("DELETE FROM admins WHERE id = :id")
                    .bind("id", id)
                    .execute();
        });
    }

    public int countActive() {
        return get().withHandle(h ->
                h. createQuery("SELECT COUNT(*) FROM admins WHERE is_active = 1")
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public static void main(String[] args) {
        AdminDAO dao = new AdminDAO();
        List<Admin> admins = dao.getListAdmin();
        dao.insert(admins);
        System.out.println(" Inserted " + admins.size() + " admins");

        Admin admin = dao.login("admin", "admin123");
        System.out.println(admin != null ? " Login success:  " + admin :  " Login failed");
    }
}