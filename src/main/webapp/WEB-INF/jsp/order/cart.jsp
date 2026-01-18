<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <title>Giỏ hàng - DPK Shop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/cart.css">
</head>
<body>

<!--Header -->
<jsp:include page="/WEB-INF/jsp/common/header.jsp"/>

<main class="container cart-page" id="main">
    <h1 class="section-title">Giỏ hàng của bạn</h1>

    <!-- Khi giỏ rỗng -->
    <c:if test="${empty cart}">
        <div id="emptyBlock" class="cart-empty" role="status" aria-live="polite">
            <p>Giỏ hàng của bạn đang trống.</p>
            <a class="btn-primary" href="${pageContext.request.contextPath}/products">Tiếp tục mua hàng</a>
        </div>
    </c:if>

    <!-- Khi giỏ có sản phẩm -->
    <c:if test="${not empty cart}">
        <div class="cart-layout">
            <section class="cart-list" id="list" aria-label="Danh sách sản phẩm">
                <c:forEach var="item" items="${cart}">
                    <div class="cart-item">
                        <img src="${pageContext.request.contextPath}${item.imageUrl}"
                             alt="${item.productName}"
                             class="cart-item-image">
                        <div class="cart-item-info">
                            <h3>${item.productName}</h3>
                            <p class="cart-item-price">
                                <c:choose>
                                    <c:when test="${item.salePrice != null && item.salePrice > 0}">
                                        <fmt:formatNumber value="${item.salePrice}" type="number" groupingUsed="true"/>₫
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:formatNumber value="${item.price}" type="number" groupingUsed="true"/>₫
                                    </c:otherwise>
                                </c:choose>
                            </p>
                        </div>
                        <div class="cart-item-quantity">
                            <form action="${pageContext.request.contextPath}/cart" method="POST" style="display:inline;">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="productId" value="${item.id}">
                                <input type="hidden" name="quantity" value="-1">
                                <button type="submit" class="qty-btn">-</button>
                            </form>
                            <span>${item.quantity}</span>
                            <form action="${pageContext.request.contextPath}/cart" method="POST" style="display:inline;">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="productId" value="${item.id}">
                                <input type="hidden" name="quantity" value="1">
                                <button type="submit" class="qty-btn">+</button>
                            </form>
                        </div>
                        <div class="cart-item-total">
                            <c:choose>
                            <c:when test="${item.salePrice != null && item. salePrice > 0}">
                            <fmt:formatNumber value="${item.salePrice * item.quantity}" type="number" groupingUsed="true"/>₫
                            </c:when>
                            <c:otherwise>
                                <fmt:formatNumber value="${item.price * item.quantity}" type="number" groupingUsed="true"/>₫
                            </c:otherwise>
                            </c:choose>
                        </div>
                        <form action="${pageContext.request.contextPath}/cart" method="POST" style="display: inline;">
                            <input type="hidden" name="action" value="remove">
                            <input type="hidden" name="productId" value="${item.id}">
                            <button type="submit" class="cart-item-remove">✕</button>
                        </form>
                    </div>
                </c:forEach>
            </section>

            <aside class="cart-summary" aria-label="Tóm tắt đơn hàng">
                <div class="summary-box">
                    <h2>Tóm tắt</h2>
                    <div class="summary-row">
                        <span>Tạm tính</span>
                        <strong id="subTotal">
                            <fmt:formatNumber value="${cartTotal}" type="number" groupingUsed="true"/>₫
                        </strong>
                    </div>
                    <a href="${pageContext.request.contextPath}/checkout" class="btn-primary">Thanh toán</a>
                </div>
            </aside>
        </div>
    </c:if>
</main>

<!--Footer -->
<jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>

</body>
</html>