package org.example;

/**
 * Represents an address with details such as ID, address lines, country, and postal code.
 */
public class Address {
    /** The unique identifier for the address. */
    int id;

    /** The first line of the address. */
    String addressLine1;

    /** The second line of the address. */
    String addressLine2;

    /** The third line of the address. */
    String addressLine3;

    /** The country of the address. */
    String country;

    /** The postal code of the address. */
    String postCode;

    /**
     * Constructs a new Address with the specified details, including an ID.
     * id            The unique identifier for the address.
     * addressLine1  The first line of the address.
     * addressLine2  The second line of the address.
     * addressLine3  The third line of the address.
     * country       The country of the address.
     * postCode      The postal code of the address.
     */
    public Address(String addressLine1, String addressLine2, String addressLine3, String country, String postCode) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
        this.country = country;
        this.postCode = postCode;
    }

    public Address() {

    }

    /**
     * Gets the unique identifier for the address.
     * return The address's unique identifier.
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddressLine1() {
        return addressLine1;
    }
    /**
     * Sets the first line of the address.
     * addressLine1 The new first line of the address.
     */
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }
    /**
     * Gets the country of the address.
     * return The country of the address.
     */
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
