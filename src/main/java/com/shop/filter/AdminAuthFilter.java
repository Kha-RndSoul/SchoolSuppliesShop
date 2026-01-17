package com.shop.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Filter để protect tất cả các trang admin
 * Chỉ cho phép truy cập nếu đã đăng nhập với tư cách admin
 */
@WebFilter(filterName = "AdminAuthFilter", urlPatterns = {"/admin/*"})
public class AdminAuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("================================");
        System.out.println(" AdminAuthFilter initialized");
        System.out.println("→ Protecting all URLs: /admin/*");
        System.out.println("================================");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Get request URI và context path
        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();

        // Lấy path sau context (e.g., /admin/dashboard)
        String path = requestURI.substring(contextPath.length());

        // ========== DEBUG LOG ==========
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println(" AdminAuthFilter: Processing request");
        System.out.println("   Request URI: " + requestURI);
        System.out.println("   Path: " + path);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        // ===============================

        // Check nếu là static resources (CSS, JS, images) → Cho qua
        if (isStaticResource(path)) {
            System.out.println("→ Static resource, allowing access");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            chain.doFilter(request, response);
            return;
        }

        // Get session (false = không tạo mới nếu chưa có)
        HttpSession session = httpRequest.getSession(false);

        // Check nếu admin đã đăng nhập
        boolean isAdminLoggedIn = (session != null && session.getAttribute("admin") != null);

        if (!isAdminLoggedIn) {
            // Admin chưa đăng nhập → Block và redirect về login
            System.out.println(" UNAUTHORIZED ACCESS ATTEMPT");
            System.out.println("   Path: " + path);
            System.out.println("   Session: " + (session != null ? "exists but no admin" : "null"));
            System.out.println("   Action: Redirecting to /login");

            // Lưu URL hiện tại để redirect lại sau khi login (optional)
            String redirectUrl = requestURI;
            String queryString = httpRequest.getQueryString();
            if (queryString != null && !queryString.isEmpty()) {
                redirectUrl += "?" + queryString;
            }

            // Tạo session mới để lưu redirect URL
            HttpSession newSession = httpRequest.getSession(true);
            newSession.setAttribute("redirectAfterLogin", redirectUrl);

            System.out.println("   Saved redirect URL: " + redirectUrl);
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            // Redirect về login page (chung với customer)
            httpResponse.sendRedirect(contextPath + "/login");
            return; // Stop filter chain
        }

        // Admin đã đăng nhập → Cho qua
        String adminUsername = (String) session.getAttribute("adminUsername");
        String adminRole = (String) session.getAttribute("adminRole");

        System.out.println(" AUTHORIZED ACCESS");
        System.out.println("   Admin: " + adminUsername);
        System.out.println("   Role: " + adminRole);
        System.out.println("   Accessing: " + path);

        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        // Continue filter chain
        chain.doFilter(request, response);
    }

    /**
     * Check nếu là static resource (CSS, JS, images)
     */
    private boolean isStaticResource(String path) {
        return path.matches(".+\\.(css|js|jpg|jpeg|png|gif|svg|ico|woff|woff2|ttf|eot)$");
    }

    @Override
    public void destroy() {
        System.out.println(" AdminAuthFilter destroyed");
    }
}