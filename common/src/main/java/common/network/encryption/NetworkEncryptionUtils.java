package common.network.encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class NetworkEncryptionUtils {
    public static SecretKey generateSecretKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static PublicKey decodeEncodedRsaPublicKey(byte[] key) {
        try {
            EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(key);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(encodedKeySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Cipher cipherFromKey(int opMode, Key key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
            cipher.init(opMode, key, new IvParameterSpec(key.getEncoded()));
            return cipher;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] encrypt(Key key, byte[] data) {
        return crypt(Cipher.ENCRYPT_MODE, key, data);
    }

    public static byte[] decrypt(Key key, byte[] data) {
        return crypt(Cipher.DECRYPT_MODE, key, data);
    }

    private static byte[] crypt(int opMode, Key key, byte[] data) {
        try {
            return createCipher(opMode, key.getAlgorithm(), key).doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Cipher createCipher(int opMode, String algorithm, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(opMode, key);
        return cipher;
    }

    public static SecretKey decryptSecretKey(PrivateKey privateKey, byte[] data) {
        byte[] bytes = decrypt(privateKey, data);

        try {
            return new SecretKeySpec(bytes, "AES");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
