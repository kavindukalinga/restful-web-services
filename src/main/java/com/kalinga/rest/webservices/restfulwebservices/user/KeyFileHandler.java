//package com.kalinga.rest.webservices.restfulwebservices.user;
//
//import org.springframework.stereotype.Component;
//
//import java.io.*;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//
//@Component
//public class KeyFileHandler {
//
//    // Method to write PrivateKey to a file
//    public void writePrivateKeyToFile(PrivateKey privateKey, String filePath) throws IOException {
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
//            oos.writeObject(privateKey);
//        }
//    }
//
//    // Method to read PrivateKey from a file
//    public PrivateKey readPrivateKeyFromFile(String filePath) throws IOException, ClassNotFoundException {
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
//            return (PrivateKey) ois.readObject();
//        }
//    }
//
//    // Method to write PublicKey to a file
//    public void writePublicKeyToFile(PublicKey publicKey, String filePath) throws IOException {
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
//            oos.writeObject(publicKey);
//        }
//    }
//
//    // Method to read PublicKey from a file
//    public PublicKey readPublicKeyFromFile(String filePath) throws IOException, ClassNotFoundException {
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
//            return (PublicKey) ois.readObject();
//        }
//    }
//}