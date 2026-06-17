<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!doctype html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý tài khoản - DPK Shop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-common.css">

    <style>
        /* --- PAGE SPECIFIC STYLES -- */

        .profile-page-wrapper {
            padding: 40px 0;
            background-color: #f8f9fa;
            min-height: calc(100vh - 200px);
        }
        .profile-heading {
            text-align: center;
            margin-bottom: 35px;
            font-weight: 700;
        }

        /* Layout Grid h */
        .profile-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
            gap: 30px;
            margin-bottom: 30px;
        }

        /* Card Style - Khối chứa nội dung */
        .profile-card {
            background: #fff;
            padding: 30px;
            border-radius: 8px;
            border: 1px solid #eee;
            height: 100%;
        }
        .profile-card h3 {
            margin-bottom: 25px;
            padding-bottom: 15px;
            border-bottom: 2px solid #f5f5f5;
            color: #444;
            font-size: 1.2rem;
            font-weight: 600;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        /* Form Elements  */
        .form-group {
            margin-bottom: 20px;
        }
        .form-label {
            display: block;
            margin-bottom: 8px;
            font-weight: 600;
            color: #555;
            font-size: 0.95rem;
        }
        .form-control-styled {
            width: 100%;
            padding: 12px 15px;
            border: 1px solid #ddd;
            border-radius: 6px;
            font-size: 1rem;
            transition: border-color 0.3s, box-shadow 0.3s;
            font-family: inherit;
        }
        .form-control-styled:focus {
            outline: none;
            border-color: var(--primary-color);
            box-shadow: 0 0 0 3px rgba(215, 0, 24, 0.1);
        }
        textarea.form-control-styled {
            resize: vertical;
        }

        /* Nút bấm: Sử dụng màu đỏ chủ đạo của DPK Shop */
        .btn-brand {
            background-color: var(--primary-color);
            color: #fff;
            border: none;
            padding: 12px 25px;
            border-radius: 6px;
            font-weight: 600;
            font-size: 1rem;
            cursor: pointer;
            transition: background-color 0.2s ease-in-out;
        }

        /* Thông báo lỗi/thành công */
        .alert {
            padding: 12px 15px;
            border-radius: 6px;
            margin-bottom: 20px;
            font-size: 0.95rem;
        }
        .alert-success { background-color: #e6f4ea; color: #1e7e34; border: 1px solid #c3e6cb; }
        .alert-danger { background-color: #fbeaea; color: #c53030; border: 1px solid #f5c6cb; }

        /* --- ORDER HISTORY TABLE --- */
        .order-history-section {
            margin-top: 40px;
        }
        /* Đảm bảo bảng có thể cuộn ngang trên màn hình nhỏ */
        .table-responsive {
            overflow-x: auto;
            border-radius: 8px;
            border: 1px solid #eee;
        }

        .profile-table {
            width: 100%;
            border-collapse: collapse;
            background: #fff;
        }

        .profile-table th {
            background: #f8f9fa;
            color: #666;
            font-weight: 600;
            padding: 15px;
            text-align: left;
            border-bottom: 2px solid #eee;
            white-space: nowrap;
        }

        .profile-table td {
            padding: 15px;
            border-bottom: 1px solid #eee;
            vertical-align: middle;
            color: #444;
        }
        .profile-table tr:last-child td { border-bottom: none; }

        /* Highlight mã đơn và giá tiền */
        .text-primary-brand { color:var(--primary-color); font-weight: 700; }
        .order-code-link { color: #333; font-weight: 600; text-decoration: none; transition: color 0.2s;}
        .order-code-link:hover { color: var(--primary-color); text-decoration: underline;}
        /* Badge trạng thái đơn hàng  */
        .status-badge {
            padding: 6px 12px;
            border-radius: 30px;
            font-size: 0.85rem;
            font-weight: 600;
            display: inline-block;
            letter-spacing: 0.5px;
        }
        /* Màu sắc dựa trên trạng thái, có viền nhẹ để nổi bật */
        .status-PENDING { background: #fff7e6; color: #b76e00; border: 1px solid #ffe0b2;}
        .status-CONFIRMED { background: #e6f7ff; color: #0050b3; border: 1px solid #91d5ff;}
        .status-SHIPPING { background: #f6ffed; color: #389e0d; border: 1px solid #b7eb8f;}
        .status-CANCELLED { background: #fff1f0; color: #cf1322; border: 1px solid #ffa39e;}

        .empty-state {
            text-align: center;
            padding: 50px 20px;
            color: #888;
        }
        .empty-state a { color: #d70018; font-weight: 600; }

        /* Badge xác minh chữ ký số */
        .verify-badge {
            padding: 6px 12px;
            border-radius: 4px;
            font-size: 0.85rem;
            font-weight: 600;
            display: inline-block;
        }
        .verify-VALID { background: #e6f4ea; color: #1e7e34; border: 1px solid #c3e6cb; }
        .verify-NOT_YET { background: #f8f9fa; color: #666; border: 1px solid #ddd; }
        .verify-INVALID { background: #fbeaea; color: #c53030; border: 1px solid #f5c6cb; font-weight: 700; }

        /* CSS để đổi toàn bộ dòng sang màu đỏ chớp nháy khi phát hiện gian lận dữ liệu */
        .row-fraud-warning {
            background-color: #fff1f0 !important;
            animation: pulse-danger 2s infinite;
        }
        .row-fraud-warning td {
            color: #cf1322 !important;
            border-bottom: 1px solid #ffa39e !important;
        }

        @keyframes pulse-danger {
            0% { background-color: #fff1f0; }
            50% { background-color: #ffccc7; }
            100% { background-color: #fff1f0; }
        }

        /* Nút xử lý ký số nhỏ gọn trong bảng */
        .btn-sign-action {
            background-color: #2f54eb;
            color: white;
            border: none;
            padding: 6px 12px;
            border-radius: 4px;
            font-size: 0.85rem;
            font-weight: 600;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            transition: background 0.2s;
        }
        .btn-sign-action:hover {
            background-color: #1d39c4;
            color: white;
        }

        /* CSS Phong cách cho các nút bấm phân trang tương tự Admin */
        .pagination-controls {
            margin-top: 25px;
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 5px;
        }
        .pagination-btn {
            padding: 6px 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            background: #fff;
            color: #333;
            text-decoration: none;
            font-size: 0.9rem;
            font-weight: 500;
            transition: all 0.2s ease;
            cursor: pointer;
            display: inline-block;
        }
        .pagination-btn:hover:not(.disabled):not(.active) {
            background-color: #f5f5f5;
            border-color: #ccc;
        }
        .pagination-btn.active {
            background-color: var(--primary-color, #d70018);
            border-color: var(--primary-color, #d70018);
            color: #fff;
            font-weight: bold;
            cursor: default;
        }
        .pagination-btn.disabled {
            border-color: #eee;
            color: #ccc;
            background: #fafafa;
            cursor: not-allowed;
        }
        .pagination-dots {
            padding: 6px 8px;
            color: #999;
            cursor: default;
            font-weight: 500;
        }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />

<div class="profile-page-wrapper" id="orders-section">
    <main class="container">
        <h1 class="profile-heading">Quản lý tài khoản</h1>

        <c:if test="${not empty successMessage}">
            <div class="alert alert-success">${successMessage}</div>
            <c:remove var="successMessage" scope="session"/>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
            <c:remove var="errorMessage" scope="session"/>
        </c:if>

        <div class="profile-grid">
            <div class="profile-card">
                <h3>
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-user"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path><circle cx="12" cy="7" r="4"></circle></svg>
                    Thông tin cá nhân
                </h3>

                <c:if test="${not empty success}">
                    <div class="alert alert-success">${success}</div>
                </c:if>

                <form action="profile" method="POST">
                    <input type="hidden" name="formType" value="updateProfile">
                    <div class="form-group">
                        <label class="form-label">Họ và tên</label>
                        <input type="text" name="fullName" value="${customer.fullName}" required class="form-control-styled" placeholder="Nhập họ tên của bạn">
                    </div>
                    <div class="form-group">
                        <label class="form-label">Email (Tên đăng nhập)</label>
                        <input type="email" name="email" value="${customer.email}" required class="form-control-styled" readonly style="background: #f9f9f9; cursor: not-allowed;" title="Không thể thay đổi email">
                    </div>
                    <div class="form-group">
                        <label class="form-label">Số điện thoại</label>
                        <input type="text" name="phone" value="${customer.phone}" class="form-control-styled" placeholder="Nhập số điện thoại">
                    </div>
                    <div class="form-group">
                        <label class="form-label">Địa chỉ giao hàng mặc định</label>
                        <textarea name="address" rows="3" class="form-control-styled" placeholder="Nhập địa chỉ giao hàng">${customer.address}</textarea>
                    </div>
                    <div style="text-align: right;">
                        <button type="submit" class="btn-brand">Lưu thay đổi</button>
                    </div>
                </form>
            </div>

            <div class="profile-card">
                <h3>
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-lock"><rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect><path d="M7 11V7a5 5 0 0 1 10 0v4"></path></svg>
                    Bảo mật & Mật khẩu
                </h3>

                <c:if test="${not empty successPassword}">
                    <div class="alert alert-success">${successPassword}</div>
                </c:if>
                <c:if test="${not empty errorPassword}">
                    <div class="alert alert-danger">${errorPassword}</div>
                </c:if>

                <form action="profile" method="POST" autocomplete="off">
                    <input type="hidden" name="formType" value="changePassword">
                    <div class="form-group">
                        <label class="form-label">Mật khẩu hiện tại</label>
                        <input type="password" name="currentPassword" required class="form-control-styled" placeholder="••••••••">
                    </div>
                    <div class="form-group">
                        <label class="form-label">Mật khẩu mới</label>
                        <input type="password" name="newPassword" required class="form-control-styled" placeholder="Tối thiểu 8 ký tự">
                    </div>
                    <div style="text-align: right;">
                        <button type="submit" class="btn-brand">Cập nhật mật khẩu</button>
                    </div>
                </form>
            </div>
        </div>

        <div class="profile-card order-history-section">
            <h3>
                Đơn hàng của tôi :
            </h3>

            <c:choose>
                <c:when test="${not empty orderHistory}">
                    <div class="table-responsive">
                        <table class="profile-table">
                            <thead>
                            <tr>
                                <th>Mã đơn hàng</th>
                                <th>Ngày đặt</th>
                                <th>Tổng tiền</th>
                                <th>Trạng thái</th>
                                <th>Khóa áp dụng</th>
                                <th>Xác minh chữ ký</th>
                                <th>Hành động</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="order" items="${orderHistory}">
                                <tr class="${order.is_verified == -1 ? 'row-fraud-warning' : ''}">
                                    <td>
                                        <a href="javascript:void(0)"
                                           class="order-code-link"
                                           data-hash="${order.order_hash}"
                                           onclick="copyOrderHash(this)"
                                           style="cursor: pointer;"
                                           title="Click để copy nhanh Mã xác thực (Order Hash)">
                                            #${order.order_code} <span style="font-size: 0.8rem; color: #999; font-weight: normal; margin-left: 2px;"></span>
                                        </a>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${order.created_at}" pattern="dd/MM/yyyy"/><br>
                                        <small style="color: #999;"><fmt:formatDate value="${order.created_at}" pattern="HH:mm"/></small>
                                    </td>
                                    <td class="text-primary-brand">
                                        <fmt:formatNumber value="${order.total_amount}" type="number"/>₫
                                    </td>
                                    <td>
                                        <span class="status-badge status-${order.order_status}">${order.order_status}</span>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty order.key_created_at}">
                                                <fmt:parseDate value="${order.key_created_at}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedKeyDate" type="both"/>

                                                <span class="key-badge" style="font-family: monospace; font-size: 0.85rem; background: #f3f4f6; padding: 4px 8px; border-radius: 4px; color: #374151; font-weight: 500; border: 1px solid #e5e7eb;" title="Khóa được sử dụng để ký xác thực đơn hàng này">
                                                    PublicKey_<fmt:formatDate value="${parsedKeyDate}" pattern="ddMMyyyy_HHmmss"/>
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span style="color: #999; font-style: italic; font-size: 0.9rem;">Chưa gắn khóa</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>

                                    <td>
                                        <c:choose>
                                            <c:when test="${order.is_verified == 1}"><span class="verify-badge verify-VALID">Đã xác minh</span></c:when>
                                            <c:when test="${order.is_verified == -1}"><span class="verify-badge verify-INVALID">⚠️ Bị chỉnh sửa!</span></c:when>
                                            <c:otherwise><span class="verify-badge verify-NOT_YET">Chưa ký</span></c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <%-- Nếu đơn hàng bị hủy, xuất hiện nút đặt lại đơn --%>
                                            <c:when test="${order.order_status == 'CANCELLED'}">
                                                <button onclick="recreateOrder('${order.id}')" class="btn-sign-action" style="background-color: #28a745;">
                                                    Đặt lại đơn
                                                </button>
                                            </c:when>

                                            <c:when test="${order.is_verified == null || order.is_verified == 0}">
                                                <button onclick="openSignModal('${order.order_code}', '${order.id}')" class="btn-sign-action">
                                                    Dán chữ ký
                                                </button>
                                            </c:when>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <c:if test="${totalPages > 1}">
                        <div class="pagination-controls">
                            <c:choose>
                                <c:when test="${currentPage == 1}">
                                    <span class="pagination-btn disabled">◀ Trước</span>
                                </c:when>
                                <c:otherwise>
                                    <a href="?page=${currentPage - 1}" class="pagination-btn">◀ Trước</a>
                                </c:otherwise>
                            </c:choose>

                            <c:forEach var="i" begin="1" end="${totalPages}">
                                <c:choose>
                                    <%-- Điều kiện hiển thị: Trang đầu, trang cuối, và khoảng xung quanh trang hiện tại (i >= currentPage - 2 và i <= currentPage + 2) --%>
                                    <c:when test="${i == 1 || i == totalPages || (i >= currentPage - 2 && i <= currentPage + 2)}">
                                        <c:choose>
                                            <c:when test="${i == currentPage}">
                                                <span class="pagination-btn active">${i}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="?page=${i}" class="pagination-btn">${i}</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>

                                    <%-- Thêm dấu ba chấm ... ở các biên chặn --%>
                                    <c:when test="${i == currentPage - 3 || i == currentPage + 3}">
                                        <span class="pagination-dots">...</span>
                                    </c:when>
                                </c:choose>
                            </c:forEach>

                            <c:choose>
                                <c:when test="${currentPage == totalPages}">
                                    <span class="pagination-btn disabled">Sau ▶</span>
                                </c:when>
                                <c:otherwise>
                                    <a href="?page=${currentPage + 1}" class="pagination-btn">Sau ▶</a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:if>

                </c:when>
                <c:otherwise>
                    <div class="empty-state">
                        <p>Bạn chưa có đơn hàng nào.</p>
                        <a href="${pageContext.request.contextPath}/products">Khám phá sản phẩm ngay &rarr;</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <script>
            function openSignModal(orderCode, orderId) {
                let signature = prompt("Đơn hàng: #" + orderCode + "\nHãy dán chuỗi Chữ ký số (Signature) tạo được từ Tool Offline vào đây:");
                if (signature != null && signature.trim() !== "") {
                    const params = new URLSearchParams();
                    params.append("orderId", orderId);
                    params.append("signatureStr", signature.trim());

                    fetch("${pageContext.request.contextPath}/sign-order", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/x-www-form-urlencoded"
                        },
                        body: params.toString()
                    })
                        .then(async response => {
                            if (response.ok) {
                                alert("Ký số đơn hàng #" + orderCode + " thành công!");
                                window.location.reload();
                            } else {
                                const errorText = await response.text();
                                alert("Lỗi ký số: " + errorText);
                            }
                        })
                        .catch(error => {
                            console.error("Lỗi hệ thống AJAX:", error);
                            alert("Không thể kết nối đến máy chủ xử lý!");
                        });
                }
            }

            function recreateOrder(orderId) {
                if (confirm("Bạn có muốn tự động tạo lại đơn hàng mới tương tự như đơn hàng đã hủy này không?")) {
                    let form = document.createElement("form");
                    form.method = "POST";
                    form.action = "${pageContext.request.contextPath}/recreate-order";

                    let idInput = document.createElement("input");
                    idInput.type = "hidden";
                    idInput.name = "oldOrderId";
                    idInput.value = orderId;
                    form.appendChild(idInput);

                    document.body.appendChild(form);
                    form.submit();
                }
            }
            function copyOrderHash(element) {
                const hash = element.getAttribute('data-hash');

                if (!hash || hash.trim() === "") {
                    alert("Đơn hàng này hệ thống chưa tạo Mã xác thực (Order Hash)!");
                    return;
                }

                navigator.clipboard.writeText(hash.trim()).then(function() {
                    alert("Đã copy thành công Mã xác thực (Order Hash) của đơn hàng #" + element.innerText.replace('📋', '').trim() + "\n\nBây giờ bạn có thể dán vào Tool Offline để ký số.");
                }).catch(function(err) {
                    console.error("Không thể copy tự động, lỗi: ", err);
                });
            }
        </script>
    </main>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>