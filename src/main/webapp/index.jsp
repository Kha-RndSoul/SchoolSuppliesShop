<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <title>DPK Shop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-common.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-home.css">
</head>
<body>

<header class="header">
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/" class="logo">
            <span>DPK Shop</span>
        </a>

        <form action="${pageContext.request.contextPath}/products" method="GET" class="search-bar">
            <input type="text" name="keyword" placeholder="Tìm kiếm sản phẩm ">
            <button type="submit" class="search-button">🔍</button>
        </form>

        <div class="header-actions">
            <a href="${pageContext.request.contextPath}/contact.jsp" class="action-item">
                <div class="action-text">
                    <button class="phone-button">📞</button>
                    <span>Liên hệ</span>
                </div>
            </a>
            <a href="${pageContext.request.contextPath}/login.jsp" class="action-item">
                <div class="action-text">
                    <button class="user-button">👤</button>
                    <span>Đăng nhập</span>
                </div>
            </a>
            <a href="${pageContext.request.contextPath}/cart" class="action-item">
                <div class="action-text">
                    <button class="cart-button">🛒</button>
                    <span>Giỏ hàng</span>
                </div>
            </a>
        </div>
    </nav>

    <div class="nav-row">
        <div class="container">
            <ul class="nav-links">
                <c:forEach var="category" items="${listCategory}">
                    <li>
                        <a href="${pageContext.request.contextPath}/products?categoryId=${category.categoryId}">
                                ${category.categoryName}
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</header>

<main>

    <section class="hero-section">
        <div class="slider-container">
            <c:forEach var="banner" items="${listBan}" varStatus="status">
                <div class="slide ${status.first ? 'active' : ''}"
                     style="background-image:linear-gradient(rgba(0,0,0,0.4),rgba(0,0,0,0.4)), url('${pageContext.request.contextPath}${banner.imageUrl}');">
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
                    <span class="slider-dot ${status.first ?  'active' : ''}" onclick="goToSlide(${status.index})"></span>
                </c:forEach>
            </div>
        </div>
    </section>

    <section class="featured-categories">
        <div class="container">
            <h2 class="section-title">Danh Mục Nổi Bật</h2>
            <div class="categories-grid">
                <c:forEach var="category" items="${featuredCategories}">
                    <a href="${pageContext.request.contextPath}/products?categoryId=${category.categoryId}" class="category-card">
                        <img src="${pageContext.request.contextPath}${category.imageUrl}"
                             alt="${category.categoryName}"
                             class="category-image"
                             onerror="this.src='${pageContext.request.contextPath}/assets/images/no-image.png'">
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
                    <a href="${pageContext.request.contextPath}/product-detail?id=${product.product_id}" class="product-card">
                        <div class="product-badge">
                            <span class="sold-count">🔥 Đã bán <fmt:formatNumber value="${product.sold_count}" pattern="#,###"/></span>
                        </div>

                        <img src="${pageContext.request.contextPath}${product.imageUrl}"
                             alt="${product.product_name}"
                             class="product-image"
                             onerror="this.src='${pageContext.request.contextPath}/assets/images/no-image.png'">

                        <div class="product-info">
                            <h3 class="product-name">${product.product_name}</h3>
                            <p class="product-brand">${product.brandName}</p>

                            <div>
                                <c:choose>
                                    <c:when test="${not empty product.sale_price && product.sale_price > 0}">
                                    <span class="product-price">
                                            <fmt:formatNumber value="${product.sale_price}" pattern="#,###"/>đ
                                        </span>
                                        <span class="product-price-old">
                                            <fmt:formatNumber value="${product.price}" pattern="#,###"/>đ
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="product-price">
                                            <fmt:formatNumber value="${product.price}" pattern="#,###"/>đ
                                        </span>
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
                            <img class="promo-icon" src="${pageContext.request.contextPath}/assets/images/icons/coupon-icon.png"
                                 onerror="this.src='${pageContext.request.contextPath}/assets/images/no-image.png'">
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

                            <button class="btn-primary" onclick="copyCouponCode('${coupon.code}')">
                                🛒 Sử dụng ngay
                            </button>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <a href="${pageContext.request.contextPath}/promotions" class="promo-view-all">Xem tất cả →</a>
        </div>
    </section>

</main>

<footer class="footer">
    <div class="container">
        <div class="footer-content">
            <div class="footer-section">
                <h3>Giới thiệu</h3>
                <p>DPK Shop là cửa hàng chuyên cung cấp đồ dùng học tập chất lượng cao với giá cả hợp lý.</p>
                <a href="${pageContext.request.contextPath}/about.jsp" class="text-link">Về chúng tôi →</a>
            </div>
            <div class="footer-section">
                <h3>Liên Hệ</h3>
                <p>📧 Email: contact@dpkshop.com</p>
                <p>📞 Hotline: 1900 5678</p>
                <p>📍 Địa chỉ: 123 Đường Học Tập, Phường 1, Quận 1, TP.HCM</p>
                <a href="${pageContext.request.contextPath}/contact.jsp" class="text-link">Chi tiết liên hệ →</a>
            </div>
            <div class="footer-section">
                <h3>Hỗ Trợ</h3>
                <a href="${pageContext.request.contextPath}/guide.jsp">Hướng dẫn mua hàng</a>
                <a href="${pageContext.request.contextPath}/return-policy.jsp">Chính sách đổi trả</a>
                <a href="${pageContext.request.contextPath}/shipping.jsp">Vận chuyển</a>
                <a href="${pageContext.request.contextPath}/payment.jsp">Thanh toán</a>
            </div>
            <div class="footer-section">
                <h3>Theo Dõi</h3>
                <a href="#">Facebook</a>
                <a href="#">Instagram</a>
                <a href="#">Twitter</a>
                <a href="#">YouTube</a>
            </div>
        </div>
        <div class="footer-bottom">
            <p>&copy; 2025 DPK Shop.Tất cả bản quyền được bảo lưu.</p>
        </div>
    </div>
</footer>

<script src="${pageContext.request.contextPath}/assets/js/home.js"></script>
<script>
    function copyCouponCode(code) {
        navigator.clipboard.writeText(code).then(() => {
            alert('Đã copy mã:  ' + code);
        }).catch(err => {
            console.error('Failed to copy:', err);
        });
    }
</script>
</body>
</html>

