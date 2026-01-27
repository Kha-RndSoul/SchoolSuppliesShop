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
                List<Banner> banners = bannerService.getAllBanners();
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
                jsonResponse.put("message", "Vui lòng sử dụng POST method cho action này");
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Lỗi server: " + e.getMessage());
        }

        out.print(jsonResponse.toString());
        out.flush();
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
            System.out.println("   Action: " + action);

            if (action == null || action.isEmpty()) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Thiếu parameter 'action'");
                out.print(jsonResponse.toString());
                out.flush();
                return;
            }

            // 1. Toggle Status
            if ("toggleStatus".equals(action)) {
                String idStr = request.getParameter("id");
                String statusStr = request.getParameter("status");
                if (idStr != null && statusStr != null) {
                    int id = Integer.parseInt(idStr);
                    boolean newStatus = Boolean.parseBoolean(statusStr);
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
                    jsonResponse.put("message", "Thiếu tham số id hoặc status");
                }
            }
            // 2. Delete Banner
            else if ("delete".equals(action)) {
                String idStr = request.getParameter("id");
                if (idStr != null && !idStr.isEmpty()) {
                    int id = Integer.parseInt(idStr);
                    boolean deleted = bannerService.deleteBanner(id);
                    if (deleted) {
                        jsonResponse.put("success", true);
                        jsonResponse.put("message", "Xóa banner thành công!");
                    } else {
                        jsonResponse.put("success", false);
                        jsonResponse.put("message", "Lỗi khi xóa banner (ID không tồn tại hoặc lỗi DB).");
                    }
                } else {
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "Thiếu ID banner");
                }
            } else {
                System.out.println(" Unknown action: " + action);
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Action không hợp lệ: " + action);
            }
        } catch (NumberFormatException e) {
            System.err.println(" Invalid number format: " + e.getMessage());
            jsonResponse.put("success", false);
            jsonResponse.put("message", "ID không hợp lệ");
        } catch (Exception e) {
            System.err.println(" Error in doPost: " + e.getMessage());
            e.printStackTrace();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Lỗi server: " + e.getMessage());
        }
        out.print(jsonResponse.toString());
        out.flush();
    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward DELETE requests to doPost
        doPost(request, response);
    }
}