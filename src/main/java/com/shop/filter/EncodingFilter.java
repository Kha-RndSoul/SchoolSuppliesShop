package com.shop.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * Filter để set UTF-8 encoding
 * CRITICAL FIX: Thêm dispatcherTypes để tránh loop khi forward
 */
@WebFilter(
        filterName = "EncodingFilter",
        urlPatterns = {"/*"},
        dispatcherTypes = {DispatcherType.REQUEST}  // ← QUAN TRỌNG: Chỉ chạy với REQUEST, KHÔNG chạy với FORWARD
)
public class EncodingFilter implements Filter {

    private static final String ENCODING = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("✅ EncodingFilter initialized - Encoding: " + ENCODING);
        System.out.println("→ Dispatcher: REQUEST only");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        System.out.println("🔵 EncodingFilter: " + path);

        // Skip static resources
        if (isStaticResource(path)) {
            System.out.println("   → Skipping static resource");
            chain.doFilter(request, response);
            return;
        }

        System.out.println("   → Setting encoding");

        request.setCharacterEncoding(ENCODING);
        response.setCharacterEncoding(ENCODING);

        chain.doFilter(request, response);
    }

    private boolean isStaticResource(String path) {
        return path.startsWith("/assets/") ||
                path.startsWith("/WEB-INF/") ||
                path.matches(".+\\.(css|js|jpg|jpeg|png|gif|svg|ico|woff|woff2|ttf|eot|webp|bmp)$");
    }

    @Override
    public void destroy() {
        System.out.println("❌ EncodingFilter destroyed");
    }
}