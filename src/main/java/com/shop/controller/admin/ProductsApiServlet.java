package com.shop.controller.admin;

import com.shop.dao.product.ProductDAO;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API Servlet cho Products - Xử lý AJAX requests
 * URL: /admin/api/products
 */
@WebServlet(name = "ProductsApiServlet", urlPatterns = {"/admin/api/products"})
public class ProductsApiServlet extends HttpServlet {

    private ProductDAO productDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAO();
        gson = new Gson();
        System.out.println("✅ ProductsApiServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Lấy tham số phân trang
            int page = 1;
            int pageSize = 20; // 20 sản phẩm/trang

            String pageParam = request.getParameter("page");
            if (pageParam != null && !pageParam.isEmpty()) {
                try {
                    page = Integer.parseInt(pageParam);
                    if (page < 1) page = 1;
                } catch (NumberFormatException e) {
                    page = 1;
                }
            }

            String pageSizeParam = request.getParameter("pageSize");
            if (pageSizeParam != null && !pageSizeParam.isEmpty()) {
                try {
                    pageSize = Integer.parseInt(pageSizeParam);
                    if (pageSize < 1) pageSize = 20;
                    if (pageSize > 100) pageSize = 100; // Max 100
                } catch (NumberFormatException e) {
                    pageSize = 20;
                }
            }

            System.out.println("📊 Loading products - Page: " + page + ", Size: " + pageSize);

            // Lấy tất cả sản phẩm
            List<Map<String, Object>> allProducts = productDAO.getListWithImage();

            // Tính toán phân trang
            int totalItems = allProducts.size();
            int totalPages = (int) Math.ceil((double) totalItems / pageSize);

            // Đảm bảo page không vượt quá totalPages
            if (page > totalPages && totalPages > 0) {
                page = totalPages;
            }

            int startIndex = (page - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, totalItems);

            // Lấy sản phẩm cho trang hiện tại
            List<Map<String, Object>> products = allProducts.subList(startIndex, endIndex);

            System.out.println("✅ Products loaded: " + products.size() + "/" + totalItems);

            // Tạo response JSON
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("products", products);
            result.put("pagination", Map.of(
                    "currentPage", page,
                    "pageSize", pageSize,
                    "totalItems", totalItems,
                    "totalPages", totalPages,
                    "startIndex", startIndex + 1,
                    "endIndex", endIndex
            ));

            // Gửi response
            String json = gson.toJson(result);
            response.getWriter().write(json);

        } catch (Exception e) {
            System.err.println("❌ Error in ProductsApiServlet: " + e.getMessage());
            e.printStackTrace();

            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Có lỗi xảy ra: " + e.getMessage());

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson(error));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // POST không được hỗ trợ cho API này
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        Map<String, Object> error = Map.of(
                "success", false,
                "message", "Method not allowed"
        );
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(error));
    }
}