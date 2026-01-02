<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Có Lỗi Xảy Ra</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #a8edea 0%, #fed6e3 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #333;
        }
        .error-container {
            background: white;
            padding: 3rem 2rem;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
            text-align: center;
            max-width: 500px;
            width: 90%;
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
        .btn {
            padding: 0.8rem 2rem;
            border-radius: 25px;
            text-decoration: none;
            font-weight: 600;
            background: #a8edea;
            color:  #333;
            display: inline-block;
            transition: all 0.3s;
        }
        .btn:hover {
            background: #8fd9d6;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(168, 237, 234, 0.4);
        }
        .icon {
            font-size: 4rem;
            margin-bottom: 1rem;
        }
    </style>
</head>
<body>
<div class="error-container">
    <div class="icon">⚠️</div>
    <h1 class="error-title">Có Lỗi Xảy Ra</h1>
    <p class="error-message">
        Xin lỗi, đã có lỗi xảy ra khi xử lý yêu cầu của bạn.
        Vui lòng thử lại sau hoặc liên hệ hỗ trợ nếu vấn đề vẫn tiếp diễn.
    </p>
    <a href="${pageContext.request.contextPath}/" class="btn">
        ← Về Trang Chủ
    </a>
</div>
</body>
</html>