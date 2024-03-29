package pl.edu.wszib.library.gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pl.edu.wszib.library.models.*;

import javax.swing.*;


public class GUI {
    private final Scanner scanner = new Scanner(System.in);
    private static final GUI instance = new GUI();


    private GUI() {
        JFrame frame = new JFrame();
        JButton button = new JButton("Click me");
        JLabel label = new JLabel("Number of clicks");

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(button);
        panel.add(label);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("Library Gray Lake");
        frame.pack();
        frame.setVisible(true);
        System.out.println("s");
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
        System.out.println("1. Show List of all books");
        System.out.println("2. Loan the book");
        System.out.println("3. Return book");
        System.out.println("4. Search book and check available");
        System.out.println("5. Logout");
        return scanner.nextLine();
    }

    public String showOptionsSearchBook() {
        System.out.println("\n=== Search Book by ===");
        System.out.println("1. ISBN");
        System.out.println("2. Title");
        System.out.println("3. Author");
        return scanner.nextLine();
    }

    public void layoutBooks(List<Book> books) {
        System.out.printf("%-25s%-40s%30s\n", "ISBN", "TITLE", "AUTHOR");
        headline(115);
        for (Book book : books) System.out.printf("%-25s%-40s%30s\n",
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor());
    }

    public void layoutOrderedBooksExtended(ArrayList<LoanView> loans) {
        System.out.printf("%-30s%-30s%-20s%-20s%-10s%-20s%-20s%-20s\n",
                "ISBN", "TITLE", "USER_NAME", "USER  SURNAME", "USER_ID", "ORDERED_DATE", "DEADLINE_DATE", "RETURN_DATE");
        headline(163);
        for (LoanView loan: loans) System.out.printf("%-30s%-30s%-20s%-20s%-10s%-20s%-20s%-20s\n",
                loan.getIsbn(),
                loan.getTitle(),
                loan.getName(),
                loan.getSurname(),
                loan.getId(),
                loan.getOrderedDate(),
                loan.getDeadlineDate(),
                loan.getReturnDate() == null ? "Not returned" : loan.getReturnDate());
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

    private void correctPattern(){
        System.out.println("""
                Patterns which are correct:
                __________________________
                ISBN 978-0-596-52068-7
                ISBN-13: 978-0-596-52068-7
                978 0 596 52068 7
                9780596520687
                0-596-52068-9
                0 512 52068 9
                ISBN-10 0-596-52068-9
                ISBN-10: 0-596-52068-9""");
    }

    public Book readBookFields() {
        Book book = new Book();
        correctPattern();
        book.setIsbn(this.readTextByCalled("ISBN"));
        book.setTitle(this.readTextByCalled("Title"));
        book.setAuthor(this.readTextByCalled("Author"));
        book.setAvailable(1);
        return book;
    }

    private void headline(int n) {
        System.out.println();
        for (int i = 0; i < n; i++) System.out.print("-");
        System.out.println();
    }

    public void showExtendedBookInfo(ArrayList<LoanView> loans) {
        try {
            System.out.printf("%-10s%-30s%-30s%-20s%-20s%-10s%-20s%-20s\n",
                    "AVAILABLE", "ISBN", "TITLE", "AUTHOR", "NAME & SURNAME", "USER_ID", "ORDERED_DATE", "DEADLINE_DATE");
            headline(163);
            for (LoanView loan : loans)
                System.out.printf("%-10s%-30s%-30s%-20s%-20s%-10s%-20s%-20s\n",
                        loan.getAvailable() == 1 ? "Yes" : "No",
                        loan.getIsbn(),
                        loan.getTitle(),
                        loan.getAuthor(),
                        loan.getAvailable() == 0 ? loan.getName() + " " + loan.getSurname(): "",
                        loan.getAvailable() == 0 ? loan.getId(): " ",
                        loan.getAvailable() == 0 ? loan.getOrderedDate(): "",
                        loan.getAvailable() == 0 && loan.getReturnDate() == null ? loan.getDeadlineDate(): "");
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
    public String readAuthor() {
        return this.readTextByCalled("Author of book");
    }

    public static GUI getInstance() {
        return instance;
    }


}