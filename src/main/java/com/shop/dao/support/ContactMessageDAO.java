package com.shop.dao.support;

import com.shop. model.ContactMessage;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject. customizer.Bind;
import org.jdbi.v3.sqlobject.customizer. BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement. SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(ContactMessage. class)
public interface ContactMessageDAO {

    // Danh sách tất cả tin nhắn liên hệ
    @SqlQuery("SELECT id, customer_id, full_name, email, phone, subject, message, status, " +
            "admin_reply, ip_address, created_at, replied_at " +
            "FROM contact_messages ORDER BY created_at DESC")
    List<ContactMessage> getAll();

    // Danh sách tin nhắn liên hệ theo id
    @SqlQuery("SELECT id, customer_id, full_name, email, phone, subject, message, status, " +
            "admin_reply, ip_address, created_at, replied_at " +
            "FROM contact_messages WHERE id = :id")
    Optional<ContactMessage> getById(@Bind("id") int id);

    // Danh sách tin nhắn liên hệ theo trạng thái
    @SqlQuery("SELECT id, customer_id, full_name, email, phone, subject, message, status, " +
            "admin_reply, ip_address, created_at, replied_at " +
            "FROM contact_messages WHERE status = :status ORDER BY created_at DESC")
    List<ContactMessage> getByStatus(@Bind("status") String status);

    // Danh sách tin nhắn liên hệ theo khách hàng
    @SqlQuery("SELECT id, customer_id, full_name, email, phone, subject, message, status, " +
            "admin_reply, ip_address, created_at, replied_at " +
            "FROM contact_messages WHERE customer_id = :customerId ORDER BY created_at DESC")
    List<ContactMessage> getByCustomer(@Bind("customerId") int customerId);

    // Insert
    @SqlUpdate("INSERT INTO contact_messages (customer_id, full_name, email, phone, subject, message, ip_address) " +
            "VALUES (: customerId, :fullName, : email, :phone, :subject, :message, :ipAddress)")
    @GetGeneratedKeys
    int insert(@BindBean ContactMessage contactMessage);

    // Cập nhật trạng thái
    @SqlUpdate("UPDATE contact_messages SET status = :status WHERE id = :id")
    void updateStatus(@Bind("id") int id, @Bind("status") String status);

    // Phản hồi từ admin
    @SqlUpdate("UPDATE contact_messages SET admin_reply = :adminReply, status = 'REPLIED', " +
            "replied_at = CURRENT_TIMESTAMP WHERE id = :id")
    void reply(@Bind("id") int id, @Bind("adminReply") String adminReply);

    // Delete
    @SqlUpdate("DELETE FROM contact_messages WHERE id = :id")
    void delete(@Bind("id") int id);

    // Đếm số lượng tin nhắn liên hệ theo trạng thái
    @SqlQuery("SELECT COUNT(*) FROM contact_messages WHERE status = :status")
    int countByStatus(@Bind("status") String status);

    // Đếm số lượng tin nhắn liên hệ mới
    @SqlQuery("SELECT COUNT(*) FROM contact_messages WHERE status = 'NEW'")
    int countNew();
}
