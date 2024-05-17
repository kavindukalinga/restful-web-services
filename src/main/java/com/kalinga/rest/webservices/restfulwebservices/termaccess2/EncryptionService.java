package com.kalinga.rest.webservices.restfulwebservices.termaccess;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class EncryptionService {

    public static String decryptData(String data, String privateKeyPath) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("gpg", "--decrypt", "--batch", "--quiet", "--pinentry-mode", "loopback", "--passphrase-file", privateKeyPath);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
             BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

            // Write data to process
            process.getOutputStream().write(data.getBytes());
            process.getOutputStream().flush();
            process.getOutputStream().close();

            // Read the output from the command
            StringBuilder decryptedData = new StringBuilder();
            String s;
            while ((s = stdInput.readLine()) != null) {
                decryptedData.append(s).append("\n");
            }

            // Read any errors from the attempted command
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

    public static String encryptData(String data, String recipient) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("gpg", "--encrypt", "--recipient", recipient);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
             BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

            // Write data to process
            process.getOutputStream().write(data.getBytes());
            process.getOutputStream().flush();
            process.getOutputStream().close();

            // Read the output from the command
            StringBuilder encryptedData = new StringBuilder();
            String s;
            while ((s = stdInput.readLine()) != null) {
                encryptedData.append(s).append("\n");
            }

            // Read any errors from the attempted command
            StringBuilder errorOutput = new StringBuilder();
            while ((s = stdError.readLine()) != null) {
                errorOutput.append(s).append("\n");
            }

            if (process.waitFor() != 0) {
                throw new RuntimeException("Encryption failed: " + errorOutput.toString());
            }

            return encryptedData.toString().trim();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Encryption process was interrupted", e);
        }
    }
}
