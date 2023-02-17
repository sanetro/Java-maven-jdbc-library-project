package pl.edu.wszib.library.gui;

import java.util.Scanner;
import pl.edu.wszib.library.models.User;
import pl.edu.wszib.library.database.ProductsDB;
import pl.edu.wszib.library.models.Product;
public class GUI {
    private final Scanner scanner = new Scanner(System.in);
    final ProductsDB productsDB = ProductsDB.getInstance();
    private static final GUI instance = new GUI();

    private GUI() {
    }

    public String showMenu() {
        System.out.println("1. Login");
        System.out.println("2. Exit");
        return scanner.nextLine();
    }

    public String showAdminPanel() {
        System.out.println("\n=== Panel ===");
        System.out.println("1. Add book");
        System.out.println("2. Search book");
        System.out.println("3. Loan the book");
        System.out.println("4. List of books");
        System.out.println("5. List of loans");
        System.out.println("6. Overdue loanists' list");
        System.out.println("7. Logout");
        return scanner.nextLine();
    }

    public String readUserLogin() {
        System.out.println("Nazwa usera:");
        return scanner.nextLine();
    }

    public String readUserPassword() {
        System.out.println("Nazwa usera:");
        return scanner.nextLine();
    }

    public String showUserPanel() {
        System.out.println("\n=== Panel Userowy ===");
        System.out.println("1. Lista produktow");
        System.out.println("2. Kup Produkt");
        System.out.println("3. Wyloguj");
        return scanner.nextLine();
    }

    public int readId() {
        System.out.println("ID przedmiotu:");
        return scanner.nextInt();
    }

    public int readQuantity() {
        System.out.println("Liczba sztuk:");
        return scanner.nextInt();
    }

    public void listProduct() {
        System.out.format("%-5s %-5s %30s %10.2s %10s %10s %10s\n", "ID", "FIRMA", "NAZWA", "CENA", "ILOŚĆ", "MAGAZYN", "KATEGORIA");
        System.out.println("--------------------------------------------------------------------------------------------");
        for(Product product : this.productsDB.getProducts()) {
            System.out.println(product);
        }
    }

    public String readPlate() {
        System.out.println("Plate:");
        return this.scanner.nextLine();
    }

    public User readLoginAndPassword() {
        User user = new User();
        System.out.println("Login:");
        user.setLogin(this.scanner.nextLine());
        System.out.println("Password:");
        user.setPassword(this.scanner.nextLine());
        return user;
    }
    public static GUI getInstance() {
        return instance;
    }
}