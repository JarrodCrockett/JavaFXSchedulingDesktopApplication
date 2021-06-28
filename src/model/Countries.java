package model;
/**
 *
 * @author Jarrod Crockett
 */
import java.time.LocalDateTime;

/**Countries class. Is used to save the information of countries that are retrieved from the database.*/
public class Countries {

    private int countryID;
    private String country;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdated;
    private String lastUpdatedBy;

    public Countries(int countryID, String country, LocalDateTime lastUpdated) {
        this.countryID = countryID;
        this.country = country;
        this.lastUpdated = lastUpdated;
    }

    /**This method is used to return the country name for the combo boxes.
     * @return Returns a String of the country*/
    @Override
    public String toString() {
        return country;
    }

    /**This method is used to return the country id of the country.
     * @return Returns an integer of the country ID*/
    public int getCountryID() {
        return countryID;
    }

    /**This method sets the country id for the country
     * @param countryID the country id to set for the country*/
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**This method gets the country name of the country.
     * @return Returns a String of the country.*/
    public String getCountry() {
        return country;
    }

    /**This method sets the country name for the country.
     * @param country the country name to set the country to.*/
    public void setCountry(String country) {
        this.country = country;
    }

    /**This method gets the create date of the country.
     * @return Returns a Local Date Time of the create date*/
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**This method sets the create date for the country
     * @param createDate The date to set for the country*/
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**This method gets the person that created the country entry.
     * @return Returns a String of the name of the creator*/
    public String getCreatedBy() {
        return createdBy;
    }

    /**This method sets the created by field in the country.
     * @param createdBy is the name of the person that created the country entry*/
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**This method gets the last time the country was updated in the database.
     * @return Returns the LocalDateTime of the last time country was updated in the database*/
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    /**This method sets the last updated time on the country.
     * @param lastUpdated the Local date time of the update for the country*/
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**This method gets the last person the updated the country entry.
     * @return Returns a String of the last person the updated the country entry*/
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**This method sets the last person to update the country entry.
     * @param lastUpdatedBy Sets the countries last updated by name*/
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
