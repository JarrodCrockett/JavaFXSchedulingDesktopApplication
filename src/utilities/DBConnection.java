package utilities;
/**
 *
 * @author Jarrod Crockett
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**DBConnection class. This class is used to access a connection to the database to allow sending and receiving data.*/
public class DBConnection {
    // JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ07td3";

    // JDBC URL
    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;

    // Driver Interface reference and database connection
    private static final String mySQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static  Connection conn = null;

    // Username for the database
    private static final String userName = "U07td3";

    // Password
    private static final String password = "53689128003";

    /**This method starts a connection with the database. It is called at the start of the application.*/
    public static Connection startConnection(){
        try {
            Class.forName(mySQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcURL,userName, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connection established.");
        return conn;
    }

    /**This method gets the connection that was established at the start of the application.
     * @return Returns the Connection.*/
    public static Connection getConnection(){
        return conn;
    }

    /**This closes the database connection and is called at the end of the application.*/
    public static void closeConnection(){
        try {
            conn.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
