//package com.kalinga.rest.webservices.restfulwebservices.asymen;
//
//import org.bouncycastle.openssl.PEMKeyPair;
//import org.bouncycastle.openssl.PEMParser;
//import org.springframework.stereotype.Component;
//
//import java.io.FileReader;
//import java.io.IOException;
//import java.security.KeyPair;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//
//@Component
//public class KeyParser {
//
//    public KeyPair getKeyPairFromPEM(String privateKeyFilePath, String publicKeyFilePath) throws IOException {
//        try (FileReader privateKeyFileReader = new FileReader(privateKeyFilePath);
//             FileReader publicKeyFileReader = new FileReader(publicKeyFilePath)) {
//
//            PEMParser privateKeyParser = new PEMParser(privateKeyFileReader);
//            PEMParser publicKeyParser = new PEMParser(publicKeyFileReader);
//
//            PEMKeyPair privateKeyPair = (PEMKeyPair) privateKeyParser.readObject();
//            PEMKeyPair publicKeyPair = (PEMKeyPair) publicKeyParser.readObject();
//
//            // Parse private and public keys
//            PrivateKey privateKey = KeyUtils.getPrivateKey(privateKeyPair);
//            PublicKey publicKey = KeyUtils.getPublicKey(publicKeyPair);
//
//            return new KeyPair(publicKey, privateKey);
//        }
//    }
//}
