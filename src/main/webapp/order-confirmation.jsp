<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Xác nhận đơn hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/order-confirmation.css">
</head>
<body>
<header class="header">
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/index" class="logo">
            <span>DPK Shop</span>
        </a>

        <form action="${pageContext.request.contextPath}/products" method="GET" class="search-bar">
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
                    <span>Đăng nhập/Đăng ký</span>
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

<main class="wrap">
    <c:choose>
        <c:when test="${not empty order}">
            <div id="root" class="card">
                <h2>🎉 Đặt hàng thành công!</h2>
                <div class="order-info">
                    <p><strong>Mã đơn hàng:</strong> ${order.orderCode}</p>
                    <p><strong>Người nhận:</strong> ${order.shippingName}</p>
                    <p><strong>Số điện thoại:</strong> ${order.shippingPhone}</p>
                    <p><strong>Địa chỉ:</strong> ${order.shippingAddress}</p>
                    <p><strong>Phương thức thanh toán:</strong>
                        <c:choose>
                            <c:when test="${order.paymentMethod == 'cod'}">Thanh toán khi nhận hàng (COD)</c:when>
                            <c:otherwise>Chuyển khoản / Online</c:otherwise>
                        </c:choose>
                    </p>
                    <p><strong>Trạng thái:</strong> ${order.orderStatus}</p>
                    <p class="total"><strong>Tổng tiền:</strong>
                        <fmt:formatNumber value="${order.totalAmount}" type="number" groupingUsed="true"/>₫
                    </p>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div id="root" class="card">
                <p>Không tìm thấy thông tin đơn hàng.</p>
            </div>
        </c:otherwise>
    </c:choose>

    <div class="actions">
        <button id="printBtn" class="btn" onclick="window.print()">In hóa đơn</button>
        <a href="${pageContext.request.contextPath}/index" class="btn link">Về trang chủ</a>
    </div>
</main>

<footer class="footer">
    <div class="container">
        <div class="footer-content">
            <div class="footer-section">
                <h3>Giới thiệu</h3>
                <p>DPK Shop là cửa hàng chuyên cung cấp đồ dùng học tập chất lượng cao với giá cả hợp lý, phục vụ học sinh, sinh viên trên toàn quốc.</p>
                <a href="${pageContext.request.contextPath}/about.jsp" class="text-link">Về chúng tôi</a>
            </div>
            <div class="footer-section">
                <h3>Liên Hệ</h3>
                <p>📧 Email: contact@dpkshop.com</p>
                <p>📞 Hotline: 1900 5678</p>
                <p>📍 Địa chỉ: 123 Đường Học Tập, Phường 1, Quận 1, TP.HCM</p>
                <a href="${pageContext.request.contextPath}/contact.jsp" class="text-link">Chi tiết liên hệ</a>
            </div>
            <div class="footer-section">
                <h3>Hỗ Trợ</h3>
                <a href="#">Hướng dẫn mua hàng</a>
                <a href="#">Chính sách đổi trả</a>
                <a href="#">Vận chuyển</a>
                <a href="#">Thanh toán</a>
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
            <p>&copy; 2025 DPK Shop. Tất cả bản quyền được bảo lưu.</p>
        </div>
    </div>
</footer>

<script src="${pageContext.request.contextPath}/assets/js/order-confirmation.js"></script>
</body>
</html>
