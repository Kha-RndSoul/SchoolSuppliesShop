package com.shop.controller.admin;

import com.shop.dao.product.ProductDAO;
import com.shop.dao.product.ProductImageDAO;
import com.shop.model.Product;
import com.shop.model.ProductImage;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

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
    private ProductImageDAO productImageDAO;

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAO();
        productImageDAO = new ProductImageDAO();
        System.out.println("✓ AdminProductServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if ("getProduct".equals(action)) {
            handleGetProduct(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        try {
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
     * Lấy thông tin sản phẩm để edit
     */
    private void handleGetProduct(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        try {
            String productIdStr = request.getParameter("productId");

            if (productIdStr == null || productIdStr.trim().isEmpty()) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "ID sản phẩm không hợp lệ");
                out.print(jsonResponse.toString());
                return;
            }

            int productId = Integer.parseInt(productIdStr);
            Map<String, Object> product = productDAO.getProductByIdWithImage(productId);

            if (product == null) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Sản phẩm không tồn tại");
            } else {
                jsonResponse.put("success", true);
                jsonResponse.put("product", product);
            }

        } catch (NumberFormatException e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "ID sản phẩm không hợp lệ");
        } catch (Exception e) {
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
            String productName = request.getParameter("productName");
            String description = request.getParameter("description");
            String categoryIdStr = request.getParameter("categoryId");
            String brandIdStr = request.getParameter("brandId");
            String priceStr = request.getParameter("price");
            String salePriceStr = request.getParameter("salePrice");
            String stockQuantityStr = request.getParameter("stockQuantity");

            // Validate
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

            // Parse
            int categoryId = Integer.parseInt(categoryIdStr);
            int brandId = Integer.parseInt(brandIdStr);
            double price = Double.parseDouble(priceStr);
            double salePrice = 0;
            if (salePriceStr != null && !salePriceStr.trim().isEmpty()) {
                salePrice = Double.parseDouble(salePriceStr);
            }
            int stockQuantity = Integer.parseInt(stockQuantityStr);

            // Validate values
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

            // Tạo Product object
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

            // Insert và lấy ID
            int productId = productDAO.insertProduct(product);
            System.out.println("✓ Product added: " + productName + " (ID: " + productId + ")");

            // Xử lý upload ảnh
            Part filePart = request.getPart("productImage");
            if (filePart != null && filePart.getSize() > 0) {
                String imageUrl = saveProductImage(filePart, productId);
                if (imageUrl != null) {
                    ProductImage productImage = new ProductImage();
                    productImage.setProductId(productId);
                    productImage.setImageUrl(imageUrl);
                    productImage.setIsPrimary(true);
                    productImageDAO.insertImage(productImage);
                }
            }

            jsonResponse.put("success", true);
            jsonResponse.put("message", "Thêm sản phẩm thành công!");
            jsonResponse.put("productId", productId);

        } catch (NumberFormatException e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Dữ liệu số không hợp lệ");
        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * ⭐ HOÀN THIỆN: Xử lý cập nhật sản phẩm
     */
    private void handleUpdateProduct(HttpServletRequest request, JSONObject jsonResponse) {
        try {
            String productIdStr = request.getParameter("productId");

            if (productIdStr == null || productIdStr.trim().isEmpty()) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "ID sản phẩm không hợp lệ");
                return;
            }

            int productId = Integer.parseInt(productIdStr);

            // Lấy thông tin sản phẩm hiện tại
            Map<String, Object> existingProduct = productDAO.getProductByIdWithImage(productId);
            if (existingProduct == null) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Sản phẩm không tồn tại");
                return;
            }

            // Lấy dữ liệu từ form
            String productName = request.getParameter("productName");
            String description = request.getParameter("description");
            String categoryIdStr = request.getParameter("categoryId");
            String brandIdStr = request.getParameter("brandId");
            String priceStr = request.getParameter("price");
            String salePriceStr = request.getParameter("salePrice");
            String stockQuantityStr = request.getParameter("stockQuantity");

            // Validate
            if (productName == null || productName.trim().isEmpty()) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Tên sản phẩm không được để trống");
                return;
            }

            // Parse
            int categoryId = Integer.parseInt(categoryIdStr);
            int brandId = Integer.parseInt(brandIdStr);
            double price = Double.parseDouble(priceStr);
            double salePrice = 0;
            if (salePriceStr != null && !salePriceStr.trim().isEmpty()) {
                salePrice = Double.parseDouble(salePriceStr);
            }
            int stockQuantity = Integer.parseInt(stockQuantityStr);

            // Validate values
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

            // Tạo Product object để update
            Product product = new Product();
            product.setId(productId);
            product.setProductName(productName.trim());
            product.setDescription(description != null ? description.trim() : "");
            product.setCategoryId(categoryId);
            product.setBrandId(brandId);
            product.setPrice(price);
            product.setSalePrice(salePrice);
            product.setStockQuantity(stockQuantity);
            product.setIsActive(true); // Giữ active

            // Lấy soldCount từ sản phẩm cũ
            Integer soldCount = (Integer) existingProduct.get("soldCount");
            product.setSoldCount(soldCount != null ? soldCount : 0);

            // Update vào database
            productDAO.updateProduct(product);
            System.out.println("✓ Product updated: " + productName + " (ID: " + productId + ")");

            // Xử lý upload ảnh mới (nếu có)
            Part filePart = request.getPart("productImage");
            if (filePart != null && filePart.getSize() > 0) {
                // Xóa ảnh cũ
                List<ProductImage> oldImages = productImageDAO.getByProductId(productId);
                for (ProductImage img : oldImages) {
                    deleteImageFile(img.getImageUrl());
                    productImageDAO.deleteImage(img.getId());
                }

                // Upload ảnh mới
                String imageUrl = saveProductImage(filePart, productId);
                if (imageUrl != null) {
                    ProductImage productImage = new ProductImage();
                    productImage.setProductId(productId);
                    productImage.setImageUrl(imageUrl);
                    productImage.setIsPrimary(true);
                    productImageDAO.insertImage(productImage);
                }
            }

            jsonResponse.put("success", true);
            jsonResponse.put("message", "Cập nhật sản phẩm thành công!");

        } catch (NumberFormatException e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Dữ liệu số không hợp lệ");
        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Xử lý xóa sản phẩm
     */
    private void handleDeleteProduct(HttpServletRequest request, JSONObject jsonResponse) {
        try {
            String productIdStr = request.getParameter("productId");

            if (productIdStr == null || productIdStr.trim().isEmpty()) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "ID sản phẩm không hợp lệ");
                return;
            }

            int productId = Integer.parseInt(productIdStr);
            Map<String, Object> product = productDAO.getProductByIdWithImage(productId);

            if (product == null) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Sản phẩm không tồn tại");
                return;
            }

            // Xóa ảnh
            List<ProductImage> images = productImageDAO.getByProductId(productId);
            for (ProductImage img : images) {
                deleteImageFile(img.getImageUrl());
                productImageDAO.deleteImage(img.getId());
            }

            // Xóa sản phẩm
            productDAO.deleteProduct(productId);
            System.out.println("✓ Product deleted: ID " + productId);

            jsonResponse.put("success", true);
            jsonResponse.put("message", "Xóa sản phẩm thành công!");

        } catch (NumberFormatException e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "ID sản phẩm không hợp lệ");
        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Lưu ảnh sản phẩm và trả về URL
     */
    private String saveProductImage(Part filePart, int productId) {
        try {
            String fileName = filePart.getSubmittedFileName();
            String contentType = filePart.getContentType();

            if (!contentType.startsWith("image/")) {
                return null;
            }

            String fileExtension = fileName.substring(fileName.lastIndexOf("."));
            String uniqueFileName = "product_" + productId + "_" + System.currentTimeMillis() + fileExtension;

            String uploadPath = getServletContext().getRealPath("") + "assets/images/products";
            java.io.File uploadDir = new java.io.File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String filePath = uploadPath + java.io.File.separator + uniqueFileName;
            filePart.write(filePath);

            return "/assets/images/products/" + uniqueFileName;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Xóa file ảnh trên server
     */
    private void deleteImageFile(String imageUrl) {
        try {
            if (imageUrl != null && !imageUrl.isEmpty()) {
                String filePath = getServletContext().getRealPath("") + imageUrl;
                java.io.File file = new java.io.File(filePath);
                if (file.exists()) {
                    file.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}