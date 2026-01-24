<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - DPK Shop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-admin.css">
</head>
<body>

<%-- INCLUDE HEADER CHUNG --%>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>

<!-- Admin Panel Container -->
<div class="admin-container">
    <!-- Sidebar -->
    <aside class="admin-sidebar" id="adminSidebar">
        <div class="sidebar-header">
            <h2>🛠️ Bảng quản trị</h2>
        </div>
        <nav class="sidebar-nav">
            <a href="#dashboard-section" class="nav-item active" data-section="dashboard">
                <span class="nav-icon">📊</span>
                <span class="nav-text">Dashboard</span>
            </a>

            <a href="#products-section" class="nav-item" data-section="products">
                <span class="nav-icon">📦</span>
                <span class="nav-text">Sản phẩm</span>
            </a>

            <a href="#orders-section" class="nav-item" data-section="orders">
                <span class="nav-icon">🛒</span>
                <span class="nav-text">Đơn hàng</span>
            </a>

            <a href="#customers-section" class="nav-item" data-section="customers">
                <span class="nav-icon">👥</span>
                <span class="nav-text">Khách hàng</span>
            </a>

            <a href="#categories-section" class="nav-item" data-section="categories">
                <span class="nav-icon">📑</span>
                <span class="nav-text">Danh mục</span>
            </a>

            <a href="#coupons-section" class="nav-item" data-section="coupons">
                <span class="nav-icon">🎟️</span>
                <span class="nav-text">Mã giảm giá</span>
            </a>

            <a href="#reviews-section" class="nav-item" data-section="reviews">
                <span class="nav-icon">⭐</span>
                <span class="nav-text">Đánh giá</span>
            </a>

            <a href="#reports-section" class="nav-item" data-section="reports">
                <span class="nav-icon">📈</span>
                <span class="nav-text">Báo cáo</span>
            </a>

            <a href="#settings-section" class="nav-item" data-section="settings">
                <span class="nav-icon">⚙️</span>
                <span class="nav-text">Cài đặt</span>
            </a>
        </nav>
    </aside>

    <!-- Main Content -->
    <main class="admin-main">
        <!-- DASHBOARD SECTION -->
        <section id="dashboard-section" class="admin-section active">
            <div class="section-header">
                <h2>Dashboard</h2>
                <div class="section-actions">
                    <select class="filter-select">
                        <option value="today">Hôm nay</option>
                        <option value="yesterday">Hôm qua</option>
                        <option value="7days">7 ngày qua</option>
                        <option value="30days" selected>30 ngày qua</option>
                        <option value="custom">Tùy chỉnh</option>
                    </select>
                </div>
            </div>

            <!-- Stats Cards -->
            <div class="stats-grid">
                <div class="stat-card blue">
                    <div class="stat-icon">💰</div>
                    <div class="stat-content">
                        <p class="stat-label">Doanh Thu</p>
                        <h3 class="stat-value">${stats.revenue}</h3>
                    </div>
                </div>

                <div class="stat-card orange">
                    <div class="stat-icon">🛒</div>
                    <div class="stat-content">
                        <p class="stat-label">Đơn Hàng Mới</p>
                        <h3 class="stat-value">${stats.newOrders}</h3>
                    </div>
                </div>
            </div>

            <!-- Recent Orders -->
            <div class="dashboard-widget">
                <div class="widget-header">
                    <h3 class="widget-title">Đơn Hàng Gần Đây</h3>
                </div>
                <div class="table-responsive">
                    <table class="admin-table">
                        <thead>
                        <tr>
                            <th>Mã Đơn</th>
                            <th>Khách Hàng</th>
                            <th>Tổng Tiền</th>
                            <th>Trạng Thái</th>
                            <th>Ngày Đặt</th>
                            <th>Hành Động</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:choose>
                            <c:when test="${empty recentOrders}">
                                <tr>
                                    <td colspan="6" style="text-align: center; padding: 2rem; color: #6b7280;">
                                        Chưa có đơn hàng nào
                                    </td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="order" items="${recentOrders}">
                                    <tr>
                                        <td><span class="order-code">${order.order_code}</span></td>
                                        <td>${order.customer_name}</td>
                                        <td><strong><fmt:formatNumber value="${order.total_amount}" pattern="#,###"/>đ</strong></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${order.order_status == 'PENDING'}">
                                                    <span class="status-badge pending">Chờ xử lý</span>
                                                </c:when>
                                                <c:when test="${order.order_status == 'CONFIRMED'}">
                                                    <span class="status-badge processing">Đang xử lý</span>
                                                </c:when>
                                                <c:when test="${order.order_status == 'SHIPPING'}">
                                                    <span class="status-badge shipping">Đang giao</span>
                                                </c:when>
                                                <c:when test="${order.order_status == 'DELIVERED'}">
                                                    <span class="status-badge completed">Hoàn thành</span>
                                                </c:when>
                                                <c:when test="${order.order_status == 'CANCELLED'}">
                                                    <span class="status-badge cancelled">Đã hủy</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="status-badge">${order.order_status}</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${order.created_at}" pattern="dd/MM/yyyy HH:mm"/>
                                        </td>
                                        <td>
                                            <button class="btn-primary"
                                                    onclick="viewOrderDetail(${order.id})"
                                                    title="Xem chi tiết">Xem</button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                        </tbody>
                    </table>
                    <a href="#orders-section" class="view-all-link" onclick="showSection('orders')">Xem tất cả →</a>
                </div>
            </div>

            <!-- Best Selling Products -->
            <div class="dashboard-widget">
                <div class="widget-header">
                    <h3 class="widget-title">Sản Phẩm Bán Chạy</h3>
                </div>
                <div class="table-responsive">
                    <table class="admin-table">
                        <thead>
                        <tr>
                            <th>Sản Phẩm</th>
                            <th>Danh Mục</th>
                            <th>Giá</th>
                            <th>Đã Bán</th>
                            <th>Tồn Kho</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:choose>
                            <c:when test="${empty bestSellers}">
                                <tr>
                                    <td colspan="5" style="text-align: center; padding: 2rem; color: #6b7280;">
                                        Chưa có dữ liệu sản phẩm bán chạy
                                    </td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="product" items="${bestSellers}">
                                    <tr>
                                        <td>
                                            <div class="product-info">
                                                <span class="product-name">${product.productName}</span>
                                            </div>
                                        </td>
                                        <td>${product.categoryName}</td>
                                        <td>
                                            <strong>
                                                <c:choose>
                                                    <c:when test="${product.salePrice > 0 && product.salePrice < product.price}">
                                                        <fmt:formatNumber value="${product.salePrice}" pattern="#,###"/>đ
                                                    </c:when>
                                                    <c:otherwise>
                                                        <fmt:formatNumber value="${product.price}" pattern="#,###"/>đ
                                                    </c:otherwise>
                                                </c:choose>
                                            </strong>
                                        </td>
                                        <td>
                                            <fmt:formatNumber value="${product.soldCount}" pattern="#,###"/>
                                        </td>
                                        <td>
                                                <%-- Logic phân loại stock badge theo số lượng tồn kho --%>
                                            <c:choose>
                                                <c:when test="${product.stockQuantity >= 500}">
                                        <span class="stock-badge high">
                                            <fmt:formatNumber value="${product.stockQuantity}" pattern="#,###"/>
                                        </span>
                                                </c:when>
                                                <c:when test="${product.stockQuantity >= 100 && product.stockQuantity < 500}">
                                        <span class="stock-badge medium">
                                            <fmt:formatNumber value="${product.stockQuantity}" pattern="#,###"/>
                                        </span>
                                                </c:when>
                                                <c:otherwise>
                                        <span class="stock-badge low">
                                            <fmt:formatNumber value="${product.stockQuantity}" pattern="#,###"/>
                                        </span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                        </tbody>
                    </table>
                    <a href="#products-section" class="view-all-link" onclick="showSection('products')">Xem tất cả →</a>
                </div>
            </div>
        </section>

        <!-- PRODUCTS SECTION-->
        <section id="products-section" class="admin-section">
            <div class="section-header">
                <h2>Quản Lý Sản Phẩm</h2>
                <div class="section-actions">
                    <button class="btn-add-new" id="toggleFormBtn" onclick="toggleProductForm()">
                        Thêm Sản Phẩm
                    </button>
                </div>
            </div>

            <!-- Toast Notification -->
            <div id="toast" class="toast"></div>

            <!-- Product Form  -->
            <div class="product-form-container" id="productFormContainer">
                <h3> Thêm Sản Phẩm Mới</h3>
                <form class="product-form" id="productForm">
                    <input type="hidden" name="action" value="add">

                    <!-- Row 1: Tên sản phẩm & Danh mục -->
                    <div class="form-row">
                        <div class="form-group">
                            <label>Tên sản phẩm *</label>
                            <input type="text"
                                   name="productName"
                                   id="productName"
                                   placeholder="VD: Bút bi Thiên Long TL-027"
                                   required>
                        </div>

                        <div class="form-group">
                            <label>Danh mục *</label>
                            <select name="categoryId" id="categoryId" required>
                                <option value="">-- Chọn danh mục --</option>
                                <c:forEach var="category" items="${allCategories}">
                                    <option value="${category.id}">${category.categoryName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <!-- Row 2: Thương hiệu & Giá bán -->
                    <div class="form-row">
                        <div class="form-group">
                            <label>Thương hiệu *</label>
                            <select name="brandId" id="brandId" required>
                                <option value="">-- Chọn thương hiệu --</option>
                                <c:forEach var="brand" items="${allBrands}">
                                    <option value="${brand.id}">${brand.brandName}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <label>Giá bán (đ) *</label>
                            <input type="number"
                                   name="price"
                                   id="price"
                                   placeholder="VD: 25000"
                                   min="0"
                                   step="1000"
                                   required>
                        </div>
                    </div>

                    <!-- Row 3: Giá khuyến mãi & Tồn kho -->
                    <div class="form-row">
                        <div class="form-group">
                            <label>Giá khuyến mãi (đ)</label>
                            <input type="number"
                                   name="salePrice"
                                   id="salePrice"
                                   placeholder="VD: 20000 (để trống nếu không có)"
                                   min="0"
                                   step="1000">
                        </div>

                        <div class="form-group">
                            <label>Số lượng tồn kho *</label>
                            <input type="number"
                                   name="stockQuantity"
                                   id="stockQuantity"
                                   placeholder="VD: 100"
                                   min="0"
                                   required>
                        </div>
                    </div>

                    <!-- Row 4: Mô tả sản phẩm -->
                    <div class="form-group">
                        <label>Mô tả sản phẩm</label>
                        <textarea name="description"
                                  id="description"
                                  rows="4"
                                  placeholder="Nhập mô tả chi tiết về sản phẩm..."></textarea>
                    </div>

                    <!-- Row 5: Upload hình ảnh -->
                    <div class="form-group">
                        <label>Hình ảnh sản phẩm</label>
                        <input type="file"
                               name="productImage"
                               id="productImage"
                               accept="image/*"
                               onchange="previewImage(event)">
                        <div id="imagePreview" class="image-preview"></div>
                        <small style="color: #6b7280;">Chọn hình ảnh có kích thước tối đa 10MB</small>
                    </div>

                    <!-- Form Actions -->
                    <div class="form-actions">
                        <button type="submit" class="btn-primary" id="submitBtn">
                            Lưu Sản Phẩm
                        </button>
                        <button type="button" class="btn-reset" onclick="resetForm()">
                            Nhập Lại
                        </button>
                        <button type="button" class="btn-secondary" onclick="toggleProductForm()">
                            Hủy
                        </button>
                    </div>
                </form>
            </div>

            <!--  PRODUCTS TABLE  -->
            <div class="dashboard-widget">
                <div class="widget-header">
                    <h3 class="widget-title">Danh Sách Sản Phẩm</h3>

                    <!--  SEARCH BOX  -->
                    <div class="product-search-box">
                        <input type="text"
                               id="productSearchInput"
                               class="search-input"
                               placeholder=" Tìm theo ID hoặc tên sản phẩm..."
                               onkeyup="searchProducts()">
                        <button class="search-clear-btn"
                                id="searchClearBtn"
                                onclick="clearSearch()"
                                style="display: none;">
                            ✕
                        </button>
                    </div>
                </div>

                <div class="table-responsive">
                    <table class="admin-table">
                        <thead>
                        <tr>
                            <th>Mã SP</th>
                            <th>Tên Sản Phẩm</th>
                            <th>Danh Mục</th>
                            <th>Thương Hiệu</th>
                            <th>Giá Bán</th>
                            <th>Tồn Kho</th>
                            <th>Hành Động</th>
                        </tr>
                        </thead>
                        <tbody id="productTableBody">
                        <%-- Products will be loaded by AJAX --%>
                        <tr>
                            <td colspan="7" style="text-align: center; padding: 3rem;">
                                <div style="font-size: 2rem;"></div>
                                <div style="margin-top: 1rem; color: #6b7280;">Click vào "Sản phẩm" để tải dữ liệu...</div>
                            </td>
                        </tr>
                        </tbody>
                    </table>

                    <%-- Pagination --%>
                    <div class="pagination"></div>
                </div>
            </div>
        </section>

        <!-- OTHER SECTIONS ( -->
        <section id="orders-section" class="admin-section">
            <div class="section-header">
                <h2>Quản lý đơn hàng</h2>
            </div>
            <h3>Danh sách đơn hàng</h3>
            <p>Tương tự section Sản phẩm</p>
        </section>

        <section id="customers-section" class="admin-section">
            <div class="section-header">
                <h2>Quản lý khách hàng</h2>
            </div>
            <h3>Danh sách khách hàng</h3>
            <p>Tương tự section Sản phẩm</p>
        </section>

        <section id="categories-section" class="admin-section">
            <div class="section-header">
                <h2>Quản lý danh mục</h2>
            </div>
            <h3>Xem danh sách danh mục, thêm, sửa, xóa danh mục</h3>
            <p>Tương tự section Sản phẩm</p>
        </section>

        <section id="coupons-section" class="admin-section">
            <div class="section-header">
                <h2>Quản lý mã giảm giá</h2>
            </div>
            <h3>Xem danh sách mã giảm giá, thêm, sửa, xóa mã giảm giá</h3>
            <p>Tương tự section Sản phẩm</p>
        </section>

        <section id="reviews-section" class="admin-section">
            <div class="section-header">
                <h2>Quản lý đánh giá</h2>
            </div>
            <h3>Xem danh sách đánh giá, duyệt/từ chối đánh giá</h3>
            <p>Tương tự section Sản phẩm</p>
        </section>

        <section id="reports-section" class="admin-section">
            <div class="section-header">
                <h2>Báo cáo</h2>
            </div>
            <h3>Tổng quan thống kê, báo cáo sản phẩm bán chạy</h3>
            <p>Tương tự section Dashboard</p>
        </section>

        <section id="settings-section" class="admin-section">
            <div class="section-header">
                <h2>Cấu hình banner</h2>
            </div>

            <div class="banner-config" style="margin-top: 20px;">
                <div class="dashboard-widget">
                    <div class="widget-header">
                        <h3 class="widget-title">Quản lý Banner Trang chủ</h3>
                    </div>
                    <div class="table-responsive">
                        <table class="admin-table banner-table">
                            <thead>
                            <tr>
                                <th style="width: 50px;">ID</th>
                                <th style="width: 150px;">Hình ảnh</th>
                                <th>Tiêu đề</th>
                                <th style="width: 150px;">Trạng thái</th>
                            </tr>
                            </thead>
                            <tbody id="bannerTableBody">
                            <c:choose>
                                <c:when test="${not empty listBanners}">
                                    <c:forEach var="b" items="${listBanners}">
                                        <tr>
                                            <td>#${b.id}</td>
                                            <td>
                                                <img src="${pageContext.request.contextPath}${b.imageUrl}"
                                                     alt="Banner"
                                                     style="height: 60px; width: 100px; border-radius: 4px; object-fit: cover; border: 1px solid #ddd;">
                                            </td>
                                            <td>
                                                <strong>${b.title}</strong>
                                            </td>
                                            <td>
                                                <div style="display: flex; align-items: center; gap: 10px;">
                                                    <label class="switch">
                                                        <input type="checkbox"
                                                               id="switch_${b.id}"
                                                            ${b.status ? 'checked' : ''}
                                                               onchange="toggleBannerStatus(${b.id}, this)">
                                                        <span class="slider round"></span>
                                                    </label>

                                                    <span id="status_text_${b.id}" style="font-size: 0.9em; font-weight: 500;">
                                                            ${b.status ? '<span style="color: #28a745;">Hiển thị</span>' : '<span style="color: #6c757d;">Đang ẩn</span>'}
                                                    </span>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="4" style="text-align: center; padding: 20px;">Không có banner nào</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

        </section>
    </main>
</div>

<%-- INCLUDE FOOTER CHUNG --%>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script src="${pageContext.request.contextPath}/assets/js/admin.js"></script>
<script>
    // Hàm xử lý bật tắt banner
    function toggleBannerStatus(bannerId, checkbox) {
        // 1. Lấy trạng thái mới (Checked = true, Unchecked = false)
        const newStatus = checkbox.checked;
        const statusTextSpan = document.getElementById('status_text_' + bannerId);
        // 2. Cập nhật giao diện ngay lập tức
        if (newStatus) {
            statusTextSpan.innerHTML = '<span style="color: #28a745;">Đang xử lý...</span>';
        } else {
            statusTextSpan.innerHTML = '<span style="color: #6c757d;">Đang xử lý...</span>';
        }
        // 3. Gửi dữ liệu về Server
        fetch('${pageContext.request.contextPath}/admin/api/banner/status', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
            },
            body: 'id=' + bannerId + '&status=' + newStatus
        })
            .then(response => {
                if (response.ok) {
                    // Thành công
                    console.log('Update banner #' + bannerId + ' success');
                    if (newStatus) {
                        statusTextSpan.innerHTML = '<span style="color: #28a745;">Hiển thị</span>';
                    } else {
                        statusTextSpan.innerHTML = '<span style="color: #6c757d;">Đang ẩn</span>';
                    }
                } else {
                    throw new Error('Server error');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Lỗi: Không thể cập nhật trạng thái. Vui lòng thử lại!');
                // Hoàn tác lại nút bấm nếu lỗi
                checkbox.checked = !newStatus;
                if (!newStatus) {
                    statusTextSpan.innerHTML = '<span style="color: #6c757d;">Đang ẩn</span>';
                } else {
                    statusTextSpan.innerHTML = '<span style="color: #28a745;">Hiển thị</span>';
                }
            });
    }

</script>
</body>
</html>