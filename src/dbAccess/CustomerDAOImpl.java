package dbAccess;
/**
 *
 * @author Jarrod Crockett
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Customer;
import utilities.DBConnection;
import utilities.DBQuery;
import java.sql.*;

/**CustomerDAOImpl class. Accesses the customers in the database.*/
public class CustomerDAOImpl{

    /**This returns an Observable List of all the customers in the database.
     * @return Returns an Observable List of all customers*/
    public static ObservableList<Customer> getAllCustomers(){

    ObservableList<Customer> customersList = FXCollections.observableArrayList();
        try {
            String sql = "Select customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, " +
                    "first_level_divisions.Division, countries.Country, customers.Phone FROM customers " +
                    "inner join first_level_divisions on customers.Division_ID = first_level_divisions.Division_ID " +
                    "inner join countries on first_level_divisions.COUNTRY_ID = countries.Country_ID;";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int customerID = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String division = rs.getString("Division");
                String country = rs.getString("Country");
                String phone = rs.getString("Phone");

                Customer customer = new Customer(customerID, name, address, postalCode, division, country, phone);
                customersList.add(customer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return customersList;
    }

    /**This method inserts a new customer in the datebase and returns an integer for error checking.
     * @param name the name for the customer
     * @param address the address of the customer
     * @param postalCode the postal code of the customer
     * @param stateProvince this is the state or province the customer lives in.
     * @param phone this is the phone number of the customer
     * @return Returns an int for error checking logic*/
    public static int insertNewCustomer(String name,String address,String postalCode,String stateProvince, String phone) throws SQLException {

        // Checking if the name already exist in the database.

        ObservableList<Customer> customerList = CustomerDAOImpl.getAllCustomers();
        for (Customer customer:customerList) {
            if (customer.getName().equals(name))
                return -1;

        }

        Connection conn = DBConnection.getConnection();

        DBQuery.setStatement(conn);
        Statement statement = DBQuery.getStatement();

        int divisionID = FirstLevelDivisionsDAOImpl.getDivisionID(stateProvince);
        if (divisionID < 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Customer");
            alert.setHeaderText("Error");
            alert.setContentText("Your customer was unable to be saved into the database.");
            alert.showAndWait();
        }

        String sqlInsertCustomer = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID)" +
                " VALUES('" + name + "','" + address + "','" + postalCode + "','" + phone + "'," + divisionID + ");";

        statement.execute(sqlInsertCustomer);

        return 1;

    }

    /**This method updates a current customer in the database.
     * @param id the id of the customer to be updated
     * @param name the name of the customer
     * @param address the address of the customer
     * @param postalCode the postal code of the customer
     * @param stateProvince the state or province of the customer
     * @param phone the phone number of the customer*/
    public static void updateCustomer(int id, String name,String address,String postalCode,String stateProvince, String phone) throws SQLException {


        int divisionID = FirstLevelDivisionsDAOImpl.getDivisionID(stateProvince);

        String sqlUpdateCustomer = "UPDATE customers SET Customer_Name = '" + name + "', Address = '" +
                address + "', Postal_Code = '" + postalCode + "', Phone = '" + phone + "', Division_ID = " + divisionID + " WHERE customers.Customer_ID = " + id + ";";

        Connection conn = DBConnection.getConnection();

        DBQuery.setStatement(conn);
        Statement statement = DBQuery.getStatement();

        statement.execute(sqlUpdateCustomer);

    }

    /**This method deletes a customer from the database.
     * @param customerID the customers ID to delete from the database*/
    public static void deleteCustomer(int customerID) throws SQLException {
        String sqlDelete = "DELETE FROM customers WHERE Customer_ID = " + customerID + ";";

        Connection conn = DBConnection.getConnection();

        DBQuery.setStatement(conn);
        Statement statement = DBQuery.getStatement();

        statement.execute(sqlDelete);

    }


}
