package com.greenmart.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {
    public static final String dbUrl = "jdbc:mysql://localhost:3306/greenmart";
    public static final String dbUsername = "root";
    public static final String dbPassword = "12345678";

    private Connection con = null;

    public Connection getConnection() {
        try {
            if (con == null || con.isClosed()) {
                System.out.println("Attempting to establish a new database connection...");
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(DBConfig.dbUrl, DBConfig.dbUsername, DBConfig.dbPassword);
                System.out.println("Database connection established.");
            } else {
                System.out.println("Reusing existing database connection...");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

}