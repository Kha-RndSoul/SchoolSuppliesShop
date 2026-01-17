package com.shop.controller.product;

import com.shop.services.*;
import com.shop.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "ProductController", urlPatterns = {"/products"})
public class ProductController extends HttpServlet {

    private ProductService productService;
    private CategoryService categoryService;
    private BrandService brandService;

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
        categoryService = new CategoryService();
        brandService = new BrandService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException {
        try {
            // Lấy parameters
            String keyword = request.getParameter("keyword");
            String categoryIdStr = request.getParameter("categoryId");
            String brandIdStr = request.getParameter("brandId");
            String sortBy = request.getParameter("sortBy");
            String pageStr = request.getParameter("page");
            String minPriceStr = request.getParameter("minPrice");
            String maxPriceStr = request.getParameter("maxPrice");

            // Parse parameters
            Integer categoryId = (categoryIdStr != null && !categoryIdStr.isEmpty()) ? Integer.parseInt(categoryIdStr) : null;
            Integer brandId = (brandIdStr != null && !brandIdStr.isEmpty()) ? Integer.parseInt(brandIdStr) : null;
            int page = (pageStr != null && !pageStr.isEmpty()) ? Integer.parseInt(pageStr) : 1;
            int pageSize = 12;

            // Load danh sách sản phẩm
            List<Map<String, Object>> products;

            if (keyword != null && !keyword.trim().isEmpty()) {
                products = productService.search(keyword);
                request.setAttribute("searchKeyword", keyword);
            } else if (categoryId != null) {
                products = productService.getByCategory(categoryId);
                Category category = categoryService.getCategoryById(categoryId);
                request.setAttribute("currentCategory", category);
            } else if (brandId != null) {
                products = productService.getByBrand(brandId);
                Brand brand = brandService.getBrandById(brandId);
                request.setAttribute("currentBrand", brand);
            } else {
                products = productService.getAllProductsWithImage();
            }

            // Lọc theo giá (nếu có)
            if (minPriceStr != null || maxPriceStr != null) {
                double minPrice = (minPriceStr != null && !minPriceStr.isEmpty()) ? Double.parseDouble(minPriceStr) : 0;
                double maxPrice = (maxPriceStr != null && ! maxPriceStr.isEmpty()) ? Double.parseDouble(maxPriceStr) : Double.MAX_VALUE;

                products.removeIf(p -> {
                    Object priceObj = p.get("sale_price");
                    if (priceObj == null || ((Number) priceObj).doubleValue() == 0) {
                        priceObj = p.get("price");
                    }
                    double price = ((Number) priceObj).doubleValue();
                    return price < minPrice || price > maxPrice;
                });

                request.setAttribute("minPrice",minPrice);
                request.setAttribute("maxPrice",maxPrice);
            }

            // Sắp xếp
            if (sortBy != null) {
                switch (sortBy) {
                    case "price-asc":
                        products.sort(Comparator.comparingDouble(p -> {
                            Object price = p.get("sale_price");
                            if (price == null || ((Number) price).doubleValue() == 0) price = p.get("price");
                            return ((Number) price).doubleValue();
                        }));
                        break;
                    case "price-desc":
                        products.sort((p1, p2) -> {
                            Object price1 = p1.get("sale_price");
                            if (price1 == null || ((Number) price1).doubleValue() == 0) price1 = p1.get("price");
                            Object price2 = p2.get("sale_price");
                            if (price2 == null || ((Number) price2).doubleValue() == 0) price2 = p2.get("price");
                            return Double.compare(((Number) price2).doubleValue(),((Number) price1).doubleValue());
                        });
                        break;
                    case "name-asc":
                        products.sort(Comparator.comparing(p -> (String) p.get("product_name")));
                        break;
                    case "name-desc":
                        products.sort((p1, p2) -> ((String) p2.get("product_name")).compareTo((String) p1.get("product_name")));
                        break;
                    case "rating-desc":
                        products.sort((p1, p2) -> {
                            Object r1 = p1.get("averageRating");
                            Object r2 = p2.get("averageRating");
                            double rating1 = r1 != null ? ((Number) r1).doubleValue() : 0;
                            double rating2 = r2 != null ? ((Number) r2).doubleValue() : 0;
                            return Double.compare(rating2,rating1);
                        });
                        break;
                }
                request.setAttribute("sortBy",sortBy);
            }

            // Phân trang
            int totalProducts = products.size();
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize);
            int startIndex = (page - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize,totalProducts);

            List<Map<String, Object>> pagedProducts = products.subList(startIndex,endIndex);

            // Set attributes
            request.setAttribute("products", pagedProducts);
            request.setAttribute("totalProducts", totalProducts);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("pageSize", pageSize);

            // Load categories và brands cho filter
            List<Category> allCategories = categoryService.getAllCategories();
            List<Brand> allBrands = brandService.getAllBrands();
            request.setAttribute("allCategories",allCategories);
            request.setAttribute("allBrands",allBrands);

            // Forward to JSP
            request.getRequestDispatcher("/products.jsp").forward(request,response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi khi tải danh sách sản phẩm:  " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request,response);
    }
}