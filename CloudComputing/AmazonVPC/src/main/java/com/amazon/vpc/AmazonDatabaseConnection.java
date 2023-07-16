package com.amazon.vpc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AmazonDatabaseConnection {
    public static Connection establishRDSConnection() {

        /*// Replace with your Aurora endpoint and credentials
        String hostname = "jdbc:mysql://database-cluster-instance-1.crgpug9qppye.us-east-1.rds.amazonaws.com/giridatabase";
        String dbName = "giridatabase";
        String userName = "admin";
        String password = "admin123";
        Integer port = 3306;*/

        // Retrieve values from environment variables
        String hostname = System.getenv("DB_HOSTNAME");
        String dbName = System.getenv("DB_NAME");
        String userName = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");

        String hostname_bkp = "jdbc:mysql://giricluster-instance-1.cjqqafqh2rlm.us-east-1.rds.amazonaws.com:3306/giridatabase";

        String url = "jdbc:mysql://" + hostname + ":3306/" + dbName;
        System.out.println(hostname_bkp);
        System.out.println(url);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, userName, password);
//            Connection connection = DriverManager.getConnection(hostname, userName, password);
            System.out.println("Connected to Aurora database");
            return connection;
        } catch (SQLException e) {
            System.out.println("Failed to connect to Aurora database");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
