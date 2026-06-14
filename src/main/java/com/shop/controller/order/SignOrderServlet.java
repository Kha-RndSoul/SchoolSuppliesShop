package com.shop.controller.order;

import com.shop.dao.order.OrderDAO;
import com.shop.model.Customer;
import com.shop.model.Order;
import com.shop.model.UserKey;
import com.shop.services.UserKeyService;
import com.shop.util.SignatureUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@WebServlet(name = "SignOrderServlet", urlPatterns = {"/sign-order"})
@MultipartConfig
public class SignOrderServlet extends HttpServlet {

    private OrderDAO orderDAO;
    private UserKeyService userKeyService;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
        userKeyService = new UserKeyService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Customer customer = getCustomer(request, response);
        if (customer == null) return;

        try {
            String orderIdStr = request.getParameter("orderId");
            if (orderIdStr == null || orderIdStr.trim().isEmpty()) {
                redirectError(request, response, "Vui lòng chọn đơn hàng cần ký.");
                return;
            }

            int orderId = Integer.parseInt(orderIdStr.trim());

            Part privateKeyPart = request.getPart("privateKeyFile");
            if (privateKeyPart == null || privateKeyPart.getSize() == 0) {
                redirectError(request, response, "Vui lòng upload file private key.");
                return;
            }

            Order order = orderDAO.getOrderById(orderId);
            if (order == null) {
                redirectError(request, response, "Không tìm thấy đơn hàng.");
                return;
            }

            if (order.getCustomerId() != customer.getId()) {
                redirectError(request, response, "Bạn không có quyền ký đơn hàng này.");
                return;
            }

            if (order.getSignature() != null && !order.getSignature().trim().isEmpty()) {
                redirectError(request, response, "Đơn hàng này đã được ký.");
                return;
            }

            if (order.getOrderHash() == null || order.getOrderHash().trim().isEmpty()) {
                redirectError(request, response, "Đơn hàng chưa có mã hash.");
                return;
            }

            if (order.getKeyId() == null) {
                redirectError(request, response, "Đơn hàng chưa được gắn khóa công khai.");
                return;
            }

            UserKey userKey = userKeyService.getById(order.getKeyId());
            if (userKey == null) {
                redirectError(request, response, "Không tìm thấy public key của đơn hàng.");
                return;
            }

            if (userKey.getCustomerId() != customer.getId()) {
                redirectError(request, response, "Public key không thuộc tài khoản hiện tại.");
                return;
            }

            if (userKey.getPublicKey() == null || userKey.getPublicKey().trim().isEmpty()) {
                redirectError(request, response, "Public key không hợp lệ.");
                return;
            }

            byte[] privateKeyBytes = readPrivateKeyBytes(privateKeyPart);
            byte[] signatureBytes = SignatureUtil.sign(order.getOrderHash(), privateKeyBytes);

            byte[] publicKeyBytes = Base64.getDecoder().decode(userKey.getPublicKey().trim());

            boolean valid = SignatureUtil.verify(
                    order.getOrderHash(),
                    signatureBytes,
                    publicKeyBytes
            );

            if (!valid) {
                redirectError(request, response, "Private key không khớp với public key của đơn hàng.");
                return;
            }

            String signatureBase64 = Base64.getEncoder().encodeToString(signatureBytes);

            orderDAO.updateSignature(
                    order.getId(),
                    order.getOrderHash(),
                    signatureBase64,
                    order.getKeyId()
            );

            orderDAO.updateVerifyResult(order.getId(), 1);

            redirectSuccess(request, response, "Ký đơn hàng thành công.");

        } catch (NumberFormatException e) {
            redirectError(request, response, "Mã đơn hàng không hợp lệ.");
        } catch (IllegalArgumentException e) {
            redirectError(request, response, "File private key không hợp lệ.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectError(request, response, "Lỗi ký đơn hàng: " + e.getMessage());
        }
    }

    private byte[] readPrivateKeyBytes(Part privateKeyPart) throws Exception {
        byte[] uploadedBytes = privateKeyPart.getInputStream().readAllBytes();

        if (uploadedBytes == null || uploadedBytes.length == 0) {
            throw new IllegalArgumentException("Private key file rỗng");
        }

        String privateKeyText = new String(uploadedBytes, StandardCharsets.UTF_8)
                .trim()
                .replaceAll("\\s+", "");

        try {
            return Base64.getDecoder().decode(privateKeyText);
        } catch (IllegalArgumentException e) {
            return uploadedBytes;
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

    private void redirectSuccess(HttpServletRequest request,
                                 HttpServletResponse response,
                                 String message) throws IOException {
        response.sendRedirect(request.getContextPath()
                + "/signature-tool?success="
                + URLEncoder.encode(message, StandardCharsets.UTF_8));
    }

    private void redirectError(HttpServletRequest request,
                               HttpServletResponse response,
                               String message) throws IOException {
        response.sendRedirect(request.getContextPath()
                + "/signature-tool?error="
                + URLEncoder.encode(message, StandardCharsets.UTF_8));
    }
}