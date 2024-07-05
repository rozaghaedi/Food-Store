package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Data Access Object (DAO) for managing FoodProduct entities in the database.
 */
public class FoodProductDAO {

    /**
     * Singleton instance of FoodProductDAO.
     */
    private static FoodProductDAO instance;
    /**
     * JDBC URL for connecting to the database.
     */
    private final String JDBC_URL;

    /**
     * Private constructor to enforce singleton pattern.
     * JDBC_URL : The JDBC URL for connecting to the database.
     */
    private FoodProductDAO(String JDBC_URL) {
        this.JDBC_URL = JDBC_URL;
    }

    /**
     * Gets the singleton instance of FoodProductDAO.
     * param JDBC_URL The JDBC URL for connecting to the database.
     * return The singleton instance of FoodProductDAO.
     */
    public static FoodProductDAO getInstance(String JDBC_URL) {
        if (instance == null)
            instance = new FoodProductDAO(JDBC_URL);
        return instance;
    }

    /**
     * Retrieves a list of all food products from the database.
     * return A list of FoodProduct entities.
     */
    public List<FoodProduct> findAllProducts() {
        List<FoodProduct> products = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM food_products")) {

            while (resultSet.next()) {
                FoodProduct product = new FoodProduct();
                product.setId(resultSet.getInt("id"));
                product.setSku(resultSet.getString("SKU"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getInt("price"));
                product.setCategory(resultSet.getString("category"));
                product.setExpiryDate(resultSet.getString("expiry_date"));

                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    /**
     * Retrieves a specific food product by its ID from the database.
     * @param id The ID of the food product.
     * @return The FoodProduct entity, or null if not found.
     */
    public FoodProduct findProduct(int id) {
        FoodProduct product = null;

        try (Connection connection = DriverManager.getConnection(JDBC_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM food_products WHERE id = ?")) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    product = new FoodProduct();
                    product.setId(resultSet.getInt("id"));
                    product.setSku(resultSet.getString("SKU"));
                    product.setDescription(resultSet.getString("description"));
                    product.setPrice(resultSet.getInt("price"));
                    product.setCategory(resultSet.getString("category"));
                    product.setExpiryDate(resultSet.getString("expiry_date"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    /**
     * Deletes a food product from the database based on its ID.
     * @param id The ID of the food product to be deleted.
     * @return True if the deletion was successful, false otherwise.
     */
    public boolean deleteProduct(int id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM food_products WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    /**
     * Updates the information of a food product in the database.
     * @param foodProduct The FoodProduct entity with updated information.
     * @return True if the update was successful, false otherwise.
     */
    public boolean updateProduct(FoodProduct foodProduct) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE food_products SET SKU = ?, description = ?, price = ?, category = ?, expiry_date = ? WHERE id = ?")) {

            preparedStatement.setString(1, foodProduct.getSku());
            preparedStatement.setString(2, foodProduct.getDescription());
            preparedStatement.setInt(3, foodProduct.getPrice());
            preparedStatement.setString(4, foodProduct.getCategory());
            preparedStatement.setString(5, foodProduct.getExpiryDate());
            preparedStatement.setInt(6, foodProduct.getId());

            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    /**
     * Adds a new food product to the database.
     * @param foodProduct The new FoodProduct entity to be added.
     * @return True if the addition was successful, false otherwise.
     */
    public boolean addProduct(FoodProduct foodProduct) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO food_products (SKU, description, price, category, expiry_date) VALUES (?, ?, ?, ?, ?)")) {

            preparedStatement.setString(1, foodProduct.getSku());
            preparedStatement.setString(2, foodProduct.getDescription());
            preparedStatement.setInt(3, foodProduct.getPrice());
            preparedStatement.setString(4, foodProduct.getCategory());
            preparedStatement.setString(5, foodProduct.getExpiryDate());

            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    /**
     * Retrieves a list of food products based on a specified category from the database.
     * @param category The category of food products to retrieve.
     * @return A list of FoodProduct entities within the specified category.
     */
    public List<FoodProduct> findProductsByCategory(String category) {
        List<FoodProduct> products = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM food_products WHERE category = ?")) {

            preparedStatement.setString(1, category);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    FoodProduct product = new FoodProduct();
                    product.setId(resultSet.getInt("id"));
                    product.setSku(resultSet.getString("SKU"));
                    product.setDescription(resultSet.getString("description"));
                    product.setPrice(resultSet.getInt("price"));
                    product.setCategory(resultSet.getString("category"));
                    product.setExpiryDate(resultSet.getString("expiry_date"));

                    products.add(product);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
    /**
     * Retrieves a list of unique categories available in the database.
     * @return A list of unique category names.
     */
    public List<String> findUniqueCategories() {
        List<String> uniqueCategories = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
            String query = "SELECT DISTINCT category FROM food_products";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String category = resultSet.getString("category");
                    uniqueCategories.add(category);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return uniqueCategories;
    }

    /**
     * Retrieves a list of food products based on a partial description match from the database.
     * @param description The partial description to match against.
     * @return A list of FoodProduct entities with descriptions matching the specified criteria.
     */
    public List<FoodProduct> findProductsByDescription(String description) {
        List<FoodProduct> products = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM food_products WHERE description LIKE ?")) {

            preparedStatement.setString(1, "%" + description + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    FoodProduct product = new FoodProduct();
                    product.setId(resultSet.getInt("id"));
                    product.setSku(resultSet.getString("SKU"));
                    product.setDescription(resultSet.getString("description"));
                    product.setPrice(resultSet.getInt("price"));
                    product.setCategory(resultSet.getString("category"));
                    product.setExpiryDate(resultSet.getString("expiry_date"));

                    products.add(product);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public List<FoodProduct> getFoodsHavingIDs(String checkedItems) {
        List<FoodProduct> products = new ArrayList<>();

        if (checkedItems == null || checkedItems.split(",").length == 0) {
            return products;
        }

        String[] checkedItemsArray = checkedItems.split(",");

        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < checkedItemsArray.length; i++) {
            placeholders.append("?");
            if (i < checkedItemsArray.length - 1) {
                placeholders.append(",");
            }
        }

        try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
            String query = "SELECT * FROM food_products WHERE id IN (" + placeholders + ")";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                for (int i = 0; i < checkedItemsArray.length; i++) {
                    preparedStatement.setInt(i + 1, Integer.parseInt(checkedItemsArray[i]));
                }

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        FoodProduct product = new FoodProduct();
                        product.setId(resultSet.getInt("id"));
                        product.setSku(resultSet.getString("SKU"));
                        product.setDescription(resultSet.getString("description"));
                        product.setPrice(resultSet.getInt("price"));
                        product.setCategory(resultSet.getString("category"));
                        product.setExpiryDate(resultSet.getString("expiry_date"));

                        products.add(product);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

}

