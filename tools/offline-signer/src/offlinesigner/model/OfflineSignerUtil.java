package offlinesigner.model;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class OfflineSignerUtil {

    private static final String KEY_ALGORITHM = "DSA";
    private static final String SIGNATURE_ALGORITHM = "SHA1withDSA";
    private static final String PROVIDER = "SUN";

    public static void generateKeyPair(Path outputDir) throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(KEY_ALGORITHM, PROVIDER);
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", PROVIDER);

        keyGen.initialize(1024, random);

        KeyPair pair = keyGen.generateKeyPair();

        byte[] privateKeyBytes = pair.getPrivate().getEncoded();
        byte[] publicKeyBytes = pair.getPublic().getEncoded();

        Files.write(outputDir.resolve("private_key.key"), privateKeyBytes);
        Files.write(outputDir.resolve("public_key.key"), publicKeyBytes);
    }

    public static String signOrderHash(String orderHash, Path privateKeyPath) throws Exception {
        if (orderHash == null || orderHash.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã xác thực đơn hàng không được rỗng");
        }

        byte[] privateKeyBytes = Files.readAllBytes(privateKeyPath);
        PrivateKey privateKey = loadPrivateKey(privateKeyBytes);

        Signature dsa = Signature.getInstance(SIGNATURE_ALGORITHM, PROVIDER);
        dsa.initSign(privateKey);

        byte[] data = orderHash.trim().getBytes(StandardCharsets.UTF_8);

        BufferedInputStream input = new BufferedInputStream(new ByteArrayInputStream(data));
        byte[] buffer = new byte[1024];
        int len;

        while ((len = input.read(buffer)) != -1) {
            dsa.update(buffer, 0, len);
        }

        input.close();

        byte[] signatureBytes = dsa.sign();

        return Base64.getEncoder().encodeToString(signatureBytes);
    }

    private static PrivateKey loadPrivateKey(byte[] privateKeyBytes) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM, PROVIDER);

        return keyFactory.generatePrivate(keySpec);
    }
}