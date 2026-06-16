package com.shop.controller.order;

import com.shop.dao.order.OrderDAO;
import com.shop.model.Customer;
import com.shop.model.Order;
import com.shop.model.UserKey;
import com.shop.services.UserKeyService;
import com.shop.util.SignatureUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@WebServlet(name = "SignOrderServlet", urlPatterns = {"/sign-order"})
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
        if (customer == null) {
            return;
        }

        String orderIdStr = request.getParameter("orderId");
        String signatureBase64 = request.getParameter("signatureBase64");

        try {
            if (isBlank(orderIdStr)) {
                redirectError(request, response, "Vui lòng chọn đơn hàng cần ký.");
                return;
            }

            if (isBlank(signatureBase64)) {
                redirectError(request, response, "Vui lòng dán chữ ký được tạo từ tool ký offline.");
                return;
            }

            int orderId = Integer.parseInt(orderIdStr.trim());

            Order order = orderDAO.getOrderById(orderId);
            if (order == null) {
                redirectError(request, response, "Không tìm thấy đơn hàng.");
                return;
            }

            if (order.getCustomerId() != customer.getId()) {
                redirectError(request, response, "Bạn không có quyền ký đơn hàng này.");
                return;
            }

            if (!isBlank(order.getSignature())) {
                redirectError(request, response, "Đơn hàng này đã được ký.");
                return;
            }

            if (isBlank(order.getOrderHash())) {
                redirectError(request, response, "Đơn hàng chưa có mã xác thực.");
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

            if (userKey.getReportedLostAt() != null) {
                redirectError(request, response,
                        "Khóa này đã bị báo mất/thu hồi. Không thể dùng để ký đơn hàng. Vui lòng đăng ký khóa mới và tạo lại đơn hàng nếu cần.");
                return;
            }

            if (isBlank(userKey.getPublicKey())) {
                redirectError(request, response, "Public key không hợp lệ.");
                return;
            }

            String cleanSignatureBase64 = signatureBase64
                    .trim()
                    .replaceAll("\\s+", "");

            byte[] signatureBytes;
            byte[] publicKeyBytes;

            try {
                signatureBytes = Base64.getDecoder().decode(cleanSignatureBase64);
                publicKeyBytes = Base64.getDecoder().decode(
                        userKey.getPublicKey().trim().replaceAll("\\s+", "")
                );
            } catch (IllegalArgumentException e) {
                redirectError(request, response, "Chữ ký không đúng định dạng Base64.");
                return;
            }

            boolean valid = SignatureUtil.verify(
                    order.getOrderHash(),
                    signatureBytes,
                    publicKeyBytes
            );

            if (!valid) {
                redirectError(request, response,
                        "Chữ ký không hợp lệ. Vui lòng kiểm tra lại private key hoặc mã xác thực đơn hàng.");
                return;
            }

            orderDAO.updateSignature(
                    order.getId(),
                    order.getOrderHash(),
                    cleanSignatureBase64,
                    order.getKeyId()
            );

            orderDAO.updateVerifyResult(order.getId(), 1);

            redirectSuccess(request, response, "Xác minh chữ ký thành công.");

        } catch (NumberFormatException e) {
            redirectError(request, response, "Mã đơn hàng không hợp lệ.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectError(request, response, "Lỗi xác minh chữ ký: " + e.getMessage());
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

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void redirectSuccess(HttpServletRequest request,
                                 HttpServletResponse response,
                                 String message) throws IOException {
        response.sendRedirect(request.getContextPath()
                + "/signature-tool?success="
                + URLEncoder.encode(message, StandardCharsets.UTF_8)
                + "#sign-section");
    }

    private void redirectError(HttpServletRequest request,
                               HttpServletResponse response,
                               String message) throws IOException {
        response.sendRedirect(request.getContextPath()
                + "/signature-tool?error="
                + URLEncoder.encode(message, StandardCharsets.UTF_8)
                + "#sign-section");
    }
}