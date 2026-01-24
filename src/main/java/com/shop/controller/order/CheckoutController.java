package com.shop.controller.order;

import com.shop.model.Customer;
import com.shop.model.Coupon;
import com.shop.model.Order;
import com.shop.services.CartService;
import com.shop.services.CategoryService;
import com.shop.services.CouponService;
import com.shop.services.CustomerService;
import com.shop.services.OrderService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@WebServlet(name = "CheckoutController", urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {

    private CategoryService categoryService;
    private OrderService orderService;
    private CustomerService customerService;
    private CartService cartService;
    private CouponService couponService;

    @Override
    public void init() throws ServletException {
        this.categoryService = new CategoryService();
        this.orderService = new OrderService();
        this.customerService = new CustomerService();
        this.cartService = new CartService();
        this.couponService = new CouponService();
        System.out.println(" CheckoutController initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // If this is an AJAX coupon validation request, handle and return JSON then exit.
        String validateParam = request.getParameter("validateCoupon");
        if (validateParam != null) {
            handleCouponValidateAjax(request, response);
            return;
        }

        HttpSession session = request.getSession();
        Integer userId = resolveSessionUserId(session);

        try {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> cartView = (List<Map<String, Object>>) session.getAttribute("cart");
            if ((cartView == null || cartView.isEmpty()) && userId != null && userId > 0) {
                try {
                    cartView = cartService.buildCartViewForUser(userId);
                } catch (Exception ignored) {
                }
            }

            if (cartView == null || cartView.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            Integer cartTotal = null;
            Object ctObj = session.getAttribute("cartTotal");
            if (ctObj instanceof Number) cartTotal = ((Number) ctObj).intValue();
            else cartTotal = (cartView == null) ? 0 : calculateCartTotalFromView(cartView);

            int cartCount = calculateCartCountFromView(cartView);

            // Load current user profile (ưu tiên object trong session)
            Customer customer = null;
            Object custObj = session.getAttribute("customer");
            if (custObj instanceof Customer) {
                customer = (Customer) custObj;
            } else if (userId != null && userId > 0) {
                try {
                    customer = customerService.getCustomerById(userId);
                    if (customer != null) {
                        session.setAttribute("customer", customer);
                        session.setAttribute("user", customer);
                    }
                } catch (Exception e) {
                    // ignore loading error
                }
            }

            Object pending = session.getAttribute("pendingCheckoutForm");
            if (pending != null) {
                request.setAttribute("pendingCheckoutForm", pending);
            }

            request.setAttribute("listCategory", categoryService.getAllCategories());
            request.setAttribute("cart", cartView);

            int effectiveTotal = cartTotal != null ? cartTotal : 0;
            request.setAttribute("cartTotal", effectiveTotal);
            session.setAttribute("cartTotal", effectiveTotal);

            request.setAttribute("cartCount", cartCount);
            session.setAttribute("cartCount", cartCount);

            // Đồng bộ cart trong session (giống CartController behavior)
            session.setAttribute("cart", cartView);

            if (customer != null) request.setAttribute("currentUser", customer);

            try {
                List<Coupon> activeCoupons = couponService.getActiveCoupons();
                request.setAttribute("activeCoupons", activeCoupons);
            } catch (Exception e) {
                e.printStackTrace();
            }

            request.getRequestDispatcher("/WEB-INF/jsp/order/checkout.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi khi mở trang thanh toán: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }


    private void handleCouponValidateAjax(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String code = safeTrim(request.getParameter("code"));
        String orderAmountParam = safeTrim(request.getParameter("orderAmount"));
        BigDecimal orderAmount = BigDecimal.ZERO;

        try {
            if (!orderAmountParam.isEmpty()) {
                orderAmount = new BigDecimal(orderAmountParam);
            } else {
                Object ct = request.getSession().getAttribute("cartTotal");
                if (ct instanceof Number) orderAmount = BigDecimal.valueOf(((Number) ct).longValue());
            }
        } catch (Exception ignored) {
        }

        PrintWriter w = response.getWriter();

        if (code.isEmpty()) {
            w.write("{\"success\":false,\"message\":\"Mã coupon không được rỗng\"}");
            return;
        }

        try {
            Coupon coupon = couponService.validateAndApplyCoupon(code.trim().toUpperCase(), orderAmount);
            BigDecimal discount = couponService.calculateDiscount(coupon, orderAmount);
            long discountLong = discount.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();

            String message = "Áp dụng thành công: giảm " + discountLong + " đ";
            String json = String.format("{\"success\":true,\"discount\":%d,\"message\":\"%s\",\"couponCode\":\"%s\"}",
                    discountLong,
                    escapeJson(message),
                    escapeJson(coupon.getCouponCode())
            );
            w.write(json);
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage() != null ? e.getMessage() : "Mã không hợp lệ";
            w.write("{\"success\":false,\"message\":\"" + escapeJson(msg) + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            w.write("{\"success\":false,\"message\":\"Lỗi khi kiểm tra mã coupon\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Integer userId = resolveSessionUserId(session);

        String name = safeTrim(request.getParameter("name"));
        String phone = safeTrim(request.getParameter("phone"));
        String email = safeTrim(request.getParameter("email"));
        String address = safeTrim(request.getParameter("address"));
        String shipping = safeTrim(request.getParameter("shipping")); // standard|express
        String payment = safeTrim(request.getParameter("payment"));   // cod|online
        String couponCode = safeTrim(request.getParameter("couponCode"));

        try {
            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                request.setAttribute("errorMessage", "Vui lòng điền đầy đủ thông tin bắt buộc!");
                doGet(request, response);
                return;
            }

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> cartView = (List<Map<String, Object>>) session.getAttribute("cart");
            if ((cartView == null || cartView.isEmpty()) && (userId == null || userId <= 0)) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }


            Integer sessionCartTotal = null;
            Object ctObj = session.getAttribute("cartTotal");
            if (ctObj instanceof Number) sessionCartTotal = ((Number) ctObj).intValue();
            else sessionCartTotal = (cartView == null) ? 0 : calculateCartTotalFromView(cartView);
            int cartTotal = sessionCartTotal != null ? sessionCartTotal : 0;


            if (userId == null) {
                session.setAttribute("pendingCheckoutForm", Map.of(
                        "name", name,
                        "phone", phone,
                        "email", email,
                        "address", address,
                        "shipping", shipping,
                        "payment", payment,
                        "couponCode", couponCode
                ));
                response.sendRedirect(request.getContextPath() + "/login?redirect=/checkout");
                return;
            }


            Coupon validatedCoupon = null;
            if (!couponCode.isEmpty()) {
                try {
                    BigDecimal orderAmountBD = BigDecimal.valueOf(cartTotal);
                    validatedCoupon = couponService.validateAndApplyCoupon(couponCode.trim().toUpperCase(), orderAmountBD);

                } catch (IllegalArgumentException iae) {
                    request.setAttribute("errorMessage", iae.getMessage());
                    doGet(request, response);
                    return;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    request.setAttribute("errorMessage", "Lỗi khi kiểm tra mã coupon: " + ex.getMessage());
                    doGet(request, response);
                    return;
                }
            }

            int customerId = userId;
            if (customerId <= 0) {
                throw new IllegalStateException("Invalid customerId in session");
            }

            int shippingFee = "express".equalsIgnoreCase(shipping) ? 50000 : 25000;

            Order createdOrder = orderService.createOrder(
                    customerId,
                    couponCode.isEmpty() ? null : couponCode,
                    shippingFee,
                    name,
                    phone,
                    address,
                    (payment == null || payment.isEmpty()) ? "COD" : payment.toUpperCase(),
                    (shipping == null || shipping.isEmpty()) ? "standard" : shipping.toLowerCase(),
                    email.isEmpty() ? null : email
            );

            if (createdOrder == null || createdOrder.getId() <= 0) {
                throw new IllegalStateException("Tạo đơn hàng thất bại (orderId không hợp lệ).");
            }

            if (validatedCoupon != null) {
                try {
                    couponService.useCoupon(validatedCoupon.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            session.removeAttribute("cart");
            session.removeAttribute("cartTotal");
            session.removeAttribute("cartCount");
            session.removeAttribute("pendingCheckoutForm");

            session.setAttribute("lastOrderId", createdOrder.getId());

            response.sendRedirect(request.getContextPath() + "/order-confirmation");

        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", e.getMessage());
            doGet(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Có lỗi xảy ra khi đặt hàng: " + e.getMessage());
            doGet(request, response);
        }
    }

    @Override
    public void destroy() {
        System.out.println("✓ CheckoutController destroyed");
    }

    // Helpers

    private String safeTrim(String s) {
        return s == null ? "" : s.trim();
    }

    private Integer resolveSessionUserId(HttpSession session) {
        if (session == null) return null;
        Object v = session.getAttribute("userId");
        if (v instanceof Number) return ((Number) v).intValue();
        try { if (v != null) return Integer.parseInt(v.toString()); } catch (Exception ignored) {}

        v = session.getAttribute("customerId");
        if (v instanceof Number) return ((Number) v).intValue();
        try { if (v != null) return Integer.parseInt(v.toString()); } catch (Exception ignored) {}

        v = session.getAttribute("customer_id");
        if (v instanceof Number) return ((Number) v).intValue();
        try { if (v != null) return Integer.parseInt(v.toString()); } catch (Exception ignored) {}

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

    private int calculateCartTotalFromView(List<Map<String, Object>> view) {
        double total = 0;
        if (view == null) return 0;
        for (Map<String, Object> item : view) {
            Number qtyN = (Number) item.getOrDefault("quantity", 1);
            int qty = qtyN != null ? qtyN.intValue() : 1;
            Object saleObj = item.get("salePrice");
            double unit = 0;
            if (saleObj instanceof Number && ((Number) saleObj).doubleValue() > 0) {
                unit = ((Number) saleObj).doubleValue();
            } else {
                Object priceObj = item.get("price");
                if (priceObj instanceof Number) unit = ((Number) priceObj).doubleValue();
            }
            total += unit * qty;
        }
        return (int) Math.round(total);
    }

    private int calculateCartCountFromView(List<Map<String, Object>> view) {
        int count = 0;
        if (view == null) return 0;
        for (Map<String, Object> item : view) {
            Number qtyN = (Number) item.getOrDefault("quantity", 1);
            count += qtyN != null ? qtyN.intValue() : 0;
        }
        return count;
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}