<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--@elvariable id="error" type="java.lang.String"--%>
<%--@elvariable id="email" type="java.lang.String"--%>
<%--@elvariable id="fullName" type="java.lang.String"--%>
<%--@elvariable id="phone" type="java.lang.String"--%>
<%--@elvariable id="address" type="java.lang.String"--%>

<!doctype html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng ký - DPK Shop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-login.css">
    <style>
        /* Additional styles for register form */
        .card {
            max-width: 600px;
        }

        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 1rem;
        }

        @media (max-width: 768px) {
            .form-row {
                grid-template-columns: 1fr;
            }
        }

        /* Password strength indicator */
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

        /* Alert styling */
        .alert {
            padding: 1rem;
            border-radius: 8px;
            margin-bottom: 1.5rem;
        }

        .alert-error {
            background-color: #fee2e2;
            color: #991b1b;
            border: 1px solid #fecaca;
        }

        /* Terms checkbox styling */
        .terms-label {
            display: inline-flex;
            align-items: flex-start;
            gap: 0.5rem;
            cursor: pointer;
            color: var(--text-dark);
        }

        .terms-label input[type="checkbox"] {
            margin-top: 0.25rem;
            cursor: pointer;
            width: 18px;
            height: 18px;
        }

        .text-link {
            color: var(--primary-color);
            text-decoration: underline;
            transition: color 0.3s;
        }

        .text-link:hover {
            color: var(--secondary-color);
        }

        /* Form field small text */
        .form-hint {
            display: block;
            margin-top: 0.25rem;
            color: var(--text-light);
            font-size: 0.85rem;
        }

        /* Social buttons */
        .social-divider {
            text-align: center;
            color: var(--text-light);
            margin: 1.5rem 0 1rem;
        }

        .social-row {
            display: flex;
            gap: 1rem;
        }

        .social-btn {
            flex: 1;
            padding: 0.875rem;
            border: 1px solid var(--border-color);
            border-radius: 8px;
            background: var(--bg-white);
            color: var(--text-dark);
            font-size: 1rem;
            font-weight: 500;
            cursor: pointer;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 0.5rem;
            transition: all 0.3s;
        }

        .social-btn:hover {
            border-color: var(--primary-color);
            transform: translateY(-2px);
            box-shadow: var(--shadow-md);
        }

        .social-btn span {
            font-size: 1.2rem;
        }

        /* Login link */
        .signup-line {
            text-align: center;
            margin-top: 1.5rem;
            color: var(--text-dark);
        }

        /* Required field indicator */
        .required {
            color: #dc2626;
        }
    </style>
</head>
<body>

<%-- INCLUDE HEADER --%>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>

<main class="container">
    <section class="card" aria-labelledby="register-title">
        <h1 id="register-title">Đăng ký tài khoản</h1>

        <%-- Error message --%>
        <c:if test="${not empty error}">
            <div class="alert alert-error">
                 ${error}
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/register" method="post" novalidate>

            <%-- Email --%>
            <div class="form-field">
                <label for="email">Email <span class="required">*</span></label>
                <input id="email"
                       name="email"
                       type="email"
                       placeholder="you@example.com"
                       value="${email}"
                       required
                       autofocus />
            </div>

            <%-- Full Name --%>
            <div class="form-field">
                <label for="fullName">Họ và tên <span class="required">*</span></label>
                <input id="fullName"
                       name="fullName"
                       type="text"
                       placeholder="Nguyễn Văn A"
                       value="${fullName}"
                       required />
            </div>

            <%-- Password Row --%>
            <div class="form-row">
                <div class="form-field">
                    <label for="password">Mật khẩu <span class="required">*</span></label>
                    <input id="password"
                           name="password"
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

                <div class="form-field">
                    <label for="confirmPassword">Xác nhận mật khẩu <span class="required">*</span></label>
                    <input id="confirmPassword"
                           name="confirmPassword"
                           type="password"
                           placeholder="Nhập lại mật khẩu"
                           minlength="8"
                           required />
                </div>
            </div>

            <%-- Phone & Address Row --%>
            <div class="form-row">
                <div class="form-field">
                    <label for="phone">Số điện thoại</label>
                    <input id="phone"
                           name="phone"
                           type="tel"
                           placeholder="0901234567"
                           value="${phone}" />
                    <small class="form-hint">Ví dụ: 0901234567 (10 chữ số)</small>
                </div>

                <div class="form-field">
                    <label for="address">Địa chỉ</label>
                    <input id="address"
                           name="address"
                           type="text"
                           placeholder="123 Đường ABC, Quận 1, TP.HCM"
                           value="${address}" />
                </div>
            </div>

            <%-- Terms & Conditions --%>
            <div style="text-align: left; margin-bottom: 1.5rem;">
                <label class="terms-label">
                    <input type="checkbox" name="terms" required />
                    <span>
                        Tôi đồng ý với
                        <a href="${pageContext.request.contextPath}/terms" class="text-link" target="_blank">Điều khoản sử dụng</a>
                        và
                        <a href="${pageContext.request.contextPath}/privacy" class="text-link" target="_blank">Chính sách bảo mật</a>
                    </span>
                </label>
            </div>

            <%-- Submit Button --%>
            <div class="row-center">
                <button type="submit" class="btn-primary">Đăng ký</button>
            </div>

            <%-- Login Link --%>
            <div class="signup-line">
                Đã có tài khoản?
                <a href="${pageContext.request.contextPath}/login" class="text-link">Đăng nhập tại đây</a>
            </div>

            <%-- Social Register --%>
            <div class="social-divider">
                <span>Hoặc đăng ký với</span>
            </div>
            <div class="social-row">
                <button type="button" class="social-btn social-google" aria-label="Sign up with Google">
                    <span></span> Google
                </button>
                <button type="button" class="social-btn social-facebook" aria-label="Sign up with Facebook">
                    <span></span> Facebook
                </button>
            </div>
        </form>
    </section>
</main>

<%-- INCLUDE FOOTER --%>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>

<%-- Client-side validation --%>
<script>
    // Password strength validation
    const passwordInput = document.getElementById('password');
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

        // Return true if all requirements are met
        return Object.values(requirements).every(v => v === true);
    }

    // Password input event
    passwordInput.addEventListener('input', function() {
        const password = this.value;

        // Validate strength
        const isValid = validatePasswordStrength(password);

        // Set custom validity
        if (password && !isValid) {
            this.setCustomValidity('Mật khẩu chưa đáp ứng đủ yêu cầu');
        } else {
            this.setCustomValidity('');
        }

        // Reset confirm password validation
        if (confirmPasswordInput.value) {
            validatePasswordMatch();
        }
    });

    // Confirm password validation
    function validatePasswordMatch() {
        const password = passwordInput.value;
        const confirmPassword = confirmPasswordInput.value;

        if (confirmPassword && password !== confirmPassword) {
            confirmPasswordInput.setCustomValidity('Mật khẩu xác nhận không khớp');
        } else {
            confirmPasswordInput.setCustomValidity('');
        }
    }

    confirmPasswordInput.addEventListener('input', validatePasswordMatch);

    // Form submit validation
    document.querySelector('form').addEventListener('submit', function(e) {
        const password = passwordInput.value;

        // Final validation before submit
        if (!validatePasswordStrength(password)) {
            e.preventDefault();
            passwordInput.focus();
            alert('Vui lòng đảm bảo mật khẩu đáp ứng tất cả các yêu cầu');
            return false;
        }

        if (password !== confirmPasswordInput.value) {
            e.preventDefault();
            confirmPasswordInput.focus();
            alert('Mật khẩu xác nhận không khớp');
            return false;
        }
    });
</script>

</body>
</html>