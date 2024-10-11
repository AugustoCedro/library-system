package test;

import model.dao.DaoFactory;
import model.dao.LoanDao;
import model.entities.CSVReader;
import model.entities.Loan;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LoanDaoJDBCTest {

    private static LoanDao loanDao = DaoFactory.createLoanDao();

    @Test
    public void findAll(){
        List<Loan> list = CSVReader.readLoans("loans.csv");
        assertEquals(list.size(),loanDao.findAll().size());
    }
}
