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
import java.util.Base64;
import java.util.List;

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

        HttpSession session = request.getSession(false);

        byte[] pendingPrivate = (byte[]) session.getAttribute("pendingPrivateKey");
        byte[] pendingPublic  = (byte[]) session.getAttribute("pendingPublicKey");

        if (pendingPrivate != null && pendingPublic != null) {
            session.removeAttribute("pendingPrivateKey");
            session.removeAttribute("pendingPublicKey");

            request.setAttribute("pendingPrivateKeyB64",
                    Base64.getEncoder().encodeToString(pendingPrivate));
            request.setAttribute("pendingPublicKeyB64",
                    Base64.getEncoder().encodeToString(pendingPublic));
            request.setAttribute("customerId", customer.getId());

            Boolean newKeyActivated = (Boolean) session.getAttribute("newKeyActivated");
            if (newKeyActivated != null && newKeyActivated) {
                session.removeAttribute("newKeyActivated");
                request.setAttribute("newKeyActivated", true);
            } else {
                request.setAttribute("hasPendingKey", true);
            }
        }

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
            case "generate":
                handleGenerate(request, response, customer);
                break;
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

    private void handleGenerate(HttpServletRequest request,
                                HttpServletResponse response,
                                Customer customer) throws IOException {
        try {
            byte[][] keyPair = userKeyService.generateKeyPairOnly();
            UserKey existing = userKeyService.getActiveKey(customer.getId());
            HttpSession session = request.getSession();

            if (existing == null) {
                userKeyService.saveAndActivate(customer.getId(), keyPair[1], "GENERATED");
                session.setAttribute("newKeyActivated", true);
            }

            session.setAttribute("pendingPrivateKey", keyPair[0]);
            session.setAttribute("pendingPublicKey",  keyPair[1]);

            response.sendRedirect(request.getContextPath() + "/key");

        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
            response.sendRedirect(request.getContextPath()
                    + "/key?error=" + java.net.URLEncoder.encode(
                    "Lỗi sinh khóa: " + e.getMessage(), "UTF-8"));
        }
    }

    private void handleUpload(HttpServletRequest request,
                              HttpServletResponse response,
                              Customer customer) throws IOException, ServletException {
        try {
            HttpSession session = request.getSession(false);
            byte[] pendingPublic = (session != null)
                    ? (byte[]) session.getAttribute("pendingPublicKey") : null;

            if (pendingPublic != null) {
                session.removeAttribute("pendingPublicKey");
                session.removeAttribute("pendingPrivateKey");

                userKeyService.saveAndActivate(customer.getId(), pendingPublic, "GENERATED");

                response.sendRedirect(request.getContextPath()
                        + "/key?success=" + java.net.URLEncoder.encode(
                        "Khóa mới đã được kích hoạt.", "UTF-8"));

            } else {
                Part filePart = request.getPart("publicKeyFile");
                if (filePart == null || filePart.getSize() == 0) {
                    response.sendRedirect(request.getContextPath()
                            + "/key?error=" + java.net.URLEncoder.encode(
                            "Vui lòng chọn file .key", "UTF-8"));
                    return;
                }

                InputStream is = filePart.getInputStream();
                byte[] encKey = is.readAllBytes();
                is.close();

                userKeyService.uploadPublicKey(customer.getId(), encKey);

                response.sendRedirect(request.getContextPath()
                        + "/key?success=" + java.net.URLEncoder.encode(
                        "Upload public key thành công!", "UTF-8"));
            }

        } catch (GeneralSecurityException e) {
            response.sendRedirect(request.getContextPath()
                    + "/key?error=" + java.net.URLEncoder.encode(
                    "File không hợp lệ.", "UTF-8"));
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath()
                    + "/key?error=" + java.net.URLEncoder.encode(
                    "Lỗi upload: " + e.getMessage(), "UTF-8"));
        }
    }

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
                    "Đã báo mất khóa.", "UTF-8"));

        } catch (Exception e) {
            response.sendRedirect(request.getContextPath()
                    + "/key?error=" + java.net.URLEncoder.encode(
                    "Lỗi báo mất khóa: " + e.getMessage(), "UTF-8"));
        }
    }

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