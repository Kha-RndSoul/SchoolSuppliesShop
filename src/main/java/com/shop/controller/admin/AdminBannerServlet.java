package com.shop.controller.admin;

import com.shop.model.Banner;
import com.shop.services.BannerService; // Sử dụng Service
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Servlet quản lý Banner
 */
@WebServlet(name = "AdminBannerServlet", urlPatterns = {"/admin/banners"})
public class AdminBannerServlet extends HttpServlet {

    private BannerService bannerService;

    @Override
    public void init() throws ServletException {
        // Khởi tạo Service
        bannerService = new BannerService();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();
        String action = request.getParameter("action");

        try {
            if ("getList".equals(action)) {
                // Gọi Service lấy tất cả banner
                List<Banner> banners = bannerService.getAllBanners();
                // Chuyển List Java sang JSON Array
                JSONArray arr = new JSONArray();
                for (Banner b : banners) {
                    JSONObject item = new JSONObject();
                    item.put("id", b.getId());
                    item.put("title", b.getTitle());
                    item.put("imageUrl", b.getImageUrl());
                    item.put("status", b.isStatus());
                    arr.put(item);
                }
                jsonResponse.put("success", true);
                jsonResponse.put("data", arr);
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Action không hợp lệ.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Lỗi server: " + e.getMessage());
        }

        out.print(jsonResponse.toString());
        out.flush();
    }
    /**
     * doPost: Xử lý thay đổi dữ liệu (Toggle Status, Delete...)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        try {
            String action = request.getParameter("action");
            // 1. Xử lý Toggle Status (Bật/Tắt)
            if ("toggleStatus".equals(action)) {
                String idStr = request.getParameter("id");
                String statusStr = request.getParameter("status");
                if (idStr != null && statusStr != null) {
                    int id = Integer.parseInt(idStr);
                    boolean newStatus = Boolean.parseBoolean(statusStr);
                    // Gọi Service
                    boolean updated = bannerService.toggleBannerStatus(id, newStatus);
                    if (updated) {
                        jsonResponse.put("success", true);
                        jsonResponse.put("message", "Cập nhật trạng thái thành công!");
                    } else {
                        jsonResponse.put("success", false);
                        jsonResponse.put("message", "Không thể cập nhật (ID không tồn tại hoặc lỗi DB).");
                    }
                } else {
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "Dữ liệu không hợp lệ.");
                }
            }
            // 2. Xử lý Xóa Banner
            else if ("delete".equals(action)) {
                String idStr = request.getParameter("id");
                if (idStr != null) {
                    int id = Integer.parseInt(idStr);
                    // Gọi Service
                    boolean deleted = bannerService.deleteBanner(id);
                    if (deleted) {
                        jsonResponse.put("success", true);
                        jsonResponse.put("message", "Xóa banner thành công!");
                    } else {
                        jsonResponse.put("success", false);
                        jsonResponse.put("message", "Lỗi khi xóa banner.");
                    }
                }
            }
            // Action lạ
            else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Action không tồn tại: " + action);
            }
        } catch (NumberFormatException e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Lỗi định dạng số (ID phải là số nguyên).");
        } catch (IllegalArgumentException e) {
            // Catch các lỗi do Service ném ra (ví dụ: ID <= 0)
            jsonResponse.put("success", false);
            jsonResponse.put("message", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Lỗi hệ thống: " + e.getMessage());
        }
        out.print(jsonResponse.toString());
        out.flush();
    }
}