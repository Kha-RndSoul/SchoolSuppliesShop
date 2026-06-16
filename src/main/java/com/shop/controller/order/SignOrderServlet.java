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

        request.setCharacterEncoding("UTF-8");
        Customer customer = getCustomer(request, response);
        if (customer == null) {
            sendAjaxError(response, "Vui lòng đăng nhập để thực hiện ký đơn hàng.");
            return;
        }

        String orderIdStr = request.getParameter("orderId");
        String signatureBase64 = request.getParameter("signatureStr");

        try {
            if (isBlank(orderIdStr)) {
                sendAjaxError(response, "Vui lòng chọn đơn hàng cần ký.");
                return;
            }

            if (isBlank(signatureBase64)) {
                sendAjaxError(response, "Vui lòng dán chữ ký được tạo từ tool ký offline.");
                return;
            }

            int orderId = Integer.parseInt(orderIdStr.trim());

            Order order = orderDAO.getOrderById(orderId);
            if (order == null) {
                sendAjaxError(response, "Không tìm thấy đơn hàng.");
                return;
            }

            if (order.getCustomerId() != customer.getId()) {
                sendAjaxError(response, "Bạn không có quyền ký đơn hàng này.");
                return;
            }

            if (!"PENDING".equalsIgnoreCase(order.getOrderStatus())) {
                sendAjaxError(response, "Chỉ có thể ký đơn hàng đang chờ xử lý.");
                return;
            }

            if (!isBlank(order.getSignature())) {
                sendAjaxError(response, "Đơn hàng này đã được ký.");
                return;
            }

            if (isBlank(order.getOrderHash())) {
                sendAjaxError(response, "Đơn hàng chưa có mã xác thực.");
                return;
            }

            if (order.getKeyId() == null) {
                sendAjaxError(response, "Đơn hàng chưa được gắn khóa công khai.");
                return;
            }

            UserKey userKey = userKeyService.getById(order.getKeyId());
            if (userKey == null) {
                sendAjaxError(response, "Không tìm thấy public key của đơn hàng.");
                return;
            }

            if (userKey.getCustomerId() != customer.getId()) {
                sendAjaxError(response, "Public key không thuộc tài khoản hiện tại.");
                return;
            }

            if (userKey.getReportedLostAt() != null) {
                sendAjaxError(response, "Khóa này đã bị báo mất hoặc thu hồi. Không thể dùng để ký đơn hàng.");
                return;
            }

            if (isBlank(userKey.getPublicKey())) {
                sendAjaxError(response, "Public key không hợp lệ.");
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
                sendAjaxError(response, "Chữ ký không đúng định dạng Base64.");
                return;
            }

            boolean valid = SignatureUtil.verify(
                    order.getOrderHash(),
                    signatureBytes,
                    publicKeyBytes
            );

            if (!valid) {
                sendAjaxError(response, "Chữ ký không hợp lệ. Vui lòng kiểm tra lại cặp khóa hoặc mã xác thực đơn hàng.");
                return;
            }

            orderDAO.updateSignature(
                    order.getId(),
                    order.getOrderHash(),
                    cleanSignatureBase64,
                    order.getKeyId()
            );

            orderDAO.updateVerifyResult(order.getId(), 1);

            response.setStatus(HttpServletResponse.SC_OK);

        } catch (NumberFormatException e) {
            sendAjaxError(response, "Mã đơn hàng không hợp lệ.");
        } catch (Exception e) {
            e.printStackTrace();
            sendAjaxError(response, "Lỗi xác minh chữ ký: " + e.getMessage());
        }
    }

    private Customer getCustomer(HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("customer") == null) {
            return null;
        }
        return (Customer) session.getAttribute("customer");
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void sendAjaxError(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write(message);
    }
}