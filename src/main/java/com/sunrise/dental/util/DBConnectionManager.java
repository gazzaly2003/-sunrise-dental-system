package com.sunrise.dental.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {
    private static DBConnectionManager instance;
    private Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/sunrise_dental";
    private static final String USER = "root";
    private static final String PASSWORD = "Gazzy@2003"; // change to your MySQL password

    private DBConnectionManager() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    public static synchronized DBConnectionManager getInstance() {
        if (instance == null) {
            instance = new DBConnectionManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}