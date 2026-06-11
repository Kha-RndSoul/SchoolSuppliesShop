package com.shop.controller.admin;

import com.shop.model.Banner;
import com.shop.services.BannerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AdminBannerAddServlet", urlPatterns = {"/admin/api/banner/add"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class AdminBannerAddServlet extends HttpServlet {

    private BannerService bannerService;

    @Override
    public void init() throws ServletException {
        bannerService = new BannerService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        try {
            // Lấy dữ liệu từ form
            String title = request.getParameter("title");
            String statusStr = request.getParameter("status");
            boolean status = "true".equals(statusStr);

            // Validate
            if (title == null || title.trim().isEmpty()) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Tiêu đề không được để trống");
                out.print(jsonResponse.toString());
                return;
            }

            // Xử lý upload ảnh
            Part filePart = request.getPart("bannerImage");
            if (filePart == null || filePart.getSize() == 0) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Vui lòng chọn hình ảnh banner");
                out.print(jsonResponse.toString());
                return;
            }

            String fileName = filePart.getSubmittedFileName();
            String contentType = filePart.getContentType();

            // Validate file type
            if (!contentType.startsWith("image/")) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "File upload phải là ảnh");
                out.print(jsonResponse.toString());
                return;
            }

            // Tạo tên file unique
            String fileExtension = fileName.substring(fileName.lastIndexOf("."));
            String uniqueFileName = "banner_" + System.currentTimeMillis() + fileExtension;

            // Đường dẫn lưu file
            String uploadPath = getServletContext().getRealPath("") + "assets/images/banners";
            java.io.File uploadDir = new java.io.File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Lưu file
            String filePath = uploadPath + java.io.File.separator + uniqueFileName;
            filePart.write(filePath);

            // Tạo banner object
            Banner banner = new Banner();
            banner.setTitle(title.trim());
            banner.setImageUrl("/assets/images/banners/" + uniqueFileName);
            banner.setStatus(status);

            // Lưu vào database
            boolean created = bannerService.createBanner(banner);

            if (created) {
                System.out.println("✓ Banner added successfully: " + title);
                jsonResponse.put("success", true);
                jsonResponse.put("message", "Thêm banner thành công!");
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Không thể thêm banner vào database");
            }

        } catch (Exception e) {
            System.err.println("✗ Error adding banner: " + e.getMessage());
            e.printStackTrace();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Lỗi: " + e.getMessage());
        }

        out.print(jsonResponse.toString());
        out.flush();
    }
}