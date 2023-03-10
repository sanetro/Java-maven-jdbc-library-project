package pl.edu.wszib.library.DAO;

import pl.edu.wszib.library.core.ConnectionProvider;
import pl.edu.wszib.library.models.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private static final BookDAO instance = new BookDAO();
    private static final ConnectionProvider conn = ConnectionProvider.getInstance();
    private static final Connection connection = conn.connect();

    public void addBook(Book book) {
        try {
            String sql = "INSERT INTO books VALUES (?,?,?,1)";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, book.getIsbn());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getAuthor());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    public boolean searchBookByISBN(Book book) {
        try {
            String sql = "SELECT * FROM books WHERE isbn = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, book.getIsbn());

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Fatal error");
        }
        return false;
    }

    public boolean searchBookByTitle(Book book) {
        try {
            String sql = "SELECT * FROM books WHERE title = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, book.getTitle());

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Fatal error");
        }
        return false;
    }

    public boolean setBookAvailable(Book book) {
        try {
            String sql = "UPDATE books SET available = 1 WHERE available = 0 AND title = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, book.getTitle());
            ps.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean setBookNotavailable(Book book) {
        try {
            String sql = "UPDATE books SET available = 0 WHERE available = 1 AND title = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, book.getTitle());
            ps.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public List<Book> getAllBooks(){
        List<Book> result = new ArrayList<>();
        try {
            String sql = "SELECT isbn, title, author, available FROM books";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                result.add(new Book(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("available")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }



    public static BookDAO getInstance() {
        return instance;
    }

}