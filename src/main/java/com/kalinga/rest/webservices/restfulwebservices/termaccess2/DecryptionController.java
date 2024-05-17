//package com.kalinga.rest.webservices.restfulwebservices.termaccess;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//
//@RestController
//@RequestMapping("/api")
//public class DecryptionController {
//
//    @Autowired
//    private DecryptionService decryptionService;
//
//    @PostMapping("/decrypt")
//    public String decrypt(@RequestBody DecryptRequest request) {
//        try {
//            return decryptionService.decryptData(request.getData(), request.getPrivateKeyPath());
//        } catch (IOException e) {
//            throw new RuntimeException("Decryption error", e);
//        }
//    }
//
//    public static class DecryptRequest {
//        private String data;
//        private String privateKeyPath;
//
//        // Getters and setters
//        public String getData() {
//            return data;
//        }
//
//        public void setData(String data) {
//            this.data = data;
//        }
//
//        public String getPrivateKeyPath() {
//            return privateKeyPath;
//        }
//
//        public void setPrivateKeyPath(String privateKeyPath) {
//            this.privateKeyPath = privateKeyPath;
//        }
//    }
//}