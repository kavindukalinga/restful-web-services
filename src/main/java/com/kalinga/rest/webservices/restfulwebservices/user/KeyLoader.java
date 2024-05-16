//package com.kalinga.rest.webservices.restfulwebservices.user;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.nio.charset.StandardCharsets;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//
//public class KeyLoader {
//
//    public static String loadPrivateKey() throws IOException {
//        Resource privateKeyResource = new ClassPathResource("private.key");
//        return readInputStream(privateKeyResource.getInputStream());
//    }
//
//    public static String loadPublicKey() throws IOException {
//        Resource publicKeyResource = new ClassPathResource("public.key");
//        return readInputStream(publicKeyResource.getInputStream());
//    }
//
//    private static String readInputStream(InputStream inputStream) throws IOException {
//        StringBuilder stringBuilder = new StringBuilder();
//        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                stringBuilder.append(line);
//            }
//        }
//        return stringBuilder.toString();
//    }
//}
