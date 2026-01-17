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
                <a href="${pageContext.request.contextPath}/about" class="text-link">
                    Về chúng tôi
                </a>
            </div>

            <!-- Liên hệ -->
            <div class="footer-section">
                <h3>Liên Hệ</h3>
                <p>📧 Email: contact@dpkshop.com</p>
                <p>📞 Hotline: 1900 5678</p>
                <p>📍 Địa chỉ: 123 Đường Học Tập, Phường 1, Quận 1, TP.HCM</p>
                <a href="${pageContext.request.contextPath}/contact" class="text-link">
                    Chi tiết liên hệ
                </a>
            </div>

            <!-- Hỗ trợ -->
            <div class="footer-section">
                <h3>Hỗ Trợ</h3>
                <a href="${pageContext.request.contextPath}/help/buying-guide">Hướng dẫn mua hàng</a>
                <a href="${pageContext.request.contextPath}/help/return-policy">Chính sách đổi trả</a>
                <a href="${pageContext.request.contextPath}/help/shipping">Vận chuyển</a>
                <a href="${pageContext.request.contextPath}/help/payment">Thanh toán</a>
            </div>

            <!-- Social Media -->
            <div class="footer-section">
                <h3>Theo Dõi</h3>
                <a href="https://facebook.com/dpkshop" target="_blank" rel="noopener">Facebook</a>
                <a href="https://instagram.com/dpkshop" target="_blank" rel="noopener">Instagram</a>
                <a href="https://twitter.com/dpkshop" target="_blank" rel="noopener">Twitter</a>
                <a href="https://youtube.com/dpkshop" target="_blank" rel="noopener">YouTube</a>
            </div>
        </div>

        <!-- Footer Bottom -->
        <div class="footer-bottom">
            <p>&copy; 2025 DPK Shop.Tất cả bản quyền được bảo lưu.</p>
            <%-- Hiển thị app version từ web.xml context-param --%>
            <c:if test="${not empty initParam['app.version']}">
                <p style="font-size: 0.8rem; color: #999; margin-top: 0.5rem;">
                    Version ${initParam['app.version']}
                </p>
                </c:if>
        </div>
    </div>
</footer>