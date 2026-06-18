package com.shop.controller.order;

import com.shop.model.Customer;
import com.shop.model.UserKey;
import com.shop.services.UserKeyService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

@WebServlet(name = "KeyController", urlPatterns = {"/key"})
@MultipartConfig
public class KeyController extends HttpServlet {

    private UserKeyService userKeyService;

    @Override
    public void init() throws ServletException {
        userKeyService = new UserKeyService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Customer customer = getCustomer(request, response);
        if (customer == null) return;

        request.setAttribute("activeKey", userKeyService.getActiveKey(customer.getId()));
        request.setAttribute("keys", userKeyService.getAllKeys(customer.getId()));

        request.getRequestDispatcher("/WEB-INF/jsp/order/key-manager.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Customer customer = getCustomer(request, response);
        if (customer == null) return;

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "upload":
                handleUpload(request, response, customer);
                break;
            case "reportLost":
                handleReportLost(request, response, customer);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/key");
        }
    }

    // upload public key
    private void handleUpload(HttpServletRequest request,
                              HttpServletResponse response,
                              Customer customer) throws IOException, ServletException {
        try {
            Part filePart = request.getPart("publicKeyFile");
            if (filePart == null || filePart.getSize() == 0) {
                response.sendRedirect(request.getContextPath()
                        + "/key?error=" + java.net.URLEncoder.encode(
                        "Vui lòng chọn file .key", "UTF-8"));
                return;
            }
            // lấy tên file gốc để hiển thị
            String fileName = filePart.getSubmittedFileName();
            InputStream is = filePart.getInputStream();
            byte[] encKey = is.readAllBytes();
            is.close();

            userKeyService.uploadPublicKey(customer.getId(), encKey, fileName);

            response.sendRedirect(request.getContextPath()
                    + "/key?success=" + java.net.URLEncoder.encode(
                    "Upload public key thành công! Khóa đã được kích hoạt.", "UTF-8"));

        } catch (GeneralSecurityException e) {
            response.sendRedirect(request.getContextPath()
                    + "/key?error=" + java.net.URLEncoder.encode(
                    "File không hợp lệ, vui lòng upload đúng file public key DSA", "UTF-8"));
        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
            response.sendRedirect(request.getContextPath()
                    + "/key?error=" + java.net.URLEncoder.encode(
                    e.getMessage(), "UTF-8"));
        }
    }
// Báo mất khóa
    private void handleReportLost(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Customer customer) throws IOException {
        try {
            String keyIdStr = request.getParameter("keyId");
            if (keyIdStr == null || keyIdStr.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/key");
                return;
            }

            int keyId = Integer.parseInt(keyIdStr.trim());
            UserKey key = userKeyService.getById(keyId);
            if (key == null || key.getCustomerId() != customer.getId()) {
                response.sendRedirect(request.getContextPath()
                        + "/key?error=" + java.net.URLEncoder.encode(
                        "Không tìm thấy khóa", "UTF-8"));
                return;
            }
            userKeyService.reportLost(keyId);

            response.sendRedirect(request.getContextPath()
                    + "/key?success=" + java.net.URLEncoder.encode(
                    "Đã báo mất khóa. Vui lòng upload khóa mới.", "UTF-8"));

        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
            response.sendRedirect(request.getContextPath()
                    + "/key?error=" + java.net.URLEncoder.encode(
                    "Lỗi báo mất khóa: " + e.getMessage(), "UTF-8"));
        }
    }
    // lấy customer từ session, chưa login thì về trang login
    private Customer getCustomer(HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("customer") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return null;
        }
        return (Customer) session.getAttribute("customer");
    }
}