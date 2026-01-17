package com.shop.controller.product;

import com.shop.services.*;
import com.shop.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "HomeController", urlPatterns = {"/index"})
public class HomeController extends HttpServlet {

    private BannerService bannerService;
    private ProductService productService;
    private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
        bannerService = new BannerService();
        productService = new ProductService();
        categoryService = new CategoryService();
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

            List<Category> featuredCategories = allCategories.size() > 6
                    ? allCategories.subList(0, 6)
                    : allCategories;
            request.setAttribute("featuredCategories", featuredCategories);

            // Load best sellers
            List<Map<String, Object>> bestSellers = productService.getBestSellers(8);
            request.setAttribute("bestSellingProducts", bestSellers);

            // Load coupons (tạm thời empty)
            List<Object> topCoupons = new ArrayList<>();
            request.setAttribute("topCoupons", topCoupons);

            // Forward to index.jsp
            request.getRequestDispatcher("/index.jsp").forward(request,response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi khi tải trang chủ:  " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}