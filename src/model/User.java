package model;
/**
 *
 * @author Jarrod Crockett
 */
import java.time.LocalDateTime;

/**User Class. Used to create users from data retrieved from the database. This allows user credentials to be evaluated.*/
public class User {
    private int userID;
    private String userName;
    private String userPassword;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdate;

    public User(int userID, String userName, String userPassword, LocalDateTime lastUpdate) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
        this.lastUpdate = lastUpdate;
    }

    /**This method gets the users ID.
     * @return Returns an integer of the users ID*/
    public int getUserID() {
        return userID;
    }

    /**This method sets the users ID for the user.
     * @param userID Used to set the users ID for the user*/
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**This method gets the users name for the user.
     * @return Returns a String of the users name.*/
    public String getUserName() {
        return userName;
    }

    /**This method sets the users name.
     * @param userName Used to set the user name field.*/
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**This method gets the users password.
     * @return Returns a String of the users password.*/
    public String getUserPassword() {
        return userPassword;
    }

    /**This method sets the password for the user.
     * @param userPassword Used to set the user password field*/
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**This method gets the date the user was created in the database.
     * @return Returns a LocalDateTime for the users creation date.*/
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**This method sets the creation date for the user.
     * @param createDate Used to set the createDate field for the user.*/
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**This method gets the last time the users information was updated.
     * @return Returns a LocalDateTime when the user was last updated.*/
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**This method sets the last time the user was updated field for the user.
     * @param lastUpdate Used to sett he lastUpdate field*/
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
