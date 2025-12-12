package com.shop.dao. order;

import com. shop.model.CartItem;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org. jdbi.v3.sqlobject. statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement. SqlQuery;
import org.jdbi. v3.sqlobject.statement.SqlUpdate;

import java.util. List;
import java.util. Optional;

@RegisterBeanMapper(CartItem.class)
public interface CartItemDAO {

    /// Lấy all cart items
    @SqlQuery("Select id, customer_id, product_id, quantity, created_at, updated_at " +
            "From cart_items")
    List<CartItem> getAll();

    /// Lấy cart item theo ID
    @SqlQuery("Select id, customer_id, product_id, quantity, created_at, updated_at " +
            "From cart_items " +
            "Where id = :id")
    Optional<CartItem> getById(@Bind("id") int id);

    /// Thêm sản phẩm vào cart
    @SqlUpdate("Insert into cart_items " +
            "(customer_id, product_id, quantity, created_at, updated_at) " +
            "Values (:customerId, : productId, :quantity, NOW(), NOW())")
    @GetGeneratedKeys
    int insert(@BindBean CartItem cartItem);

    /// Cập nhật cart item
    @SqlUpdate("Update cart_items " +
            "Set quantity = : quantity, updated_at = NOW() " +
            "Where id = :id")
    boolean update(@BindBean CartItem cartItem);

    /// Xóa cart item theo ID
    @SqlUpdate("Delete From cart_items Where id = :id")
    boolean delete(@Bind("id") int id);


    /// Lấy all sản phẩm trong giỏ hàng của 1 khách hàng
    @SqlQuery("Select id, customer_id, product_id, quantity, created_at, updated_at " +
            "From cart_items " +
            "Where customer_id = :customerId " +
            "Order by created_at Desc")
    List<CartItem> getByCustomerId(@Bind("customerId") int customerId);

    /// Lấy cart item theo customer_id và product_id
    @SqlQuery("Select id, customer_id, product_id, quantity, created_at, updated_at " +
            "From cart_items " +
            "Where customer_id = :customerId And product_id = : productId")
    Optional<CartItem> getByCustomerIdAndProductId(@Bind("customerId") int customerId, @Bind("productId") int productId);

    /// Kiểm tra sản phẩm đã có trong giỏ hàng chưa
    @SqlQuery("Select Count(id) > 0 " +
            "From cart_items " +
            "Where customer_id = :customerId And product_id = : productId")
    boolean existsInCart(@Bind("customerId") int customerId, @Bind("productId") int productId);

    /// Cập nhật số lượng sản phẩm trong giỏ hàng
    @SqlUpdate("Update cart_items " +
            "Set quantity = :quantity, updated_at = NOW() " +
            "Where customer_id = : customerId And product_id = :productId")
    boolean updateQuantity(@Bind("customerId") int customerId, @Bind("productId") int productId, @Bind("quantity") int quantity);

    /// Tăng số lượng sản phẩm trong giỏ hàng
    @SqlUpdate("Update cart_items " +
            "Set quantity = quantity + :amount, updated_at = NOW() " +
            "Where customer_id = :customerId And product_id = :productId")
    boolean incrementQuantity(@Bind("customerId") int customerId, @Bind("productId") int productId, @Bind("amount") int amount);

    /// Giảm số lượng sản phẩm trong giỏ hàng
    @SqlUpdate("Update cart_items " +
            "Set quantity = quantity - : amount, updated_at = NOW() " +
            "Where customer_id = :customerId And product_id = :productId And quantity > :amount")
    boolean decrementQuantity(@Bind("customerId") int customerId, @Bind("productId") int productId, @Bind("amount") int amount);

    /// Xóa sản phẩm khỏi giỏ hàng theo customer_id và product_id
    @SqlUpdate("Delete From cart_items " +
            "Where customer_id = : customerId And product_id = :productId")
    boolean deleteByCustomerIdAndProductId(@Bind("customerId") int customerId, @Bind("productId") int productId);

    /// Xóa toàn bộ giỏ hàng của khách hàng - khi checkout xong
    @SqlUpdate("Delete From cart_items Where customer_id = : customerId")
    boolean clearCart(@Bind("customerId") int customerId);

    /// Đếm số loại sản phẩm trong giỏ hàng
    @SqlQuery("Select Count(id) " +
            "From cart_items " +
            "Where customer_id = :customerId")
    int countItemsByCustomerId(@Bind("customerId") int customerId);

    /// Tính tổng số lượng sản phẩm trong giỏ hàng
    @SqlQuery("Select Coalesce(Sum(quantity), 0) " +
            "From cart_items " +
            "Where customer_id = :customerId")
    int getTotalQuantityByCustomerId(@Bind("customerId") int customerId);

    /// Lấy giỏ hàng kèm thông tin sản phẩm - JOIN với bảng products
    @SqlQuery("Select ci.id, ci. customer_id, ci. product_id, ci.quantity, " +
            "ci.created_at, ci. updated_at, " +
            "p.name as product_name, p.price as product_price, p.image_url as product_image " +
            "From cart_items ci " +
            "Join products p On ci.product_id = p.id " +
            "Where ci.customer_id = :customerId " +
            "Order by ci.created_at Desc")
    List<CartItem> getCartWithProductInfo(@Bind("customerId") int customerId);

    /// Tính tổng tiền giỏ hàng - JOIN với bảng products
    @SqlQuery("Select Coalesce(Sum(ci.quantity * p.price), 0) " +
            "From cart_items ci " +
            "Join products p On ci.product_id = p.id " +
            "Where ci.customer_id = :customerId")
    double getCartTotal(@Bind("customerId") int customerId);

    /// Xóa các cart items có sản phẩm không còn tồn tại
    @SqlUpdate("Delete From cart_items " +
            "Where product_id Not In (Select id From products)")
    int removeInvalidItems();

    /// Xóa các cart items đã quá hạn - ví dụ sau 30 ngày không hoạt động
    @SqlUpdate("Delete From cart_items " +
            "Where updated_at < Date_sub(NOW(), Interval : days Day)")
    int removeExpiredItems(@Bind("days") int days);

    /// Kiểm tra giỏ hàng có trống không
    @SqlQuery("Select Count(id) = 0 " +
            "From cart_items " +
            "Where customer_id = :customerId")
    boolean isCartEmpty(@Bind("customerId") int customerId);
}