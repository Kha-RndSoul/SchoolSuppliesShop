package com.shop.dao.support;

import com. shop.model.Customer;
import org.jdbi.v3.core.statement.PreparedBatch;

import java.util.*;

public class CustomerDAO extends BaseDao {

    static Map<Integer, Customer> data = new HashMap<>();
    static {
        data.put(1, new Customer(1, "customer1@email.com", "pass123", "Nguyễn Văn A", "0901234567", "123 Nguyễn Văn A, Q1, HCM",null,null));
        data.put(2, new Customer(2, "customer2@email.com", "pass456", "Trần Thị B", "0912345678", "456 Lê Văn B, Q3, HCM",null,null));
        data.put(3, new Customer(3, "customer3@email. com", "pass789", "Lê Văn C", "0923456789", "789 Trần Văn C, Q5, HCM",null,null));
        data.put(4, new Customer(4, "customer4@email.com", "pass000", "Phạm Thị D", "0934567890", "111 Phạm Văn D, Q7, HCM",null,null));
    }

    public List<Customer> getListCustomer() {
        return new ArrayList<>(data.values());
    }

    public Customer getCustomer(int id) {
        return data.get(id);
    }

    public List<Customer> getList() {
        return get().withHandle(h ->
                h.createQuery("SELECT id, email, password_hash, full_name, phone, address, created_at, updated_at FROM customers ORDER BY created_at DESC")
                        .mapToBean(Customer.class)
                        .list()
        );
    }

    public Customer getCustomerById(int id) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, email, password_hash, full_name, phone, address, created_at, updated_at FROM customers WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(Customer.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public Customer getByEmail(String email) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, email, password_hash, full_name, phone, address, created_at, updated_at FROM customers WHERE email = :email")
                        .bind("email", email)
                        .mapToBean(Customer.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public Customer login(String email, String password_hash) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, email, password_hash, full_name, phone, address, created_at, updated_at FROM customers WHERE email = :email AND password = :password")
                        .bind("email", email)
                        .bind("password_hash", password_hash)
                        .mapToBean(Customer.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public List<Customer> search(String keyword) {
        return get().withHandle(h ->
                h.createQuery("SELECT id, email, password_hash, full_name, phone, address, created_at, updated_at FROM customers WHERE full_name LIKE CONCAT('%', :keyword, '%') OR email LIKE CONCAT('%', :keyword, '%') ORDER BY created_at DESC")
                        .bind("keyword", keyword)
                        .mapToBean(Customer. class)
                        .list()
        );
    }

    public void insert(List<Customer> customers) {
        get().useHandle(h -> {
            PreparedBatch batch = h.prepareBatch(
                    "INSERT INTO customers (id, email, password_hash, full_name, phone, address) VALUES (:id, :email, :password, :fullName, : phone, :address)"
            );
            customers.forEach(c -> batch.bindBean(c).add());
            batch.execute();
        });
    }

    public void insertCustomer(Customer customer) {
        get().useHandle(h -> {
            h.createUpdate("INSERT INTO customers (email, password_hash, full_name, phone, address) VALUES (:email, :password, :fullName, :phone, :address)")
                    .bindBean(customer)
                    .execute();
        });
    }

    public void updateCustomer(Customer customer) {
        get().useHandle(h -> {
            h.createUpdate("UPDATE customers SET full_name = :fullName, phone = :phone, address = :address, updated_at = CURRENT_TIMESTAMP WHERE id = :id")
                    .bindBean(customer)
                    .execute();
        });
    }

    public void updatePassword(int id, String password_hash) {
        get().useHandle(h -> {
            h. createUpdate("UPDATE customers SET password_hash = :password_hash, updated_at = CURRENT_TIMESTAMP WHERE id = : id")
                    .bind("id", id)
                    .bind("password_hash", password_hash)
                    .execute();
        });
    }

    public void deleteCustomer(int id) {
        get().useHandle(h -> {
            h.createUpdate("DELETE FROM customers WHERE id = :id")
                    .bind("id", id)
                    .execute();
        });
    }

    public int count() {
        return get().withHandle(h ->
                h. createQuery("SELECT COUNT(*) FROM customers")
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public static void main(String[] args) {
        CustomerDAO dao = new CustomerDAO();
        List<Customer> customers = dao.getListCustomer();
        dao.insert(customers);
        System.out. println(" Inserted " + customers.size() + " customers");

        Customer customer = dao.login("customer1@email.com", "pass123");
        System.out.println(customer != null ? " Login success:  " + customer :  " Login failed");
    }
}