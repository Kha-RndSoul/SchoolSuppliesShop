<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Xác nhận đơn hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/order-confirmation.css">
</head>
<body>

<!--Header -->
<jsp: include page="header.jsp"/>

<main class="wrap">
    <c:choose>
        <c:when test="${not empty order}">
            <div id="root" class="card">
                <h2>🎉 Đặt hàng thành công!</h2>
                <div class="order-info">
                    <p><strong>Mã đơn hàng:</strong> ${order.orderCode}</p>
                    <p><strong>Người nhận:</strong> ${order.shippingName}</p>
                    <p><strong>Số điện thoại:</strong> ${order.shippingPhone}</p>
                    <p><strong>Địa chỉ:</strong> ${order.shippingAddress}</p>
                    <p><strong>Phương thức thanh toán:</strong>
                        <c:choose>
                            <c:when test="${order.paymentMethod == 'cod'}">Thanh toán khi nhận hàng (COD)</c:when>
                            <c:otherwise>Chuyển khoản / Online</c:otherwise>
                        </c:choose>
                    </p>
                    <p><strong>Trạng thái:</strong> ${order.orderStatus}</p>
                    <p class="total"><strong>Tổng tiền:</strong>
                        <fmt:formatNumber value="${order.totalAmount}" type="number" groupingUsed="true"/>₫
                    </p>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div id="root" class="card">
                <p>Không tìm thấy thông tin đơn hàng.</p>
            </div>
        </c:otherwise>
    </c:choose>

    <div class="actions">
        <button id="printBtn" class="btn" onclick="window.print()">In hóa đơn</button>
        <a href="${pageContext.request.contextPath}/index" class="btn link">Về trang chủ</a>
    </div>
</main>

<!--Footer -->
<jsp: include page="footer.jsp"/>

<script src="${pageContext.request.contextPath}/assets/js/order-confirmation.js"></script>
</body>
</html>
