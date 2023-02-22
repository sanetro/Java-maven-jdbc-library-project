package pl.edu.wszib.library.models;

import java.text.DateFormat;

public class Loan {

    private int id;
    private int userid;
    private String bookId; // isbn
    private DateFormat orderDate;
    private DateFormat deadlineDate;

    public Loan() {
    }

    public Loan(int id, int userId, String bookId, DateFormat orderDate, DateFormat deadlineDate) {
        this.id = id;
        this.userid = userId;
        this.bookId = bookId;
        this.orderDate = orderDate;
        this.deadlineDate = deadlineDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DateFormat getDeadlineDate() {
        return deadlineDate;
    }

    public DateFormat getOrderDate() {
        return orderDate;
    }

    public int getUserid() {
        return userid;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setDeadlineDate(DateFormat deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public void setOrderDate(DateFormat orderDate) {
        this.orderDate = orderDate;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
