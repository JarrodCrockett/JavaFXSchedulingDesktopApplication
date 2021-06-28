package view_controller;
/**
 *
 * @author Jarrod Crockett
 */
import dbAccess.AppointmentDAOImpl;
import dbAccess.CustomerDAOImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.DateTime;
import utilities.AlertMessage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**MainScreen class. This class is used as the controller for the main screen of the application.
 * Its methods can be used to enter the other screens as well as delete appointments or customers from the database.*/
public class MainScreen implements Initializable {

    Stage stage;
    Parent scene;
    ZonedDateTime currentZDT;

    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TableColumn<Appointment, Integer> appID;

    @FXML
    private TableColumn<Appointment, String> apptitle;

    @FXML
    private TableColumn<Appointment, String> appDesc;

    @FXML
    private TableColumn<Appointment, String> appLoca;

    @FXML
    private TableColumn<Appointment, String> appCont;

    @FXML
    private TableColumn<Appointment, String> apptype;

    @FXML
    private TableColumn<Appointment, DateTime> appSD;

    @FXML
    private TableColumn<Appointment, DateTime> appED;

    @FXML
    private TableColumn<Appointment, Integer> appCustID;

    @FXML
    private Label appointmentAlert;

    @FXML
    private Button addAppBtn;

    @FXML
    private Button updateAppBtn;

    @FXML
    private Button deleteAppBtn;

    @FXML
    private RadioButton filterWeekRadioBtn;

    @FXML
    private RadioButton filterMonthRadioBtn;

    @FXML
    private RadioButton allSelectedRadio;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, Integer> custCustID;

    @FXML
    private TableColumn<Customer, String> custName;

    @FXML
    private TableColumn<Customer, String> custAddress;

    @FXML
    private TableColumn<Customer, String> custPostCode;

    @FXML
    private TableColumn<Customer, String> custDivi;

    @FXML
    private TableColumn<Customer, String> custCountry;

    @FXML
    private TableColumn<Customer, String> custPhone;

    @FXML
    private Button addCustBtn;

    @FXML
    private Button updateCustBtn;

    @FXML
    private Button deleteCustBtn;

    @FXML
    private Button closeProgBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button reportBtn;

    @FXML
    private Button contactReportBtn;

    @FXML
    private Button customerReportBtn;

    @FXML
    private Button divisionReportBtn;

    @FXML
    private TextField customerSearchTB;


    ObservableList<Customer> customerList = FXCollections.observableArrayList();
    ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    ObservableList<Appointment> weeklyAppointmentList = FXCollections.observableArrayList();
    ObservableList<Appointment> monthlyAppointmentList = FXCollections.observableArrayList();

    /** This method listens for the search customer text box to be clicked and text entered. It then updates the customer table.
     * @param event the search text box being clicked*/
    @FXML
    public void searchCustomers(ActionEvent event) {
        ObservableList<Customer> searchList = FXCollections.observableArrayList();
        if (customerSearchTB.getText().isEmpty()){
            updateCustomerTable();
        }
        else if (!customerSearchTB.getText().isEmpty() && !customerSearchTB.getText().trim().matches("[0-9]*")){
            for (Customer customer: customerList){
                if (customer.getName().toLowerCase(Locale.ROOT).contains(customerSearchTB.getText().trim().toLowerCase(Locale.ROOT))){
                    searchList.add(customer);
                }
            }
            customerTable.setItems(searchList);
        }
        else if (!customerSearchTB.getText().isEmpty() && customerSearchTB.getText().trim().matches("[0-9]*")) {
            for (Customer customer: customerList){
                if (customer.getId() == Integer.parseInt(customerSearchTB.getText().trim())){
                    searchList.add(customer);
                }
            }
            customerTable.setItems(searchList);
        }
    }

    /**This method listens for the add appointment button being clicked. It then loads the add appointment screen.
     * @param event the add appointment button being pressed*/
    @FXML
    public void addApp(ActionEvent event) {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(getClass().getResource("../View_Controller/AddAppointmentScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method listens for the add customer button being clicked. It then loads the add customer screen.
     * @param event the add customer button being pressed*/
    @FXML
    public void addCust(ActionEvent event) {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(getClass().getResource("../View_Controller/AddCustomerScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method listens for the close button to be selected. It closes the application when the button is pressed.
     * @param event the close button being pressed.*/
    @FXML
    public void closeProg(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Main Screen");
        alert.setContentText("Confirmation");
        alert.setContentText("Do you want to Logout of the System?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.CANCEL)
            return;

        Platform.exit();
    }

    /**This method listens for the delete appointment button from being pressed and deletes the selected appointment from the table and database.
     * This method contains two lambda expressions for removing appointments from the weekly and monthly appointment list.
     * This allows for the weekly and monthly appointment list to be updated and added to the tables when the radio selection buttons are pressed.
     * By using lambda expressions this saves code space and time allowing for faster comparison to be made within the list.
     * @param event the delete appointment button being pressed.*/
    @FXML
    public void deleteApp(ActionEvent event) throws SQLException {
        Appointment selected = appointmentTable.getSelectionModel().getSelectedItem();

        if (selected == null){
            AlertMessage.mainScreenError(2);
            return;
        }

        //Two lambda expressions for removing the appointment from the weekly and monthly appointment list.
        weeklyAppointmentList.removeIf(app -> selected.getId() == app.getId());
        monthlyAppointmentList.removeIf(app -> selected.getId() == app.getId());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment");
        alert.setHeaderText("Deleting Appointment");
        alert.setContentText("Deleting Appointment ID: " + selected.getId() + ", Appointment type: " + selected.getType());
        alert.showAndWait();

        AppointmentDAOImpl.deleteAppointment(selected.getId());


        appointmentList = AppointmentDAOImpl.getAllAppointments();

        allSelectedRadio.setSelected(true);

        appointmentTable.setItems(appointmentList);
    }

    /**This method listens for the delete customer button from being pressed and deletes the selected customer from the table and database.
     * @param event the delete customer button being pressed.*/
    @FXML
    public void deleteCust(ActionEvent event) throws SQLException {
        Customer selected = customerTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            AlertMessage.mainScreenError(4);
            return;
        }

        for (Appointment app: appointmentList){
            if (app.getCustomerID() == selected.getId()){
                AlertMessage.mainScreenError(5);
                return;
            }
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Customer");
        alert.setHeaderText("Deleting customer");
        alert.setContentText("Customer: " + selected.getName() + " has been deleted from the database.");
        alert.showAndWait();
        CustomerDAOImpl.deleteCustomer(selected.getId());

        customerTable.setItems(CustomerDAOImpl.getAllCustomers());
    }

    /**This method listens for the logout button to be pressed. It returns the user to the login screen.
     * @param event the logout button being pressed*/
    @FXML
    public void logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Main Screen");
        alert.setContentText("Confirmation");
        alert.setContentText("Do you want to Logout of the System?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.CANCEL)
            return;

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(getClass().getResource("../View_Controller/LoginScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method listens for the update appointment button being clicked. It then loads the update appointment screen.
     * @param event the update appointment button being pressed*/
    @FXML
    public void updateApp(ActionEvent event) throws IOException {
        Appointment selected = appointmentTable.getSelectionModel().getSelectedItem();

        if (CustomerDAOImpl.getAllCustomers().isEmpty()){
            return;
        }
        if (selected == null){
            AlertMessage.mainScreenError(1);
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View_Controller/ModifyAppointmentScreen.fxml"));
        loader.load();

        ModifyAppointment controller = loader.getController();

        controller.sendData(selected);


        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method listens for the update customer button being clicked. It then loads the update customer screen.
     * @param event the update customer button being pressed*/
    @FXML
    public void updateCust(ActionEvent event) throws IOException {
        Customer selected = customerTable.getSelectionModel().getSelectedItem();

        if (CustomerDAOImpl.getAllCustomers().isEmpty()){
            AlertMessage.mainScreenError(3);
            return;
        }
        if (selected == null){
            AlertMessage.mainScreenError(3);
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View_Controller/ModifyCustomerScreen.fxml"));
        loader.load();

        ModifyCustomer controller = loader.getController();

        controller.sendData(selected);


        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method listens for the all radio button being selected. It then populates the appointment table with all appointments in the database.
     * @param event the all radio button being selected.*/
    @FXML
    public void allSelected(ActionEvent event){
        appointmentTable.setItems(appointmentList);
    }

    /**This method listens for the month radio button being selected. It then populates the appointment table with appointments from the current month in the database.
     * @param event the month radio button being selected.*/
    @FXML
    public void monthSelected(ActionEvent event) {
        int month = currentZDT.getMonthValue();
        if (monthlyAppointmentList.isEmpty()){
            for (Appointment appointment: appointmentList){
                if (appointment.getStartDateTime().getMonthValue() == month){
                    monthlyAppointmentList.add(appointment);
                }
            }
        }
        appointmentTable.setItems(monthlyAppointmentList);
    }

    /**This method listens for the week radio button being selected. It then populates the appointment table with appointments from the current week in the database.
     * @param event the week radio button being selected.*/
    @FXML
    public void weekSelected(ActionEvent event) {
        int dayOfWeek = currentZDT.getDayOfWeek().getValue();

        if (!weeklyAppointmentList.isEmpty()){
            appointmentTable.setItems(weeklyAppointmentList);
            return;
        }

        for (Appointment app: appointmentList){
            if (app.getStartDateTime().getMonth().equals(currentZDT.getMonth())){

                if (dayOfWeek == 7 && app.getStartDateTime().isAfter(ChronoLocalDateTime.from(currentZDT)) &&
                app.getStartDateTime().isBefore(ChronoLocalDateTime.from(currentZDT.plusDays(7)))){
                    weeklyAppointmentList.add(app);
                }
                else if (dayOfWeek == 1 && app.getStartDateTime().isAfter(ChronoLocalDateTime.from(currentZDT.minusDays(2)))  &&
                        app.getStartDateTime().isBefore(ChronoLocalDateTime.from(currentZDT.plusDays(5)))){
                    weeklyAppointmentList.add(app);
                }
                else if (dayOfWeek == 2 && app.getStartDateTime().isAfter(ChronoLocalDateTime.from(currentZDT.minusDays(3)))  &&
                        app.getStartDateTime().isBefore(ChronoLocalDateTime.from(currentZDT.plusDays(4)))){
                    weeklyAppointmentList.add(app);
                }
                else if (dayOfWeek == 3 && app.getStartDateTime().isAfter(ChronoLocalDateTime.from(currentZDT.minusDays(3)))  &&
                        app.getStartDateTime().isBefore(ChronoLocalDateTime.from(currentZDT.plusDays(3)))){
                    weeklyAppointmentList.add(app);
                }
                else  if (dayOfWeek == 4 && (app.getStartDateTime().isAfter(ChronoLocalDateTime.from(currentZDT.minusDays(4))))  &&
                        (app.getStartDateTime().isBefore(ChronoLocalDateTime.from(currentZDT.plusDays(3))))){
                    weeklyAppointmentList.add(app);
                }
                else if (dayOfWeek == 5 && (app.getStartDateTime().isAfter(ChronoLocalDateTime.from(currentZDT.minusDays(5))))  &&
                        (app.getStartDateTime().isBefore(ChronoLocalDateTime.from(currentZDT.plusDays(1))))){
                    weeklyAppointmentList.add(app);
                }
                else if (dayOfWeek == 6 && (app.getStartDateTime().isAfter(ChronoLocalDateTime.from(currentZDT.minusDays(7)))) &&
                        app.getStartDateTime().isBefore(ChronoLocalDateTime.from(currentZDT))){
                    weeklyAppointmentList.add(app);
                }
            }
        }
        appointmentTable.setItems(weeklyAppointmentList);
    }

    /**This method sets the current zoned date time of the user.*/
    private void setCurrentZDT(){
        this.currentZDT = ZonedDateTime.now();
    }

    /**This method updates the appointment table with all the appointments in the database.*/
    public void updateAppointmentTable(){
        appointmentTable.setItems(AppointmentDAOImpl.getAllAppointments());
    }

    /**This method updates the customer table with all the customers in the database.*/
    public void updateCustomerTable(){
        customerTable.setItems(CustomerDAOImpl.getAllCustomers());
    }

    /**This method listens for the contact report button being pressed. It then loads the contact report screen.
     * @param event the contact report button being pressed.*/
    @FXML
    public void contactReport(ActionEvent event) {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(getClass().getResource("../View_Controller/ContactReportScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method listens for the customer report button being pressed. It then loads the customer report screen.
     * @param event the customer report button being pressed.*/
    @FXML
    public void customerReport(ActionEvent event) {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(getClass().getResource("../View_Controller/CustomerReportScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method listens for the division report button being pressed. It then loads the division report screen.
     * @param event the division report button being pressed.*/
    @FXML
    public void divisionReportPage(ActionEvent event) {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(getClass().getResource("../View_Controller/DivisionReportScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method sets an appointment reminder for appointments within the next 15 minutes.*/
    private void setAppointmentAlert(){
        String currentAppointments = "There are no appointment scheduled in the next 15 minutes.";
        String customerName = "";
        for (Appointment app: appointmentList){
            if (app.getStartDateTime().isAfter(LocalDateTime.now()) && app.getStartDateTime().isBefore(LocalDateTime.now().plusMinutes(15))){
                for (Customer customer: customerList){
                    if (customer.getId() == app.getCustomerID()){
                        customerName = customer.getName();
                    }
                }
                currentAppointments = "There is an appointment scheduled with: " + customerName + " appointment ID: " + app.getId() + " at "+ app.getStartDateTime() + ".";
            }
        }
        appointmentAlert.setText(currentAppointments);
    }

    /**This method sets the appointment table when the main screen is loaded.*/
    private void setAppointmentTable(){
        appID.setCellValueFactory(new PropertyValueFactory<>("id"));
        apptitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        appDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        appLoca.setCellValueFactory(new PropertyValueFactory<>("location"));
        appCont.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        apptype.setCellValueFactory(new PropertyValueFactory<>("type"));
        appSD.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        appED.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        appCustID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }

    /**This method initializes the main screen when the screen is loaded.
     * @param location the location of the screen
     * @param resources the resources used for the main screen*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerList = CustomerDAOImpl.getAllCustomers();
        appointmentList = AppointmentDAOImpl.getAllAppointments();
        setCurrentZDT();

        setAppointmentAlert();
        customerTable.setItems(customerList);
        custCustID.setCellValueFactory(new PropertyValueFactory<>("id"));
        custName.setCellValueFactory(new PropertyValueFactory<>("name"));
        custAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        custPostCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        custDivi.setCellValueFactory(new PropertyValueFactory<>("division"));
        custCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        custPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        appointmentTable.setItems(appointmentList);
        setAppointmentTable();
    }
}
