package com.shop.controller.support;

import com.shop.model.ContactMessage;
import com.shop.model.Customer;
import com.shop.services.ContactMessageService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

/**
 * Servlet xử lý trang liên hệ
 */
@WebServlet(name = "ContactServlet", urlPatterns = {"/contact"})
public class ContactServlet extends HttpServlet {

    private ContactMessageService contactMessageService;

    @Override
    public void init() {
        System.out.println("================================");
        System.out.println(" ContactServlet.init() STARTING...");

        try {
            System.out.println("→ Creating ContactMessageService...");
            contactMessageService = new ContactMessageService();
            System.out.println(" ContactMessageService created successfully");

            System.out.println(" ContactServlet initialized");

        } catch (Exception e) {
            System.err.println(" ERROR in ContactServlet.init() ");
            System.err.println("Exception: " + e.getClass().getName());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize ContactServlet", e);
        }

        System.out.println("================================");
    }

    /**
     * GET /contact - Hiển thị trang liên hệ
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("================================");
        System.out.println(" ContactServlet.doGet() CALLED");
        System.out.println("   Request URI: " + request.getRequestURI());
        System.out.println("   Context Path: " + request.getContextPath());

        // Check nếu user đã đăng nhập → Pre-fill thông tin
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("customer") != null) {
            Customer customer = (Customer) session.getAttribute("customer");
            System.out.println(" User logged in: " + customer.getEmail());

            // Pre-fill customer info vào form
            request.setAttribute("customerFullName", customer.getFullName());
            request.setAttribute("customerEmail", customer.getEmail());
            request.setAttribute("customerPhone", customer.getPhone());
        } else {
            System.out.println("→ Guest user (not logged in)");
        }

        System.out.println("================================");

        // Forward tới contact.jsp
        System.out.println("→ Forwarding to /WEB-INF/jsp/support/contact.jsp");
        request.getRequestDispatcher("/WEB-INF/jsp/support/contact.jsp").forward(request, response);
    }

    /**
     * POST /contact - Xử lý gửi tin nhắn liên hệ
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("================================");
        System.out.println(" ContactServlet.doPost() CALLED");

        // Get parameters từ form
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");

        // Get customer ID nếu đã đăng nhập
        int customerId = 0;
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("customer") != null) {
            Customer customer = (Customer) session.getAttribute("customer");
            customerId = customer.getId();
        }

        // Get IP address
        String ipAddress = getClientIpAddress(request);

        // ========== DEBUG LOG ==========
        System.out.println("→ Contact message data:");
        System.out.println("   Customer ID: " + (customerId > 0 ? customerId : "Guest"));
        System.out.println("   Full Name: [" + fullName + "]");
        System.out.println("   Email: [" + email + "]");
        System.out.println("   Phone: [" + phone + "]");
        System.out.println("   Subject: [" + subject + "]");
        System.out.println("   Message length: " + (message != null ? message.length() + " chars" : "null"));
        System.out.println("   IP Address: " + ipAddress);
        System.out.println("================================");
        // ===============================

        try {
            // ========== VALIDATION ==========

            // 1. Check empty fields
            if (fullName == null || fullName.trim().isEmpty()) {
                throw new IllegalArgumentException("Họ tên không được rỗng");
            }
            if (email == null || email.trim().isEmpty()) {
                throw new IllegalArgumentException("Email không được rỗng");
            }
            if (message == null || message.trim().isEmpty()) {
                throw new IllegalArgumentException("Nội dung tin nhắn không được rỗng");
            }

            // 2. Validate email format
            if (!isValidEmail(email)) {
                throw new IllegalArgumentException("Email không hợp lệ");
            }

            // 3. Validate phone (optional)
            if (phone != null && !phone.trim().isEmpty()) {
                if (!isValidPhone(phone)) {
                    throw new IllegalArgumentException("Số điện thoại không hợp lệ");
                }
            }

            // 4. Check message length
            if (message.trim().length() < 10) {
                throw new IllegalArgumentException("Nội dung tin nhắn phải có ít nhất 10 ký tự");
            }
            if (message.trim().length() > 5000) {
                throw new IllegalArgumentException("Nội dung tin nhắn không được vượt quá 5000 ký tự");
            }

            System.out.println(" Validation passed");

            // ========== CREATE CONTACT MESSAGE ==========

            System.out.println("→ Creating contact message...");
            ContactMessage contactMessage = new ContactMessage();
            contactMessage.setCustomerId(customerId); // 0 if guest
            contactMessage.setFullName(fullName.trim());
            contactMessage.setEmail(email.trim());
            contactMessage.setPhone(phone != null ? phone.trim() : "");
            contactMessage.setSubject(subject != null ? subject.trim() : "");
            contactMessage.setMessage(message.trim());
            contactMessage.setStatus("NEW");
            contactMessage.setIpAddress(ipAddress);

            // Call service to save
            System.out.println("→ Calling ContactMessageService.createMessage()...");
            contactMessageService.createMessage(contactMessage);

            System.out.println(" Contact message saved successfully!");
            System.out.println("   From: " + fullName + " <" + email + ">");
            System.out.println("================================");

            // ========== SET SUCCESS MESSAGE ==========

            request.setAttribute("success", "Cảm ơn bạn đã liên hệ! Chúng tôi sẽ phản hồi trong thời gian sớm nhất.");

            // Clear form sau khi submit thành công
            request.removeAttribute("customerFullName");
            request.removeAttribute("customerEmail");
            request.removeAttribute("customerPhone");

            // Forward lại contact.jsp
            request.getRequestDispatcher("/WEB-INF/jsp/support/contact.jsp").forward(request, response);

        } catch (IllegalArgumentException e) {
            // Validation failed → Show error
            request.setAttribute("error", e.getMessage());

            // Giữ lại thông tin đã nhập
            request.setAttribute("customerFullName", fullName);
            request.setAttribute("customerEmail", email);
            request.setAttribute("customerPhone", phone);
            request.setAttribute("formSubject", subject);
            request.setAttribute("formMessage", message);

            System.out.println(" Contact message submission failed");
            System.out.println("   Error: " + e.getMessage());
            System.out.println("================================");

            // Forward lại contact.jsp với error message
            request.getRequestDispatcher("/WEB-INF/jsp/support/contact.jsp").forward(request, response);

        } catch (Exception e) {
            // Unexpected error
            System.err.println(" UNEXPECTED ERROR in ContactServlet.doPost() ");
            System.err.println("Exception: " + e.getClass().getName());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            System.err.println("================================");

            request.setAttribute("error", "Đã có lỗi xảy ra. Vui lòng thử lại sau.");
            request.setAttribute("customerFullName", fullName);
            request.setAttribute("customerEmail", email);
            request.setAttribute("customerPhone", phone);
            request.setAttribute("formSubject", subject);
            request.setAttribute("formMessage", message);

            request.getRequestDispatcher("/WEB-INF/jsp/support/contact.jsp").forward(request, response);
        }
    }

    /**
     * Get client IP address
     */
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }

        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }

        return request.getRemoteAddr();
    }

    /**
     * Validate email format
     */
    private boolean isValidEmail(String email) {
        if (email == null) return false;
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    /**
     * Validate số điện thoại Việt Nam
     */
    private boolean isValidPhone(String phone) {
        if (phone == null) return false;
        String phoneRegex = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";
        return phone.matches(phoneRegex);
    }

    @Override
    public void destroy() {
        System.out.println(" ContactServlet destroyed");
    }
}