package com.shop.services;

import com.shop.dao.order.UserKeyDAO;
import com.shop.model.UserKey;

import java.security.*;
import java.util.Base64;
import java.util.List;

public class UserKeyService {
    private final UserKeyDAO userKeyDAO;
    public UserKeyService() {
        this.userKeyDAO = new UserKeyDAO();
    }

    public byte[] generateAndSave(int customerId) throws GeneralSecurityException {
        // Tạo cặp khóa DSA
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        keyGen.initialize(1024, random);
        KeyPair pair = keyGen.generateKeyPair();

        PrivateKey priv = pair.getPrivate();
        PublicKey  pub  = pair.getPublic();
        String pubKeyBase64 = Base64.getEncoder().encodeToString(pub.getEncoded());
        // Deactivate key cũ (không xóa, chỉ set is_active = false)
        userKeyDAO.deactivateAllByCustomerId(customerId);
        UserKey userKey = new UserKey(customerId, pubKeyBase64, "GENERATED");
        userKeyDAO.insert(userKey);
        return priv.getEncoded();
    }
    public void uploadPublicKey(int customerId, byte[] pubKeyBytes) throws GeneralSecurityException {
        java.security.spec.X509EncodedKeySpec pubKeySpec = new java.security.spec.X509EncodedKeySpec(pubKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
        keyFactory.generatePublic(pubKeySpec);

        String pubKeyBase64 = Base64.getEncoder().encodeToString(pubKeyBytes);
        userKeyDAO.deactivateAllByCustomerId(customerId);
        // Lưu public key mới
        UserKey userKey = new UserKey(customerId, pubKeyBase64, "UPLOADED");
        userKeyDAO.insert(userKey);
    }

    public PublicKey loadPublicKey(int keyId) throws GeneralSecurityException {
        UserKey userKey = userKeyDAO.getById(keyId);
        if (userKey == null) throw new IllegalArgumentException("Không tìm thấy key id: " + keyId);

        byte[] pubKeyBytes = Base64.getDecoder().decode(userKey.getPublicKey());
        java.security.spec.X509EncodedKeySpec pubKeySpec =
                new java.security.spec.X509EncodedKeySpec(pubKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
        return keyFactory.generatePublic(pubKeySpec);
    }
    // Lấy key đang active của customer
    public UserKey getActiveKey(int customerId) {
        return userKeyDAO.getActiveByCustomerId(customerId);
    }
    // Lấy key theo id
    public UserKey getById(int keyId) {
        return userKeyDAO.getById(keyId);
    }
    // Lấy lịch sử tất cả key của customer
    public List<UserKey> getAllKeys(int customerId) {
        return userKeyDAO.getAllByCustomerId(customerId);
    }
    // Báo mất khóa
    public void reportLost(int keyId) {
        userKeyDAO.reportLost(keyId);
    }
}