package com.kalinga.rest.webservices.restfulwebservices.user;

import com.kalinga.rest.webservices.restfulwebservices.asymen.PrivateKeyLoader;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class EncryptDecryptService {

//    private final KeyFileHandler keyFileHandler;

    public static Map<String, Object> map = new HashMap<>();

//    public EncryptDecryptService(KeyFileHandler keyFileHandler) {
//        this.keyFileHandler = keyFileHandler;
//    }

    public static void createKeys(Boolean sure) {
        try {
            if (sure) {
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
                keyPairGenerator.initialize(4096);
                KeyPair keyPair = keyPairGenerator.generateKeyPair();
                PublicKey publicKey = keyPair.getPublic();
                PrivateKey privateKey = keyPair.getPrivate();
                map.put("publicKey", publicKey);
                map.put("privateKey", privateKey);
//                keyFileHandler.writePrivateKeyToFile(privateKey, "private.key");
//                keyFileHandler.writePublicKeyToFile(publicKey, "public.key");
            }
            else {
                PrivateKeyLoader loader = new PrivateKeyLoader();
                PrivateKey privateKey = loader.load("key.pem");

                PublicKeyLoader loader1 = new PublicKeyLoader();
                PublicKey publicKey = loader1.load("public.pem");

                map.put("publicKey",publicKey);
                map.put("privateKey",privateKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String encryptMessage(String plainText) {

        try {

            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING");
            PublicKey publicKey = (PublicKey) map.get("publicKey");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encrypt = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encrypt);
        } catch (Exception ignored) {

        }
        return "";
    }

    public static String decryptMessage(String encryptedMessgae) {

        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING");
            PrivateKey privateKey = (PrivateKey) map.get("privateKey");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decrypt = cipher.doFinal(Base64.getDecoder().decode(encryptedMessgae));
            return new String(decrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}