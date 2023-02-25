package pl.edu.wszib.library.gui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import pl.edu.wszib.library.models.*;
import pl.edu.wszib.library.database.ProductsDB;

public class GUI {
    private final Scanner scanner = new Scanner(System.in);
    final ProductsDB productsDB = ProductsDB.getInstance();
    private static final GUI instance = new GUI();

    private GUI() {
    }

    public String showMenu() {
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        return scanner.nextLine();
    }

    public String showAdminPanel() {
        System.out.println("\n=== Admin Panel ===");
        System.out.println("1. Add book");
        System.out.println("2. Show List of all books");
        System.out.println("3. Loan the book");
        System.out.println("4. Return book");
        System.out.println("5. Show List of loans with user information");
        System.out.println("6. Show List of debt users with user information");
        System.out.println("7. Give user admin permission");
        System.out.println("8. Logout");
        return scanner.nextLine();
    }
    public String showUserPanel() {
        System.out.println("\n=== User Panel ===");
        System.out.println("1. Search book");
        System.out.println("2. Loan the book");
        System.out.println("3. List of books");
        System.out.println("4. Logout");
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

    public void layoutBooks(List<Book> books) {
        String line = "";
        System.out.printf("%-25s%-40s%30s%20s\n", "ISBN", "TITLE", "AUTHOR", "DATE");
        for (int i = 0; i < 115; i++) line += "-"; System.out.println(line);
        for (Book book : books) System.out.printf("%-25s%-40s%30s%20s\n",
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getDate());
    }

    public void layoutOrderedBooksExtended(ArrayList<LoanExtended> loans) {
        String line = "";
        System.out.printf("%-30s%-30s%-20s%-20s%-10s%-20s%-20s\n",
                "ISBN", "TITLE", "NAME", "SURNAME", "ID", "ORDERD DATE", "DEADLINE DATE");
        for (int i = 0; i < 143; i++) line += "-"; System.out.println(line);
        for (LoanExtended loan: loans) System.out.printf("%-30s%-30s%-20s%-20s%-10s%-20s%-20s\n",
                loan.getIsbn(),
                loan.getTitle(),
                loan.getName(),
                loan.getSurname(),
                loan.getId(),
                loan.getOrderedDate(),
                loan.getDeadlineDate());
    }

    public String readPlate() {
        System.out.println("Plate:");
        return this.scanner.nextLine();
    }

    public String readTextByCalled(String labelName) {
        System.out.println(labelName+ ":"); return scanner.nextLine();
    }


    public User readLoginAndPassword() {
        User user = new User();
        user.setLogin(this.readTextByCalled("Login"));
        user.setPassword(this.readTextByCalled("Password"));
        return user;
    }

    public User readRegisterFields() {
        User user = new User();
        user.setName(this.readTextByCalled("Name"));
        user.setSurname(this.readTextByCalled("Surname"));
        user.setLogin(this.readTextByCalled("Login"));
        user.setPassword(this.readTextByCalled("Password"));
        return user;
    }

    public Book readAddBookFields() {
        Book book = new Book();
        book.setIsbn(this.readTextByCalled("ISBN"));
        book.setTitle(this.readTextByCalled("Title"));
        book.setAuthor(this.readTextByCalled("Author"));
        book.setDate(this.readTextByCalled("Date (yyyy-mm-dd)"));
        return book;
    }

    public String readName() { return this.readTextByCalled("Your Name"); }
    public String readSurname() {
        return this.readTextByCalled("Your Surname");
    }
    public String readTitle() {
        return this.readTextByCalled("Title of book");
    }


    public static GUI getInstance() {
        return instance;
    }
}