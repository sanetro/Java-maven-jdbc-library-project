package pl.edu.wszib.library.DAO;

import pl.edu.wszib.library.models.Book;
import pl.edu.wszib.library.models.Role;
import pl.edu.wszib.library.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDAO {
    private static final BookDAO instance = new BookDAO();
    private static final ConnectionDAO conn = ConnectionDAO.getInstance();
    private static Connection connection = conn.connect();

    public static void addBook(Book book) {
        try {
            String sql = "INSERT INTO books VALUES (?,?,?,?)";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, book.getIsbn());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getAuthor());
            ps.setString(4, book.getDate());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static BookDAO getInstance() {
        return instance;
    }

    public static Book searchBook(Book book) {
        try {
            String sql = "SELECT * FROM books WHERE isbn = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, book.getIsbn());

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return new Book(rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("date"));
            }
        } catch (SQLException e) {
            System.out.println("Fatal error");
        }
        return null;
    }
}