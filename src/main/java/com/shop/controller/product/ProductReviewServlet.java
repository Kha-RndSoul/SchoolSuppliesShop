package com.shop.controller.product;

import com.shop.services.*;
import com.shop.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "ProductReviewServlet", urlPatterns = {"/product-review"})
public class ProductReviewServlet extends HttpServlet {

    private ProductReviewService reviewService;
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        System.out.println(" ProductReviewServlet initialized");
        reviewService = new ProductReviewService();
        productService = new ProductService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println(" ProductReviewServlet.doPost() CALLED");

        // Set UTF-8 encoding
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String ajaxHeader = request.getHeader("X-Requested-With");
        boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);
        System.out.println("→ Is AJAX request: " + isAjax);

        // Kiểm tra login
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("customer") == null) {
            System.out.println(" User not logged in");

            if (isAjax) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
                response.setContentType("text/plain; charset=UTF-8");
                response.getWriter().write("Vui lòng đăng nhập để đánh giá");
                return;
            }

            String productId = request.getParameter("productId");
            response.sendRedirect(request.getContextPath() +
                    "/login?redirect=" + request.getContextPath() + "/product-detail?id=" + productId);
            return;
        }

        Customer customer = (Customer) session.getAttribute("customer");
        System.out.println(" Customer ID: " + customer.getId());

        String productId = request.getParameter("productId");

        try {
            String ratingStr = request.getParameter("rating");
            String comment = request.getParameter("comment");

            System.out.println(" ProductID: " + productId);
            System.out.println(" Rating: " + ratingStr);
            System.out.println(" Comment: " + (comment != null ? comment.substring(0, Math.min(50, comment.length())) : "null"));

            // Validation
            if (productId == null || ratingStr == null || comment == null) {
                throw new IllegalArgumentException("Vui lòng điền đầy đủ thông tin");
            }

            int productIdInt = Integer.parseInt(productId);
            int rating = Integer.parseInt(ratingStr);
            comment = comment.trim();

            if (rating < 1 || rating > 5) {
                throw new IllegalArgumentException("Đánh giá phải từ 1-5 sao");
            }

            if (comment.length() < 10) {
                throw new IllegalArgumentException("Nội dung đánh giá phải có ít nhất 10 ký tự");
            }

            if (comment.length() > 500) {
                throw new IllegalArgumentException("Nội dung đánh giá không được vượt quá 500 ký tự");
            }

            // Tạo review object
            ProductReview review = new ProductReview();
            review.setProductId(productIdInt);
            review.setCustomerId(customer.getId());
            review.setRating(rating);
            review.setComment(comment);
            review.setStatus(false);

            System.out.println(" Creating review: " + review);

            // Lưu vào database
            reviewService.createReview(review);
            System.out.println(" Review created successfully");

            if (isAjax) {
                // AJAX request trả về JSON
                response.setStatus(HttpServletResponse.SC_OK); // 200
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().write("{\"success\": true, \"message\": \"Cảm ơn bạn đã đánh giá! Đánh giá của bạn sẽ được hiển thị sau khi được duyệt.\"}");
            } else {
                // Normal request → Redirect về product-detail
                session.setAttribute("successMessage", "Cảm ơn bạn đã đánh giá! Đánh giá của bạn sẽ được hiển thị sau khi được duyệt.");
                response.sendRedirect(request.getContextPath() + "/product-detail?id=" + productIdInt);
            }

        } catch (NumberFormatException e) {
            System.err.println(" Number format error");
            e.printStackTrace();

            if (isAjax) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("text/plain; charset=UTF-8");
                response.getWriter().write("Dữ liệu không hợp lệ");
            } else {
                session.setAttribute("errorMessage", "Dữ liệu không hợp lệ");
                String fallbackId = (productId != null && !productId.isEmpty()) ? productId : "1";
                response.sendRedirect(request.getContextPath() + "/product-detail?id=" + fallbackId);
            }
        } catch (IllegalArgumentException e) {
            System.err.println(" Validation error: " + e.getMessage());

            if (isAjax) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("text/plain; charset=UTF-8");
                response.getWriter().write(e.getMessage());
            } else {
                session.setAttribute("errorMessage", e.getMessage());
                String fallbackId = (productId != null && !productId.isEmpty()) ? productId : "1";
                response.sendRedirect(request.getContextPath() + "/product-detail?id=" + fallbackId);
            }
        } catch (Exception e) {
            System.err.println(" ERROR: " + e.getMessage());
            e.printStackTrace();

            if (isAjax) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType("text/plain; charset=UTF-8");
                response.getWriter().write("Có lỗi xảy ra: " + e.getMessage());
            } else {
                session.setAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
                String fallbackId = (productId != null && !productId.isEmpty()) ? productId : "1";
                response.sendRedirect(request.getContextPath() + "/product-detail?id=" + fallbackId);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Redirect GET requests về product-detail
        String productId = request.getParameter("productId");
        if (productId != null) {
            System.out.println("→ Redirecting GET /product-review to /product-detail");
            response.sendRedirect(request.getContextPath() + "/product-detail?id=" + productId);
        } else {
            response.sendRedirect(request.getContextPath() + "/products");
        }
    }

    @Override
    public void destroy() {
        System.out.println("✓ ProductReviewServlet destroyed");
    }
}