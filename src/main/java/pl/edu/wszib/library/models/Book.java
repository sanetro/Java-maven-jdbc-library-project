package pl.edu.wszib.library.models;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private int available;

    public Book (){
    }
    public Book (String isbn, String title, String author, int available) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = available;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public int getAvailable() {
        return this.available;
    }

    public String getTitle() {
        return this.title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAvailable(int available) {
        this.available = available;
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
        sb.append(available);
        return sb.toString();
    }

    public Object getIsbnFromTitle(String title) {
        return this.isbn;
    }
}
