package pl.edu.wszib.library.DAO;

import pl.edu.wszib.library.models.Book;
import pl.edu.wszib.library.models.Loan;
import pl.edu.wszib.library.models.LoanExtended;
import pl.edu.wszib.library.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoanDAO {
    private static final LoanDAO instance = new LoanDAO();
    private static final ConnectionProvider conn = ConnectionProvider.getInstance();
    private static final Connection connection = conn.connect();



    public boolean saveLoan(User user, Book book) {
        try {
            String sql = "INSERT INTO loans (userid, bookid, orderdate, deadlinedate) VALUES (?,?,?,?)";

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

    public boolean deleteLoan(User user, Loan loanist) {
        try {

            String sql = "DELETE FROM loans WHERE userid = ? AND bookid = ?";


            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, user.getId());
            ps.setString(2, loanist.getBookId());

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
                        rs.getDate("deadlinedate")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public ArrayList<LoanExtended> getLoansWithUserInformation() {
        ArrayList<LoanExtended> result = new ArrayList<>();
        try {
            String sql = "SELECT b.isbn, b.title, u.name, u.surname, u.id, l.orderdate, l.deadlinedate FROM loans as l INNER JOIN books as b ON l.bookid = b.isbn INNER JOIN users as u ON l.userid = u.id";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                result.add(new LoanExtended(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getInt("id"),
                        rs.getDate("orderdate"),
                        rs.getDate("deadlinedate")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    public ArrayList<LoanExtended> getLoansWithUserInformationOverTime() {
        ArrayList<LoanExtended> result = new ArrayList<>();
        try {
            String sql = "SELECT b.isbn, b.title, u.name, u.surname, u.id, l.orderdate, l.deadlinedate FROM loans as l INNER JOIN books as b ON l.bookid = b.isbn INNER JOIN users as u ON l.userid = u.id HAVING l.deadlinedate < NOW();";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                result.add(new LoanExtended(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getInt("id"),
                        rs.getDate("orderdate"),
                        rs.getDate("deadlinedate")));
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
