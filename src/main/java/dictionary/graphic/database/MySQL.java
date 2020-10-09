package dictionary.graphic.database;

import java.sql.*;

public class MySQL {
    String dbURL = "jdbc:mysql://localhost:3306/dictionary";
    String username = "root";
    String password = "nguyenthanhdomysql";

    public Connection connectToDB() throws SQLException {
        Connection conn = DriverManager.getConnection(dbURL, username, password);
        return conn;
    }
}
