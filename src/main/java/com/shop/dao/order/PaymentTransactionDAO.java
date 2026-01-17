package com.shop.dao.order;

import com.shop.dao.support.BaseDao;
import com.shop.model.PaymentTransaction;
import org.jdbi.v3.core.statement.PreparedBatch;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.*;

public class PaymentTransactionDAO extends BaseDao {

    static Map<Integer, PaymentTransaction> data = new HashMap<>();

    static {
        data.put(1, new PaymentTransaction(
                1, 1, "BANK_TRANSFER", "SUCCESS",
                new BigDecimal("500000"), "TXN001",
                Timestamp.valueOf("2023-10-27 10:35:00"),
                Timestamp.valueOf("2023-10-27 10:30:00")
        ));
        data.put(2, new PaymentTransaction(
                2, 2, "VNPAY", "SUCCESS",
                new BigDecimal("350000"), "TXN002",
                Timestamp.valueOf("2023-10-28 09:16:00"),
                Timestamp.valueOf("2023-10-28 09:15:00")
        ));
        data.put(3, new PaymentTransaction(
                3, 3, "COD", "PENDING",
                new BigDecimal("750000"), "TXN003",
                null,
                Timestamp.valueOf("2023-10-29 14:00:00")
        ));
        data.put(4, new PaymentTransaction(
                4, 4, "MOMO", "FAILED",
                new BigDecimal("220000"), "TXN004",
                Timestamp.valueOf("2023-10-30 16:21:30"),
                Timestamp.valueOf("2023-10-30 16:20:00")
        ));
    }

    public List<PaymentTransaction> getListPaymentTransaction() {
        return new ArrayList<>(data.values());
    }

    public PaymentTransaction getPaymentTransaction(int id) {
        return data.get(id);
    }

    public List<PaymentTransaction> getList() {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT id, order_id, transaction_id, payment_method, amount, currency, " +
                                        "status, payment_gateway, gateway_response, payer_email, created_at, updated_at " +
                                        "FROM payment_transactions ORDER BY created_at DESC"
                        )
                        .mapToBean(PaymentTransaction.class)
                        .list()
        );
    }

    public PaymentTransaction getPaymentTransactionById(int id) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT id, order_id, transaction_id, payment_method, amount, currency, " +
                                        "status, payment_gateway, gateway_response, payer_email, created_at, updated_at " +
                                        "FROM payment_transactions WHERE id = :id"
                        )
                        .bind("id", id)
                        .mapToBean(PaymentTransaction.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public List<PaymentTransaction> getByOrderId(int orderId) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT id, order_id, transaction_id, payment_method, amount, currency, " +
                                        "status, payment_gateway, gateway_response, payer_email, created_at, updated_at " +
                                        "FROM payment_transactions WHERE order_id = :orderId ORDER BY created_at DESC"
                        )
                        .bind("orderId", orderId)
                        .mapToBean(PaymentTransaction.class)
                        .list()
        );
    }

    public PaymentTransaction getByTransactionId(String transactionId) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT id, order_id, transaction_id, payment_method, amount, currency, " +
                                        "status, payment_gateway, gateway_response, payer_email, created_at, updated_at " +
                                        "FROM payment_transactions WHERE transaction_id = :transactionId"
                        )
                        .bind("transactionId", transactionId)
                        .mapToBean(PaymentTransaction.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public List<PaymentTransaction> getByStatus(String status) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT id, order_id, transaction_id, payment_method, amount, currency, " +
                                        "status, payment_gateway, gateway_response, payer_email, created_at, updated_at " +
                                        "FROM payment_transactions WHERE status = :status ORDER BY created_at DESC"
                        )
                        .bind("status", status)
                        .mapToBean(PaymentTransaction.class)
                        .list()
        );
    }

    public List<PaymentTransaction> getByPaymentMethod(String paymentMethod) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT id, order_id, transaction_id, payment_method, amount, currency, " +
                                        "status, payment_gateway, gateway_response, payer_email, created_at, updated_at " +
                                        "FROM payment_transactions WHERE payment_method = :paymentMethod ORDER BY created_at DESC"
                        )
                        .bind("paymentMethod", paymentMethod)
                        .mapToBean(PaymentTransaction.class)
                        .list()
        );
    }

    public List<PaymentTransaction> getByPaymentGateway(String paymentGateway) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT id, order_id, transaction_id, payment_method, amount, currency, " +
                                        "status, payment_gateway, gateway_response, payer_email, created_at, updated_at " +
                                        "FROM payment_transactions WHERE payment_gateway = :paymentGateway ORDER BY created_at DESC"
                        )
                        .bind("paymentGateway", paymentGateway)
                        .mapToBean(PaymentTransaction.class)
                        .list()
        );
    }

    public boolean hasSuccessfulPayment(int orderId) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT COUNT(id) > 0 FROM payment_transactions " +
                                        "WHERE order_id = :orderId AND status = 'SUCCESS'"
                        )
                        .bind("orderId", orderId)
                        .mapTo(Boolean.class)
                        .one()
        );
    }

    public PaymentTransaction getSuccessfulPaymentByOrderId(int orderId) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT id, order_id, transaction_id, payment_method, amount, currency, " +
                                        "status, payment_gateway, gateway_response, payer_email, created_at, updated_at " +
                                        "FROM payment_transactions WHERE order_id = :orderId AND status = 'SUCCESS' " +
                                        "ORDER BY created_at DESC LIMIT 1"
                        )
                        .bind("orderId", orderId)
                        .mapToBean(PaymentTransaction.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public void insert(List<PaymentTransaction> transactions) {
        get().useHandle(h -> {
            PreparedBatch batch = h.prepareBatch(
                    "INSERT INTO payment_transactions " +
                            "(id, order_id, transaction_id, payment_method, amount, currency, status, " +
                            "payment_gateway, gateway_response, payer_email, created_at) " +
                            "VALUES (:id, :orderId, :transactionId, :paymentMethod, :amount, :currency, " +
                            ":status, :paymentGateway, :gatewayResponse, :payerEmail, NOW())"
            );
            transactions.forEach(t -> batch.bindBean(t).add());
            batch.execute();
        });
    }

    public void insertPaymentTransaction(PaymentTransaction transaction) {
        get().useHandle(h -> {
            h.createUpdate(
                            "INSERT INTO payment_transactions " +
                                    "(order_id, transaction_id, payment_method, amount, currency, status, " +
                                    "payment_gateway, gateway_response, payer_email, created_at) " +
                                    "VALUES (:orderId, :transactionId, :paymentMethod, :amount, :currency, " +
                                    ":status, :paymentGateway, :gatewayResponse, :payerEmail, NOW())"
                    )
                    .bindBean(transaction)
                    .execute();
        });
    }

    public void updatePaymentTransaction(PaymentTransaction transaction) {
        get().useHandle(h -> {
            h.createUpdate(
                            "UPDATE payment_transactions SET status = :status, " +
                                    "gateway_response = :gatewayResponse, updated_at = NOW() WHERE id = :id"
                    )
                    .bindBean(transaction)
                    .execute();
        });
    }

    public void updateStatus(int id, String status, String gatewayResponse) {
        get().useHandle(h -> {
            h.createUpdate(
                            "UPDATE payment_transactions SET status = :status, " +
                                    "gateway_response = :gatewayResponse, updated_at = NOW() WHERE id = :id"
                    )
                    .bind("id", id)
                    .bind("status", status)
                    .bind("gatewayResponse", gatewayResponse)
                    .execute();
        });
    }

    public void updateStatusByTransactionId(
            String transactionId, String status, String gatewayResponse) {

        get().useHandle(h -> {
            h.createUpdate(
                            "UPDATE payment_transactions SET status = :status, " +
                                    "gateway_response = :gatewayResponse, updated_at = NOW() " +
                                    "WHERE transaction_id = :transactionId"
                    )
                    .bind("transactionId", transactionId)
                    .bind("status", status)
                    .bind("gatewayResponse", gatewayResponse)
                    .execute();
        });
    }

    public void deletePaymentTransaction(int id) {
        get().useHandle(h -> {
            h.createUpdate("DELETE FROM payment_transactions WHERE id = :id")
                    .bind("id", id)
                    .execute();
        });
    }

    public int countAll() {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(id) FROM payment_transactions")
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public int countByStatus(String status) {
        return get().withHandle(h ->
                h.createQuery(
                                "SELECT COUNT(id) FROM payment_transactions WHERE status = :status"
                        )
                        .bind("status", status)
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public double getTotalSuccessfulAmount() {
        Double total = get().withHandle(h ->
                h.createQuery(
                                "SELECT COALESCE(SUM(amount), 0) FROM payment_transactions WHERE status = 'SUCCESS'"
                        )
                        .mapTo(Double.class)
                        .one()
        );
        return total != null ? total : 0.0;
    }

    public double getTotalAmountByPaymentMethod(String paymentMethod) {
        Double total = get().withHandle(h ->
                h.createQuery(
                                "SELECT COALESCE(SUM(amount), 0) FROM payment_transactions " +
                                        "WHERE payment_method = :paymentMethod AND status = 'SUCCESS'"
                        )
                        .bind("paymentMethod", paymentMethod)
                        .mapTo(Double.class)
                        .one()
        );
        return total != null ? total : 0.0;
    }

    public static void main(String[] args) {
        PaymentTransactionDAO dao = new PaymentTransactionDAO();
        System.out.println("INSERT DUMMY DATA");
        List<PaymentTransaction> transactions = dao.getListPaymentTransaction();
        dao.insert(transactions);
        System.out.println("Inserted " + transactions.size() + " payment transactions");

        System.out.println("\nGET SUCCESS TRANSACTIONS");
        dao.getByStatus("SUCCESS").forEach(System.out::println);
    }
}
