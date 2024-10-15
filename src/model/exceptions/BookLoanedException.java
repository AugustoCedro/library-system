package model.exceptions;

public class BookLoanedException extends RuntimeException{
    public BookLoanedException(String message) {
        super(message);
    }
}
