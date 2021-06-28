package model;
/**
 *
 * @author Jarrod Crockett
 */
import java.time.LocalDateTime;

/**FirstLevelDivisions Class. Is used to create a first level division for the first level division retrieved from the database.*/
public class FirstLevelDivisions {

    private int divisionID;
    private String division;
    private LocalDateTime dateTime;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int countryID;

    public FirstLevelDivisions(int divisionID, String division, LocalDateTime lastUpdate, int countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.lastUpdate = lastUpdate;
        this.countryID = countryID;
    }

    /**This method is used to return the division as a string for the Combo boxes.
     * @return Returns a String of the division*/
    @Override
    public String toString() {
        return division;
    }

    /**This method gets the division id of the division.
     * @return Returns an integer for the division ID*/
    public int getDivisionID() {
        return divisionID;
    }

    /**This method sets the division id for the division.
     * @param divisionID The division id to set*/
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**This method gets the division name of the division.
     * @return Returns a String of the division*/
    public String getDivision() {
        return division;
    }

    /**This method sets the division name.
     * @param division used to set the division name*/
    public void setDivision(String division) {
        this.division = division;
    }

    /**This method gets the division local date time.
     * @return Returns a LocalDateTime of the division*/
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**This method sets the date time of the division.
     * @param dateTime used to set the date and time for the division*/
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**This method gets the name of the creator.
     * @return Returns a String of the name of the creator*/
    public String getCreatedBy() {
        return createdBy;
    }

    /**This method sets the name of the divisions creator.
     * @param createdBy Used to set the created by field*/
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**This method gets the local date time of the last time the division was updated.
     * @return Returns a LocalDateTime for the last time the division was updated*/
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**This method sets the last updated local date time field for the division.
     * @param lastUpdate Used to set the lastUpdated field*/
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**This method gets the last persons name that updated the division.
     * @return Returns a String of the name of the person the updated the division.*/
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**This method sets the last updated by field for the division.
     * @param lastUpdatedBy Used to update the field for the lastUpdatedBy field for the division*/
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**This method gets the country ID for the division.
     * @return Returns an integer for the country ID*/
    public int getCountryID() {
        return countryID;
    }

    /**This method sets the country ID for the division.
     * @param countryID Used to set the divisions country ID field.*/
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
}
