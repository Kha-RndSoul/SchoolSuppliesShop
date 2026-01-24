package com.shop.controller.admin;

import com.shop.dao.product.ProductDAO;
import com.shop.model.Banner;
import com.shop.services.BannerService;
import com.shop.services.BrandService;
import com.shop.services.CategoryService;
import com.shop.services.OrderService;
import com.shop.services.StatisticsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Servlet xử lý trang Admin Dashboard
 */
@WebServlet(name = "AdminDashboardServlet", urlPatterns = {"/admin/dashboard", "/admin"})
public class AdminDashboardServlet extends HttpServlet {

    private StatisticsService statisticsService;
    private OrderService orderService;
    private ProductDAO productDAO;
    private CategoryService categoryService;
    private BrandService brandService;
    private BannerService bannerService; // <--- Khai báo service

    @Override
    public void init() throws ServletException {
        System.out.println("================================");
        System.out.println(" AdminDashboardServlet initialized");
        System.out.println("   URL Patterns: /admin, /admin/dashboard");
        System.out.println("================================");
        // Khởi tạo service
        statisticsService = new StatisticsService();
        orderService = new OrderService();
        productDAO = new ProductDAO();
        categoryService = new CategoryService();
        brandService = new BrandService();
        bannerService = new BannerService(); // <--- Khởi tạo service
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println(" AdminDashboardServlet.doGet() CALLED");
        System.out.println("   Request URI: " + request.getRequestURI());
        try {
            // Get admin info from session
            HttpSession session = request.getSession();
            String adminUsername = (String) session.getAttribute("adminUsername");
            String adminRole = (String) session.getAttribute("adminRole");
            String adminFullName = (String) session.getAttribute("adminFullName");

            System.out.println(" Admin Info:");
            System.out.println("   Username: " + adminUsername);
            System.out.println("   Role: " + adminRole);
            System.out.println("   Full Name: " + adminFullName);
            // Lấy filter parameters từ request
            String filterType = request.getParameter("filter");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");

            System.out.println(" Filter Parameters:");
            System.out.println("   Filter Type: " + filterType);
            System.out.println("   Start Date: " + startDate);
            System.out.println("   End Date: " + endDate);

            // 1. Load dashboard statistics
            System.out.println("→ Loading statistics...");
            Map<String, Object> stats = statisticsService.getStatistics(filterType, startDate, endDate);
            System.out.println(" Statistics loaded successfully");
            // 2. Load recent orders
            System.out.println("→ Loading recent orders...");
            List<Map<String, Object>> recentOrders = orderService.getRecentOrdersWithCustomer(5);
            System.out.println(" Recent orders loaded: " + recentOrders.size() + " orders");
            // 3. Load best selling products
            System.out.println("→ Loading best selling products...");
            List<Map<String, Object>> bestSellers = productDAO.getBestSellersWithImage(5);
            System.out.println(" Best sellers loaded: " + bestSellers.size() + " products");
            // 4. Load categories and brands
            System.out.println("→ Loading categories and brands...");
            var allCategories = categoryService.getAllCategories();
            var allBrands = brandService.getAllBrands();
            System.out.println(" Categories: " + allCategories.size() + ", Brands: " + allBrands.size());
            // 5. Load Banners (MỚI THÊM)
            System.out.println("→ Loading banners...");
            List<Banner> listBanners = bannerService.getAllBanners();
            System.out.println(" Banners loaded: " + listBanners.size());
            // --- Set attributes for JSP ---
            request.setAttribute("stats", stats);
            request.setAttribute("recentOrders", recentOrders);
            request.setAttribute("bestSellers", bestSellers);
            request.setAttribute("allCategories", allCategories);
            request.setAttribute("allBrands", allBrands);
            // Đẩy dữ liệu Banner sang JSP
            request.setAttribute("listBanners", listBanners);
            // Set filter values
            request.setAttribute("selectedFilter", filterType != null ? filterType : "30days");
            request.setAttribute("customStartDate", startDate);
            request.setAttribute("customEndDate", endDate);

            System.out.println(" Dashboard Stats Loaded:");
            System.out.println("   Revenue: " + stats.get("revenue"));
            System.out.println("   New Orders: " + stats.get("newOrders"));
            System.out.println("   Best Sellers: " + bestSellers.size() + " products");
            System.out.println("   Banners Count: " + listBanners.size()); // Log kiểm tra
            System.out.println("   Display Filter: " + stats.get("displayFilter"));
            System.out.println("   Date Range: " + stats.get("startDate") + " - " + stats.get("endDate"));
            System.out.println(" Forwarding to /WEB-INF/jsp/admin/admin.jsp");
            // Forward to admin JSP
            request.getRequestDispatcher("/WEB-INF/jsp/admin/admin.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println(" ERROR in AdminDashboardServlet.doGet() ");
            System.err.println("Error message: " + e.getMessage());
            System.err.println("Error type: " + e.getClass().getName());
            e.printStackTrace();
            // Set error message
            request.setAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
            // Forward to error page
            request.getRequestDispatcher("/WEB-INF/jsp/error/error.jsp").forward(request, response);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // POST request sẽ redirect về GET với parameters
        String filterType = request.getParameter("filter");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        StringBuilder redirectUrl = new StringBuilder(request.getContextPath() + "/admin/dashboard");
        redirectUrl.append("?filter=").append(filterType != null ? filterType : "30days");

        if ("custom".equals(filterType) && startDate != null && endDate != null) {
            redirectUrl.append("&startDate=").append(startDate);
            redirectUrl.append("&endDate=").append(endDate);
        }
        // Thêm hash để quay về dashboard section
        redirectUrl.append("#dashboard-section");
        response.sendRedirect(redirectUrl.toString());
    }

    @Override
    public void destroy() {
        System.out.println(" AdminDashboardServlet destroyed");
    }
}