<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <title>Thanh toán</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/checkout.css">
</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp"/>

<main class="container" id="main" data-context="${pageContext.request.contextPath}">
    <div class="checkout-wrapper">
        <form id="checkoutForm" action="${pageContext.request.contextPath}/checkout" method="POST" novalidate>

            <section class="checkout-section">
                <h2>Thông tin khách hàng</h2>

                <div class="form-group">
                    <label for="name">Tên <span class="required">*</span></label>
                    <input id="name" name="name" type="text" required placeholder="Họ và tên"
                           value="${fn:escapeXml((not empty requestScope.currentUser) ? requestScope.currentUser.fullName : '')}">
                </div>

                <div class="form-group">
                    <label for="phone">Số điện thoại <span class="required">*</span></label>
                    <input id="phone" name="phone" type="tel" required placeholder="0912345678"
                           value="${fn:escapeXml((not empty requestScope.currentUser) ? requestScope.currentUser.phone : '')}">
                </div>

                <div class="form-group">
                    <label for="email">Email <span class="optional">(tùy chọn)</span></label>
                    <input id="email" name="email" type="email" placeholder="you@example.com"
                           value="${fn:escapeXml((not empty requestScope.currentUser) ? requestScope.currentUser.email : '')}">
                </div>

                <div class="form-group">
                    <label for="address">Địa chỉ giao hàng <span class="required">*</span></label>
                    <textarea id="address" name="address" required placeholder="Số nhà, Đường, Phường, Quận">${fn:escapeXml((not empty requestScope.currentUser) ? requestScope.currentUser.address : '')}</textarea>
                </div>
            </section>

            <section class="checkout-section">
                <h3>Vận chuyển</h3>
                <div class="radio-group">
                    <label class="radio-option">
                        <input type="radio" name="shipping" value="standard" data-fee="25000" checked>
                        <span>Bình thường (25.000₫)</span>
                    </label>
                    <label class="radio-option">
                        <input type="radio" name="shipping" value="express" data-fee="50000">
                        <span>Hỏa tốc (50.000₫)</span>
                    </label>
                </div>
            </section>

            <section class="checkout-section">
                <h3>Phương thức thanh toán</h3>
                <div class="radio-group">
                    <label class="radio-option">
                        <input type="radio" name="payment" value="cod" checked>
                        <span>Thanh toán khi nhận hàng (COD)</span>
                    </label>
                    <label class="radio-option">
                        <input type="radio" name="payment" value="online">
                        <span>Chuyển khoản / Online</span>
                    </label>
                </div>
                <div class="payment-note" style="display:none; margin-top:12px; color:#666;"></div>
            </section>

            <c:set var="effectiveCartTotal" value="${not empty cartTotal ? cartTotal : (not empty sessionScope.cartTotal ? sessionScope.cartTotal : 0)}" />
            <input type="hidden" id="cartTotalHidden" name="cartTotalHidden" value="${effectiveCartTotal}" />

            <!-- Coupon: nhập mã giảm giá -->
            <section class="checkout-section">
                <h3>Mã giảm giá</h3>

                <p class="coupon-desc">
                    Nhập mã giảm giá của bạn.
                    <a href="${pageContext.request.contextPath}/promotions" target="_blank">
                        Xem danh sách mã khuyến mãi
                    </a>
                </p>

                <div class="coupon-input-wrapper">
                    <input type="text" id="couponInput"
                            name="couponCode"
                            placeholder="Nhập mã giảm giá (VD: RAUMA36)"
                            maxlength="50"
                            autocomplete="off"
                    />
                    <button type="button" id="btnApplyCoupon" class="btn-apply-coupon">
                        Áp dụng
                    </button>
                </div>
                <div id="couponMessage" class="coupon-message"></div>
                <input type="hidden" id="couponCodeHidden" name="couponCode" value="${param.couponCode}">
            </section>

            <section class="checkout-section">
                <h3>Tổng tiền</h3>
                <div class="totals">
                    <div class="total-row">
                        <span>Phí hàng:</span>
                        <span id="subtotal" data-value="${effectiveCartTotal}">
                            <fmt:formatNumber value="${effectiveCartTotal}" type="number" groupingUsed="true"/>₫
                        </span>
                    </div>
                    <div class="total-row">
                        <span>Phí vận chuyển:</span>
                        <span id="shippingFee" data-value="25000">25.000₫</span>
                    </div>

                    <!-- Discount row (hidden initially) -->
                    <div id="discountRow" class="total-discount" style="display:none;">
                        <span>Giảm giá (<span id="appliedCouponCode"></span>):</span>
                        <span id="discountAmount" data-value="0">-0₫</span>
                    </div>

                    <div class="total-row total-final">
                        <span>Tổng:</span>
                        <strong id="total">
                            <fmt:formatNumber value="${effectiveCartTotal + 25000}" type="number" groupingUsed="true"/>₫
                        </strong>
                    </div>
                </div>
            </section>

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

<jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
<script>
    const CONTEXT_PATH = '${pageContext.request.contextPath}';
</script>

<script src="${pageContext.request.contextPath}/assets/js/checkout.js"></script>
</body>
</html>
