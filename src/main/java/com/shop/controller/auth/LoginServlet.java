package com.shop.controller.auth;

import com.shop.model.Admin;
import com.shop.model.Customer;
import com.shop.services.AdminService;
import com.shop.services.CustomerService;
import com.shop.util.PasswordUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

/**
 * Servlet xử lý đăng nhập cho cả Customer và Admin
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private CustomerService customerService;
    private AdminService adminService;

    @Override
    public void init() {
        System.out.println("================================");
        System.out.println(" LoginServlet.init() STARTING...");

        try {
            System.out.println("→ Creating CustomerService...");
            customerService = new CustomerService();
            System.out.println(" CustomerService created successfully");

            System.out.println(" Creating AdminService...");
            adminService = new AdminService();
            System.out.println(" AdminService created successfully");

            System.out.println(" LoginServlet initialized");

        } catch (Exception e) {
            System.err.println(" ERROR in LoginServlet.init() ");
            System.err.println("Exception: " + e.getClass().getName());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize LoginServlet", e);
        }

        System.out.println("================================");
    }

    /**
     * GET /login - Hiển thị form đăng nhập
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("================================");
        System.out.println(" LoginServlet.doGet() CALLED");
        System.out.println("   Request URI: " + request.getRequestURI());
        System.out.println("   Context Path: " + request.getContextPath());
        System.out.println("================================");

        // Check nếu đã đăng nhập → redirect
        HttpSession session = request.getSession(false);
        if (session != null) {
            // Check customer
            if (session.getAttribute("customer") != null) {
                System.out.println(" Customer already logged in, redirecting to home");
                response.sendRedirect(request.getContextPath() + "/");
                return;
            }
            // Check admin
            if (session.getAttribute("admin") != null) {
                System.out.println(" Admin already logged in, redirecting to dashboard");
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                return;
            }
        }

        // Forward tới login.jsp
        System.out.println("→ Forwarding to /WEB-INF/jsp/auth/login.jsp");
        request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp").forward(request, response);
    }

    /**
     * POST /login - Xử lý đăng nhập
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("================================");
        System.out.println(" LoginServlet.doPost() CALLED");

        // Get parameters từ form
        String emailOrUsername = request.getParameter("email"); // có thể là email hoặc username
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");

        // ========== DEBUG LOG ==========
        System.out.println("→ Login attempt:");
        System.out.println("   Email/Username: [" + emailOrUsername + "]");
        System.out.println("   Password length: " + (password != null ? password.length() + " chars" : "null"));
        System.out.println("   Remember me: " + (remember != null ? "Yes" : "No"));
        System.out.println("================================");
        // ===============================

        try {
            // ========== VALIDATION ==========

            if (emailOrUsername == null || emailOrUsername.trim().isEmpty()) {
                throw new IllegalArgumentException("Email hoặc username không được rỗng");
            }
            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("Mật khẩu không được rỗng");
            }

            System.out.println(" Validation passed");

            // ========== PHÂN LOẠI LOGIN TYPE ==========

            boolean isEmail = emailOrUsername.contains("@");
            String loginType = isEmail ? "CUSTOMER (Email)" : "ADMIN (Username)";
            System.out.println("→ Detected login type: " + loginType);

            // ========== TRY LOGIN ==========

            if (isEmail) {
                // ========== CUSTOMER LOGIN ==========
                System.out.println("→ Attempting CUSTOMER login...");

                try {
                    Customer customer = customerService.login(emailOrUsername.trim(), password.trim());

                    if (customer != null) {
                        System.out.println(" CUSTOMER LOGIN SUCCESSFUL!");
                        System.out.println("   Customer ID: " + customer.getId());
                        System.out.println("   Name: " + customer.getFullName());
                        System.out.println("   Email: " + customer.getEmail());

                        // Create session
                        HttpSession session = request.getSession(true);
                        session.setAttribute("customer", customer);
                        session.setAttribute("customerId", customer.getId());
                        session.setAttribute("customerEmail", customer.getEmail());
                        session.setAttribute("customerName", customer.getFullName());
                        session.setAttribute("userType", "CUSTOMER");
                        session.setMaxInactiveInterval(30 * 60); // 30 minutes

                        System.out.println(" Customer session created");

                        // Remember me (optional)
                        if (remember != null && "on".equals(remember)) {
                            Cookie emailCookie = new Cookie("customerEmail", emailOrUsername);
                            emailCookie.setMaxAge(30 * 24 * 60 * 60); // 30 days
                            emailCookie.setPath(request.getContextPath());
                            emailCookie.setHttpOnly(true);
                            response.addCookie(emailCookie);
                            System.out.println(" Remember-me cookie set");
                        }

                        System.out.println("================================");

                        // Redirect về trang chủ hoặc trang đã lưu
                        String redirectUrl = (String) session.getAttribute("redirectAfterLogin");
                        if (redirectUrl != null && !redirectUrl.isEmpty()) {
                            session.removeAttribute("redirectAfterLogin");
                            System.out.println("→ Redirecting to saved URL: " + redirectUrl);
                            response.sendRedirect(redirectUrl);
                        } else {
                            System.out.println("→ Redirecting to home page");
                            response.sendRedirect(request.getContextPath() + "/");
                        }
                        return;
                    }

                } catch (IllegalArgumentException e) {
                    System.out.println(" Customer login failed: " + e.getMessage());
                    // Throw để hiển thị error
                    throw e;
                }

            } else {
                // ========== ADMIN LOGIN ==========
                System.out.println("→ Attempting ADMIN login...");

                try {
                    // Get admin by username
                    Admin admin = adminService.getAdminByUsername(emailOrUsername.trim());

                    if (admin == null) {
                        System.out.println(" Admin not found with username: " + emailOrUsername);
                        throw new IllegalArgumentException("Username hoặc mật khẩu không đúng");
                    }

                    System.out.println(" Admin found: " + admin.getUsername());

                    // Verify password with BCrypt
                    System.out.println("→ Verifying admin password...");
                    boolean isPasswordCorrect = PasswordUtil.verifyPassword(password.trim(), admin.getPasswordHash());

                    if (!isPasswordCorrect) {
                        System.out.println(" Admin password verification FAILED");
                        throw new IllegalArgumentException("Username hoặc mật khẩu không đúng");
                    }

                    System.out.println(" Admin password verified");
                    // Thêm debug log sau dòng 211
                    System.out.println(" Admin password verified");

                    // DEBUG: Kiểm tra giá trị isActive
                    System.out.println("→ DEBUG: admin.isActive() = " + admin.isActive());
                    System.out.println("→ DEBUG: Admin object: " + admin);

                    // Check if admin is active
                    if (!admin.isActive()) {
                        System.out.println(" Admin account is INACTIVE");
                        throw new IllegalArgumentException("Tài khoản admin đã bị vô hiệu hóa");
                    }

                    System.out.println(" ADMIN LOGIN SUCCESSFUL!");
                    System.out.println("   Admin ID: " + admin.getId());
                    System.out.println("   Username: " + admin.getUsername());
                    System.out.println("   Full Name: " + admin.getFullName());
                    System.out.println("   Role: " + admin.getRole());

                    // Create session
                    HttpSession session = request.getSession(true);
                    session.setAttribute("admin", admin);
                    session.setAttribute("adminId", admin.getId());
                    session.setAttribute("adminUsername", admin.getUsername());
                    session.setAttribute("adminFullName", admin.getFullName());
                    session.setAttribute("adminRole", admin.getRole());
                    session.setAttribute("userType", "ADMIN");
                    session.setMaxInactiveInterval(30 * 60); // 30 minutes

                    System.out.println(" Admin session created");

                    // Remember me (optional)
                    if (remember != null && "on".equals(remember)) {
                        Cookie usernameCookie = new Cookie("adminUsername", emailOrUsername);
                        usernameCookie.setMaxAge(30 * 24 * 60 * 60); // 30 days
                        usernameCookie.setPath(request.getContextPath() + "/admin");
                        usernameCookie.setHttpOnly(true);
                        response.addCookie(usernameCookie);
                        System.out.println(" Admin remember-me cookie set");
                    }

                    System.out.println("================================");

                    // Redirect về admin dashboard
                    System.out.println("→ Redirecting to /admin/dashboard");
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                    return;

                } catch (IllegalArgumentException e) {
                    System.out.println(" Admin login failed: " + e.getMessage());
                    throw e;
                }
            }

        } catch (IllegalArgumentException e) {
            // Login failed → Show error
            request.setAttribute("error", e.getMessage());
            request.setAttribute("email", emailOrUsername);

            System.out.println(" Login FAILED");
            System.out.println("   Error: " + e.getMessage());
            System.out.println("================================");

            // Forward lại login.jsp với error message
            request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp").forward(request, response);

        } catch (Exception e) {
            // Unexpected error
            System.err.println(" UNEXPECTED ERROR in LoginServlet.doPost() ");
            System.err.println("Exception: " + e.getClass().getName());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            System.err.println("================================");

            request.setAttribute("error", "Đã có lỗi xảy ra. Vui lòng thử lại sau.");
            request.setAttribute("email", emailOrUsername);

            request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        System.out.println("✓ LoginServlet destroyed");
    }
}