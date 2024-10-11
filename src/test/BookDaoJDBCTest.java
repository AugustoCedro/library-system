package test;

import model.dao.BookDao;
import model.dao.DaoFactory;
import model.dao.GenreDao;
import model.entities.Book;
import model.entities.CSVReader;
import model.entities.Genre;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BookDaoJDBCTest{

    private static BookDao bookDao = DaoFactory.createBookDao();
    private static GenreDao genreDao = DaoFactory.createGenreDao();
    private static Genre g = genreDao.findById(4);
    @Test
    public void insert() {
        Book test = new Book(null,"Teste","Author",g,300);
        bookDao.insert(test);
        assertEquals("Teste",bookDao.findByTitle("Teste").getTitle());
        test = bookDao.findByTitle("Teste");
        bookDao.deleteById(test.getId());
    }

    @Test
    public void update() {
        Book test = new Book(null,"Teste","Author",g,300);
        bookDao.insert(test);
        test.setTitle("TesteUpdate");
        bookDao.update(test);
        assertEquals("TesteUpdate",bookDao.findByTitle("TesteUpdate").getTitle());
        test = bookDao.findByTitle("TesteUpdate");
        bookDao.deleteById(test.getId());
    }

    @Test
    public void deleteById() {
        Book test = new Book(null,"Teste","Author",g,300);
        bookDao.insert(test);
        test = bookDao.findByTitle("Teste");
        bookDao.deleteById(test.getId());
        test = bookDao.findByTitle("Teste");
        assertEquals(null,test);

    }

    @Test
    public void findById() {
        assertEquals("The Hobbit",bookDao.findById(2).getTitle());
    }

    @Test
    public void findByTitle() {
        assertEquals("The Hobbit",bookDao.findByTitle("The Hobbit").getTitle());
    }

    @Test
    public void findByAuthor() {
        assertEquals("Dune",bookDao.findByAuthor("Frank Herbert").getFirst().getTitle());
    }

    @Test
    public void findByGenre() {
    }

    @Test
    public void findAll() {
        List<Book> list = CSVReader.readBooks("books.csv");
        assertEquals(list.size(),bookDao.findAll().size());
    }
}
