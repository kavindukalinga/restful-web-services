package com.kalinga.rest.webservices.restfulwebservices.termaccess;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseService {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    public List<byte[]> getEncryptedData() throws Exception {
        List<byte[]> encryptedDataList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT name FROM public.mages")) {

            while (resultSet.next()) {
                encryptedDataList.add(resultSet.getBytes("name"));
            }
        }

        return encryptedDataList;
    }

    public void saveEncryptedData(byte[] encryptedData) throws Exception {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO public.mages (name) VALUES (?)")) {

            preparedStatement.setBytes(1, encryptedData);
            preparedStatement.executeUpdate();
        }
    }
}
