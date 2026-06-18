package com.shop.dao.order;

import com.shop.dao.support.BaseDao;
import com.shop.model.UserKey;

import java.util.List;

public class UserKeyDAO extends BaseDao {
// Thêm khóa mới
    public int insert(UserKey userKey) {
        return get().withHandle(h -> h.createUpdate("INSERT INTO user_keys " +
                                "(customer_id, public_key, is_active, source, file_name, created_at) " +
                                "VALUES (:customerId, :publicKey, :active, :source, :fileName, NOW())"
                        )
                        .bind("customerId", userKey.getCustomerId())
                        .bind("publicKey",  userKey.getPublicKey())
                        .bind("active",     userKey.isActive())
                        .bind("source",     userKey.getSource())
                        .bind("fileName",   userKey.getFileName())
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(Integer.class)
                        .one()
        );
    }
// Lấy khóa đang hoạt động
    public UserKey getActiveByCustomerId(int customerId) {
        return get().withHandle(h -> h.createQuery("SELECT id, customer_id, public_key, is_active AS active, source, " +
                                "file_name, created_at, reported_lost_at " +
                                "FROM user_keys " +
                                "WHERE customer_id = :customerId AND is_active = true " +
                                "ORDER BY created_at DESC LIMIT 1"
                        )
                        .bind("customerId", customerId)
                        .mapToBean(UserKey.class)
                        .findOne()
                        .orElse(null)
        );
    }
// Lấy khóa theo ID
    public UserKey getById(int id) {
        return get().withHandle(h -> h.createQuery("SELECT id, customer_id, public_key, is_active AS active, source, " +
                                "file_name, created_at, reported_lost_at " +
                                "FROM user_keys WHERE id = :id"
                        )
                        .bind("id", id)
                        .mapToBean(UserKey.class)
                        .findOne()
                        .orElse(null)
        );
    }
// Lấy tất cả khóa của khách hàng, sắp xếp theo ngày tạo giảm dần
    public List<UserKey> getAllByCustomerId(int customerId) {
        return get().withHandle(h -> h.createQuery("SELECT id, customer_id, public_key, is_active AS active, source, " +
                                "file_name, created_at, reported_lost_at " +
                                "FROM user_keys " +
                                "WHERE customer_id = :customerId " +
                                "ORDER BY created_at DESC"
                        )
                        .bind("customerId", customerId)
                        .mapToBean(UserKey.class)
                        .list()
        );
    }
// Vô hiệu hóa tất cả khóa đang hoạt động của khách hàng
    public void deactivateAllByCustomerId(int customerId) {
        get().useHandle(h -> h.createUpdate("UPDATE user_keys SET is_active = false " +
                                "WHERE customer_id = :customerId AND is_active = true"
                        )
                        .bind("customerId", customerId)
                        .execute()
        );
    }
// Báo mất khóa
    public void reportLost(int keyId) {
        get().useHandle(h -> h.createUpdate("UPDATE user_keys " +
                                "SET reported_lost_at = NOW(), is_active = false " +
                                "WHERE id = :keyId"
                        )
                        .bind("keyId", keyId)
                        .execute()
        );
    }
    // tìm key theo public_key và customer_id để kiểm tra đã báo mất chưa
    public UserKey getByPublicKeyAndCustomerId(String publicKey, int customerId) {
        return get().withHandle(h -> h.createQuery("SELECT id, customer_id, public_key, is_active AS active, source, " +
                                "file_name, created_at, reported_lost_at " +
                                "FROM user_keys " +
                                "WHERE customer_id = :customerId AND public_key = :publicKey " +
                                "ORDER BY created_at DESC LIMIT 1"
                        )
                        .bind("customerId", customerId)
                        .bind("publicKey",  publicKey)
                        .mapToBean(UserKey.class)
                        .findOne()
                        .orElse(null)
        );
    }
}