package com.shop.controller.order;

import com.shop.dao.order.CouponDAO;
import com.shop.model.Coupon;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * PromotionController (placed under order package as requested)
 * URL: /promotions
 * Minimal logic: chỉ lấy dữ liệu từ CouponDAO và forward tới JSP.
 */
@WebServlet(name = "PromotionController", urlPatterns = {"/promotions"})
public class PromotionController extends HttpServlet {

    private CouponDAO couponDAO;

    @Override
    public void init() throws ServletException {
        couponDAO = new CouponDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy danh sách coupons đang active (có thể đổi sang getList() nếu bạn muốn tất cả)
            List<Coupon> promotions = couponDAO.getActiveCoupons();

            request.setAttribute("promotions", promotions);

            request.getRequestDispatcher("/WEB-INF/jsp/order/promotion.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi khi tải chương trình khuyến mãi: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Giữ consistent với các controller khác
        doGet(request, response);
    }
}