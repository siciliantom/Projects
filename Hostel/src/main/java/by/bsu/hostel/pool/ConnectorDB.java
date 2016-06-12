package by.bsu.hostel.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Class for configurating db connection
 *
 *
 * @implements Connection
 * @author Kate
 * @version 1.0
 */
class ConnectorDB {
    static Connection getConnection() throws SQLException {
        ResourceBundle resource = ResourceBundle.getBundle("database");
        String url = resource.getString("db.url");
        String user = resource.getString("db.user");
        String pass = resource.getString("db.password");
        return DriverManager.getConnection(url, user, pass);
    }
}
