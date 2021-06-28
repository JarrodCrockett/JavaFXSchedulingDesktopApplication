package view_controller;
/**
 *
 * @author Jarrod Crockett
 */
import dbAccess.CountryDAOImpl;
import dbAccess.CustomerDAOImpl;
import dbAccess.FirstLevelDivisionsDAOImpl;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Countries;
import model.FirstLevelDivisions;
import utilities.AlertMessage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**AddCustomer Class. This class is used as a controller for the AddCustomerScreen.
 * It has the functionality and controls for the screen.
 * It has methods for the accessing the database and adding a new customer.*/
public class AddCustomer implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Button save;

    @FXML
    private Button cancel;

    @FXML
    private TextField idTB;

    @FXML
    private TextField nameTB;

    @FXML
    private TextField addressTB;

    @FXML
    private TextField postCodeTB;

    @FXML
    private TextField phone;

    @FXML
    private ComboBox<FirstLevelDivisions> stateProvinceCB;

    @FXML
    private ComboBox<Countries> countryCB;

    ObservableList<FirstLevelDivisions> firstLevelDivisions;

    /**This method cancels adding a new customer when the button is clicked and returns to the main screen.
     * This method contains a lambda alert confirming the user wants to return to the main screen.
     * This creates a method internally for pushing the OK button.
     * @param event The close button being clicked.*/
    @FXML
    public void cancelCust(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Add Customer");
        alert.setHeaderText("Confirmation");
        alert.setContentText("Do you want to cancel a new customer?");

        //Lambda expression used to receive confirmation from the user to leave the add customer.
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

    /**This method listens for what country is selected in the countries combo box.
     * This allows the states or provinces combo box to be filled when this selection is made.
     * @param event The country combo box being selected.*/
    @FXML
    public void changeCountry(ActionEvent event) {
        Countries countries = countryCB.getSelectionModel().getSelectedItem();

        showStatesProvinces(countries.getCountryID());
    }

    /**This method listens for the change state or province combo box being selected.
     *
     * @param event the stateProvince combo box being selected
     */
    @FXML
    void changeStateProvince(ActionEvent event) {

    }

    /**This method listens to the save button being clicked and then submits the information from the fields to the database for a new customer.
     * @param event The save button being clicked.*/
    @FXML
    public void saveCust(ActionEvent event) throws SQLException {
        if (completionCheck() < 0)
            return;

        if (CustomerDAOImpl.insertNewCustomer(nameTB.getText().trim(), addressTB.getText().trim(), postCodeTB.getText().trim(), stateProvinceCB.getSelectionModel().getSelectedItem().toString(), phone.getText()) < 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Customer");
            alert.setHeaderText("Error");
            alert.setContentText("This custom already exist in the database. Please select and modify the current customer.");
            alert.showAndWait();
            return;
        }

        AlertMessage.addCustomerInfo();

        stage = (Stage)((Button) event.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(getClass().getResource("../View_Controller/MainScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**This method is used to insure all the fields for a new customer are completed before sending the information to the database.
     * @return Returns an integer that is used for error checking.*/
    private int completionCheck() {
        if (nameTB.getText().trim().isEmpty()) {
            AlertMessage.addCustomerError(1);
            return -1;
        }
        if (addressTB.getText().trim().isEmpty()) {
            AlertMessage.addCustomerError(2);
            return -1;
        }
        if (postCodeTB.getText().trim().isEmpty()){
            AlertMessage.addCustomerError(3);
            return -1;
         }
        if (countryCB.getSelectionModel().isEmpty()) {
            AlertMessage.addCustomerError(4);
            return -1;
        }
        if (stateProvinceCB.getSelectionModel().isEmpty()) {
            AlertMessage.addCustomerError(5);
            return -1;
        }
        if (phone.getText().trim().isEmpty()) {
            AlertMessage.addCustomerError(6);
            return -1;
        }
        return 1;
    }

    /**This method is used to show the starts or provinces in the combo box for the appropriate country that is selected.
     * @param countryID Uses the country ID to display the appropriate states or provinces.*/
    private void showStatesProvinces(int countryID){
        this.firstLevelDivisions = FirstLevelDivisionsDAOImpl.getAllStatesProvinces(countryID);
        if (countryID == 1){
            stateProvinceCB.setPromptText("Select A State");
        }else{
            stateProvinceCB.setPromptText("Select A Province");
        }
        stateProvinceCB.setItems(firstLevelDivisions);
    }

    /**This method is used to initialize the scene at the start of adding a new customer.
     * @param location the location of the screen
     * @param resources the resourceBundle used*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Countries> countries = CountryDAOImpl.getAllCountries();
        countryCB.setItems(countries);
    }
}