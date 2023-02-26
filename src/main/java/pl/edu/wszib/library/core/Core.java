package pl.edu.wszib.library.core;

import pl.edu.wszib.library.DAO.LoanDAO;
import pl.edu.wszib.library.gui.GUI;
import pl.edu.wszib.library.models.Role;


public class Core {

    final Authenticator authenticator = Authenticator.getInstance();
    final LoanDAO loanDAO = LoanDAO.getInstance();
    final GUI gui = GUI.getInstance();
    private static final Core instance = new Core();

    private Core() {

    }
    public void start() {

        boolean isLogged = false;
        boolean running = true;

        while (running)
        {
            while (!isLogged)
            {
                switch (this.gui.showMenu()) {
                    case "1" -> {
                        this.authenticator.authenticate(this.gui.readLoginAndPassword());
                        isLogged = this.authenticator.getLoggedUser() != null;
                        if (!isLogged) System.out.println("Authentication failed");
                    }
                    case "2" -> {
                        if (this.authenticator.register(this.gui.readRegisterFields()))
                            System.out.println("Successful.");
                        else System.out.println("Authentication failed");
                    }
                    case "3" -> {
                        System.out.println("Bye bye..");
                        this.authenticator.unmountLoggedUser();
                        running = false;
                        isLogged = true;
                    }
                    default -> System.out.println("Undefined option");
                }
            }

            if(!running) break;

            if (this.authenticator.getLoggedUser().getRole() == Role.ADMIN)
            {
                while (isLogged)
                {
                    switch (this.gui.showAdminPanel()) {
                        case "1" -> // Add book
                                System.out.println(
                                        this.authenticator.addBookAgent(this.gui.readBookFields()));

                        case "2" -> // List all books
                                this.gui.layoutBooks(this.authenticator.showBookList());

                        case "3" -> // Order book
                                System.out.println(
                                        this.authenticator.orderBookValidator(
                                                this.gui.readName(),
                                                this.gui.readSurname(),
                                                this.gui.readTitle(),
                                                "addLoan"));

                        case "4" -> // Return Book
                                System.out.println(
                                        this.authenticator.orderBookValidator(
                                                this.gui.readName(),
                                                this.gui.readSurname(),
                                                this.gui.readTitle(),
                                                "deleteLoan"));

                        case "5" -> // Show List of loans with user information
                                this.gui.layoutOrderedBooksExtended(
                                        this.authenticator.showBookListAndUser());

                        case "6" -> // Show List of loans with user information
                                this.gui.layoutOrderedBooksExtended(
                                        this.authenticator.showBookListAndUserOverTime());

                        case "7" -> // Give user admin permission
                                System.out.println(
                                        this.authenticator.UserToAdmin(
                                                this.gui.readTextByCalled("Login")));

                        case "8" -> { // Search book and check available
                            switch (this.gui.showOptionsSearchBook()) {
                                case "1" -> this.gui.showExtendedBookInfo(
                                        this.loanDAO.getLoansWithUserInformationByOption("1",
                                                this.authenticator.isBlank(this.gui.readISBN())));
                                case "2" -> this.gui.showExtendedBookInfo(
                                        this.loanDAO.getLoansWithUserInformationByOption("2",
                                                this.authenticator.isBlank(this.gui.readTitle())));
                                case "3" -> this.gui.showExtendedBookInfo(
                                        this.loanDAO.getLoansWithUserInformationByOption("3",
                                                this.authenticator.isBlank(this.gui.readAuthor())));
                                default -> System.out.println("Undefined option");
                            }
                        }
                        case "9" -> { // Logout
                            System.out.println("Logged out");
                            this.authenticator.unmountLoggedUser();
                            isLogged = false;
                        }
                        default -> System.out.println("Undefined option!");
                    }
                }
            }
            else if (this.authenticator.getLoggedUser().getRole() == Role.USER)
            {
                while (isLogged)
                {
                    //switch (this.gui.showUserPanel())

                        System.out.println("NO");

                }
            }
            else System.out.println("Undefined Role. Can not find interface.");
        }
    }
    public static Core getInstance() {
        return instance;
    }
}
