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
            min-height: calc(100vh - 200px); /
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
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/header.jsp" />

<div class="profile-page-wrapper">
    <main class="container">
        <h1 class="profile-heading">Quản lý tài khoản</h1>

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
                Đơn hàng gần đây của bạn :
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
                                <th>Thanh toán</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="order" items="${orderHistory}">
                                <tr>
                                    <td>
                                        <a href="#" class="order-code-link">#${order.orderCode}</a>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${order.createdAt}" pattern="dd/MM/yyyy"/>
                                        <br>
                                        <small style="color: #999;"><fmt:formatDate value="${order.createdAt}" pattern="HH:mm"/></small>
                                    </td>
                                    <td class="text-primary-brand">
                                        <fmt:formatNumber value="${order.totalAmount}" type="number"/>₫
                                    </td>
                                    <td>
                                        <span class="status-badge status-${order.orderStatus}">${order.orderStatus}</span>
                                    </td>
                                    <td>
                                        <span style="font-weight: 500;">${order.paymentMethod}</span><br>
                                        <small style="color: #888;">(${order.paymentStatus})</small>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="empty-state">
                        <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="#ddd" stroke-width="1" stroke-linecap="round" stroke-linejoin="round" class="feather feather-shopping-bag" style="margin-bottom: 15px;"><path d="M6 2L3 6v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6l-3-4z"></path><line x1="3" y1="6" x2="21" y2="6"></line><path d="M16 10a4 4 0 0 1-8 0"></path></svg>
                        <p>Bạn chưa có đơn hàng nào.</p>
                        <a href="${pageContext.request.contextPath}/products">Khám phá sản phẩm ngay &rarr;</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </main>
</div>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp" />
</body>
</html>