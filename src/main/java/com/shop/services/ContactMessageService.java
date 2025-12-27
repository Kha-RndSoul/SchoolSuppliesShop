package com.shop.services;

import com.shop.dao.support.ContactMessageDAO;
import com.shop.model.ContactMessage;

import java.util.List;

public class ContactMessageService {

    private final ContactMessageDAO contactMessageDAO;

    public ContactMessageService() {
        this.contactMessageDAO = new ContactMessageDAO();
    }

    /**
     * Lấy danh sách tất cả tin nhắn liên hệ
     */
    public List<ContactMessage> getAllMessages() {
        return contactMessageDAO.getList();
    }

    /**
     * Lấy tin nhắn theo ID
     */
    public ContactMessage getMessageById(int id) {
        return contactMessageDAO.getContactMessageById(id);
    }

    /**
     * Lấy danh sách tin nhắn theo trạng thái
     */
    public List<ContactMessage> getMessagesByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Trạng thái không được rỗng");
        }
        return contactMessageDAO.getByStatus(status);
    }

    /**
     * Lấy danh sách tin nhắn của khách hàng
     */
    public List<ContactMessage> getMessagesByCustomer(int customerId) {
        if (customerId <= 0) {
            throw new IllegalArgumentException("ID khách hàng không hợp lệ");
        }
        return contactMessageDAO.getByCustomer(customerId);
    }

    /**
     * Lấy danh sách tin nhắn mới
     */
    public List<ContactMessage> getNewMessages() {
        return contactMessageDAO.getByStatus("NEW");
    }

    /**
     * Lấy danh sách tin nhắn đang xử lý
     */
    public List<ContactMessage> getProcessingMessages() {
        return contactMessageDAO.getByStatus("PROCESSING");
    }

    /**
     * Lấy danh sách tin nhắn đã trả lời
     */
    public List<ContactMessage> getRepliedMessages() {
        return contactMessageDAO.getByStatus("REPLIED");
    }

    /**
     * Tạo tin nhắn liên hệ mới
     */
    public void createMessage(ContactMessage message) {
        if (message == null) {
            throw new IllegalArgumentException("Tin nhắn không được null");
        }
        if (message.getFullName() == null || message.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Họ tên không được rỗng");
        }
        if (message.getEmail() == null || message.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email không được rỗng");
        }
        if (!isValidEmail(message.getEmail())) {
            throw new IllegalArgumentException("Email không hợp lệ");
        }
        if (message.getSubject() == null || message.getSubject().trim().isEmpty()) {
            throw new IllegalArgumentException("Tiêu đề không được rỗng");
        }
        if (message.getMessage() == null || message.getMessage().trim().isEmpty()) {
            throw new IllegalArgumentException("Nội dung tin nhắn không được rỗng");
        }

        contactMessageDAO.insertContactMessage(message);
    }

    /**
     * Cập nhật trạng thái tin nhắn
     */
    public void updateMessageStatus(int id, String status) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID tin nhắn không hợp lệ");
        }
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Trạng thái không được rỗng");
        }

        ContactMessage existingMessage = contactMessageDAO.getContactMessageById(id);
        if (existingMessage == null) {
            throw new IllegalArgumentException("Không tìm thấy tin nhắn với ID: " + id);
        }

        // Validate status
        if (!isValidStatus(status)) {
            throw new IllegalArgumentException("Trạng thái không hợp lệ. Chỉ chấp nhận: NEW, PROCESSING, REPLIED, CLOSED");
        }

        contactMessageDAO.updateStatus(id, status);
    }

    /**
     * Trả lời tin nhắn
     */
    public void replyToMessage(int id, String adminReply) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID tin nhắn không hợp lệ");
        }
        if (adminReply == null || adminReply.trim().isEmpty()) {
            throw new IllegalArgumentException("Nội dung trả lời không được rỗng");
        }

        ContactMessage existingMessage = contactMessageDAO.getContactMessageById(id);
        if (existingMessage == null) {
            throw new IllegalArgumentException("Không tìm thấy tin nhắn với ID: " + id);
        }

        contactMessageDAO.reply(id, adminReply);
    }

    /**
     * Xóa tin nhắn
     */
    public void deleteMessage(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID tin nhắn không hợp lệ");
        }

        ContactMessage existingMessage = contactMessageDAO.getContactMessageById(id);
        if (existingMessage == null) {
            throw new IllegalArgumentException("Không tìm thấy tin nhắn với ID: " + id);
        }

        contactMessageDAO.deleteContactMessage(id);
    }

    /**
     * Đếm số tin nhắn theo trạng thái
     */
    public int countByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Trạng thái không được rỗng");
        }
        return contactMessageDAO.countByStatus(status);
    }

    /**
     * Đếm số tin nhắn mới
     */
    public int countNewMessages() {
        return contactMessageDAO.countNew();
    }

    /**
     * Đánh dấu tin nhắn là đang xử lý
     */
    public void markAsProcessing(int id) {
        updateMessageStatus(id, "PROCESSING");
    }

    /**
     * Đóng tin nhắn
     */
    public void closeMessage(int id) {
        updateMessageStatus(id, "CLOSED");
    }

    /**
     * Kiểm tra tin nhắn có tồn tại không
     */
    public boolean messageExists(int id) {
        return contactMessageDAO.getContactMessageById(id) != null;
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
     * Validate status
     */
    private boolean isValidStatus(String status) {
        if (status == null) return false;
        return status.equals("NEW") || status.equals("PROCESSING") ||
                status.equals("REPLIED") || status.equals("CLOSED");
    }
}
