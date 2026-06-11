package com.shop.services;

import com.shop.dao.order.UserKeyDAO;
import com.shop.model.UserKey;
import java.security.*;
import java.util.Base64;


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
}