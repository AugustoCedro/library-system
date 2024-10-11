package model.dao;

import model.entities.Loan;

import java.util.List;

public interface LoanDao {
    void insert(Loan loan);
    List<Loan> findAll();
}
