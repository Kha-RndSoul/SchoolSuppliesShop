package com.shop.controller.order;

import com.shop.model.Order;
import com.shop.services.CategoryService;
import com.shop.services.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

@WebServlet(name = "OrderConfirmationController", urlPatterns = {"/order-confirmation"})
public class OrderConfirmationController extends HttpServlet {

    // Fix 1: Thay System.out/err bằng Logger
    private static final Logger LOGGER = Logger.getLogger(OrderConfirmationController.class.getName());

    // Fix 2: Định nghĩa constant thay vì lặp lại literal string
    private static final String ERROR_MESSAGE_ATTR = "errorMessage";

    private CategoryService categoryService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        this.categoryService = new CategoryService();
        this.orderService = new OrderService();
        // Fix 1: Dùng logger thay System.out
        LOGGER.info("✓ OrderConfirmationController initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Integer userId = resolveSessionUserId(session);

        try {
            Integer orderId = resolveOrderId(session, request);

            if (orderId != null) {
                processOrder(request, session, orderId, userId);
            } else {
                request.setAttribute(ERROR_MESSAGE_ATTR, "Không có mã đơn hàng để hiển thị.");
            }

            request.setAttribute("listCategory", categoryService.getAllCategories());
            request.getRequestDispatcher("/WEB-INF/jsp/order/order-confirmation.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi tải trang xác nhận đơn", e);
            request.setAttribute(ERROR_MESSAGE_ATTR, "Lỗi khi tải trang xác nhận đơn: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    // Fix 3: Tách logic lấy orderId thành method riêng để giảm Cognitive Complexity của doGet
    private Integer resolveOrderId(HttpSession session, HttpServletRequest request) {
        Integer orderId = sessionAttributeAsInt(session, "lastOrderId");
        if (orderId != null) {
            return orderId;
        }
        return parseOrderIdFromParam(request.getParameter("orderId"));
    }

    // Fix 4: Tách logic parse orderId từ param thành method riêng
    private Integer parseOrderIdFromParam(String param) {
        if (param == null || param.trim().isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(param.trim());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid orderId param: {0}", param);
            return null;
        }
    }

    // Fix 5: Tách logic xử lý order thành method riêng để giảm Cognitive Complexity của doGet
    private void processOrder(HttpServletRequest request, HttpSession session,
                              Integer orderId, Integer userId) {
        Order order = fetchOrder(orderId, userId);
        if (order != null) {
            request.setAttribute("order", order);
            if (session != null) {
                session.removeAttribute("lastOrderId");
            }
        } else {
            request.setAttribute(ERROR_MESSAGE_ATTR, "Không tìm thấy đơn hàng hoặc bạn không có quyền xem.");
        }
    }

    // Fix 6: Tách logic fetch order thành method riêng
    private Order fetchOrder(Integer orderId, Integer userId) {
        try {
            if (userId != null && userId > 0) {
                return orderService.getById(orderId, userId);
            } else {
                return orderService.getById(orderId);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Order fetch/authorization failed for orderId={0}: {1}",
                    new Object[]{orderId, ex.getMessage()});
            return null;
        }
    }

    private Integer sessionAttributeAsInt(HttpSession session, String name) {
        if (session == null) return null;
        Object v = session.getAttribute(name);
        if (v instanceof Number) return ((Number) v).intValue();
        try {
            return v != null ? Integer.parseInt(v.toString()) : null;
        } catch (Exception e) {
            return null;
        }
    }

    // Fix 7: Tách resolveSessionUserId thành các method nhỏ để giảm Cognitive Complexity
    private Integer resolveSessionUserId(HttpSession session) {
        if (session == null) return null;

        Integer id = resolveUserIdFromAttributes(session);
        if (id != null) return id;

        return resolveUserIdFromCustomerObject(session);
    }

    private Integer resolveUserIdFromAttributes(HttpSession session) {
        Integer id = sessionAttributeAsInt(session, "userId");
        if (id != null) return id;

        id = sessionAttributeAsInt(session, "customerId");
        if (id != null) return id;

        return sessionAttributeAsInt(session, "customer_id");
    }

    // Fix 8: Tách logic lấy userId từ customer object thành method riêng
    private Integer resolveUserIdFromCustomerObject(HttpSession session) {
        Object cust = session.getAttribute("customer");
        if (cust == null) return null;

        try {
            if (cust instanceof java.util.Map) {
                return resolveIdFromMap((java.util.Map<?, ?>) cust);
            } else {
                return resolveIdFromReflection(cust);
            }
        } catch (Exception ignored) {
            return null;
        }
    }

    private Integer resolveIdFromMap(java.util.Map<?, ?> map) {
        Object idObj = map.get("id");
        if (idObj instanceof Number) return ((Number) idObj).intValue();
        if (idObj != null) {
            try { return Integer.parseInt(idObj.toString()); } catch (Exception e) { return null; }
        }
        return null;
    }

    // Fix 9: Tách logic reflection thành method riêng
    private Integer resolveIdFromReflection(Object cust) {
        try {
            java.lang.reflect.Method m = cust.getClass().getMethod("getId");
            Object idObj = m.invoke(cust);
            if (idObj instanceof Number) return ((Number) idObj).intValue();
            if (idObj != null) return Integer.parseInt(idObj.toString());
        } catch (NoSuchMethodException ignore) {
            // Không có method getId, bỏ qua
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Reflection error resolving userId", e);
        }
        return null;
    }
}