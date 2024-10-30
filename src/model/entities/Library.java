package model.entities;

import model.dao.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
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
            try{
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
                        clientSystem(clientLogged);
                    }
                }
                else if (n == 2) {
                    boolean managgerLogged = managerLogin();
                    if (!managgerLogged) {
                        break;
                    } else {
                        managerSystem(managgerLogged);
                    }
                }else{
                    throw new InputMismatchException();
                }
            }catch (InputMismatchException e){
                System.out.println("ERROR: Invalid Input, try again");
                sc.nextLine();

            }
        }
    }

    private static void clientSystem(boolean clientLogged) {
        while (true){
            try{
                if(clientLogged) {
                    System.out.println("1 -> Operations with the Books of the Library");
                    System.out.println("2 -> Operations with the Clients of the Library");
                    System.out.println("3 -> Operations with the Loans of the Library");
                    int n = sc.nextInt();
                    if (n == 1) {
                        bookClientOperations();
                    } else if (n == 2) {
                        clientClientOperations();
                    } else if (n == 3) {
                        loanClientOperations();
                    } else {
                        throw new InputMismatchException();
                    }
                }
            } catch (InputMismatchException e){
                System.out.println("ERROR: Invalid Input, try again");
                sc.nextLine();
            }
        }
    }

    protected static void managerSystem(boolean mannagerLogged){
        while (true) {
            try{
                if(mannagerLogged){
                    System.out.println("1 -> Operations with the Books of the Library");
                    System.out.println("2 -> Operations with the Clients of the Library");
                    System.out.println("3 -> Operations with the Loans of the Library");
                    int n = sc.nextInt();
                    if(n == 1){
                        bookManagerOperations();

                    }
                    else if(n == 2){
                        clientManagerOperations();

                    }
                    else if(n == 3){
                        loanManagerOperations();

                    }else{
                        throw new InputMismatchException();
                    }
                }
            }catch (InputMismatchException e){
                System.out.println("ERROR: Invalid Input, try again");
                sc.nextLine();
            }

        }

    }







    protected static boolean managerLogin() {
        while (true) {
            try{
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
            }catch (InputMismatchException e){
                System.out.println("ERROR: Invalid Input, try again");
                sc.nextLine();
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
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                System.out.println("ERROR: Invalid Input, try again");

            }
        }
        return true;
    }

    protected static boolean processUserChoice () {
        while (true) {
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
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                System.out.println("ERROR: Invalid Input, try again");
                sc.nextLine();

            }
        }

    }

    private static void bookClientOperations() {
        while (true) {
            try {
                System.out.println("1 -> Get all Books from Library");
                System.out.println("2 -> Search a specific Book from Library");
                System.out.println("3 -> Return to Client Menu");
                int n = sc.nextInt();
                if (n == 1) {
                    List<Book> list = bookDao.findAll();
                    System.out.println("===================");
                    for (Book b : list) {
                        System.out.println(b);
                    }
                    System.out.println("===================");
                }
                else if (n == 2) {
                    findBooksOperations();
                }

                else if (n == 3) {
                    break;
                }
                else{
                    throw new InputMismatchException();
                }
            }catch (InputMismatchException e){
                System.out.println("ERROR: Invalid Input, try again");
                sc.nextLine();
            }
        }
    }
    protected static void bookManagerOperations() {
        while (true) {
            try {
                System.out.println("1 -> Insert a Book to Library");
                System.out.println("2 -> Remove a Book from Library");
                System.out.println("3 -> Update the Quantity of a specific Book from Library");
                System.out.println("4 -> Get all Books from Library");
                System.out.println("5 -> Search a specific Book from Library");
                System.out.println("6 -> Return to Manager Menu");
                int n = sc.nextInt();
                if (n == 1) {
                    sc.nextLine();
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
                }
                else if (n == 2) {
                    sc.nextLine();
                    System.out.print("Id of the Book: ");
                    int id = sc.nextInt();
                    Book b = bookDao.findById(id);
                    if (bookExists(b)) {
                        List<Loan> loan = loanDao.findByTitle(b.getTitle());
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
                else if (n == 3) {
                    sc.nextLine();
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
                else if (n == 4) {
                    List<Book> list = bookDao.findAll();
                    System.out.println("===================");
                    for (Book b : list) {
                        System.out.println(b);
                    }
                    System.out.println("===================");
                }
                else if (n == 5) {
                    findBooksOperations();
                }

                else if (n == 6) {
                    break;
                }
                else{
                    throw new InputMismatchException();
                }
            }catch (InputMismatchException e){
                System.out.println("ERROR: Invalid Input, try again");
                sc.nextLine();
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

        if (n == 1){
            sc.nextLine();
            System.out.print("Book Id: ");
            int id = sc.nextInt();
            b = bookDao.findById(id);
            if(bookExists(b)){
                System.out.println(b);
            }else{
                System.out.println("Book not found");
            }
        }
        else if(n == 2){
            sc.nextLine();
            System.out.print("Book Title: ");
            String title = sc.nextLine();
            b = bookDao.findByTitle(title);
            if(bookExists(b)){
                System.out.println(b);
            }else{
                System.out.println("Book not found");
            }
        }
        else if(n == 3){
            sc.nextLine();
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
        else if(n == 4){
            sc.nextLine();
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
        }else{
            throw new InputMismatchException();
        }
    }

    private static void clientClientOperations() {
        while(true){
        try{
            System.out.println("1 -> Update your data");
            System.out.println("2 -> Return to Manager Menu");

            int n = sc.nextInt();
            if(n == 1){
                sc.nextLine();
                System.out.print("Email your Email: ");
                String email = sc.nextLine();
                Client c = clientDao.findByEmail(email);

                if(clientDao.findByEmail(email) != null){
                    System.out.print("New Name: ");
                    String name = sc.nextLine();
                    System.out.println("New email: ");
                    email = sc.nextLine();
                    Client c2 = new Client(c.getId(),name,email);
                    clientDao.update(c2);
                    System.out.println("Client updated sucessfully!");
                }else{
                    System.out.println("Update error: Client not exists!");
                }
            }
            else if(n == 2){
                break;
            }else{
                throw new InputMismatchException();
            }
        }catch (InputMismatchException e){
            System.out.println("ERROR: Invalid Input, try again");
            sc.nextLine();
        }
    }
    }
    protected static void clientManagerOperations(){
        while(true){
            try{
                System.out.println("1 -> Insert a Client to Library");
                System.out.println("2 -> Remove a Client from Library ");
                System.out.println("3 -> Update data from a Client");
                System.out.println("4 -> Search All Clients");
                System.out.println("5 -> Search a specific Client from Library");
                System.out.println("6 -> Return to Manager Menu");

                int n = sc.nextInt();
                if(n == 1){
                    sc.nextLine();
                    System.out.print("Name of the Client: ");
                    String name = sc.nextLine();
                    System.out.print("Email of the Client: ");
                    String email = sc.nextLine();
                    Client c = new Client(null,name,email);
                    if(clientDao.findByEmail(email) == null){
                        clientDao.insert(c);
                        System.out.println("Client added successfully!");
                    }else {
                        System.out.println("Failed to add: Client already exists!");
                    }
                }
                else if(n == 2){
                    sc.nextLine();
                    System.out.print("Email of the Client: ");
                    String email = sc.nextLine();
                    Client c = clientDao.findByEmail(email);
                    if(clientExists(c)){
                        List<Loan> list = loanDao.findByEmail(c.getEmail());
                        if(list.isEmpty()){
                            clientDao.deleteByEmail(email);
                            System.out.println("Client removed successfully!");
                        }else{
                            System.out.println("Failed to Remove: The Client have Loans associated");
                        }

                    }else{
                        System.out.println("Failed to Remove: Client not exists!");
                    }
                }
                else if(n == 3){
                    sc.nextLine();
                    System.out.print("Email of the Client that will be updated: ");
                    String email = sc.nextLine();
                    Client c = clientDao.findByEmail(email);

                    if(clientDao.findByEmail(email) != null){
                        System.out.print("New Name: ");
                        String name = sc.nextLine();
                        System.out.println("New email: ");
                        email = sc.nextLine();
                        Client c2 = new Client(c.getId(),name,email);
                        clientDao.update(c2);
                        System.out.println("Client updated sucessfully!");
                    }else{
                        System.out.println("Update error: Client not exists!");
                    }
                }
                else if(n == 4){
                    List<Client> list = clientDao.findAll();
                    System.out.println("===================");
                    for(Client c : list){
                        System.out.println(c);
                    }
                    System.out.println("===================");
                }
                else if(n == 5){
                    findClientsOperations();
                }
                else if(n == 6){
                    break;
                }else{
                    throw new InputMismatchException();
                }
            }catch (InputMismatchException e){
                System.out.println("ERROR: Invalid Input, try again");
                sc.nextLine();
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


        if(n == 1){
            sc.nextLine();
            System.out.print("Client Id: ");
            int id = sc.nextInt();
            c = clientDao.findById(id);
            if(clientExists(c)){
                System.out.println(c);
            }else{
                System.out.println("Client not found");
            }
        }
        else if(n == 2){
            sc.nextLine();
            System.out.print("Client Email: ");
            String email = sc.nextLine();
            c = clientDao.findByEmail(email);
            if(clientExists(c)){
                System.out.println(c);
            }else{
                System.out.println("Client not found");
            }
        }
        else if(n == 3){
            sc.nextLine();
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
        }else {
            throw new InputMismatchException();
        }

    }

    private static void loanClientOperations() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while(true){
            try{
                System.out.println("1 -> Search your loans");
                System.out.println("2 -> Make a loan");
                System.out.println("3 -> Return to Main Menu");

                int n = sc.nextInt();


                if(n == 1){
                    sc.nextLine();
                    System.out.print("Insert your Email: ");
                    String email = sc.nextLine();
                    List<Loan> list = loanDao.findByEmail(email);
                    if(!list.isEmpty()){
                        System.out.println("===================");
                        for(Loan l : list){
                            System.out.println(l);
                        }
                        System.out.println("===================");
                    }else{
                        System.out.println("Failed to Search: Client email " + email + " not exists");
                    }
                }

                else if(n == 2){
                    sc.nextLine();
                    System.out.print("Enter the Title of the book to be borrowed: ");
                    String title = sc.nextLine();;

                    System.out.println("Enter the email of the Client who will make the loan: ");
                    String email = sc.nextLine();

                    Book b = bookDao.findByTitle(title);
                    Client c = clientDao.findByEmail(email);

                    System.out.print("Insert the Loan Date dd/MM/yyyy : ");
                    String dateString = sc.nextLine();
                    LocalDate date = LocalDate.parse(dateString,dtf);
                    System.out.println(date);
                    if(bookExists(b) && clientExists(c)){
                        Loan loan = new Loan(null,b,c,date);
                        loanDao.insert(loan);
                        System.out.println("Loan  added successfully");

                    }
                    else{
                        System.out.println("Failed to insert: data insert error");
                    }
                }
                else if(n == 3){
                    break;
                }else{
                    throw new InputMismatchException();
                }
            }catch (InputMismatchException e){
                System.out.println("ERROR: Invalid Input, try again");
                sc.nextLine();
            }
     }
    }
    protected static void loanManagerOperations(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while(true){
            try{
                System.out.println("1 -> Search for a Loan by Id");
                System.out.println("2 -> Search for a Loan by Book Title");
                System.out.println("3 -> Search for a Loan by Client Email");
                System.out.println("4 -> Search for a Loan by created date");
                System.out.println("5 -> Search for a Loan by return Date");
                System.out.println("6 -> Find all Loans");
                System.out.println("7 -> Delete a Loan");
                System.out.println("8 -> Insert a Loan");
                System.out.println("9 -> Return to Main Menu");

                int n = sc.nextInt();

                if(n == 1){
                    sc.nextLine();
                    System.out.print("Insert the Loan Id: ");
                    int id = sc.nextInt();
                    Loan l = loanDao.findById(id);
                    if(loanExists(l)){
                        System.out.println(l);
                    }else{
                        System.out.println("Failed to Search: Id " + id + " not exists");
                    }
                }
                else if(n == 2){
                    sc.nextLine();
                    System.out.print("Insert the Book Title: ");
                    String title = sc.nextLine();
                    List<Loan> list = loanDao.findByTitle(title);
                    if(!list.isEmpty()){
                        System.out.println("===================");
                        for(Loan l : list){
                            System.out.println(l);
                        }
                        System.out.println("===================");
                    }else{
                        System.out.println("Failed to Search: title" + title + " not exists");
                    }
                }
                else if(n == 3){
                    sc.nextLine();
                    System.out.print("Insert the Client Email: ");
                    String email = sc.nextLine();
                    List<Loan> list = loanDao.findByEmail(email);
                    if(!list.isEmpty()){
                        System.out.println("===================");
                        for(Loan l : list){
                            System.out.println(l);
                        }
                        System.out.println("===================");
                    }else{
                        System.out.println("Failed to Search: Client email " + email + " not exists");
                    }
                }
                else  if(n == 4){
                    sc.nextLine();
                    System.out.print("Insert the Loan Date dd/MM/yyyy : ");
                    String dateString = sc.nextLine();
                    LocalDate date = LocalDate.parse(dateString,dtf);
                    List<Loan> list = loanDao.findByLoanDate(date);
                    if(!list.isEmpty()){
                        System.out.println("===================");
                        for(Loan l : list){
                            System.out.println(l);
                        }
                        System.out.println("===================");
                    }else{
                        System.out.println("There is no Loans in this Date");
                    }
                }
                else if(n == 5){
                    sc.nextLine();
                    System.out.print("Insert the Loan Return Date dd/MM/yyyy : ");
                    String dateString = sc.nextLine();
                    LocalDate date = LocalDate.parse(dateString,dtf);
                    List<Loan> list = loanDao.findByReturnDate(date);
                    if(!list.isEmpty()){
                        System.out.println("===================");
                        for(Loan l : list){
                            System.out.println(l);
                        }
                        System.out.println("===================");
                    }else{
                        System.out.println("There is no Loans in this Return Date");
                    }
                }
                else if(n == 6){
                    List<Loan> list = loanDao.findAll();
                    if(!list.isEmpty()){
                        System.out.println("===================");
                        for(Loan l : list){
                            System.out.println(l);
                        }
                        System.out.println("===================");
                    }else{
                        System.out.println("Failed to Search: No Loans Found");
                    }
                }
                else if(n == 7){
                    sc.nextLine();
                    System.out.print("Insert the Loan Id: ");
                    int id = sc.nextInt();
                    Loan l = loanDao.findById(id);
                    if(loanExists(l)){
                        loanDao.deleteById(id);
                        System.out.println("Loan removed with successfully");
                    }else{
                        System.out.println("Failed to Delete: Id " + id + " not exists");
                    }
                }
                else if(n == 8){
                    sc.nextLine();
                    System.out.print("Enter the Title of the book to be borrowed: ");
                    String title = sc.nextLine();;

                    System.out.println("Enter the email of the Client who will make the loan: ");
                    String email = sc.nextLine();

                    Book b = bookDao.findByTitle(title);
                    Client c = clientDao.findByEmail(email);

                    System.out.print("Insert the Loan Date dd/MM/yyyy : ");
                    String dateString = sc.nextLine();
                    LocalDate date = LocalDate.parse(dateString,dtf);
                    System.out.println(date);
                    if(bookExists(b) && clientExists(c)){
                        Loan loan = new Loan(null,b,c,date);
                        loanDao.insert(loan);
                        System.out.println("Loan  added successfully");

                    }
                    else{
                        System.out.println("Failed to insert: data insert error");
                    }
                }
                else if(n == 9){
                    break;
                }else{
                    throw new InputMismatchException();
                }
            }catch (InputMismatchException e){
                System.out.println("ERROR: Invalid Input, try again");
                sc.nextLine();
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
        if (c != null) {
            return true;
        }
        else{
            return false;
        }
    }

}
