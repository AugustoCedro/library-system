package application;


import model.dao.BookDao;
import model.dao.DaoFactory;
import model.dao.GenreDao;
import model.dao.LoanDao;
import model.entities.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Program {
    public static void main(String[] args) {
        GenreDao genreDao = DaoFactory.createGenreDao();
        BookDao bookDao = DaoFactory.createBookDao();
        LoanDao loanDao = DaoFactory.createLoanDao();
        //List<Book> bookList = CSVReader.readBooks("books.csv");
        //List<Genre> genreList = CSVReader.readGenres("genres.csv");
        //List<Client> clientList = CSVReader.readClients("clients.csv");
        //List<Loan> loanList = CSVReader.readLoans("loans.csv");




    }
}
