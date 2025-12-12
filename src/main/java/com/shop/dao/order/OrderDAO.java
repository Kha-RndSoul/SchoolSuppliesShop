package com.shop.dao. order;

import com.shop.model.Order;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org. jdbi.v3.sqlobject. statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement. SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util. List;
import java.util.Optional;

@RegisterBeanMapper(Order.class)
public interface OrderDAO {

    /// Lấy all đơn hàng
    @SqlQuery("Select id, customer_id, total_amount, status, shipping_address, " +
            "shipping_phone, payment_method, note, created_at, updated_at " +
            "From orders " +
            "Order by created_at Desc")
    List<Order> getAll();

    /// Lấy đơn hàng theo ID
    @SqlQuery("Select id, customer_id, total_amount, status, shipping_address, " +
            "shipping_phone, payment_method, note, created_at, updated_at " +
            "From orders " +
            "Where id = :id")
    Optional<Order> getById(@Bind("id") int id);

    /// Thêm đơn hàng mới
    @SqlUpdate("Insert into orders (customer_id, total_amount, status, shipping_address, " +
            "shipping_phone, payment_method, note, created_at) " +
            "Values (:customerId, :totalAmount, :status, : shippingAddress, " +
            ":shippingPhone, :paymentMethod, :note, NOW())")
    @GetGeneratedKeys
    int insert(@BindBean Order order);

    /// Cập nhật đơn hàng
    @SqlUpdate("Update orders Set " +
            "total_amount = :totalAmount, " +
            "status = : status, " +
            "shipping_address = :shippingAddress, " +
            "shipping_phone = :shippingPhone, " +
            "payment_method = :paymentMethod, " +
            "note = :note, " +
            "updated_at = NOW() " +
            "Where id = :id")
    boolean update(@BindBean Order order);

    /// Xóa đơn hàng
    @SqlUpdate("Delete From orders Where id = : id")
    boolean delete(@Bind("id") int id);


    /// Lấy all đơn hàng của 1 khách hàng
    @SqlQuery("Select id, customer_id, total_amount, status, shipping_address, " +
            "shipping_phone, payment_method, note, created_at, updated_at " +
            "From orders " +
            "Where customer_id = :customerId " +
            "Order by created_at Desc")
    List<Order> getByCustomerId(@Bind("customerId") int customerId);

    /// Lấy đơn hàng theo trạng thái
    @SqlQuery("Select id, customer_id, total_amount, status, shipping_address, " +
            "shipping_phone, payment_method, note, created_at, updated_at " +
            "From orders " +
            "Where status = :status " +
            "Order by created_at Desc")
    List<Order> getByStatus(@Bind("status") String status);

    /// Cập nhật trạng thái đơn hàng
    @SqlUpdate("Update orders " +
            "Set status = :status, updated_at = NOW() " +
            "Where id = : id")
    boolean updateStatus(@Bind("id") int id, @Bind("status") String status);

    /// Đếm tổng số đơn hàng
    @SqlQuery("Select Count(id) From orders")
    int countAll();

    /// Đếm đơn hàng theo trạng thái
    @SqlQuery("Select Count(id) From orders Where status = :status")
    int countByStatus(@Bind("status") String status);

    /// Lấy đơn hàng mới nhất của khách hàng
    @SqlQuery("Select id, customer_id, total_amount, status, shipping_address, " +
            "shipping_phone, payment_method, note, created_at, updated_at " +
            "From orders " +
            "Where customer_id = :customerId " +
            "Order by created_at Desc " +
            "Limit 1")
    Optional<Order> getLatestByCustomerId(@Bind("customerId") int customerId);

    /// Tính tổng doanh thu
    @SqlQuery("Select Coalesce(Sum(total_amount), 0) " +
            "From orders " +
            "Where status = 'COMPLETED'")
    double getTotalRevenue();

    /// Tính tổng doanh thu theo tháng
    @SqlQuery("Select Coalesce(Sum(total_amount), 0) " +
            "From orders " +
            "Where status = 'COMPLETED' " +
            "And Month(created_at) = : month " +
            "And Year(created_at) = :year")
    double getRevenueByMonth(@Bind("month") int month, @Bind("year") int year);

    /// Lấy đơn hàng trong khoảng thời gian
    @SqlQuery("Select id, customer_id, total_amount, status, shipping_address, " +
            "shipping_phone, payment_method, note, created_at, updated_at " +
            "From orders " +
            "Where created_at Between : startDate And :endDate " +
            "Order by created_at Desc")
    List<Order> getByDateRange(@Bind("startDate") String startDate, @Bind("endDate") String endDate);

    /// Tìm kiếm đơn hàng theo từ khóa
    @SqlQuery("Select id, customer_id, total_amount, status, shipping_address, " +
            "shipping_phone, payment_method, note, created_at, updated_at " +
            "From orders " +
            "Where shipping_address Like : keyword Or note Like :keyword " +
            "Order by created_at Desc")
    List<Order> search(@Bind("keyword") String keyword);
}