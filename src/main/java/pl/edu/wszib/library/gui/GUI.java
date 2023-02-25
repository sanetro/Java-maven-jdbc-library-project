package pl.edu.wszib.library.gui;

import java.util.ArrayList;
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
        System.out.println("8. Search book and check available");
        System.out.println("9. Logout");
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

    public String showOptionsSearchBook() {
        System.out.println("\n=== Search Book by ===");
        System.out.println("1. ISBN");
        System.out.println("2. Title");
        System.out.println("3. Author");
        System.out.println("4. Date");
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
        System.out.printf("%-25s%-40s%30s%20s\n", "ISBN", "TITLE", "AUTHOR", "DATE");
        headline(115);
        for (Book book : books) System.out.printf("%-25s%-40s%30s%20s\n",
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getDate());
    }

    public void layoutOrderedBooksExtended(ArrayList<LoanExtended> loans) {
        System.out.printf("%-30s%-30s%-20s%-20s%-10s%-20s%-20s%-20s\n",
                "ISBN", "TITLE", "USER NAME", "USER  SURNAME", "USER ID", "ORDERD DATE", "DEADLINE DATE", "RETURN DATE");
        headline(163);
        for (LoanExtended loan: loans) System.out.printf("%-30s%-30s%-20s%-20s%-10s%-20s%-20s%-20s\n",
                loan.getIsbn(),
                loan.getTitle(),
                loan.getName(),
                loan.getSurname(),
                loan.getId(),
                loan.getOrderedDate(),
                loan.getDeadlineDate(),
                loan.getReturnDate() == null ? "Not returned" : loan.getReturnDate());
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

    public Book readBookFields() {
        Book book = new Book();
        book.setIsbn(this.readTextByCalled("ISBN"));
        book.setTitle(this.readTextByCalled("Title"));
        book.setAuthor(this.readTextByCalled("Author"));
        book.setDate(this.readTextByCalled("Date (yyyy-mm-dd)"));
        return book;
    }

    private void headline(int n) {
        System.out.println();
        for (int i = 0; i < n; i++) System.out.print("-");
        System.out.println();
    }

    public void showExtendedBookInfo(ArrayList<LoanExtended> loans) {
        try {
            System.out.printf("%-30s%-30s%-20s%-20s%-10s%-20s%-20s%-20s\n",
                    "ISBN", "TITLE", "AUTHOR", "NAME & SURNAME", "USER ID", "ORDERED DATE", "DEADLINE DATE", "RETURNED DATE");
            headline(163);
            for (LoanExtended loan : loans)
                System.out.printf("%-30s%-30s%-20s%-20s%-10s%-20s%-20s%-20s\n",
                        loan.getIsbn(),
                        loan.getTitle(),
                        loan.getAuthor(),
                        loan.getReturnDate() == null ? loan.getName() + " " + loan.getSurname(): "",
                        loan.getReturnDate() == null ? loan.getId(): "",
                        loan.getReturnDate() == null ? loan.getOrderedDate(): "",
                        loan.getReturnDate() == null ? loan.getDeadlineDate(): "",
                        loan.getReturnDate() == null ? "Not returned" : loan.getReturnDate());
        } catch (Exception e) {
            System.out.println("No records");
        }
    }

    public String readName() { return this.readTextByCalled("Your Name"); }
    public String readSurname() {
        return this.readTextByCalled("Your Surname");
    }
    public String readTitle() {
        return this.readTextByCalled("Title of book");
    }
    public String readISBN() {
        return this.readTextByCalled("ISBN of book");
    }
    public String readDate() {
        return this.readTextByCalled("Date of book");
    }
    public String readAuthor() {
        return this.readTextByCalled("Author of book");
    }


    public static GUI getInstance() {
        return instance;
    }


}