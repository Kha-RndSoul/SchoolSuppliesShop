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
        productService = new ProductService();
        productImageService = new ProductImageService();
        productReviewService = new ProductReviewService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idStr = request.getParameter("id");
            if (idStr == null || idStr.isEmpty()) {
                throw new IllegalArgumentException("Thiếu ID sản phẩm");
            }

            int productId = Integer.parseInt(idStr);

            // Load thông tin sản phẩm
            Map<String, Object> product = productService.getProductById(productId);
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
                }
            }

            // Load tất cả hình ảnh của sản phẩm
            List<ProductImage> productImages = productImageService.getImagesByProductId(productId);
            request.setAttribute("productImages", productImages);

            // Load đánh giá đã được duyệt
            List<ProductReview> reviews = productReviewService.getByProductId(productId);
            reviews.removeIf(r -> ! r.isStatus()); // Chỉ lấy reviews đã duyệt
            request.setAttribute("reviews", reviews);

            // Tính rating trung bình
            Object avgRatingObj = product.get("averageRating");
            double averageRating = avgRatingObj != null ? ((Number) avgRatingObj).doubleValue() : 0;
            int reviewCount = reviews.size();
            request.setAttribute("averageRating", averageRating);
            request.setAttribute("reviewCount", reviewCount);

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
            }

            //  Forward đến WEB-INF/jsp/product/product-detail.jsp
            request.getRequestDispatcher("/WEB-INF/jsp/products/product-detail.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "ID sản phẩm không hợp lệ");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi khi tải chi tiết sản phẩm:  " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}