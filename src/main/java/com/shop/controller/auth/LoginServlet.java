package com.shop.controller.auth;

import com.shop.model.Admin;
import com.shop.model.Customer;
import com.shop.services.AdminService;
import com.shop.services.CartService; // [MỚI] Import CartService
import com.shop.services.CustomerService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

/**
 * Servlet xử lý đăng nhập cho cả Customer và Admin
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private CustomerService customerService;
    private AdminService adminService;
    private CartService cartService; // [MỚI] Khai báo CartService

    @Override
    public void init() {
        System.out.println("================================");
        System.out.println(" LoginServlet.init() STARTING...");

        try {
            System.out.println(" Creating CustomerService...");
            customerService = new CustomerService();

            System.out.println(" Creating AdminService...");
            adminService = new AdminService();

            System.out.println(" Creating CartService...");
            cartService = new CartService();

            System.out.println(" All Services initialized successfully");

        } catch (Exception e) {
            System.err.println(" ERROR in LoginServlet.init() ");
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Hiển thị trang login
        request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailOrUsername = request.getParameter("email");
        String password = request.getParameter("password");

        try {

            try {
                Customer customer = customerService.login(emailOrUsername, password);

                if (customer != null) {
                    // --- ĐĂNG NHẬP CUSTOMER THÀNH CÔNG ---
                    HttpSession session = request.getSession();

                    session.setAttribute("customer", customer);
                    session.setAttribute("customerName", customer.getFullName());

                    session.setAttribute("user", customer);
                    session.setAttribute("role", "customer");
                    session.setAttribute("email", customer.getEmail());

                    // --- LẤY SỐ LƯỢNG GIỎ HÀNG ---
                    try {
                        int count = cartService.getCartCount(customer.getId());
                        session.setAttribute("cartCount", count); // Khớp với header.jsp
                    } catch (Exception ex) {
                        session.setAttribute("cartCount", 0);
                    }

                    response.sendRedirect(request.getContextPath() + "/home");
                    return;
                } else {
                    throw new IllegalArgumentException("Thông tin đăng nhập không đúng.");
                }
            } catch (IllegalArgumentException e) {
                // Nếu login Customer thất bại, thử login Admin
                System.out.println("Login Customer failed, trying Admin...");

                Admin admin = adminService.login(emailOrUsername, password);
                if (admin != null) {
                    // --- ĐĂNG NHẬP ADMIN THÀNH CÔNG ---
                    HttpSession session = request.getSession();
                    session.setAttribute("admin", admin);
                    session.setAttribute("role", "admin");
                    session.setAttribute("adminFullName", admin.getFullName());
                    session.setAttribute("adminUsername", admin.getUsername());

                    // Chuyển hướng về trang quản trị
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                    return;
                }
                // Nếu cả 2 đều sai
                throw new IllegalArgumentException("Email hoặc mật khẩu không chính xác.");
            }
        } catch (IllegalArgumentException e) {
            // Xử lý lỗi đăng nhập thất bại chung
            request.setAttribute("error", e.getMessage());
            request.setAttribute("email", emailOrUsername);
            request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp").forward(request, response);

        } catch (Exception e) {
            // Lỗi hệ thống
            e.printStackTrace();
            request.setAttribute("error", "Đã có lỗi hệ thống xảy ra.");
            request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        System.out.println(" LoginServlet destroyed");
    }
}