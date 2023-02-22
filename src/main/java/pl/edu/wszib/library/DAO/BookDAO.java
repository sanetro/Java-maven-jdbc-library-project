package pl.edu.wszib.library.DAO;

import pl.edu.wszib.library.gui.GUI;
import pl.edu.wszib.library.models.Book;
import pl.edu.wszib.library.models.Role;
import pl.edu.wszib.library.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private static final BookDAO instance = new BookDAO();
    private static final ConnectionDAO conn = ConnectionDAO.getInstance();
    private static final Connection connection = conn.connect();

    public void addBook(Book book) {
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




    public boolean searchExistsBook(Book book) {
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

    public boolean searchBookByTitle(String title) {
        try {
            String sql = "SELECT * FROM books WHERE title = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, title);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Fatal error");
        }
        return false;
    }
    public List<Book> getAllBooks(){
        List<Book> result = new ArrayList<>();
        try {
            String sql = "SELECT isbn, title, author, date FROM books";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                result.add(new Book(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("date")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void arrayLayout(List<Book> books) {
        try {
            books.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("List is empty");
        }
    }

    public static BookDAO getInstance() {
        return instance;
    }

}