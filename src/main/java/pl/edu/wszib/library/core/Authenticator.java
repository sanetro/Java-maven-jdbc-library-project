package pl.edu.wszib.library.core;

import org.apache.commons.codec.digest.DigestUtils;
import pl.edu.wszib.library.DAO.BookDAO;
import pl.edu.wszib.library.DAO.LoanDAO;
import pl.edu.wszib.library.DAO.UserDAO;
import pl.edu.wszib.library.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Authenticator {

    final UserDAO userDAO = UserDAO.getInstance();
    final BookDAO bookDAO = BookDAO.getInstance();
    final LoanDAO loanDAO = LoanDAO.getInstance();
    private User loggedUser = null;
    private final String seed = "razdwa!3nie3trzyTYLKO2two!";
    private static final Authenticator instance = new Authenticator();

    private Authenticator() {

    }
    public void authenticate(User user) {
        User userFromDB = this.userDAO.getUserByLogin(user.getLogin());
        if(userFromDB != null &&
                userFromDB.getPassword().equals(
                        DigestUtils.md5Hex(user.getPassword() + this.seed))) {
            this.loggedUser = userFromDB;
        }
    }
    public boolean register(User user) {
        if(user.getPassword() == null || user.getLogin() == null ||
                user.getName() == null || user.getSurname() == null ||
                user.getPassword().equals("") || user.getLogin().equals("") ||
                user.getName().equals("") || user.getSurname().equals("")) {
            return false;
        }

        if(this.userDAO.getUserByLogin(user.getLogin()) == null) {
            user.setPassword(DigestUtils.md5Hex(user.getPassword() + this.seed));
            //System.out.println(DigestUtils.md5Hex(user.getPassword() + this.seed)); // handicap
            user.setRole(Role.USER);
            userDAO.saveUser(user);
            return true;
        }
        else return false;
    }

    public String addBookAgent(Book book) {
        String regexISBN = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}" +
                "$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$";

        Matcher isbnValidate = Pattern.compile(regexISBN).matcher(book.getIsbn());

        if (!isbnValidate.matches()) return "Values aren't in specific format or are blank.";
        if (this.bookDAO.searchBookByISBN(book)) return "ISBN duplication";
        if (this.bookDAO.searchBookByTitle(book)) return "Title duplicated";

        this.bookDAO.addBook(book);
        return "Book added Successful.";
    }

    public List<Book> showBookList() {
        return this.bookDAO.getAllBooks();
    }

    public ArrayList<LoanView> showBookListAndUser() {
        return this.loanDAO.getLoansWithUserInformation();
    }

    public ArrayList<LoanView> showBookListAndUserOverTime() {
        return this.loanDAO.getLoansWithUserInformationOverTime();
    }


    public String orderBookValidator(String name, String surname, String title, String action) {

        if (name.equals("") || surname.equals("") || title.equals(""))
            return "Values aren't in specific format or are blank";


        if (!this.userDAO.getSessionUser().getName().equals(name) &&
                !this.userDAO.getSessionUser().getSurname().equals(surname))
                    return "Authentication failed.";


        Book givenBook = this.bookDAO.getAllBooks().stream()
                .filter(book -> book.getTitle().equals(title))
                .findFirst()
                .orElse(null);

        if (givenBook == null)
            return "Book not found";





        if (Objects.equals(action, "addLoan")) {
            if (givenBook.getAvailable() == 0)
                return "Book isn't available";

            if (this.loanDAO.saveLoan(this.userDAO.getSessionUser(), givenBook)) {
                this.bookDAO.setBookNotavailable(givenBook);
                return "Loan save successful.";
            }
        }



        if (Objects.equals(action, "deleteLoan")) {

            Loan loanist = this.loanDAO.getLoans().stream()
                    .filter(loan -> loan.getBookId().equals(givenBook.getIsbnFromTitle(title)) &&
                            !this.loanDAO.isAvailable(title) &&
                            loan.getUserid() == this.userDAO.getSessionUser().getId())
                    .findFirst()
                    .orElse(null);

            if (loanist == null) {
                return "Insert book which you ordered";
            }

            if (this.loanDAO.returnBook(this.userDAO.getSessionUser(), loanist)) {
                this.bookDAO.setBookAvailable(givenBook);
                return "Returned book successful.";
            }
        }

        if (givenBook.getAvailable() == 0)
            return "Book isn't available";

        return "Returning book failed.";
    }

    public String UserToAdmin(String login) {
        return switch (userDAO.checkRoleToAdmin(login)) {
            case "0" -> "Admin role deploy";
            case "1" -> "User already has admin privileges";
            case "2" -> "Couldn't find user";
            default -> "Undefined error";
        };
    }

    public static Authenticator getInstance() {
        return instance;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void unmountLoggedUser() { this.loggedUser = null; }

    public String isBlank(String text) {
        return text.equals("") ? null : text;
    }
}
