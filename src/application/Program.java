package application;


import model.dao.BookDao;
import model.dao.DaoFactory;
import model.dao.GenreDao;
import model.entities.Book;
import model.entities.CSVReader;
import model.entities.Client;
import model.entities.Genre;

import java.util.List;
import java.util.Objects;

public class Program {
    public static void main(String[] args) {
        GenreDao genreDao = DaoFactory.createGenreDao();
        BookDao bookDao = DaoFactory.createBookDao();
        //List<Book> bookList = CSVReader.readBooks("books.csv");
        //List<Genre> genreList = CSVReader.readGenres("genres.csv");
        //List<Client> clientList = CSVReader.readClients("clients.csv");

        List<Genre> genreList = genreDao.findAll();













    }
}
