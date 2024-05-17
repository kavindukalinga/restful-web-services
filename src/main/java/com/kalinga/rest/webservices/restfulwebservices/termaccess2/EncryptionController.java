package com.kalinga.rest.webservices.restfulwebservices.termaccess;

import com.kalinga.rest.webservices.restfulwebservices.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EncryptionController {

    @PostMapping("/decrypt")
    public String decrypt(@RequestBody DecryptRequest request) {
        try {
            return EncryptionService.decryptData(request.getData(), request.getPrivateKeyPath());
        } catch (IOException e) {
            throw new RuntimeException("Decryption error", e);
        }
    }

    @PostMapping("/encrypt")
    public String encrypt(@RequestBody EncryptRequest request) {
        try {
            return EncryptionService.encryptData(request.getData(), request.getRecipient());
        } catch (IOException e) {
            throw new RuntimeException("Encryption error", e);
        }
    }

    public static class DecryptRequest {
        private String data;
        private String privateKeyPath;

        // Getters and setters
        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getPrivateKeyPath() {
            return privateKeyPath;
        }

        public void setPrivateKeyPath(String privateKeyPath) {
            this.privateKeyPath = privateKeyPath;
        }
    }

    public static class EncryptRequest {
        private String data;
        private String recipient;

        // Getters and setters
        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getRecipient() {
            return recipient;
        }

        public void setRecipient(String recipient) {
            this.recipient = recipient;
        }
    }
}
