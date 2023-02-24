package pl.edu.wszib.library.DAO;

import pl.edu.wszib.library.models.Book;
import pl.edu.wszib.library.models.Loan;
import pl.edu.wszib.library.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoanDAO {
    private static final LoanDAO instance = new LoanDAO();
    private ArrayList<Loan> loans;
    private static final ConnectionProvider conn = ConnectionProvider.getInstance();
    private static final Connection connection = conn.connect();
    private static final BookDAO bookDAO = BookDAO.getInstance();
    private static final UserDAO userDAO = UserDAO.getInstance();

    public LoanDAO () {

    }

    public boolean saveLoan(User user, Book book) {
        String sql;
        try {

            sql = "INSERT INTO loans (userid, bookid, orderdate, deadlinedate) VALUES (?,?,?,?)";


            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, user.getId());
            ps.setString(2, book.getIsbn());
            ps.setString(3, "2020-01-01");
            ps.setString(4, "2000-02-02");

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static LoanDAO getInstance() {
        return instance;
    }


}
