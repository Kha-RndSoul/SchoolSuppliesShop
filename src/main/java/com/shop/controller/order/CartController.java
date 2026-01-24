package com.shop.controller.order;

import com.shop.model.CartItem;
import com.shop.services.CartService;
import com.shop.services.CategoryService;
import com.shop.services.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "CartController", urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    private CategoryService categoryService;
    private ProductService productService;
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        this.categoryService = new CategoryService();
        this.productService = new ProductService();
        this.cartService = new CartService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = resolveSessionUserId(session);

        List<Map<String, Object>> cartView = buildCartView(session, userId);
        int cartTotal = calculateCartTotalFromView(cartView);
        int cartCount = calculateCartCount(cartView);

        request.setAttribute("cart", cartView);
        request.setAttribute("cartTotal", cartTotal);
        request.setAttribute("cartCount", cartCount);
        request.setAttribute("listCategory", categoryService.getAllCategories());

        session.setAttribute("cart", cartView);
        session.setAttribute("cartTotal", cartTotal);
        session.setAttribute("cartCount", cartCount);

        request.getRequestDispatcher("/WEB-INF/jsp/order/cart.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Integer userId = resolveSessionUserId(session);
        // Đọc action
        String action = Optional.ofNullable(request.getParameter("action")).orElse("add").trim();
        String buyNow = request.getParameter("buyNow");

        try {
            switch (action) {
                case "add":
                    handleAdd(request, session, userId);
                    break;
                case "update":
                    handleUpdate(request, session, userId);
                    break;
                case "remove":
                    handleRemove(request, session, userId);
                    break;
                case "clear":
                    handleClear(request, session, userId);
                    break;
                default:

            }
            // Cập nhật lại Session
            List<Map<String, Object>> cartView = buildCartView(session, userId);
            int cartTotal = calculateCartTotalFromView(cartView);
            int cartCount = calculateCartCount(cartView);
            session.setAttribute("cart", cartView);
            session.setAttribute("cartTotal", cartTotal);
            session.setAttribute("cartCount", cartCount);

            // 1. Nếu là Mua ngay (buyNow=true) Chuyển hướng ngay tới Giỏ hàng
            if ("true".equals(buyNow)) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            // 2. Thêm vào giỏ hàng từ nút Add to Cart
            String xhr = request.getHeader("X-Requested-With");
            if ("XMLHttpRequest".equalsIgnoreCase(xhr)) {
                response.setContentType("application/json;charset=UTF-8");
                String json = String.format("{\"success\":true,\"cartTotal\":%d,\"cartCount\":%d,\"items\":%d}",
                        cartTotal, cartCount, cartView.size());
                response.getWriter().write(json);
                return;
            }
            // 3.  Nếu không phải 2 trường hợp trên
            // Quay lại trang hiện tại hoặc về giỏ hàng
            String referer = request.getHeader("Referer");
            if (referer != null && !referer.isEmpty() && !referer.contains("/login")) {
                response.sendRedirect(referer);
            } else {
                response.sendRedirect(request.getContextPath() + "/cart");
            }

        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", e.getMessage());
            doGet(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi xử lý giỏ hàng: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void handleAdd(HttpServletRequest request, HttpSession session, Integer userId) throws Exception {
        String productIdStr = request.getParameter("productId");
        String qtyStr = request.getParameter("quantity");

        if (productIdStr == null || productIdStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Thiếu productId");
        }
        int productId = Integer.parseInt(productIdStr.trim());
        int quantity = 1;
        if (qtyStr != null && !qtyStr.trim().isEmpty()) {
            try { quantity = Integer.parseInt(qtyStr.trim()); } catch (NumberFormatException ignored) {}
            if (quantity < 1) quantity = 1;
        }

        if (userId != null && userId > 0) {
            cartService.addToCart(userId, productId, quantity);
        } else {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");
            if (cart == null) {
                cart = new ArrayList<>();
                session.setAttribute("cart", cart);
            }
            Map<String, Object> existing = findCartItem(cart, productId);
            if (existing != null) {
                int cur = ((Number) existing.getOrDefault("quantity", 1)).intValue();
                existing.put("quantity", cur + quantity);
            } else {
                Map<String, Object> product = productService.getProductById(productId);
                if (product == null) {
                    throw new IllegalArgumentException("Sản phẩm không tồn tại");
                }
                Map<String, Object> item = new LinkedHashMap<>();
                item.put("id", product.get("id"));
                item.put("productName", product.get("productName"));
                item.put("imageUrl", product.get("imageUrl"));
                item.put("price", product.get("price"));
                item.put("salePrice", product.get("salePrice"));
                item.put("quantity", quantity);
                cart.add(item);
            }
        }
    }

    private void handleUpdate(HttpServletRequest request, HttpSession session, Integer userId) throws Exception {
        String productIdStr = request.getParameter("productId");
        String qtyStr = request.getParameter("quantity");
        String absolute = request.getParameter("absolute");

        if (productIdStr == null || productIdStr.trim().isEmpty() || qtyStr == null || qtyStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Thiếu params update");
        }
        int productId = Integer.parseInt(productIdStr.trim());
        int qty = Integer.parseInt(qtyStr.trim());

        if (userId != null && userId > 0) {
            if ("true".equalsIgnoreCase(absolute)) {
                List<CartItem> items = cartService.getCartItems(userId);
                int cur = items.stream().filter(i -> i.getProductId() == productId).mapToInt(CartItem::getQuantity).findFirst().orElse(0);
                int delta = qty - cur;
                if (delta > 0) cartService.incrementQuantity(userId, productId, delta);
                else if (delta < 0) cartService.decrementQuantity(userId, productId, -delta);
            } else {
                if (qty > 0) cartService.incrementQuantity(userId, productId, qty);
                else if (qty < 0) cartService.decrementQuantity(userId, productId, -qty);
            }
        } else {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");
            if (cart == null) throw new IllegalArgumentException("Giỏ hàng rỗng");
            Map<String, Object> existing = findCartItem(cart, productId);
            if (existing == null) throw new IllegalArgumentException("Sản phẩm không có trong giỏ hàng");
            if ("true".equalsIgnoreCase(absolute)) {
                if (qty <= 0) cart.remove(existing); else existing.put("quantity", qty);
            } else {
                int cur = ((Number) existing.getOrDefault("quantity", 1)).intValue();
                int next = cur + qty;
                if (next <= 0) cart.remove(existing); else existing.put("quantity", next);
            }
        }
    }

    private void handleRemove(HttpServletRequest request, HttpSession session, Integer userId) throws Exception {
        String productIdStr = request.getParameter("productId");
        if (productIdStr == null || productIdStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Thiếu productId");
        }
        int productId = Integer.parseInt(productIdStr.trim());

        if (userId != null && userId > 0) {
            cartService.removeByProductId(userId, productId);
        } else {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");
            if (cart != null) cart.removeIf(item -> {
                Object id = item.get("id");
                if (id instanceof Number) return ((Number) id).intValue() == productId;
                try { return Integer.parseInt(id.toString()) == productId; } catch (Exception e) { return false; }
            });
        }
    }

    private void handleClear(HttpServletRequest request, HttpSession session, Integer userId) throws Exception {
        if (userId != null && userId > 0) {
            cartService.clearCart(userId);
        }
        session.removeAttribute("cart");
        session.removeAttribute("cartTotal");
        session.removeAttribute("cartCount");
    }

    private List<Map<String, Object>> buildCartView(HttpSession session, Integer userId) {
        List<Map<String, Object>> view = new ArrayList<>();
        if (userId != null && userId > 0) {
            try {
                List<CartItem> items = cartService.getCartItems(userId);
                if (items != null) {
                    for (CartItem ci : items) {
                        int pid = ci.getProductId();
                        Map<String, Object> prod = productService.getProductById(pid);
                        Map<String, Object> item = new LinkedHashMap<>();
                        item.put("id", pid);
                        item.put("productName", prod != null ? prod.get("productName") : "Sản phẩm");
                        item.put("imageUrl", prod != null ? prod.get("imageUrl") : null);
                        item.put("price", prod != null ? prod.get("price") : 0);
                        item.put("salePrice", prod != null ? prod.get("salePrice") : 0);
                        item.put("quantity", ci.getQuantity());
                        view.add(item);
                    }
                }
            } catch (Exception e) {
                Object s = session.getAttribute("cart");
                if (s instanceof List) view = (List<Map<String, Object>>) s;
            }
        } else {
            Object s = session.getAttribute("cart");
            if (s instanceof List) view = (List<Map<String, Object>>) s;
        }
        return view;
    }

    private Map<String, Object> findCartItem(List<Map<String, Object>> cart, int productId) {
        for (Map<String, Object> item : cart) {
            Object idObj = item.get("id");
            if (idObj instanceof Number && ((Number) idObj).intValue() == productId) return item;
            if (idObj != null) {
                try { if (Integer.parseInt(idObj.toString()) == productId) return item; } catch (NumberFormatException ignored) {}
            }
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

    private int calculateCartCount(List<Map<String, Object>> view) {
        int count = 0;
        if (view == null) return 0;
        for (Map<String, Object> item : view) {
            Number qtyN = (Number) item.getOrDefault("quantity", 1);
            count += qtyN != null ? qtyN.intValue() : 0;
        }
        return count;
    }

    private Integer sessionAttributeAsInt(HttpSession session, String name) {
        if (session == null) return null;
        Object v = session.getAttribute(name);
        if (v instanceof Number) return ((Number) v).intValue();
        try { return v != null ? Integer.parseInt(v.toString()) : null; } catch (Exception e) { return null; }
    }

    private Integer resolveSessionUserId(HttpSession session) {
        if (session == null) return null;
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
            } catch (Exception e) {
            }
        }
        return null;
    }
}