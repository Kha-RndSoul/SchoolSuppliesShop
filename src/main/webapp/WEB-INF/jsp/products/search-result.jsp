<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kết quả tìm kiếm:  ${keyword} - DPK Shop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-common.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-products.css">
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/header.jsp"/>

<main class="container">
    <section class="search-results-section">
        <!-- Breadcrumb -->
        <nav class="breadcrumb">
            <a href="${pageContext.request.contextPath}/">Trang chủ</a>
            <span>›</span>
            <a href="${pageContext.request.contextPath}/products">Sản phẩm</a>
            <span>›</span>
            <span>Tìm kiếm</span>
        </nav>

        <!-- Search Header -->
        <div class="search-header">
            <h1>Kết quả tìm kiếm cho: "<strong>${fn:escapeXml(keyword)}</strong>"</h1>
            <p class="search-count">
                <c:choose>
                <c:when test="${totalResults == 0}">
                    Không tìm thấy sản phẩm nào
                </c:when>
                <c:when test="${totalResults == 1}">
                Tìm thấy <strong>1</strong> sản phẩm
                </c:when>
                <c:otherwise>
                    Tìm thấy <strong><fmt:formatNumber value="${totalResults}" pattern="#,###"/></strong> sản phẩm
                    </c:otherwise>
                    </c:choose>
            </p>
        </div>

        <c:choose>
        <c:when test="${empty products}">
            <!-- No Results -->
            <div class="no-results">
                <div class="no-results-icon">🔍</div>
                <h2>Không tìm thấy sản phẩm nào</h2>
                <p>Không có sản phẩm nào phù hợp với từ khóa "<strong>${fn:escapeXml(keyword)}</strong>"</p>
                <div class="no-results-suggestions">
                    <h3>Gợi ý:</h3>
                    <ul>
                        <li>Kiểm tra lại chính tả từ khóa</li>
                        <li>Thử sử dụng từ khóa khác</li>
                        <li>Sử dụng từ khóa ngắn gọn hơn</li>
                    </ul>
                </div>
                <a href="${pageContext.request.contextPath}/products" class="btn-primary">
                    Xem tất cả sản phẩm
                </a>
            </div>
        </c:when>
        <c:otherwise>
            <!-- Sort & Filter Bar -->
            <div class="toolbar">
                <div class="toolbar-left">
                        <span class="showing-results">
                            Hiển thị ${(currentPage - 1) * 12 + 1}-${currentPage * 12 > totalResults ? totalResults :currentPage * 12}
                            trong ${totalResults} sản phẩm
                        </span>
                </div>
                <div class="toolbar-right">
                    <label for="sortBy">Sắp xếp:</label>
                    <select id="sortBy" name="sortBy" onchange="changeSortOrder(this.value)">
                        <option value="">Mặc định</option>
                        <option value="name-asc" ${sortBy == 'name-asc' ? 'selected' : ''}>Tên A-Z</option>
                        <option value="name-desc" ${sortBy == 'name-desc' ? 'selected' :  ''}>Tên Z-A</option>
                        <option value="price-asc" ${sortBy == 'price-asc' ? 'selected' :  ''}>Giá thấp → cao</option>
                        <option value="price-desc" ${sortBy == 'price-desc' ? 'selected' : ''}>Giá cao → thấp</option>
                        <option value="rating-desc" ${sortBy == 'rating-desc' ? 'selected' : ''}>Đánh giá cao nhất</option>
                    </select>
                </div>
            </div>

            <!-- Products Grid -->
            <div class="products-grid">
                <c:forEach var="product" items="${products}">
                <a href="${pageContext.request.contextPath}/product-detail?id=${product.id}" class="product-card">
                    <!-- Sale Badge -->
                    <c:if test="${not empty product.salePrice && product.salePrice > 0 && product.salePrice < product.price}">
                        <div class="product-badge sale">
                            <fmt:formatNumber value="${(1 - product.salePrice / product.price) * 100}" pattern="#"/>% OFF
                        </div>
                    </c:if>

                    <!-- Product Image -->
                    <img src="${pageContext.request.contextPath}${product.imageUrl}"
                         alt="${product.productName}"
                         class="product-image"
                         onerror="this.src='${pageContext.request.contextPath}/assets/images/no-image.png'">

                    <!-- Product Info -->
                    <div class="product-info">
                        <h3 class="product-name">${product.productName}</h3>
                        <p class="product-brand">${product.brandName}</p>

                        <!-- Price -->
                        <div class="product-price-wrapper">
                            <c:choose>
                            <c:when test="${not empty product.salePrice && product.salePrice > 0}">
                                            <span class="product-price">
                                                <fmt:formatNumber value="${product.salePrice}" pattern="#,###"/>đ
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

                        <!-- Rating -->
                        <div class="product-rating">
                                    <span class="stars">
                                        <c:forEach begin="1" end="5" var="i">
                                            <c:choose>
                                                <c:when test="${i <= product.averageRating}">★</c:when>
                                                <c:otherwise>☆</c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </span>
                            <span class="rating-number">
                                        (<fmt:formatNumber value="${product.averageRating}" pattern="#.#"/>)
                                    </span>
                        </div>

                        <!-- Sold Count -->
                        <c:if test="${product.soldCount > 0}">
                            <p class="product-sold">
                                Đã bán <fmt:formatNumber value="${product.soldCount}" pattern="#,###"/>
                            </p>
                            </c:if>
                    </div>
                </a>
                </c:forEach>
            </div>

            <!-- Pagination -->
            <c:if test="${totalPages > 1}">
            <div class="pagination">
                <c:if test="${currentPage > 1}">
                    <a href="${pageContext.request.contextPath}/search? keyword=${keyword}&page=${currentPage - 1}${not empty sortBy ? '&sortBy='.concat(sortBy) : ''}"
                       class="page-link">‹ Trước</a>
                </c:if>

                <c:forEach begin="1" end="${totalPages}" var="i">
                    <c:choose>
                        <c:when test="${i == currentPage}">
                            <span class="page-link active">${i}</span>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/search? keyword=${keyword}&page=${i}${not empty sortBy ? '&sortBy='.concat(sortBy) : ''}"
                               class="page-link">${i}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${currentPage < totalPages}">
                    <a href="${pageContext.request.contextPath}/search?keyword=${keyword}&page=${currentPage + 1}${not empty sortBy ? '&sortBy='.concat(sortBy) : ''}"
                       class="page-link">Sau ›</a>
                </c:if>
            </div>
        </c:if>
        </c:otherwise>
        </c:choose>
    </section>
</main>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>

<script>
    function changeSortOrder(sortValue) {
        const keyword = '${keyword}';
        const url = new URL(window.location);

        if (sortValue) {
            url.searchParams.set('sortBy', sortValue);
        } else {
            url.searchParams.delete('sortBy');
        }

        url.searchParams.set('page', '1'); // Reset về trang 1
        window.location.href = url.toString();
    }
</script>

</body>
</html>