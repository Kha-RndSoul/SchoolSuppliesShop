<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>500 - Lỗi Server</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #333;
        }
        .error-container {
            background: white;
            padding:  3rem 2rem;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
            text-align: center;
            max-width: 500px;
            width: 90%;
        }
        .error-code {
            font-size: 8rem;
            font-weight: bold;
            color: #f5576c;
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
            margin-bottom:  2rem;
            line-height: 1.6;
        }
        .btn {
            padding: 0.8rem 2rem;
            border-radius: 25px;
            text-decoration: none;
            font-weight: 600;
            background: #f5576c;
            color: white;
            display: inline-block;
            transition: all 0.3s;
        }
        .btn:hover {
            background: #e14658;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(245, 87, 108, 0.4);
        }
        .icon {
            font-size: 4rem;
            margin-bottom: 1rem;
        }
    </style>
</head>
<body>
<div class="error-container">
    <div class="icon">🔧</div>
    <div class="error-code">500</div>
    <h1 class="error-title">Lỗi Server</h1>
    <p class="error-message">
        Xin lỗi, đã có lỗi xảy ra trên server. Chúng tôi đang khắc phục sự cố này.
        Vui lòng thử lại sau ít phút.
    </p>
    <a href="${pageContext.request.contextPath}/" class="btn">
        ← Về Trang Chủ
    </a>
</div>
</body>
</html>