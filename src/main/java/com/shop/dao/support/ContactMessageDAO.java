package com.shop.dao.support;

import com. shop.model.ContactMessage;
import org.jdbi.v3.core.statement.PreparedBatch;

import java.util.*;

public class ContactMessageDAO extends BaseDao {

    static Map<Integer, ContactMessage> data = new HashMap<>();
    static {
        data.put(1, new ContactMessage(1, 1, "Nguyễn Văn A", "customer1@email.com", "0901234567", "Hỏi về sản phẩm", "Sản phẩm balo có màu xanh không?", "NEW", null, "192.168.1.1",null,null));
        data.put(2, new ContactMessage(2, 2, "Trần Thị B", "customer2@email.com", "0912345678", "Vấn đề giao hàng", "Đơn hàng của tôi chưa nhận được", "PROCESSING", null, "192.168.1.2",null,null));
        data.put(3, new ContactMessage(3, 3, "Lê Văn C", "guest@email.com", "0923456789", "Yêu cầu hợp tác", "Tôi muốn trở thành đối tác", "NEW", null, "192.168.1.3",null,null));
    }

    public List<ContactMessage> getListContactMessage() {
        return new ArrayList<>(data. values());
    }

    public ContactMessage getContactMessage(int id) {
        return data.get(id);
    }

    public List<ContactMessage> getList() {
        return get().withHandle(h ->
                h. createQuery("SELECT id, customer_id AS customerId, full_name, email, phone, subject, message, status, admin_reply, ip_address, created_at, replied_at FROM contact_messages ORDER BY created_at DESC")
                        .mapToBean(ContactMessage. class)
                        .list()
        );
    }

    public ContactMessage getContactMessageById(int id) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, customer_id AS customerId, full_name, email, phone, subject, message, status, admin_reply, ip_address, created_at, replied_at FROM contact_messages WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(ContactMessage.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public List<ContactMessage> getByStatus(String status) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, customer_id AS customerId, full_name, email, phone, subject, message, status, admin_reply, ip_address, created_at, replied_at FROM contact_messages WHERE status = :status ORDER BY created_at DESC")
                        .bind("status", status)
                        .mapToBean(ContactMessage.class)
                        .list()
        );
    }

    public List<ContactMessage> getByCustomer(int customerId) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, customer_id AS customerId, full_name, email, phone, subject, message, status, admin_reply, ip_address, created_at, replied_at FROM contact_messages WHERE customer_id = :customerId ORDER BY created_at DESC")
                        .bind("customerId", customerId)
                        .mapToBean(ContactMessage.class)
                        .list()
        );
    }

    public void insert(List<ContactMessage> messages) {
        get().useHandle(h -> {
            PreparedBatch batch = h.prepareBatch(
                    "INSERT INTO contact_messages (id, customer_id AS customerId, full_name, email, phone, subject, message, ip_address) VALUES (:id, :customerId, :fullName, :email, :phone, :subject, :message, :ipAddress)"
            );
            messages.forEach(m -> batch.bindBean(m).add());
            batch.execute();
        });
    }

    public void insertContactMessage(ContactMessage message) {
        get().useHandle(h -> {
            h.createUpdate("INSERT INTO contact_messages (customer_id AS customerId, full_name, email, phone, subject, message, ip_address) VALUES (:customerId, :fullName, :email, : phone, :subject, :message, :ipAddress)")
                    .bindBean(message)
                    .execute();
        });
    }

    public void updateStatus(int id, String status) {
        get().useHandle(h -> {
            h.createUpdate("UPDATE contact_messages SET status = :status WHERE id = :id")
                    .bind("id", id)
                    .bind("status", status)
                    .execute();
        });
    }

    public void reply(int id, String adminReply) {
        get().useHandle(h -> {
            h.createUpdate("UPDATE contact_messages SET admin_reply = :adminReply, status = 'REPLIED', replied_at = CURRENT_TIMESTAMP WHERE id = :id")
                    .bind("id", id)
                    .bind("adminReply", adminReply)
                    .execute();
        });
    }

    public void deleteContactMessage(int id) {
        get().useHandle(h -> {
            h.createUpdate("DELETE FROM contact_messages WHERE id = : id")
                    .bind("id", id)
                    .execute();
        });
    }

    public int countByStatus(String status) {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(*) FROM contact_messages WHERE status = :status")
                        .bind("status", status)
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public int countNew() {
        return get().withHandle(h ->
                h. createQuery("SELECT COUNT(*) FROM contact_messages WHERE status = 'NEW'")
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public static void main(String[] args) {
        ContactMessageDAO dao = new ContactMessageDAO();
        List<ContactMessage> messages = dao. getListContactMessage();
        dao.insert(messages);
        System.out.println(" Inserted " + messages.size() + " contact messages");

        dao.getByStatus("NEW").forEach(System.out::println);
    }
}