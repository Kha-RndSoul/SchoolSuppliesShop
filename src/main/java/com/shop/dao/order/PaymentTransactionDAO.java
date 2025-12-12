package com. shop.dao.order;

import com. shop.model.PaymentTransaction;
import org.jdbi. v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org. jdbi.v3.sqlobject. statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util. List;
import java.util.Optional;

@RegisterBeanMapper(PaymentTransaction.class)
public interface PaymentTransactionDAO {

    /// Lấy all giao dịch thanh toán
    @SqlQuery("Select id, order_id, transaction_id, payment_method, amount, currency, " +
            "status, payment_gateway, gateway_response, payer_email, created_at, updated_at " +
            "From payment_transactions " +
            "Order by created_at Desc")
    List<PaymentTransaction> getAll();

    /// Lấy giao dịch theo ID
    @SqlQuery("Select id, order_id, transaction_id, payment_method, amount, currency, " +
            "status, payment_gateway, gateway_response, payer_email, created_at, updated_at " +
            "From payment_transactions " +
            "Where id = :id")
    Optional<PaymentTransaction> getById(@Bind("id") int id);

    /// Thêm giao dịch mới
    @SqlUpdate("Insert into payment_transactions " +
            "(order_id, transaction_id, payment_method, amount, currency, status, " +
            "payment_gateway, gateway_response, payer_email, created_at) " +
            "Values (:orderId, :transactionId, :paymentMethod, :amount, : currency, : status, " +
            ":paymentGateway, : gatewayResponse, :payerEmail, NOW())")
    @GetGeneratedKeys
    int insert(@BindBean PaymentTransaction paymentTransaction);

    /// Cập nhật giao dịch
    @SqlUpdate("Update payment_transactions Set " +
            "status = :status, " +
            "gateway_response = :gatewayResponse, " +
            "updated_at = NOW() " +
            "Where id = :id")
    boolean update(@BindBean PaymentTransaction paymentTransaction);

    /// Xóa giao dịch
    @SqlUpdate("Delete From payment_transactions Where id = :id")
    boolean delete(@Bind("id") int id);


    /// Lấy giao dịch theo order_id
    @SqlQuery("Select id, order_id, transaction_id, payment_method, amount, currency, " +
            "status, payment_gateway, gateway_response, payer_email, created_at, updated_at " +
            "From payment_transactions " +
            "Where order_id = :orderId " +
            "Order by created_at Desc")
    List<PaymentTransaction> getByOrderId(@Bind("orderId") int orderId);

    /// Lấy giao dịch theo transaction_id từ payment gateway
    @SqlQuery("Select id, order_id, transaction_id, payment_method, amount, currency, " +
            "status, payment_gateway, gateway_response, payer_email, created_at, updated_at " +
            "From payment_transactions " +
            "Where transaction_id = :transactionId")
    Optional<PaymentTransaction> getByTransactionId(@Bind("transactionId") String transactionId);

    /// Lấy giao dịch theo trạng thái
    @SqlQuery("Select id, order_id, transaction_id, payment_method, amount, currency, " +
            "status, payment_gateway, gateway_response, payer_email, created_at, updated_at " +
            "From payment_transactions " +
            "Where status = :status " +
            "Order by created_at Desc")
    List<PaymentTransaction> getByStatus(@Bind("status") String status);

    /// Lấy giao dịch theo phương thức thanh toán
    @SqlQuery("Select id, order_id, transaction_id, payment_method, amount, currency, " +
            "status, payment_gateway, gateway_response, payer_email, created_at, updated_at " +
            "From payment_transactions " +
            "Where payment_method = :paymentMethod " +
            "Order by created_at Desc")
    List<PaymentTransaction> getByPaymentMethod(@Bind("paymentMethod") String paymentMethod);

    /// Cập nhật trạng thái giao dịch
    @SqlUpdate("Update payment_transactions " +
            "Set status = :status, gateway_response = :gatewayResponse, updated_at = NOW() " +
            "Where id = :id")
    boolean updateStatus(@Bind("id") int id, @Bind("status") String status, @Bind("gatewayResponse") String gatewayResponse);

    /// Cập nhật trạng thái theo transaction_id
    @SqlUpdate("Update payment_transactions " +
            "Set status = :status, gateway_response = :gatewayResponse, updated_at = NOW() " +
            "Where transaction_id = : transactionId")
    boolean updateStatusByTransactionId(@Bind("transactionId") String transactionId, @Bind("status") String status, @Bind("gatewayResponse") String gatewayResponse);

    /// Check đơn hàng đã có giao dịch thành công chưa
    @SqlQuery("Select Count(id) > 0 " +
            "From payment_transactions " +
            "Where order_id = :orderId And status = 'SUCCESS'")
    boolean hasSuccessfulPayment(@Bind("orderId") int orderId);

    /// Lấy giao dịch thành công mới nhất của đơn hàng
    @SqlQuery("Select id, order_id, transaction_id, payment_method, amount, currency, " +
            "status, payment_gateway, gateway_response, payer_email, created_at, updated_at " +
            "From payment_transactions " +
            "Where order_id = :orderId And status = 'SUCCESS' " +
            "Order by created_at Desc " +
            "Limit 1")
    Optional<PaymentTransaction> getSuccessfulPaymentByOrderId(@Bind("orderId") int orderId);

    /// Đếm tổng số giao dịch
    @SqlQuery("Select Count(id) From payment_transactions")
    int countAll();

    /// Đếm giao dịch theo trạng thái
    @SqlQuery("Select Count(id) From payment_transactions Where status = : status")
    int countByStatus(@Bind("status") String status);

    /// Tính tổng tiền giao dịch thành công
    @SqlQuery("Select Coalesce(Sum(amount), 0) " +
            "From payment_transactions " +
            "Where status = 'SUCCESS'")
    double getTotalSuccessfulAmount();

    /// Tính tổng tiền theo phương thức thanh toán
    @SqlQuery("Select Coalesce(Sum(amount), 0) " +
            "From payment_transactions " +
            "Where payment_method = :paymentMethod And status = 'SUCCESS'")
    double getTotalAmountByPaymentMethod(@Bind("paymentMethod") String paymentMethod);

    /// Lấy giao dịch trong khoảng thời gian
    @SqlQuery("Select id, order_id, transaction_id, payment_method, amount, currency, " +
            "status, payment_gateway, gateway_response, payer_email, created_at, updated_at " +
            "From payment_transactions " +
            "Where created_at Between :startDate And : endDate " +
            "Order by created_at Desc")
    List<PaymentTransaction> getByDateRange(@Bind("startDate") String startDate, @Bind("endDate") String endDate);

    /// Lấy giao dịch theo payment gateway - VNPAY, MOMO, PAYPAL, etc
    @SqlQuery("Select id, order_id, transaction_id, payment_method, amount, currency, " +
            "status, payment_gateway, gateway_response, payer_email, created_at, updated_at " +
            "From payment_transactions " +
            "Where payment_gateway = :paymentGateway " +
            "Order by created_at Desc")
    List<PaymentTransaction> getByPaymentGateway(@Bind("paymentGateway") String paymentGateway);
}