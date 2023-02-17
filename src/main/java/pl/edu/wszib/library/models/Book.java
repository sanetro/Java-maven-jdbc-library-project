package pl.edu.wszib.library.models;

public class Book {
    public static String isbn;
    public static String title;
    public static String author;
    public static String date; // yyyy-mm-

    public Book (String isbn, String title, String author, String date) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.date = date;
    }

    public static String getAuthor() {
        return author;
    }

    public static String getIsbn() {
        return isbn;
    }

    public static String getDate() {
        return date;
    }

    public static String getTitle() {
        return title;
    }

    public static void setAuthor(String author) {
        Book.author = author;
    }

    public static void setDate(String date) {
        Book.date = date;
    }

    public static void setIsbn(String isbn) {
        Book.isbn = isbn;
    }

    public static void setTitle(String title) {
        Book.title = title;
    }
}
