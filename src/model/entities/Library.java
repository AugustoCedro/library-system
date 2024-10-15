package model.entities;

import model.dao.*;
import model.exceptions.InvalidInputException;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Library {
    private static Scanner sc = new Scanner(System.in);
    private static GenreDao genreDao = DaoFactory.createGenreDao();
    private static ClientDao clientDao = DaoFactory.createClientDao();
    private static BookDao bookDao = DaoFactory.createBookDao();
    private static LoanDao loanDao = DaoFactory.createLoanDao();
    private static ManagerDao managerDao = DaoFactory.createManagerDao();
    private static Manager manager;


    public static void menu() {
        while (true) {
            System.out.println("Welcome to the Library System");
            System.out.println("Choose 1 option: ");
            System.out.println("1 -> Enter as Client");
            System.out.println("2 -> Enter as Manager");
            int n = sc.nextInt();

            if (n == 1) {
                boolean clientLogged = clientLogin();
                if(!clientLogged){
                    break;
                }else{
                    System.out.println("adwiahwdhiawdihawdadw");
                    break;
                }
            }
            if (n == 2) {
                boolean managgerLogged = managerLogin();
                if (!managgerLogged) {
                    break;
                } else {
                    managerSystem(managgerLogged);
                }
            }
        }
    }

    protected static boolean managerLogin() {
        while (true) {
            System.out.print("Enter the manager email: ");
            String email = sc.next();
            System.out.print("Enter the password: ");
            String password = sc.next();
            Manager manager = managerDao.findByEmail(email);
            if (manager != null) {
                if (Objects.equals(password, manager.getPassword())) {
                    System.out.println("Login executed successfully!");
                    break;
                } else {
                    System.out.println("Invalid password for this email: " + email);
                }
            } else {
                System.out.println("Manager email : " + email + " not exists!");
            }
            if (processUserChoice()) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    protected static boolean clientLogin() {
        while (true) {
            try {
                System.out.println("1 -> Create an account");
                System.out.println("2 -> Login with an account");
                int n = sc.nextInt();
                sc.nextLine();
                if (n == 1) {
                    System.out.print("Enter your client name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter your client email: ");
                    String email = sc.next();

                    Client c = new Client(null, name, email);
                    if (clientDao.findByEmail(c.getEmail()) == null) {
                        clientDao.insert(c);
                        System.out.println("Account create successfully!");
                        break;
                    } else {
                        System.out.println("Account not created, Email already exists!");
                        if (processUserChoice()) {
                            continue;
                        } else {
                            return false;
                        }
                    }
                } else if (n == 2) {
                    System.out.println("Enter your client email: ");
                    String email = sc.next();
                    if (clientDao.findByEmail(email) != null) {
                        System.out.println("Login executed successfully!");
                        break;
                    } else {
                        System.out.println("Login not executed, Email not exists!");
                        if (processUserChoice()) {
                            continue;
                        } else {
                            return false;
                        }
                    }
                } else {
                    throw new InvalidInputException("Error: try again with another option");
                }
            } catch (Exception e) {
                throw new InvalidInputException("Error: " + e.getMessage());
            }
        }
        return true;
    }
    protected static boolean processUserChoice () {
        try {
                System.out.println("1 -> Try again");
                System.out.println("2 -> Return to main Menu");
                System.out.println("3 -> Close application");
                int n = sc.nextInt();
                if (n == 1) {
                    return true;
                } else if (n == 2) {
                    menu();
                } else if (n == 3) {
                    return false;
                } else {
                    System.out.println("Error: try again with another option");
                }
                return false;
            } catch (Exception e) {
                throw new InvalidInputException("Error: " + e.getMessage());
            }
    }

    protected static void managerSystem(boolean mannagerLogged){
        if(mannagerLogged){
            System.out.println("1 -> Operations with the Books of the Library");
            System.out.println("2 -> Operations with the Clients of the Library");
            System.out.println("3 -> Operations withe the Loans of the Library");
            int n = sc.nextInt();
            if(n == 1){
                bookOperations();
            }
            if(n == 2){
                clientOperations();
            }
        }
    }

    protected static void bookOperations() {
        while (true) {
            System.out.println("1 -> Insert a Book to Library");
            System.out.println("2 -> Remove a Book from Library");
            System.out.println("3 -> Update the Quantity of a specific Book from Library");
            System.out.println("4 -> Get all Books from Library");
            System.out.println("5 -> Search a specific Book from Library");
            System.out.println("6 -> Return to Main Menu");
            int n = sc.nextInt();
            sc.nextLine();
            if (n == 1) {
                System.out.print("Title of the Book: ");
                String title = sc.nextLine();
                System.out.print("Author of the Book: ");
                String author = sc.nextLine();
                System.out.print("Genre of the Book: ");
                String g = sc.nextLine();
                System.out.print("Quantity of this Book: ");
                int quantity = sc.nextInt();
                Genre genre = genreDao.findByName(g);
                if (genreExists(genre)) {
                    Book book = new Book(null, title, author, genre, quantity);
                    bookDao.insert(book);
                    System.out.println("Book: " + book.getTitle() + " added successfully!");
                } else {
                    System.out.println("Failed to add: Invalid Genre");
                }
            }  if (n == 2) {
                System.out.print("Id of the Book: ");
                int id = sc.nextInt();
                Book b = bookDao.findById(id);
                if (bookExists(b)) {
                    List<Loan> loan =  loanDao.findByTitle(b.getTitle());
                    System.out.println(b);
                    if (loan.isEmpty()) {
                        bookDao.deleteById(id);
                        System.out.println("Book: " + b.getTitle() + " removed succesfully!");
                    } else {
                        System.out.println("Failed to Remove: The Book have loans associated");
                    }
                } else {
                    System.out.println("Failed to Remove: Invalid Id");
                }
            }
             if (n == 3) {
                System.out.print("Title of the Book: ");
                String title = sc.nextLine();
                Book b = bookDao.findByTitle(title);
                if (bookExists(b)) {
                    System.out.print("Quantity to added: ");
                    int quantity = sc.nextInt();
                    b.setQuantity(b.getQuantity() + quantity);
                    bookDao.update(b);
                    System.out.println("Book: " + b.getTitle() + " quantity updated successfully!");
                } else {
                    System.out.println("Failed to update: Invalid Book");
                }
            }
             if (n == 4) {
                List<Book> list = bookDao.findAll();
                System.out.println("===================");
                for (Book b : list) {
                    System.out.println(b);
                }
                System.out.println("===================");
            } if (n == 5) {
                findBooksOperations();
            }
            if(n == 6){
                break;
            }
        }
    }
    protected static void findBooksOperations(){
        Book b;
        List<Book> list;
        System.out.println("1 -> Find by Book Id");
        System.out.println("2 -> Find by Book Title");
        System.out.println("3 -> Find by Book Author");
        System.out.println("4 -> Find by Book Genre");

        int n = sc.nextInt();
        sc.nextLine();
        if (n == 1){
            System.out.print("Book Id: ");
            int id = sc.nextInt();
            b = bookDao.findById(id);
            if(bookExists(b)){
                System.out.println(b);
            }else{
                System.out.println("Book not found");
            }
        }
        if(n == 2){
            System.out.print("Book Title: ");
            String title = sc.nextLine();
            b = bookDao.findByTitle(title);
            if(bookExists(b)){
                System.out.println(b);
            }else{
                System.out.println("Book not found");
            }
        }
        if(n == 3){
            System.out.print("Book Author: ");
            String author = sc.nextLine();
            list = bookDao.findByAuthor(author);
            if(!list.isEmpty()){
                System.out.println("===================");
                for(Book book: list){
                    System.out.println(book);
                }
                System.out.println("===================");

            }else{
                System.out.println("Books not found");
            }
        }
        if(n == 4){
            System.out.print("Book Genre: ");
            String genre = sc.nextLine();
            list = bookDao.findByGenre(genre);
            if(!list.isEmpty()){
                System.out.println("===================");
                for(Book book: list){
                    System.out.println(book);
                }
                System.out.println("===================");

            }else{
                System.out.println("Books not found");
            }
        }
    }
    protected static void clientOperations(){
        while(true){
            System.out.println("1 -> Insert a Client to Library");
            System.out.println("2 -> Remove a Client from Library ");
            System.out.println("3 -> Update data from a Client");
            System.out.println("4 -> Search All Clients");
            System.out.println("5 -> Search a specific Client from Library");
            System.out.println("6 -> Return to Main Menu");

            int n = sc.nextInt();
            sc.nextLine();
            if(n == 1){
                System.out.print("Name of the Client: ");
                String name = sc.nextLine();
                System.out.print("Email of the Client: ");
                String email = sc.nextLine();
                Client c = new Client(null,name,email);
                if(clientExists(c)){
                    clientDao.insert(c);
                    System.out.println("Client added successfully!");
                }else {
                    System.out.println("Insert Error, Client already exists!");
                }
            }
            if(n == 2){
                System.out.print("Email of the Client: ");
                String email = sc.nextLine();
                Client c = clientDao.findByEmail(email);
                if(!clientExists(c)){
                    clientDao.deleteByEmail(email);
                    System.out.println("Client removed successfully!");
                }else{
                    System.out.println("Remove error, Client not exists!");
                }
            }
            if(n == 3){
                System.out.print("Email of the Client that will be updated: ");
                String email = sc.nextLine();
                Client c = clientDao.findByEmail(email);
                if(!clientExists(c)){
                    System.out.print("New Name: ");
                    String name = sc.nextLine();
                    System.out.println("New email: ");
                    email = sc.nextLine();
                    Client c2 = new Client(c.getId(),name,email);
                    clientDao.update(c2);
                    System.out.println("Client updated sucessfully!");
                }else{
                    System.out.println("Update error, Client not exists!");
                }
            }
            if(n == 4){
                List<Client> list = clientDao.findAll();
                System.out.println("===================");
                for(Client c : list){
                    System.out.println(c);
                }
                System.out.println("===================");
            }
            if(n == 5){
                findClientsOperations();
            }
            if(n == 6){
                break;
            }
        }
    }
    protected static void findClientsOperations(){

        Client c;
        List<Client> list;

        System.out.println("1 -> Find by Client Id");
        System.out.println("2 -> Find by Client Email");
        System.out.println("3 -> Find by Client Name");
        int n = sc.nextInt();
        sc.nextLine();

        if(n == 1){
            System.out.print("Client Id: ");
            int id = sc.nextInt();
            c = clientDao.findById(id);
            if(!clientExists(c)){
                System.out.println(c);
            }else{
                System.out.println("Client not found");
            }
        }
        if(n == 2){
            System.out.print("Client Email: ");
            String email = sc.nextLine();
            c = clientDao.findByEmail(email);
            if(!clientExists(c)){
                System.out.println(c);
            }else{
                System.out.println("Client not found");
            }
        }
        if(n == 3){
            System.out.println("Client Name: ");
            String name = sc.nextLine();
            list = clientDao.findByName(name);
            if(!list.isEmpty()){
                System.out.println("===================");
                for(Client client : list){
                    System.out.println(client);
                }
                System.out.println("===================");
            }else {
                System.out.println("Client not found");
            }
        }

    }


    protected static boolean bookExists(Book b){
        if(b != null){
            return true;
        }else{
            return false;
        }
    }
    protected static boolean genreExists(Genre g){
        if(g != null){
            return true;
        }
        else{
            return false;
        }
    }

    protected static boolean loanExists(Loan l){
        if(l != null){
            return true;
        }
        else{
            return false;
        }
    }
    protected static boolean clientExists(Client c){
        if(c == null){
            return true;
        }
        Client obj = clientDao.findByEmail(c.getEmail());
        if (obj != null) {
            return false;
        }
        else{
            return true;
        }
    }

}

