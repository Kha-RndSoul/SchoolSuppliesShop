package com.shop.controller.admin;

import com.shop.model.Admin;
import com.shop.services.AdminService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/admin/profile")
public class AdminProfileServlet extends HttpServlet {

    private AdminService adminService;

    @Override
    public void init() {
        adminService = new AdminService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Kiểm tra đăng nhập
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Lấy thông tin admin từ session
        Admin sessionAdmin = (Admin) session.getAttribute("admin");

        // Lấy thông tin mới nhất từ database
        Admin admin = adminService.getAdminById(sessionAdmin.getId());

        if (admin == null) {
            response.sendRedirect(request.getContextPath() + "/logout");
            return;
        }

        // Cập nhật lại session với thông tin mới nhất
        session.setAttribute("admin", admin);
        session.setAttribute("adminFullName", admin.getFullName());
        session.setAttribute("adminUsername", admin.getUsername());

        request.setAttribute("admin", admin);
        request.getRequestDispatcher("/WEB-INF/jsp/admin/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        // Kiểm tra đăng nhập
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Admin sessionAdmin = (Admin) session.getAttribute("admin");
        String action = request.getParameter("action");

        if ("update-info".equals(action)) {
            handleUpdateInfo(request, response, sessionAdmin);
        } else if ("change-password".equals(action)) {
            handleChangePassword(request, response, sessionAdmin);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/profile");
        }
    }

    /**
     * Xử lý cập nhật thông tin cá nhân
     */
    private void handleUpdateInfo(HttpServletRequest request, HttpServletResponse response,
                                  Admin sessionAdmin) throws ServletException, IOException {
        try {
            String email = request.getParameter("email");
            String fullName = request.getParameter("fullName");

            // Validate
            if (fullName == null || fullName.trim().isEmpty()) {
                request.setAttribute("error", "Họ tên không được để trống");
                doGet(request, response);
                return;
            }

            if (email == null || email.trim().isEmpty()) {
                request.setAttribute("error", "Email không được để trống");
                doGet(request, response);
                return;
            }

            // Validate email format
            if (!isValidEmail(email)) {
                request.setAttribute("error", "Email không hợp lệ");
                doGet(request, response);
                return;
            }

            // Lấy thông tin admin hiện tại
            Admin admin = adminService.getAdminById(sessionAdmin.getId());

            // Cập nhật thông tin
            admin.setEmail(email.trim());
            admin.setFullName(fullName.trim());

            // Lưu vào database
            adminService.updateAdmin(admin);

            // Cập nhật session
            HttpSession session = request.getSession();
            session.setAttribute("admin", admin);
            session.setAttribute("adminFullName", admin.getFullName());

            request.setAttribute("success", "Cập nhật thông tin thành công!");
            doGet(request, response);

        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi cập nhật thông tin");
            doGet(request, response);
        }
    }

    /**
     * Xử lý đổi mật khẩu
     */
    private void handleChangePassword(HttpServletRequest request, HttpServletResponse response,
                                      Admin sessionAdmin) throws ServletException, IOException {
        try {
            String oldPassword = request.getParameter("oldPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            // Validate
            if (oldPassword == null || oldPassword.trim().isEmpty()) {
                request.setAttribute("errorPassword", "Vui lòng nhập mật khẩu cũ");
                doGet(request, response);
                return;
            }

            if (newPassword == null || newPassword.trim().isEmpty()) {
                request.setAttribute("errorPassword", "Vui lòng nhập mật khẩu mới");
                doGet(request, response);
                return;
            }

            if (newPassword.length() < 8) {
                request.setAttribute("errorPassword", "Mật khẩu mới phải có ít nhất 8 ký tự");
                doGet(request, response);
                return;
            }

            // Validate password strength
            if (!isStrongPassword(newPassword)) {
                request.setAttribute("errorPassword", "Mật khẩu phải chứa chữ hoa, chữ thường, số và ký tự đặc biệt");
                doGet(request, response);
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("errorPassword", "Mật khẩu xác nhận không khớp");
                doGet(request, response);
                return;
            }

            if (oldPassword.equals(newPassword)) {
                request.setAttribute("errorPassword", "Mật khẩu mới không được trùng với mật khẩu cũ");
                doGet(request, response);
                return;
            }

            // Cập nhật mật khẩu
            adminService.updatePassword(sessionAdmin.getId(), oldPassword, newPassword);

            request.setAttribute("successPassword", "Đổi mật khẩu thành công!");
            doGet(request, response);

        } catch (IllegalArgumentException e) {
            request.setAttribute("errorPassword", e.getMessage());
            doGet(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorPassword", "Có lỗi xảy ra khi đổi mật khẩu");
            doGet(request, response);
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
     * Validate password strength
     */
    private boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) return false;

        boolean hasLowercase = password.matches(".*[a-z].*");
        boolean hasUppercase = password.matches(".*[A-Z].*");
        boolean hasDigit = password.matches(".*[0-9].*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{}|;:,.<>?].*");

        return hasLowercase && hasUppercase && hasDigit && hasSpecial;
    }
}