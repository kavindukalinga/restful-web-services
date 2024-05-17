package com.kalinga.rest.webservices.restfulwebservices.termaccess;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class EncryptionService {

    public static String decryptData(byte[] data, String privateKeyPath) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("gpg", "--decrypt", "--batch", "--quiet", "--pinentry-mode", "loopback", "--passphrase","kalinga" );
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        process.getOutputStream().write(data);
        process.getOutputStream().flush();
        process.getOutputStream().close();

        try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
             BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

            StringBuilder decryptedData = new StringBuilder();
            String s;
            while ((s = stdInput.readLine()) != null) {
                decryptedData.append(s).append("\n");
            }

            StringBuilder errorOutput = new StringBuilder();
            while ((s = stdError.readLine()) != null) {
                errorOutput.append(s).append("\n");
            }

            if (process.waitFor() != 0) {
                throw new RuntimeException("Decryption failed: " + errorOutput.toString());
            }

            return decryptedData.toString().trim();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Decryption process was interrupted", e);
        }
    }

    public List<String> decryptRows(List<byte[]> encryptedRows, String privateKeyPath) {
        List<String> decryptedRows = new ArrayList<>();

        for (byte[] encryptedData : encryptedRows) {
            try {
                decryptedRows.add(decryptData(encryptedData, privateKeyPath));
            } catch (Exception e) {
                decryptedRows.add("Decryption Error: " + e.getMessage());
            }
        }

        return decryptedRows;
    }

    public byte[] encryptData(String data, String recipient) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("gpg", "--encrypt", "--recipient", recipient, "--armor");
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        process.getOutputStream().write(data.getBytes());
        process.getOutputStream().flush();
        process.getOutputStream().close();

        try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
             BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

            StringBuilder encryptedData = new StringBuilder();
            String s;
            while ((s = stdInput.readLine()) != null) {
                encryptedData.append(s).append("\n");
            }

            StringBuilder errorOutput = new StringBuilder();
            while ((s = stdError.readLine()) != null) {
                errorOutput.append(s).append("\n");
            }

            if (process.waitFor() != 0) {
                throw new RuntimeException("Encryption failed: " + errorOutput.toString());
            }

            return encryptedData.toString().getBytes();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Encryption process was interrupted", e);
        }
    }
}
