package com.shop.controller.admin;

import com.shop.dao.product.ProductDAO;
import com.shop.dao.product.ProductImageDAO;
import com.shop.model.Product;
import com.shop.model.ProductImage;
import com.shop.services.CategoryService;
import com.shop.services.BrandService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import org.json.JSONObject;

/**
 * Servlet xử lý các thao tác CRUD sản phẩm từ Admin
 * URL: /admin/products
 */
@WebServlet(name = "AdminProductServlet", urlPatterns = {"/admin/products"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class AdminProductServlet extends HttpServlet {

    private ProductDAO productDAO;
    private CategoryService categoryService;
    private BrandService brandService;

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAO();
        categoryService = new CategoryService();
        brandService = new BrandService();
        System.out.println("✓ AdminProductServlet initialized");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set response type là JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        try {
            // Lấy action từ request
            String action = request.getParameter("action");

            if ("add".equals(action)) {
                handleAddProduct(request, jsonResponse);
            } else if ("update".equals(action)) {
                handleUpdateProduct(request, jsonResponse);
            } else if ("delete".equals(action)) {
                handleDeleteProduct(request, jsonResponse);
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Action không hợp lệ");
            }

        } catch (Exception e) {
            System.err.println("✗ Error in AdminProductServlet: " + e.getMessage());
            e.printStackTrace();

            jsonResponse.put("success", false);
            jsonResponse.put("message", "Lỗi: " + e.getMessage());
        }

        out.print(jsonResponse.toString());
        out.flush();
    }

    /**
     * Xử lý thêm sản phẩm mới
     */
    private void handleAddProduct(HttpServletRequest request, JSONObject jsonResponse) {
        try {
            // Lấy dữ liệu từ form
            String productName = request.getParameter("productName");
            String description = request.getParameter("description");
            String categoryIdStr = request.getParameter("categoryId");
            String brandIdStr = request.getParameter("brandId");
            String priceStr = request.getParameter("price");
            String salePriceStr = request.getParameter("salePrice");
            String stockQuantityStr = request.getParameter("stockQuantity");

            // Validate dữ liệu bắt buộc
            if (productName == null || productName.trim().isEmpty()) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Tên sản phẩm không được để trống");
                return;
            }

            if (categoryIdStr == null || categoryIdStr.trim().isEmpty()) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Vui lòng chọn danh mục");
                return;
            }

            if (brandIdStr == null || brandIdStr.trim().isEmpty()) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Vui lòng chọn thương hiệu");
                return;
            }

            if (priceStr == null || priceStr.trim().isEmpty()) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Giá bán không được để trống");
                return;
            }

            if (stockQuantityStr == null || stockQuantityStr.trim().isEmpty()) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Số lượng tồn kho không được để trống");
                return;
            }

            // Parse dữ liệu
            int categoryId = Integer.parseInt(categoryIdStr);
            int brandId = Integer.parseInt(brandIdStr);
            double price = Double.parseDouble(priceStr);
            double salePrice = 0;
            if (salePriceStr != null && !salePriceStr.trim().isEmpty()) {
                salePrice = Double.parseDouble(salePriceStr);
            }
            int stockQuantity = Integer.parseInt(stockQuantityStr);

            // Validate giá trị
            if (price <= 0) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Giá bán phải lớn hơn 0");
                return;
            }

            if (salePrice < 0) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Giá khuyến mãi không được âm");
                return;
            }

            if (salePrice > 0 && salePrice >= price) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Giá khuyến mãi phải nhỏ hơn giá bán");
                return;
            }

            if (stockQuantity < 0) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Số lượng tồn kho không được âm");
                return;
            }

            // Tạo đối tượng Product
            Product product = new Product();
            product.setProductName(productName.trim());
            product.setDescription(description != null ? description.trim() : "");
            product.setCategoryId(categoryId);
            product.setBrandId(brandId);
            product.setPrice(price);
            product.setSalePrice(salePrice);
            product.setStockQuantity(stockQuantity);
            product.setSoldCount(0);
            product.setIsActive(true);

            // Thêm vào database và nhận ID
            int productId = productDAO.insertProduct(product);

            System.out.println("✓ Product added successfully: " + productName + " (ID: " + productId + ")");

            // Xử lý upload ảnh
            Part filePart = request.getPart("productImage");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = filePart.getSubmittedFileName();

                // Validate file type
                String contentType = filePart.getContentType();
                if (!contentType.startsWith("image/")) {
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "File upload phải là ảnh");
                    return;
                }

                // Tạo tên file unique
                String fileExtension = fileName.substring(fileName.lastIndexOf("."));
                String uniqueFileName = "product_" + productId + "_" + System.currentTimeMillis() + fileExtension;

                // Đường dẫn lưu file (webapp/assets/images/products/)
                String uploadPath = getServletContext().getRealPath("") + "assets/images/products";
                java.io.File uploadDir = new java.io.File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // Lưu file
                String filePath = uploadPath + java.io.File.separator + uniqueFileName;
                filePart.write(filePath);

                // Lưu thông tin ảnh vào database
                ProductImageDAO imageDAO = new ProductImageDAO();
                ProductImage productImage = new ProductImage();
                productImage.setProductId(productId);
                productImage.setImageUrl("/assets/images/products/" + uniqueFileName);
                productImage.setIsPrimary(true); // Ảnh đầu tiên là ảnh chính
                imageDAO.insertImage(productImage);

                System.out.println("✓ Product image uploaded: " + uniqueFileName);
            }

            jsonResponse.put("success", true);
            jsonResponse.put("message", "Thêm sản phẩm thành công!");
            jsonResponse.put("productName", productName);
            jsonResponse.put("productId", productId);

        } catch (NumberFormatException e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Dữ liệu số không hợp lệ");
        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Lỗi khi thêm sản phẩm: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Xử lý cập nhật sản phẩm
     */
    private void handleUpdateProduct(HttpServletRequest request, JSONObject jsonResponse) {
        // TODO: Implement update product
        jsonResponse.put("success", false);
        jsonResponse.put("message", "Chức năng đang phát triển");
    }

    /**
     * Xử lý xóa sản phẩm
     */
    private void handleDeleteProduct(HttpServletRequest request, JSONObject jsonResponse) {
        // TODO: Implement delete product
        jsonResponse.put("success", false);
        jsonResponse.put("message", "Chức năng đang phát triển");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GET method is not supported");
    }
}