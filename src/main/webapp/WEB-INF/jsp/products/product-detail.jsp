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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-product-review-popup.css"/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp"/>
//Đường dẫn trang chi tiết sản phẩm
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
                        <img id="mainImage" src="${pageContext.request.contextPath}${not empty productImages ? productImages[0].imageUrl : product.imageUrl}" alt="${product.productName}" class="main-image" onerror="this.src='${pageContext.request.contextPath}/assets/images/no-image.png'">
                        <c:if test="${not empty discountPercent && discountPercent > 0}"><span class="discount-badge">-${discountPercent}%</span></c:if>
                    </div>
                    <c:if test="${not empty productImages && productImages.size() > 1}">
                        <div class="thumbnail-gallery">
                            <c:forEach var="image" items="${productImages}" varStatus="status">
                                <img src="${pageContext.request.contextPath}${image.imageUrl}" class="thumbnail ${status.first ? 'active' : ''}" onclick="changeMainImage('${pageContext.request.contextPath}${image.imageUrl}', this)" onerror="this.src='${pageContext.request.contextPath}/assets/images/no-image.png'">
                            </c:forEach>
                        </div>
                    </c:if>
                </div>

                <div class="product-info-section">
                    <h1 class="product-title">${product.productName}</h1>
                    <c:if test="${not empty product.brandName}"><p class="product-brand">Thương hiệu: <strong>${product.brandName}</strong></p></c:if>

                    <div class="product-rating-section">
                        <span style="color: #fbbf24; font-size: 1.2rem">
                             <c:forEach begin="1" end="5" var="i">${i <= averageRating ? '★' : '☆'}</c:forEach>
                        </span>
                        <span class="rating-text"><fmt:formatNumber value="${averageRating}" pattern="#.#"/>/5</span>
                        <span class="review-count">(${reviewCount > 0 ? reviewCount : '0'} đánh giá)</span>
                        <span class="sold-info">| Đã bán: <strong>${product.soldCount}</strong></span>
                    </div>

                    <div class="price-section">
                        <c:choose>
                            <c:when test="${not empty product.salePrice && product.salePrice > 0}">
                                <span class="current-price"><fmt:formatNumber value="${product.salePrice}" pattern="#,###"/>đ</span>
                                <span class="original-price"><fmt:formatNumber value="${product.price}" pattern="#,###"/>đ</span>
                            </c:when>
                            <c:otherwise><span class="current-price"><fmt:formatNumber value="${product.price}" pattern="#,###"/>đ</span></c:otherwise>
                        </c:choose>
                    </div>

                    <div class="stock-section">
                        <span class="${product.stockQuantity > 0 ? 'stock-available' : 'stock-out'}">
                            ${product.stockQuantity > 0 ? 'Còn hàng' : 'Hết hàng'}
                        </span>
                    </div>

                    <form action="${pageContext.request.contextPath}/cart" method="POST" class="add-to-cart-form">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="productId" value="${product.id}">
                        <div class="quantity-section">
                            <label>Số lượng:</label>
                            <div class="quantity-controls">
                                <button type="button" class="qty-btn" onclick="decreaseQuantity()">−</button>
                                <input type="number" id="quantity" name="quantity" value="1" min="1" max="${product.stockQuantity}" class="qty-input">
                                <button type="button" class="qty-btn" onclick="increaseQuantity(${product.stockQuantity})">+</button>
                            </div>
                        </div>
                        <div class="action-buttons">
                            <c:if test="${product.stockQuantity > 0}">
                                <button type="submit" class="btn-add-to-cart">Thêm vào giỏ</button>
                                <button type="button" class="btn-buy-now" onclick="buyNow()">Mua ngay</button>
                            </c:if>
                            <c:if test="${product.stockQuantity <= 0}"><button type="button" class="btn-out-of-stock" disabled>Hết hàng</button></c:if>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>

    <section class="product-details-section">
        <div class="container">
            <div class="tabs-container">
                <div class="tab-headers">
                    <button class="tab-header active" onclick="switchTab('description')">Mô tả</button>
                    <button class="tab-header" onclick="switchTab('reviews')">Đánh giá (${reviewCount})</button>
                    <button class="tab-header" onclick="switchTab('specifications')">Thông số</button>
                </div>
<%--Mô tả sản phẩm, đánh giá và thông số kỹ thuật--%>
                <div class="tab-contents">
                    <div id="description" class="tab-content active">
                        <div class="description-content"><p>${not empty product.description ? product.description : 'Đang cập nhật...'}</p></div>
                    </div>

                    <div id="reviews" class="tab-content">
                        <div class="review-summary-section">
                            <div class="review-stats-left">
                                <div class="overall-rating">
                                    <div class="rating-number"><fmt:formatNumber value="${averageRating}" pattern="#.#"/></div>
                                    <div class="rating-stars-display">
                                        <c:forEach begin="1" end="5" var="i"><span style="color:${i<=averageRating?'#fbbf24':'#d1d5db'}">★</span></c:forEach>
                                    </div>
                                    <div class="rating-count">${reviewCount} đánh giá</div>
                                </div>
                                <div class="rating-breakdown">
                                    <c:forEach begin="1" end="5" var="idx">
                                        <c:set var="star" value="${6 - idx}"/>
                                        <c:set var="count" value="0"/>
                                        <c:forEach var="entry" items="${ratingDistribution}">
                                            <c:if test="${entry.key == star}"><c:set var="count" value="${entry.value}"/></c:if>
                                        </c:forEach>
                                        <c:set var="percent" value="${reviewCount > 0 ? (count * 100 / reviewCount) : 0}"/>

                                        <div class="rating-bar-item">
                                            <span class="rating-label">${star} ★</span>
                                            <div class="rating-bar-container"><div class="rating-bar-fill" style="width: ${percent}%"></div></div>
                                            <span class="rating-bar-count">${count}</span>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                            <div class="review-action-right">
                                <p class="review-prompt-text">Nhấn vào ngôi sao để đánh giá ngay!</p>
                                <div class="rating-stars-interactive">
                                    <c:forEach begin="1" end="5" var="i"><button class="star-btn" onclick="openReviewPopup(${i})">★</button></c:forEach>
                                </div>
                                <div class="cta-text">Trước khi đánh giá thấp hãy liên hệ cho chúng tôi để xử lý vấn đề!</div>
                            </div>
                        </div>
<%--Danh sách đánh giá--%>
                        <div class="reviews-list">
                            <c:if test="${empty reviews}"><div class="no-reviews"><p>Chưa có đánh giá nào.</p></div></c:if>
                            <c:forEach var="review" items="${reviews}">
                                <div class="review-item">
                                    <div class="review-header">
                                        <span class="reviewer-name">${not empty review.customerName ? review.customerName : 'Khách hàng'}</span>
                                        <span class="review-date"><fmt:formatDate value="${review.createdAt}" pattern="dd/MM/yyyy"/></span>
                                    </div>
                                    <div class="review-rating" style="color: #fbbf24">
                                        <c:forEach begin="1" end="5" var="i">${i <= review.rating ? '★' : '☆'}</c:forEach>
                                    </div>
                                    <div class="review-comment">${review.comment}</div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
<%--Thông số sản phẩm--%>
                    <div id="specifications" class="tab-content">
                        <table class="specifications-table">
                            <tr><td>Tên SP</td><td>${product.productName}</td></tr>
                            <c:if test="${not empty product.categoryName}"><tr><td>Danh mục</td><td>${product.categoryName}</td></tr></c:if>
                            <c:if test="${not empty product.brandName}"><tr><td>Thương hiệu</td><td>${product.brandName}</td></tr></c:if>
                            <tr><td>Xuất xứ </td><td>Việt Nam</td></tr>
                            <tr><td>Bảo hành </td>
                                <td>
                                    <c:choose>
                                    <%-- Nếu là balo va căpj thi bảo hành 1 tháng --%>
                                    <c:when test="${product.categoryName == 'Balo & cặp'}">1 tháng
                                    </c:when>
                                        <%-- Nếu là máy tính thì bảo hành 12 tháng --%>
                                            <c:when test="${product.categoryName == 'Máy tính' || product.categoryName == 'Đèn học'}">12 tháng
                                            </c:when>
                                        <%-- Còn lại không bảo hành --%>
                                            <c:otherwise>Không bảo hành</c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
<%--Sản phẩm liên quan--%>
            <c:if test="${not empty relatedProducts}">
                <section class="related-products-section">
                    <div class="container">
                        <h2 class="section-title">Sản phẩm liên quan</h2>
                        <div class="products-grid">
                            <c:forEach var="rp" items="${relatedProducts}">
                                <a href="${pageContext.request.contextPath}/product-detail?id=${rp.id}" class="product-card">
                                    <img src="${pageContext.request.contextPath}${rp.imageUrl}" class="product-image" onerror="this.src='${pageContext.request.contextPath}/assets/images/no-image.png'">
                                    <div class="product-info">
                                        <h3 class="product-name">${rp.productName}</h3>
                                        <div class="product-price"><fmt:formatNumber value="${rp.salePrice > 0 ? rp.salePrice : rp.price}" pattern="#,###"/>đ</div>
                                    </div>
                                </a>
                            </c:forEach>
                        </div>
                    </div>
                </section>
            </c:if>
<%--Popup đánh giá sản phẩm--%>
            <div class="review-popup-overlay" id="reviewPopupOverlay">
                <div class="review-popup">
                    <div class="review-popup-header">
                        <h3>Đánh giá sản phẩm</h3>
                        <button class="close-popup-btn" onclick="closeReviewPopup()">×</button>
                    </div>

                    <div class="review-popup-body">
                        <c:choose>
                            <c:when test="${not empty sessionScope.customer}">
                                <form id="reviewPopupForm" action="${pageContext.request.contextPath}/product-review" method="POST">
                                    <input type="hidden" name="productId" value="${product.id}">

                                    <div class="review-popup-product">
                                        <img src="${pageContext.request.contextPath}${product.imageUrl}" class="popup-product-image" onerror="this.src='${pageContext.request.contextPath}/assets/images/no-image.png'">
                                        <div><strong>${product.productName}</strong></div>
                                    </div>

                                    <div class="form-group">
                                        <label>Đánh giá <span style="color:red">*</span></label>
                                        <div class="popup-star-rating">
                                            <div class="popup-stars">
                                                <c:forEach begin="0" end="4" var="i">
                                                    <c:set var="val" value="${5 - i}"/>
                                                    <input type="radio" name="rating" id="st${val}" value="${val}" ${val==5?'required':''}>
                                                    <label for="st${val}" title="${val} sao">★</label>
                                                </c:forEach>
                                            </div>
                                            <span class="rating-text-label">Chọn sao</span>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label>Nhận xét <span style="color:red">*</span></label>
                                        <textarea name="comment" id="reviewComment" class="review-textarea" rows="4" placeholder="Nhập tối thiểu 10 ký tự..." required minlength="10"></textarea>
                                        <div class="char-counter"><span id="charCounter">0</span>/500</div>
                                    </div>

                                    <div class="review-popup-footer">
                                        <button type="button" class="btn-cancel" onclick="closeReviewPopup()">Hủy</button>
                                        <button type="submit" class="btn-submit-review">Gửi đánh giá</button>
                                    </div>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <div class="alert alert-error">Bạn cần đăng nhập để đánh giá.</div>
                                <div style="text-align: center;">
                                    <a href="${pageContext.request.contextPath}/login?redirect=${pageContext.request.contextPath}/product-detail?id=${product.id}" class="btn-submit-review" style="text-decoration:none">Đăng nhập ngay</a>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </main>

        <jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
        <script src="${pageContext.request.contextPath}/assets/js/product-detail.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/product-review-popup.js"></script>
        </body>
        </html>