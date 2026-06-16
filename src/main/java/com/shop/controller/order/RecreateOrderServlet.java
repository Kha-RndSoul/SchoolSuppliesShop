package com.shop.controller.order;

import com.shop.model.Order;
import com.shop.services.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RecreateOrderServlet", urlPatterns = {"/recreate-order"})
public class RecreateOrderServlet extends HttpServlet {

    private final OrderService orderService = new OrderService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("customerId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int customerId = (int) session.getAttribute("customerId");
        String oldOrderIdStr = request.getParameter("oldOrderId");

        try {
            if (oldOrderIdStr == null || oldOrderIdStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Mã đơn hàng gốc không hợp lệ.");
            }

            int oldOrderId = Integer.parseInt(oldOrderIdStr.trim());
            Order newOrder = orderService.recreateOrder(oldOrderId, customerId);

            if (newOrder != null) {
                session.setAttribute("successMessage", "Đã tạo lại đơn hàng mới (" + newOrder.getOrderCode() + ") thành công! Vui lòng thực hiện ký số.");
            } else {
                throw new Exception("Hệ thống không thể khởi tạo bản sao đơn hàng.");
            }

        } catch (Exception e) {
            session.setAttribute("errorMessage", "Không thể đặt lại đơn hàng: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/profile#orders-section");
    }
}