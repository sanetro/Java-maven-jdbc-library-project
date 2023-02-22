package pl.edu.wszib.library.models;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private String date; // yyyy-mm-dd

    public Book (){
    }
    public Book (String isbn, String title, String author, String date) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.date = date;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public String getDate() {
        return this.date;
    }

    public String getTitle() {
        return this.title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(isbn);
        sb.append("\t\t");
        sb.append(title);
        sb.append("\t\t");
        sb.append(author);
        sb.append("\t\t");
        sb.append(date);
        return sb.toString();
    }
}
