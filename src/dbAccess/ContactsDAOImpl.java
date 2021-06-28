package dbAccess;
/**
 *
 * @author Jarrod Crockett
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;
import utilities.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**ContactsDAOImpl class. Accesses the contacts in the database.*/
public class ContactsDAOImpl {


    /**This method returns a list of all the contacts in the database.
     * @return Returns an ObservableList of all contacts*/
    public static ObservableList<Contacts> getAllContacts() {

        ObservableList<Contacts> contactList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM contacts;";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String Email = rs.getString("Email");

                Contacts contact = new Contacts(contactID, contactName, Email);
                contactList.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contactList;

    }
}
