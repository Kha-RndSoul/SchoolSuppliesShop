package com.shop.dao.support;

import com.shop.model.ContactMessage;
import org.jdbi.v3.core.statement.PreparedBatch;

import java.util.*;

public class ContactMessageDAO extends BaseDao {

    /**
     * Lấy danh sách tất cả tin nhắn liên hệ
     */
    public List<ContactMessage> getList() {
        return get().withHandle(h ->
                h.createQuery("SELECT id, customer_id, full_name, email, phone, subject, message, " +
                                "status, admin_reply, ip_address, created_at, replied_at " +
                                "FROM contact_messages ORDER BY created_at DESC")
                        .mapToBean(ContactMessage.class)
                        .list()
        );
    }

    /**
     * Lấy tin nhắn theo ID
     */
    public ContactMessage getContactMessageById(int id) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, customer_id, full_name, email, phone, subject, message, " +
                                "status, admin_reply, ip_address, created_at, replied_at " +
                                "FROM contact_messages WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(ContactMessage.class)
                        .findOne()
                        .orElse(null)
        );
    }

    /**
     * Lấy tin nhắn theo trạng thái
     */
    public List<ContactMessage> getByStatus(String status) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, customer_id, full_name, email, phone, subject, message, " +
                                "status, admin_reply, ip_address, created_at, replied_at " +
                                "FROM contact_messages WHERE status = :status ORDER BY created_at DESC")
                        .bind("status", status)
                        .mapToBean(ContactMessage.class)
                        .list()
        );
    }

    /**
     * Lấy tin nhắn theo customer
     */
    public List<ContactMessage> getByCustomer(int customerId) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, customer_id, full_name, email, phone, subject, message, " +
                                "status, admin_reply, ip_address, created_at, replied_at " +
                                "FROM contact_messages WHERE customer_id = :customerId ORDER BY created_at DESC")
                        .bind("customerId", customerId)
                        .mapToBean(ContactMessage.class)
                        .list()
        );
    }

    /**
     * Insert tin nhắn mới
     */
    public void insertContactMessage(ContactMessage message) {
        get().useHandle(h -> {
            h.createUpdate("INSERT INTO contact_messages " +
                            "(customer_id, full_name, email, phone, subject, message, status, ip_address) " +
                            "VALUES (:customerId, :fullName, :email, :phone, :subject, :message, :status, :ipAddress)")
                    .bindBean(message)
                    .execute();
        });
    }

    /**
     * Insert nhiều tin nhắn
     */
    public void insertBatch(List<ContactMessage> messages) {
        get().useHandle(h -> {
            PreparedBatch batch = h.prepareBatch(
                    "INSERT INTO contact_messages " +
                            "(customer_id, full_name, email, phone, subject, message, status, ip_address) " +
                            "VALUES (:customerId, :fullName, :email, :phone, :subject, :message, :status, :ipAddress)"
            );
            messages.forEach(m -> batch.bindBean(m).add());
            batch.execute();
        });
    }

    /**
     * Cập nhật trạng thái
     */
    public void updateStatus(int id, String status) {
        get().useHandle(h -> {
            h.createUpdate("UPDATE contact_messages SET status = :status WHERE id = :id")
                    .bind("id", id)
                    .bind("status", status)
                    .execute();
        });
    }

    /**
     * Trả lời tin nhắn
     */
    public void reply(int id, String adminReply) {
        get().useHandle(h -> {
            h.createUpdate("UPDATE contact_messages " +
                            "SET admin_reply = :adminReply, status = 'REPLIED', replied_at = CURRENT_TIMESTAMP " +
                            "WHERE id = :id")
                    .bind("id", id)
                    .bind("adminReply", adminReply)
                    .execute();
        });
    }

    /**
     * Xóa tin nhắn
     */
    public void deleteContactMessage(int id) {
        get().useHandle(h -> {
            h.createUpdate("DELETE FROM contact_messages WHERE id = :id")
                    .bind("id", id)
                    .execute();
        });
    }

    /**
     * Đếm tin nhắn theo trạng thái
     */
    public int countByStatus(String status) {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(*) FROM contact_messages WHERE status = :status")
                        .bind("status", status)
                        .mapTo(Integer.class)
                        .one()
        );
    }

    /**
     * Đếm tin nhắn mới
     */
    public int countNew() {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(*) FROM contact_messages WHERE status = 'NEW'")
                        .mapTo(Integer.class)
                        .one()
        );
    }
}