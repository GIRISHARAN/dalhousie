package com.amazon.vpc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class InsertProducts {
    public static boolean insertProductsToTable(List<Product> productsList) {
        String insertSqlQuery = "INSERT INTO products (name, price, availability) VALUES (?, ?, ?)";
        try {
            Connection connection = AmazonDatabaseConnection.establishRDSConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(insertSqlQuery);

            // Check if the 'products' table exists
            boolean isTableExists = false;
            try (ResultSet resultSet = connection.getMetaData().getTables(null, null, "products", null)) {
                if (resultSet.next()) {
                    isTableExists = true;
                }
            }

            // If 'products' table doesn't exist, create it
            if (!isTableExists) {
                String createTableSql = "CREATE TABLE products (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), " +
                        "price VARCHAR(255), availability BOOLEAN)";
                try (PreparedStatement createTableStatement = connection.prepareStatement(createTableSql)) {
                    createTableStatement.executeUpdate();
                }
                System.out.println("Created 'products' table.");
            }

            // Disabling auto-commit to enable batch processing
            connection.setAutoCommit(false);

            for (Product product : productsList) {
                preparedStatement.setString(1, product.getName());
                preparedStatement.setString(2, product.getPrice());
                preparedStatement.setBoolean(3, product.isAvailability());
                preparedStatement.addBatch();
            }

            // Execute the batch insert
            preparedStatement.executeBatch();

            // Commit the changes
            connection.commit();

            System.out.println("Records inserted successfully");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
