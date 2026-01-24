package com.shop.controller.product;

import com.shop.model.Banner;
import com.shop.model.Category;
import com.shop.model.Coupon;
import com.shop.services.BannerService;
import com.shop.services.CategoryService;
import com.shop.services.CouponService;
import com.shop.services.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "HomeController", urlPatterns = {"/home", ""}) // Map cả /home và root /
public class HomeController extends HttpServlet {

    private BannerService bannerService;
    private ProductService productService;
    private CategoryService categoryService;
    private CouponService couponService;

    @Override
    public void init() throws ServletException {
        // Khởi tạo các Service
        bannerService = new BannerService();
        productService = new ProductService();
        categoryService = new CategoryService();
        couponService = new CouponService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            System.out.println("========================================");
            System.out.println("HomeController.doGet() START");

            // 1. Load banners (Đã đồng bộ với Service mới)
            List<Banner> banners = bannerService.getActiveBanners();

            // Debug Banner
            System.out.println("--- BANNERS ---");
            if (banners != null && !banners.isEmpty()) {
                System.out.println("Found " + banners.size() + " active banners.");
                banners.forEach(b -> System.out.println("Banner ID: " + b.getId() + " | Img: " + b.getImageUrl()));
            } else {
                System.out.println("WARNING: No active banners found inside Controller!");
            }

            // Lưu vào request với tên "listBan" (Chú ý tên biến này để dùng trong JSP)
            request.setAttribute("listBan", banners);

            // 2. Load categories
            List<Category> allCategories = categoryService.getAllCategories();
            request.setAttribute("listCategory", allCategories);

            List<Category> featuredCategories = allCategories.size() > 4
                    ? allCategories.subList(0, 4)
                    : allCategories;
            request.setAttribute("featuredCategories", featuredCategories);

            // 3. Load best sellers
            List<Map<String, Object>> bestSellers = productService.getBestSellers(8);
            System.out.println("--- BEST SELLERS ---");
            if (bestSellers != null) {
                bestSellers.forEach(p -> {
                    System.out.println("ID: " + p.get("id") + " - " + p.get("productName"));
                });
            }
            request.setAttribute("bestSellingProducts", bestSellers);

            // 4. Load top 4 coupons hot nhất
            List<Coupon> topCoupons = couponService.getTopUsedCoupons(4);
            System.out.println("--- TOP COUPONS ---");
            if (topCoupons != null) {
                System.out.println("Coupons count: " + topCoupons.size());
            }
            request.setAttribute("topCoupons", topCoupons);

            System.out.println("Forwarding to /WEB-INF/jsp/products/index.jsp");
            System.out.println("========================================");

            // Forward đến trang JSP
            request.getRequestDispatcher("/WEB-INF/jsp/products/index.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERROR in HomeController: " + e.getMessage());
            request.setAttribute("errorMessage", "Lỗi khi tải trang chủ: " + e.getMessage());
            // Đảm bảo đường dẫn error.jsp đúng
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}