package com.kalinga.rest.webservices.restfulwebservices.user;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class PublicKeyLoader {

    /**
     * This method loads a file from the classpath and returns it as a String.
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    private String readFile(final String fileName) throws IOException {
        final File file = new File(getClass().getClassLoader().getResource(fileName).getFile());

        return new String(Files.readAllBytes(file.toPath()));
    }

    /**
     * This methos load the RSA private key from a PKCS#8 PEM file.
     *
     * @param pemFilename
     * @return
     * @throws Exception
     */
    private PublicKey loadPemRsaPublicKey(String pemFilename) throws Exception {

        String pemString = readFile(pemFilename);

        String publicKeyPEM = pemString
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PUBLIC KEY-----", "");

        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        return keyFactory.generatePublic(keySpec);
    }

    public PublicKey load(String file) throws Exception {
        return loadPemRsaPublicKey(file);
    }
}

