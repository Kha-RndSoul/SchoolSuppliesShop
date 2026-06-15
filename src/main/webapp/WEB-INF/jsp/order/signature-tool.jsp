<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Ký đơn hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/signature-tool.css">
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/header.jsp"/>

<main class="wrap">

    <h1 class="page-title">Ký đơn hàng</h1>
    <div class="page-links">
        <a href="${pageContext.request.contextPath}/home"> về trang chủ</a>
        <a href="${pageContext.request.contextPath}/key">Quản lý khóa</a>
    </div>

    <%-- Thông báo --%>
    <c:if test="${not empty success}">
        <div class="alert alert-success">✓ ${success}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-error">✕ ${error}</div>
    </c:if>

    <%-- Danh sách đơn chưa ký --%>
    <div class="card">
        <div class="card-title">Danh sách đơn chưa ký</div>

        <c:choose>
            <c:when test="${empty unsignedOrders}">
                <p class="empty-text">Không có đơn hàng nào cần ký.</p>
            </c:when>
            <c:otherwise>
                <table class="order-table">
                    <thead>
                    <tr>
                        <th>Mã đơn</th>
                        <th>Ngày tạo</th>
                        <th>Tổng tiền</th>
                        <th>Trạng thái</th>
                        <th>Hash</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="order" items="${unsignedOrders}">
                        <tr>
                            <td><strong>${order.orderCode}</strong></td>
                            <td>
                                <fmt:formatDate value="${order.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
                            </td>
                            <td>
                                <fmt:formatNumber value="${order.totalAmount}" type="number" groupingUsed="true"/>₫
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${order.orderStatus == 'PENDING'}">
                                        <span class="badge badge-pending">Chờ xử lý</span>
                                    </c:when>
                                    <c:when test="${order.orderStatus == 'CONFIRMED'}">
                                        <span class="badge badge-confirmed">Đã xác nhận</span>
                                    </c:when>
                                    <c:when test="${order.orderStatus == 'CANCELLED'}">
                                        <span class="badge badge-cancelled">Đã huỷ</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge badge-pending">${order.orderStatus}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="hash-cell">${order.orderHash}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/signature-tool?orderId=${order.id}#sign-section"
                                    class="btn-select">Chọn ký
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>

    <%-- Form ký --%>
    <div class="card">
        <div class="card-title">
            <h2 id="sign-section">Ký đơn hàng</h2>
        </div>

        <c:choose>
            <c:when test="${empty selectedOrder}">
                <p class="placeholder-text">Chọn một đơn trong danh sách phía trên để tiến hành ký.</p>
            </c:when>
            <c:otherwise>
                <div class="sign-info">
                    <div class="sign-info-row">
                        <strong>Mã đơn:</strong>
                        <span>${selectedOrder.orderCode}</span>
                    </div>
                    <div class="sign-info-row">
                        <strong>Order hash:</strong>
                        <span class="hash-value">${selectedOrder.orderHash}</span>
                    </div>
                </div>

                <form action="${pageContext.request.contextPath}/sign-order"
                      method="post"
                      enctype="multipart/form-data">

                    <input type="hidden" name="orderId" value="${selectedOrder.id}"/>

                    <label class="file-upload-label" for="privateKeyFile">
                        Upload file private key (.key)
                    </label>
                    <p class="file-upload-hint">Chọn file private key đã tải về khi tạo khóa.</p>
                    <input type="file" id="privateKeyFile" name="privateKeyFile" accept=".key" required>

                    <button type="submit" class="btn-sign">Ký đơn hàng</button>
                </form>
            </c:otherwise>
        </c:choose>
    </div>

</main>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>

</body>
</html>
