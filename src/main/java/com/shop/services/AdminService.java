package com.shop.services;

import com.shop.dao.support.AdminDAO;
import com.shop.model.Admin;

import java.util.List;

public class AdminService {

    private final AdminDAO adminDAO;

    public AdminService() {
        this.adminDAO = new AdminDAO();
    }

    /**
     * Lấy danh sách tất cả admin
     */
    public List<Admin> getAllAdmins() {
        return adminDAO.getList();
    }

    /**
     * Lấy admin theo ID
     */
    public Admin getAdminById(int id) {
        return adminDAO.getAdminById(id);
    }

    /**
     * Lấy admin theo username
     */
    public Admin getAdminByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username không được rỗng");
        }
        return adminDAO.getByUsername(username);
    }

    /**
     * Lấy admin theo email
     */
    public Admin getAdminByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email không được rỗng");
        }
        return adminDAO.getByEmail(email);
    }

    /**
     * Lấy danh sách admin theo vai trò
     */
    public List<Admin> getAdminsByRole(String role) {
        if (role == null || role.trim().isEmpty()) {
            throw new IllegalArgumentException("Vai trò không được rỗng");
        }
        if (!isValidRole(role)) {
            throw new IllegalArgumentException("Vai trò không hợp lệ. Chỉ chấp nhận: SUPER_ADMIN, MANAGER, STAFF");
        }
        return adminDAO.getByRole(role);
    }

    /**
     * Đăng nhập admin
     */
    public Admin login(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username không được rỗng");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không được rỗng");
        }

        Admin admin = adminDAO.login(username, password);
        if (admin == null) {
            throw new IllegalArgumentException("Username hoặc mật khẩu không đúng hoặc tài khoản đã bị khóa");
        }

        return admin;
    }

    /**
     * Tạo admin mới
     */
    public void createAdmin(Admin admin) {
        if (admin == null) {
            throw new IllegalArgumentException("Thông tin admin không được null");
        }
        if (admin.getUsername() == null || admin.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username không được rỗng");
        }
        if (admin.getEmail() == null || admin.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email không được rỗng");
        }
        if (!isValidEmail(admin.getEmail())) {
            throw new IllegalArgumentException("Email không hợp lệ");
        }
        if (admin.getPasswordHash() == null || admin.getPasswordHash().trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không được rỗng");
        }
        if (admin.getFullName() == null || admin.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Họ tên không được rỗng");
        }
        if (admin.getRole() == null || !isValidRole(admin.getRole())) {
            throw new IllegalArgumentException("Vai trò không hợp lệ");
        }

        // Kiểm tra username đã tồn tại
        if (adminDAO.getByUsername(admin.getUsername()) != null) {
            throw new IllegalArgumentException("Username đã tồn tại");
        }

        // Kiểm tra email đã tồn tại
        if (adminDAO.getByEmail(admin.getEmail()) != null) {
            throw new IllegalArgumentException("Email đã được sử dụng");
        }

        adminDAO.insertAdmin(admin);
    }

    /**
     * Thêm nhiều admin cùng lúc
     */
    public void createAdmins(List<Admin> admins) {
        if (admins == null || admins.isEmpty()) {
            throw new IllegalArgumentException("Danh sách admin không được rỗng");
        }
        adminDAO.insert(admins);
    }

    /**
     * Cập nhật thông tin admin
     */
    public void updateAdmin(Admin admin) {
        if (admin == null) {
            throw new IllegalArgumentException("Thông tin admin không được null");
        }
        if (admin.getId() <= 0) {
            throw new IllegalArgumentException("ID admin không hợp lệ");
        }

        Admin existingAdmin = adminDAO.getAdminById(admin.getId());
        if (existingAdmin == null) {
            throw new IllegalArgumentException("Không tìm thấy admin với ID: " + admin.getId());
        }

        if (admin.getEmail() == null || admin.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email không được rỗng");
        }
        if (!isValidEmail(admin.getEmail())) {
            throw new IllegalArgumentException("Email không hợp lệ");
        }
        if (admin.getFullName() == null || admin.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Họ tên không được rỗng");
        }
        if (admin.getRole() == null || !isValidRole(admin.getRole())) {
            throw new IllegalArgumentException("Vai trò không hợp lệ");
        }

        // Kiểm tra email mới có trùng với admin khác không
        Admin adminWithEmail = adminDAO.getByEmail(admin.getEmail());
        if (adminWithEmail != null && adminWithEmail.getId() != admin.getId()) {
            throw new IllegalArgumentException("Email đã được sử dụng bởi admin khác");
        }

        adminDAO.updateAdmin(admin);
    }

    /**
     * Cập nhật mật khẩu admin
     */
    public void updatePassword(int id, String oldPasswordHash, String newPasswordHash) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID admin không hợp lệ");
        }
        if (newPasswordHash == null || newPasswordHash.trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu mới không được rỗng");
        }

        Admin existingAdmin = adminDAO.getAdminById(id);
        if (existingAdmin == null) {
            throw new IllegalArgumentException("Không tìm thấy admin với ID: " + id);
        }

        // Kiểm tra mật khẩu cũ
        if (!existingAdmin.getPasswordHash().equals(oldPasswordHash)) {
            throw new IllegalArgumentException("Mật khẩu cũ không đúng");
        }

        adminDAO.updatePassword(id, newPasswordHash);
    }

    /**
     * Đặt lại mật khẩu (dùng cho SUPER_ADMIN)
     */
    public void resetPassword(int id, String newPasswordHash) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID admin không hợp lệ");
        }
        if (newPasswordHash == null || newPasswordHash.trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu mới không được rỗng");
        }

        Admin existingAdmin = adminDAO.getAdminById(id);
        if (existingAdmin == null) {
            throw new IllegalArgumentException("Không tìm thấy admin với ID: " + id);
        }

        adminDAO.updatePassword(id, newPasswordHash);
    }

    /**
     * Bật/tắt trạng thái active của admin
     */
    public void toggleAdminActive(int id, boolean isActive) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID admin không hợp lệ");
        }

        Admin existingAdmin = adminDAO.getAdminById(id);
        if (existingAdmin == null) {
            throw new IllegalArgumentException("Không tìm thấy admin với ID: " + id);
        }

        // Không cho phép vô hiệu hóa SUPER_ADMIN cuối cùng
        if (!isActive && "SUPER_ADMIN".equals(existingAdmin.getRole())) {
            int activeSuperAdmins = adminDAO.getByRole("SUPER_ADMIN").stream()
                    .filter(Admin::isActive)
                    .mapToInt(a -> 1)
                    .sum();
            if (activeSuperAdmins <= 1) {
                throw new IllegalArgumentException("Không thể vô hiệu hóa SUPER_ADMIN cuối cùng");
            }
        }

        adminDAO.toggleActive(id, isActive);
    }

    /**
     * Kích hoạt admin
     */
    public void activateAdmin(int id) {
        toggleAdminActive(id, true);
    }

    /**
     * Vô hiệu hóa admin
     */
    public void deactivateAdmin(int id) {
        toggleAdminActive(id, false);
    }

    /**
     * Xóa admin
     */
    public void deleteAdmin(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID admin không hợp lệ");
        }

        Admin existingAdmin = adminDAO.getAdminById(id);
        if (existingAdmin == null) {
            throw new IllegalArgumentException("Không tìm thấy admin với ID: " + id);
        }

        // Không cho phép xóa SUPER_ADMIN cuối cùng
        if ("SUPER_ADMIN".equals(existingAdmin.getRole())) {
            long superAdminCount = adminDAO.getByRole("SUPER_ADMIN").size();
            if (superAdminCount <= 1) {
                throw new IllegalArgumentException("Không thể xóa SUPER_ADMIN cuối cùng");
            }
        }

        adminDAO.deleteAdmin(id);
    }

    /**
     * Đếm số admin đang active
     */
    public int getActiveAdminsCount() {
        return adminDAO.countActive();
    }

    /**
     * Kiểm tra admin có tồn tại không
     */
    public boolean adminExists(int id) {
        return adminDAO.getAdminById(id) != null;
    }

    /**
     * Kiểm tra username đã được sử dụng chưa
     */
    public boolean isUsernameExists(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        return adminDAO.getByUsername(username) != null;
    }

    /**
     * Kiểm tra email đã được sử dụng chưa
     */
    public boolean isEmailExists(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return adminDAO.getByEmail(email) != null;
    }

    /**
     * Xác thực thông tin đăng nhập
     */
    public boolean validateLogin(String username, String password) {
        try {
            Admin admin = login(username, password);
            return admin != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Validate email format
     */
    private boolean isValidEmail(String email) {
        if (email == null) return false;
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    /**
     * Validate role
     */
    private boolean isValidRole(String role) {
        if (role == null) return false;
        return role.equals("SUPER_ADMIN") || role.equals("MANAGER") || role.equals("STAFF");
    }
}
