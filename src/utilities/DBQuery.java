package utilities;
/**
 *
 * @author Jarrod Crockett
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**DBQuery class. This class is used to build queries to be sent to the database.*/
public class DBQuery {

    // Statement reference
    private static Statement statement;

    /**This method is used to create a statement object.
     * @param conn Takes in the connection*/
    public static void setStatement(Connection conn) throws SQLException {
        statement = conn.createStatement();
    }

    /**This method returns the statement for the query that was created.*/
    public static Statement getStatement() {
        return statement;
    }
}
