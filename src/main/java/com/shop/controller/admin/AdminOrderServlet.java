package com.shop.controller.admin;

import com.shop.dao.order.OrderDAO;
import com.shop.services.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AdminOrderServlet", urlPatterns = {"/admin/api/orders"})
public class AdminOrderServlet extends HttpServlet {

    private OrderService orderService;
    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        orderService = new OrderService();
        orderDAO     = new OrderDAO();
        System.out.println(" AdminOrderServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        try {
            String action = request.getParameter("action");

            if ("list".equals(action) || action == null) {
                handleGetList(request, jsonResponse);
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Action không hợp lệ");
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

            if ("updateStatus".equals(action)) {
                handleUpdateStatus(request, jsonResponse);
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Action không hợp lệ");
            }

        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Lỗi server: " + e.getMessage());
        }

        out.print(jsonResponse.toString());
        out.flush();
    }

    //Lấy danh sách đơn hàng
    private void handleGetList(HttpServletRequest request, JSONObject jsonResponse)
            throws Exception {

        int page     = parseIntParam(request, "page",     1);
        int pageSize = parseIntParam(request, "pageSize", 20);

        List<Map<String, Object>> allOrders = OrderDAO.getRecentOrdersWithCustomer(10000);

        String statusFilter = request.getParameter("status");
        if (statusFilter != null && !statusFilter.trim().isEmpty() && !"ALL".equals(statusFilter)) {
            allOrders = allOrders.stream()
                    .filter(o -> statusFilter.equals(o.get("order_status")))
                    .collect(java.util.stream.Collectors.toList());
        }

        // Phân trang
        int total      = allOrders.size();
        int totalPages = (int) Math.ceil((double) total / pageSize);
        int startIndex = (page - 1) * pageSize;
        int endIndex   = Math.min(startIndex + pageSize, total);

        List<Map<String, Object>> pageOrders = startIndex < total
                ? allOrders.subList(startIndex, endIndex)
                : java.util.Collections.emptyList();

        JSONArray ordersArray = new JSONArray();
        for (Map<String, Object> o : pageOrders) {
            JSONObject item = new JSONObject();
            item.put("id",            o.get("id"));
            item.put("order_code",    o.get("order_code"));
            item.put("order_status",  o.get("order_status"));
            item.put("total_amount",  o.get("total_amount"));
            item.put("customer_name", o.get("customer_name"));
            item.put("created_at",    o.get("created_at") != null
                    ? o.get("created_at").toString() : "");

            item.put("signature",   JSONObject.NULL);
            item.put("is_verified", 0);
            item.put("key_id",      JSONObject.NULL);

            ordersArray.put(item);
        }

        JSONObject pagination = new JSONObject();
        pagination.put("currentPage", page);
        pagination.put("pageSize",    pageSize);
        pagination.put("totalOrders", total);
        pagination.put("totalPages",  totalPages);

        jsonResponse.put("success",    true);
        jsonResponse.put("orders",     ordersArray);
        jsonResponse.put("pagination", pagination);
    }

    // Cập nhật trạng thái đơn hàng
    private void handleUpdateStatus(HttpServletRequest request, JSONObject jsonResponse)
            throws Exception {

        int    orderId   = parseIntParam(request, "orderId", 0);
        String newStatus = request.getParameter("newStatus");

        if (orderId <= 0) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "ID đơn hàng không hợp lệ");
            return;
        }

        if (newStatus == null || newStatus.trim().isEmpty()) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Trạng thái không được rỗng");
            return;
        }

        try {
            orderService.updateStatus(orderId, newStatus);
            jsonResponse.put("success", true);
            jsonResponse.put("message", "Cập nhật trạng thái thành công");
        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", e.getMessage());
        }
    }

    private int parseIntParam(HttpServletRequest request, String name, int defaultValue) {
        try {
            String value = request.getParameter(name);
            return (value != null && !value.trim().isEmpty())
                    ? Integer.parseInt(value.trim())
                    : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}