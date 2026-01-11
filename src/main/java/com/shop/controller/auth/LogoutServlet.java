package com.shop.controller.auth;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

/**
 * Servlet xử lý đăng xuất khách hàng
 */
@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    @Override
    public void init() {
        System.out.println("================================");
        System.out.println(" LogoutServlet.init() STARTING...");
        System.out.println(" LogoutServlet initialized");
        System.out.println("================================");
    }

    /**
     * GET /logout - Xử lý đăng xuất
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("================================");
        System.out.println(" LogoutServlet.doGet() CALLED");
        System.out.println("   Request URI: " + request.getRequestURI());
        System.out.println("   Context Path: " + request.getContextPath());

        try {
            // Get current session
            HttpSession session = request.getSession(false);

            if (session != null) {
                // Lấy thông tin customer trước khi invalidate
                Object customer = session.getAttribute("customer");
                String customerEmail = (String) session.getAttribute("customerEmail");
                String customerName = (String) session.getAttribute("customerName");

                if (customer != null) {
                    System.out.println("→ Logging out customer:");
                    System.out.println("   Email: " + customerEmail);
                    System.out.println("   Name: " + customerName);
                } else {
                    System.out.println("→ No customer in session (already logged out?)");
                }

                // Xóa toàn bộ session data
                System.out.println("→ Invalidating session...");
                session.invalidate();
                System.out.println(" Session invalidated successfully");

            } else {
                System.out.println("→ No active session found");
            }

            System.out.println("================================");

            // ========== REDIRECT ==========

            // Redirect về trang chủ
            System.out.println("→ Redirecting to home page");
            response.sendRedirect(request.getContextPath() + "/");

        } catch (Exception e) {
            // Unexpected error
            System.err.println("= UNEXPECTED ERROR in LogoutServlet.doGet() ");
            System.err.println("Exception: " + e.getClass().getName());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            System.err.println("================================");

            // Vẫn redirect về home dù có lỗi
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

    /**
     * POST /logout - xử lý đăng xuất
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("================================");
        System.out.println(" LogoutServlet.doPost() CALLED");
        System.out.println("   Forwarding to doGet()...");
        System.out.println("================================");

        // Forward tới doGet để xử lý
        doGet(request, response);
    }

    @Override
    public void destroy() {
        System.out.println(" LogoutServlet destroyed");
    }
}