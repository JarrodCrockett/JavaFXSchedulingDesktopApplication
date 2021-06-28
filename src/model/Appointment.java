package model;
/**
 *
 * @author Jarrod Crockett
 */
import java.time.LocalDateTime;
import java.time.LocalTime;

/**Appointment class. This is used to build appointments from data retrieved from the database.*/
public class Appointment {

    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int customerID;
    private int userID;
    private int contactID;
    private String contactName;

    public Appointment(int id, String title, String description, String location, String contactName, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customerID = customerID;
        this.contactName = contactName;
    }

    /**This method gets the id of the appointment.
     * @return Returns an integer of the id*/
    public int getId() {
        return id;
    }

    /**This method sets the id of the appointment.
     * @param id an integer to set the id of the appointment*/
    public void setId(int id) {
        this.id = id;
    }

    /**This method gets the title of the appointment.
     * @return Returns a string the title of the appointment*/
    public String getTitle() {
        return title;
    }

    /**This method sets the title on the appointment.
     * @param title the title of the appointment*/
    public void setTitle(String title) {
        this.title = title;
    }

    /**This method gets the description of the appointment.
     * @return Returns a string of the description of the appointment*/
    public String getDescription() {
        return description;
    }

    /**This sets the description of the appointment.
     * @param description this is the description of the appointment*/
    public void setDescription(String description) {
        this.description = description;
    }

    /**This method gets the location of the appointment.
     * @return Returns a string of the location of the appointment*/
    public String getLocation() {
        return location;
    }

    /**This method sets the location for the appointment.
     * @param location the location to set the appointment to.*/
    public void setLocation(String location) {
        this.location = location;
    }

    /**This method gets the type of appointment.
     * @return Returns a string of the type of appointment*/
    public String getType() {
        return type;
    }

    /**This method sets the type of the appointment.
     * @param type the type to set for the appointment*/
    public void setType(String type) {
        this.type = type;
    }

    /**This method gets the start time and date of the appointment.
     * @return Returns a LocalDateTime for the start of the appointment*/
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /**This method sets the start date and time for the appointment.
     * @param startDateTime the start date and time to set the on the appointment*/
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**This method gets end time and date of the appointment.
     * @return Returns a LocalDateTime for the end of the appointment*/
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /**This method sets the end date and time for the appointment.
     * @param endDateTime this is the end date an time to set the appointment to.*/
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**This method gets the customer id of the appointment.
     * @return Returns an integer of the customer id*/
    public int getCustomerID() {
        return customerID;
    }

    /**This sets the customer id on the appointment.
     * @param customerID the customer ID to set on the appointment.*/
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**This method gets the user id of the appointment.
     * @return Returns an integer of the user id*/
    public int getUserID() {
        return userID;
    }

    /**This method sets the user id on the appointment.
     * @param userID this is the user ID to set on the appointment*/
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**This method gets the contact id of the appointment.
     * @return Returns an integer of the contact id*/
    public int getContactID() {
        return contactID;
    }

    /**This method sets the contact ID on the appointment.
     * @param contactID the id to set for the contact on the appointment.*/
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**This method gets the contact name of the appointment.
     * @return Returns a string of the contact name on the appointment*/
    public String getContactName() {
        return contactName;
    }

    /**This method sets the contact name on the appointment.
     * @param contactName the contact name to set on the appointment*/
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
