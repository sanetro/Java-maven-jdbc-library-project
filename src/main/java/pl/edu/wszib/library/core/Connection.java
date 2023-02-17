package pl.edu.wszib.library.core;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import pl.edu.wszib.library.models.User;
public class Connection {
    public static java.sql.Connection connection;
    private static final Connection instance = new Connection();
    public Connection () {

    }

    public static void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/librarydb",
                    "root",
                    "");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveUser(User user) {
        try {
            String sql = "INSERT INTO users (name, surname, login, password, role) VALUES (?,?,?,?,?)";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setString(3, user.getLogin());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getRole().toString());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getInstance() { return instance; }
}
