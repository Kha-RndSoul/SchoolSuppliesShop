package com.shop.services;

import com.shop.dao.support.CustomerDAO;
import com.shop.model.Customer;

import java.util.List;

public class CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerService() {
        this.customerDAO = new CustomerDAO();
    }

    //Lấy danh sách tất cả khách hàng
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
     */
    public void registerCustomer(Customer customer) {
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

        customerDAO.insertCustomer(customer);
    }

    /**
     * Thêm nhiều khách hàng cùng lúc
     */
    public void createCustomers(List<Customer> customers) {
        if (customers == null || customers.isEmpty()) {
            throw new IllegalArgumentException("Danh sách khách hàng không được rỗng");
        }
        customerDAO.insert(customers);
    }

    /**
     * Đăng nhập khách hàng
     */
    public Customer login(String email, String passwordHash) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email không được rỗng");
        }
        if (passwordHash == null || passwordHash.trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không được rỗng");
        }

        Customer customer = customerDAO.login(email, passwordHash);
        if (customer == null) {
            throw new IllegalArgumentException("Email hoặc mật khẩu không đúng");
        }

        return customer;
    }

    /**
     * Cập nhật thông tin khách hàng
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
    public void updatePassword(int id, String oldPasswordHash, String newPasswordHash) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID khách hàng không hợp lệ");
        }
        if (newPasswordHash == null || newPasswordHash.trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu mới không được rỗng");
        }

        Customer existingCustomer = customerDAO.getCustomerById(id);
        if (existingCustomer == null) {
            throw new IllegalArgumentException("Không tìm thấy khách hàng với ID: " + id);
        }

        // Kiểm tra mật khẩu cũ
        if (!existingCustomer.getPasswordHash().equals(oldPasswordHash)) {
            throw new IllegalArgumentException("Mật khẩu cũ không đúng");
        }

        customerDAO.updatePassword(id, newPasswordHash);
    }

    /**
     * Đặt lại mật khẩu (không cần mật khẩu cũ - dùng cho admin hoặc quên mật khẩu)
     */
    public void resetPassword(int id, String newPasswordHash) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID khách hàng không hợp lệ");
        }
        if (newPasswordHash == null || newPasswordHash.trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu mới không được rỗng");
        }

        Customer existingCustomer = customerDAO.getCustomerById(id);
        if (existingCustomer == null) {
            throw new IllegalArgumentException("Không tìm thấy khách hàng với ID: " + id);
        }

        customerDAO.updatePassword(id, newPasswordHash);
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
    public boolean validateLogin(String email, String passwordHash) {
        try {
            Customer customer = login(email, passwordHash);
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
