package com.shop.controller.product;

import com.shop.services.*;
import com.shop.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

/**
 * Controller xử lý trang danh sách sản phẩm
 * URL: /products
 * Chức năng:
 * - Hiển thị tất cả sản phẩm
 * - Lọc theo category, brand (nhiều brand), giá
 * - Tìm kiếm theo từ khóa
 * - Sắp xếp theo giá, tên, rating
 * - Phân trang
 */
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
            throws ServletException, IOException {
        try {
            // Lấy parameters từ request
            String keyword = request.getParameter("keyword");
            String categoryIdStr = request.getParameter("categoryId");
            String[] brandIdStrs = request.getParameterValues("brandId");
            String sortBy = request.getParameter("sortBy");
            String pageStr = request.getParameter("page");
            String minPriceStr = request.getParameter("minPrice");
            String maxPriceStr = request.getParameter("maxPrice");

            Integer categoryId = null;
            List<Integer> brandIds = new ArrayList<>();
            int page = 1;
            int pageSize = 12;

            // Parse categoryId
            if (categoryIdStr != null && !categoryIdStr.trim().isEmpty()) {
                try {
                    categoryId = Integer.parseInt(categoryIdStr.trim());
                    // Kiểm tra categoryId phải > 0
                    if (categoryId <= 0) {
                        System.err.println(" Invalid categoryId: " + categoryId + " (must be > 0)");
                        categoryId = null;
                    }
                } catch (NumberFormatException e) {
                    System.err.println(" Cannot parse categoryId: " + categoryIdStr);
                    categoryId = null;
                }
            }
            // Parse brandIds (có thể có nhiều brandId từ checkbox)
            if (brandIdStrs != null && brandIdStrs.length > 0) {
                for (String brandIdStr : brandIdStrs) {
                    if (brandIdStr != null && !brandIdStr.trim().isEmpty()) {
                        try {
                            int brandId = Integer.parseInt(brandIdStr.trim());
                            // Chỉ thêm brandId hợp lệ (> 0)
                            if (brandId > 0) {
                                brandIds.add(brandId);
                            } else {
                                System.err.println("️ Invalid brandId: " + brandId + " (must be > 0)");
                            }
                        } catch (NumberFormatException e) {
                            System.err.println(" Cannot parse brandId: " + brandIdStr);
                        }
                    }
                }
                System.out.println(" Parsed brandIds: " + brandIds);
            }
            // Parse page (số trang)
            if (pageStr != null && !pageStr.trim().isEmpty()) {
                try {
                    page = Integer.parseInt(pageStr.trim());
                    // Page phải >= 1
                    if (page < 1) page = 1;
                } catch (NumberFormatException e) {
                    page = 1;
                }
            }

            //Load sản phẩm theo bộ lọc
            List<Map<String, Object>> products;
            // Thứ tự ưu tiên: Tìm kiếm > Brand+Category > Brand > Category > Tất cả
            if (keyword != null && !keyword.trim().isEmpty()) {
                //  TRƯỜNG HỢP 1: TÌM KIẾM THEO TỪ KHÓA
                System.out.println(" Searching products with keyword: " + keyword);
                products = productService.search(keyword);
                request.setAttribute("searchKeyword", keyword);

            } else if (!brandIds.isEmpty() && categoryId != null && categoryId > 0) {
                //  TRƯỜNG HỢP 2: LỌC THEO NHIỀU BRAND + CATEGORY
                System.out.println(" Loading products for brandIds: " + brandIds + " AND categoryId: " + categoryId);
                products = productService.getByBrandIdsAndCategory(brandIds, categoryId);
                // Lấy thông tin category để hiển thị
                Category category = categoryService.getCategoryById(categoryId);
                if (category != null) {
                    request.setAttribute("currentCategory", category);
                    System.out.println(" Category found: " + category.getCategoryName());
                } else {
                    System.err.println(" Category not found for ID: " + categoryId);
                }
            } else if (!brandIds.isEmpty()) {
                //  TRƯỜNG HỢP 3: CHỈ LỌC THEO NHIỀU BRAND
                System.out.println(" Loading products for brandIds: " + brandIds);
                products = productService.getByBrandIds(brandIds);

            } else if (categoryId != null && categoryId > 0) {
                //  TRƯỜNG HỢP 4: CHỈ LỌC THEO CATEGORY
                System.out.println(" Loading products for categoryId: " + categoryId);
                products = productService.getByCategory(categoryId);
                // Lấy thông tin category để hiển thị breadcrumb
                Category category = categoryService.getCategoryById(categoryId);
                if (category != null) {
                    request.setAttribute("currentCategory", category);
                    System.out.println(" Category found: " + category.getCategoryName());
                } else {
                    System.err.println(" Category not found for ID: " + categoryId);
                }

            } else {
                //  TRƯỜNG HỢP 5: HIỂN THỊ TẤT CẢ SẢN PHẨM (
                System.out.println(" Loading all products");
                products = productService.getAllProductsWithImage();
            }
            System.out.println(" Total products loaded: " + products.size());

            // Lọc theo khoảng giá
            if (minPriceStr != null || maxPriceStr != null) {
                double minPrice = 0;
                double maxPrice = Double.MAX_VALUE;

                // Parse minPrice
                if (minPriceStr != null && !minPriceStr.trim().isEmpty()) {
                    try {
                        minPrice = Double.parseDouble(minPriceStr.trim());
                    } catch (NumberFormatException e) {
                        System.err.println(" Invalid minPrice: " + minPriceStr);
                    }
                }
                // Parse maxPrice
                if (maxPriceStr != null && !maxPriceStr.trim().isEmpty()) {
                    try {
                        maxPrice = Double.parseDouble(maxPriceStr.trim());
                    } catch (NumberFormatException e) {
                        System.err.println(" Invalid maxPrice: " + maxPriceStr);
                    }
                }
                final double finalMinPrice = minPrice;
                final double finalMaxPrice = maxPrice;

                // Loại bỏ sản phẩm ngoài khoảng giá
                products.removeIf(p -> {
                    // Ưu tiên lấy salePrice, nếu không có thì lấy price
                    Object priceObj = p.get("salePrice");
                    if (priceObj == null || ((Number) priceObj).doubleValue() == 0) {
                        priceObj = p.get("price");
                    }
                    double price = ((Number) priceObj).doubleValue();
                    return price < finalMinPrice || price > finalMaxPrice;
                });

                request.setAttribute("minPrice", minPrice);
                request.setAttribute("maxPrice", maxPrice);
                System.out.println("💰 Filtered by price: " + minPrice + " - " + maxPrice + " → " + products.size() + " products");
            }

            // Sắp xếp sản phẩm
            if (sortBy != null && !sortBy.trim().isEmpty()) {
                System.out.println(" Sorting by: " + sortBy);

                switch (sortBy) {
                    case "price-asc":
                        // Sắp xếp theo giá tăng dần
                        products.sort(Comparator.comparingDouble(p -> {
                            Object price = p.get("salePrice");
                            if (price == null || ((Number) price).doubleValue() == 0)
                                price = p.get("price");
                            return ((Number) price).doubleValue();
                        }));
                        break;

                    case "price-desc":
                        // Sắp xếp theo giá giảm dần
                        products.sort((p1, p2) -> {
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
                        // Sắp xếp theo tên A-Z
                        products.sort(Comparator.comparing(p -> (String) p.get("productName")));
                        break;

                    case "name-desc":
                        // Sắp xếp theo tên Z-A
                        products.sort((p1, p2) ->
                                ((String) p2.get("productName")).compareTo((String) p1.get("productName"))
                        );
                        break;

                    case "rating-desc":
                        // Sắp xếp theo rating cao nhất
                        products.sort((p1, p2) -> {
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
            int totalProducts = products.size();
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize);
            // Nếu page > totalPages, reset về trang cuối
            if (page > totalPages && totalPages > 0) {
                page = totalPages;
            }
            // Tính chỉ số bắt đầu và kết thúc cho trang hiện tại
            int startIndex = (page - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, totalProducts);

            // Cắt danh sách sản phẩm theo trang
            List<Map<String, Object>> pagedProducts =
                    totalProducts > 0 ? products.subList(startIndex, endIndex) : new ArrayList<>();

            System.out.println("📄 Page " + page + "/" + totalPages + " | Showing " + pagedProducts.size() + " products");

            //Set attributes cho JSP
            // Danh sách sản phẩm của trang hiện tại
            request.setAttribute("products", pagedProducts);
            // Thông tin phân trang
            request.setAttribute("totalProducts", totalProducts);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            // Load categories và brands cho sidebar filter
            List<Category> allCategories = categoryService.getAllCategories();
            List<Brand> allBrands = brandService.getAllBrands();
            request.setAttribute("listCategory", allCategories != null ? allCategories : new ArrayList<>());
            request.setAttribute("allCategories", allCategories);
            request.setAttribute("allBrands", allBrands);

            System.out.println(" ProductController completed successfully");

           //forward đến JSP
            request.getRequestDispatcher("/WEB-INF/jsp/products/products.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            System.err.println(" ERROR in ProductController:");
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi khi tải danh sách sản phẩm: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}