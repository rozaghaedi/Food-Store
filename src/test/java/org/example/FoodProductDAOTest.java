package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FoodProductDAOTest {

    private static final String JDBC_URL = "jdbc:sqlite:identifier.sqlite";
    private static FoodProductDAO foodProductDAO;

    @BeforeAll
    static void setUp() {
        foodProductDAO = FoodProductDAO.getInstance(JDBC_URL);
        foodProductDAO.addProduct(new FoodProduct("Box of orange","Fruit","OR32","2023-09-18",23));
    }

    @AfterAll
    static void tearDown() {
    }

    @Test
    void findAllProducts_ShouldReturnListOfProducts() {
        List<FoodProduct> products = foodProductDAO.findAllProducts();
        assertNotNull(products);
        assertFalse(products.isEmpty());
    }

    @Test
    void findProduct_ExistingProductId_ShouldReturnProduct() {
        int existingProductId = 27;

        FoodProduct product = foodProductDAO.findProduct(existingProductId);

        assertNotNull(product);
        assertEquals(existingProductId, product.getId());
    }

    @Test
    void findProduct_NonExistingProductId_ShouldReturnNull() {

        int nonExistingProductId = -1;

        FoodProduct product = foodProductDAO.findProduct(nonExistingProductId);

        assertNull(product);
    }

    @Test
    void deleteProduct_ExistingProductId_ShouldReturnTrue() {

        int existingProductId = 22;
        boolean result = foodProductDAO.deleteProduct(existingProductId);

        assertTrue(result);
    }

    @Test
    void deleteProduct_NonExistingProductId_ShouldReturnFalse() {
        int nonExistingProductId = -1;

        boolean result = foodProductDAO.deleteProduct(nonExistingProductId);

        assertFalse(result);
    }

    @Test
    void updateProduct_ExistingProduct_ShouldReturnTrue() {
        FoodProduct existingProduct = new FoodProduct();
        existingProduct.setId(22);
        existingProduct.setSku("BN135");
        existingProduct.setDescription("Box of bananas");
        existingProduct.setPrice(35);
        existingProduct.setCategory("Fruit");

        boolean result = foodProductDAO.updateProduct(existingProduct);

        assertTrue(result);
    }

    @Test
    void updateProduct_NonExistingProduct_ShouldReturnFalse() {
        FoodProduct nonExistingProduct = new FoodProduct();
        nonExistingProduct.setId(-1);

        boolean result = foodProductDAO.updateProduct(nonExistingProduct);

        assertFalse(result);
    }

    @Test
    void addProduct_NewProduct_ShouldReturnTrue() {
        FoodProduct newProduct = new FoodProduct();
        newProduct.setSku("new_sku");
        newProduct.setDescription("New Description");
        newProduct.setPrice(99);
        newProduct.setCategory("New Category");

        boolean result = foodProductDAO.addProduct(newProduct);

        assertTrue(result);
    }

    @Test
    void findProductsByCategory_ExistingCategory_ShouldReturnListOfProducts() {
        String existingCategory = "Fruit";

        List<FoodProduct> products = foodProductDAO.findProductsByCategory(existingCategory);

        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertTrue(products.stream().allMatch(product -> existingCategory.equals(product.getCategory())));
    }

    @Test
    void findProductsByCategory_NonExistingCategory_ShouldReturnEmptyList() {
        String nonExistingCategory = "NonExistingCategory";

        List<FoodProduct> products = foodProductDAO.findProductsByCategory(nonExistingCategory);

        assertNotNull(products);
        assertTrue(products.isEmpty());
    }

    @Test
    void findUniqueCategories_ShouldReturnListOfCategories() {
        List<String> uniqueCategories = foodProductDAO.findUniqueCategories();

        assertNotNull(uniqueCategories);
        assertFalse(uniqueCategories.isEmpty());
    }

    @Test
    void findProductsByDescription_ExistingDescription_ShouldReturnListOfProducts() {
        String existingDescription = "Box of bananas";

        List<FoodProduct> products = foodProductDAO.findProductsByDescription(existingDescription);

        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertTrue(products.stream().anyMatch(product -> product.getDescription().contains(existingDescription)));
    }

    @Test
    void findProductsByDescription_NonExistingDescription_ShouldReturnEmptyList() {
        String nonExistingDescription = "NonExistingDescription";

        List<FoodProduct> products = foodProductDAO.findProductsByDescription(nonExistingDescription);

        assertNotNull(products);
        assertTrue(products.isEmpty());
    }
}
