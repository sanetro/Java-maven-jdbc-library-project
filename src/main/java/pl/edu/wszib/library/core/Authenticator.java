package pl.edu.wszib.library.core;

import org.apache.commons.codec.digest.DigestUtils;
import pl.edu.wszib.library.DAO.BookDAO;
import pl.edu.wszib.library.DAO.LoanDAO;
import pl.edu.wszib.library.DAO.UserDAO;
import pl.edu.wszib.library.database.ProductsDB;
import pl.edu.wszib.library.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Authenticator {

    final UserDAO userDAO = UserDAO.getInstance();
    final BookDAO bookDAO = BookDAO.getInstance();
    final LoanDAO loanDAO = LoanDAO.getInstance();
    final ProductsDB productsDB = ProductsDB.getInstance();
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
        // No blank labels
        if(user.getPassword() == null || user.getLogin() == null ||
                user.getName() == null || user.getSurname() == null ||
                user.getPassword().equals("") || user.getLogin().equals("") ||
                user.getName().equals("") || user.getSurname().equals("")) {
            System.out.println(user.getPassword());
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
        /*
        ISBN 978-0-596-52068-7
        ISBN-13: 978-0-596-52068-7
        978 0 596 52068 7
        9780596520687
        0-596-52068-9
        0 512 52068 9
        ISBN-10 0-596-52068-9
        ISBN-10: 0-596-52068-9
        */
        String regexISBN = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}" +
                "$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$";
        String regexDate = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";

        Matcher isbnValidate = Pattern.compile(regexISBN).matcher(book.getIsbn());
        Matcher dateValidate = Pattern.compile(regexDate).matcher(book.getDate());
        if (isbnValidate.matches() && dateValidate.matches()) {
            if (!this.bookDAO.searchExistsBook(book)){
                this.bookDAO.addBook(book);
                return "Book added successfully";
            }

            return "ISBN duplication";
        }
        return "Values aren't in specific format or are blank.";

    }

    public List<Book> showBookList() {
        return this.bookDAO.getAllBooks();
    }

    public ArrayList<LoanExtended> showBookListAndUser() {
        return this.loanDAO.getLoansWithUserInformation();
    }

    public ArrayList<LoanExtended> showBookListAndUserOverTime() {
        return this.loanDAO.getLoansWithUserInformationOverTime();
    }


    public String orderBookValidator(String name, String surname, String title, String action) {

        if (name.equals("") || surname.equals("") || title.equals("")) {
            return "Values aren't in specific format or are blank";
        }

        if (!this.userDAO.getSessionUser().getName().equals(name) &&
                !this.userDAO.getSessionUser().getSurname().equals(surname)) {
            return "Authentication failed.";
        }

        Book givenBook = this.bookDAO.getAllBooks().stream()
                .filter(book -> book.getTitle().equals(title))
                .findFirst()
                .orElse(null);

        if (givenBook == null) {
            return "Book not found";
        }

        if (action == "addLoan") {
            if (this.loanDAO.saveLoan(this.userDAO.getSessionUser(), givenBook)) {
                return "Loan save successful.";
            }
        }

        // In filter. Trying to get bookId in loan by title
        // So If given title is in book return isbn
        // Than if isbn is identical to bookId this is right loan who wants to give back book who ordered previously
        if (action == "deleteLoan") {
            Loan loanist = this.loanDAO.getLoans().stream()
                    .filter(loan -> loan.getBookId().equals(givenBook.getIsbnFromTitle(title)) &&
                            loan.getReturnDate() != null &&
                            loan.getUserid() == this.userDAO.getSessionUser().getId())
                    .findFirst()
                    .orElse(null);

            if (loanist == null) {
                return "Insert book which you ordered";
            }

            if (this.loanDAO.returnBook(this.userDAO.getSessionUser(), loanist)) {
                return "Returned book successful.";
            }
        }
        return "Returning book failed.";

    }

    public String checkProduct(int orderedId, int orderedQuantity) {
        if (productsDB.findById(orderedId))
        {
            if (orderedQuantity >= 1)
            {
                if (productsDB.getProduct(orderedId).getQuantity() - orderedQuantity >= 0)
                {
                    return String.format("Zakup przeszedł pomyślny. Do zapłaty: %2.2f",
                            productsDB.buyProduct(orderedId, orderedQuantity));
                }
                else {
                    return "Liczba sztuk przekracza liczbe sztuk posiadanych w magazynie przedmiotu (zmniejsz liczbe sztuk).";
                }
            }
            else {
                return "Liczba sztuk musi być minimum 1";
            }
        }
        else {
            return "Nie znaleziono przedmiotu";
        }
    }

    public String UserToAdmin(String login) {
        return switch (userDAO.checkRoleToAdmin(login)) {
            case "0" -> "Admin role deploy";
            case "1" -> "User already has admin privileges";
            case "2" -> "Couldn't find user";
            default -> "Undefined error";
        };
    }

    public String magazineManager(int givenId, int addValue) {
        if (productsDB.findById(givenId))
        {
            if (addValue >= 1)
            {
                productsDB.getProduct(givenId).addQuantity(addValue);
                return "Pomyślnie zakończono";
            }
            else
            {
                return "Liczba nie może być mniejsza niż 1";
            }
        }
        else
        {
            return "Nie znaleziono przedmiotu";
        }
    }

    public static Authenticator getInstance() {
        return instance;
    }
    public User getLoggedUser() {
        return loggedUser;
    }

    public void unmountLoggedUser() { this.loggedUser = null; }

    public String getSeed() {
        return seed;
    }


    public String isBlank(String text) {
        return text.equals("") ? null : text;
    }
}
