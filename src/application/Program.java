package application;


import model.dao.*;
import model.entities.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {


        //List<Book> bookList = CSVReader.readBooks("books.csv");
        //List<Genre> genreList = CSVReader.readGenres("genres.csv");
        //List<Client> clientList = CSVReader.readClients("clients.csv");
        //List<Loan> loanList = CSVReader.readLoans("loans.csv");


        Library.menu();



    }
}
