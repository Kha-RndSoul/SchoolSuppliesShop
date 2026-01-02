<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${product.product_name} - DPK Shop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-common.css">
    <link rel="stylesheet" href="${pageContext. request.contextPath}/assets/css/style-product-detail.css">
</head>
<body>
<header class="header">
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/home" class="logo">
            <span>DPK Shop</span>
        </a>
        <form action="${pageContext.request. contextPath}/products" method="GET" class="search-bar">
            <input type="text" name="keyword" placeholder="Tìm kiếm sản phẩm...">
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
</header>

<div class="container product-detail">
    <!-- Breadcrumb -->
    <div class="breadcrumb">
        <a href="${pageContext.request.contextPath}/home">Trang chủ</a> ›
        <a href="${pageContext.request.contextPath}/products">Sản phẩm</a> ›
        <span>${product.product_name}</span>
    </div>

    <!-- Product Detail Section -->
    <section class="product-section">
        <div class="detail-container">
            <div class="detail-left">
                <c:choose>
                    <c:when test="${not empty productImages && productImages.size() > 0}">
                        <img id="mainImage" src="${pageContext.request.contextPath}${productImages[0].imageUrl}"
                             alt="${product.product_name}" class="detail-image">

                        <!-- Thumbnail Gallery -->
                        <c:if test="${productImages.size() > 1}">
                            <div class="image-thumbnails">
                                <c:forEach var="img" items="${productImages}" varStatus="status">
                                    <img src="${pageContext.request. contextPath}${img.imageUrl}"
                                         alt="Ảnh ${status.index + 1}"
                                         class="thumbnail ${status.first ? 'active' : ''}"
                                         onclick="changeImage('${pageContext.request.contextPath}${img.imageUrl}', this)">
                                </c:forEach>
                            </div>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}${product.imageUrl}"
                             alt="${product.product_name}" class="detail-image"
                             onerror="this.src='${pageContext.request.contextPath}/assets/images/no-image.png'">
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="detail-right">
                <h1 class="detail-title">${product.product_name}</h1>

                <div class="detail-brand">Thương hiệu: <strong>${product.brandName}</strong></div>

                <div class="detail-rating">
                    <span class="stars">
                        <c:forEach begin="1" end="5" var="i">
                            <c:choose>
                                <c:when test="${i <= averageRating}">★</c:when>
                                <c:otherwise>☆</c:otherwise>
                        </c:choose>
                        </c:forEach>
                    </span>
                    <span class="rating-num"><fmt:formatNumber value="${averageRating}" pattern="#.#"/></span>
                    <span class="divider">|</span>
                    <span class="sold-count">Đã bán <fmt:formatNumber value="${product.sold_count}" pattern="#,###"/></span>
                </div>

                <div class="detail-price-section">
                    <c:choose>
                        <c:when test="${not empty product. sale_price && product.sale_price > 0}">
                            <div class="detail-price"><fmt:formatNumber value="${product. sale_price}" pattern="#,###"/>₫</div>
                            <div class="detail-old-price"><fmt:formatNumber value="${product.price}" pattern="#,###"/>₫</div>
                            <div class="detail-discount">-${discountPercent}%</div>
                        </c:when>
                        <c:otherwise>
                            <div class="detail-price"><fmt:formatNumber value="${product.price}" pattern="#,###"/>₫</div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="detail-description">
                    <h3>Mô tả sản phẩm</h3>
                    <p>${product.description}</p>
                </div>

                <div class="detail-stock">
                    <c:choose>
                        <c:when test="${product.stock_quantity > 0}">
                            <span class="stock-available">✓ Còn hàng (${product.stock_quantity} sản phẩm)</span>
                        </c:when>
                        <c:otherwise>
                            <span class="stock-out">✗ Hết hàng</span>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="detail-actions">
                    <div class="quantity-selector">
                        <span class="qty-label">Số lượng:</span>
                        <button onclick="decreaseQty()">-</button>
                        <input type="number" id="quantity" value="1" min="1" max="${product.stock_quantity}">
                        <button onclick="increaseQty()">+</button>
                    </div>
                    <button class="btn-add-to-cart" onclick="addToCart(${product. id})">🛒 Thêm vào giỏ hàng</button>
                    <button class="btn-buy-now" onclick="buyNow(${product.id})">Mua ngay</button>
                </div>
            </div>
        </div>

        <!-- Reviews Section -->
        <c:if test="${not empty reviews}">
            <div class="reviews-section">
                <h2>Đánh giá sản phẩm (${reviewCount})</h2>
                <div class="reviews-list">
                    <c:forEach var="review" items="${reviews}">
                        <div class="review-item">
                            <div class="review-header">
                                <span class="review-author">👤 Khách hàng</span>
                                <span class="review-stars">
                                    <c:forEach begin="1" end="5" var="i">
                                        <c:choose>
                                            <c:when test="${i <= review.rating}">★</c:when>
                                            <c:otherwise>☆</c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </span>
                                <span class="review-date"><fmt:formatDate value="${review. createdAt}" pattern="dd/MM/yyyy"/></span>
                            </div>
                            <p class="review-comment">${review.comment}</p>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:if>

        <!-- Related Products -->
        <c:if test="${not empty relatedProducts}">
        <div class="related-products">
            <h2>Sản phẩm liên quan</h2>
            <div class="products-grid">
                <c:forEach var="related" items="${relatedProducts}">
                    <a href="${pageContext.request.contextPath}/product-detail? id=${related.id}" class="product-card">
                        <img src="${pageContext.request.contextPath}${related.imageUrl}"
                             alt="${related.product_name}" class="product-image">
                        <div class="product-info">
                            <h3 class="product-name">${related.product_name}</h3>
                            <div class="product-price">
                                <c:choose>
                                    <c:when test="${not empty related.sale_price && related.sale_price > 0}">
                                    <fmt:formatNumber value="${related.sale_price}" pattern="#,###"/>đ
                                    </c:when>
                                    <c:otherwise>
                                    <fmt:formatNumber value="${related. price}" pattern="#,###"/>đ
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </a>
                </c:forEach>
            </div>
        </div>
        </c:if>
    </section>
</div>

<footer class="footer">
    <div class="container">
        <div class="footer-content">
            <div class="footer-section">
                <h3>Giới thiệu</h3>
                <p>DPK Shop là cửa hàng chuyên cung cấp đồ dùng học tập chất lượng cao. </p>
            </div>
            <div class="footer-section">
                <h3>Liên Hệ</h3>
                <p>📧 Email: contact@dpkshop.com</p>
                <p>📞 Hotline: 1900 5678</p>
            </div>
        </div>
        <div class="footer-bottom">
            <p>&copy; 2025 DPK Shop. Tất cả bản quyền được bảo lưu.</p>
        </div>
    </div>
</footer>

<script>
    function changeImage(src, thumb) {
        document.getElementById('mainImage').src = src;
        document.querySelectorAll('.thumbnail').forEach(t => t.classList.remove('active'));
        thumb.classList.add('active');
    }

    function decreaseQty() {
        const input = document.getElementById('quantity');
        if (input.value > 1) input.value = parseInt(input.value) - 1;
    }

    function increaseQty() {
        const input = document.getElementById('quantity');
        const max = parseInt(input.max);
        if (parseInt(input.value) < max) input.value = parseInt(input. value) + 1;
    }

    function addToCart(productId) {
        const quantity = document.getElementById('quantity').value;
        // TODO: Implement add to cart
        alert('Thêm ' + quantity + ' sản phẩm vào giỏ hàng');
    }

    function buyNow(productId) {
        const quantity = document.getElementById('quantity').value;
        // TODO:  Implement buy now
        window.location.href = '${pageContext.request.contextPath}/checkout?productId=' + productId + '&quantity=' + quantity;
    }
</script>
</body>
</html>