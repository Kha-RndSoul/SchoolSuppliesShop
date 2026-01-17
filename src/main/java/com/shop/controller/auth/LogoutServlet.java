package com.shop.controller.auth;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

/**
 * Servlet xử lý đăng xuất cho cả Customer và Admin
 */
@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    @Override
    public void init() {
        System.out.println("================================");
        System.out.println(" LogoutServlet initialized");
        System.out.println("================================");
    }

    /**
     * GET /logout - Đăng xuất
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("================================");
        System.out.println(" LogoutServlet.doGet() CALLED");

        // Get current session
        HttpSession session = request.getSession(false);

        String userType = "UNKNOWN";
        String redirectUrl = request.getContextPath() + "/login";

        if (session != null) {
            // Xác định user type trước khi xóa session
            if (session.getAttribute("admin") != null) {
                userType = "ADMIN";
                String adminUsername = (String) session.getAttribute("adminUsername");
                Integer adminId = (Integer) session.getAttribute("adminId");

                System.out.println("→ Logging out ADMIN:");
                System.out.println("   Admin ID: " + adminId);
                System.out.println("   Username: " + adminUsername);

            } else if (session.getAttribute("customer") != null) {
                userType = "CUSTOMER";
                String customerEmail = (String) session.getAttribute("customerEmail");
                Integer customerId = (Integer) session.getAttribute("customerId");

                System.out.println("→ Logging out CUSTOMER:");
                System.out.println("   Customer ID: " + customerId);
                System.out.println("   Email: " + customerEmail);

                // Customer redirect về home page
                redirectUrl = request.getContextPath() + "/";
            }

            System.out.println("   Session ID: " + session.getId());

            // Invalidate session (xóa tất cả attributes)
            session.invalidate();
            System.out.println(" Session invalidated");
        } else {
            System.out.println("→ No active session found");
        }

        // Delete remember-me cookies
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // Delete customer cookie
                if ("customerEmail".equals(cookie.getName())) {
                    System.out.println("→ Deleting customer remember-me cookie");
                    cookie.setMaxAge(0);
                    cookie.setPath(request.getContextPath());
                    response.addCookie(cookie);
                }
                // Delete admin cookie
                if ("adminUsername".equals(cookie.getName())) {
                    System.out.println("→ Deleting admin remember-me cookie");
                    cookie.setMaxAge(0);
                    cookie.setPath(request.getContextPath() + "/admin");
                    response.addCookie(cookie);
                }
            }
            System.out.println(" Remember-me cookies deleted");
        }

        System.out.println("================================");

        // Redirect với success message
        HttpSession newSession = request.getSession(true);
        newSession.setAttribute("success", "Đăng xuất thành công!");

        System.out.println("→ User type: " + userType);
        System.out.println("→ Redirecting to: " + redirectUrl);
        response.sendRedirect(redirectUrl);
    }

    /**
     * POST /logout - Cũng hỗ trợ POST method
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public void destroy() {
        System.out.println(" LogoutServlet destroyed");
    }
}