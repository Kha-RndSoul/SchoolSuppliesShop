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
        System.out.println("✓ CartController.init() STARTING...");

        try {
            System.out.println("→ Creating CategoryDAO...");
            categoryDAO = new CategoryDAO();
            System.out.println("✓ CategoryDAO created successfully");

            System.out.println("→ Creating ProductDAO...");
            productDAO = new ProductDAO();
            System.out.println("✓ ProductDAO created successfully");

            System.out.println("✓ CartController initialized");

        } catch (Exception e) {
            System.err.println("ERROR in CartController.init()");
            System.err.println("Exception: " + e.getClass().getName());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Failed to initialize CartController", e);
        }

    }

    /**
     * GET /cart - Hiển thị giỏ hàng
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("✓ CartController.doGet() CALLED");
        System.out.println("   Request URI: " + request.getRequestURI());
        System.out.println("   Context Path: " + request.getContextPath());

        try {
            HttpSession session = request.getSession();

            // Lấy giỏ hàng từ session
            System.out.println("→ Retrieving cart from session.. .");
            List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");

            if (cart == null) {
                System.out.println("→ Cart is empty, creating new cart");
                cart = new ArrayList<>();
            } else {
                System.out.println("✓ Cart found with " + cart.size() + " item(s)");
            }

            // Tính tổng tiền
            System.out.println("→ Calculating cart total...");
            double cartTotal = calculateCartTotal(cart);
            System.out.println("✓ Cart total: " + String.format("%,.0f", cartTotal) + "₫");

            // Lấy danh sách category cho header
            System.out.println("→ Loading categories for header...");

            List<? > categories = null;
            try {
                categories = categoryDAO.getList();
                System.out.println(" Loaded " + (categories != null ? categories.size() : 0) + " categories");
            } catch (Exception e) {
                System.err.println(" ERROR loading categories:");
                System.err.println("   Exception: " + e.getClass().getName());
                System.err.println("   Message: " + e.getMessage());
                e.printStackTrace();
                // Không throw, để cart vẫn hiển thị được
                categories = new ArrayList<>();
            }

            // Set attributes
            System.out.println("→ Setting request attributes...");
            request.setAttribute("cart", cart);
            request.setAttribute("cartTotal", cartTotal);
            request.setAttribute("listCategory", categories);
            System.out.println("✓ Attributes set successfully");

            // Forward to JSP
            String jspPath = "/WEB-INF/jsp/order/cart.jsp";
            System.out.println("→ Forwarding to " + jspPath);

            try {
                request.getRequestDispatcher(jspPath).forward(request, response);
                System.out.println(" Forward completed successfully");
            } catch (Exception e) {
                System.err.println(" ERROR during forward to JSP:");
                System.err.println("   JSP Path: " + jspPath);
                System.err.println("   Exception: " + e.getClass().getName());
                System.err.println("   Message: " + e.getMessage());
                e.printStackTrace();

                // Re-throw để Tomcat bắt
                throw e;
            }


        } catch (Exception e) {
            System.err.println(" UNEXPECTED ERROR in CartController.doGet() ");
            System.err.println("   Request URI: " + request.getRequestURI());
            System.err.println("   Exception Type: " + e.getClass().getName());
            System.err.println("   Exception Message: " + e.getMessage());
            System.err.println("   Stack Trace:");
            e.printStackTrace();


            // Set error message để hiển thị
            request.setAttribute("errorMessage", "Lỗi hệ thống:  " + e.getMessage());
            request.setAttribute("errorDetail", e.getClass().getName());

            // Forward to error page
            try {
                request.getRequestDispatcher("/WEB-INF/jsp/error/error.jsp").forward(request, response);
            } catch (Exception e2) {
                // Nếu error page cũng lỗi, throw ServletException
                throw new ServletException("Error in CartController and error page", e);
            }
        }
    }

    /**
     * POST /cart - Xử lý các thao tác với giỏ hàng
     * Actions:  add, update, remove, clear
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("✓ CartController.doPost() CALLED");

        String action = request.getParameter("action");

        System.out.println("→ Action: " + (action != null ? action : "null"));

        if (action == null || action.trim().isEmpty()) {
            System.out.println("No action specified, redirecting to cart");
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        try {
            switch (action.trim()) {
                case "add":
                    System.out.println("→ Handling ADD TO CART");
                    addToCart(request, response);
                    break;
                case "update":
                    System.out.println("→ Handling UPDATE CART");
                    updateCart(request, response);
                    break;
                case "remove":
                    System.out.println("→ Handling REMOVE FROM CART");
                    removeFromCart(request, response);
                    break;
                case "clear":
                    System.out.println("→ Handling CLEAR CART");
                    clearCart(request, response);
                    break;
                default:
                    System.out.println(" Unknown action: " + action);
                    response.sendRedirect(request.getContextPath() + "/cart");
            }
        } catch (IllegalArgumentException e) {
            // Validation error
            System.out.println(" Validation error:  " + e.getMessage());

            request.setAttribute("errorMessage", e.getMessage());
            doGet(request, response);

        } catch (Exception e) {
            // Unexpected error
            System.err.println(" UNEXPECTED ERROR in CartController.doPost()");
            System.err.println("Exception: " + e.getClass().getName());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();

            request.setAttribute("errorMessage", "Đã có lỗi xảy ra. Vui lòng thử lại sau.");
            doGet(request, response);
        }
    }

    /**
     * Thêm sản phẩm vào giỏ hàng
     */
    private void addToCart(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();

        // Get parameters
        String productIdStr = request.getParameter("productId");
        String quantityStr = request.getParameter("quantity");

        System.out.println("→ Add to cart parameters:");
        System.out.println("   Product ID: " + productIdStr);
        System.out.println("   Quantity: " + quantityStr);

        // VALIDATION

        if (productIdStr == null || productIdStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID không được rỗng");
        }

        int productId;
        try {
            productId = Integer.parseInt(productIdStr.trim());
            if (productId <= 0) {
                throw new IllegalArgumentException("Product ID không hợp lệ");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Product ID phải là số nguyên");
        }

        int quantity = 1;
        if (quantityStr != null && ! quantityStr.trim().isEmpty()) {
            try {
                quantity = Integer.parseInt(quantityStr.trim());
                if (quantity <= 0) {
                    throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
                }
                if (quantity > 999) {
                    throw new IllegalArgumentException("Số lượng không được vượt quá 999");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Số lượng phải là số nguyên");
            }
        }

        System.out.println("✓ Validation passed");
        System.out.println("   Product ID: " + productId);
        System.out.println("   Quantity: " + quantity);


        System.out.println("→ Fetching product info from database...");
        Map<String, Object> product = productDAO.getProductByIdWithImage(productId);

        if (product == null) {
            System.out.println(" Product not found:  ID = " + productId);
            throw new IllegalArgumentException("Sản phẩm không tồn tại");
        }

        System.out.println("✓ Product found:");
        System.out.println("   Name: " + product.get("productName"));
        System.out.println("   Price: " + product.get("price"));


        System.out.println("→ Retrieving cart from session...");
        List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");

        if (cart == null) {
            System.out.println("→ Cart is empty, creating new cart");
            cart = new ArrayList<>();
        } else {
            System.out.println("✓ Cart found with " + cart.size() + " item(s)");
        }


        boolean found = false;
        for (Map<String, Object> item : cart) {
            if (((Number) item.get("id")).intValue() == productId) {
                int currentQty = ((Number) item.get("quantity")).intValue();
                int newQty = currentQty + quantity;

                System.out.println("→ Product already in cart");
                System.out.println("   Current quantity: " + currentQty);
                System.out.println("   Adding:  " + quantity);
                System.out.println("   New quantity: " + newQty);

                item.put("quantity", newQty);
                found = true;
                break;
            }
        }


        if (!found) {
            System.out.println("→ Adding new item to cart");

            Map<String, Object> cartItem = new HashMap<>();
            cartItem.put("id", product.get("id"));
            cartItem.put("productId", product.get("id"));
            cartItem.put("productName", product.get("productName"));
            cartItem.put("price", product.get("price"));
            cartItem.put("salePrice", product.get("salePrice"));
            cartItem.put("imageUrl", product.get("imageUrl"));
            cartItem.put("quantity", quantity);

            cart.add(cartItem);
            System.out.println(" Item added to cart");
        }


        session.setAttribute("cart", cart);
        double cartTotal = calculateCartTotal(cart);
        session.setAttribute("cartTotal", cartTotal);

        System.out.println("✓ Cart updated in session");
        System.out.println("   Total items: " + cart.size());
        System.out.println("   Total amount: " + String.format("%,. 0f", cartTotal) + "₫");


        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isEmpty()) {
            System.out.println("→ Redirecting back to:  " + referer);
            response.sendRedirect(referer);
        } else {
            System.out.println("→ Redirecting to cart page");
            response.sendRedirect(request.getContextPath() + "/cart");
        }
    }

    /**
     * Cập nhật số lượng sản phẩm trong giỏ hàng
     */
    private void updateCart(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();

        // Get parameters
        String productIdStr = request.getParameter("productId");
        String quantityStr = request.getParameter("quantity");

        System.out.println("→ Update cart parameters:");
        System.out.println("   Product ID: " + productIdStr);
        System.out.println("   Quantity change: " + quantityStr);


        if (productIdStr == null || productIdStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID không được rỗng");
        }

        if (quantityStr == null || quantityStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Quantity change không được rỗng");
        }

        int productId;
        try {
            productId = Integer.parseInt(productIdStr.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Product ID phải là số nguyên");
        }

        int change;
        try {
            change = Integer.parseInt(quantityStr.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Quantity change phải là số nguyên");
        }

        System.out.println("✓ Validation passed");
        System.out.println("   Product ID: " + productId);
        System.out.println("   Change: " + (change > 0 ? "+" :  "") + change);

        List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            System.out.println(" Cart is empty");
            throw new IllegalArgumentException("Giỏ hàng rỗng");
        }


        boolean found = false;
        for (int i = 0; i < cart.size(); i++) {
            Map<String, Object> item = cart.get(i);
            if (((Number) item.get("id")).intValue() == productId) {
                int currentQty = ((Number) item.get("quantity")).intValue();
                int newQty = currentQty + change;

                System.out.println("→ Found product in cart");
                System.out.println("   Current quantity: " + currentQty);
                System.out.println("   New quantity: " + newQty);

                if (newQty > 0) {
                    if (newQty > 999) {
                        throw new IllegalArgumentException("Số lượng không được vượt quá 999");
                    }
                    item.put("quantity", newQty);
                    System.out.println("✓ Quantity updated");
                } else {
                    System.out.println("→ Quantity <= 0, removing item from cart");
                    cart.remove(i);
                    System.out.println("✓ Item removed");
                }

                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println(" Product not found in cart");
            throw new IllegalArgumentException("Sản phẩm không có trong giỏ hàng");
        }



        session.setAttribute("cart", cart);
        double cartTotal = calculateCartTotal(cart);
        session.setAttribute("cartTotal", cartTotal);

        System.out.println(" Cart updated");
        System.out.println("   Total items: " + cart.size());
        System.out.println("   Total amount: " + String.format("%,.0f", cartTotal) + "₫");

        response.sendRedirect(request.getContextPath() + "/cart");
    }

    /**
     * Xóa sản phẩm khỏi giỏ hàng
     */
    private void removeFromCart(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();

        // Get parameter
        String productIdStr = request.getParameter("productId");

        System.out.println("→ Remove from cart parameter:");
        System.out.println("   Product ID: " + productIdStr);


        if (productIdStr == null || productIdStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID không được rỗng");
        }

        int productId;
        try {
            productId = Integer.parseInt(productIdStr.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Product ID phải là số nguyên");
        }

        System.out.println("✓ Validation passed");
        System.out.println("   Product ID: " + productId);



        List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            System.out.println(" Cart is empty");
            throw new IllegalArgumentException("Giỏ hàng rỗng");
        }


        System.out.println("→ Removing product from cart...");
        boolean removed = cart.removeIf(item -> ((Number) item.get("id")).intValue() == productId);

        if (removed) {
            System.out.println(" Product removed from cart");
        } else {
            System.out.println(" Product not found in cart");
            throw new IllegalArgumentException("Sản phẩm không có trong giỏ hàng");
        }


        session.setAttribute("cart", cart);
        double cartTotal = calculateCartTotal(cart);
        session.setAttribute("cartTotal", cartTotal);

        System.out.println("✓ Cart updated");
        System.out.println("   Total items: " + cart.size());
        System.out.println("   Total amount: " + String.format("%,.0f", cartTotal) + "₫");

        response.sendRedirect(request.getContextPath() + "/cart");
    }

    /**
     * Xóa toàn bộ giỏ hàng
     */
    private void clearCart(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        System.out.println("→ Clearing entire cart...");

        HttpSession session = request.getSession();
        session.removeAttribute("cart");
        session.removeAttribute("cartTotal");

        System.out.println("✓ Cart cleared");

        response.sendRedirect(request.getContextPath() + "/cart");
    }

    /**
     * Tính tổng tiền giỏ hàng
     */
    private double calculateCartTotal(List<Map<String, Object>> cart) {
        if (cart == null || cart.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;
        for (Map<String, Object> item : cart) {
            double price = item.get("salePrice") != null
                    ? ((Number) item.get("salePrice")).doubleValue()
                    : ((Number) item.get("price")).doubleValue();
            int quantity = ((Number) item.get("quantity")).intValue();
            total += price * quantity;
        }

        return total;
    }

    @Override
    public void destroy() {
        System.out.println("✓ CartController destroyed");
    }
}