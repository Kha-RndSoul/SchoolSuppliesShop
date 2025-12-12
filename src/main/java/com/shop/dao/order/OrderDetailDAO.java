package com.shop.dao.order;

import com.shop.model.OrderDetail;
import org.jdbi.v3.sqlobject.config. RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org. jdbi.v3.sqlobject. statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org. jdbi.v3.sqlobject. statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util. List;
import java.util.Optional;

@RegisterBeanMapper(OrderDetail. class)
public interface OrderDetailDAO {

    /// Lấy all chi tiết đơn hàng
    @SqlQuery("Select id, order_id, product_id, product_name, quantity, unit_price, subtotal " +
            "From order_details")
    List<OrderDetail> getAll();

    /// Lấy chi tiết đơn hàng theo ID
    @SqlQuery("Select id, order_id, product_id, product_name, quantity, unit_price, subtotal " +
            "From order_details " +
            "Where id = :id")
    Optional<OrderDetail> getById(@Bind("id") int id);

    /// Thêm chi tiết đơn hàng mới
    @SqlUpdate("Insert into order_details " +
            "(order_id, product_id, product_name, quantity, unit_price, subtotal) " +
            "Values (:orderId, :productId, :productName, :quantity, :unitPrice, :subtotal)")
    @GetGeneratedKeys
    int insert(@BindBean OrderDetail orderDetail);

    /// Cập nhật chi tiết đơn hàng
    @SqlUpdate("Update order_details Set " +
            "quantity = :quantity, " +
            "unit_price = :unitPrice, " +
            "subtotal = :subtotal " +
            "Where id = :id")
    boolean update(@BindBean OrderDetail orderDetail);

    /// Xóa chi tiết đơn hàng
    @SqlUpdate("Delete From order_details Where id = :id")
    boolean delete(@Bind("id") int id);


    /// Lấy all chi tiết của 1 đơn hàng
    @SqlQuery("Select id, order_id, product_id, product_name, quantity, unit_price, subtotal " +
            "From order_details " +
            "Where order_id = :orderId")
    List<OrderDetail> getByOrderId(@Bind("orderId") int orderId);

    /// Xóa all chi tiết của 1 đơn hàng
    @SqlUpdate("Delete From order_details Where order_id = :orderId")
    boolean deleteByOrderId(@Bind("orderId") int orderId);

    /// Đếm số lượng sản phẩm trong 1 đơn hàng
    @SqlQuery("Select Count(id) From order_details Where order_id = : orderId")
    int countByOrderId(@Bind("orderId") int orderId);

    /// Tính tổng tiền của 1 đơn hàng
    @SqlQuery("Select Coalesce(Sum(subtotal), 0) " +
            "From order_details " +
            "Where order_id = :orderId")
    double getTotalByOrderId(@Bind("orderId") int orderId);

    /// Lấy chi tiết theo product_id - dùng để thống kê sản phẩm bán chạy
    @SqlQuery("Select id, order_id, product_id, product_name, quantity, unit_price, subtotal " +
            "From order_details " +
            "Where product_id = :productId")
    List<OrderDetail> getByProductId(@Bind("productId") int productId);

    /// Đếm số lượng đã bán của 1 sản phẩm
    @SqlQuery("Select Coalesce(Sum(quantity), 0) " +
            "From order_details " +
            "Where product_id = :productId")
    int getTotalSoldByProductId(@Bind("productId") int productId);

    /// Thêm nhiều chi tiết đơn hàng cùng lúc - batch insert
    @SqlBatch("Insert into order_details " +
            "(order_id, product_id, product_name, quantity, unit_price, subtotal) " +
            "Values (:orderId, :productId, :productName, : quantity, :unitPrice, :subtotal)")
    void insertBatch(@BindBean List<OrderDetail> orderDetails);

    /// Kiểm tra sản phẩm đã có trong đơn hàng chưa
    @SqlQuery("Select id, order_id, product_id, product_name, quantity, unit_price, subtotal " +
            "From order_details " +
            "Where order_id = :orderId And product_id = : productId")
    Optional<OrderDetail> getByOrderIdAndProductId(@Bind("orderId") int orderId, @Bind("productId") int productId);
}