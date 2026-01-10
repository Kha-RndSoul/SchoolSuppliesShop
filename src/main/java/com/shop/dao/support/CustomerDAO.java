package com.shop.dao.support;

import com.shop.model.Customer;
import org.jdbi.v3.core.statement.PreparedBatch;
import java.util.*;

public class CustomerDAO extends BaseDao {

    /**
     * Lấy danh sách tất cả customers từ database
     */
    public List<Customer> getList() {
        return get().withHandle(h ->
                h.createQuery("SELECT id, email, password_hash, full_name, phone, address, created_at, updated_at " +
                                "FROM customers ORDER BY created_at DESC")
                        .mapToBean(Customer.class)
                        .list()
        );
    }

    /**
     * Lấy customer theo ID
     */
    public Customer getCustomerById(int id) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, email, password_hash, full_name, phone, address, created_at, updated_at " +
                                "FROM customers WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(Customer.class)
                        .findOne()
                        .orElse(null)
        );
    }

    /**
     * Lấy customer theo email
     */
    public Customer getByEmail(String email) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, email, password_hash, full_name, phone, address, created_at, updated_at " +
                                "FROM customers WHERE email = :email")
                        .bind("email", email)
                        .mapToBean(Customer.class)
                        .findOne()
                        .orElse(null)
        );
    }

    /**
     * Tìm kiếm customers theo keyword (tên hoặc email)
     */
    public List<Customer> search(String keyword) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, email, password_hash, full_name, phone, address, created_at, updated_at " +
                                "FROM customers " +
                                "WHERE full_name LIKE CONCAT('%', :keyword, '%') " +
                                "   OR email LIKE CONCAT('%', :keyword, '%') " +
                                "ORDER BY created_at DESC")
                        .bind("keyword", keyword)
                        .mapToBean(Customer.class)
                        .list()
        );
    }

    /**
     * Insert nhiều customers cùng lúc (batch insert)
     */
    public void insertBatch(List<Customer> customers) {
        get().useHandle(h -> {
            PreparedBatch batch = h.prepareBatch(
                    "INSERT INTO customers (id, email, password_hash, full_name, phone, address) " +
                            "VALUES (:id, :email, :passwordHash, :fullName, :phone, :address)"
            );
            customers.forEach(c -> batch.bindBean(c).add());
            batch.execute();
        });
    }

    /**
     * Insert customer mới
     */
    public void insertCustomer(Customer customer) {
        get().useHandle(h -> {
            h.createUpdate("INSERT INTO customers (email, password_hash, full_name, phone, address) " +
                            "VALUES (:email, :passwordHash, :fullName, :phone, :address)")
                    .bindBean(customer)
                    .execute();
        });
    }

    /**
     * Update thông tin customer (không update password)
     */
    public void updateCustomer(Customer customer) {
        get().useHandle(h -> {
            h.createUpdate("UPDATE customers " +
                            "SET full_name = :fullName, " +
                            "    phone = :phone, " +
                            "    address = :address, " +
                            "    updated_at = CURRENT_TIMESTAMP " +
                            "WHERE id = :id")
                    .bindBean(customer)
                    .execute();
        });
    }

    /**
     * Update password của customer
     */
    public void updatePassword(int id, String passwordHash) {
        get().useHandle(h -> {
            h.createUpdate("UPDATE customers " +
                            "SET password_hash = :passwordHash, " +
                            "    updated_at = CURRENT_TIMESTAMP " +
                            "WHERE id = :id")
                    .bind("id", id)
                    .bind("passwordHash", passwordHash)
                    .execute();
        });
    }

    /**
     * Xóa customer theo ID
     */
    public void deleteCustomer(int id) {
        get().useHandle(h -> {
            h.createUpdate("DELETE FROM customers WHERE id = :id")
                    .bind("id", id)
                    .execute();
        });
    }

    /**
     * Đếm tổng số customers
     */
    public int count() {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(*) FROM customers")
                        .mapTo(Integer.class)
                        .one()
        );
    }

    /**
     * Kiểm tra email đã tồn tại chưa
     */
    public boolean emailExists(String email) {
        return getByEmail(email) != null;
    }
}