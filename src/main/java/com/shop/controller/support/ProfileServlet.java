package com.shop.controller.support;

import com.shop.dao.order.OrderDAO;
import com.shop.model.Customer;
import com.shop.model.Order;
import com.shop.services.CustomerService;
import com.shop.services.OrderService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile"})
public class ProfileServlet extends HttpServlet {

    private CustomerService customerService;
    private OrderDAO orderDAO;

    @Override
    public void init() {
        try {
            customerService = new CustomerService();
            orderDAO = new OrderDAO();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");

        if (customer == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            // Xử lý phân trang
            int page = 1;
            int recordsPerPage = 5;
            if (request.getParameter("page") != null) {
                try {
                    page = Integer.parseInt(request.getParameter("page"));
                    if (page < 1) page = 1;
                } catch (NumberFormatException e) {
                    page = 1;
                }
            }

            int offset = (page - 1) * recordsPerPage;

            OrderService orderService = new OrderService();
            orderService.reVerifyOrdersOnLoad(customer.getId());

            List<Map<String, Object>> orderHistory = orderDAO.getOrdersWithKeyTimeByCustomerId(customer.getId());
            request.setAttribute("orderHistory", orderHistory);

            int totalRecords = orderDAO.getTotalOrderCountByCustomerId(customer.getId());
            int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
            if (totalPages == 0) totalPages = 1;

            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);

            request.getRequestDispatcher("/WEB-INF/jsp/support/profile.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String formType = request.getParameter("formType");

        if ("updateProfile".equals(formType)) {
            handleUpdateProfile(request, response);
        } else if ("changePassword".equals(formType)) {
            handleChangePassword(request, response);
        }
    }

    private void handleUpdateProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");

        try {
            customer.setFullName(request.getParameter("fullName"));
            customer.setEmail(request.getParameter("email"));
            customer.setPhone(request.getParameter("phone"));
            customer.setAddress(request.getParameter("address"));

            // Gọi hàm update trong CustomerService của bạn
            customerService.updateCustomer(customer);
            session.setAttribute("customer", customer);
            request.setAttribute("success", "Cập nhật thông tin thành công!");
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi: " + e.getMessage());
        }
        doGet(request, response);
    }

    private void handleChangePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");

        String currentPass = request.getParameter("currentPassword");
        String newPass = request.getParameter("newPassword");

        try {
            customerService.updatePassword(customer.getId(), currentPass, newPass);
            request.setAttribute("successPassword", "Đổi mật khẩu thành công!");
        } catch (Exception e) {
            request.setAttribute("errorPassword", e.getMessage());
        }
        doGet(request, response);
    }
}