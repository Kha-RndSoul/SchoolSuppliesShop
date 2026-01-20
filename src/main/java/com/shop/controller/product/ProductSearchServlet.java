package com.shop.controller.product;

import com.shop.services.*;
import com.shop.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "ProductSearchServlet", urlPatterns = {"/search"})
public class ProductSearchServlet extends HttpServlet {

    private ProductService productService;
    private CategoryService categoryService;
    private BrandService brandService;

    @Override
    public void init() throws ServletException {
        System.out.println(" ProductSearchServlet.init() STARTING...");

        try {
            productService = new ProductService();
            categoryService = new CategoryService();
            brandService = new BrandService();

            System.out.println(" ProductSearchServlet initialized");
        } catch (Exception e) {
            System.err.println(" ERROR in ProductSearchServlet.init()");
            e.printStackTrace();
            throw new ServletException("Failed to initialize ProductSearchServlet", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println(" ProductSearchServlet.doGet() CALLED");

        try {
            // Lấy keyword từ parameter
            String keyword = request.getParameter("keyword");
            String sortBy = request.getParameter("sortBy");
            String pageStr = request.getParameter("page");

            System.out.println("→ Search keyword: [" + keyword + "]");
            System.out.println("→ Sort by: [" + sortBy + "]");

            // Kiểm tra keyword
            if (keyword == null || keyword.trim().isEmpty()) {
                System.out.println(" Empty keyword, redirecting to /products");
                response.sendRedirect(request.getContextPath() + "/products");
                return;
            }

            // Tìm kiếm sản phẩm
            List<Map<String, Object>> searchResults = productService.search(keyword.trim());

            System.out.println(" Found " + searchResults.size() + " products");

            // Sắp xếp kết quả
            if (sortBy != null && !searchResults.isEmpty()) {
                switch (sortBy) {
                    case "price-asc":
                        searchResults.sort(Comparator.comparingDouble(p -> {
                            Object price = p.get("salePrice");
                            if (price == null || ((Number) price).doubleValue() == 0)
                                price = p.get("price");
                            return ((Number) price).doubleValue();
                        }));
                        break;
                    case "price-desc":
                        searchResults.sort((p1, p2) -> {
                            Object price1 = p1.get("salePrice");
                            if (price1 == null || ((Number) price1).doubleValue() == 0)
                                price1 = p1.get("price");
                            Object price2 = p2.get("salePrice");
                            if (price2 == null || ((Number) price2).doubleValue() == 0)
                                price2 = p2.get("price");
                            return Double.compare(
                                    ((Number) price2).doubleValue(),
                                    ((Number) price1).doubleValue()
                            );
                        });
                        break;
                    case "name-asc":
                        searchResults.sort(Comparator.comparing(p -> (String) p.get("productName")));
                        break;
                    case "rating-desc":
                        searchResults.sort((p1, p2) -> {
                            Object r1 = p1.get("averageRating");
                            Object r2 = p2.get("averageRating");
                            double rating1 = r1 != null ? ((Number) r1).doubleValue() : 0;
                            double rating2 = r2 != null ? ((Number) r2).doubleValue() : 0;
                            return Double.compare(rating2, rating1);
                        });
                        break;
                }
                request.setAttribute("sortBy", sortBy);
            }

            // Phân trang
            int page = (pageStr != null && ! pageStr.isEmpty()) ? Integer.parseInt(pageStr) : 1;
            int pageSize = 12;
            int totalProducts = searchResults.size();
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize);
            int startIndex = (page - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, totalProducts);

            List<Map<String, Object>> pagedResults =
                    totalProducts > 0 ? searchResults.subList(startIndex, endIndex) : new ArrayList<>();

            // Set attributes
            request.setAttribute("keyword", keyword);
            request.setAttribute("products", pagedResults);
            request.setAttribute("totalResults", totalProducts);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);

            // Load categories và brands cho filter
            List<Category> allCategories = categoryService.getAllCategories();
            List<Brand> allBrands = brandService.getAllBrands();
            request.setAttribute("allCategories", allCategories);
            request.setAttribute("allBrands", allBrands);

            System.out.println("✓ Forwarding to search-results.jsp");
            System.out.println("================================");

            // Forward tới search-results.jsp
            request.getRequestDispatcher("/WEB-INF/jsp/products/search-results.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            System.err.println(" ERROR in ProductSearchServlet.doGet()");
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi khi tìm kiếm sản phẩm:  " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("✓ ProductSearchServlet destroyed");
    }
}