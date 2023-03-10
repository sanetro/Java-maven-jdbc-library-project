package pl.edu.wszib.library.models;

import java.util.Date;

public class LoanView {
    private String isbn;
    private String title;
    private String author;
    private int available;
    private  String name;
    private  String surname;
    private int id;
    private Date orderedDate;
    private Date deadlineDate;
    private Date returnDate;

    public LoanView(String isbn, String title, String author, Integer available, String name, String surname, int id, java.sql.Date orderdate, java.sql.Date deadlinedate, java.sql.Date returndate) {
    }

    public LoanView(String isbn, String title, String author, int available, String name, String surname, int id, Date orderedDate, Date deadlineDate, Date returnDate) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = available;
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.orderedDate = orderedDate;
        this.deadlineDate = deadlineDate;
        this.returnDate = returnDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(Date orderedDate) {
        this.orderedDate = orderedDate;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public Date getReturnDate() { return returnDate; }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
}
