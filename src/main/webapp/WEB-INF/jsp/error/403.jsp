<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>403 - Truy Cập Bị Từ Chối</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #333;
        }
        .error-container {
            background:  white;
            padding: 3rem 2rem;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
            text-align: center;
            max-width: 500px;
            width: 90%;
        }
        .error-code {
            font-size: 8rem;
            font-weight: bold;
            color: #fa709a;
            line-height: 1;
            margin-bottom: 1rem;
        }
        .error-title {
            font-size: 1.8rem;
            color: #333;
            margin-bottom: 1rem;
        }
        .error-message {
            font-size: 1.1rem;
            color: #666;
            margin-bottom: 2rem;
            line-height: 1.6;
        }
        .error-actions {
            display: flex;
            gap: 1rem;
            justify-content: center;
            flex-wrap: wrap;
        }
        .btn {
            padding: 0.8rem 2rem;
            border-radius: 25px;
            text-decoration: none;
            font-weight: 600;
            transition: all 0.3s;
            display: inline-block;
        }
        .btn-primary {
            background: #fa709a;
            color: white;
        }
        .btn-primary:hover {
            background: #e85d87;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(250, 112, 154, 0.4);
        }
        .btn-secondary {
            background: #f0f0f0;
            color: #333;
        }
        .btn-secondary:hover {
            background: #e0e0e0;
            transform: translateY(-2px);
        }
        .icon {
            font-size: 4rem;
            margin-bottom: 1rem;
        }
    </style>
</head>
<body>
<div class="error-container">
    <div class="icon">🚫</div>
    <div class="error-code">403</div>
    <h1 class="error-title">Truy Cập Bị Từ Chối</h1>
    <p class="error-message">
        Bạn không có quyền truy cập vào trang này.
        Vui lòng đăng nhập hoặc liên hệ quản trị viên nếu bạn nghĩ đây là lỗi.
    </p>
    <div class="error-actions">
        <a href="${pageContext.request.contextPath}/" class="btn btn-primary">
            ← Về Trang Chủ
        </a>
        <a href="${pageContext.request.contextPath}/login" class="btn btn-secondary">
            🔑 Đăng Nhập
        </a>
    </div>
</div>
</body>
</html>