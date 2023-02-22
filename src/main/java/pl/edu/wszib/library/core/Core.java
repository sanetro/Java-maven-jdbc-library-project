package pl.edu.wszib.library.core;

import pl.edu.wszib.library.database.ProductsDB;
import pl.edu.wszib.library.gui.GUI;
import pl.edu.wszib.library.models.Role;
import pl.edu.wszib.library.models.User;

public class Core {
    final ProductsDB productsDB = ProductsDB.getInstance();
    final Authenticator authenticator = Authenticator.getInstance();
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
                switch (this.gui.showMenu())
                {
                    case "1":
                        this.authenticator.authenticate(this.gui.readLoginAndPassword());
                        isLogged = this.authenticator.getLoggedUser() != null;
                        if (!isLogged) System.out.println("Authentication failed");
                        break;

                    case "2":
                        if (this.authenticator.register(this.gui.readRegisterFields()))
                            System.out.println("Successful.");
                        else System.out.println("Authentication failed");
                        break;

                    case "3":
                        System.out.println("Bye bye..");
                        this.authenticator.unmountLoggedUser();

                        running = false;
                        isLogged = true;
                        break;

                    default:
                        System.out.println("Undefined option");
                        break;
                }
            }

            if(running == false) break;

            if (this.authenticator.getLoggedUser().getRole() == Role.ADMIN)
            {
                while (isLogged)
                {
                    switch (this.gui.showAdminPanel())
                    {
                        case "1": // Add book
                            System.out.println(
                                    this.authenticator.addBookAgent(this.gui.readAddBookFields()));
                            break;

                        case "2": // List all books
                            this.gui.layoutBooks();
                            this.authenticator.showBookList();
                            /*System.out.println(
                                    this.authenticator.checkProduct(
                                            this.gui.readId(),
                                            this.gui.readQuantity()
                                    ));*/
                            break;

                        case "3": // Increase product quantity
                            System.out.println(
                                    this.authenticator.magazineManager(
                                        this.gui.readId(),this.gui.readQuantity()
                                    ));
                            break;

                        case "4": // Give user admin permission
                            System.out.println(
                                    this.authenticator.UserToAdmin(
                                            this.gui.readTextByCalled("Login")));
                            break;

                        case "7": // Logout
                            System.out.println("Wylogowano");
                            this.authenticator.unmountLoggedUser();
                            isLogged = false;
                            break;

                        default:
                            System.out.println("Nie ma takiej opcji");
                            break;
                    }
                }
            }
            else if (this.authenticator.getLoggedUser().getRole() == Role.USER)
            {
                while (isLogged)
                {
                    switch (this.gui.showUserPanel())
                    {
                        case "1": // Show product list
                            //this.gui.listProduct();
                            break;

                        case "2": // Buy product
                            System.out.println(
                                    this.authenticator.checkProduct(
                                            this.gui.readId(),
                                            this.gui.readQuantity()
                                    ));
                            break;

                        case "3": // Logout
                            System.out.println("Wylogowano");
                            this.authenticator.unmountLoggedUser();
                            isLogged = false;
                            break;

                        default:
                            System.out.println("Nie ma takiej opcji");
                            break;
                    }
                }
            }
            else System.out.println("Nieznana rola! Nie wybrano panelu.");
        }
    }
    public static Core getInstance() {
        return instance;
    }
}
