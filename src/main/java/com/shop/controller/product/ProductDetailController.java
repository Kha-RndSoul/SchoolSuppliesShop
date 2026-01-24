package com.shop.controller.product;

import com.shop.services.*;
import com.shop.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "ProductDetailController", urlPatterns = {"/product-detail"})
public class ProductDetailController extends HttpServlet {

    private ProductService productService;
    private ProductImageService productImageService;
    private ProductReviewService productReviewService;

    @Override
    public void init() throws ServletException {
        System.out.println(" ProductDetailController.init() STARTING...");
        try {
            productService = new ProductService();
            productImageService = new ProductImageService();
            productReviewService = new ProductReviewService();
            System.out.println(" ProductDetailController initialized successfully");
        } catch (Exception e) {
            System.err.println(" ERROR in ProductDetailController.init()");
            e.printStackTrace();
            throw new ServletException("Failed to initialize ProductDetailController", e);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println(" ProductDetailController.doGet() CALLED");
        System.out.println("   Request URI: " + request.getRequestURI());

        try {
            String idStr = request.getParameter("id");
            System.out.println("   Product ID parameter: " + idStr);

            if (idStr == null || idStr.isEmpty()) {
                System.out.println(" Missing product ID");
                throw new IllegalArgumentException("Thiếu ID sản phẩm");
            }

            int productId = Integer.parseInt(idStr);
            System.out.println(" Loading product with ID: " + productId);

            // Load thông tin sản phẩm
            Map<String, Object> product = productService.getProductById(productId);

            if (product == null) {
                System.out.println(" Product not found: " + productId);
                throw new IllegalArgumentException("Không tìm thấy sản phẩm");
            }
            System.out.println(" Product loaded: " + product.get("productName"));
            request.setAttribute("product", product);
            // Tính discount percent
            Object salePriceObj = product.get("salePrice");
            Object priceObj = product.get("price");
            if (salePriceObj != null && priceObj != null) {
                double salePrice = ((Number) salePriceObj).doubleValue();
                double price = ((Number) priceObj).doubleValue();
                if (salePrice > 0 && salePrice < price) {
                    int discountPercent = (int) Math.round((1 - salePrice / price) * 100);
                    request.setAttribute("discountPercent", discountPercent);
                    System.out.println(" Discount: " + discountPercent + "%");
                }
            }

            // Load tất cả hình ảnh của sản phẩm
            List<ProductImage> productImages = productImageService.getImagesByProductId(productId);
            request.setAttribute("productImages", productImages);
            System.out.println("→ Loaded " + (productImages != null ? productImages.size() : 0) + " images");
            // Load đánh giá đã được duyệt
            List<ProductReview> reviews = productReviewService.getByProductId(productId);

            request.setAttribute("reviews", reviews);
            System.out.println("→ Loaded " + reviews.size() + " approved reviews");
            // Tính rating trung bình
            Object avgRatingObj = product.get("averageRating");
            double averageRating = avgRatingObj != null ? ((Number) avgRatingObj).doubleValue() : 0;
            int reviewCount = reviews.size();
            request.setAttribute("averageRating", averageRating);
            request.setAttribute("reviewCount", reviewCount);
            System.out.println("→ Average rating: " + averageRating + " (" + reviewCount + " reviews)");
            // Đếm số lượng review theo từng mức sao (1-5)
            Map<Integer, Long> ratingCounts = new HashMap<>();
            for (int star = 1; star <= 5; star++) {
                final int currentStar = star;
                long count = reviews.stream()
                        .filter(r -> r.getRating() == currentStar && r.isStatus())
                        .count();
                ratingCounts.put(star, count);
            }
            request.setAttribute("ratingDistribution", ratingCounts);
            System.out.println(" Rating breakdown: " + ratingCounts);
            // Kiểm tra message từ review submit
            HttpSession session = request.getSession(false);
            if (session != null) {
                String successMsg = (String) session.getAttribute("successMessage");
                String errorMsg = (String) session.getAttribute("errorMessage");

                if (successMsg != null) {
                    request.setAttribute("successMessage", successMsg);
                    session.removeAttribute("successMessage");
                    System.out.println(" Success message: " + successMsg);
                }
                if (errorMsg != null) {
                    request.setAttribute("errorMessage", errorMsg);
                    session.removeAttribute("errorMessage");
                    System.out.println(" Error message: " + errorMsg);
                }
            }
            // Load sản phẩm liên quan (cùng danh mục)
            Object categoryIdObj = product.get("categoryId");
            if (categoryIdObj != null) {
                int categoryId = ((Number) categoryIdObj).intValue();
                List<Map<String, Object>> relatedProducts = productService.getByCategory(categoryId);
                relatedProducts.removeIf(p -> ((Number) p.get("id")).intValue() == productId);
                if (relatedProducts.size() > 4) {
                    relatedProducts = relatedProducts.subList(0, 4);
                }
                request.setAttribute("relatedProducts", relatedProducts);
                System.out.println(" Loaded " + relatedProducts.size() + " related products");
            }
            // Forward đến JSP
            String jspPath = "/WEB-INF/jsp/products/product-detail.jsp";
            System.out.println(" Forwarding to: " + jspPath);
            request.getRequestDispatcher(jspPath).forward(request, response);
            System.out.println(" ProductDetailController completed successfully");

        } catch (NumberFormatException e) {
            System.err.println(" Invalid product ID format");
            e.printStackTrace();
            request.setAttribute("errorMessage", "ID sản phẩm không hợp lệ");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println(" ERROR in ProductDetailController.doGet()");
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi khi tải chi tiết sản phẩm: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println(" ProductDetailController.doPost() → calling doGet()");
        doGet(request, response);
    }

    @Override
    public void destroy() {
        System.out.println(" ProductDetailController destroyed");
    }
}