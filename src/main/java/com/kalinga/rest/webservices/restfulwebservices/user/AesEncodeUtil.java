package com.kalinga.rest.webservices.restfulwebservices.user;


//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static org.springframework.util.StringUtils.isEmpty;

@Component
public class AesEncodeUtil {
    private static final int length=128;

    private static final String ALGORITHM = "AES";

    public static String PRIVATE_KEY;

    @Value("${text.privateKey}")
    public void setPrivateKey(String key){ PRIVATE_KEY = key;}

    private static byte[] encrypt(String content, String password)
            throws Exception {
        SecretKeySpec key = getSecretKeySpec(password);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(byteContent);
    }



    private static byte[] decrypt(byte[] content, String password)
            throws Exception {
        SecretKeySpec key = getSecretKeySpec(password);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(content);
    }

    private static SecretKeySpec getSecretKeySpec(String password) throws NoSuchAlgorithmException {
        KeyGenerator key = KeyGenerator.getInstance(ALGORITHM);
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(password.getBytes());
        key.init(length, secureRandom);
        SecretKey secretKey = key.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        return new SecretKeySpec(enCodeFormat, ALGORITHM);
    }

    public static String encrypt2Str(String content, String password) throws Exception {
        if (FieldIsEmpty(content, password)) {
            return null;
        }
        byte[] encryptResult = encrypt(content, password);
        return Base64.getEncoder().encodeToString(encryptResult);
    }

    public static String decrypt2Str(String content, String password) throws Exception {
        if (FieldIsEmpty(content, password)) {
            return null;
        }
        byte[] decryptResult = decrypt(Base64.getDecoder().decode(content), password);
        return new String(decryptResult, StandardCharsets.UTF_8);
    }

    private static boolean FieldIsEmpty(String content, String password) {
        return isEmpty(content) || isEmpty(password);
    }
}