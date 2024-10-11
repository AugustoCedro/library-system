package model.dao;

import model.entities.Loan;

import java.time.LocalDate;
import java.util.List;

public interface LoanDao {
    void insert(Loan loan);
    void deleteById(Integer id);
    List<Loan> findByEmail(String email);
    List<Loan> findAll();
    List<Loan> findByTitle(String title);
    List<Loan> findByLoanDate(LocalDate date);
    List<Loan> findByReturnDate(LocalDate date);
    Loan findById(Integer id);
}
