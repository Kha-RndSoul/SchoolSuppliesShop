<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--@elvariable id="success" type="java.lang.String"--%>
<%--@elvariable id="error" type="java.lang.String"--%>
<%--@elvariable id="successPassword" type="java.lang.String"--%>
<%--@elvariable id="errorPassword" type="java.lang.String"--%>
<%--@elvariable id="admin" type="com.shop.model.Admin"--%>

<!doctype html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hồ sơ Admin - DPK Shop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-login.css">
    <style>
        /* Profile Page Specific Styles */
        main {
            padding: 2rem 0;
        }

        .profile-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 2rem;
        }

        .profile-header {
            text-align: center;
            margin-bottom: 3rem;
        }

        .profile-header h1 {
            font-size: 2.5rem;
            color: var(--text-dark);
            margin-bottom: 0.5rem;
        }

        .profile-header p {
            color: var(--text-light);
            font-size: 1.1rem;
        }

        .admin-badge {
            display: inline-block;
            padding: 0.35rem 1rem;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border-radius: 20px;
            font-size: 0.85rem;
            font-weight: 600;
            margin-left: 0.5rem;
            box-shadow: 0 2px 8px rgba(102, 126, 234, 0.3);
        }

        .profile-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 2rem;
            margin-bottom: 2rem;
        }

        @media (max-width: 968px) {
            .profile-grid {
                grid-template-columns: 1fr;
            }
        }

        .card {
            max-width: 100%;
        }

        .card h2 {
            font-size: 1.5rem;
            color: var(--text-dark);
            margin-bottom: 1.5rem;
            padding-bottom: 0.75rem;
            border-bottom: 2px solid var(--border-color);
        }

        /* Form disabled styling */
        .form-field input:disabled {
            background-color: var(--bg-light);
            color: var(--text-light);
            cursor: not-allowed;
        }

        /* Password requirements */
        .password-requirements {
            margin-top: 0.5rem;
            padding: 0.75rem;
            background-color: var(--bg-light);
            border: 1px solid var(--border-color);
            border-radius: 8px;
            font-size: 0.85rem;
        }

        .password-requirements strong {
            color: var(--text-dark);
            display: block;
            margin-bottom: 0.5rem;
        }

        .password-requirements ul {
            margin: 0;
            padding-left: 1.5rem;
            list-style: none;
        }

        .password-requirements li {
            margin: 0.35rem 0;
            color: var(--text-light);
            transition: color 0.3s;
        }

        .password-requirements li::before {
            content: "○ ";
            color: var(--text-light);
            font-weight: bold;
            margin-right: 0.25rem;
        }

        .password-requirements li.valid {
            color: #059669;
        }

        .password-requirements li.valid::before {
            content: "✓ ";
            color: #059669;
        }

        .password-requirements li.invalid {
            color: #dc2626;
        }

        .password-requirements li.invalid::before {
            content: "✗ ";
            color: #dc2626;
        }

        /* Info badge */
        .info-badge {
            display: inline-block;
            padding: 0.25rem 0.75rem;
            background: var(--bg-light);
            color: var(--text-light);
            border-radius: 4px;
            font-size: 0.85rem;
            margin-left: 0.5rem;
        }

        /* Role Badge */
        .role-badge {
            display: inline-block;
            padding: 0.25rem 0.75rem;
            border-radius: 4px;
            font-size: 0.85rem;
            font-weight: 600;
            margin-left: 0.5rem;
        }

        .role-super-admin {
            background: #fef3c7;
            color: #92400e;
            border: 1px solid #fcd34d;
        }

        .role-manager {
            background: #dbeafe;
            color: #1e40af;
            border: 1px solid #93c5fd;
        }

        .role-staff {
            background: #e0e7ff;
            color: #4338ca;
            border: 1px solid #a5b4fc;
        }
    </style>
</head>
<body>

<%-- INCLUDE HEADER --%>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>

<main>
    <div class="profile-container">
        <!-- Profile Header -->
        <div class="profile-header">
            <h1>Hồ sơ quản trị viên </h1>
            <p>
                Xin chào, <strong>${sessionScope.admin.fullName}</strong>!
                <span class="role-badge
                    <c:choose>
                        <c:when test="${sessionScope.admin.role == 'SUPER_ADMIN'}">role-super-admin</c:when>
                        <c:when test="${sessionScope.admin.role == 'MANAGER'}">role-manager</c:when>
                        <c:otherwise>role-staff</c:otherwise>
                    </c:choose>
                ">
                    ${sessionScope.admin.role}
                </span>
            </p>
        </div>

        <!-- Profile Grid -->
        <div class="profile-grid">

            <!-- Left: Personal Information -->
            <section class="card">
                <h2>📋 Thông tin cá nhân</h2>

                <%-- Success/Error Messages for Info Update --%>
                <c:if test="${not empty success}">
                    <div class="alert alert-success">
                            ${success}
                    </div>
                </c:if>

                <c:if test="${not empty error}">
                    <div class="alert alert-error">
                            ${error}
                    </div>
                </c:if>

                <form action="${pageContext.request.contextPath}/admin/profile" method="post">
                    <input type="hidden" name="action" value="update-info">

                    <%-- Username (Read-only) --%>
                    <div class="form-field">
                        <label for="username">
                            Tên đăng nhập
                            <span class="info-badge">Không thể thay đổi</span>
                        </label>
                        <input id="username"
                               type="text"
                               value="${sessionScope.admin.username}"
                               disabled />
                    </div>

                    <%-- Email --%>
                    <div class="form-field">
                        <label for="email">Email <span style="color: #dc2626;">*</span></label>
                        <input id="email"
                               name="email"
                               type="email"
                               value="${sessionScope.admin.email}"
                               placeholder="admin@shop.com"
                               required />
                    </div>

                    <%-- Full Name --%>
                    <div class="form-field">
                        <label for="fullName">Họ và tên <span style="color: #dc2626;">*</span></label>
                        <input id="fullName"
                               name="fullName"
                               type="text"
                               value="${sessionScope.admin.fullName}"
                               placeholder="Nguyễn Văn A"
                               required />
                    </div>

                    <%-- Role (Read-only) --%>
                    <div class="form-field">
                        <label for="role">
                            Vai trò
                            <span class="info-badge">Không thể thay đổi</span>
                        </label>
                        <input id="role"
                               type="text"
                               value="${sessionScope.admin.role}"
                               disabled />
                    </div>

                    <%-- Submit Button --%>
                    <div class="row-center">
                        <button type="submit" class="btn btn-primary">Cập nhật thông tin</button>
                    </div>
                </form>
            </section>

            <!-- Right: Change Password -->
            <section class="card">
                <h2>🔒 Đổi mật khẩu</h2>

                <%-- Success/Error Messages for Password Change --%>
                <c:if test="${not empty successPassword}">
                    <div class="alert alert-success">
                            ${successPassword}
                    </div>
                </c:if>

                <c:if test="${not empty errorPassword}">
                    <div class="alert alert-error">
                            ${errorPassword}
                    </div>
                </c:if>

                <form action="${pageContext.request.contextPath}/admin/profile" method="post" id="changePasswordForm">
                    <input type="hidden" name="action" value="change-password">

                    <%-- Old Password --%>
                    <div class="form-field">
                        <label for="oldPassword">Mật khẩu cũ <span style="color: #dc2626;">*</span></label>
                        <input id="oldPassword"
                               name="oldPassword"
                               type="password"
                               placeholder="Nhập mật khẩu cũ"
                               required />
                    </div>

                    <%-- New Password --%>
                    <div class="form-field">
                        <label for="newPassword">Mật khẩu mới <span style="color: #dc2626;">*</span></label>
                        <input id="newPassword"
                               name="newPassword"
                               type="password"
                               placeholder="Tối thiểu 8 ký tự"
                               minlength="8"
                               required />

                        <%-- Password Requirements --%>
                        <div class="password-requirements">
                            <strong>Yêu cầu mật khẩu:</strong>
                            <ul id="password-checklist">
                                <li id="check-length">Tối thiểu 8 ký tự</li>
                                <li id="check-lowercase">Có chữ cái thường (a-z)</li>
                                <li id="check-uppercase">Có chữ cái hoa (A-Z)</li>
                                <li id="check-number">Có chữ số (0-9)</li>
                                <li id="check-special">Có ký tự đặc biệt (!@#$%^&*...)</li>
                            </ul>
                        </div>
                    </div>

                    <%-- Confirm Password --%>
                    <div class="form-field">
                        <label for="confirmPassword">Xác nhận mật khẩu mới <span style="color: #dc2626;">*</span></label>
                        <input id="confirmPassword"
                               name="confirmPassword"
                               type="password"
                               placeholder="Nhập lại mật khẩu mới"
                               minlength="8"
                               required />
                    </div>

                    <%-- Submit Buttons --%>
                    <div class="row-center">
                        <button type="submit" class="btn btn-primary">Đổi mật khẩu</button>
                    </div>
                </form>
            </section>

        </div>
    </div>
</main>

<%-- INCLUDE FOOTER --%>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>

<%-- Client-side validation --%>
<script>
    // Password strength validation
    const newPasswordInput = document.getElementById('newPassword');
    const confirmPasswordInput = document.getElementById('confirmPassword');

    // Password requirements checklist
    const checks = {
        length: document.getElementById('check-length'),
        lowercase: document.getElementById('check-lowercase'),
        uppercase: document.getElementById('check-uppercase'),
        number: document.getElementById('check-number'),
        special: document.getElementById('check-special')
    };

    // Validate password strength
    function validatePasswordStrength(password) {
        const requirements = {
            length: password.length >= 8,
            lowercase: /[a-z]/.test(password),
            uppercase: /[A-Z]/.test(password),
            number: /[0-9]/.test(password),
            special: /[!@#$%^&*()_+\-=\[\]{}|;:,.<>?]/.test(password)
        };

        // Update visual indicators
        for (const [key, element] of Object.entries(checks)) {
            if (requirements[key]) {
                element.classList.remove('invalid');
                element.classList.add('valid');
            } else {
                element.classList.remove('valid');
                element.classList.add('invalid');
            }
        }

        return Object.values(requirements).every(v => v === true);
    }

    // New password input event
    newPasswordInput.addEventListener('input', function() {
        const password = this.value;
        const isValid = validatePasswordStrength(password);

        if (password && !isValid) {
            this.setCustomValidity('Mật khẩu chưa đáp ứng đủ yêu cầu');
        } else {
            this.setCustomValidity('');
        }

        if (confirmPasswordInput.value) {
            validatePasswordMatch();
        }
    });

    // Confirm password validation
    function validatePasswordMatch() {
        const newPassword = newPasswordInput.value;
        const confirmPassword = confirmPasswordInput.value;

        if (confirmPassword && newPassword !== confirmPassword) {
            confirmPasswordInput.setCustomValidity('Mật khẩu xác nhận không khớp');
        } else {
            confirmPasswordInput.setCustomValidity('');
        }
    }

    confirmPasswordInput.addEventListener('input', validatePasswordMatch);

    // Form submit validation
    document.getElementById('changePasswordForm').addEventListener('submit', function(e) {
        const newPassword = newPasswordInput.value;

        if (!validatePasswordStrength(newPassword)) {
            e.preventDefault();
            newPasswordInput.focus();
            alert('Vui lòng đảm bảo mật khẩu đáp ứng tất cả các yêu cầu');
            return false;
        }

        if (newPassword !== confirmPasswordInput.value) {
            e.preventDefault();
            confirmPasswordInput.focus();
            alert('Mật khẩu xác nhận không khớp');
            return false;
        }
    });
</script>

</body>
</html>