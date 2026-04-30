package com.shop.controller.order;

import com.shop.model.Order;
import com.shop.services.CategoryService;
import com.shop.services.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "OrderConfirmationController", urlPatterns = {"/order-confirmation"})
public class OrderConfirmationController extends HttpServlet {

    private CategoryService categoryService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        this.categoryService = new CategoryService();
        this.orderService = new OrderService();
        System.out.println("✓ OrderConfirmationController initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Integer userId = resolveSessionUserId(session);

        try {
            // Lấy orderId ưu tiên từ session (được set sau khi đặt hàng),
            // nếu không có thì thử đọc từ param ?orderId=...
            Integer orderId = sessionAttributeAsInt(session, "lastOrderId");
            if (orderId == null) {
                String param = request.getParameter("orderId");
                if (param != null && !param.trim().isEmpty()) {
                    try { orderId = Integer.parseInt(param.trim()); } catch (NumberFormatException ignored) { }
                }
            }

            if (orderId != null) {
                Order order = null;
                try {
                    // Nếu có userId, dùng overload kiểm tra owner để tăng an toàn
                    if (userId != null && userId > 0) {
                        order = orderService.getById(orderId, userId);
                    } else {
                        order = orderService.getById(orderId);
                    }
                } catch (Exception ex) {
                    // Nếu không phải owner hoặc có lỗi, log và để order = null
                    System.err.println(" Order fetch/authorization failed for orderId=" + orderId + ": " + ex.getMessage());
                }

                if (order != null) {
                    request.setAttribute("order", order);
                    // Xóa session lastOrderId để tránh hiển thị lặp lại
                    if (session != null) session.removeAttribute("lastOrderId");
                } else {
                    request.setAttribute("errorMessage", "Không tìm thấy đơn hàng hoặc bạn không có quyền xem.");
                }
            } else {
                request.setAttribute("errorMessage", "Không có mã đơn hàng để hiển thị.");
            }

            // Lấy danh mục cho header/menu giống các controller khác
            request.setAttribute("listCategory", categoryService.getAllCategories());

            // Forward đến JSP (theo cấu trúc của các controller khác)
            request.getRequestDispatcher("/WEB-INF/jsp/order/order-confirmation.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi khi tải trang xác nhận đơn: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }


    private Integer sessionAttributeAsInt(HttpSession session, String name) {
        if (session == null) return null;
        Object v = session.getAttribute(name);
        if (v instanceof Number) return ((Number) v).intValue();
        try { return v != null ? Integer.parseInt(v.toString()) : null; } catch (Exception e) { return null; }
    }

    private Integer resolveSessionUserId(HttpSession session) {
        if (session == null) return null;
        // check common attribute names
        Integer id = sessionAttributeAsInt(session, "userId");
        if (id != null) return id;
        id = sessionAttributeAsInt(session, "customerId");
        if (id != null) return id;
        id = sessionAttributeAsInt(session, "customer_id");
        if (id != null) return id;

        Object cust = session.getAttribute("customer");
        if (cust != null) {
            try {
                if (cust instanceof java.util.Map) {
                    Object idObj = ((java.util.Map<?,?>) cust).get("id");
                    if (idObj instanceof Number) return ((Number) idObj).intValue();
                    if (idObj != null) return Integer.parseInt(idObj.toString());
                } else {
                    try {
                        java.lang.reflect.Method m = cust.getClass().getMethod("getId");
                        Object idObj = m.invoke(cust);
                        if (idObj instanceof Number) return ((Number) idObj).intValue();
                        if (idObj != null) return Integer.parseInt(idObj.toString());
                    } catch (NoSuchMethodException ignore) { }
                }
            } catch (Exception ignored) {}
        }
        return null;
    }
}