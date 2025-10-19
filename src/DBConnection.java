import java.sql.*;

public class DBConnection {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/oriented";
    public static final String DB_USER = "root";
    public static final String DB_PASS = "";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

}