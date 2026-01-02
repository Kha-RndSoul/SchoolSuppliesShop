<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<header class="header">
    <nav class="navbar">
        <!-- Logo -->
        <a href="${pageContext.request.contextPath}/" class="logo">
            <span>DPK Shop</span>
        </a>

        <!-- Search Bar -->
        <form action="${pageContext.request.contextPath}/products" method="GET" class="search-bar">
            <input type="text"
                   name="keyword"
                   placeholder="Tìm kiếm sản phẩm..."
                   value="${param.keyword}">
            <button type="submit" class="search-button">🔍</button>
        </form>

        <!-- Header Actions -->
        <div class="header-actions">
            <!-- Contact -->
            <a href="${pageContext.request.contextPath}/contact" class="action-item">
                <div class="action-text">
                    <button class="phone-button" type="button">📞</button>
                    <span>Liên hệ</span>
                </div>
            </a>

            <!-- Login/Profile -->
            <c:choose>
                <%-- Đã đăng nhập --%>
                <c:when test="${not empty sessionScope.customer}">
                    <a href="${pageContext.request.contextPath}/profile" class="action-item">
                        <div class="action-text">
                            <button class="user-button" type="button">👤</button>
                            <span>${sessionScope.customerName}</span>
                        </div>
                    </a>
                    <a href="${pageContext.request.contextPath}/logout" class="action-item"
                       onclick="return confirm('Bạn có chắc muốn đăng xuất?');">
                        <div class="action-text">
                            <button class="logout-button" type="button">🚪</button>
                            <span>Đăng xuất</span>
                        </div>
                    </a>
                </c:when>
                <%-- Chưa đăng nhập --%>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login" class="action-item">
                        <div class="action-text">
                            <button class="user-button" type="button">👤</button>
                            <span>Đăng nhập/Đăng ký</span>
                        </div>
                    </a>
                </c:otherwise>
            </c:choose>

            <!-- Shopping Cart -->
            <a href="${pageContext.request.contextPath}/cart" class="action-item">
                <div class="action-text">
                    <button class="cart-button" type="button">
                        🛒
                        <%-- Badge hiển thị số lượng items trong giỏ --%>
                        <c:if test="${not empty sessionScope.cartCount and sessionScope.cartCount > 0}">
                        <span class="cart-badge">${sessionScope.cartCount}</span>
                        </c:if>
                    </button>
                    <span>Giỏ hàng</span>
                </div>
            </a>
        </div>
    </nav>

    <!-- Category Navigation -->
    <div class="nav-row">
        <div class="container">
            <ul class="nav-links">

                    <c:forEach var="category" items="${applicationScope.categories}">
                        <li>
                            <a href="${pageContext.request.contextPath}/products?categoryId=${category.id}">
                                ${category.categoryName}
                            </a>
                        </li>
                    </c:forEach>

            </ul>
        </div>
    </div>
</header>