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
            FilterParams params = parseFilterParams(request);
            List<Map<String, Object>> products = loadProducts(request, params);
            applyPriceFilter(request, params, products);
            applySorting(request, params.sortBy, products);
            applyPagination(request, params.page, products);
            loadSidebarData(request);

            request.getRequestDispatcher("/WEB-INF/jsp/products/products.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            System.err.println("ERROR in ProductController:");
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

    // ─── Inner class chứa các tham số lọc ───────────────────────────────────

    private static class FilterParams {
        String keyword;
        Integer categoryId;
        List<Integer> brandIds = new ArrayList<>();
        String sortBy;
        int page = 1;
        String minPriceStr;
        String maxPriceStr;
    }

    // ─── Parse tham số từ request ────────────────────────────────────────────

    private FilterParams parseFilterParams(HttpServletRequest request) {
        FilterParams p = new FilterParams();
        p.keyword     = request.getParameter("keyword");
        p.sortBy      = request.getParameter("sortBy");
        p.minPriceStr = request.getParameter("minPrice");
        p.maxPriceStr = request.getParameter("maxPrice");
        p.categoryId  = parseCategoryId(request.getParameter("categoryId"));
        p.brandIds    = parseBrandIds(request.getParameterValues("brandId"));
        p.page        = parsePage(request.getParameter("page"));
        return p;
    }

    private Integer parseCategoryId(String categoryIdStr) {
        if (categoryIdStr == null || categoryIdStr.trim().isEmpty()) return null;
        try {
            int id = Integer.parseInt(categoryIdStr.trim());
            if (id <= 0) {
                System.err.println("Invalid categoryId: " + id + " (must be > 0)");
                return null;
            }
            return id;
        } catch (NumberFormatException e) {
            System.err.println("Cannot parse categoryId: " + categoryIdStr);
            return null;
        }
    }

    private List<Integer> parseBrandIds(String[] brandIdStrs) {
        List<Integer> brandIds = new ArrayList<>();
        if (brandIdStrs == null) return brandIds;

        for (String brandIdStr : brandIdStrs) {
            if (brandIdStr == null || brandIdStr.trim().isEmpty()) continue;
            try {
                int id = Integer.parseInt(brandIdStr.trim());
                if (id > 0) {
                    brandIds.add(id);
                } else {
                    System.err.println("Invalid brandId: " + id + " (must be > 0)");
                }
            } catch (NumberFormatException e) {
                System.err.println("Cannot parse brandId: " + brandIdStr);
            }
        }
        System.out.println("Parsed brandIds: " + brandIds);
        return brandIds;
    }

    private int parsePage(String pageStr) {
        if (pageStr == null || pageStr.trim().isEmpty()) return 1;
        try {
            int p = Integer.parseInt(pageStr.trim());
            return p < 1 ? 1 : p;
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    // ─── Load sản phẩm theo bộ lọc ──────────────────────────────────────────

    private List<Map<String, Object>> loadProducts(HttpServletRequest request, FilterParams p) {
        if (p.keyword != null && !p.keyword.trim().isEmpty()) {
            return loadByKeyword(request, p.keyword);
        }
        if (!p.brandIds.isEmpty() && p.categoryId != null && p.categoryId > 0) {
            return loadByBrandIdsAndCategory(request, p.brandIds, p.categoryId);
        }
        if (!p.brandIds.isEmpty()) {
            return loadByBrandIds(p.brandIds);
        }
        if (p.categoryId != null && p.categoryId > 0) {
            return loadByCategory(request, p.categoryId);
        }
        return loadAll();
    }

    private List<Map<String, Object>> loadByKeyword(HttpServletRequest request, String keyword) {
        System.out.println("Searching products with keyword: " + keyword);
        List<Map<String, Object>> products = productService.search(keyword);
        request.setAttribute("searchKeyword", keyword);
        return products;
    }

    private List<Map<String, Object>> loadByBrandIdsAndCategory(
            HttpServletRequest request, List<Integer> brandIds, int categoryId) {
        System.out.println("Loading products for brandIds: " + brandIds + " AND categoryId: " + categoryId);
        List<Map<String, Object>> products = productService.getByBrandIdsAndCategory(brandIds, categoryId);
        attachCategory(request, categoryId);
        return products;
    }

    private List<Map<String, Object>> loadByBrandIds(List<Integer> brandIds) {
        System.out.println("Loading products for brandIds: " + brandIds);
        return productService.getByBrandIds(brandIds);
    }

    private List<Map<String, Object>> loadByCategory(HttpServletRequest request, int categoryId) {
        System.out.println("Loading products for categoryId: " + categoryId);
        List<Map<String, Object>> products = productService.getByCategory(categoryId);
        attachCategory(request, categoryId);
        return products;
    }

    private List<Map<String, Object>> loadAll() {
        System.out.println("Loading all products");
        return productService.getAllProductsWithImage();
    }

    private void attachCategory(HttpServletRequest request, int categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        if (category != null) {
            request.setAttribute("currentCategory", category);
            System.out.println("Category found: " + category.getCategoryName());
        } else {
            System.err.println("Category not found for ID: " + categoryId);
        }
    }

    // ─── Lọc theo khoảng giá ────────────────────────────────────────────────

    private void applyPriceFilter(HttpServletRequest request, FilterParams p,
                                  List<Map<String, Object>> products) {
        if (p.minPriceStr == null && p.maxPriceStr == null) return;

        double minPrice = parsePrice(p.minPriceStr, 0, "minPrice");
        double maxPrice = parsePrice(p.maxPriceStr, Double.MAX_VALUE, "maxPrice");

        products.removeIf(product -> {
            double price = resolvePrice(product);
            return price < minPrice || price > maxPrice;
        });

        request.setAttribute("minPrice", minPrice);
        request.setAttribute("maxPrice", maxPrice);
        System.out.println("Filtered by price: " + minPrice + " - " + maxPrice
                + " → " + products.size() + " products");
    }

    private double parsePrice(String raw, double defaultValue, String fieldName) {
        if (raw == null || raw.trim().isEmpty()) return defaultValue;
        try {
            return Double.parseDouble(raw.trim());
        } catch (NumberFormatException e) {
            System.err.println("Invalid " + fieldName + ": " + raw);
            return defaultValue;
        }
    }

    private double resolvePrice(Map<String, Object> product) {
        Object priceObj = product.get("salePrice");
        if (priceObj == null || ((Number) priceObj).doubleValue() == 0) {
            priceObj = product.get("price");
        }
        return ((Number) priceObj).doubleValue();
    }

    // ─── Sắp xếp sản phẩm ───────────────────────────────────────────────────

    private void applySorting(HttpServletRequest request, String sortBy,
                              List<Map<String, Object>> products) {
        if (sortBy == null || sortBy.trim().isEmpty()) return;

        System.out.println("Sorting by: " + sortBy);
        Comparator<Map<String, Object>> comparator = buildComparator(sortBy);
        if (comparator != null) {
            products.sort(comparator);
        }
        request.setAttribute("sortBy", sortBy);
    }

    private Comparator<Map<String, Object>> buildComparator(String sortBy) {
        switch (sortBy) {
            case "price-asc":
                return Comparator.comparingDouble(this::resolvePrice);
            case "price-desc":
                return Comparator.comparingDouble(this::resolvePrice).reversed();
            case "name-asc":
                return Comparator.comparing(p -> (String) p.get("productName"));
            case "name-desc":
                return Comparator.comparing((Map<String, Object> p) ->
                        (String) p.get("productName")).reversed();
            case "rating-desc":
                return Comparator.comparingDouble((Map<String, Object> p) -> {
                    Object r = p.get("averageRating");
                    return r != null ? ((Number) r).doubleValue() : 0;
                }).reversed();
            default:
                return null;
        }
    }

    // ─── Phân trang ─────────────────────────────────────────────────────────

    private void applyPagination(HttpServletRequest request, int page,
                                 List<Map<String, Object>> products) {
        final int pageSize = 12;
        int totalProducts = products.size();
        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

        if (page > totalPages && totalPages > 0) page = totalPages;

        int startIndex = (page - 1) * pageSize;
        int endIndex   = Math.min(startIndex + pageSize, totalProducts);

        List<Map<String, Object>> pagedProducts =
                totalProducts > 0 ? products.subList(startIndex, endIndex) : new ArrayList<>();

        System.out.println("Page " + page + "/" + totalPages
                + " | Showing " + pagedProducts.size() + " products");

        request.setAttribute("products",      pagedProducts);
        request.setAttribute("totalProducts", totalProducts);
        request.setAttribute("currentPage",   page);
        request.setAttribute("totalPages",    totalPages);
    }

    // ─── Load dữ liệu sidebar ────────────────────────────────────────────────

    private void loadSidebarData(HttpServletRequest request) {
        List<Category> allCategories = categoryService.getAllCategories();
        List<Brand>    allBrands     = brandService.getAllBrands();

        request.setAttribute("listCategory",   allCategories != null ? allCategories : new ArrayList<>());
        request.setAttribute("allCategories",  allCategories);
        request.setAttribute("allBrands",      allBrands);

        System.out.println("ProductController completed successfully");
    }
}