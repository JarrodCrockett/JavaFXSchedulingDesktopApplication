package dbAccess;
/**
 *
 * @author Jarrod Crockett
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import utilities.DBConnection;
import utilities.DBQuery;
import java.sql.*;
import java.time.LocalDateTime;

/**AppointmentDAOImpl class accesses the appointments in the database.*/
public class AppointmentDAOImpl {

    /**This method returns an Observable List of all appointments
     * @return Returns an ObservableList of all appointments*/
    public static ObservableList<Appointment> getAllAppointments() {

        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, contacts.Contact_Name, " +
                    "appointments.Type, appointments.Start, appointments.End, appointments.Customer_ID, appointments.User_ID  FROM appointments" +
                    " inner join contacts on appointments.Contact_ID = contacts.Contact_ID Order by appointments.Appointment_ID;";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointment_id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String contact_name = rs.getString("Contact_Name");
                String type = rs.getString("Type");
                Timestamp startDateTime = rs.getTimestamp("Start");
                Timestamp endDateTime = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");

                LocalDateTime start = startDateTime.toLocalDateTime();
                LocalDateTime end = endDateTime.toLocalDateTime();

                Appointment appointment = new Appointment(appointment_id, title, description, location, contact_name, type, start, end, customerID);
                appointment.setUserID(userID);
                appointmentsList.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentsList;
    }
    /**This method takes in the fields of an appointment and adds a new appointment in the database.
     * @param title the title
     * @param description the description
     * @param location the location
     * @param type the type
     * @param start the start time of the appointment
     * @param end the end time of the appointment
     * @param customerID the customer id of the appointment
     * @param userID the user id
     * @param contactID the contacts id
     * @throws SQLException accesses the database and throws SQL exception
     * */
    public static void addAppointment(String title, String description, String location, String type, Timestamp start, Timestamp end, int customerID, int userID, int contactID) throws SQLException {
        
        Connection conn = DBConnection.getConnection();

        DBQuery.setStatement(conn);
        Statement statement = DBQuery.getStatement();



        String sqlInsert = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID)" +
                " VALUES('" + title+"','"+ description +"','"+ location +"','"+ type +"','" + start + "','" + end + "',"+ customerID + "," + userID + "," + contactID +");";


        statement.execute(sqlInsert);

    }

    /** This method modifies an existing appointment in the database.
     * @param appointmentID the appointments id used to reference the correct appointment
     * @param title the title
     * @param description the description
     * @param location the location
     * @param type the type
     * @param start the start time of the appointment
     * @param end the end time of the appointment
     * @param customerID the customer id of the appointment
     * @param userID the user id
     * @param contactID the contacts id
     * @throws SQLException accesses the database and throws SQL exception
     * */
    public static void modifyAppointment(int appointmentID, String title, String description, String location, String type, Timestamp start, Timestamp end, int customerID, int userID, int contactID) throws SQLException {

        Connection conn = DBConnection.getConnection();

        DBQuery.setStatement(conn);

        Statement statement = DBQuery.getStatement();

        String sqlInsert = "UPDATE appointments SET Title = '" + title + "', Description = '" + description + "', Location = '" + location + "', Type = '" + type +
                "' , Start = '" + start + "', End = '" + end + "', Customer_ID = " + customerID + ", User_ID = " + userID + ", Contact_ID = " + contactID +
                " WHERE Appointment_ID = " + appointmentID + ";";


        statement.execute(sqlInsert);
    }

    /**This method deletes an existing appointment from the database.
     * @param appointmentID the appointment id of the appointment to delete*/
    public static void deleteAppointment(int appointmentID) throws SQLException {
        String sqlDelete = "DELETE FROM appointments WHERE Appointment_ID = " + appointmentID + ";";

        Connection conn = DBConnection.getConnection();

        DBQuery.setStatement(conn);
        Statement statement = DBQuery.getStatement();

        statement.execute(sqlDelete);
    }

    /**This method gets all the distinct types from the database and returns them in an Observable List.
     * @return Returns an ObservableList of distinct types*/
    public static ObservableList<String> getDistinctTypes(){
        ObservableList<String> typeList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT DISTINCT appointments.Type FROM appointments;";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String type = rs.getString("Type");

                typeList.add(type);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return typeList;
    }


}
