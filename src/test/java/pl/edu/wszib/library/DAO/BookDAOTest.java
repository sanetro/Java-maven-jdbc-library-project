package pl.edu.wszib.library.DAO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edu.wszib.library.models.Book;

import java.util.List;

public class BookDAOTest {

    @Test
    public void GetAllBooksSuccessTest() {
        BookDAO bookDAO = BookDAO.getInstance();
        List<Book> bookList =  bookDAO.getAllBooks();
        Boolean statement = false;
        if (!bookList.isEmpty())
            statement = true;
        Assertions.assertTrue(statement);
    }

    @Test
    public void GetAllBooksFailedTest() {
        BookDAO bookDAO = BookDAO.getInstance();
        List<Book> bookList =  bookDAO.getAllBooks();
        if (bookList.isEmpty())
            Assertions.assertThrows(RuntimeException.class, () -> bookDAO.getAllBooks());
    }

}
