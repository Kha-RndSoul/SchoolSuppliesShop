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

        <!-- PRODUCTS SECTION -->
        <section id="products-section" class="admin-section">
            <div class="section-header">
                <h2>Quản Lý Sản Phẩm</h2>
                <div class="section-actions">
                    <button class="btn-add-new" onclick="toggleProductForm()">Thêm Sản Phẩm</button>
                </div>
            </div>

            <!-- Product Form -->
            <div class="product-form-container" id="productFormContainer" style="display: none;">
                <h3>Thêm Sản Phẩm Mới</h3>
                <form class="product-form" id="productForm">
                    <div class="form-row">
                        <div class="form-group">
                            <label>Tên sản phẩm *</label>
                            <input type="text" name="productName" placeholder="Nhập tên sản phẩm" required>
                        </div>

                        <div class="form-group">
                            <label>Danh mục *</label>
                            <select name="categoryId" required>
                                <option value="">-- Chọn danh mục --</option>
                                <option value="1">Văn phòng phẩm</option>
                                <option value="2">Sách vở</option>
                                <option value="3">Đồ dùng vẽ</option>
                                <option value="4">Balo & Cặp</option>
                                <option value="5">Máy tính</option>
                                <option value="6">Đèn học</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label>Giá bán (đ) *</label>
                            <input type="number" name="price" placeholder="0" min="0" required>
                        </div>

                        <div class="form-group">
                            <label>Số lượng tồn kho *</label>
                            <input type="number" name="stock" placeholder="0" min="0" required>
                        </div>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn-primary">Lưu Sản Phẩm</button>
                        <button type="button" class="btn-secondary" onclick="toggleProductForm()">Hủy</button>
                    </div>
                </form>
            </div>

            <!-- Products Table -->
            <div class="dashboard-widget">
                <div class="widget-header">
                    <h3 class="widget-title">Danh Sách Sản Phẩm</h3>
                </div>
                <div class="table-responsive">
                    <table class="admin-table">
                        <thead>
                        <tr>
                            <th>Mã SP</th>
                            <th>Tên Sản Phẩm</th>
                            <th>Danh Mục</th>
                            <th>Giá Bán</th>
                            <th>Tồn Kho</th>
                            <th>Hành Động</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td><span class="order-code">#SP001</span></td>
                            <td><strong>Bút bi Thiên Long TL-027</strong></td>
                            <td>Văn phòng phẩm</td>
                            <td><strong>5,000đ</strong></td>
                            <td><span class="stock-badge high">2,500</span></td>
                            <td>
                                <button class="btn-edit" title="Sửa">Sửa</button>
                                <button class="btn-delete" title="Xóa">Xóa</button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </section>

        <!-- OTHER SECTIONS (Placeholder) -->
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
                <h2>Cài đặt</h2>
            </div>
            <h3>Cài đặt thông tin admin</h3>
            <div class="banner-config">
                <div class="dashboard-widget">
                    <div class="widget-header">
                        <h3 class="widget-title">Cấu hình Banner</h3>
                    </div>
                    <div class="table-responsive">
                        <table class="admin-table banner-table">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Tiêu đề</th>
                                <th>Hiển thị</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr class="banner-row" data-id="1">
                                <td>#1</td>
                                <td>Khuyến mãi 1</td>
                                <td>
                                    <label class="switch">
                                        <input type="checkbox" class="banner-switch" data-id="1">
                                        <span class="slider"></span>
                                    </label>
                                </td>
                            </tr>
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
</body>
</html>