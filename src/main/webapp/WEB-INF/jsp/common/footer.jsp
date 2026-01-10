<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<footer class="footer">
    <div class="container">
        <div class="footer-content">
            <!-- Giới thiệu -->
            <div class="footer-section">
                <h3>Giới thiệu</h3>
                <p>
                    DPK Shop là cửa hàng chuyên cung cấp đồ dùng học tập chất lượng cao
                    với giá cả hợp lý, phục vụ học sinh, sinh viên trên toàn quốc.
                </p>
                <a href="${pageContext.request.contextPath}/about.jsp" class="text-link">
                    Về chúng tôi
                </a>
            </div>

            <!-- Liên hệ -->
            <div class="footer-section">
                <h3>Liên Hệ</h3>
                <p>📧 Email: contact@dpkshop.com</p>
                <p>📞 Hotline: 1900 5678</p>
                <p>📍 Địa chỉ: 123 Đường Học Tập, Phường 1, Quận 1, TP.HCM</p>
                <a href="${pageContext.request.contextPath}/contact.jsp" class="text-link">
                    Chi tiết liên hệ
                </a>
            </div>

            <!-- Hỗ trợ -->
            <div class="footer-section">
                <h3>Hỗ Trợ</h3>
                <a href="${pageContext.request.contextPath}/">Hướng dẫn mua hàng</a>
                <a href="${pageContext.request.contextPath}/">Chính sách đổi trả</a>
                <a href="${pageContext.request.contextPath}/">Vận chuyển</a>
                <a href="${pageContext.request.contextPath}/">Thanh toán</a>
            </div>

            <!-- Social Media -->
            <div class="footer-section">
                <h3>Theo Dõi</h3>
                <a href="#">Facebook</a>
                <a href="#">Instagram</a>
                <a href="#">Twitter</a>
                <a href="#">YouTube</a>
            </div>
        </div>

        <!-- Footer Bottom -->
        <div class="footer-bottom">
            <p>&copy; 2025 DPK Shop. Tất cả bản quyền được bảo lưu.</p>
        </div>
    </div>
</footer>