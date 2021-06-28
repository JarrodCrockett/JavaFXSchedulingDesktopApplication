package test;

import dbAccess.UserDAOImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilities.DBConnection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOImplTest {


    ObservableList<User> users = FXCollections.observableArrayList();
    @BeforeEach
    void setUp() {
        DBConnection.startConnection();
        try {
            String sqlUserAth = "SELECT User_ID, User_Name, Password, Last_Update FROM users;";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sqlUserAth);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int dbUserID = rs.getInt("User_ID");
                String dbUsername = rs.getString("User_Name");
                String dbPassword = rs.getString("Password");
                LocalDateTime lastUpdate = (rs.getTimestamp("Last_Update")).toLocalDateTime();

                User user = new User(dbUserID, dbUsername, dbPassword, lastUpdate);
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        DBConnection.closeConnection();
    }

    @Test
    void checkUserAccess() {

        System.out.println("Testing database users.");
        System.out.println("Users are retrieved from the database using a for loop.");

        for (User databaseUser: users){
            System.out.println("Testing Username: \"" + databaseUser.getUserName() + "\" with edge cases.");
            assertEquals(1, UserDAOImpl.checkUserAccess(databaseUser.getUserName(), databaseUser.getUserPassword()));
            assertEquals(-1,UserDAOImpl.checkUserAccess(databaseUser.getUserName().toUpperCase(Locale.ROOT), databaseUser.getUserPassword()));
            assertEquals(-1,UserDAOImpl.checkUserAccess(databaseUser.getUserName(), databaseUser.getUserPassword().toUpperCase(Locale.ROOT)));
            System.out.println("Username: " + databaseUser.getUserName() + " complete." );
        }




    }
}