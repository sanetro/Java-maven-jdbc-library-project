package pl.edu.wszib.library.core;

import java.sql.*;
import java.sql.Connection;

public class ConnectionProvider {
    private String url = "jdbc:mysql://localhost:3306/librarydb";

    private String user = "root";
    private String password = "";
    public static Connection conn;
    private static final ConnectionProvider instance = new ConnectionProvider();

    public ConnectionProvider() {
    }

    public Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    public static void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public void setPassword(String password) {
        this.password = password;
    }public void setUser(String user) {
        this.user = user;
    }

    public static ConnectionProvider getInstance() { return instance; }


}
