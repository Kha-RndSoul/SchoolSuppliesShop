package com.shop.dao.support;

import com.shop.model.Customer;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org. jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi. v3.sqlobject.statement.GetGeneratedKeys;
import org. jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject. statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(Customer. class)
public interface CustomerDAO {

    // Danh sách tất cả khách hàng
    @SqlQuery("SELECT id, email, password, full_name, phone, address, created_at, updated_at " +
            "FROM customers ORDER BY created_at DESC")
    List<Customer> getAll();

    // Danh sách khách hàng theo id
    @SqlQuery("SELECT id, email, password, full_name, phone, address, created_at, updated_at " +
            "FROM customers WHERE id = :id")
    Optional<Customer> getById(@Bind("id") int id);

    // Danh sách khách hàng theo email
    @SqlQuery("SELECT id, email, password, full_name, phone, address, created_at, updated_at " +
            "FROM customers WHERE email = :email")
    Optional<Customer> getByEmail(@Bind("email") String email);

    // Đăng nhập
    @SqlQuery("SELECT id, email, password, full_name, phone, address, created_at, updated_at " +
            "FROM customers WHERE email = :email AND password = :password")
    Optional<Customer> login(@Bind("email") String email, @Bind("password") String password);

    // Insert
    @SqlUpdate("INSERT INTO customers (email, password, full_name, phone, address) " +
            "VALUES (:email, :password, :fullName, :phone, :address)")
    @GetGeneratedKeys
    int insert(@BindBean Customer customer);

    // Update
    @SqlUpdate("UPDATE customers SET full_name = :fullName, phone = :phone, address = :address, " +
            "updated_at = CURRENT_TIMESTAMP WHERE id = :id")
    void update(@BindBean Customer customer);

    // Update mật khẩu
    @SqlUpdate("UPDATE customers SET password = :password, updated_at = CURRENT_TIMESTAMP WHERE id = :id")
    void updatePassword(@Bind("id") int id, @Bind("password") String password);

    // Delete
    @SqlUpdate("DELETE FROM customers WHERE id = :id")
    void delete(@Bind("id") int id);

    // Đếm số lượng khách hàng
    @SqlQuery("SELECT COUNT(*) FROM customers")
    int count();

    // Tìm theo tên hoặc email
    @SqlQuery("SELECT id, email, password, full_name, phone, address, created_at, updated_at " +
            "FROM customers WHERE full_name LIKE :keyword OR email LIKE :keyword " +
            "ORDER BY created_at DESC")
    List<Customer> search(@Bind("keyword") String keyword);
}
