package offlinesigner.model;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
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

        Files.write(outputDir.resolve("private_key.key"), pair.getPrivate().getEncoded());
        Files.write(outputDir.resolve("public_key.key"), pair.getPublic().getEncoded());
    }

    public static String signOrderHash(String orderHash, Path privateKeyPath) throws Exception {
        if (orderHash == null || orderHash.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã xác thực đơn hàng không được rỗng");
        }

        byte[] privateKeyBytes = Files.readAllBytes(privateKeyPath);
        PrivateKey privateKey = loadPrivateKey(privateKeyBytes);

        Signature dsa = Signature.getInstance(SIGNATURE_ALGORITHM, PROVIDER);
        dsa.initSign(privateKey);
        dsa.update(orderHash.trim().getBytes(StandardCharsets.UTF_8));

        byte[] signatureBytes = dsa.sign();

        return Base64.getEncoder().encodeToString(signatureBytes);
    }

    private static PrivateKey loadPrivateKey(byte[] privateKeyBytes) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM, PROVIDER);

        return keyFactory.generatePrivate(keySpec);
    }
}