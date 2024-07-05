package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
/**
 * Contains information about a food product, including its ID, SKU, description,expiry_date, category, and price.
 */
public class FoodProduct {
    int id;
    String sku;
    String description;
    String category;
    int price;
    String expiry_date;

    /**
     * Creates a new FoodProduct with the given information.
     * @param sku         The stock keeping unit (SKU) code.
     * @param description The description of the food product.
     * @param category    The category to which the food product is in.
     * @param price       The price of the food product.
     */
    public FoodProduct(String sku, String description, String category, String expiryDate, int price) {
        this.sku = sku;
        this.description = description;
        this.category = category;
        this.expiry_date = expiryDate;
        this.price = price;
    }

    /**
     * Creates a new FoodProduct with the given information.
     * @param sku         The stock keeping unit (SKU) code.
     * @param description The description of the food product.
     * @param category    The category to which the food product is in.
     * @param price       The price of the food product.
     */
    public FoodProduct(int id, String sku, String description, String category, String expiryDate, int price) {
        this.id = id;
        this.sku = sku;
        this.description = description;
        this.category = category;
        this.expiry_date = expiryDate;
        this.price = price;
    }
    /**
     * The default FoodProduct constructor.
     */
    public FoodProduct() {

    }

    /**
     * Filters a list of food products based on the selected expiration criteria.
     *
     * @param products       The list of food products to filter.
     * @param selectedExpiry The selected expiration criteria ("expired" or "non-expired").
     * @return A filtered list of food products based on the selected expiration criteria.
     */
    public static List<FoodProduct> filterExpiration(List<FoodProduct> products, String selectedExpiry) {

        List<FoodProduct> filteredProducts = new LinkedList<>();


        LocalDate today = LocalDate.now();

        switch (selectedExpiry) {
            case "expired":

                products.stream().filter(product -> LocalDate.parse(product.getExpiryDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).isBefore(today))
                        .forEach(filteredProducts::add);
                return filteredProducts;

            case "non-expired":

                products.stream().filter(product -> !LocalDate.parse(product.getExpiryDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).isBefore(today))
                        .forEach(filteredProducts::add);
                return filteredProducts;

            default:

                return products;
        }
    }
    /**
     * Gets the id of the food product.
     * return The id of the food product.
     */
    public int getId() {
        return id;
    }
    /**
     * Sets the id of the food product.
     *
     * @param id The unique identifier for the food product.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Gets the SKU code of the food product.
     *return The SKU code of the food product.
     */
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
    /**
     * gets the description of the food product.
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getExpiryDate() {
        return expiry_date;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiry_date = expiryDate;
    }

    @Override
    public String toString() {
        return "FoodProduct{" +
                "id=" + id +
                ", sku='" + sku + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", expiry Date='" + expiry_date + '\'' +
                ", price=" + price +
                '}';
    }
}
