package pl.edu.wszib.library.DAO;

import pl.edu.wszib.library.core.ConnectionProvider;
import pl.edu.wszib.library.models.Book;
import pl.edu.wszib.library.models.Loan;
import pl.edu.wszib.library.models.LoanView;
import pl.edu.wszib.library.models.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class LoanDAO {
    private static final LoanDAO instance = new LoanDAO();
    private static final ConnectionProvider conn = ConnectionProvider.getInstance();
    private static final Connection connection = conn.connect();



    public boolean saveLoan(User user, Book book) {
        try {
            String sql = "INSERT INTO loans (userid, bookid, orderdate, deadlinedate, returndate) VALUES (?,?,?,?,NULL)";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, user.getId());
            ps.setString(2, book.getIsbn());
            ps.setString(3, String.valueOf(LocalDate.now()));
            ps.setString(4, String.valueOf(LocalDate.now().plusDays(14)));

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean returnBook(User user, Loan loan) {
        try {

            String sql = "UPDATE loans SET returndate = ? WHERE userid = ? AND bookid = ?;";


            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(LocalDate.now()));
            ps.setInt(2, user.getId());
            ps.setString(3, loan.getBookId());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            return false;
        }

    }
    public ArrayList<Loan> getLoans() {
        ArrayList<Loan> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM loans";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                result.add(new Loan(
                        rs.getInt("id"),
                        rs.getInt("userid"),
                        rs.getString("bookid"),
                        rs.getDate("orderdate"),
                        rs.getDate("deadlinedate"),
                        rs.getDate("returndate")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean isAvailable(String title) {
        ArrayList<Loan> result = new ArrayList<>();
        try {
            String sql = "SELECT b.title, l.returndate FROM books as b LEFT JOIN loans as l ON b.isbn = l.bookid HAVING l.returndate IS NULL AND b.title = ? AND b.available = 0;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, title);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    public ArrayList<LoanView> getLoansWithUserInformation() {
        ArrayList<LoanView> result = new ArrayList<>();
        try {
            String sql = "SELECT b.isbn, b.title, b.author, b.available, u.name, u.surname, u.id, l.orderdate, l.deadlinedate, l.returndate FROM loans as l INNER JOIN books as b ON l.bookid = b.isbn INNER JOIN users as u ON l.userid = u.id;";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                result.add(new LoanView(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("available"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getInt("id"),
                        rs.getDate("orderdate"),
                        rs.getDate("deadlinedate"),
                        rs.getDate("returndate")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    public ArrayList<LoanView> getLoansWithUserInformationOverTime() {
        ArrayList<LoanView> result = new ArrayList<>();
        try {
            String sql = "SELECT b.isbn, b.title, b.author, b.available, u.name, u.surname, u.id, l.orderdate, l.deadlinedate, l.returndate FROM loans as l INNER JOIN books as b ON l.bookid = b.isbn INNER JOIN users as u ON l.userid = u.id HAVING l.deadlinedate < NOW() AND l.returndate IS NULL";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                result.add(new LoanView(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("available"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getInt("id"),
                        rs.getDate("orderdate"),
                        rs.getDate("deadlinedate"),
                        rs.getDate("returndate")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public ArrayList<LoanView> getLoansWithUserInformationByOption(String option, String value) {
        ArrayList<LoanView> result = new ArrayList<>();

        try {
            String sql = null;
            switch (option) {
                case "1" -> {
                    sql = "SELECT b.isbn, b.title, b.author, b.available, u.name, u.surname, u.id, l.orderdate, l.deadlinedate, l.returndate \n" +
                            "FROM books as b \n" +
                            "LEFT JOIN loans as l ON b.isbn = l.bookid \n" +
                            "LEFT JOIN users as u ON l.userid = u.id \n" +
                            "where l.returndate IS NULL AND (b.isbn LIKE CONCAT('%',?,'%') OR b.isbn LIKE CONCAT('%',?) OR b.isbn LIKE CONCAT(?,'%'))";
                }
                case "2" -> {
                    sql = "SELECT b.isbn, b.title, b.author, b.available, u.name, u.surname, u.id, l.orderdate, l.deadlinedate, l.returndate \n" +
                            "FROM books as b \n" +
                            "LEFT JOIN loans as l ON b.isbn = l.bookid \n" +
                            "LEFT JOIN users as u ON l.userid = u.id \n" +
                            "where l.returndate IS NULL AND (b.title LIKE CONCAT('%',?,'%') OR b.title LIKE CONCAT('%',?) OR b.title LIKE CONCAT(?,'%'))";

                }
                case "3" -> {
                    sql = "SELECT b.isbn, b.title, b.author, b.available, u.name, u.surname, u.id, l.orderdate, l.deadlinedate, l.returndate \n" +
                            "FROM books as b \n" +
                            "LEFT JOIN loans as l ON b.isbn = l.bookid \n" +
                            "LEFT JOIN users as u ON l.userid = u.id \n" +
                            "where l.returndate IS NULL AND (b.author LIKE CONCAT('%',?,'%') OR b.author LIKE CONCAT('%',?) OR b.author LIKE CONCAT(?,'%'))";
                }
            }
            

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, value);
            ps.setString(2, value);
            ps.setString(3, value);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                result.add(new LoanView(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("available"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getInt("id"),
                        rs.getDate("orderdate"),
                        rs.getDate("deadlinedate"),
                        rs.getDate("returndate")));
            }
        } catch (SQLException e) {
           throw new RuntimeException(e);
        }
        return result;
    }





    public static LoanDAO getInstance() {
        return instance;
    }



}
