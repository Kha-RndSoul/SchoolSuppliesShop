<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <title>Thông tin khách hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/checkout.css">
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

<main class="container">
    <div class="checkout-card">
        <form id="checkoutForm" action="${pageContext.request.contextPath}/checkout" method="POST" novalidate>
            <h2>Thông tin khách hàng</h2>

            <label class="field">Tên
                <input id="name" name="name" required placeholder="Họ và tên" value="${sessionScope.user.name}">
            </label>

            <label class="field">Số điện thoại
                <input id="phone" name="phone" required placeholder="0912345678" value="${sessionScope.user.phone}">
            </label>

            <label class="field">Email (tuỳ chọn)
                <input id="email" name="email" type="email" placeholder="you@example.com" value="${sessionScope.user.email}">
            </label>

            <label class="field">Địa chỉ giao hàng
                <textarea id="address" name="address" required placeholder="Số nhà, đường, phường, quận">${sessionScope.user.address}</textarea>
            </label>

            <h3>Vận chuyển</h3>
            <label class="radio-inline">
                <input type="radio" name="shipping" value="standard" checked> Bình thường (25.000₫)
            </label>
            <label class="radio-inline">
                <input type="radio" name="shipping" value="express"> Hỏa tốc (50.000₫)
            </label>

            <h3>Phương thức thanh toán</h3>
            <label>
                <input type="radio" name="payment" value="cod" checked> Thanh toán khi nhận hàng (COD)
            </label>
            <label>
                <input type="radio" name="payment" value="online"> Chuyển khoản / Online
            </label>

            <div class="totals">
                <div>Phí hàng: <span id="subtotal">
                    <fmt:formatNumber value="${cartTotal}" type="number" groupingUsed="true"/>₫
                </span></div>
                <div>Phí vận chuyển: <span id="shippingFee">25.000₫</span></div>
                <div class="total-line">Tổng:
                    <strong id="total">
                        <fmt:formatNumber value="${cartTotal + 25000}" type="number" groupingUsed="true"/>₫
                    </strong>
                </div>
            </div>

            <div id="checkoutError" role="alert" class="form-error" aria-live="polite">
                <c:if test="${not empty errorMessage}">
                    ${errorMessage}
                </c:if>
            </div>

            <div class="form-actions">
                <button id="placeOrderBtn" type="submit" class="btn-primary">Đặt hàng</button>
            </div>
        </form>
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

<script src="${pageContext.request.contextPath}/assets/js/checkout.js"></script>
</body>
</html>
