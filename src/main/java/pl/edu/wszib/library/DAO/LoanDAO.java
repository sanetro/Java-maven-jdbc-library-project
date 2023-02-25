package pl.edu.wszib.library.DAO;

import pl.edu.wszib.library.core.ConnectionProvider;
import pl.edu.wszib.library.models.Book;
import pl.edu.wszib.library.models.Loan;
import pl.edu.wszib.library.models.LoanExtended;
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

    public ArrayList<LoanExtended> getLoansWithUserInformation() {
        ArrayList<LoanExtended> result = new ArrayList<>();
        try {
            String sql = "SELECT b.isbn, b.title, u.name, u.surname, u.id, l.orderdate, l.deadlinedate, l.returndate FROM loans as l INNER JOIN books as b ON l.bookid = b.isbn INNER JOIN users as u ON l.userid = u.id;";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                result.add(new LoanExtended(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        null,
                        null,
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
    public ArrayList<LoanExtended> getLoansWithUserInformationOverTime() {
        ArrayList<LoanExtended> result = new ArrayList<>();
        try {
            String sql = "SELECT b.isbn, b.title, u.name, u.surname, u.id, l.orderdate, l.deadlinedate, l.returndate FROM loans as l INNER JOIN books as b ON l.bookid = b.isbn INNER JOIN users as u ON l.userid = u.id HAVING l.deadlinedate < NOW() AND l.returndate IS NULL";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                result.add(new LoanExtended(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        null,
                        null,
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

    public ArrayList<LoanExtended> getLoansWithUserInformationByISBN(String isbn) {
        ArrayList<LoanExtended> result = new ArrayList<>();
        try {
            String sql = "SELECT b.isbn, b.title, b.author, b.date, u.name, u.surname, u.id, l.orderdate, l.deadlinedate, l.returndate " +
                    "FROM loans as l INNER JOIN books as b ON l.bookid = b.isbn INNER JOIN users as u ON l.userid = u.id " +
                    "HAVING b.isbn LIKE CONCAT( '%',?,'%')";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            System.out.println(ps);
            while(rs.next()) {
                result.add(new LoanExtended(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDate("date"),
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
