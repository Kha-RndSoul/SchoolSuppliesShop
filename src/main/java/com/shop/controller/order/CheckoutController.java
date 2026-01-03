package com.shop.controller.order;

import java.math.BigDecimal;
import com.shop.dao.product.CategoryDAO;
import com.shop.dao.order.OrderDAO;
import com.shop.model.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.*;

@WebServlet(name = "CheckoutController", urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {

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

        // Lấy giỏ hàng từ session
        List<?> cart = (List<?>) session.getAttribute("cart");

        // Nếu giỏ hàng rỗng, chuyển về trang cart
        if (cart == null || cart.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        // Lấy tổng tiền giỏ hàng
        Integer cartTotal = (Integer) session.getAttribute("cartTotal");
        if (cartTotal == null) {
            cartTotal = 0;
        }

        // Lấy danh sách category cho menu
        request.setAttribute("listCategory", categoryDAO.getList());
        request.setAttribute("cart", cart);
        request.setAttribute("cartTotal", cartTotal);

        request.getRequestDispatcher("/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        try {
            // Lấy thông tin từ form
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            String shipping = request.getParameter("shipping");
            String payment = request.getParameter("payment");

            // Validate thông tin
            if (name == null || name.trim().isEmpty()
                    || phone == null || phone.trim().isEmpty()
                    || address == null || address.trim().isEmpty()) {

                request.setAttribute(
                        "errorMessage",
                        "Vui lòng điền đầy đủ thông tin bắt buộc!"
                );
                doGet(request, response);
                return;
            }

            // Lấy giỏ hàng từ session
            List<?> cart = (List<?>) session.getAttribute("cart");
            if (cart == null || cart.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            // Tính phí vận chuyển
            int shippingFee = "express".equals(shipping) ? 50000 : 25000;

            // Lấy tổng tiền
            Integer cartTotal = (Integer) session.getAttribute("cartTotal");
            if (cartTotal == null) {
                cartTotal = 0;
            }
            int totalAmount = cartTotal + shippingFee;

            // Tạo đơn hàng mới
            Order order = new Order();
            order.setCustomerId(session.getAttribute("userId") != null ? (int) session.getAttribute("userId") : 0);
            order.setOrderCode("ORD-" + System.currentTimeMillis());
            order.setOrderStatus("PENDING");
            order.setPaymentMethod(payment);
            order.setPaymentStatus("PENDING");
            order.setTotalAmount(new BigDecimal(totalAmount));
            order.setShippingName(name);
            order.setShippingPhone(phone);
            order.setShippingAddress(address);
            order.setNote(email != null ? "Email: " + email + " | Shipping: " + shipping : "Shipping: " + shipping);

            // Lưu đơn hàng vào database
            int orderId = orderDAO.insertOrder(order);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute(
                    "errorMessage",
                    "Có lỗi xảy ra: " + e.getMessage()
            );
            doGet(request, response);
        }
    }
}
