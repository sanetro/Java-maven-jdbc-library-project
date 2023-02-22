package pl.edu.wszib.library.DAO;

import pl.edu.wszib.library.gui.GUI;
import pl.edu.wszib.library.models.Book;
import pl.edu.wszib.library.models.Loan;
import pl.edu.wszib.library.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {
    private static final LoanDAO instance = new LoanDAO();
    private ArrayList<Loan> loans;
    private static final ConnectionDAO conn = ConnectionDAO.getInstance();
    private static final Connection connection = conn.connect();
    private static final BookDAO bookDAO = BookDAO.getInstance();
    private static final UserDAO userDAO = UserDAO.getInstance();

    public LoanDAO () {

    }

    public boolean saveLoan(String name, String surname, String title) {
        String sql;
        try {

            sql = "INSERT INTO loans (userid, bookid, orderdate, deadlinedate) VALUES (?,?,?,?)";


            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, );
            ps.setString(2, );
            ps.setString(3, loan.getOrderDate().toString());
            ps.setString(4, loan.getDeadlineDate().toString());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static LoanDAO getInstance() {
        return instance;
    }


}
