package com.shop.controller.auth;

import com.shop.model.Customer;
import com.shop.services.CustomerService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

/**
 * Servlet xử lý đăng ký tài khoản khách hàng
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    private CustomerService customerService;

    @Override
    public void init() {
        System.out.println("================================");
        System.out.println(" RegisterServlet.init() STARTING...");

        try {
            System.out.println("→ Creating CustomerService...");
            customerService = new CustomerService();
            System.out.println(" CustomerService created successfully");

            System.out.println(" RegisterServlet initialized");

        } catch (Exception e) {
            System.err.println("ERROR in RegisterServlet.init() ");
            System.err.println("Exception: " + e.getClass().getName());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize RegisterServlet", e);
        }

        System.out.println("================================");
    }

    /**
     * GET /register - Hiển thị form đăng ký
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("================================");
        System.out.println(" RegisterServlet.doGet() CALLED");
        System.out.println("   Request URI: " + request.getRequestURI());
        System.out.println("   Context Path: " + request.getContextPath());
        System.out.println("================================");

        // Check nếu user đã đăng nhập → redirect về home
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("customer") != null) {
            System.out.println(" User already logged in, redirecting to home");
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        // Forward tới register.jsp
        System.out.println("→ Forwarding to /WEB-INF/jsp/auth/register.jsp");
        request.getRequestDispatcher("/WEB-INF/jsp/auth/register.jsp").forward(request, response);
    }

    /**
     * POST /register - Xử lý đăng ký
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("================================");
        System.out.println(" RegisterServlet.doPost() CALLED");

        // Get parameters từ form
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        // ========== DEBUG LOG ==========
        System.out.println("→ Registration data:");
        System.out.println("   Email: [" + email + "]");
        System.out.println("   Password length: " + (password != null ? password.length() + " chars" : "null"));
        System.out.println("   Full Name: [" + fullName + "]");
        System.out.println("   Phone: [" + phone + "]");
        System.out.println("   Address: [" + address + "]");
        System.out.println("================================");
        // ===============================

        try {
            // ========== VALIDATION ==========

            // 1. Check empty fields
            if (email == null || email.trim().isEmpty()) {
                throw new IllegalArgumentException("Email không được rỗng");
            }
            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("Mật khẩu không được rỗng");
            }
            if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
                throw new IllegalArgumentException("Xác nhận mật khẩu không được rỗng");
            }
            if (fullName == null || fullName.trim().isEmpty()) {
                throw new IllegalArgumentException("Họ tên không được rỗng");
            }

            // 2. Validate email format
            if (!isValidEmail(email)) {
                throw new IllegalArgumentException("Email không hợp lệ");
            }

            // 3. Check password match
            if (!password.equals(confirmPassword)) {
                throw new IllegalArgumentException("Mật khẩu xác nhận không khớp");
            }

            // 4. Validate password strength
            String passwordError = validatePasswordStrength(password);
            if (passwordError != null) {
                throw new IllegalArgumentException(passwordError);
            }

            // 5. Validate phone (optional)
            if (phone != null && !phone.trim().isEmpty()) {
                if (!isValidPhone(phone)) {
                    throw new IllegalArgumentException("Số điện thoại không hợp lệ");
                }
            }

            System.out.println(" Validation passed");

            // ========== CREATE CUSTOMER ==========

            System.out.println("→ Creating new customer...");
            Customer newCustomer = new Customer();
            newCustomer.setEmail(email.trim());
            newCustomer.setPasswordHash(password);
            newCustomer.setFullName(fullName.trim());
            newCustomer.setPhone(phone != null ? phone.trim() : "");
            newCustomer.setAddress(address != null ? address.trim() : "");

            System.out.println("→ Calling CustomerService.registerCustomer()...");
            customerService.registerCustomer(newCustomer);

            System.out.println(" Customer registered successfully!");
            System.out.println("   Email: " + email);
            System.out.println("================================");

            // ========== REDIRECT TO LOGIN WITH SUCCESS MESSAGE ==========

            HttpSession session = request.getSession(true);
            session.setAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");

            System.out.println("→ Redirecting to login page with success message");
            response.sendRedirect(request.getContextPath() + "/login");

        } catch (IllegalArgumentException e) {
            // Registration failed → Show error
            request.setAttribute("error", e.getMessage());

            // Giữ lại thông tin đã nhập (trừ password)
            request.setAttribute("email", email);
            request.setAttribute("fullName", fullName);
            request.setAttribute("phone", phone);
            request.setAttribute("address", address);

            System.out.println(" Registration failed");
            System.out.println("   Error: " + e.getMessage());
            System.out.println("================================");

            // Forward lại register.jsp với error message
            request.getRequestDispatcher("/WEB-INF/jsp/auth/register.jsp").forward(request, response);

        } catch (Exception e) {
            // Unexpected error
            System.err.println(" UNEXPECTED ERROR in RegisterServlet.doPost() ");
            System.err.println("Exception: " + e.getClass().getName());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            System.err.println("================================");

            request.setAttribute("error", "Đã có lỗi xảy ra. Vui lòng thử lại sau.");
            request.setAttribute("email", email);
            request.setAttribute("fullName", fullName);
            request.setAttribute("phone", phone);
            request.setAttribute("address", address);

            request.getRequestDispatcher("/WEB-INF/jsp/auth/register.jsp").forward(request, response);
        }
    }

    /**
     * Validate email
     */
    private boolean isValidEmail(String email) {
        if (email == null) return false;
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    /**
     * Validate mật khẩu
     */
    private String validatePasswordStrength(String password) {
        if (password == null) {
            return "Mật khẩu không được rỗng";
        }

        // 1. Kiểm tra độ dài tối thiểu
        if (password.length() < 8) {
            return "Mật khẩu phải có ít nhất 8 ký tự";
        }

        // 2. Kiểm tra có chữ cái thường
        if (!password.matches(".*[a-z].*")) {
            return "Mật khẩu phải chứa ít nhất 1 chữ cái thường (a-z)";
        }

        // 3. Kiểm tra có chữ cái hoa
        if (!password.matches(".*[A-Z].*")) {
            return "Mật khẩu phải chứa ít nhất 1 chữ cái hoa (A-Z)";
        }

        // 4. Kiểm tra có chữ số
        if (!password.matches(".*[0-9].*")) {
            return "Mật khẩu phải chứa ít nhất 1 chữ số (0-9)";
        }

        // 5. Kiểm tra có ký tự đặc biệt
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{}|;:,.<>?].*")) {
            return "Mật khẩu phải chứa ít nhất 1 ký tự đặc biệt ";
        }

        // Tất cả điều kiện đều thỏa mãn
        return null;
    }

    /**
     * Validate số điện thoại Việt Nam
     */
    private boolean isValidPhone(String phone) {
        if (phone == null) return false;
        // Format: 0xxxxxxxxx (10 chữ số)
        String phoneRegex = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";
        return phone.matches(phoneRegex);
    }

    @Override
    public void destroy() {
        System.out.println(" RegisterServlet destroyed");
    }
}