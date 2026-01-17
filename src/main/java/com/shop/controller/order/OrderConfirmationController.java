package com.shop.controller.order;

import com.shop.dao.product.CategoryDAO;
import com.shop.dao.order.OrderDAO;
import com.shop.model.Order;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "OrderConfirmationController", urlPatterns = {"/order-confirmation"})
public class OrderConfirmationController extends HttpServlet {

    private CategoryDAO categoryDAO;
    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        categoryDAO = new CategoryDAO();
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // Lấy orderId từ session (được set sau khi đặt hàng thành công)
        Integer orderId = (Integer) session.getAttribute("lastOrderId");

        if (orderId != null) {
            // Lấy thông tin đơn hàng từ database
            Order order = orderDAO.getOrderById(orderId);
            request.setAttribute("order", order);

            // Xóa lastOrderId khỏi session sau khi đã hiển thị
            session.removeAttribute("lastOrderId");
        }

        // Lấy danh sách category cho menu
        request.setAttribute("listCategory", categoryDAO.getList());

        request.getRequestDispatcher("/order-confirmation.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
