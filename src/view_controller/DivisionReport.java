package view_controller;
/**
 *
 * @author Jarrod Crockett
 */
import dbAccess.CountryDAOImpl;
import dbAccess.CustomerDAOImpl;
import dbAccess.FirstLevelDivisionsDAOImpl;
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
import model.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**DivisionReport class. The DivisionReport class is used to create a controller for the DivisionReportScreen.
 * The controller contains methods for the functionality of the screen.*/
public class DivisionReport implements Initializable{

    Parent scene;
    Stage stage;

    @FXML
    private ComboBox<Countries> countryCB;

    @FXML
    private ComboBox<FirstLevelDivisions> stateProvinceCB;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, Integer> id;

    @FXML
    private TableColumn<Customer, String> name;

    @FXML
    private TableColumn<Customer, String> address;

    @FXML
    private TableColumn<Customer, String> postalCode;

    @FXML
    private TableColumn<Customer, Integer> division;

    @FXML
    private TableColumn<Customer, String> country;

    @FXML
    private TableColumn<Customer, String> phone;

    @FXML
    private Button closeBtn;

    ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    ObservableList<Customer> customer = FXCollections.observableArrayList();
    ObservableList<FirstLevelDivisions> firstLevelDivisions;

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

    /**This method listens for the country combo box to be selected and then adds the correct states or provinces to their combo box.
     * @param event the country combo box being selected.*/
    @FXML
    public void selectCountry(ActionEvent event) {
        Countries countries = countryCB.getSelectionModel().getSelectedItem();

        showStatesProvinces(countries.getCountryID());

        customer.clear();
        for (Customer cust: allCustomers){
            if (cust.getCountry().equals(countries.getCountry())){
                customer.add(cust);
            }
        }

        customerTable.setItems(customer);

    }

    /**This method generates a table of customers based on the selected state or province and displays the customers in the location on the table.
     * @param countryID the country id for the customers in that location to be displayed on the table.*/
    private void showStatesProvinces(int countryID){
        this.firstLevelDivisions = FirstLevelDivisionsDAOImpl.getAllStatesProvinces(countryID);

        if (countryID == 1){
            stateProvinceCB.setPromptText("Select A State");
        }else{
            stateProvinceCB.setPromptText("Select A Province");
        }
        stateProvinceCB.setItems(firstLevelDivisions);
    }

    /**This method listens for the state or province combo box to be selected.
     * @param event the state or province combo box being selected.*/
    @FXML
    public void selectStateProvince(ActionEvent event) {
        FirstLevelDivisions selected = stateProvinceCB.getSelectionModel().getSelectedItem();


        if (selected == null){
            return;
        }
        customer.clear();
        for (Customer cust: allCustomers){
            if (cust.getDivision().equals(selected.getDivision())){
                customer.add(cust);
            }
        }
    }

    /**This method is used to initialize the scene at the start of a division report view.
     * @param location the location of the screen
     * @param resources the resourceBundle used*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        allCustomers = CustomerDAOImpl.getAllCustomers();

        customerTable.setItems(allCustomers);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        division.setCellValueFactory(new PropertyValueFactory<>("division"));
        country.setCellValueFactory(new PropertyValueFactory<>("country"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        customerTable.setItems(allCustomers);

        ObservableList<Countries> countries = CountryDAOImpl.getAllCountries();
        countryCB.setItems(countries);
    }
}
