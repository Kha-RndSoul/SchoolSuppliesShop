package com.shop.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * Filter để set UTF-8 encoding cho tất cả request/response
 * EXCLUDE static resources (CSS, JS, images) để tránh override MIME type
 */
@WebFilter(filterName = "EncodingFilter", urlPatterns = {"/*"})
public class EncodingFilter implements Filter {

    private static final String ENCODING = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("✅ EncodingFilter initialized - Encoding: " + ENCODING);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        System.out.println("🔵 EncodingFilter: " + path); // ← THÊM DÒNG NÀY

        // Skip static resources
        if (isStaticResource(path)) {
            System.out.println("   → Skipping static resource"); // ← THÊM DÒNG NÀY
            chain.doFilter(request, response);
            return;
        }

        System.out.println("   → Setting encoding"); // ← THÊM DÒNG NÀY

        request.setCharacterEncoding(ENCODING);
        response.setCharacterEncoding(ENCODING);

        chain.doFilter(request, response);
    }

    /**
     * Check nếu là static resource (CSS, JS, images, fonts)
     */
    private boolean isStaticResource(String path) {
        return path.startsWith("/assets/") ||
                path.matches(".+\\.(css|js|jpg|jpeg|png|gif|svg|ico|woff|woff2|ttf|eot|webp|bmp)$");
    }

    @Override
    public void destroy() {
        System.out.println("❌ EncodingFilter destroyed");
    }
}