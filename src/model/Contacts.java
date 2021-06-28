package model;
/**
 *
 * @author Jarrod Crockett
 */

/**Contacts class. This class is used to create a contact from information retieved from the database.*/
public class Contacts {

    private int contactID;
    private String contactName;
    private String email;

    public Contacts(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /**This method is used to return a String of the contact name it is used to display in the combo boxes.
     * @return Returns a String of the contact name.*/
    @Override
    public String toString() {
        return contactName;
    }

    /**This method returns the contact id.
     * @return Returns an integer of the contact ID*/
    public int getContactID() {
        return contactID;
    }

    /**This method sets the contact ID.
     * @param contactID the contact ID to set for the contact*/
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**This method is used to get the contact name of the contact.
     * @return Returns a String of the contact name.*/
    public String getContactName() {
        return contactName;
    }

    /**This method is used to set the contact name of the contact.
     * @param contactName the contact name to be set for the contact.*/
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**This method is used to get the email address of the contact.
     * @return Returns a String of the email address of the contact.*/
    public String getEmail() {
        return email;
    }

    /**This method is used to set the email address for the contact.
     * @param email used to set the email address.*/
    public void setEmail(String email) {
        this.email = email;
    }
}
