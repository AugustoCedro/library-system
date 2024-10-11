package model.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


public class Loan implements Serializable {
    private Integer id;
    private Book book;
    private Client client;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private static final long daysToLoanReturn = 15;

    public Loan() {
    }

    public Loan(Integer id, Book book, Client client, LocalDate loanDate) {
        this.id = id;
        this.book = book;
        this.client = client;
        this.loanDate = loanDate;
        this.returnDate = loanDate.plusDays(daysToLoanReturn);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", book=" + book +
                ", client=" + client +
                ", loanDate=" + loanDate +
                ", returnDate=" + returnDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return Objects.equals(id, loan.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
