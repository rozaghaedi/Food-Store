package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

class CustomersDAOTest {
    private static final String JDBC_URL = "jdbc:sqlite:identifier.sqlite";
    private static CustomersDAO customersDAO;

    @BeforeAll
    static void setUp() {
        customersDAO = CustomersDAO.getInstance(JDBC_URL);
    }

    @AfterAll
    static void tearDown() {
    }

    @Test
    void authenticateUser_ValidCredentials_ReturnsCustomerObject() {

        String validUsername = "testUser";
        String validPassword = "testPassword";

        Customer customer = customersDAO.authenticateUser(validUsername, validPassword);

        assertNotNull(customer);
        assertEquals(validUsername, customer.getUsername());
    }

    @Test
    void authenticateUser_InvalidCredentials_ReturnsNull() {

        String invalidUsername = "invalidUser";
        String invalidPassword = "invalidPassword";

        Customer customer = customersDAO.authenticateUser(invalidUsername, invalidPassword);

        assertNull(customer);
    }

    @Test
    void registerUser_SuccessfulRegistration_ReturnsTrue() {

        Customer testUser = new Customer();
        testUser.setBusinessName("Test Business");
        testUser.setTelephoneNumber("1234567890");
        testUser.setUsername("testUser");
        testUser.setPassword("testPassword");
        testUser.setAddress(new Address("Manchester","Aytoun Street","The grand Building","United Kingdom","M1 D3AO"));

        assertTrue(customersDAO.registerUser(testUser));
    }

    @Test
    void registerUser_UnsuccessfulRegistration_ReturnsFalse() {

        Customer invalidUser = new Customer();

        assertFalse(customersDAO.registerUser(invalidUser));
    }
}

