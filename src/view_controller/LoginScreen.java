package view_controller;
/**
 *
 * @author Jarrod Crockett
 */
import dbAccess.AppointmentDAOImpl;
import dbAccess.UserDAOImpl;
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
import javafx.stage.Stage;
import model.Appointment;
import model.User;
import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

/**LoginScreen class. This class is used to create a controller for the login screen of the application.
 * It has methods to check login credentials and to show current appointments on login.*/
public class LoginScreen implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField userNameTB;

    @FXML
    private TextField passwordTB;

    @FXML
    private Button loginBtn;

    @FXML
    private Button closeBtn;

    @FXML
    private Label timeZoneLabel;

    @FXML
    private Label timeZone;

    @FXML
    private Label headerLabel;

    @FXML
    private Label loginLabel;

    @FXML
    private Label userLabel;

    @FXML
    private Label passwordLabel;

    String alertHeader = "Login Screen";
    String confirmation = "Confirmation";
    String appointmentAlert = "Appointment Alert";
    String loginSuccess = "Login Successful";
    String noAppointments = "There are no appointment scheduled in the next 15 minutes.";
    String error = "Error";
    String error1 = "Please enter a user name and password.";
    String error2 = "The user name or password entered was incorrect.";
    String appointmentReminder = "has an appointment scheduled within the next 15 minutes.";
    String exit = "Do you want to exit?";

    ObservableList<User> users = FXCollections.observableArrayList();

    /**This method listens for the close button to be selected. It closes the application when the button is pressed.
     * @param event the close button being pressed.*/
    @FXML
    public void closeProg(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(alertHeader);
        alert.setContentText(confirmation);
        alert.setContentText(exit);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.CANCEL)
            return;

        Platform.exit();
    }

    /**This method listens to the login button being selected. When the button is pressed in checks the user name and password to allow login.
     * @param event the login button being pressed*/
    @FXML
    public void login(ActionEvent event) {
        if (userNameTB.getText().isEmpty() || passwordTB.getText().isEmpty()){
            loginScreenError(1);
            clearTBs();
            return;
        }
        if (UserDAOImpl.checkUserAccess(userNameTB.getText(), passwordTB.getText()) == -1){
            loginScreenError(2);
            logActivity(-1);
            clearTBs();
            return;
        }
        ObservableList<Appointment> appointments = AppointmentDAOImpl.getAllAppointments();
        String currentAppointments = "";
        for (Appointment app: appointments){
            if (app.getStartDateTime().isAfter(LocalDateTime.now()) && app.getStartDateTime().isBefore(LocalDateTime.now().plusMinutes(15))){
                currentAppointments = currentAppointments + " " + app.getId() + " " + app.getStartDateTime() + "\n";
            }
        }
        if (!currentAppointments.matches("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(alertHeader);
            alert.setHeaderText(appointmentAlert);
            alert.setContentText(currentAppointments + appointmentReminder);
            alert.showAndWait();
        }else{
            loginScreenSuccess();
        }

        logActivity(1);
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(getClass().getResource("../View_Controller/MainScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method creates a file for login attempts made by users. It saves the user name, date and time of login attempt.
     * @param code used to write a successful attempt or unsuccessful attempt to the log file.*/
    private void logActivity(int code){
        if (code == 1){
            try {
                String successful = "\n Login by user: " + userNameTB.getText() + " was successful on " + LocalDateTime.now() + ".";
                BufferedWriter writer = new BufferedWriter(new FileWriter("login_activity.txt", true));
                writer.write(successful);

                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                String successful = "\n Login by user: " + userNameTB.getText() + " was unsuccessful on " + LocalDateTime.now() + ".";
                BufferedWriter writer = new BufferedWriter(new FileWriter("login_activity.txt", true));
                writer.write(successful);

                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**This method creates a login screen error alert,
     * @param errorCode the integer used to choose which error to display*/
    private void loginScreenError(int errorCode){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(alertHeader);
        alert.setHeaderText(error);
        if (errorCode == 1)
            alert.setContentText(error1);
        if (errorCode == 2)
            alert.setContentText(error2);
        alert.showAndWait();
    }

    /**This method displays a message for login successful on login.*/
    private void loginScreenSuccess(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(alertHeader);
        alert.setHeaderText(loginSuccess);
        alert.setContentText(noAppointments);
        alert.show();
    }

    /**This method clears the text boxes with an unsuccessful login attempt is made.*/
    private void clearTBs(){
        userNameTB.clear();
        passwordTB.clear();
    }

    /**This method initializes the start of the login screen when the screen is loaded.
     * @param url the location of the screen
     * @param resourceBundle the resourceBundle used to change the language based on the users zone ID*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId zid = ZoneId.systemDefault();
        this.timeZoneLabel.setText(zid.toString());

        this.users = UserDAOImpl.userList();

        try {
            if (Locale.getDefault().getLanguage().equals("fr")) {
                resourceBundle = ResourceBundle.getBundle("utilities/login",Locale.getDefault());
                headerLabel.setText(resourceBundle.getString("title"));
                loginLabel.setText(resourceBundle.getString("login"));
                userLabel.setText(resourceBundle.getString("user"));
                passwordLabel.setText(resourceBundle.getString("password"));
                userNameTB.setPromptText(resourceBundle.getString("userName"));
                passwordTB.setPromptText(resourceBundle.getString("password"));
                loginBtn.setText(resourceBundle.getString("login"));
                closeBtn.setText(resourceBundle.getString("close"));
                timeZone.setText(resourceBundle.getString("timeZone"));
                alertHeader = resourceBundle.getString("alertHeader");
                confirmation = resourceBundle.getString("confirmation");
                appointmentAlert = resourceBundle.getString("appointmentAlert");
                loginSuccess = resourceBundle.getString("loginSuccess");
                noAppointments = resourceBundle.getString("noAppointment");
                error = resourceBundle.getString("error");
                error1 = resourceBundle.getString("error1");
                error2 = resourceBundle.getString("error2");
                exit = resourceBundle.getString("exit");
                appointmentReminder = resourceBundle.getString("appointmentReminder");
            }

        }catch (MissingResourceException e){
            e.printStackTrace();
        }

    }
}

