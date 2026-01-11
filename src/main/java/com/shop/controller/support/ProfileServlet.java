package com.shop.controller.support;

import com.shop.model.Customer;
import com.shop.services.CustomerService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

/**
 * Servlet xử lý trang profile khách hàng
 */
@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile"})
public class ProfileServlet extends HttpServlet {

    private CustomerService customerService;

    @Override
    public void init() {
        System.out.println("================================");
        System.out.println(" ProfileServlet.init() STARTING...");

        try {
            System.out.println("→ Creating CustomerService...");
            customerService = new CustomerService();
            System.out.println(" CustomerService created successfully");

            System.out.println(" ProfileServlet initialized");

        } catch (Exception e) {
            System.err.println(" ERROR in ProfileServlet.init() ");
            System.err.println("Exception: " + e.getClass().getName());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize ProfileServlet", e);
        }

        System.out.println("================================");
    }

    /**
     * GET /profile - Hiển thị trang profile
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("================================");
        System.out.println(" ProfileServlet.doGet() CALLED");
        System.out.println("   Request URI: " + request.getRequestURI());
        System.out.println("   Context Path: " + request.getContextPath());

        // Check authentication
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("customer") == null) {
            System.out.println(" User not logged in, redirecting to login");

            // Lưu URL hiện tại để redirect lại sau khi login
            session = request.getSession(true);
            session.setAttribute("redirectAfterLogin", request.getContextPath() + "/profile");

            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Get customer from session
        Customer customer = (Customer) session.getAttribute("customer");
        System.out.println(" Customer logged in:");
        System.out.println("   ID: " + customer.getId());
        System.out.println("   Email: " + customer.getEmail());
        System.out.println("   Name: " + customer.getFullName());

        // Refresh customer data from database
        System.out.println("→ Refreshing customer data from database...");
        Customer refreshedCustomer = customerService.getCustomerById(customer.getId());

        if (refreshedCustomer == null) {
            System.err.println(" Customer not found in database, logging out");
            session.invalidate();
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Update session với data mới nhất
        session.setAttribute("customer", refreshedCustomer);
        session.setAttribute("customerName", refreshedCustomer.getFullName());

        System.out.println(" Customer data refreshed");
        System.out.println("================================");

        // Forward tới profile.jsp
        System.out.println("→ Forwarding to /WEB-INF/jsp/support/profile.jsp");
        request.getRequestDispatcher("/WEB-INF/jsp/support/profile.jsp").forward(request, response);
    }

    /**
     * POST /profile - Xử lý cập nhật profile
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("================================");
        System.out.println(" ProfileServlet.doPost() CALLED");

        // Check authentication
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("customer") == null) {
            System.out.println(" User not logged in, redirecting to login");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Get customer from session
        Customer customer = (Customer) session.getAttribute("customer");

        // Get action parameter (update-info hoặc change-password)
        String action = request.getParameter("action");

        System.out.println("→ Action: " + action);
        System.out.println("   Customer ID: " + customer.getId());

        if ("update-info".equals(action)) {
            handleUpdateInfo(request, response, customer, session);
        } else if ("change-password".equals(action)) {
            handleChangePassword(request, response, customer, session);
        } else {
            System.out.println(" Unknown action: " + action);
            request.setAttribute("error", "Hành động không hợp lệ");
            request.getRequestDispatcher("/WEB-INF/jsp/auth/profile.jsp").forward(request, response);
        }
    }

    /**
     * Xử lý cập nhật thông tin cá nhân
     */
    private void handleUpdateInfo(HttpServletRequest request, HttpServletResponse response,
                                  Customer customer, HttpSession session)
            throws ServletException, IOException {

        System.out.println("→ Handling UPDATE INFO");

        // Get parameters
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        System.out.println("→ Update data:");
        System.out.println("   Full Name: [" + fullName + "]");
        System.out.println("   Phone: [" + phone + "]");
        System.out.println("   Address: [" + address + "]");

        try {
            // Validation
            if (fullName == null || fullName.trim().isEmpty()) {
                throw new IllegalArgumentException("Họ tên không được rỗng");
            }

            // Validate phone (optional)
            if (phone != null && !phone.trim().isEmpty()) {
                if (!isValidPhone(phone)) {
                    throw new IllegalArgumentException("Số điện thoại không hợp lệ");
                }
            }

            System.out.println(" Validation passed");

            // Update customer object
            customer.setFullName(fullName.trim());
            customer.setPhone(phone != null ? phone.trim() : "");
            customer.setAddress(address != null ? address.trim() : "");

            // Call service to update
            System.out.println("→ Calling CustomerService.updateCustomer()...");
            customerService.updateCustomer(customer);

            System.out.println(" Customer info updated successfully");

            // Update session
            session.setAttribute("customer", customer);
            session.setAttribute("customerName", customer.getFullName());

            // Set success message
            request.setAttribute("success", "Cập nhật thông tin thành công!");
            System.out.println("================================");

            // Forward lại profile.jsp
            request.getRequestDispatcher("/WEB-INF/jsp/auth/profile.jsp").forward(request, response);

        } catch (IllegalArgumentException e) {
            System.out.println(" Update info failed");
            System.out.println("   Error: " + e.getMessage());
            System.out.println("================================");

            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/auth/profile.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println(" UNEXPECTED ERROR in handleUpdateInfo() ");
            System.err.println("Exception: " + e.getClass().getName());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            System.err.println("================================");

            request.setAttribute("error", "Đã có lỗi xảy ra. Vui lòng thử lại sau.");
            request.getRequestDispatcher("/WEB-INF/jsp/auth/profile.jsp").forward(request, response);
        }
    }

    /**
     * Xử lý đổi mật khẩu
     */
    private void handleChangePassword(HttpServletRequest request, HttpServletResponse response,
                                      Customer customer, HttpSession session)
            throws ServletException, IOException {

        System.out.println("→ Handling CHANGE PASSWORD");

        // Get parameters
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        System.out.println("→ Password change request:");
        System.out.println("   Old password length: " + (oldPassword != null ? oldPassword.length() + " chars" : "null"));
        System.out.println("   New password length: " + (newPassword != null ? newPassword.length() + " chars" : "null"));

        try {
            // Validation
            if (oldPassword == null || oldPassword.trim().isEmpty()) {
                throw new IllegalArgumentException("Mật khẩu cũ không được rỗng");
            }
            if (newPassword == null || newPassword.trim().isEmpty()) {
                throw new IllegalArgumentException("Mật khẩu mới không được rỗng");
            }
            if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
                throw new IllegalArgumentException("Xác nhận mật khẩu không được rỗng");
            }

            // Check password match
            if (!newPassword.equals(confirmPassword)) {
                throw new IllegalArgumentException("Mật khẩu xác nhận không khớp");
            }

            // Validate password strength
            String passwordError = validatePasswordStrength(newPassword);
            if (passwordError != null) {
                throw new IllegalArgumentException(passwordError);
            }

            System.out.println(" Validation passed");

            // Call service to change password
            System.out.println("→ Calling CustomerService.updatePassword()...");
            customerService.updatePassword(customer.getId(), oldPassword, newPassword);

            System.out.println(" Password changed successfully");

            // Set success message
            request.setAttribute("successPassword", "Đổi mật khẩu thành công!");
            System.out.println("================================");

            // Forward lại profile.jsp
            request.getRequestDispatcher("/WEB-INF/jsp/auth/profile.jsp").forward(request, response);

        } catch (IllegalArgumentException e) {
            System.out.println(" Change password failed");
            System.out.println("   Error: " + e.getMessage());
            System.out.println("================================");

            request.setAttribute("errorPassword", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/auth/profile.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println(" UNEXPECTED ERROR in handleChangePassword() ");
            System.err.println("Exception: " + e.getClass().getName());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            System.err.println("================================");

            request.setAttribute("errorPassword", "Đã có lỗi xảy ra. Vui lòng thử lại sau.");
            request.getRequestDispatcher("/WEB-INF/jsp/auth/profile.jsp").forward(request, response);
        }
    }

    /**
     * Validate password strength
     */
    private String validatePasswordStrength(String password) {
        if (password == null) {
            return "Mật khẩu không được rỗng";
        }
        if (password.length() < 8) {
            return "Mật khẩu phải có ít nhất 8 ký tự";
        }
        if (!password.matches(".*[a-z].*")) {
            return "Mật khẩu phải chứa ít nhất 1 chữ cái thường (a-z)";
        }
        if (!password.matches(".*[A-Z].*")) {
            return "Mật khẩu phải chứa ít nhất 1 chữ cái hoa (A-Z)";
        }
        if (!password.matches(".*[0-9].*")) {
            return "Mật khẩu phải chứa ít nhất 1 chữ số (0-9)";
        }
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{}|;:,.<>?].*")) {
            return "Mật khẩu phải chứa ít nhất 1 ký tự đặc biệt ";
        }
        return null;
    }

    /**
     * Validate số điện thoại Việt Nam
     */
    private boolean isValidPhone(String phone) {
        if (phone == null) return false;
        String phoneRegex = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";
        return phone.matches(phoneRegex);
    }

    @Override
    public void destroy() {
        System.out.println(" ProfileServlet destroyed");
    }
}