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
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GenreDao genreDao = DaoFactory.createGenreDao();
        BookDao bookDao = DaoFactory.createBookDao();
        LoanDao loanDao = DaoFactory.createLoanDao();
        //List<Book> bookList = CSVReader.readBooks("books.csv");
        //List<Genre> genreList = CSVReader.readGenres("genres.csv");
        //List<Client> clientList = CSVReader.readClients("clients.csv");
        //List<Loan> loanList = CSVReader.readLoans("loans.csv");


        System.out.println("Welcome to the Library System");
        System.out.println("Choose 1 option: ");
        System.out.println("1 -> Enter as Client");
        System.out.println("2 -> Enter as Manager");
        int n = sc.nextInt();
        if(n == 2){
            System.out.println("Enter the ");
        }






    }
}
