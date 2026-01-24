package com.shop.controller.admin;

import com.shop.dao.support.BannerDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/api/banner/status")
public class UpdateBannerStatusServlet extends HttpServlet {

    private BannerDAO bannerDAO = new BannerDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // 1. Lấy tham số từ client gửi lên
            String idStr = request.getParameter("id");
            String statusStr = request.getParameter("status");
            // 2. Kiểm tra dữ liệu
            if (idStr == null || statusStr == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            int id = Integer.parseInt(idStr);
            boolean status = Boolean.parseBoolean(statusStr); // "true" -> true, "false" -> false
            // 3. Gọi hàm updateStatus từ BannerDAO
            boolean success = bannerDAO.updateStatus(id, status);
            // 4. Trả kết quả về
            if (success) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Success");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Failed to update DB");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}