<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <title>${product.productName} - DPK Shop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-common.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-product-detail.css"/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp"/>

<main class="product-detail-container">
    <div class="container">
        <nav class="breadcrumb">
            <a href="${pageContext.request.contextPath}/">Trang chủ</a> ›
            <a href="${pageContext.request.contextPath}/products">Sản phẩm</a>
            <c:if test="${not empty product.categoryName}"> › <a href="${pageContext.request.contextPath}/products?categoryId=${product.categoryId}">${product.categoryName}</a></c:if>
            › <span class="current">${product.productName}</span>
        </nav>
    </div>

    <section class="product-main">
        <div class="container">
            <div class="product-layout">
                <div class="product-gallery">
                    <div class="main-image-container">
                        <img id="mainImage" src="${pageContext.request.contextPath}${not empty productImages && productImages.size() > 0 ? productImages[0].imageUrl : product.imageUrl}" alt="${product.productName}" class="main-image" onerror="this.src='${pageContext.request.contextPath}/assets/images/no-image.png'">
                        <c:if test="${not empty discountPercent && discountPercent > 0}">
                            <span class="discount-badge">-${discountPercent}%</span>
                        </c:if>
                    </div>
                    <c:if test="${not empty productImages && productImages.size() > 1}">
                        <div class="thumbnail-gallery">
                            <c:forEach var="image" items="${productImages}" varStatus="status">
                                <img src="${pageContext.request.contextPath}${image.imageUrl}" alt="${product.productName}" class="thumbnail ${status.first ? 'active' : ''}" onclick="changeMainImage('${pageContext.request.contextPath}${image.imageUrl}', this)" onerror="this.src='${pageContext.request.contextPath}/assets/images/no-image.png'">
                            </c:forEach>
                        </div>
                    </c:if>
                </div>

                <div class="product-info-section">
                    <h1 class="product-title">${product.productName}</h1>
                    <c:if test="${not empty product.brandName}">
                        <p class="product-brand">Thương hiệu: <strong>${product.brandName}</strong></p>
                    </c:if>

                    <div class="product-rating-section">
                        <div class="stars-large">
                            <c:forEach begin="1" end="5" var="i">
                                ${i <= averageRating ? '★' : '☆'}
                            </c:forEach>
                        </div>
                        <span class="rating-text"><fmt:formatNumber value="${averageRating}" pattern="#.#"/> / 5.0</span>
                        <span class="review-count">(${reviewCount > 0 ? reviewCount : 'Chưa có'} đánh giá)</span>
                        <span class="sold-info">| 🔥 Đã bán: <strong><fmt:formatNumber value="${product.soldCount}" pattern="#,###"/></strong></span>
                    </div>

                    <div class="price-section">
                        <c:choose>
                            <c:when test="${not empty product.salePrice && product.salePrice > 0}">
                                <span class="current-price"><fmt:formatNumber value="${product.salePrice}" pattern="#,###"/>đ</span>
                                <span class="original-price"><fmt:formatNumber value="${product.price}" pattern="#,###"/>đ</span>
                            </c:when>
                            <c:otherwise>
                                <span class="current-price"><fmt:formatNumber value="${product.price}" pattern="#,###"/>đ</span>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="stock-section">
                        <span class="${product.stockQuantity > 0 ? 'stock-available' : 'stock-out'}">
                            ${product.stockQuantity > 0 ? ' Còn hàng ('.concat(product.stockQuantity).concat(' sản phẩm)') : ' Tạm hết hàng'}
                        </span>
                    </div>

                    <form action="${pageContext.request.contextPath}/cart" method="POST" class="add-to-cart-form">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="productId" value="${product.id}">

                        <div class="quantity-section">
                            <label for="quantity">Số lượng:</label>
                            <div class="quantity-controls">
                                <button type="button" class="qty-btn" onclick="decreaseQuantity()">−</button>
                                <input type="number" id="quantity" name="quantity" value="1" min="1" max="${product.stockQuantity > 0 ? product.stockQuantity : 999}" class="qty-input">                                <button type="button" class="qty-btn" onclick="increaseQuantity(${product.stockQuantity})">+</button>
                            </div>
                        </div>

                        <div class="action-buttons">
                            <c:choose>
                                <c:when test="${product.stockQuantity > 0}">
                                    <button type="submit" class="btn-add-to-cart"> Thêm vào giỏ hàng</button>
                                    <button type="button" class="btn-buy-now" onclick="buyNow()"> Mua ngay</button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="btn-out-of-stock" disabled>Hết hàng</button>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </form>

                    <div class="additional-info">
                        <div class="info-item"><span class="info-icon">🚚</span> Miễn phí vận chuyển cho đơn hàng từ 300.000đ</div>
                        <div class="info-item"><span class="info-icon">↩️</span> Đổi trả trong vòng 7 ngày</div>
                        <div class="info-item"><span class="info-icon">✓</span> Cam kết hàng chính hãng 100%</div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section class="product-details-section">
        <div class="container">
            <div class="tabs-container">
                <div class="tab-headers">
                    <button class="tab-header active" onclick="switchTab('description')">Mô tả sản phẩm</button>
                    <button class="tab-header" onclick="switchTab('reviews')">Đánh giá (${reviewCount})</button>
                    <button class="tab-header" onclick="switchTab('specifications')">Thông tin chi tiết</button>
                </div>

                <div class="tab-contents">
                    <div id="description" class="tab-content active">
                        <h3>Mô tả sản phẩm</h3>
                        <div class="description-content">
                            <p>${not empty product.description ? product.description : 'Chưa có mô tả chi tiết cho sản phẩm này.'}</p>
                        </div>
                    </div>

                    <div id="reviews" class="tab-content">
                        <h3>Đánh giá sản phẩm</h3>
                        <c:choose>
                            <c:when test="${not empty reviews && reviews.size() > 0}">
                                <div class="rating-summary">
                                    <div class="rating-avg">
                                        <span class="avg-number"><fmt:formatNumber value="${averageRating}" pattern="#.#"/></span>
                                        <div class="stars-large">
                                            <c:forEach begin="1" end="5" var="i">
                                                ${i <= averageRating ? '★' : '☆'}
                                            </c:forEach>
                                        </div>
                                        <span class="review-count-text">${reviewCount} đánh giá</span>
                                    </div>
                                </div>
                                <div class="reviews-list">
                                    <c:forEach var="review" items="${reviews}">
                                        <div class="review-item">
                                            <div class="review-header">
                                                <span class="reviewer-name">Khách hàng #${review.customerId}</span>
                                                <span class="review-date"><fmt:formatDate value="${review.createdAt}" pattern="dd/MM/yyyy HH:mm"/></span>
                                            </div>
                                            <div class="review-rating">
                                                <c:forEach begin="1" end="5" var="i">
                                                    ${i <= review.rating ? '★' : '☆'}
                                                </c:forEach>
                                            </div>
                                            <div class="review-comment">${review.comment}</div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="no-reviews">
                                    <p>Chưa có đánh giá nào cho sản phẩm này. Hãy là người đầu tiên đánh giá!</p>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div id="specifications" class="tab-content">
                        <h3>Thông tin chi tiết</h3>
                        <table class="specifications-table">
                            <tr><td class="spec-label">Tên sản phẩm</td><td class="spec-value">${product.productName}</td></tr>
                            <c:if test="${not empty product.brandName}">
                                <tr><td class="spec-label">Thương hiệu</td><td class="spec-value">${product.brandName}</td></tr>
                            </c:if>
                            <c:if test="${not empty product.categoryName}">
                                <tr><td class="spec-label">Danh mục</td><td class="spec-value">${product.categoryName}</td></tr>
                            </c:if>
                            <tr>
                                <td class="spec-label">Giá</td>
                                <td class="spec-value">
                                    <fmt:formatNumber value="${not empty product.salePrice && product.salePrice > 0 ? product.salePrice : product.price}" pattern="#,###"/>đ
                                    <c:if test="${not empty product.salePrice && product.salePrice > 0}">
                                        <span style="text-decoration:line-through;color:#999;margin-left:10px;"><fmt:formatNumber value="${product.price}" pattern="#,###"/>đ</span>
                                    </c:if>
                                </td>
                            </tr>
                            <tr><td class="spec-label">Tình trạng</td><td class="spec-value">${product.stockQuantity > 0 ? 'Còn hàng ('.concat(product.stockQuantity).concat(' sản phẩm)') : 'Tạm hết hàng'}</td></tr>
                            <tr><td class="spec-label">Đã bán</td><td class="spec-value"><fmt:formatNumber value="${product.soldCount}" pattern="#,###"/> sản phẩm</td></tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <c:if test="${not empty relatedProducts && relatedProducts.size() > 0}">
        <section class="related-products-section">
            <div class="container">
                <h2 class="section-title">Sản phẩm liên quan</h2>
                <div class="products-grid">
                    <c:forEach var="rp" items="${relatedProducts}">
                        <a href="${pageContext.request.contextPath}/product-detail?id=${rp.id}" class="product-card">
                            <img src="${pageContext.request.contextPath}${rp.imageUrl}" alt="${rp.productName}" class="product-image" onerror="this.src='${pageContext.request.contextPath}/assets/images/no-image.png'">
                            <div class="product-info">
                                <h3 class="product-name">${rp.productName}</h3>
                                <c:if test="${not empty rp.brandName}">
                                    <p class="product-brand">${rp.brandName}</p>
                                </c:if>
                                <div>
                                    <span class="product-price"><fmt:formatNumber value="${not empty rp.salePrice && rp.salePrice > 0 ? rp.salePrice : rp.price}" pattern="#,###"/>đ</span>
                                    <c:if test="${not empty rp.salePrice && rp.salePrice > 0}">
                                        <span class="product-price-old"><fmt:formatNumber value="${rp.price}" pattern="#,###"/>đ</span>
                                    </c:if>
                                </div>
                                <div class="product-rating">
                                    <span class="stars">
                                        <c:forEach begin="1" end="5" var="i">
                                            ${i <= rp.averageRating ? '★' : '☆'}
                                        </c:forEach>
                                    </span>
                                    <span>(<fmt:formatNumber value="${rp.averageRating}" pattern="#.#"/>)</span>
                                </div>
                            </div>
                        </a>
                    </c:forEach>
                </div>
            </div>
        </section>
    </c:if>
</main>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
<script src="${pageContext.request.contextPath}/assets/js/product-detail.js"></script>
</body>
</html>