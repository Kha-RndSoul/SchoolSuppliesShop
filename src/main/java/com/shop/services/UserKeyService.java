package com.shop.services;

import com.shop.dao.order.UserKeyDAO;
import com.shop.model.UserKey;

import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;

public class UserKeyService {
    private final UserKeyDAO userKeyDAO;
    public UserKeyService() {
        this.userKeyDAO = new UserKeyDAO();
    }
    public void uploadPublicKey(int customerId, byte[] encKey, String fileName) throws Exception {
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
        KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
        keyFactory.generatePublic(pubKeySpec);
        String pubKeyBase64 = Base64.getEncoder().encodeToString(encKey);
        // kiểm tra key này đã từng bị báo mất chưa
        UserKey existing = userKeyDAO.getByPublicKeyAndCustomerId(pubKeyBase64, customerId);
        if (existing != null && existing.getReportedLostAt() != null) {
            throw new Exception("Khóa này đã bị báo mất trước đó, không thể kích hoạt lại. Vui lòng tạo cặp khóa mới.");
        }
        // deactivate key cũ rồi lưu key mới, active luôn
        userKeyDAO.deactivateAllByCustomerId(customerId);
        UserKey userKey = new UserKey(customerId, pubKeyBase64, "UPLOADED");
        userKey.setFileName(fileName);
        userKey.setActive(true);
        userKeyDAO.insert(userKey);
    }
    public PublicKey loadPublicKey(int keyId) throws Exception {
        UserKey userKey = userKeyDAO.getById(keyId);
        if (userKey == null) throw new Exception("Không tìm thấy key id: " + keyId);
        byte[] encKey = Base64.getDecoder().decode(userKey.getPublicKey());
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
        KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
        return keyFactory.generatePublic(pubKeySpec);
    }
// Lấy key đang active
    public UserKey getActiveKey(int customerId) {
        return userKeyDAO.getActiveByCustomerId(customerId);
    }
// Lấy key theo id
    public UserKey getById(int keyId) {
        return userKeyDAO.getById(keyId);
    }
// Lấy lịch sử tất cả key theo customer
    public List<UserKey> getAllKeys(int customerId) {
        return userKeyDAO.getAllByCustomerId(customerId);
    }
// Báo mất khóa
    public void reportLost(int keyId) {
        userKeyDAO.reportLost(keyId);
    }
}