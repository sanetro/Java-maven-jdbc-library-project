package pl.edu.wszib.library.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pl.edu.wszib.library.models.*;


public class GUI {
    private final Scanner scanner = new Scanner(System.in);
    private static final GUI instance = new GUI();

    private GUI() {
    }

    public String showMenu() {
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        return scanner.nextLine();
    }

    public static GUI getInstance() {
        return instance;
    }


}