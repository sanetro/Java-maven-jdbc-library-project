package pl.edu.wszib.library.models;

import java.util.Date;

public class LoanExtended {
    private String isbn;
    private String title;
    private  String name;
    private  String surname;
    private int id;
    private Date orderedDate;
    private Date deadlineDate;

    public LoanExtended() {
    }

    public LoanExtended(String isbn, String title, String name, String surname, int id, Date orderedDate, Date deadlineDate) {
        this.isbn = isbn;
        this.title = title;
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.orderedDate = orderedDate;
        this.deadlineDate = deadlineDate;
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
}
