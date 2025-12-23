package com.shop.controller.product;

import com.shop.dao.product.CategoryDAO;
import com.shop.dao.product.ProductDAO;
import com.shop.dao.order.CouponDAO;
import com. shop.dao.support.BannerDAO;
import com. shop.model.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.*;

@WebServlet(name = "HomeController", urlPatterns = {"", "/index", "/home"})
public class HomeController extends HttpServlet {

    private BannerDAO bannerDAO;
    private CategoryDAO categoryDAO;
    private ProductDAO productDAO;
    private CouponDAO couponDAO;

    @Override
    public void init() throws ServletException {
        bannerDAO = new BannerDAO();
        categoryDAO = new CategoryDAO();
        productDAO = new ProductDAO();
        couponDAO = new CouponDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 1. Lấy banners
            List<Banner> listBan = bannerDAO.getActive();
            request.setAttribute("listBan", listBan);

            // 2. Lấy categories
            List<Category> listCategory = categoryDAO.getList();
            request.setAttribute("listCategory", listCategory);

            // 3. Featured categories
            List<Category> featuredCategories = listCategory.stream().limit(4).toList();
            request.setAttribute("featuredCategories", featuredCategories);

            // 4. Best sellers - TRẢ VỀ Map<String, Object>
            List<Map<String, Object>> bestSellingProducts = productDAO.getBestSellersWithImage(10);
            request.setAttribute("bestSellingProducts", bestSellingProducts);

            // 5. Active coupons
            List<Coupon> activeCoupons = couponDAO. getActiveCoupons();
            List<Coupon> topCoupons = activeCoupons.stream().limit(4).toList();
            request.setAttribute("topCoupons", topCoupons);

            request.getRequestDispatcher("/index.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Có lỗi xảy ra:  " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}