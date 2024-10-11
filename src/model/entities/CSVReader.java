package model.entities;

import model.dao.BookDao;
import model.dao.ClientDao;
import model.dao.DaoFactory;
import model.dao.impl.BookDaoJDBC;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    private static String filePath = "D:\\projetos java\\library-system\\src\\application\\resources\\";
    public static List<Genre> readGenres(String fileName) {
        String file = filePath + fileName;
        List<Genre> genres = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line = br.readLine();
            while (line != null){
                Genre genre = new Genre(null,line);
                genres.add(genre);
                line = br.readLine();
            }
            return genres;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Book> readBooks(String fileName) {
        String file = filePath + fileName;
        List<Book> books = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line = br.readLine();
            while(line != null){
                String[] fields = line.split(",");
                Genre genre = new Genre(null, fields[2]);
                Book book = new Book(null,fields[0],fields[1],genre,Integer.parseInt(fields[3]));
                books.add(book);
                line = br.readLine();
            }
            return books;
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Client> readClients(String fileName){
        String file = filePath + fileName;
        List<Client> clients = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line = br.readLine();
            while(line != null) {
                String[] fields = line.split(",");
                Client client = new Client(null, fields[0], fields[1]);
                clients.add(client);
                line = br.readLine();
            }
            return clients;
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Loan> readLoans(String fileName){
        String file = filePath + fileName;
        BookDao bookDao = DaoFactory.createBookDao();
        ClientDao clientDao = DaoFactory.createClientDao();
        List<Loan> loans = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line = br.readLine();
            while(line != null) {
                String[] fields = line.split(",");
                LocalDate date = LocalDate.parse(fields[2]);
                Loan loan = new Loan(null,bookDao.findByTitle(fields[0]),clientDao.findByEmail(fields[1]),date);
                loans.add(loan);
                line = br.readLine();
            }
            return loans;
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

