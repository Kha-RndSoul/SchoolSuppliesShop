package com.shop.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Filter để protect các URL cần đăng nhập
 * CRITICAL FIX: Thêm dispatcherTypes để tránh loop khi forward
 */
@WebFilter(
        filterName = "AuthFilter",
        urlPatterns = {"/*"},
        dispatcherTypes = {DispatcherType.REQUEST}  // ← QUAN TRỌNG: Chỉ chạy với REQUEST, KHÔNG chạy với FORWARD
)
public class AuthFilter implements Filter {

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

    private static final List<String> PROTECTED_PATTERNS = Arrays.asList(
            "/orders/.*",
            "/profile/.*"
    );

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("================================");
        System.out.println("🔵 AuthFilter initialized");
        System.out.println("→ Protected URLs: " + PROTECTED_URLS);
        System.out.println("→ Protected Patterns: " + PROTECTED_PATTERNS);
        System.out.println("→ Dispatcher: REQUEST only");
        System.out.println("================================");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String path = requestURI.substring(contextPath.length());

        System.out.println("🟡 AuthFilter: " + path);

        // Skip static resources
        if (isStaticResource(path)) {
            System.out.println("   → Skipping static resource");
            chain.doFilter(request, response);
            return;
        }

        System.out.println("   → Checking authentication");

        // Check nếu URL cần protection
        if (requiresAuthentication(path)) {
            System.out.println("🔒 AuthFilter: Checking authentication for: " + path);

            HttpSession session = httpRequest.getSession(false);
            boolean isLoggedIn = (session != null && session.getAttribute("customer") != null);

            if (!isLoggedIn) {
                System.out.println("❌ User not authenticated, redirecting to login");
                System.out.println("   Requested URL: " + requestURI);

                String redirectUrl = requestURI;
                String queryString = httpRequest.getQueryString();
                if (queryString != null && !queryString.isEmpty()) {
                    redirectUrl += "?" + queryString;
                }

                HttpSession newSession = httpRequest.getSession(true);
                newSession.setAttribute("redirectAfterLogin", redirectUrl);

                System.out.println("→ Saved redirect URL: " + redirectUrl);
                System.out.println("→ Redirecting to: " + contextPath + "/login");

                httpResponse.sendRedirect(contextPath + "/login");
                return;
            } else {
                String customerEmail = (String) session.getAttribute("customerEmail");
                System.out.println("✅ User authenticated: " + customerEmail);
                System.out.println("   Accessing: " + path);
            }
        }

        chain.doFilter(request, response);
    }

    private boolean requiresAuthentication(String path) {
        if (path.length() > 1 && path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }

        if (PROTECTED_URLS.contains(path)) {
            return true;
        }

        for (String pattern : PROTECTED_PATTERNS) {
            if (path.matches(pattern)) {
                return true;
            }
        }

        return false;
    }

    private boolean isStaticResource(String path) {
        return path.startsWith("/assets/") ||
                path.startsWith("/WEB-INF/") ||
                path.matches(".+\\.(css|js|jpg|jpeg|png|gif|svg|ico|woff|woff2|ttf|eot|webp|bmp)$");
    }

    @Override
    public void destroy() {
        System.out.println("❌ AuthFilter destroyed");
    }
}