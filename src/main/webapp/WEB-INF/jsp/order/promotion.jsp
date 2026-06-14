<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <title>Khuyến mãi - DPK Shop</title>

    <!-- CSS chung -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-common.css"/>

    <!-- style bạn đã có và thích -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-promotion.css"/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp"/>

<main class="container">
    <div class="promotion-hero" role="banner" aria-label="Khuyến mãi nổi bật">
        <h1>Chương Trình Khuyến Mãi</h1>
        <p>Những mã ưu đãi tốt nhất cho đơn hàng của bạn</p>
    </div>

    <c:choose>
        <c:when test="${empty promotions}">
            <p>Hiện chưa có chương trình khuyến mãi nào.</p>
        </c:when>
        <c:otherwise>
            <section class="promotions-grid" aria-label="Danh sách khuyến mãi">
                <c:forEach var="p" items="${promotions}">
                    <article class="promotion-card" role="article">
                        <c:choose>
                            <c:when test="${not empty p.imageUrl}">
                                <img class="promotion-image" src="${pageContext.request.contextPath}${p.imageUrl}"
                                     alt="Hình khuyến mãi ${p.couponCode}" onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/assets/images/default-promo.jpg'"/>
                            </c:when>
                            <c:otherwise>
                                <img class="promotion-image" src="${pageContext.request.contextPath}/assets/images/default-promo.jpg"
                                     alt="Hình khuyến mãi"/>
                            </c:otherwise>
                        </c:choose>

                        <div class="promotion-content">
                            <c:set var="type" value="${fn:trim(p.discountType)}" />
                            <c:choose>
                                <c:when test="${type == 'PERCENT' || type == 'PERCENTAGE'}">
                                    <span class="promotion-badge">Giảm <fmt:formatNumber value="${p.discountValue}" maxFractionDigits="0"/>%</span>
                                </c:when>
                                <c:when test="${type == 'AMOUNT' || type == 'FIXED_AMOUNT'}">
                                    <span class="promotion-badge">Giảm <fmt:formatNumber value="${p.discountValue}" maxFractionDigits="0"/>₫</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="promotion-badge">Khuyến mãi (${p.discountType})</span>
                                </c:otherwise>
                            </c:choose>

                            <h3 class="promotion-title">Mã: ${p.couponCode}</h3>

                            <p class="promotion-description">
                                Đơn tối thiểu: <strong><fmt:formatNumber value="${p.minOrderAmount}" maxFractionDigits="0"/>₫</strong>
                                <br/>
                                Đã dùng: <strong>${p.usedCount}</strong>
                            </p>

                            <div class="promotion-details">
                                <div class="promotion-expiry" style="width: 100%; text-align: left;">
                                    Hạn dùng:
                                    <strong><fmt:formatDate value="${p.startDate}" pattern="dd/MM/yyyy"/></strong>
                                    -
                                    <strong><fmt:formatDate value="${p.endDate}" pattern="dd/MM/yyyy"/></strong>
                                </div>
                            </div>
                        </div>
                    </article>
                </c:forEach>
            </section>
        </c:otherwise>
    </c:choose>
</main>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
</body>
</html>