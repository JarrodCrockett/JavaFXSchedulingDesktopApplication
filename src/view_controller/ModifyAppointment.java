package view_controller;
/**
 *
 * @author Jarrod Crockett
 */
import dbAccess.AppointmentDAOImpl;
import dbAccess.ContactsDAOImpl;
import dbAccess.CustomerDAOImpl;
import dbAccess.UserDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import utilities.AlertMessage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**ModifyAppointment Class. This class is used to create a controller for the ModifyAppointmentScreen. It has methods that interact with the screen and database.*/
public class ModifyAppointment implements Initializable{

    private Stage stage;
    private Parent scene;
    private Appointment appointment;
    ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
    ZonedDateTime startZDT;
    ZonedDateTime endZDT;

    @FXML
    private TextField idTB;

    @FXML
    private TextField titleTB;

    @FXML
    private TextField descriptionTB;

    @FXML
    private TextField locationTB;

    @FXML
    private ComboBox<Contacts> contactCB;

    @FXML
    private ComboBox<Integer> monthCB;

    @FXML
    private ComboBox<Integer> dayCB;

    @FXML
    private ComboBox<Integer> yearCB;

    @FXML
    private ComboBox<LocalTime> startTimeCB;

    @FXML
    private ComboBox<LocalTime> endTimeCB;

    @FXML
    private TextField typeTB;


    @FXML
    private TextField customerIDTB;

    @FXML
    private TextField userIDTB;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    /**This method is used to send data from the main screen for the selected appointment.
     * This method contains a lambda expression for removing the appointment being modified from its list of appointments.
     * This allows for the appointments list to stay up to date with a small amount of code.
     * This helps to insure there are not appointments scheduled during the same time.
     * @param appointment the appointment that was selected from the main screen.*/
    public void sendData(Appointment appointment){
        appointments = AppointmentDAOImpl.getAllAppointments();

        appointments.removeIf(appointment1 -> appointment.getId() == appointment1.getId());

        setAppointment(appointment);

        setMonths();
        setYears();

        setStartTime();
        setEndTime();

        this.idTB.setText(Integer.toString(appointment.getId()));
        this.titleTB.setText(appointment.getTitle());
        this.descriptionTB.setText(appointment.getDescription());
        this.locationTB.setText(appointment.getLocation());
        this.typeTB.setText(appointment.getType());
        this.customerIDTB.setText(Integer.toString(appointment.getCustomerID()));
        this.userIDTB.setText(Integer.toString(appointment.getUserID()));

        this.contactCB.getSelectionModel().select(setContact(appointment.getContactName()));

        this.monthCB.setValue(appointment.getStartDateTime().getMonthValue());
        setDays(appointment.getStartDateTime().getMonthValue());
        this.dayCB.setValue(appointment.getStartDateTime().getDayOfMonth());

        this.yearCB.setValue(appointment.getStartDateTime().getYear());

        this.startTimeCB.getSelectionModel().select(LocalTime.of(appointment.getStartDateTime().getHour(), appointment.getStartDateTime().getMinute()));
        this.endTimeCB.getSelectionModel().select(LocalTime.of(appointment.getEndDateTime().getHour(), appointment.getEndDateTime().getMinute()));
        this.userIDTB.setText(Integer.toString(appointment.getUserID()));

    }

    /**This method is used to set the contact list of selectable contacts in the combo box when loading the screen.
     * @param contactName the contact names from the database
     * @return Returns a contact*/
    private Contacts setContact(String contactName){
        ObservableList<Contacts> contacts = ContactsDAOImpl.getAllContacts();
        contactCB.setPromptText("Select A Contact");
        contactCB.setItems(contacts);
        for (Contacts contactSelected: contacts){
            if (contactSelected.getContactName().equals(contactName)){
                return contactSelected;
            }
        }
        return null;
    }

    /**This method is used to set the appointment that was sent from the main screen to be set for modifying on this screen.
     * @param appointment The appointment that was selected for modifying in the main screen.*/
    private void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    /**This method cancels modifying an appointment and returns to the main screen of the application.
     * This method contains a lambda alert confirming the user wants to return to the main screen.
     * This allows for an internal method to be run for clicking the OK button with a smaller amount of code.
     * @param event the close button being clicked.*/
    @FXML
    public void cancelAdd(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Add Appointment");
        alert.setHeaderText("Confirmation");
        alert.setContentText("Do you want to cancel your updated to the appointment?");

        //Lambda expression used to receive confirmation from the user to leave the modify appointment.
        alert.showAndWait().ifPresent((response ->
        {
            if (response == ButtonType.OK) {
                stage = (Stage)((Button) event.getSource()).getScene().getWindow();
                try {
                    scene = FXMLLoader.load(getClass().getResource("../View_Controller/MainScreen.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }));
    }

    /**This method is used to listen for the save button to be pressed and saves the modifications to the appointment and sends them to the database.
     * @param event the save button being pressed*/
    @FXML
    public void saveAppointment(ActionEvent event) throws SQLException {

        if (!validationCheck())
            return;

        if (!appointmentTimeCheck())
            return;
        int appointmentID = appointment.getId();
        String title = titleTB.getText().trim();
        String description = descriptionTB.getText().trim();
        String location = locationTB.getText().trim();
        String type = typeTB.getText().trim();

        Timestamp startUTCTimestamp = Timestamp.valueOf(DateTime.utcZDT(startZDT).toLocalDateTime());
        Timestamp endUTCTimestamp = Timestamp.valueOf(DateTime.utcZDT(endZDT).toLocalDateTime());

        int customerID = Integer.parseInt(customerIDTB.getText().trim());

        int userID = Integer.parseInt(userIDTB.getText().trim());
        int contactID = contactCB.getSelectionModel().getSelectedItem().getContactID();

        AppointmentDAOImpl.modifyAppointment(appointmentID, title, description, location, type, startUTCTimestamp, endUTCTimestamp, customerID, userID, contactID);

        AlertMessage.addAppointmentInfo();

        stage = (Stage)((Button) event.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(getClass().getResource("../View_Controller/MainScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method is used as an error check for the appointment times. This insures the appointment in scheduled during business hours.
     * @return Returns a boolean value for if the appointment is within business hours*/
    private boolean appointmentTimeCheck() {
        LocalDate dateOfAppointment = LocalDate.of(yearCB.getValue(),monthCB.getValue(),dayCB.getValue());
        LocalTime startTimeOfAppointment = LocalTime.of(startTimeCB.getValue().getHour(), startTimeCB.getValue().getMinute());
        LocalTime endTimeOfAppointment = LocalTime.of(endTimeCB.getValue().getHour(), endTimeCB.getValue().getMinute());

        this.startZDT = ZonedDateTime.of(dateOfAppointment,startTimeOfAppointment,localZoneId);
        this.endZDT = ZonedDateTime.of(dateOfAppointment,endTimeOfAppointment,localZoneId);


        DateTime.cstZDT(this.startZDT.withZoneSameInstant(DateTime.cstZoneId()));
        if (this.startZDT.isAfter(this.endZDT)){
            AlertMessage.addAppointmentError(11);
            return false;
        }
        if (!this.startZDT.isAfter(ZonedDateTime.now()) || !this.endZDT.isAfter(ZonedDateTime.now())){
            AlertMessage.addAppointmentError(10);
            return false;
        }

        if (!DateTime.cstBusinessHours(this.startZDT, this.endZDT)){
            AlertMessage.addAppointmentError(12);
            return false;
        }

        for (Appointment app: appointments) {
            if (app.getStartDateTime().getMonth().equals(startZDT.getMonth()) && app.getStartDateTime().getDayOfMonth() == startZDT.getDayOfMonth()){
                if (app.getStartDateTime().isEqual(startZDT.toLocalDateTime())){
                    AlertMessage.addAppointmentError(13);
                    return false;
                }
                if (app.getStartDateTime().isBefore(startZDT.toLocalDateTime()) && app.getEndDateTime().isAfter(startZDT.toLocalDateTime())){
                    AlertMessage.addAppointmentError(13);
                    return false;
                }
                if (app.getStartDateTime().isBefore(endZDT.toLocalDateTime()) && app.getEndDateTime().isAfter(endZDT.toLocalDateTime())){
                    AlertMessage.addAppointmentError(13);
                    return false;
                }
            }
        }
        return true;
    }

    /**This method is used for validation check to insure that each field is correctly filled.
     * @return Returns a boolean for completion of each field*/
    private boolean validationCheck(){
        if (titleTB.getText().trim().isEmpty()) {
            AlertMessage.addAppointmentError(1);
            return false;
        }
        if (descriptionTB.getText().trim().isEmpty()) {
            AlertMessage.addAppointmentError(2);
            return false;
        }
        if (locationTB.getText().trim().isEmpty()){
            AlertMessage.addAppointmentError(3);
            return false;
        }
        if (typeTB.getText().trim().isEmpty()){
            AlertMessage.addAppointmentError(4);
            return false;
        }
        if (customerIDTB.getText().trim().isEmpty()){
            AlertMessage.addAppointmentError(5);
            return false;
        }
        if (userIDTB.getText().trim().isEmpty()){
            AlertMessage.addAppointmentError(6);
            return false;
        }
        if (contactCB.getSelectionModel().isEmpty()){
            AlertMessage.addAppointmentError(7);
            return false;
        }
        if (!customerCheck()){
            AlertMessage.addAppointmentError(8);
            return false;
        }
        if (!userCheck()){
            AlertMessage.addAppointmentError(9);
            return false;
        }
        return true;
    }

    /**This method is used to check the database for if the customer entered does exist. This allows for referential integrity within the database.
     * @return Returns a boolean for if the customer exist in the database*/
    private boolean customerCheck(){
        ObservableList<Customer> customerList = CustomerDAOImpl.getAllCustomers();
        int customerId = Integer.parseInt(customerIDTB.getText().trim());
        for(Customer customer: customerList){
            if (customerId == customer.getId()){
                return true;
            }
        }
        return false;
    }

    /**This method is used to check the database for if the user entered does exist. This allows for referential integrity within the database.
     * @return Returns a boolean for if the user exist in the database*/
    private boolean userCheck(){
        ObservableList<User> userList = UserDAOImpl.userList();
        int userID = Integer.parseInt(userIDTB.getText().trim());
        for (User user: userList){
            if (userID == user.getUserID()){
                return true;
            }
        }
        return false;
    }

    /**This method sets the amount of days in each month for the combo boxes.
     * @param event reads how many days to add for each month when a month is selected*/
    @FXML
    public void setDayAmount(ActionEvent event) {
        setDays(monthCB.getValue());
    }

    /**This method sets the amount of days in each month for the combo boxes.
     * @param monthNum reads how many days to add for each month when a month is selected*/
    private void setDays(int monthNum){
        dayCB.getItems().remove(0,dayCB.getItems().size());
        int month = 31;
        if (monthNum == 2){
            if (leapYearCheck()){
                month = 29;
            }else {
                month = 28;
            }
        }
        int[] thirtyDays = {4, 6, 9, 11};
        for (int monthL:thirtyDays) {
            if (monthNum == monthL){
                month = 30;
                break;
            }
        }
        for (int i = 1;i < month+1;i++){
            dayCB.getItems().add(i);
        }

    }

    /**This method sets the months in the combo box when the screen is loaded.*/
    private void setMonths(){
        for (int i = 1;i <13; i++){
            monthCB.getItems().add(i);
        }
    }

    /**This method sets the years in the combo box when the screen is loaded.*/
    private void setYears(){
        int thisYear = LocalDateTime.now().getYear();
        for (int i = 0;i < 4;i++){
            yearCB.getItems().add(thisYear+i);
        }
    }

    /**This method is used to test for leap year for adding days to the month of February and returns a boolean to the day setter.
     * @return Returns a boolean to the day setter method*/
    private boolean leapYearCheck(){
        int year = yearCB.getSelectionModel().getSelectedItem();
        boolean leapYear = false;

        if (year % 4 == 0) {
            if (year % 100 == 0){
                if (year % 400 == 0){
                    leapYear = true;
                }else {
                    leapYear = false;
                }
            }else {
                leapYear = true;
            }
        }
        return leapYear;
    }

    /**This method sets the hours and minutes inside the combo box at 15 minute increments when the screen is loaded.*/
    private void setStartTime(){
        LocalTime start = LocalTime.of(8,0);
        LocalTime end = LocalTime.of(22,0);

        while (start.isBefore(end.plusSeconds(1))){
            startTimeCB.getItems().add(start);
            start = start.plusMinutes(15);
        }
    }

    /**This method sets the hours and minutes inside the combo box at 15 minute increments when the screen is loaded.*/
    private void setEndTime(){
        LocalTime start = LocalTime.of(8,15);
        LocalTime end = LocalTime.of(22,0);

        while (start.isBefore(end.plusSeconds(1))){
            endTimeCB.getItems().add(start);
            start = start.plusMinutes(15);
        }
    }

    /**This method is used to initialize the scene at the start of modifying an appointment.
     * @param location the location of the screen
     * @param resources the resourceBundle used*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

