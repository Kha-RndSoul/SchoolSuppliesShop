<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <title>Quản lý khóa - DPK Shop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-common.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/key-manager.css"/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp"/>

<main class="container key-page">
    <h1 class="section-title">🔑 Quản lý khóa điện tử</h1>

    <%-- Thông báo từ redirect --%>
    <c:if test="${not empty param.success}">
        <div class="alert alert-success"> ${param.success}</div>
    </c:if>
    <c:if test="${not empty param.error}">
        <div class="alert alert-error"> ${param.error}</div>
    </c:if>
    <%-- Thông báo khi tạo khóa lần đầu --%>
    <c:if test="${newKeyActivated}">
        <div class="alert alert-success">
            <strong>Khóa mới đã được tạo và kích hoạt thành công!</strong>
            2 file khóa đã được tải về máy của bạn. Hãy bảo quản file
            PrivateKey thật cẩn thận.
        </div>
    </c:if>

    <%-- Cảnh báo khi vừa tạo key mới trong khi đang có key cũ --%>
    <c:if test="${hasPendingKey}">
        <div class="pending-box">
            <p>
                Khóa cũ vẫn đang hoạt động. Để kích hoạt khóa mới và vô hiệu hóa khóa cũ,
                hãy upload file vừa tải về lên bên dưới.
            </p>
        </div>
    </c:if>

    <%-- Thẻ khóa đang sử dụng --%>
    <div class="key-card ${activeKey == null ? 'inactive' : (activeKey.reportedLostAt != null ? 'lost' : '')}">
        <h2>Khóa đang sử dụng</h2>

        <c:choose>
            <c:when test="${activeKey == null}">
                <p style="color: var(--text-light); margin: 1rem 0;">
                    Bạn chưa có khóa. Hãy tạo khóa để ký đơn hàng.
                </p>
            </c:when>
            <c:otherwise>
                <table style="margin-top: 1rem; border-collapse: collapse; width: 100%;">
                    <tr>
                        <td style="padding: 0.5rem 1rem 0.5rem 0; color: var(--text-light); width: 150px;">Trạng thái:</td>
                        <td>
                            <c:choose>
                                <c:when test="${activeKey.reportedLostAt != null}">
                                    <span class="key-status status-lost"> Đã báo mất</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="key-status status-active"> Đang hoạt động</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td style="padding: 0.5rem 1rem 0.5rem 0; color: var(--text-light);">Nguồn gốc:</td>
                        <td>${activeKey.source == 'GENERATED' ? ' Tạo trên web' : ' Upload'}</td>
                    </tr>
                    <tr>
                        <td style="padding: 0.5rem 1rem 0.5rem 0; color: var(--text-light);">Tạo lúc:</td>
                        <td><fmt:formatDate value="${activeKey.createdAt}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
                    </tr>
                    <c:if test="${activeKey.reportedLostAt != null}">
                        <tr>
                            <td style="padding: 0.5rem 1rem 0.5rem 0; color: var(--text-light);">Báo mất lúc:</td>
                            <td style="color: #ef4444;">
                                <fmt:formatDate value="${activeKey.reportedLostAt}" pattern="dd/MM/yyyy HH:mm:ss"/>
                            </td>
                        </tr>
                    </c:if>
                </table>
            </c:otherwise>
        </c:choose>

        <c:if test="${activeKey == null || activeKey.reportedLostAt != null}">
            <div class="warning-box" style="margin-top: 1rem;">
                <p> Bạn cần tạo khóa để có thể ký đơn hàng.</p>
            </div>
        </c:if>
    </div>
</main>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
</body>
</html>