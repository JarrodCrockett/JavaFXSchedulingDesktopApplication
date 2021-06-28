package dbAccess;
/**
 *
 * @author Jarrod Crockett
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivisions;
import utilities.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**FirstLevelDivisionsDAOImpl class. This class accesses all the first level divisions table in the database.*/
public class FirstLevelDivisionsDAOImpl {

    /**This method takes in a countryID and returns all the states or provinces of that country.
     * @param countryID the country ID
     * @return Returns an Observable list of all the states or provinces*/
    public static ObservableList<FirstLevelDivisions> getAllStatesProvinces(int countryID) {

        ObservableList<FirstLevelDivisions> stateProvinceList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = " + countryID + " order by Division;";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                LocalDateTime lastUpdate = (rs.getTimestamp("Last_Update")).toLocalDateTime();
                int countryID2 = rs.getInt("Country_ID");

                FirstLevelDivisions firstLevelDivisions = new FirstLevelDivisions(divisionID, division, lastUpdate, countryID2);
                stateProvinceList.add(firstLevelDivisions);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stateProvinceList;

    }

    /**This method gets the division ID from from the database using the division name.
     * @param divisionName the division name
     * @return  Returns an integer of the division ID*/
    public static int getDivisionID(String divisionName) throws SQLException {


        String sql = "SELECT * FROM first_level_divisions where first_level_divisions.Division = '" + divisionName + "';";

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        FirstLevelDivisions firstLevelDivisions = null;
        while (rs.next()){
            int divisionID = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            LocalDateTime lastUpdate = (rs.getTimestamp("Last_Update")).toLocalDateTime();
            int countryID2 = rs.getInt("Country_ID");

            firstLevelDivisions = new FirstLevelDivisions(divisionID, division, lastUpdate, countryID2);

        }
        if (firstLevelDivisions == null){
            return -1;
        }
        return firstLevelDivisions.getDivisionID();

    }


}
