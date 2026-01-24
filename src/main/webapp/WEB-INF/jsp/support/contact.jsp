<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liên hệ - DPK Shop</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-contact.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <style>
        /* CSS nội bộ nhỏ cho thông báo alert */
        .alert { padding: 1rem; border-radius: 8px; margin-bottom: 1.5rem; }
        .alert-success { background: #d1fae5; color: #065f46; border: 1px solid #6ee7b7; }
        .alert-error { background: #fee2e2; color: #991b1b; border: 1px solid #fecaca; }
    </style>
</head>
<body>

<%@ include file="/WEB-INF/jsp/common/header.jsp" %>

<main>
    <section class="contact-section">
        <div class="container">
            <div class="contact-layout">

                <div class="contact-info">
                    <h1>Thông Tin Liên Hệ</h1>
                    <p class="contact-desc">Liên hệ với chúng tôi qua các kênh sau:</p>

                    <div class="info-item">
                        <div class="info-icon">📧</div>
                        <div class="info-content">
                            <h2>Email</h2>
                            <p><a href="mailto:${applicationScope.shop_email}">${applicationScope.shop_email}</a></p>
                        </div>
                    </div>

                    <div class="info-item">
                        <div class="info-icon">📞</div>
                        <div class="info-content">
                            <h2>Hotline</h2>
                            <p><a href="tel:${applicationScope.shop_phone}">${applicationScope.shop_phone}</a></p>
                        </div>
                    </div>

                    <div class="info-item">
                        <div class="info-icon">📍</div>
                        <div class="info-content">
                            <h2>Địa Chỉ</h2>
                            <p>${applicationScope.shop_address}</p>
                        </div>
                    </div>
                </div>

                <div class="contact-form-wrapper">

                    <%-- Hiển thị thông báo --%>
                    <c:if test="${not empty success}">
                        <div class="alert alert-success">${success}</div>
                    </c:if>
                    <c:if test="${not empty error}">
                        <div class="alert alert-error">${error}</div>
                    </c:if>

                    <c:choose>

                        <%-- === FORM DÀNH CHO ADMIN (Cập nhật thông tin shop) === --%>
                        <c:when test="${not empty sessionScope.admin}">

                            <div class="admin-edit-form">
                                <h3><i class="fa-solid fa-pen-to-square"></i> CẬP NHẬT THÔNG TIN SHOP</h3>
                                <p>Thay đổi tại đây sẽ cập nhật ngay lập tức cho toàn bộ khách hàng.</p>

                                <form action="${pageContext.request.contextPath}/contact" method="post">
                                    <input type="hidden" name="action" value="update_info">

                                    <div class="form-group">
                                        <label>Số điện thoại Hotline:</label>
                                        <input type="text" name="shop_phone" value="${applicationScope.shop_phone}" required>
                                    </div>

                                    <div class="form-group" style="margin-top: 1.5rem;">
                                        <label>Email liên hệ:</label>
                                        <input type="email" name="shop_email" value="${applicationScope.shop_email}" required>
                                    </div>

                                    <div class="form-group" style="margin-top: 1.5rem;">
                                        <label>Địa chỉ cửa hàng:</label>
                                        <textarea name="shop_address" rows="3" required>${applicationScope.shop_address}</textarea>
                                    </div>

                                    <div class="btn-wrapper-center">
                                        <button type="submit" class="btn-submit-common">
                                            <i class="fa-solid fa-floppy-disk"></i> LƯU THAY ĐỔI
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </c:when>


                        <%-- === FORM DÀNH CHO KHÁCH (Gửi tin nhắn) === --%>
                        <c:otherwise>
                            <h2>Gửi tin nhắn cho chúng tôi</h2>

                            <form action="${pageContext.request.contextPath}/contact" method="post" class="contact-form">
                                <input type="hidden" name="action" value="send_message">

                                <div class="form-group">
                                    <label for="fullName">Họ và Tên <span class="required">*</span></label>
                                    <input type="text" id="fullName" name="fullname"
                                           value="${not empty formFullName ? formFullName : customerFullName}" required>
                                </div>

                                <div class="form-row">
                                    <div class="form-group">
                                        <label for="email">Email <span class="required">*</span></label>
                                        <input type="email" id="email" name="email"
                                               value="${not empty formEmail ? formEmail : customerEmail}" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="phone">Số Điện Thoại</label>
                                        <input type="tel" id="phone" name="phone"
                                               value="${not empty formPhone ? formPhone : customerPhone}" required>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="subject">Tiêu Đề</label>
                                    <input type="text" id="subject" name="subject" value="${formSubject}" required>
                                </div>

                                <div class="form-group">
                                    <label for="message">Nội Dung <span class="required">*</span></label>
                                    <textarea id="message" name="message" required>${formMessage}</textarea>
                                </div>

                                <div class="btn-wrapper-center">
                                    <button type="submit" class="btn-submit-common">
                                        <i class="fa-solid fa-paper-plane"></i> GỬI TIN NHẮN
                                    </button>
                                </div>
                            </form>
                        </c:otherwise>

                    </c:choose>

                </div>
            </div>
        </div>
    </section>
</main>

<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>

<script>
    const contactForm = document.querySelector('.contact-form');
    if (contactForm) {
        const messageTextarea = document.getElementById('message');
        contactForm.addEventListener('submit', function(e) {
            const message = messageTextarea.value.trim();
            if (message.length < 10) {
                e.preventDefault();
                alert('Nội dung tin nhắn phải có ít nhất 10 ký tự');
                messageTextarea.focus();
            }
        });
    }
</script>

</body>
</html>