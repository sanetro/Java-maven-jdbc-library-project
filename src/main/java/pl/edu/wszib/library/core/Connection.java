package pl.edu.wszib.library.core;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Connection {
    public static java.sql.Connection connection;

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

   /*
   *
   *  public static void saveUser(User user) {
        try {
            String sql = "INSERT INTO users (name, surname, login, password) VALUES ('Mateusz', 'Mati', 'admin', 'admin');";
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }*/
}
