package org.example;

/**
 * Represents a customer with details such as ID, business name, telephone number, username, password, and address.
 */
public class Customer {

    /**
     * The unique identifier for the customer.
     */
    int customerID;

    /**
     * The business name of the customer.
     */
    String businessName;

    /**
     * The telephone number of the customer.
     */
    String telephoneNumber;

    /**
     * The username of the customer.
     */
    String username;

    /**
     * The password of the customer.
     */
    String password;

    /**
     * The address of the customer.
     */
    Address address;

    /**
     * Constructs a new Customer with the specified details, including an ID.
     *
     * @param customerID      The unique identifier for the customer.
     * @param businessName    The business name of the customer.
     * @param telephoneNumber The telephone number of the customer.
     * @param username        The username of the customer.
     * @param password        The password of the customer.
     * @param address         The address of the customer.
     */

    public Customer(int customerID, String businessName, String telephoneNumber, String username, String password, Address address) {
        this.customerID = customerID;
        this.businessName = businessName;
        this.telephoneNumber = telephoneNumber;
        this.username = username;
        this.password = password;
        this.address = address;
    }

    /**
     * Constructs a new Customer with the specified details.
     *
     * @param businessName    The business name of the customer.
     * @param telephoneNumber The telephone number of the customer.
     * @param username        The username of the customer.
     * @param password        The password of the customer.
     * @param address         The address of the customer.
     */

    public Customer(String businessName, String telephoneNumber, String username, String password, Address address) {
        this.businessName = businessName;
        this.telephoneNumber = telephoneNumber;
        this.username = username;
        this.password = password;
        this.address = address;
    }

    /**
     * Constructor by default for Customer.
     */

    public Customer() {

    }

    /**
     * Gets the unique identifier for the customer.
     * @return The customer's unique identifier.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Sets the unique identifier for the customer.
     * @param customerID The new unique identifier for the customer.
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Gets the business name of the customer.
     * return The business name of the customer.
     */
    public String getBusinessName() {
        return businessName;
    }

    /**
     * Sets the business name of the customer.
     * @param businessName The new business name for the customer.
     */
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    /**
     * Gets the telephone number of the customer.
     * @ return The telephone number of the customer.
     */
    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    /**
     * Sets the telephone number of the customer.
     * @param telephoneNumber The new telephone number for the customer.
     */
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    /**
     * Gets the username of the customer.
     * @ return The username of the customer.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the customer.
     * @param username The new username for the customer.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the customer.
     * @ return The password of the customer.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the customer.
     * @param password The new password for the customer.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the address of the customer.
     * @ return The address of the customer.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the address of the customer.
     * @param address The new address for the customer.
     */
    public void setAddress(Address address) {
        this.address = address;
    }
}
