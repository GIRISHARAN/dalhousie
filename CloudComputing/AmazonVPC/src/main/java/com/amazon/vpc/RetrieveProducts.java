package com.amazon.vpc;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RetrieveProducts {
    public static List<Product> getProducts() {
        String selectSql = "SELECT name, price, availability FROM products";
        List<Product> products = new ArrayList<>();

        try {
            Connection connection = AmazonDatabaseConnection.establishRDSConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectSql);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String price = resultSet.getString("price");
                boolean availability = resultSet.getBoolean("availability");

                // Create a Product object and add it to the products list
                Product product = new Product(name, price, availability);
                products.add(product);
            }

            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}