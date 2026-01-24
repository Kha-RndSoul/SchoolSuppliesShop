package com.shop.controller.support;

import com.shop.model.Admin;
import com.shop.model.ContactMessage;
import com.shop.model.Customer;
import com.shop.services.ContactMessageService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

/**
 * Servlet xử lý trang liên hệ
 * - Hiển thị thông tin shop (Lấy từ Application Scope)
 * - Xử lý gửi tin nhắn (Khách hàng)
 * - Xử lý cập nhật thông tin shop (Admin)
 */
@WebServlet(name = "ContactServlet", urlPatterns = {"/contact"})
public class ContactServlet extends HttpServlet {
    private ContactMessageService contactMessageService;
    @Override
    public void init() {
        System.out.println(" ContactServlet.init() STARTING...");

        try {
            contactMessageService = new ContactMessageService();
            System.out.println(" ContactMessageService created successfully");

            // CẤU HÌNH APPLICATION

            ServletContext application = getServletContext();
            if (application.getAttribute("shop_phone") == null) {
                application.setAttribute("shop_phone", "1900 5678");
                application.setAttribute("shop_email", "contact@dpkshop.com");
                application.setAttribute("shop_address", "123 Đường Học Tập, Phường 1, Quận 1, TP. HCM");
                System.out.println(" Default Shop Info set to Application Scope");
            }

        } catch (Exception e) {
            System.err.println(" ERROR in ContactServlet.init() ");
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize ContactServlet", e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check nếu user (Khách hàng) đã đăng nhập thì lấy thông tin để điền sẵn vào form
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("customer") != null) {
            Customer customer = (Customer) session.getAttribute("customer");
            request.setAttribute("customerFullName", customer.getFullName());
            request.setAttribute("customerEmail", customer.getEmail());
            request.setAttribute("customerPhone", customer.getPhone());
        }
        // Forward tới contact.jsp
        request.getRequestDispatcher("/WEB-INF/jsp/support/contact.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        // Lấy thông tin Admin và Customer từ session
        Admin admin = (Admin) session.getAttribute("admin");
        Customer customer = (Customer) session.getAttribute("customer");

        System.out.println("ContactServlet.doPost() - Action: " + action);
        // NHÁNH 1: XỬ LÝ ADMIN CẬP NHẬT THÔNG TIN SHOP
        if ("update_info".equals(action) && admin != null) {
            handleAdminUpdate(request, response);
            return; // Dừng xử lý, không chạy xuống phần gửi tin nhắn
        }
        // NHÁNH 2: XỬ LÝ KHÁCH HÀNG GỬI TIN NHẮN
        handleCustomerMessage(request, response, customer);
    }

    /**
     * Logic xử lý khi Admin cập nhật thông tin liên hệ
     */
    private void handleAdminUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String newPhone = request.getParameter("shop_phone");
        String newEmail = request.getParameter("shop_email");
        String newAddress = request.getParameter("shop_address");

        // Validate cơ bản
        if (newPhone != null && newEmail != null && newAddress != null) {
            // Cập nhật vào Application Scope (Hiển thị ngay cho toàn bộ khách hàng)
            ServletContext application = getServletContext();
            application.setAttribute("shop_phone", newPhone);
            application.setAttribute("shop_email", newEmail);
            application.setAttribute("shop_address", newAddress);

            request.setAttribute("success", "Đã cập nhật thông tin cửa hàng thành công!");
            System.out.println("Admin updated shop info: " + newEmail);
        } else {
            request.setAttribute("error", "Vui lòng điền đầy đủ thông tin!");
        }

        // Forward lại trang để Admin thấy kết quả
        request.getRequestDispatcher("/WEB-INF/jsp/support/contact.jsp").forward(request, response);
    }

    /**
     * Logic xử lý khi Khách hàng gửi tin nhắn liên hệ
     */
    private void handleCustomerMessage(HttpServletRequest request, HttpServletResponse response, Customer customer)
            throws ServletException, IOException {

        // Get parameters từ form
        String fullName = request.getParameter("fullname"); // Lưu ý: name trong JSP là "fullname" (thường viết thường hết)
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");

        if (fullName == null) fullName = request.getParameter("fullName");

        // Lấy ID khách hàng (nếu có)
        int customerId = (customer != null) ? customer.getId() : 0;
        String ipAddress = getClientIpAddress(request);

        try {
            // --- VALIDATION ---
            if (fullName == null || fullName.trim().isEmpty()) throw new IllegalArgumentException("Họ tên không được rỗng");
            if (email == null || email.trim().isEmpty()) throw new IllegalArgumentException("Email không được rỗng");
            if (message == null || message.trim().isEmpty()) throw new IllegalArgumentException("Nội dung tin nhắn không được rỗng");
            if (!isValidEmail(email)) throw new IllegalArgumentException("Email không hợp lệ");
            if (message.trim().length() < 10) throw new IllegalArgumentException("Nội dung tin nhắn phải có ít nhất 10 ký tự");
            if (message.trim().length() > 5000) throw new IllegalArgumentException("Nội dung quá dài (tối đa 5000 ký tự)");

            // --- TẠO MODEL VÀ LƯU ---
            ContactMessage contactMessage = new ContactMessage();
            contactMessage.setCustomerId(customerId);
            contactMessage.setFullName(fullName.trim());
            contactMessage.setEmail(email.trim());
            contactMessage.setPhone(phone != null ? phone.trim() : "");
            contactMessage.setSubject(subject != null ? subject.trim() : "");
            contactMessage.setMessage(message.trim());
            contactMessage.setStatus("NEW");
            contactMessage.setIpAddress(ipAddress);

            contactMessageService.createMessage(contactMessage);

            // --- THÀNH CÔNG ---
            request.setAttribute("success", "Cảm ơn bạn đã liên hệ! Chúng tôi sẽ phản hồi sớm nhất.");
            // Xóa dữ liệu form để tránh gửi lại khi F5
            request.removeAttribute("formSubject");
            request.removeAttribute("formMessage");

        } catch (IllegalArgumentException e) {
            // Lỗi Validate
            request.setAttribute("error", e.getMessage());
            // Giữ lại dữ liệu cũ để khách không phải nhập lại
            request.setAttribute("formFullName", fullName);
            request.setAttribute("formEmail", email);
            request.setAttribute("formPhone", phone);
            request.setAttribute("formSubject", subject);
            request.setAttribute("formMessage", message);

        } catch (Exception e) {
            // Lỗi hệ thống
            e.printStackTrace();
            request.setAttribute("error", "Đã có lỗi xảy ra. Vui lòng thử lại sau.");
        }

        request.getRequestDispatcher("/WEB-INF/jsp/support/contact.jsp").forward(request, response);
    }

    // --- UTILITIES ---
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) return xRealIp;
        return request.getRemoteAddr();
    }

    private boolean isValidEmail(String email) {
        if (email == null) return false;
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    private boolean isValidPhone(String phone) {
        if (phone == null) return false;
        return phone.matches("^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$");
    }
}