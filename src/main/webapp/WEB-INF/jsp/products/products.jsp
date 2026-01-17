<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh Sách Sản Phẩm - DPK Shop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-product.css">
</head>
<body>

<%-- INCLUDE HEADER --%>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />

<div class="container">
    <div class="products-layout">
        <!-- Sidebar Filter -->
        <aside class="sidebar">
            <!-- Bộ lọc Theo Giá -->
            <div class="filter-section">
                <h3 class="filter-title">Khoảng Giá</h3>
                <div class="filter-group">
                    <input type="range" id="priceRange" min="0" max="5000000" value="${maxPrice != null ? maxPrice : 5000000}" step="50000">
                    <div class="price-range">
                        <span>0đ</span>
                        <span id="maxPrice"><fmt:formatNumber value="${maxPrice != null ? maxPrice : 5000000}" pattern="#,###"/>đ</span>
                    </div>
                    <button onclick="filterByPrice()" class="btn-filter">Áp dụng</button>
                </div>
            </div>

            <!-- Danh mục -->
            <div class="filter-section">
                <h3>Danh mục</h3>
                <c:forEach var="category" items="${allCategories}">
                    <label>
                        <input type="checkbox" ${currentCategory != null && currentCategory.id == category.id ? 'checked' :  ''}>
                        <a href="${pageContext.request.contextPath}/products?categoryId=${category.id}">${category.categoryName}</a>
                    </label>
                </c:forEach>
            </div>

            <!-- Thương hiệu -->
            <div class="filter-section">
                <h3>Thương hiệu</h3>
                <c:forEach var="brand" items="${allBrands}">
                    <label>
                        <input type="checkbox" ${currentBrand != null && currentBrand.id == brand.id ?  'checked' : ''}>
                        <a href="${pageContext.request.contextPath}/products? brandId=${brand.id}">${brand.brandName}</a>
                    </label>
                </c:forEach>
            </div>
        </aside>

        <!-- Products Grid -->
        <main class="products-main">
            <div class="products-header">
                <div class="products-count">
                    <span class="count-text">Hiển thị ${products.size()} / ${totalProducts} sản phẩm</span>
                </div>

                <!-- Sort section -->
                <div class="sort-section">
                    <label for="sortSelect" class="sort-label">Sắp xếp theo:</label>
                    <div style="position: relative; display: inline-block;">
                        <select name="sort" id="sortSelect" class="sort-select" onchange="changeSortOrder()">
                            <option value="">Mặc định</option>
                            <option value="price-asc" ${sortBy == 'price-asc' ? 'selected' : ''}>Giá:   Thấp đến Cao</option>
                            <option value="price-desc" ${sortBy == 'price-desc' ? 'selected' : ''}>Giá: Cao đến Thấp</option>
                            <option value="name-asc" ${sortBy == 'name-asc' ?  'selected' : ''}>Tên: A-Z</option>
                            <option value="name-desc" ${sortBy == 'name-desc' ? 'selected' : ''}>Tên: Z-A</option>
                            <option value="rating-desc" ${sortBy == 'rating-desc' ? 'selected' : ''}>Đánh giá cao nhất</option>
                        </select>
                        <span style="position:  absolute; right: 1rem; top: 50%; transform: translateY(-50%); color: #6b7280; font-size: 0.75rem; pointer-events: none;">▼</span>
                    </div>
                </div>
            </div>

            <c:choose>
            <c:when test="${empty products}">
                <div class="no-products">
                    <p>😔 Không tìm thấy sản phẩm nào</p>
                    <a href="${pageContext.request.contextPath}/products" class="btn-primary">Xem tất cả sản phẩm</a>
                </div>
            </c:when>
            <c:otherwise>
            <div class="products-grid">
                <c:forEach var="product" items="${products}">
                <a href="${pageContext.request.contextPath}/product-detail?id=${product.id}" class="product-card">
                    <img src="${pageContext.request.contextPath}${product.imageUrl}"
                         alt="${product.productName}"
                         class="product-image"
                         onerror="this.src='${pageContext.request.contextPath}/assets/images/no-image.png'">
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

            <!-- Pagination -->
            <c:if test="${totalPages > 1}">
                <div class="pagination">
                    <c:if test="${currentPage > 1}">
                        <a href="?page=${currentPage - 1}${not empty searchKeyword ? '&keyword='.concat(searchKeyword) : ''}${not empty sortBy ?  '&sortBy='.concat(sortBy) : ''}" class="page-link">‹ Trước</a>
                    </c:if>

                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <c:choose>
                            <c:when test="${i == currentPage}">
                            <span class="page-link active">${i}</span>
                            </c:when>
                            <c:otherwise>
                                <a href="?page=${i}${not empty searchKeyword ? '&keyword='.concat(searchKeyword) : ''}${not empty sortBy ? '&sortBy='.concat(sortBy) : ''}" class="page-link">${i}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${currentPage < totalPages}">
                        <a href="?page=${currentPage + 1}${not empty searchKeyword ? '&keyword='.concat(searchKeyword) : ''}${not empty sortBy ?  '&sortBy='.concat(sortBy) : ''}" class="page-link">Sau ›</a>
                    </c:if>
                </div>
            </c:if>
            </c:otherwise>
            </c:choose>
        </main>
    </div>
</div>

<%--  INCLUDE FOOTER --%>
<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />

<script>
    function changeSortOrder() {
        const sortValue = document.getElementById('sortSelect').value;
        const urlParams = new URLSearchParams(window.location.search);

        if (sortValue) {
            urlParams.set('sortBy', sortValue);
        } else {
            urlParams.delete('sortBy');
        }

        urlParams.delete('page');
        window.location.href = '${pageContext.request.contextPath}/products?' + urlParams.toString();
    }

    function filterByPrice() {
        const maxPrice = document.getElementById('priceRange').value;
        const urlParams = new URLSearchParams(window.location.search);
        urlParams.set('maxPrice', maxPrice);
        urlParams.delete('page');
        window.location.href = '${pageContext.request.contextPath}/products?' + urlParams.toString();
    }

    // Update price display
    document.getElementById('priceRange').addEventListener('input', function() {
        document.getElementById('maxPrice').textContent = parseInt(this.value).toLocaleString('vi-VN') + 'đ';
    });
</script>
</body>
</html>