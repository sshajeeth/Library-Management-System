package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB_connect {
    public static Connection connection;

    public static Connection getConnection() {
        String database_name = "library";
        String user_name = "root";
        String password = "";
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + database_name + "?useSSL=false", user_name, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
