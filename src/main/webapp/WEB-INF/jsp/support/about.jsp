<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Giới thiệu về DPK Shop - Cửa hàng đồ dùng học tập</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-common.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style-about.css" />
</head>
<body>

<!-- Header -->
<jsp:include page="../common/header.jsp" />

<main>
    <!-- Hero Section -->
    <section class="about-hero">
        <div class="container">
            <h1>Về Chúng Tôi</h1>
            <p class="hero-subtitle">Người bạn đồng hành tin cậy trong hành trình học tập</p>
        </div>
    </section>

    <!-- Story Section -->
    <section class="about-story">
        <div class="container">
            <div class="story-content">
                <div class="story-text">
                    <p>
                        Được thành lập vào năm 2020, <strong>DPK Shop</strong> ra đời từ khát vọng mang đến cho học sinh,
                        sinh viên những sản phẩm đồ dùng học tập chất lượng cao với mức giá hợp lý nhất.
                    </p>
                    <p>
                        Chúng tôi hiểu rằng những dụng cụ học tập phù hợp không chỉ giúp việc học trở nên dễ dàng hơn
                        mà còn khơi dậy niềm đam mê và sáng tạo. Từ những chiếc bút đơn giản đến các thiết bị công nghệ
                        hiện đại, mỗi sản phẩm tại DPK Shop đều được lựa chọn kỹ lưỡng.
                    </p>
                    <p>
                        Sau hơn 5 năm phát triển, chúng tôi tự hào phục vụ hàng nghìn khách hàng trên toàn quốc và
                        trở thành địa chỉ tin cậy cho mọi nhu cầu học tập.
                    </p>
                </div>
            </div>
        </div>
    </section>

    <!-- Mission & Vision -->
    <section class="mission-vision">
        <div class="container">
            <div class="mv-grid">
                <div class="mv-card">
                    <div class="mv-icon">🎯</div>
                    <h3>Sứ Mệnh</h3>
                    <p>
                        Cung cấp đồ dùng học tập chất lượng cao, giá cả hợp lý, giúp mọi học sinh, sinh viên
                        có điều kiện tốt nhất để phát triển năng lực và đạt thành tích cao trong học tập.
                    </p>
                </div>
                <div class="mv-card">
                    <div class="mv-icon">🌟</div>
                    <h3>Tầm Nhìn</h3>
                    <p>
                        Trở thành hệ thống cửa hàng đồ dùng học tập hàng đầu Việt Nam, được khách hàng tin tưởng
                        và lựa chọn bởi chất lượng sản phẩm và dịch vụ xuất sắc.
                    </p>
                </div>
                <div class="mv-card">
                    <div class="mv-icon">💎</div>
                    <h3>Giá Trị Cốt Lõi</h3>
                    <p>
                        Chất lượng - Uy tín - Tận tâm. Chúng tôi đặt khách hàng làm trung tâm, không ngừng
                        cải tiến để mang lại trải nghiệm mua sắm tốt nhất.
                    </p>
                </div>
            </div>
        </div>
    </section>
</main>

<!-- Footer -->
<jsp:include page="../common/footer.jsp" />

</body>
</html>