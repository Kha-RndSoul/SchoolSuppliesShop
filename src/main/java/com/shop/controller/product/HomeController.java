package com.shop.controller.product;

import com.shop.dao.product.CategoryDAO;
import com.shop.dao.product.ProductDAO;
import com.shop.dao.order.CouponDAO;
import com.shop.dao.support.BannerDAO;
import com.shop.model.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

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
            // 1. Lấy banners cho slider
            List<Banner> listBan = bannerDAO.getActive();
            request.setAttribute("listBan", listBan);

            // 2. Lấy tất cả categories cho navigation menu
            List<Category> listCategory = categoryDAO.getList();
            request.setAttribute("listCategory", listCategory);

            // 3. Lấy featured categories (top 4 cho section Danh Mục Nổi Bật)
            List<Category> featuredCategories = listCategory.stream()
                    .limit(4)
                    .toList();
            request.setAttribute("featuredCategories", featuredCategories);

            // 4. Lấy sản phẩm bán chạy (top 10)
            List<Product> bestSellingProducts = productDAO.getBestSellers(10);
            request. setAttribute("bestSellingProducts", bestSellingProducts);

            // 5. Lấy active coupons (top 4)
            List<Coupon> activeCoupons = couponDAO.getActiveCoupons();
            List<Coupon> topCoupons = activeCoupons.stream()
                    . limit(4)
                    . toList();
            request.setAttribute("topCoupons", topCoupons);

            // Forward to index.jsp
            request.getRequestDispatcher("/index. jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Có lỗi xảy ra khi tải trang chủ:  " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}