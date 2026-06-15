package com.shop.controller.order;

import com.shop.dao.order.OrderDAO;
import com.shop.model.Customer;
import com.shop.model.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "SignatureToolServlet", urlPatterns = {"/signature-tool"})
public class SignatureToolServlet extends HttpServlet {

    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Customer customer = getCustomer(request, response);
        if (customer == null) return;

        try {
            List<Order> unsignedOrders = orderDAO.getUnsignedByCustomerId(customer.getId());
            request.setAttribute("unsignedOrders", unsignedOrders);

            String orderIdStr = request.getParameter("orderId");
            if (orderIdStr != null && !orderIdStr.trim().isEmpty()) {
                int orderId = Integer.parseInt(orderIdStr.trim());
                Order selectedOrder = orderDAO.getOrderById(orderId);

                if (selectedOrder == null) {
                    request.setAttribute("error", "Không tìm thấy đơn hàng.");
                } else if (selectedOrder.getCustomerId() != customer.getId()) {
                    request.setAttribute("error", "Bạn không có quyền xem đơn hàng này.");
                } else if (selectedOrder.getSignature() != null
                        && !selectedOrder.getSignature().trim().isEmpty()) {
                    request.setAttribute("error", "Đơn hàng này đã được ký.");
                } else {
                    request.setAttribute("selectedOrder", selectedOrder);
                }
            }

            String success = request.getParameter("success");
            String error = request.getParameter("error");

            if (success != null && !success.trim().isEmpty()) {
                request.setAttribute("success", success);
            }

            if (error != null && !error.trim().isEmpty()) {
                request.setAttribute("error", error);
            }

            request.getRequestDispatcher("/WEB-INF/jsp/order/signature-tool.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Mã đơn hàng không hợp lệ.");
            request.getRequestDispatcher("/WEB-INF/jsp/order/signature-tool.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi tải công cụ ký đơn: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/order/signature-tool.jsp")
                    .forward(request, response);
        }
    }

    private Customer getCustomer(HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("customer") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return null;
        }

        return (Customer) session.getAttribute("customer");
    }
}