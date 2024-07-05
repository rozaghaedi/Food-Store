package org.example;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
/**
 * Data Access Object (DAO) for managing customer-related database operations.
 */
public class CustomersDAO {
    private static CustomersDAO instance;
    private final String JDBC_URL;
    /**
     * Private constructor for creating a CustomersDAO instance with a specific JDBC URL.
     * @param JDBC_URL The JDBC URL for the database connection.
     */
    private CustomersDAO(String JDBC_URL) {
        this.JDBC_URL = JDBC_URL;
    }
    /**
     * Gets a singleton instance of CustomersDAO with the specified JDBC URL.
     *
     * @param JDBC_URL The JDBC URL for the database connection.
     * @return The singleton instance of CustomersDAO.
     */
    public static CustomersDAO getInstance(String JDBC_URL) {
        if (instance == null)
            instance = new CustomersDAO(JDBC_URL);
        return instance;
    }
    /**
     * Authenticates a user based on the provided username and password.
     * @param username: The username of the user.
     * @param password :The password of the user.
     * @return A Customer object if authentication is successful, or null if authentication fails.
     */
    public Customer authenticateUser(String username, String password) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
            String query = "SELECT * FROM customers, Address WHERE username = ? AND password = ? AND customers.address = address.id";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, hash(password));
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Customer customer = new Customer();

                        customer.setCustomerID(resultSet.getInt("id"));
                        customer.setUsername(resultSet.getString("username"));
                        customer.setBusinessName(resultSet.getString("businessName"));
                        customer.setTelephoneNumber(resultSet.getString("telephoneNumber"));

                        Address address = new Address();
                        address.setId(resultSet.getInt("id"));
                        address.setCountry(resultSet.getString("country"));
                        address.setPostCode(resultSet.getString("postCode"));
                        address.setAddressLine1(resultSet.getString("addressLine1"));
                        address.setAddressLine2(resultSet.getString("addressLine2"));
                        address.setAddressLine3(resultSet.getString("addressLine3"));

                        customer.setAddress(address);

                        return customer;
                    } else
                        return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Registers a new user in the database.
     * @param user The Customer object representing the user to be registered.
     * @return True if registration is successful, false otherwise.
     */
    public boolean registerUser(Customer user) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
            String insertAddressQuery = "INSERT INTO address (addressLine1, addressLine2, addressLine3, country, postCode) VALUES (?, ?, ?, ?, ?)";
            String insertUserQuery = "INSERT INTO customers (businessName, telephoneNumber, username, password, address) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement addressStatement = connection.prepareStatement(insertAddressQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                addressStatement.setString(1, user.getAddress().getAddressLine1());
                addressStatement.setString(2, user.getAddress().getAddressLine2());
                addressStatement.setString(3, user.getAddress().getAddressLine3());
                addressStatement.setString(4, user.getAddress().getCountry());
                addressStatement.setString(5, user.getAddress().getPostCode());

                int rowsAffected = addressStatement.executeUpdate();
                if (rowsAffected > 0) {
                    ResultSet generatedKeys = addressStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int addressId = generatedKeys.getInt(1);

                        try (PreparedStatement userStatement = connection.prepareStatement(insertUserQuery)) {
                            userStatement.setString(1, user.getBusinessName());
                            userStatement.setString(2, user.getTelephoneNumber());
                            userStatement.setString(3, user.getUsername());
                            userStatement.setString(4, hash(user.getPassword()));
                            userStatement.setInt(5, addressId);

                            int userRowsAffected = userStatement.executeUpdate();
                            return userRowsAffected > 0;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    /**
     * Hashes the given input using the SHA-256 algorithm.
     * @param input The string to be hashed as input.
     * @return The hashed string.
     */
    public static String hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] encodedhash = digest.digest(
                    input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
