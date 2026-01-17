<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--@elvariable id="success" type="java.lang.String"--%>
<%--@elvariable id="error" type="java.lang.String"--%>
<%--@elvariable id="customerFullName" type="java.lang.String"--%>
<%--@elvariable id="customerEmail" type="java.lang.String"--%>
<%--@elvariable id="customerPhone" type="java.lang.String"--%>
<%--@elvariable id="formSubject" type="java.lang.String"--%>
<%--@elvariable id="formMessage" type="java.lang.String"--%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liên hệ - DPK Shop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-contact.css">
    <style>
        /* Alert Messages - Thêm vào để hiển thị success/error */
        .alert {
            padding: 1rem 1.25rem;
            border-radius: 8px;
            margin-bottom: 1.5rem;
            font-size: 0.95rem;
            line-height: 1.5;
            font-weight: 500;
        }

        .alert-success {
            background: #d1fae5;
            color: #065f46;
            border: 1px solid #6ee7b7;
        }

        .alert-error {
            background: #fee2e2;
            color: #991b1b;
            border: 1px solid #fecaca;
        }
    </style>
</head>
<body>

<%-- INCLUDE HEADER --%>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>

<main>
    <!-- Contact Section -->
    <section class="contact-section">
        <div class="container">
            <div class="contact-layout">
                <!-- Contact Information -->
                <div class="contact-info">
                    <h1>Thông Tin Liên Hệ</h1>
                    <p class="contact-desc">Liên hệ với chúng tôi qua các kênh sau:</p>

                    <div class="info-item">
                        <div class="info-icon">📧</div>
                        <div class="info-content">
                            <h2>Email</h2>
                            <p><a href="mailto:contact@dpkshop.com">contact@dpkshop.com</a></p>
                            <p><a href="mailto:support@dpkshop.com">support@dpkshop.com</a></p>
                        </div>
                    </div>

                    <div class="info-item">
                        <div class="info-icon">📞</div>
                        <div class="info-content">
                            <h2>Hotline</h2>
                            <p><a href="tel:19005678">1900 5678</a> (7:30 - 22:00)</p>
                            <p><a href="tel:0123456789">0123 456 789</a> (24/7)</p>
                        </div>
                    </div>

                    <div class="info-item">
                        <div class="info-icon">📍</div>
                        <div class="info-content">
                            <h2>Địa Chỉ</h2>
                            <p>123 Đường Học Tập, Phường 1</p>
                            <p>Quận 1, TP. Hồ Chí Minh</p>
                        </div>
                    </div>
                </div>

                <!-- Contact Form -->
                <div class="contact-form-wrapper">
                    <h2>Gửi Tin Nhắn Cho Chúng Tôi</h2>

                    <%-- Success Message --%>
                    <c:if test="${not empty success}">
                        <div class="alert alert-success">
                             ${success}
                        </div>
                    </c:if>

                    <%-- Error Message --%>
                    <c:if test="${not empty error}">
                        <div class="alert alert-error">
                             ${error}
                        </div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/contact" method="post" class="contact-form">

                        <%-- Full Name --%>
                        <div class="form-group">
                            <label for="fullName">Họ và Tên <span class="required">*</span></label>
                            <input type="text"
                                   id="fullName"
                                   name="fullName"
                                   placeholder="Nguyễn Văn A"
                                   value="${customerFullName}"
                                   required />
                        </div>

                        <%-- Email & Phone Row --%>
                        <div class="form-row">
                            <div class="form-group">
                                <label for="email">Email <span class="required">*</span></label>
                                <input type="email"
                                       id="email"
                                       name="email"
                                       placeholder="example@email.com"
                                       value="${customerEmail}"
                                       required />
                            </div>

                            <div class="form-group">
                                <label for="phone">Số Điện Thoại</label>
                                <input type="tel"
                                       id="phone"
                                       name="phone"
                                       placeholder="0123 456 789"
                                       value="${customerPhone}" />
                            </div>
                        </div>

                        <%-- Subject --%>
                        <div class="form-group">
                            <label for="subject">Tiêu Đề</label>
                            <input type="text"
                                   id="subject"
                                   name="subject"
                                   placeholder="Chủ đề liên hệ"
                                   value="${formSubject}" />
                        </div>

                        <%-- Message --%>
                        <div class="form-group">
                            <label for="message">Nội Dung <span class="required">*</span></label>
                            <textarea id="message"
                                      name="message"
                                      rows="6"
                                      placeholder="Nhập nội dung tin nhắn của bạn tại đây..."
                                      required>${formMessage}</textarea>
                        </div>

                        <%-- Form Actions --%>
                        <div class="form-actions">
                            <button type="submit" class="btn-primary">
                                ✉️ Gửi Tin Nhắn
                            </button>
                            <button type="reset" class="btn-reset">
                                🔄 Nhập Lại
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</main>

<%-- INCLUDE FOOTER --%>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>

<%-- Client-side validation --%>
<script>
    // Form validation
    const form = document.querySelector('.contact-form');
    const messageTextarea = document.getElementById('message');

    form.addEventListener('submit', function(e) {
        const message = messageTextarea.value.trim();

        // Check minimum length
        if (message.length < 10) {
            e.preventDefault();
            alert('Nội dung tin nhắn phải có ít nhất 10 ký tự');
            messageTextarea.focus();
            return false;
        }

        // Check maximum length
        if (message.length > 5000) {
            e.preventDefault();
            alert('Nội dung tin nhắn không được vượt quá 5000 ký tự');
            messageTextarea.focus();
            return false;
        }
    });

    // Character counter (optional)
    messageTextarea.addEventListener('input', function() {
        const length = this.value.length;
        const maxLength = 5000;

        // You can add a character counter display here if needed
        if (length > maxLength) {
            this.value = this.value.substring(0, maxLength);
        }
    });
</script>

</body>
</html>