package com.shop.util;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class SignatureUtil {

    private static final String KEY_ALGORITHM = "DSA";
    private static final String SIGNATURE_ALGORITHM = "SHA1withDSA";
    private static final String PROVIDER = "SUN";

    // Ký bằng private key
    public static byte[] sign(String orderHash, byte[] privateKeyBytes) throws Exception {
        if (orderHash == null || orderHash.trim().isEmpty()) {
            throw new IllegalArgumentException("Order hash không được rỗng");
        }

        if (privateKeyBytes == null || privateKeyBytes.length == 0) {
            throw new IllegalArgumentException("Private key không được rỗng");
        }

        PrivateKey privateKey = getPrivateKeyFromBytes(privateKeyBytes);

        Signature dsa = Signature.getInstance(SIGNATURE_ALGORITHM, PROVIDER);
        dsa.initSign(privateKey);

        byte[] data = orderHash.getBytes(StandardCharsets.UTF_8);
        dsa.update(data);

        return dsa.sign();
    }

    //Xác minh chữ ký
    public static boolean verify(String orderHash, byte[] signatureBytes, byte[] publicKeyBytes) {
        try {
            if (orderHash == null || orderHash.trim().isEmpty()) {
                return false;
            }
            if (signatureBytes == null || signatureBytes.length == 0) {
                return false;
            }
            if (publicKeyBytes == null || publicKeyBytes.length == 0) {
                return false;
            }

            PublicKey publicKey = getPublicKeyFromBytes(publicKeyBytes);

            Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM, PROVIDER);
            sig.initVerify(publicKey);

            byte[] data = orderHash.getBytes(StandardCharsets.UTF_8);
            sig.update(data);

            return sig.verify(signatureBytes);
        } catch (Exception e) {
            return false;
        }
    }

    private static PrivateKey getPrivateKeyFromBytes(byte[] privateKeyBytes) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM, PROVIDER);
        return keyFactory.generatePrivate(keySpec);
    }

    private static PublicKey getPublicKeyFromBytes(byte[] publicKeyBytes) throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM, PROVIDER);
        return keyFactory.generatePublic(keySpec);
    }
}