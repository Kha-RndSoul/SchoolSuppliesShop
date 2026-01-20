package com.shop.controller.product;

import com.shop.services.*;
import com.shop.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "HomeController", urlPatterns = { "/"})
public class HomeController extends HttpServlet {

    private BannerService bannerService;
    private ProductService productService;
    private CategoryService categoryService;
    private CouponService couponService;

    @Override
    public void init() throws ServletException {
        bannerService = new BannerService();
        productService = new ProductService();
        categoryService = new CategoryService();
        couponService = new CouponService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Load banners
            List<Banner> banners = bannerService.getActiveBanners();
            request.setAttribute("listBan", banners);

            // Load categories
            List<Category> allCategories = categoryService.getAllCategories();
            request.setAttribute("listCategory", allCategories);

            List<Category> featuredCategories = allCategories.size() > 4
                    ? allCategories.subList(0, 4)
                    : allCategories;
            request.setAttribute("featuredCategories", featuredCategories);

            // Load best sellers
            List<Map<String, Object>> bestSellers = productService.getBestSellers(8);
            // debug
            System.out.println("=== BEST SELLERS ===");
            bestSellers.forEach(p -> {
                System.out.println("ID: " + p.get("id"));
                System.out.println("Name: " + p.get("productName"));
                System.out.println("---");
            });
            request.setAttribute("bestSellingProducts", bestSellers);

            //  Load top 4 coupons hot nhất
            List<Coupon> topCoupons = couponService.getTopUsedCoupons(4);

            // Debug để kiểm tra
            System.out.println("=== TOP COUPONS ===");
            System.out.println("Số lượng coupons: " + topCoupons.size());
            topCoupons.forEach(c -> {
                System.out.println("Code: " + c.getCouponCode());
                System.out.println("Discount: " + c.getDiscountValue());
                System.out.println("Used: " + c.getUsedCount());
                System.out.println("---");
            });

            request.setAttribute("topCoupons", topCoupons);

            //  Forward đến WEB-INF/jsp/product/index.jsp
            request.getRequestDispatcher("/WEB-INF/jsp/products/index.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi khi tải trang chủ: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}