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
import model.Customer;
import model.FirstLevelDivisions;
import utilities.AlertMessage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**ModifyCustomer Class. This class is used as a controller for the ModifyCustomerScreen.
 * It has the functionality and controls for the screen.
 * It has methods for the accessing the database and modifying a customer.*/
public class ModifyCustomer implements Initializable {

    private Stage stage;
    private Parent scene;
    private Customer customer;

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
    ObservableList<Countries> countriesList;

    /**This method cancels modifying a customer when the button is clicked and returns to the main screen.
     * This method contains a lambda alert confirming the user wants to return to the main screen.
     * This creates a method internally for pushing the OK button.
     * @param event The close button being clicked.*/
    @FXML
    public void cancelCust(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Add Customer");
        alert.setHeaderText("Confirmation");
        alert.setContentText("Do you want to cancel your update to the customer?");

        //Lambda expression used to receive confirmation from the user to leave the modify customer.
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
    public void changeStateProvince(ActionEvent event) {

    }

    /**This method listens to the save button being clicked and then submits the information from the fields to the database to update a customer.
     * @param event The save button being clicked.*/
    @FXML
    public void saveCust(ActionEvent event) throws SQLException {

        if (completionCheck() < 0)
            return;

        int id = Integer.parseInt(idTB.getText().trim());

        CustomerDAOImpl.updateCustomer(id , nameTB.getText().trim(), addressTB.getText().trim(), postCodeTB.getText().trim(), stateProvinceCB.getSelectionModel().getSelectedItem().toString(), phone.getText());

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

    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    /**This method is used to send data from the main screen to the modify customer screen based on the selected customer.
     * @param customer the customer being sent to be updated from the main screen*/
    public void sendData(Customer customer){

        this.countriesList = CountryDAOImpl.getAllCountries();


        this.idTB.setText(Integer.toString(customer.getId()));
        this.nameTB.setText(customer.getName());
        this.addressTB.setText(customer.getAddress());
        this.postCodeTB.setText(customer.getPostalCode());
        this.phone.setText(customer.getPhoneNumber());
        this.countryCB.setItems(this.countriesList);
        setCountrySelected(customer.getCountry(), customer.getDivision());
        setCustomer(customer);
    }

    /**This method sets the selected country when the screen is loaded.
     * @param countrySelected the country that was selected based of the customer selected on the main screen.
     * @param divisionSelected the division selected based on the customer selected from the main screen.*/
    private void setCountrySelected(String countrySelected, String divisionSelected) {
        for (Countries country : countriesList) {
            if (country.getCountry().equals(countrySelected)){
                this.countryCB.setValue(country);
                this.firstLevelDivisions = FirstLevelDivisionsDAOImpl.getAllStatesProvinces(country.getCountryID());
                stateProvinceCB.setItems(this.firstLevelDivisions);
                setSelectedStateProvince(country.getCountryID(), divisionSelected);
                return;
            }
        }
    }

    /**This method takes in the country ID and division selected and sets the combo box for the states or provinces selected based on the country selected.
     * @param countryID the country id selected
     * @param divisionSelected the division selected for as a string*/
    private void setSelectedStateProvince(int countryID, String divisionSelected) {
        for (FirstLevelDivisions division: firstLevelDivisions) {
            if (division.getCountryID() == countryID && division.getDivision().equals(divisionSelected)){
                stateProvinceCB.setValue(division);
            }

        }
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
        stateProvinceCB.setItems(this.firstLevelDivisions);
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
        if (countryCB.getSelectionModel().getSelectedItem() == null) {
            AlertMessage.addCustomerError(4);
            return -1;
        }
        if (stateProvinceCB.getSelectionModel().getSelectedItem() == null) {
            AlertMessage.addCustomerError(5);
            return -1;
        }
        if (phone.getText().trim().isEmpty()) {
            AlertMessage.addCustomerError(6);
            return -1;
        }
        return 1;
    }

    /**This method is used to initialize the scene at the start of modifying a customer.
     * @param location the location of the screen
     * @param resources the resourceBundle used*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
