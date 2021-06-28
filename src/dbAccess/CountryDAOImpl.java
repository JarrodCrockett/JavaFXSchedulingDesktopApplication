package dbAccess;
/**
 *
 * @author Jarrod Crockett
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;
import model.FirstLevelDivisions;
import utilities.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**CountryDAOImpl class. Accesses the Countries in the database.*/
public class CountryDAOImpl {

    /**This method returns a list of all the countries from the database.
     * @return Returns an ObservableList of countries.*/
    public static ObservableList<Countries> getAllCountries(){
        ObservableList<Countries> countryList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM countries;";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int countryID = rs.getInt("Country_ID");
                String country = rs.getString("Country");
                LocalDateTime lastUpdate = (rs.getTimestamp("Last_Update")).toLocalDateTime();

                Countries countries = new Countries(countryID, country, lastUpdate);
                countryList.add(countries);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countryList;
    }

    /**This returns the division id from the database for a given country name.
     * @param  countryName the String name of the country
     * @return Returns an int value used for error checking by method call*/
    public static int getDivisionID(String countryName) throws SQLException {

        String sql = "SELECT * FROM countries where first_level_divisions.Division = '" + countryName + "';";

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        int divisionID = rs.getInt("Division_ID");
        String division = rs.getString("Division");
        LocalDateTime lastUpdate = (rs.getTimestamp("Last_Update")).toLocalDateTime();
        int countryID2 = rs.getInt("Country_ID");

        FirstLevelDivisions firstLevelDivisions = new FirstLevelDivisions(divisionID, division, lastUpdate, countryID2);

        return firstLevelDivisions.getDivisionID();
    }
}
