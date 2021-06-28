package model;
/**
 *
 * @author Jarrod Crockett
 */

/**Customer class. This class is used to create customers for the application from data retrieved from the database.*/
public class Customer {

    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String division;
    private String country;
    private String phoneNumber;

    public Customer(int id, String name, String address, String postalCode, String division, String country, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.division = division;
        this.country = country;
        this.phoneNumber = phoneNumber;
    }

    /**This method returns the division in a string.
     * @return Returns a String*/
    @Override
    public String toString() {
        return division;
    }

    /**This method gets the id of the customer.
     * @return Returns and integer of the id.*/
    public int getId() {
        return id;
    }

    /**This method sets the ID for the customer.
     * @param id the id to be set*/
    public void setId(int id) {
        this.id = id;
    }

    /**This method gets the name of a customer.
     * @return Returns a String of the name*/
    public String getName() {
        return name;
    }

    /**This method sets the name of the customer.
     * @param name the name to set for the customer*/
    public void setName(String name) {
        this.name = name;
    }

    /**This method gets the address of the customer.
     * @return Returns a String of the address*/
    public String getAddress() {
        return address;
    }

    /**This method sets the address for the customer.
     * @param address to set for the customer address.*/
    public void setAddress(String address) {
        this.address = address;
    }

    /**This method gets the postal code of the customer.
     * @return Returns a String of the postal code*/
    public String getPostalCode() {
        return postalCode;
    }

    /**This method sets the postal code field for the customer.
     * @param postalCode Sets the postal code field*/
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**This method gets the division of the customer.
     * @return Returns a String of the division for the customer*/
    public String getDivision() {
        return division;
    }

    /**This method sets the division of the customer.
     * @param division used to set the division*/
    public void setDivision(String division) {
        this.division = division;
    }

    /**This method gets the country of the customer.
     * @return Returns a String of the country*/
    public String getCountry() {
        return country;
    }

    /**This method Sets the country of the customer.
     * @param country Used to set the customers country field*/
    public void setCountry(String country) {
        this.country = country;
    }

    /**This method gets the phone number of the customer.
     * @return Returns a String of the phone number of the customer.*/
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**This method sets the phone number for the customer.
     * @param phoneNumber Used to set the phone number for the customer*/
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
