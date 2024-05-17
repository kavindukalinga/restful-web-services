package com.kalinga.rest.webservices.restfulwebservices.termaccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/encryption")
public class EncryptionController {

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private EncryptionService encryptionService;

    @Value("${gpg.privateKeyPath}")
    private String privateKeyPath;

    @Value("${gpg.recipient}")
    private String recipient;

    @GetMapping("/decrypt")
    public List<String> decryptData() throws Exception {
        List<byte[]> encryptedData = databaseService.getEncryptedData();
        return encryptionService.decryptRows(encryptedData, privateKeyPath);
    }

    @PostMapping("/encrypt")
    public String encryptData(@RequestBody String data) throws Exception {
        byte[] encryptedData = encryptionService.encryptData(data, recipient);
        databaseService.saveEncryptedData(encryptedData);
        return "Data encrypted and saved successfully.";
    }
}
