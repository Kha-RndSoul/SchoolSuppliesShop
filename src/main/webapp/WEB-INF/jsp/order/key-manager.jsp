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
    <h1 class="section-title"> Quản lý khóa điện tử</h1>

    <%-- Thông báo từ redirect --%>
    <c:if test="${not empty param.success}">
        <div class="alert alert-success">${param.success}</div>
    </c:if>
    <c:if test="${not empty param.error}">
        <div class="alert alert-error">${param.error}</div>
    </c:if>

    <%-- Thẻ khóa đang sử dụng --%>
    <div class="key-card ${activeKey == null ? 'inactive' : (activeKey.reportedLostAt != null ? 'lost' : '')}">
        <h2>Khóa đang sử dụng</h2>

        <c:choose>
            <c:when test="${activeKey == null}">
                <p style="color: var(--text-light); margin: 1rem 0;">
                    Bạn chưa có khóa. Hãy tạo khóa bằng tool offline rồi upload lên.
                </p>
            </c:when>
            <c:otherwise>
                <table style="margin-top: 1rem; border-collapse: collapse; width: 100%;">
                    <tr>
                        <td style="padding: 0.5rem 1rem 0.5rem 0; color: var(--text-light);">Tên khóa:</td>
                        <td>
                            PublicKey_<fmt:formatDate value="${activeKey.createdAt}" pattern="ddMMyyyy_HHmmss"/>
                        </td>
                    </tr>
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
                <p> Bạn cần upload khóa để có thể ký đơn hàng.</p>
            </div>
        </c:if>
    </div>

    <%-- Thẻ cập nhật khóa --%>
    <div class="key-card">
        <h2>Cập nhật khóa</h2>
        <div class="warning-box">
            <p>📌 <strong>Lưu ý:</strong>
                Dùng tool offline để tạo cặp key. Sau đó upload file PublicKey lên đây.
                Update khóa mới sẽ vô hiệu hóa khóa cũ.
            </p>
        </div>

        <div class="key-actions">
            <%-- Nút báo mất chỉ hiện khi có key active và chưa báo mất --%>
            <c:if test="${activeKey != null && activeKey.reportedLostAt == null}">
                <form action="${pageContext.request.contextPath}/key" method="POST"
                      onsubmit="return confirm('Xác nhận báo mất khóa ');">
                    <input type="hidden" name="action" value="reportLost"/>
                    <input type="hidden" name="keyId" value="${activeKey.id}"/>
                    <button type="submit" class="btn-report">️ Báo mất khóa</button>
                </form>
            </c:if>
        </div>

        <%-- Form upload public key --%>
        <div class="upload-section">
            <h3> Update Public Key</h3>
            <form action="${pageContext.request.contextPath}/key"
                  method="POST" enctype="multipart/form-data" class="upload-form">
                <input type="hidden" name="action" value="upload"/>
                <input type="file" name="publicKeyFile" accept=".key" required/>
                <button type="submit" class="btn-upload">Update</button>
            </form>
        </div>
    </div>
    <div style="margin-top: 20px;">
        <a href="${pageContext.request.contextPath}/assets/tools/SignTool.jar" download>
            Tải công cụ ký đơn hàng tại đây
        </a>
    </div>
</main>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
</body>
</html>