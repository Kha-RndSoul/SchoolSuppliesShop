package com.shop.controller.auth;

import com.shop.model.Customer;
import com.shop.services.CustomerService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

/**
 * Servlet xử lý đăng nhập khách hàng
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private CustomerService customerService;

    @Override
    public void init() {
        System.out.println("================================");
        System.out.println(" LoginServlet.init() STARTING...");

        try {
            System.out.println("→ Creating CustomerService...");
            customerService = new CustomerService();
            System.out.println(" CustomerService created successfully");

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

        // Check nếu user đã đăng nhập → redirect về home
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("customer") != null) {
            System.out.println(" User already logged in, redirecting to home");
            response.sendRedirect(request.getContextPath() + "/");
            return;
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
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");

        // DEBUG LOG
        System.out.println("→ Email parameter: [" + email + "]");
        System.out.println("→ Password parameter: [" + (password != null ? "***" + password.length() + " chars" : "null") + "]");
        System.out.println("→ Remember parameter: [" + remember + "]");
        System.out.println("================================");

        try {
            // Gọi CustomerService.login() - password là plain text
            System.out.println("→ Calling CustomerService.login()...");
            Customer customer = customerService.login(email, password);

            System.out.println(" Login successful!");
            System.out.println("   Customer ID: " + customer.getId());
            System.out.println("   Customer Name: " + customer.getFullName());
            System.out.println("   Customer Email: " + customer.getEmail());

            // Login thành công → Tạo session
            HttpSession session = request.getSession(true);
            session.setAttribute("customer", customer);
            session.setAttribute("customerId", customer.getId());
            session.setAttribute("customerName", customer.getFullName());
            session.setAttribute("customerEmail", customer.getEmail());

            // Set session timeout
            if ("on".equals(remember)) {
                session.setMaxInactiveInterval(7 * 24 * 60 * 60);
                System.out.println("→ Session timeout:  7 days (remember me)");
            } else {
                session.setMaxInactiveInterval(30 * 60);
                System.out.println("→ Session timeout: 30 minutes");
            }

            // Log
            System.out.println("✅ Customer logged in:  " + customer.getEmail() + " (ID: " + customer.getId() + ")");
            System.out.println("================================");

            // Redirect về trang trước đó hoặc home
            String redirectUrl = (String) session.getAttribute("redirectAfterLogin");
            if (redirectUrl != null) {
                session.removeAttribute("redirectAfterLogin");
                System.out.println("→ Redirecting to: " + redirectUrl);
                response.sendRedirect(redirectUrl);
            } else {
                System.out.println("→ Redirecting to home");
                response.sendRedirect(request.getContextPath() + "/");
            }

        } catch (IllegalArgumentException e) {
            // Login thất bại → Show error
            request.setAttribute("error", e.getMessage());
            request.setAttribute("email", email); // Giữ lại email đã nhập

            System.out.println(" Login failed for email: [" + email + "]");
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
            request.setAttribute("email", email);
            request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        System.out.println(" LoginServlet destroyed");
    }
}