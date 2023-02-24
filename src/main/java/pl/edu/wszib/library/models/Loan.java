package pl.edu.wszib.library.models;

import java.util.Date;

public class Loan {

    private int id;
    private int userid;
    private String bookId; // isbn
    private Date orderDate;
    private Date deadlineDate;

    public Loan() {
    }

    public Loan(int id, int userId, String bookId, Date orderDate, Date deadlineDate) {
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

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public Date getOrderDate() {
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

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
