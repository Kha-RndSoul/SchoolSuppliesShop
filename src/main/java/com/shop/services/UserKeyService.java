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

    public byte[][] generateKeyPairOnly() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        keyGen.initialize(1024, random);
        KeyPair pair = keyGen.generateKeyPair();
        return new byte[][]{
                pair.getPrivate().getEncoded(),
                pair.getPublic().getEncoded()
        };
    }
     // Khi chưa có key nào, hoặc user upload xác nhận key mới.
    public void saveAndActivate(int customerId, byte[] pubKeyBytes, String source) throws Exception {
        String pubKeyBase64 = Base64.getEncoder().encodeToString(pubKeyBytes);
        userKeyDAO.deactivateAllByCustomerId(customerId);
        UserKey userKey = new UserKey(customerId, pubKeyBase64, source);
        userKey.setActive(true);
        userKeyDAO.insert(userKey);
    }
    // lưu khóa mới xuống DB với is_active = false, chờ user upload xác nhận
    public void savePendingKey(int customerId, byte[] pubKeyBytes) throws Exception {
        String pubKeyBase64 = Base64.getEncoder().encodeToString(pubKeyBytes);
        UserKey userKey = new UserKey(customerId, pubKeyBase64, "GENERATED");
        userKey.setActive(false);
        userKeyDAO.insert(userKey);
    }
    // upload public key từ file, kiểm tra có tồn tại dưới DB không rồi mới activate
    public void uploadPublicKey(int customerId, byte[] encKey) throws Exception {
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
        KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
        keyFactory.generatePublic(pubKeySpec);
        // kiểm tra key này có tồn tại dưới DB của customer không
        String pubKeyBase64 = Base64.getEncoder().encodeToString(encKey);
        UserKey existing = userKeyDAO.getByPublicKeyAndCustomerId(pubKeyBase64, customerId);
        if (existing == null) {
            throw new Exception("Khóa này không tồn tại trong hệ thống.");
        }
        userKeyDAO.deactivateAllByCustomerId(customerId);
        userKeyDAO.activateById(existing.getId());
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