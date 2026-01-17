package com.shop.controller.order;

import com.shop.dao.product.CategoryDAO;
import com.shop.dao.product.ProductDAO;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.*;

@WebServlet(name = "CartController", urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    private CategoryDAO categoryDAO;
    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        categoryDAO = new CategoryDAO();
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // Lấy giỏ hàng từ session
        List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        // Tính tổng tiền
        double cartTotal = 0;
        for (Map<String, Object> item : cart) {
            double price = item.get("salePrice") != null
                    ? ((Number) item.get("salePrice")).doubleValue()
                    : ((Number) item.get("price")).doubleValue();
            int quantity = ((Number) item.get("quantity")).intValue();
            cartTotal += price * quantity;
        }

        // Set attributes
        request.setAttribute("cart", cart);
        request.setAttribute("cartTotal", cartTotal);
        request.setAttribute("listCategory", categoryDAO.getList());

        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        try {
            switch (action) {
                case "add":
                    addToCart(request,response);
                    break;
                case "update":
                    updateCart(request,response);
                    break;
                case "remove":
                    removeFromCart(request,response);
                    break;
                case "clear":
                    clearCart(request,response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/cart");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Đã có lỗi xảy ra: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    // Thêm sản phẩm vào giỏ hàng
    private void addToCart(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();

        String productIdStr = request.getParameter("productId");
        if (productIdStr == null || productIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        int productId = Integer.parseInt(productIdStr);
        int quantity = 1;

        try {
            String qtyStr = request.getParameter("quantity");
            if (qtyStr != null && !qtyStr.isEmpty()) {
                quantity = Integer.parseInt(qtyStr);
                if (quantity <= 0) quantity = 1;
            }
        } catch (Exception e) {
            quantity = 1;
        }

        // Lấy thông tin sản phẩm từ database
        Map<String, Object> product =
                productDAO.getProductByIdWithImage(productId);

        if (product == null) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        // Lấy giỏ hàng từ session
        List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        // Kiểm tra sản phẩm đã có trong giỏ chưa
        boolean found = false;
        for (Map<String, Object> item : cart) {
            if (((Number) item.get("id")).intValue() == productId) {
                int currentQty =
                        ((Number) item.get("quantity")).intValue();
                item.put("quantity", currentQty + quantity);
                found = true;
                break;
            }
        }

        // Nếu chưa có, thêm mới
        if (!found) {
            Map<String, Object> cartItem = new HashMap<>();
            cartItem.put("id", product.get("id"));
            cartItem.put("productId", product.get("id"));
            cartItem.put("productName", product.get("productName"));
            cartItem.put("price", product.get("price"));
            cartItem.put("salePrice", product.get("salePrice"));
            cartItem.put("imageUrl", product.get("imageUrl"));
            cartItem.put("quantity", quantity);
            cart.add(cartItem);
        }

        // Cập nhật session
        session.setAttribute("cart", cart);
        updateCartTotal(session, cart);

        // Redirect về trang trước hoặc giỏ hàng
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isEmpty()) {
            response.sendRedirect(referer);
        } else {
            response.sendRedirect(request.getContextPath() + "/cart");
        }
    }


    private void updateCart(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();

        String productIdStr = request.getParameter("productId");
        String quantityStr = request.getParameter("quantity");

        if (productIdStr == null || productIdStr.isEmpty() ||
                quantityStr == null || quantityStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        int productId = Integer.parseInt(productIdStr);
        int change = Integer.parseInt(quantityStr);  // +1 hoặc -1

        List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");

        if (cart != null) {
            for (int i = 0; i < cart.size(); i++) {
                Map<String, Object> item = cart.get(i);
                if (((Number) item.get("id")).intValue() == productId) {
                    int currentQty = ((Number) item.get("quantity")).intValue();
                    int newQty = currentQty + change;

                    if (newQty > 0) {
                        item.put("quantity", newQty);
                    } else {
                        // Nếu quantity <= 0, xóa sản phẩm
                        cart.remove(i);
                    }
                    break;
                }
            }
            session.setAttribute("cart", cart);
            updateCartTotal(session, cart);
        }

        response.sendRedirect(request.getContextPath() + "/cart");
    }

    // Xóa sản phẩm khỏi giỏ hàng
    private void removeFromCart(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();

        String productIdStr = request.getParameter("productId");
        if (productIdStr == null || productIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        int productId = Integer.parseInt(productIdStr);

        List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");

        if (cart != null) {
            cart.removeIf(
                    item -> ((Number) item.get("id")).intValue() == productId
            );
            session.setAttribute("cart", cart);
            updateCartTotal(session, cart);
        }

        response.sendRedirect(request.getContextPath() + "/cart");
    }

    // Xóa toàn bộ giỏ hàng
    private void clearCart(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        session.removeAttribute("cart");
        session.removeAttribute("cartTotal");

        response.sendRedirect(request.getContextPath() + "/cart");
    }

    // Cập nhật tổng tiền giỏ hàng
    private void updateCartTotal(HttpSession session, List<Map<String, Object>> cart) {
        double total = 0;
        for (Map<String, Object> item : cart) {
            double price = item.get("salePrice") != null
                    ? ((Number) item.get("salePrice")).doubleValue()
                    : ((Number) item.get("price")).doubleValue();
            int quantity =
                    ((Number) item.get("quantity")).intValue();
            total += price * quantity;
        }
        session.setAttribute("cartTotal", total);
    }
}