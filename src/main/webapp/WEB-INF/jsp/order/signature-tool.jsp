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
                        <th>Trạng thái đơn</th>
                        <th>Tình trạng ký</th>
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
                                    <c:when test="${order.orderStatus == 'SHIPPING'}">
                                        <span class="badge badge-pending">Đang giao</span>
                                    </c:when>
                                    <c:when test="${order.orderStatus == 'CANCELLED'}">
                                        <span class="badge badge-cancelled">Đã huỷ</span>
                                    </c:when>
                                    <c:when test="${order.orderStatus == 'DELIVERED'}">
                                        <span class="badge badge-confirmed">Đã giao</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge badge-pending">${order.orderStatus}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="hash-cell">
                                <c:choose>
                                    <c:when test="${not empty order.orderHash and not empty order.keyId}">
                                        <span style="color: green;">Sẵn sàng ký</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color: red;">Thiếu hash/key</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty order.orderHash and not empty order.keyId}">
                                        <a href="${pageContext.request.contextPath}/signature-tool?orderId=${order.id}#sign-section"
                                           class="btn-select">Chọn ký
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="btn-unselect">Không thể ký</span>
                                    </c:otherwise>
                                </c:choose>
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
            <h2 id="sign-section">KÝ ĐƠN HÀNG</h2>
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
                        <p><strong>Dữ liệu ký:</strong> Đơn hàng đã có hash, sẵn sàng ký.</p>
                    </div>
                </div>

                <form action="${pageContext.request.contextPath}/sign-order"
                      method="post">

                    <input type="hidden" name="orderId" value="${selectedOrder.id}"/>

                    <p>
                        <strong>Mã xác thực đơn hàng:</strong>
                        <span style="color: green;">Đã sẵn sàng</span>
                    </p>

                    <input type="hidden" id="orderHash" value="${selectedOrder.orderHash}"/>

                    <button type="button" onclick="copyOrderHash()">
                        Copy mã xác thực để ký
                    </button>
                    <p>
                        <a href="${pageContext.request.contextPath}/assets/tools/SignTool.jar" download>
                            Tải tool ký
                        </a>
                    </p>

                    <p><strong>Dán chữ ký từ tool:</strong></p>
                    <textarea name="signatureBase64" rows="5" cols="100" required
                              placeholder="Dán chữ ký Base64 được tạo từ tool ký"></textarea>

                    <br><br>
                    <button type="submit">Hoàn tất xác minh</button>
                </form>

                <script>
                    function copyOrderHash() {
                        const hashInput = document.getElementById("orderHash");

                        if (!hashInput || !hashInput.value) {
                            alert("Đơn hàng chưa có mã xác thực.");
                            return;
                        }
                        navigator.clipboard.writeText(hashInput.value)
                            .then(function () {
                                alert("Đã copy mã xác thực đơn hàng. Hãy dán vào tool ký offline.");
                            })
                            .catch(function () {
                                alert("Không thể copy mã xác thực. Vui lòng thử lại.");
                            });
                    }
                </script>
            </c:otherwise>
        </c:choose>
    </div>

</main>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>

</body>
</html>
