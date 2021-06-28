package view_controller;
/**
 *
 * @author Jarrod Crockett
 */
import dbAccess.AppointmentDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.DateTime;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**CustomerReport class. The CustomerReport class is used to create a controller for the CustomerReportScreen.
 * The controller contains methods for the functionality of the screen.*/
public class CustomerReport implements Initializable {

    Parent scene;
    Stage stage;

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
    private Button close;

    @FXML
    private ComboBox<String> monthCB;

    @FXML
    private ComboBox<String> typeCB;

    ObservableList<String> months = FXCollections.observableArrayList();
    ObservableList<String> types = FXCollections.observableArrayList();
    ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    /**This method cancels generating a report and returns to the main screen of the application.
     * @param event the close button being clicked.*/
    @FXML
    public void close(ActionEvent event) {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(getClass().getResource("../View_Controller/MainScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method listens for the month selected combo box to be selected and sets the table based on the month selected.*/
    @FXML
    public void monthSelection(ActionEvent event) {

        if (typeCB.getSelectionModel().isEmpty() || typeCB.getSelectionModel().getSelectedItem().isEmpty()){
            updateMonthAppointments();
            appointmentTable.setItems(appointments);
            return;
        }else {
            typeSelect();
        }
    }

    /**This method updates the table based on the month that is selected in the month selected combo box.*/
    private void updateMonthAppointments(){
        String monthSelected = monthCB.getSelectionModel().getSelectedItem();
        int monthNum = -1;
        appointments.clear();
        if (monthSelected.isEmpty()){
            return;
        }
        if (monthSelected.equals("All")){
            appointments.clear();
            appointments.addAll(allAppointments);
        }
        if (monthSelected.equals("January")){
            monthNum = 1;
        }
        else if (monthSelected.equals("February")){
            monthNum = 2;
        }
        else if (monthSelected.equals("March")){
            monthNum = 3;
        }
        else if (monthSelected.equals("April")){
            monthNum = 4;
        }
        else if (monthSelected.equals("May")){
            monthNum = 5;
        }
        else if (monthSelected.equals("June")){
            monthNum = 6;
        }
        else if (monthSelected.equals("July")){
            monthNum = 7;
        }
        else if (monthSelected.equals("August")){
            monthNum = 8;
        }
        else if (monthSelected.equals("September")){
            monthNum = 9;
        }
        else if (monthSelected.equals("October")){
            monthNum = 10;
        }
        else if (monthSelected.equals("November")){
            monthNum = 11;
        }
        else if (monthSelected.equals("December")){
            monthNum = 12;
        }
        else {
            return;
        }

        for (Appointment app:allAppointments){
            if (app.getStartDateTime().getMonth().getValue() == monthNum){
                appointments.add(app);
            }
        }
    }

    /**This method is used to get the type of appointment selected and sets the table for that type selected.*/
    private void typeSelect() {
        if (monthCB.getSelectionModel().isEmpty() || monthCB.getSelectionModel().getSelectedItem().equals("All") || monthCB.getSelectionModel().getSelectedItem().isEmpty()) {
            appointments.clear();
            for (Appointment app : allAppointments) {
                if (app.getType().equals(typeCB.getSelectionModel().getSelectedItem())) {
                    appointments.add(app);
                }
            }
            appointmentTable.setItems(appointments);
            return;
        }
        updateMonthAppointments();
        String type = typeCB.getSelectionModel().getSelectedItem();
        appointments.removeIf(app -> !app.getType().equals(type));
        appointmentTable.setItems(appointments);
    }

    /**This method listens for the type selected combo box to be selected.
     * @param event the type selected combo box being selected.*/
    @FXML
    void typeSelection(ActionEvent event) {
        typeSelect();
    }

    /**This method sets all the months that can be selected in the months combo box on the start of the screen.*/
    private void setMonths(){
        months.add("All");
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
    }

    /**This method is used to initialize the scene at the start of a customer report view.
     * @param location the location of the screen
     * @param resources the resourceBundle used*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allAppointments = AppointmentDAOImpl.getAllAppointments();
        setMonths();
        types = AppointmentDAOImpl.getDistinctTypes();

        monthCB.setItems(months);
        typeCB.setItems(types);

        appointmentTable.setItems(allAppointments);

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
}
