package pl.edu.wszib.library.core;

import org.apache.commons.codec.digest.DigestUtils;
import pl.edu.wszib.library.DAO.BookDAO;
import pl.edu.wszib.library.DAO.LoanDAO;
import pl.edu.wszib.library.DAO.UserDAO;
import pl.edu.wszib.library.database.ProductsDB;
import pl.edu.wszib.library.models.Book;
import pl.edu.wszib.library.models.Role;
import pl.edu.wszib.library.models.User;

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
        return "Values aren't in specific format.";

    }

    public void showBookList() {
        this.bookDAO.arrayLayout(this.bookDAO.getAllBooks());
    }


    public String orderBookValidator(String name, String surname, String title) {
        if (name.equals("") || surname.equals("") || title.equals("")) {
            return "Values aren't in specific format or are blank";
        } else {
            if (this.loanDAO.saveLoan(name, surname, title)) {
                return "Loan save successful.";
            }
            else {
                return "Loan save failed.";
            }
        }
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
        String result;
        switch (userDAO.checkRoleToAdmin(login)) {
            case "0":
                result = "Admin role deploy";
                break;
            case "1":
                result = "User already has admin privileges";
                break;
            case "2":
                result = "Couldn't find user";
                break;
            default:
                result = "Undefined error";
                break;
        }
        return result;
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

}
