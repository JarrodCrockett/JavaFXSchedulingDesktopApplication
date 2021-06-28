package main;
/**
 *
 * @author Jarrod Crockett
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.DBConnection;

/**Main class. This is the main class for the whole application.
 * This starts the login stage and accesses the database.*/
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("../view_controller/LoginScreen.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        primaryStage.setTitle("Appointment Scheduler HHC");
        primaryStage.setScene(scene);

        primaryStage.setResizable(false);
        primaryStage.show();

    }

    /**This method accesses the database and launches the JavaFX application screens.
     * @param args the arguments for the main method*/
    public static void main(String[] args) {

        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();

    }
}
