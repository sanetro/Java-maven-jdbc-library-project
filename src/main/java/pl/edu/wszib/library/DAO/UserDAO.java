package pl.edu.wszib.library.DAO;


import pl.edu.wszib.library.models.Role;
import pl.edu.wszib.library.models.User;
import java.sql.*;
import java.util.ArrayList;




public class UserDAO {
    private ArrayList<User> users =  new ArrayList<>();
    private static final UserDAO instance = new UserDAO();
    private static final ConnectionDAO conn = ConnectionDAO.getInstance();
    private static Connection connection = conn.connect();

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
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("login"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role")));
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

    public static void updateUser(User user) {
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

    public static UserDAO getInstance() {
        return instance;
    }


}

