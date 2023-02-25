package pl.edu.wszib.library.DAO;


import pl.edu.wszib.library.core.ConnectionProvider;
import pl.edu.wszib.library.models.Role;
import pl.edu.wszib.library.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDAO {
    private User sessionUser = null;
    private static final UserDAO instance = new UserDAO();
    private static final ConnectionProvider conn = ConnectionProvider.getInstance();
    private static final Connection connection = conn.connect();

    private UserDAO() {
    }

    public void saveUser(User user) {
        try {
            String sql = "INSERT INTO users (name, surname, login, password, role) VALUES (?,?,?,?,?)";

            PreparedStatement ps = connection.prepareStatement(sql);
            System.out.println(ps);
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

    public User getUserByLogin(String login) {
        try {
            String sql = "SELECT * FROM users WHERE login = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, login);


            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                 this.sessionUser = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("login"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role")));
                return this.sessionUser;
            }
        } catch (SQLException e) {
            System.out.println("Fatal error");
        }
        return null;
    }

    public String checkRoleToAdmin(String login) {
        if (this.getUserByLogin(login) != null) {
            if (this.getUserByLogin(login).getRole() == Role.ADMIN) {
                return "1";
            }
            updateUser(getUserByLogin(login));
            return "0";
        }
        return "2";
    }

    public List<User> getAllUsers(){
        List<User> result = new ArrayList<>();
        try {
            String sql = "SELECT id, name, surname, login, password FROM users";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                result.add(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("login"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role"))));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean searchUserByNameSurname(String name, String surname) {
        try {
            String sql = "SELECT * FROM users WHERE name = ? AND surname = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, surname);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Fatal error");
        }
        return false;
    }

    public void updateUser(User user) {
        try {
            String sql = "UPDATE users SET role = ? WHERE login = ?";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, Role.ADMIN.toString());
            ps.setString(2, user.getLogin());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSessionUser(User sessionUser) {
        this.sessionUser = sessionUser;
    }

    public User getSessionUser() {
        return sessionUser;
    }

    public static UserDAO getInstance() {
        return instance;
    }


}

