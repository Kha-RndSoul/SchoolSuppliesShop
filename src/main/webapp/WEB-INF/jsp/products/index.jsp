<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <title>DPK Shop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-common.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-home.css">
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/header.jsp"/>

<main>
    <section class="hero-section">
        <div class="slider-container">
            <c:forEach var="banner" items="${listBan}" varStatus="status">
                <div class="slide ${status.first ? 'active' : ''}"
                     style="background-image:linear-gradient(rgba(0,0,0,0.4),rgba(0,0,0,0.4)),url('${pageContext.request.contextPath}${banner.imageUrl}');">
                    <div class="slide-content">
                        <h1>${banner.title}</h1>
                        <a href="${pageContext.request.contextPath}/products" class="btn-primary">Mua Ngay</a>
                    </div>
                </div>
            </c:forEach>
            <button class="slider-arrow prev" onclick="changeSlide(-1)">‹</button>
            <button class="slider-arrow next" onclick="changeSlide(1)">›</button>
            <div class="slider-controls">
                <c:forEach var="banner" items="${listBan}" varStatus="status">
                    <span class="slider-dot ${status.first ? 'active' : ''}" onclick="goToSlide(${status.index})"></span>
                </c:forEach>
            </div>
        </div>
    </section>

    <section class="featured-categories">
        <div class="container">
            <h2 class="section-title">Danh Mục Nổi Bật</h2>
            <div class="categories-grid">
                <c:forEach var="category" items="${featuredCategories}">
                    <a href="${pageContext.request.contextPath}/products?categoryId=${category.id}" class="category-card">
                       <%--
                        <img src="${pageContext.request.contextPath}${category.imageUrl}"
                             alt="${category.categoryName}"
                             class="category-image"
                             onerror="this.onerror=null; this.src='data:image/svg+xml,%3Csvg xmlns=%22http://www.w3.org/2000/svg%22 width=%22300%22 height=%22300%22%3E%3Crect fill=%22%23f5f5f5%22 width=%22300%22 height=%22300%22/%3E%3Ctext x=%2250%25%22 y=%2250%25%22 dominant-baseline=%22middle%22 text-anchor=%22middle%22 font-family=%22Arial%22 font-size=%2218%22 fill=%22%23999%22%3E${category.categoryName}%3C/text%3E%3C/svg%3E'">
                        --%>
                        <div class="category-info">
                            <h3>${category.categoryName}</h3>
                        </div>
                    </a>
                </c:forEach>
            </div>
        </div>
    </section>

    <section class="products-section">
        <div class="container">
            <h2 class="section-title">🔥 Sản Phẩm Bán Chạy</h2>
            <div class="products-grid">
                <c:forEach var="product" items="${bestSellingProducts}">
                    <a href="${pageContext.request.contextPath}/product-detail?id=${product.id}" class="product-card">
                        <div class="product-badge">
                            <span class="sold-count">🔥 Đã bán <fmt:formatNumber value="${product.soldCount}" pattern="#,###"/></span>
                        </div>
                    <%--
                    <img src="${pageContext.request.contextPath}${product.imageUrl}"
                         alt="${product.productName}"
                         class="product-image"
                         onerror="this.onerror=null; this.src='data:image/svg+xml,%3Csvg xmlns=%22http://www.w3.org/2000/svg%22 width=%22300%22 height=%22300%22%3E%3Crect fill=%22%23f5f5f5%22 width=%22300%22 height=%22300%22/%3E%3Ctext x=%2250%25%22 y=%2250%25%22 dominant-baseline=%22middle%22 text-anchor=%22middle%22 font-family=%22Arial%22 font-size=%2216%22 fill=%22%23999%22%3EKh%C3%B4ng c%C3%B3 %E1%BA%A3nh%3C/text%3E%3C/svg%3E'">
                    --%>
                    <div class="product-info">
                        <h3 class="product-name">${product.productName}</h3>
                        <p class="product-brand">${product.brandName}</p>
                        <div>
                            <c:choose>
                                <c:when test="${not empty product.salePrice && product.salePrice > 0}">
                                    <span class="product-price"><fmt:formatNumber value="${product.salePrice}" pattern="#,###"/>đ</span>
                                    <span class="product-price-old"><fmt:formatNumber value="${product.price}" pattern="#,###"/>đ</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="product-price"><fmt:formatNumber value="${product.price}" pattern="#,###"/>đ</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="product-rating">
                        <span class="stars">
                            <c:forEach begin="1" end="5" var="i">
                                <c:choose>
                                    <c:when test="${i <= product.averageRating}">★</c:when>
                                    <c:otherwise>☆</c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </span>
                            <span>(<fmt:formatNumber value="${product.averageRating}" pattern="#.#"/>)</span>
                        </div>
                    </div>
                </a>
            </c:forEach>
        </div>
    </div>
</section>

<section class="featured-promotions">
    <div class="container">
        <div class="promotions-header">
            <h2 class="section-title">🎁 Mã Giảm Giá Hot Nhất</h2>
        </div>
        <div class="categories-grid">
            <c:forEach var="coupon" items="${topCoupons}">
                <div class="category-card">
                    <div class="promo-visual">
                <%--
                 <img class="promo-icon"
                      src="${pageContext.request.contextPath}/assets/images/icons/coupon-icon.png"
                      onerror="this.onerror=null; this.src='data:image/svg+xml,%3Csvg xmlns=%22http://www.w3.org/2000/svg%22 width=%22100%22 height=%22100%22%3E%3Crect fill=%22%23ff6b6b%22 width=%22100%22 height=%22100%22 rx=%2210%22/%3E%3Ctext x=%2250%25%22 y=%2250%25%22 dominant-baseline=%22middle%22 text-anchor=%22middle%22 font-size=%2240%22%3E%F0%9F%8E%81%3C/text%3E%3C/svg%3E'">
                --%>
             </div>
             <div class="category-info">
                 <h3>${coupon.code}</h3>
                 <p>${coupon.description}</p>
                 <c:choose>
                     <c:when test="${coupon.discountType == 'PERCENT'}">
                         <p class="promo-detail">Giảm ${coupon.discountValue}%</p>
                     </c:when>
                     <c:otherwise>
                         <p class="promo-detail">Giảm <fmt:formatNumber value="${coupon.discountValue}" pattern="#,###"/>đ</p>
                     </c:otherwise>
                 </c:choose>
                 <p class="promo-usage">👥 <fmt:formatNumber value="${coupon.usedCount}" pattern="#,###"/> đã dùng</p>
                 <button class="btn-primary" onclick="copyCouponCode('${coupon.code}')">🛒 Sử dụng ngay</button>
             </div>
         </div>
     </c:forEach>
 </div>
 <a href="${pageContext.request.contextPath}/promotions" class="promo-view-all">Xem tất cả →</a>
</div>
</section>
</main>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>

<script src="${pageContext.request.contextPath}/assets/js/home.js"></script>
<script>
function copyCouponCode(code){navigator.clipboard.writeText(code).then(()=>{alert('Đã copy mã: '+code)}).catch(err=>{console.error('Failed to copy: ',err)})}
</script>
</body>
</html>