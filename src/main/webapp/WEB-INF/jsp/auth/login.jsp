<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--@elvariable id="success" type="java.lang.String"--%>
<%--@elvariable id="error" type="java.lang.String"--%>
<%--@elvariable id="email" type="java.lang.String"--%>

<! doctype html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập - DPK Shop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-login.css">
</head>
<body>

<%-- INCLUDE HEADER --%>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>

<main class="container">
    <section class="card" aria-labelledby="login-title">
        <h1 id="login-title">Đăng nhập</h1>

        <%-- Success message (từ register chuyển sang) --%>
        <c:if test="${not empty sessionScope.success}">
            <div class="alert alert-success">
                 ${sessionScope.success}
            </div>
            <%-- Remove success message sau khi hiển thị --%>
            <c:remove var="success" scope="session" />
        </c:if>

        <%-- Error message --%>
        <c:if test="${not empty error}">
            <div class="alert alert-error">
                 ${error}
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/login" method="post" novalidate>
            <div class="form-field">
                <label for="email">Email</label>
                <input id="email"
                       name="email"
                       type="email"
                       placeholder="you@example.com"
                       value="${email}"
                       required
                       autofocus />
            </div>

            <div class="form-field">
                <label for="password">Mật khẩu</label>
                <input id="password"
                       name="password"
                       type="password"
                       placeholder="********"
                       required />
            </div>

            <div class="helper-line">
                <label style="display: inline-flex; align-items: center; gap: 0.5rem; cursor: pointer;">
                    <input type="checkbox" name="remember" style="cursor: pointer;" />
                    <span>Ghi nhớ đăng nhập</span>
                </label>
            </div>

            <div class="row-center">
                <button type="submit" class="btn btn-primary">Đăng nhập</button>
            </div>

            <div class="signup-line">
                Bạn chưa có tài khoản?
                <a href="${pageContext.request.contextPath}/register" class="text-link">Đăng ký tại đây</a>
            </div>

            <div style="margin-top: 1.5rem;">
                <div class="social-row">
                    <button type="button" class="social-btn social-google" aria-label="Sign in with Google">
                        <span>🔍</span> Google
                    </button>
                    <button type="button" class="social-btn social-facebook" aria-label="Sign in with Facebook">
                        <span>📘</span> Facebook
                    </button>
                </div>
            </div>
        </form>
    </section>
</main>

<%-- INCLUDE FOOTER --%>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>

</body>
</html>