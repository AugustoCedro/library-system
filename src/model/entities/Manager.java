package model.entities;

import model.dao.BookDao;
import model.dao.ClientDao;
import model.dao.DaoFactory;
import model.dao.LoanDao;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Manager implements Serializable {

    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private static BookDao bookDao =  DaoFactory.createBookDao();
    private static ClientDao clientDao = DaoFactory.createClientDao();
    private static LoanDao loanDao = DaoFactory.createLoanDao();

    public Manager(){}


    public Manager(Integer id, String name, String email, String phone, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static BookDao getBookDao() {
        return bookDao;
    }

    public static void setBookDao(BookDao bookDao) {
        Manager.bookDao = bookDao;
    }

    public static ClientDao getClientDao() {
        return clientDao;
    }

    public static void setClientDao(ClientDao clientDao) {
        Manager.clientDao = clientDao;
    }

    public static LoanDao getLoanDao() {
        return loanDao;
    }

    public static void setLoanDao(LoanDao loanDao) {
        Manager.loanDao = loanDao;
    }

    public void addBook(Book book) {
        if(book != null) {
            bookDao.insert(book);
        }
    }
    public void deleteBook(Integer id) {
        if(id != null) {
            bookDao.deleteById(id);
        }
    }
    public void updateBook(Book book) {
        if(book != null) {
            bookDao.update(book);
        }
    }
    public List<Book> getBooks(){
        return bookDao.findAll();
    }
    public List<Book> getBooksByAuthor(String author) {
        return bookDao.findByAuthor(author);
    }
    public Book getBookByTitle(String title) {
        return bookDao.findByTitle(title);
    }
    public List<Book> getBooksByGenre(String genre) {
        return bookDao.findByGenre(genre);
    }
    public Book getBookById(int id){
        return bookDao.findById(id);
    }

    public List<Client> getClients(){
        return clientDao.findAll();
    }
    public void insertClient(Client client){
        clientDao.insert(client);
    }
    public void deleteClient(String email){
        clientDao.deleteByEmail(email);
    }
    public void updateClient(Client client){
        clientDao.update(client);
    }

    public void insertLoan(Loan loan){
        if(loan.getClient() != null && loan.getClient() == clientDao.findById(loan.getClient().getId())){
            loanDao.insert(loan);
        }else{
            throw new IllegalArgumentException("ERROR");
        }
    }

    public void deleteLoan(Integer id){
        loanDao.deleteById(id);
    }

    public Loan getLoanById(Integer id){
        return loanDao.findById(id);
    }
    public List<Loan> getLoans(){
        return loanDao.findAll();
    }
    public List<Loan> getByClientEmail(String email){
        return loanDao.findByEmail(email);
    }
    public List<Loan> getByBookTitle(String title){
        return loanDao.findByTitle(title);
    }
    public List<Loan> findByLoanDate(LocalDate date){
        return loanDao.findByLoanDate(date);
    }
    public List<Loan> findByReturnDate(LocalDate date){
        return loanDao.findByReturnDate(date);
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
