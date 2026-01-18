<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <title>Thông tin khách hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/checkout.css">
</head>
<body>
<!--Header -->
<jsp:include page="/WEB-INF/jsp/common/header.jsp"/>

<main class="container">
    <div class="checkout-card">
        <form id="checkoutForm" action="${pageContext.request.contextPath}/checkout" method="POST" novalidate>
            <h2>Thông tin khách hàng</h2>

            <label class="field">Tên
                <input id="name" name="name" required placeholder="Họ và tên" value="${sessionScope.user.name}">
            </label>

            <label class="field">Số điện thoại
                <input id="phone" name="phone" required placeholder="0912345678" value="${sessionScope.user.phone}">
            </label>

            <label class="field">Email (tuỳ chọn)
                <input id="email" name="email" type="email" placeholder="you@example.com" value="${sessionScope.user.email}">
            </label>

            <label class="field">Địa chỉ giao hàng
                <textarea id="address" name="address" required placeholder="Số nhà, đường, phường, quận">${sessionScope.user.address}</textarea>
            </label>

            <h3>Vận chuyển</h3>
            <label class="radio-inline">
                <input type="radio" name="shipping" value="standard" checked> Bình thường (25.000₫)
            </label>
            <label class="radio-inline">
                <input type="radio" name="shipping" value="express"> Hỏa tốc (50.000₫)
            </label>

            <h3>Phương thức thanh toán</h3>
            <label>
                <input type="radio" name="payment" value="cod" checked> Thanh toán khi nhận hàng (COD)
            </label>
            <label>
                <input type="radio" name="payment" value="online"> Chuyển khoản / Online
            </label>

            <div class="totals">
                <div>Phí hàng: <span id="subtotal">
                    <fmt:formatNumber value="${cartTotal}" type="number" groupingUsed="true"/>₫
                </span></div>
                <div>Phí vận chuyển: <span id="shippingFee">25.000₫</span></div>
                <div class="total-line">Tổng:
                    <strong id="total">
                        <fmt:formatNumber value="${cartTotal + 25000}" type="number" groupingUsed="true"/>₫
                    </strong>
                </div>
            </div>

            <div id="checkoutError" role="alert" class="form-error" aria-live="polite">
                <c:if test="${not empty errorMessage}">
                    ${errorMessage}
                </c:if>
            </div>

            <div class="form-actions">
                <button id="placeOrderBtn" type="submit" class="btn-primary">Đặt hàng</button>
            </div>
        </form>
    </div>
</main>

<!--Footer -->
<jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>

<script src="${pageContext.request.contextPath}/assets/js/checkout.js"></script>
</body>
</html>
