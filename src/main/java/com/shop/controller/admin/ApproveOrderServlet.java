package com.shop.controller.admin;

import com.shop.dao.order.OrderDAO;
import com.shop.model.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ApproveOrderServlet", urlPatterns = {"/admin/approve-order"})
public class ApproveOrderServlet extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String orderIdStr = request.getParameter("orderId");

        if (orderIdStr == null || orderIdStr.trim().isEmpty()) {
            sendAjaxError(response, "Mã ID đơn hàng không hợp lệ.");
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdStr.trim());
            Order order = orderDAO.getOrderById(orderId);

            if (order == null) {
                sendAjaxError(response, "Đơn hàng không tồn tại trên hệ thống.");
                return;
            }

            if (order.getIsVerified() == null || order.getIsVerified() != 1) {
                sendAjaxError(response, "Đơn hàng chưa được xác minh chữ ký số hợp lệ. Không thể duyệt!");
                return;
            }

            if (!"PENDING".equalsIgnoreCase(order.getOrderStatus())) {
                sendAjaxError(response, "Đơn hàng này đã được xử lý từ trước.");
                return;
            }

            orderDAO.updateStatus(orderId, "CONFIRMED");

            response.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            e.printStackTrace();
            sendAjaxError(response, "Lỗi hệ thống: " + e.getMessage());
        }
    }

    private void sendAjaxError(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write(message);
    }
}