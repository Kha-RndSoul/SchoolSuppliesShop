package com.shop.controller.support;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

/**
 * Servlet xử lý trang giới thiệu
 */
@WebServlet(name = "AboutServlet", urlPatterns = {"/about"})
public class AboutServlet extends HttpServlet {

    @Override
    public void init() {
        System.out.println("================================");
        System.out.println("✓ AboutServlet.init() STARTING...");
        System.out.println("✓ AboutServlet initialized");
        System.out.println("================================");
    }

    /**
     * GET /about - Hiển thị trang giới thiệu
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("================================");
        System.out.println("✓ AboutServlet.doGet() CALLED");
        System.out.println("   Request URI: " + request.getRequestURI());
        System.out.println("   Context Path: " + request.getContextPath());
        System.out.println("================================");

        // Forward tới about.jsp
        System.out.println("→ Forwarding to /WEB-INF/jsp/support/about.jsp");
        request.getRequestDispatcher("/WEB-INF/jsp/support/about.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("✓ AboutServlet destroyed");
    }
}