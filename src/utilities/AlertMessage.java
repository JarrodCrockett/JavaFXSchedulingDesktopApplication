package utilities;
/**
 *
 * @author Jarrod Crockett
 */
import javafx.scene.control.Alert;

/**AlertMessage class. This class is used to populate the error messages for each screen and user input.*/
public class AlertMessage {

    /**This method is used to populate errors for the main screen.
     * @param errorCode Uses an integer to determine the code to create.*/
    public static void mainScreenError(int errorCode){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Main Screen");
        alert.setHeaderText("Error");
        // Appointment Errors
        if (errorCode == 1)
            alert.setContentText("Please select an appointment to update.");
        if (errorCode == 2)
            alert.setContentText("Please select an appointment to delete.");

        // Customer Errors
        if (errorCode == 3)
            alert.setContentText("Please select a customer to update.");
        if (errorCode == 4)
            alert.setContentText("Please select a customer to remove.");
        if (errorCode == 5)
            alert.setContentText("Customers can not be deleted if they have an appointment scheduled. Please delete their scheduled appointments first.");
        alert.showAndWait();
    }

    /**This method is used to populate errors for the add customer and modify customer screens.
     * @param errorCode Uses an integer to determine the code to create.*/
    public static void addCustomerError (int errorCode){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Add Customer");
        alert.setHeaderText("Error");
        if (errorCode == 1)
            alert.setContentText("Please enter a customer name.");
        if (errorCode == 2)
            alert.setContentText("Please enter an address.");
        if (errorCode == 3)
            alert.setContentText("Please enter the postal code");
        if (errorCode == 4)
            alert.setContentText("Please select a country");
        if (errorCode == 5)
            alert.setContentText("Please select a state or province");
        if (errorCode == 6)
            alert.setContentText("Please enter a phone number.");

        alert.showAndWait();
    }

    /**This method is used to create an alert for a successful creation and saved new customer.*/
    public static void addCustomerInfo(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Customer");
        alert.setHeaderText("Customer Saved Successful");
        alert.setContentText("Saving...");
        alert.show();
    }

    /**This method is used to create an alert for a successful creation and saved new appointment.*/
    public static void addAppointmentInfo(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment");
        alert.setHeaderText("Appointment Saved Successful");
        alert.setContentText("Saving...");
        alert.show();
    }

    /**This method is used to populate errors for the add appointment and modify appointment screens.
     * @param errorCode Uses an integer to determine the code to create.*/
    public static void addAppointmentError(int errorCode){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Appointment");
        alert.setHeaderText("Appointment Error");

        if (errorCode == 1)
            alert.setContentText("There must be an appointment title.");
        if (errorCode == 2)
            alert.setContentText("The description box must be filled in.");
        if (errorCode == 3)
            alert.setContentText("The location box must be filled in.");
        if (errorCode == 4)
            alert.setContentText("The type box must be filled in.");
        if (errorCode == 5)
            alert.setContentText("The customer ID must be entered.");
        if (errorCode == 6)
            alert.setContentText("The user ID must be entered.");
        if (errorCode == 7)
            alert.setContentText("A contact must be selected");
        if (errorCode == 8)
            alert.setContentText("The customer ID entered does not exist.");
        if (errorCode == 9)
            alert.setContentText("The user ID entered does not exist.");
        if (errorCode == 10)
            alert.setContentText("The appointment times must be set after your current time.");
        if (errorCode == 11)
            alert.setContentText("The appointment start time must be before the end time.");
        if (errorCode == 12)
            alert.setContentText("The appointment time must be during our business hours between 08:00 CST and 22:00 EST Monday-Friday.");
        if (errorCode == 13)
            alert.setContentText("The appointment time can not overlap with a current appointment already scheduled.");

        alert.showAndWait();

    }

}
