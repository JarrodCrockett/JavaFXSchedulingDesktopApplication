package dbAccess;
/**
 *
 * @author Jarrod Crockett
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import utilities.DBConnection;
import java.sql.*;
import java.time.LocalDateTime;

/**UserDAOImpl class. This class accesses all the users in the database.*/
public class UserDAOImpl {

    /**This method checks if a user exist in the database and returns an int for error checking.
     * @param userName the user name entered by the user to be checked
     * @param password the password entered by the user to be checked
     * @return Returns an integer for checking if the user and password exist in the database*/
    public static int checkUserAccess(String userName, String password)  {

        ObservableList<User> users = FXCollections.observableArrayList();

        try {
            String sqlUserAth = "SELECT User_ID, User_Name, Password, Last_Update FROM users;";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sqlUserAth);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int dbUserID = rs.getInt("User_ID");
                String dbUsername = rs.getString("User_Name");
                String dbPassword = rs.getString("Password");
                LocalDateTime lastUpdate = (rs.getTimestamp("Last_Update")).toLocalDateTime();

                User user = new User(dbUserID, dbUsername, dbPassword, lastUpdate);
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        for (User user: users) {
            if (userName.equals(user.getUserName()) && password.equals(user.getUserPassword())){
                return 1;
            }
        }
        return -1;
    }

    /**This method returns an Observable list of users.
     * @return  Returns an Observable List of users.*/
    public static ObservableList<User> userList() {

        ObservableList<User> users = FXCollections.observableArrayList();

        try {
            String sqlUserAth = "SELECT User_ID, User_Name, Password, Last_Update FROM users;";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sqlUserAth);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int dbUserID = rs.getInt("User_ID");
                String dbUsername = rs.getString("User_Name");
                String dbPassword = rs.getString("Password");
                LocalDateTime lastUpdate = (rs.getTimestamp("Last_Update")).toLocalDateTime();

                User user = new User(dbUserID, dbUsername, dbPassword, lastUpdate);
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return users;
    }


}
