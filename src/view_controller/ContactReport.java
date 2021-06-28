package view_controller;
/**
 *
 * @author Jarrod Crockett
 */
import dbAccess.AppointmentDAOImpl;
import dbAccess.ContactsDAOImpl;
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
import model.Contacts;
import model.DateTime;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**ContactReport class. The ContactReport class is used to create a controller for the ContactReportScreen.
 * The controller contains methods for the functionality of the screen.*/
public class ContactReport implements Initializable {

    Parent scene;
    Stage stage;

    @FXML
    private ComboBox<Contacts> contactCB;

    @FXML
    private TableView<Appointment> contactTable;

    @FXML
    private TableColumn<Appointment, Integer> id;

    @FXML
    private TableColumn<Appointment, String> title;

    @FXML
    private TableColumn<Appointment, String> type;

    @FXML
    private TableColumn<Appointment, String> description;

    @FXML
    private TableColumn<Appointment, DateTime> startDate;

    @FXML
    private TableColumn<Appointment, DateTime> endDate;

    @FXML
    private TableColumn<Appointment, Integer> customerID;

    @FXML
    private Button closeBtn;

    ObservableList<Contacts> contacts = FXCollections.observableArrayList();
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

    /**This method listens for the contact combo box to be selected and sets the table with appointments for that contact.
     * @param event the contact combo box being selected*/
    @FXML
    public void selectContact(ActionEvent event) {
        Contacts contact = contactCB.getSelectionModel().getSelectedItem();
        appointments.clear();
        for (Appointment app: allAppointments){
            if (app.getContactName().equals(contact.getContactName())){
                appointments.add(app);
            }
        }
        if (appointments.isEmpty()){
            return;
        }

        setTable();
    }

    /**This method is used to set the table of appointments per contact selected.*/
    private void setTable(){
        this.contactTable.setItems(appointments);
    }

    /**This method is used to initialize the scene at the start of a contact report view.
     * @param location the location of the screen
     * @param resources the resourceBundle used*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        contacts = ContactsDAOImpl.getAllContacts();
        contactCB.setPromptText("Select A Contact");
        contactCB.setItems(contacts);
        allAppointments = AppointmentDAOImpl.getAllAppointments();

        contactTable.setItems(allAppointments);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));

    }
}

