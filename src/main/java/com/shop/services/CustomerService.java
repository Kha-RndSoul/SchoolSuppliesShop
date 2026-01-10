package com.shop.services;

import com.shop.dao.support.CustomerDAO;
import com.shop.model.Customer;
import com.shop.util.PasswordUtil;

import java.util.List;

/**
 * Service class for Customer business logic
 */
public class CustomerService {

    private final CustomerDAO customerDAO;

    /**
     * Constructor - khởi tạo CustomerDAO
     */
    public CustomerService() {
        this.customerDAO = new CustomerDAO();
    }

    /**
     * Lấy danh sách tất cả khách hàng
     */
    public List<Customer> getAllCustomers() {
        return customerDAO.getList();
    }

    /**
     * Lấy khách hàng theo ID
     */
    public Customer getCustomerById(int id) {
        return customerDAO.getCustomerById(id);
    }

    /**
     * Lấy khách hàng theo email
     */
    public Customer getCustomerByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email không được rỗng");
        }
        return customerDAO.getByEmail(email);
    }

    /**
     * Tìm kiếm khách hàng theo từ khóa (tên hoặc email)
     */
    public List<Customer> searchCustomers(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Từ khóa tìm kiếm không được rỗng");
        }
        return customerDAO.search(keyword);
    }

    /**
     * Đăng ký khách hàng mới
     * Hash password với BCrypt trước khi lưu
     */
    public void registerCustomer(Customer customer) {
        System.out.println(" CustomerService.registerCustomer() called");

        // Validation
        if (customer == null) {
            throw new IllegalArgumentException("Thông tin khách hàng không được null");
        }
        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email không được rỗng");
        }
        if (!isValidEmail(customer.getEmail())) {
            throw new IllegalArgumentException("Email không hợp lệ");
        }
        if (customer.getPasswordHash() == null || customer.getPasswordHash().trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không được rỗng");
        }
        if (customer.getFullName() == null || customer.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Họ tên không được rỗng");
        }

        // Kiểm tra email đã tồn tại
        Customer existingCustomer = customerDAO.getByEmail(customer.getEmail());
        if (existingCustomer != null) {
            throw new IllegalArgumentException("Email đã được sử dụng");
        }

        // Hash password với BCrypt
        System.out.println("→ Hashing password with BCrypt...");
        String plainPassword = customer.getPasswordHash();
        String hashedPassword = PasswordUtil.hashPassword(plainPassword);
        customer.setPasswordHash(hashedPassword);
        System.out.println(" Password hashed successfully");
        System.out.println("   Plain password length: " + plainPassword.length() + " chars");
        System.out.println("   Hashed password length: " + hashedPassword.length() + " chars");

        customerDAO.insertCustomer(customer);
        System.out.println(" Customer registered successfully");
    }

    /**
     * Thêm nhiều khách hàng cùng lúc (batch insert)
     */
    public void createCustomers(List<Customer> customers) {
        if (customers == null || customers.isEmpty()) {
            throw new IllegalArgumentException("Danh sách khách hàng không được rỗng");
        }
        customerDAO.insertBatch(customers);
    }

    /**
     * Đăng nhập khách hàng
     */
    public Customer login(String email, String plainPassword) {
        System.out.println("================================");
        System.out.println(" CustomerService.login() called");
        System.out.println("   Email: " + email);
        System.out.println("   Password length: " + (plainPassword != null ? plainPassword.length() + " chars" : "null"));

        // Validation
        if (email == null || email.trim().isEmpty()) {
            System.out.println(" Validation failed: Email is empty");
            throw new IllegalArgumentException("Email không được rỗng");
        }
        if (plainPassword == null || plainPassword.trim().isEmpty()) {
            System.out.println(" Validation failed: Password is empty");
            throw new IllegalArgumentException("Mật khẩu không được rỗng");
        }

        // Get customer by email
        System.out.println("→ Looking up customer by email...");
        Customer customer = customerDAO.getByEmail(email.trim());

        if (customer == null) {
            System.out.println(" Customer not found with email: " + email);
            throw new IllegalArgumentException("Email hoặc mật khẩu không đúng");
        }

        System.out.println(" Customer found:");
        System.out.println("   ID: " + customer.getId());
        System.out.println("   Name: " + customer.getFullName());
        System.out.println("   Email: " + customer.getEmail());

        // Get hashed password from database
        String hashedPasswordFromDB = customer.getPasswordHash();
        System.out.println("   Password hash from DB: " + hashedPasswordFromDB.substring(0, Math.min(20, hashedPasswordFromDB.length())) + "...");
        System.out.println("   Hash length: " + hashedPasswordFromDB.length() + " chars");

        // Verify password với BCrypt
        System.out.println("→ Verifying password with BCrypt...");
        boolean isPasswordCorrect = PasswordUtil.verifyPassword(plainPassword, hashedPasswordFromDB);

        if (!isPasswordCorrect) {
            System.out.println(" Password verification FAILED");
            System.out.println("================================");
            throw new IllegalArgumentException("Email hoặc mật khẩu không đúng");
        }

        System.out.println(" Password verification SUCCESSFUL");
        System.out.println("================================");

        return customer;
    }

    /**
     * Cập nhật thông tin khách hàng (không update password)
     */
    public void updateCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Thông tin khách hàng không được null");
        }
        if (customer.getId() <= 0) {
            throw new IllegalArgumentException("ID khách hàng không hợp lệ");
        }

        Customer existingCustomer = customerDAO.getCustomerById(customer.getId());
        if (existingCustomer == null) {
            throw new IllegalArgumentException("Không tìm thấy khách hàng với ID: " + customer.getId());
        }

        if (customer.getFullName() == null || customer.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Họ tên không được rỗng");
        }

        customerDAO.updateCustomer(customer);
    }

    /**
     * Cập nhật mật khẩu khách hàng
     */
    public void updatePassword(int id, String oldPassword, String newPassword) {
        System.out.println(" CustomerService.updatePassword() called for customer ID: " + id);

        // Validation
        if (id <= 0) {
            throw new IllegalArgumentException("ID khách hàng không hợp lệ");
        }
        if (oldPassword == null || oldPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu cũ không được rỗng");
        }
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu mới không được rỗng");
        }
        if (newPassword.length() < 8) {
            throw new IllegalArgumentException("Mật khẩu mới phải có ít nhất 8 ký tự");
        }

        Customer existingCustomer = customerDAO.getCustomerById(id);
        if (existingCustomer == null) {
            throw new IllegalArgumentException("Không tìm thấy khách hàng với ID: " + id);
        }

        // Verify old password
        System.out.println("→ Verifying old password...");
        boolean isOldPasswordCorrect = PasswordUtil.verifyPassword(oldPassword, existingCustomer.getPasswordHash());

        if (!isOldPasswordCorrect) {
            System.out.println(" Old password verification failed");
            throw new IllegalArgumentException("Mật khẩu cũ không đúng");
        }

        System.out.println(" Old password verified");

        // Hash new password
        System.out.println("→ Hashing new password...");
        String hashedNewPassword = PasswordUtil.hashPassword(newPassword);
        System.out.println(" New password hashed");

        customerDAO.updatePassword(id, hashedNewPassword);
        System.out.println(" Password updated successfully");
    }

    /**
     * Đặt lại mật khẩu (không cần mật khẩu cũ - dùng cho admin hoặc quên mật khẩu)
     */
    public void resetPassword(int id, String newPassword) {
        System.out.println(" CustomerService.resetPassword() called for customer ID: " + id);

        // Validation
        if (id <= 0) {
            throw new IllegalArgumentException("ID khách hàng không hợp lệ");
        }
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu mới không được rỗng");
        }
        if (newPassword.length() < 8) {
            throw new IllegalArgumentException("Mật khẩu mới phải có ít nhất 8 ký tự");
        }

        Customer existingCustomer = customerDAO.getCustomerById(id);
        if (existingCustomer == null) {
            throw new IllegalArgumentException("Không tìm thấy khách hàng với ID: " + id);
        }

        // Hash new password
        System.out.println("→ Hashing new password...");
        String hashedNewPassword = PasswordUtil.hashPassword(newPassword);
        System.out.println(" New password hashed");

        customerDAO.updatePassword(id, hashedNewPassword);
        System.out.println(" Password reset successfully");
    }

    /**
     * Xóa khách hàng
     */
    public void deleteCustomer(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID khách hàng không hợp lệ");
        }

        Customer existingCustomer = customerDAO.getCustomerById(id);
        if (existingCustomer == null) {
            throw new IllegalArgumentException("Không tìm thấy khách hàng với ID: " + id);
        }

        customerDAO.deleteCustomer(id);
    }

    /**
     * Đếm tổng số khách hàng
     */
    public int getTotalCustomers() {
        return customerDAO.count();
    }

    /**
     * Kiểm tra khách hàng có tồn tại không
     */
    public boolean customerExists(int id) {
        return customerDAO.getCustomerById(id) != null;
    }

    /**
     * Kiểm tra email đã được sử dụng chưa
     */
    public boolean isEmailExists(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return customerDAO.getByEmail(email) != null;
    }

    /**
     * Xác thực thông tin đăng nhập
     */
    public boolean validateLogin(String email, String plainPassword) {
        try {
            Customer customer = login(email, plainPassword);
            return customer != null;
        } catch (Exception e) {
            return false;
        }
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
}