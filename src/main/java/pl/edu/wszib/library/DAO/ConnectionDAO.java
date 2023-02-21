package pl.edu.wszib.library.DAO;

import java.sql.*;
import java.sql.Connection;

public class ConnectionDAO {
    private static final String url = "jdbc:mysql://localhost:3306/librarydb";

    private static final String user = "root";
    private static final String password = "";
    public static Connection conn;
    private static final ConnectionDAO instance = new ConnectionDAO();

    public ConnectionDAO () {
    }

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Connection readyConnect() {
        connect();
        return conn;
    }

    public static void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public static ConnectionDAO getInstance() { return instance; }


}
