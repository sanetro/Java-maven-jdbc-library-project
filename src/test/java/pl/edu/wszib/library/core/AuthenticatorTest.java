package pl.edu.wszib.library.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edu.wszib.library.DAO.UserDAO;
import pl.edu.wszib.library.models.Book;
import pl.edu.wszib.library.models.Role;
import pl.edu.wszib.library.models.User;

import java.sql.Connection;

public class AuthenticatorTest {
    @Test
    public void SingletonAuthenticatorSuccessTest() {
        Authenticator authenticator = Authenticator.getInstance();
        UserDAO userDAO = UserDAO.getInstance();

        User realUser = new User(33, "user", "user", "user", "user", Role.USER);
        User expectedUser = userDAO.getUserByLogin(realUser.getLogin());

        authenticator.authenticate(realUser);
        User fetchedUser = authenticator.getLoggedUser();
        boolean sameData = false;
        if (expectedUser.getId() == fetchedUser.getId() &&
            expectedUser.getName().equals(fetchedUser.getName()) &&
            expectedUser.getSurname().equals(fetchedUser.getSurname()) &&
            expectedUser.getLogin().equals(fetchedUser.getLogin()) &&
            expectedUser.getRole().equals(fetchedUser.getRole()) &&
            expectedUser.getPassword().equals(fetchedUser.getPassword())) {
            sameData = true;
        }

        Assertions.assertTrue(sameData);

    }


    @Test
    public void AddBookAgentSuccessTest() {
        Book newBook = new Book("ISBN-10 0-596-00000-5", "Java Nowa", "Jamesa Goslinga", 1);
        Authenticator authenticator = Authenticator.getInstance();
        Boolean statement = false;
        if (authenticator.addBookAgent(newBook).equals("Book added Successful.")) {
            statement = true;
        }
        Assertions.assertTrue(statement);
    }

    @Test
    public void AddBookAgentBlankInputsFailedTest() { // checkout database, it's adding above if you are adding book
        Book newBook = new Book("", "", "", 1);
        Authenticator authenticator = Authenticator.getInstance();
        Boolean statement = false;
        if (authenticator.addBookAgent(newBook).equals("Values aren't in specific format or are blank.")) {
            statement = true;
        }
        Assertions.assertTrue(statement);
    }

    @Test
    public void OrderBookValidatorSuccessTest() {
        Authenticator authenticator = Authenticator.getInstance();
        UserDAO userDAO = UserDAO.getInstance();
        Boolean statement = false;
        int id = 31;
        String name = "mike";
        String surname = "mike";
        String login = "gus";
        String password = "1b715c8ce23d86c9ce278373c4a26abe";
        Role role = Role.ADMIN;
        String title = "Kampus";
        String action = "addLoan";
        String expectedMessage = "Loan save successful.";

        User expectedUser = new User(id, name, surname, login, password, role);
        User correctUser = userDAO.getUserByLogin(expectedUser.getLogin());
        authenticator.authenticate(correctUser);


        if(authenticator.orderBookValidator(name, surname, title, action)
                .equals(expectedMessage))
            statement = true;
        Assertions.assertTrue(statement);
    }
}
