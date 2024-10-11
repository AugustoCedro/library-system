package test;

import model.dao.BookDao;
import model.dao.ClientDao;
import model.dao.DaoFactory;
import model.dao.LoanDao;
import model.entities.Book;
import model.entities.CSVReader;
import model.entities.Client;
import model.entities.Loan;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LoanDaoJDBCTest {

    private static LoanDao loanDao = DaoFactory.createLoanDao();
    private static BookDao bookDao = DaoFactory.createBookDao();
    private static ClientDao clientDao = DaoFactory.createClientDao();

    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @Test
    public void findAll(){
        List<Loan> list = CSVReader.readLoans("loans.csv");
        assertEquals(list.size(),loanDao.findAll().size());
    }
    @Test
    public void findByReturnDate(){
        LocalDate date = LocalDate.parse("27/09/2024",dtf);
        assertEquals(2,loanDao.findByReturnDate(date).size());
    }
    @Test
    public void findByLoanDate(){
        LocalDate date = LocalDate.parse("12/09/2024",dtf);
        assertEquals(2,loanDao.findByLoanDate(date).size());
    }
    @Test
    public void findByTitle(){
        String title = "Educated";
        assertEquals(2,loanDao.findByTitle(title).size());
    }
    @Test
    public void findById(){
        int id = 25;
        assertEquals("The Lean Startup",loanDao.findById(25).getBook().getTitle());
    }
    @Test
    public void findByEmail(){
        String email = "lucas.almeida@example.com";
        assertEquals(4, loanDao.findByEmail(email).size());
    }
    @Test
    public void insert() {

        Book b = bookDao.findByTitle("Thinking Fast and Slow");
        Client c = clientDao.findById(2);
        Loan loan = new Loan(null, b, c, LocalDate.now());

        loanDao.insert(loan);
        Optional<Integer> expectedQuantity = Optional.of(b.getQuantity() - 1);
        Optional<Integer> actualQuantity = loanDao.findByTitle(loan.getBook().getTitle())
                .stream()
                .map(l -> l.getBook().getQuantity())
                .findFirst();

        assertEquals(expectedQuantity, actualQuantity);
    }
    @Test
    public void deleteById(){
        List<Loan> list = loanDao.findByTitle("Thinking Fast and Slow");
        Loan loan = list.get(0);
        Integer quantity = loan.getBook().getQuantity() + 1;
        loanDao.deleteById(loan.getId());
        assertEquals(quantity,bookDao.findById(41).getQuantity());
    }

}
