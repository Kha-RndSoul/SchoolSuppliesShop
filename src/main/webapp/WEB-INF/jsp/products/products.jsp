<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
        <!-- Sidebar Filter  -->
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
                    <button onclick="filterByPrice()" class="btn-filter" style="width: 100%; margin-top: 0.75rem; padding: 0.625rem 1rem; background: #3b82f6; color: white; border: none; border-radius: 0.375rem; cursor: pointer; font-weight: 500; transition: background-color 0.2s;">Áp dụng</button>
                </div>
            </div>
            <!-- Thương hiệu -->
            <div class="filter-section">
                <h3>Thương hiệu</h3>
                <div style="display: flex; flex-direction: column; gap: 0.5rem;">
                    <c:forEach var="brand" items="${allBrands}">
                        <label style="display: flex; align-items: center; gap: 0.5rem; padding: 0.5rem; border-radius: 0.375rem; cursor: pointer; transition: background-color 0.2s;"
                               class="brand-filter-item"
                               onmouseover="this.style.backgroundColor='#f3f4f6'"
                               onmouseout="this.style.backgroundColor='transparent'">
                            <input type="checkbox"
                                   name="brandFilter"
                                   value="${brand.id}"
                                   data-brand-id="${brand.id}"
                            <c:if test="${not empty paramValues.brandId}">
                            <c:forEach var="selectedBrandId" items="${paramValues.brandId}">
                                   <c:if test="${selectedBrandId == brand.id.toString()}">checked</c:if>
                            </c:forEach>
                            </c:if>
                                   onchange="filterByBrand()"
                                   style="width: 1rem; height: 1rem; cursor: pointer;">
                            <span style="color: #374151; user-select: none;">${brand.brandName}</span>
                        </label>
                    </c:forEach>
                </div>
            </div>
        </aside>

        <!-- Products Grid -->
        <main class="products-main">
            <!-- Hiển thị breadcrumb nếu đang lọc theo category hoặc brand -->
            <c:if test="${not empty currentCategory or not empty currentBrand or not empty searchKeyword or not empty paramValues.brandId}">
                <div class="breadcrumb" style="margin-bottom: 1rem; padding: 0.75rem; background: #f9fafb; border-radius: 0.5rem;">
                    <a href="${pageContext.request.contextPath}/products" style="color: #6b7280;">Tất cả sản phẩm</a>
                    <c:if test="${not empty currentCategory}">
                        <span style="margin: 0 0.5rem; color: #d1d5db;">›</span>
                        <strong style="color: #111827;">${currentCategory.categoryName}</strong>
                    </c:if>
                    <c:if test="${not empty paramValues.brandId}">
                        <span style="margin: 0 0.5rem; color: #d1d5db;">›</span>
                        <strong style="color: #111827;">Đã lọc theo thương hiệu</strong>
                    </c:if>
                    <c:if test="${not empty searchKeyword}">
                        <span style="margin: 0 0.5rem; color: #d1d5db;">›</span>
                        <strong style="color: #111827;">Kết quả tìm kiếm: "${searchKeyword}"</strong>
                    </c:if>
                </div>
            </c:if>

            <div class="products-header">
                <div class="products-count">
                    <span class="count-text">Hiển thị ${fn:length(products)} / ${totalProducts} sản phẩm</span>
                </div>

                <!-- Sort section -->
                <div class="sort-section">
                    <label for="sortSelect" class="sort-label">Sắp xếp theo:</label>
                    <div style="position: relative; display: inline-block;">
                        <select name="sort" id="sortSelect" class="sort-select" onchange="changeSortOrder()">
                            <option value="">Mặc định</option>
                            <option value="price-asc" ${sortBy == 'price-asc' ? 'selected' : ''}>Giá: Thấp đến Cao</option>
                            <option value="price-desc" ${sortBy == 'price-desc' ? 'selected' : ''}>Giá: Cao đến Thấp</option>
                            <option value="name-asc" ${sortBy == 'name-asc' ? 'selected' : ''}>Tên: A-Z</option>
                            <option value="name-desc" ${sortBy == 'name-desc' ? 'selected' : ''}>Tên: Z-A</option>
                            <option value="rating-desc" ${sortBy == 'rating-desc' ? 'selected' : ''}>Đánh giá cao nhất</option>
                        </select>
                        <span style="position: absolute; right: 1rem; top: 50%; transform: translateY(-50%); color: #6b7280; font-size: 0.75rem; pointer-events: none;">▼</span>
                    </div>
                </div>
            </div>

            <c:choose>
                <c:when test="${empty products}">
                    <div class="no-products" style="text-align: center; padding: 3rem 1rem; background: #f9fafb; border-radius: 0.5rem; margin-top: 2rem;">
                        <p style="font-size: 1.5rem; color: #6b7280; margin-bottom: 1rem;">😔 Không tìm thấy sản phẩm nào</p>
                        <p style="color: #9ca3af; margin-bottom: 1.5rem;">
                            <c:if test="${not empty paramValues.brandId}">
                                Không có sản phẩm nào thuộc thương hiệu đã chọn trong danh mục này.
                            </c:if>
                            <c:if test="${empty paramValues.brandId}">
                                Vui lòng thử lại với bộ lọc khác.
                            </c:if>
                        </p>
                        <a href="${pageContext.request.contextPath}/products" class="btn-primary" style="display: inline-block; padding: 0.75rem 1.5rem; background: #3b82f6; color: white; text-decoration: none; border-radius: 0.375rem; font-weight: 500; transition: background-color 0.2s;">Xem tất cả sản phẩm</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="products-grid">
                        <c:forEach var="product" items="${products}">
                            <a href="${pageContext.request.contextPath}/product-detail?id=${product.id}" class="product-card">
                                <img src="${pageContext.request.contextPath}${product.imageUrl}"
                                     alt="${product.productName}"
                                     class="product-image"
                                     onerror="this.onerror=null; this.src='data:image/svg+xml,%3Csvg xmlns=%22http://www.w3.org/2000/svg%22 width=%22300%22 height=%22300%22%3E%3Crect fill=%22%23f5f5f5%22 width=%22300%22 height=%22300%22/%3E%3Ctext x=%2250%25%22 y=%2250%25%22 dominant-baseline=%22middle%22 text-anchor=%22middle%22 font-family=%22Arial%22 font-size=%2216%22 fill=%22%23999%22%3EKh%C3%B4ng c%C3%B3 %E1%BA%A3nh%3C/text%3E%3C/svg%3E'">
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

                    <!-- Pagination  -->
                    <c:if test="${totalPages > 1}">
                        <div class="pagination">
                                <%-- Build base URL với tất cả params --%>
                            <c:set var="baseUrl" value="?" />
                            <c:if test="${not empty param.categoryId}">
                                <c:set var="baseUrl" value="${baseUrl}categoryId=${param.categoryId}&" />
                            </c:if>
                            <c:if test="${not empty paramValues.brandId}">
                                <c:forEach var="bid" items="${paramValues.brandId}">
                                    <c:set var="baseUrl" value="${baseUrl}brandId=${bid}&" />
                                </c:forEach>
                            </c:if>
                            <c:if test="${not empty searchKeyword}">
                                <c:set var="baseUrl" value="${baseUrl}keyword=${searchKeyword}&" />
                            </c:if>
                            <c:if test="${not empty sortBy}">
                                <c:set var="baseUrl" value="${baseUrl}sortBy=${sortBy}&" />
                            </c:if>
                            <c:if test="${not empty maxPrice}">
                                <c:set var="baseUrl" value="${baseUrl}maxPrice=${maxPrice}&" />
                            </c:if>

                                <%-- Nút Trước --%>
                            <c:choose>
                                <c:when test="${currentPage > 1}">
                                    <a href="${baseUrl}page=${currentPage - 1}" class="page-link">‹ Trước</a>
                                </c:when>
                                <c:otherwise>
                                    <span class="page-link disabled">‹ Trước</span>
                                </c:otherwise>
                            </c:choose>

                                <%-- Các số trang --%>
                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <c:choose>
                                    <c:when test="${i == currentPage}">
                                        <span class="page-link active">${i}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="${baseUrl}page=${i}" class="page-link">${i}</a>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>

                                <%-- Nút Sau --%>
                            <c:choose>
                                <c:when test="${currentPage < totalPages}">
                                    <a href="${baseUrl}page=${currentPage + 1}" class="page-link">Sau ›</a>
                                </c:when>
                                <c:otherwise>
                                    <span class="page-link disabled">Sau ›</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </main>
    </div>
</div>

<%-- INCLUDE FOOTER --%>
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

    function filterByBrand() {
        const urlParams = new URLSearchParams(window.location.search);

        // Xóa tất cả brandId cũ
        urlParams.delete('brandId');

        // Lấy tất cả checkbox được chọn
        const checkedBoxes = document.querySelectorAll('input[name="brandFilter"]:checked');

        // Thêm từng brandId vào URL
        checkedBoxes.forEach(checkbox => {
            urlParams.append('brandId', checkbox.value);
        });

        // Reset về trang 1 khi lọc
        urlParams.delete('page');

        // Redirect
        window.location.href = '${pageContext.request.contextPath}/products?' + urlParams.toString();
    }

    // Update price display
    document.getElementById('priceRange').addEventListener('input', function() {
        document.getElementById('maxPrice').textContent = parseInt(this.value).toLocaleString('vi-VN') + 'đ';
    });

    // Debug: Log current filters on page load
    console.log('Current URL:', window.location.search);
    console.log('Selected brands:', Array.from(document.querySelectorAll('input[name="brandFilter"]:checked')).map(cb => cb.value));
</script>
</body>
</html>