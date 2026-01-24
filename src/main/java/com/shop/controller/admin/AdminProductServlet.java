package com.shop.controller.admin;

import com.shop.dao.product.ProductDAO;
import com.shop.dao.product.ProductImageDAO;
import com.shop.model.Product;
import com.shop.model.ProductImage;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
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
        System.out.println(" AdminProductServlet initialized");
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
            System.err.println(" Error in AdminProductServlet: " + e.getMessage());
            e.printStackTrace();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Lỗi: " + e.getMessage());
        }

        out.print(jsonResponse.toString());
        out.flush();
    }
    /**
     * Lấy thông tin sản phẩm và danh sách ảnh để edit
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
            // 1.Lấy thông tin sản phẩm
            Map<String, Object> productMap = productDAO.getProductByIdWithImage(productId);
            if (productMap == null) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Sản phẩm không tồn tại");
            } else {
                JSONObject productJson = new JSONObject(productMap);

                // 2.Xử lý key "imageUrl" (
                if (!productJson.has("imageUrl")) {
                    if (productJson.has("image_url")) productJson.put("imageUrl", productJson.get("image_url"));
                    else if (productJson.has("image")) productJson.put("imageUrl", productJson.get("image"));
                    else if (productJson.has("productImage")) productJson.put("imageUrl", productJson.get("productImage"));
                }

                // 3.Lấy List ảnh và chuyển thành JSONArray ===
                List<ProductImage> images = productImageDAO.getByProductId(productId);
                JSONArray imagesArray = new JSONArray();

                for (ProductImage img : images) {
                    JSONObject imgJson = new JSONObject();
                    imgJson.put("id", img.getId());
                    imgJson.put("imageUrl", img.getImageUrl());
                    imgJson.put("isPrimary", img.getIsPrimary());
                    imagesArray.put(imgJson);
                }
                // Đưa mảng ảnh vào trong object sản phẩm
                productJson.put("images", imagesArray);
                jsonResponse.put("success", true);
                jsonResponse.put("product", productJson);
            }

        } catch (NumberFormatException e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "ID sản phẩm không phải số");
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console server để debug
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Lỗi Server: " + e.getMessage());
        }

        out.print(jsonResponse.toString());
        out.flush();
    }

    private void handleAddProduct(HttpServletRequest request, JSONObject jsonResponse) {
        try {
            // Lấy dữ liệu
            String productName = request.getParameter("productName");
            String description = request.getParameter("description");
            String categoryIdStr = request.getParameter("categoryId");
            String brandIdStr = request.getParameter("brandId");
            String priceStr = request.getParameter("price");
            String salePriceStr = request.getParameter("salePrice");
            String stockQuantityStr = request.getParameter("stockQuantity");

            // Validate cơ bản
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

            // Parse dữ liệu
            int categoryId = Integer.parseInt(categoryIdStr);
            int brandId = Integer.parseInt(brandIdStr);
            double price = Double.parseDouble(priceStr);
            double salePrice = (salePriceStr != null && !salePriceStr.trim().isEmpty()) ? Double.parseDouble(salePriceStr) : 0;
            int stockQuantity = Integer.parseInt(stockQuantityStr);

            // Tạo Object
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

            int productId = productDAO.insertProduct(product);
            // Xử lý ảnh
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

        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleUpdateProduct(HttpServletRequest request, JSONObject jsonResponse) {
        try {
            String productIdStr = request.getParameter("productId");
            if (productIdStr == null) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Thiếu ID sản phẩm");
                return;
            }
            int productId = Integer.parseInt(productIdStr);
            // Các bước lấy tham số tương tự Add
            String productName = request.getParameter("productName");
            String description = request.getParameter("description");
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            int brandId = Integer.parseInt(request.getParameter("brandId"));
            double price = Double.parseDouble(request.getParameter("price"));
            String salePriceStr = request.getParameter("salePrice");
            double salePrice = (salePriceStr != null && !salePriceStr.trim().isEmpty()) ? Double.parseDouble(salePriceStr) : 0;
            int stockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));

            Product product = new Product();
            product.setId(productId);
            product.setProductName(productName);
            product.setDescription(description);
            product.setCategoryId(categoryId);
            product.setBrandId(brandId);
            product.setPrice(price);
            product.setSalePrice(salePrice);
            product.setStockQuantity(stockQuantity);
            product.setIsActive(true);
            // Lấy soldCount cũ để không bị reset về 0
            Map<String, Object> oldProd = productDAO.getProductByIdWithImage(productId);
            if(oldProd != null && oldProd.get("soldCount") != null) {
                product.setSoldCount((Integer) oldProd.get("soldCount"));
            } else {
                product.setSoldCount(0);
            }
            productDAO.updateProduct(product);
            // Xử lý ảnh
            Part filePart = request.getPart("productImage");
            if (filePart != null && filePart.getSize() > 0) {
                // Xóa ảnh cũ logic
                List<ProductImage> oldImages = productImageDAO.getByProductId(productId);
                for (ProductImage img : oldImages) {
                    deleteImageFile(img.getImageUrl());
                    productImageDAO.deleteImage(img.getId());
                }
                // Thêm ảnh mới
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
            jsonResponse.put("message", "Cập nhật thành công!");

        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleDeleteProduct(HttpServletRequest request, JSONObject jsonResponse) {
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));

            // Xóa file ảnh
            List<ProductImage> images = productImageDAO.getByProductId(productId);
            for (ProductImage img : images) {
                deleteImageFile(img.getImageUrl());
                productImageDAO.deleteImage(img.getId());
            }

            productDAO.deleteProduct(productId);

            jsonResponse.put("success", true);
            jsonResponse.put("message", "Xóa sản phẩm thành công");
        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Lỗi xóa: " + e.getMessage());
        }
    }

    private String saveProductImage(Part filePart, int productId) {
        try {
            String fileName = filePart.getSubmittedFileName();
            // Đặt tên unique
            String fileExtension = fileName.substring(fileName.lastIndexOf("."));
            String uniqueFileName = "product_" + productId + "_" + System.currentTimeMillis() + fileExtension;
            // Đường dẫn thực trên server
            String uploadPath = getServletContext().getRealPath("") + "assets" + File.separator + "images" + File.separator + "products";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            filePart.write(uploadPath + File.separator + uniqueFileName);

            return "/assets/images/products/" + uniqueFileName;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void deleteImageFile(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) return;
        try {
            String realPath = getServletContext().getRealPath("") + imageUrl;
            File file = new File(realPath);
            if (file.exists()) file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}