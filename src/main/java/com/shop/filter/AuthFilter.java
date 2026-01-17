package com.shop.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Filter để protect các URL cần đăng nhập
 * Redirect về /login nếu user chưa đăng nhập
 * Lưu URL để redirect lại sau khi login thành công
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"/*"})
public class AuthFilter implements Filter {

    // Danh sách các URL cần đăng nhập
    private static final List<String> PROTECTED_URLS = Arrays.asList(
            "/profile",
            "/orders",
            "/order-history",
            "/checkout",
            "/cart/checkout",
            "/wishlist",
            "/reviews/add",
            "/reviews/edit"
    );

    // Danh sách các URL pattern cần đăng nhập (regex)
    private static final List<String> PROTECTED_PATTERNS = Arrays.asList(
            "/orders/.*",
            "/profile/.*"
    );

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("================================");
        System.out.println(" AuthFilter initialized");
        System.out.println("→ Protected URLs: " + PROTECTED_URLS);
        System.out.println("→ Protected Patterns: " + PROTECTED_PATTERNS);
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

        // Lấy path sau context (e.g., /profile, /orders)
        String path = requestURI.substring(contextPath.length());

        // Check nếu URL cần protection
        if (requiresAuthentication(path)) {

            // Debug log
            System.out.println(" AuthFilter: Checking authentication for: " + path);

            // Get session (false = không tạo mới nếu chưa có)
            HttpSession session = httpRequest.getSession(false);

            // Check nếu user đã đăng nhập
            boolean isLoggedIn = (session != null && session.getAttribute("customer") != null);

            if (!isLoggedIn) {
                // User chưa đăng nhập → Redirect về login
                System.out.println(" User not authenticated, redirecting to login");
                System.out.println("   Requested URL: " + requestURI);

                // Lưu URL hiện tại để redirect lại sau khi login
                String redirectUrl = requestURI;
                String queryString = httpRequest.getQueryString();
                if (queryString != null && !queryString.isEmpty()) {
                    redirectUrl += "?" + queryString;
                }

                // Tạo session mới để lưu redirect URL
                HttpSession newSession = httpRequest.getSession(true);
                newSession.setAttribute("redirectAfterLogin", redirectUrl);

                System.out.println("→ Saved redirect URL: " + redirectUrl);
                System.out.println("→ Redirecting to: " + contextPath + "/login");
                System.out.println("================================");

                // Redirect về login page
                httpResponse.sendRedirect(contextPath + "/login");
                return; // Stop filter chain
            } else {
                // User đã đăng nhập
                String customerEmail = (String) session.getAttribute("customerEmail");
                System.out.println(" User authenticated: " + customerEmail);
                System.out.println("   Accessing: " + path);
            }
        }

        // Continue filter chain
        chain.doFilter(request, response);
    }

    /**
     * Check nếu path cần authentication
     */
    private boolean requiresAuthentication(String path) {
        // Normalize path (remove trailing slash)
        if (path.length() > 1 && path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }

        // Check exact match
        if (PROTECTED_URLS.contains(path)) {
            return true;
        }

        // Check pattern match
        for (String pattern : PROTECTED_PATTERNS) {
            if (path.matches(pattern)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void destroy() {
        System.out.println(" AuthFilter destroyed");
    }
}