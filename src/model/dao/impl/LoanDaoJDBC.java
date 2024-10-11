package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.BookDao;
import model.dao.ClientDao;
import model.dao.DaoFactory;
import model.dao.LoanDao;
import model.entities.Book;
import model.entities.Client;
import model.entities.Genre;
import model.entities.Loan;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoanDaoJDBC implements LoanDao {
    private static Connection conn = null;

    public LoanDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    private Loan instantiateLoan(ResultSet rs, Book book, Client client) throws SQLException {
        Loan obj = new Loan();
        obj.setId(rs.getInt("Id"));
        obj.setBook(book);
        obj.setClient(client);
        obj.setLoanDate(rs.getDate("Loan_Date").toLocalDate());
        obj.setReturnDate(rs.getDate("Return_Date").toLocalDate());
        return obj;
    }

    @Override
    public void insert(Loan loan) {
        PreparedStatement st = null;

        try{
            conn.setAutoCommit(false);
            st = conn.prepareStatement("INSERT INTO loans (Book_Id,Client_Id,Loan_Date,Return_Date) " +
                    "VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            st.setInt(1,loan.getBook().getId());
            st.setInt(2,loan.getClient().getId());
            st.setDate(3,new java.sql.Date(java.sql.Date.valueOf(loan.getLoanDate()).getTime()));
            st.setDate(4,new java.sql.Date(java.sql.Date.valueOf(loan.getReturnDate()).getTime()));
            int rowsAffected = st.executeUpdate();
            if(rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    loan.setId(rs.getInt(1));
                    BookDao bookDao = DaoFactory.createBookDao();
                    Book b = bookDao.findById(loan.getBook().getId());
                    if(b.getQuantity() > 0){
                        b.setQuantity(b.getQuantity() - 1);
                        bookDao.update(b);
                    }else{
                        conn.rollback();
                        throw new DbException("Insert failed: Because there is no " + b.getTitle() + " more avaible");

                    }
                }
                conn.commit();
                DB.closeResultSet(rs);
            }else{
                throw new DbException("Insert failed");
            }
        }catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.setAutoCommit(conn);
        }
    }

    @Override
    public List<Loan> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Loan> list = new ArrayList<>();
        Map<Integer,Book> bookMap = new HashMap<>();
        Map<Integer,Client> clientMap = new HashMap<>();
        BookDao bookDao = DaoFactory.createBookDao();
        ClientDao clientDao = DaoFactory.createClientDao();
        try{
            st = conn.prepareStatement("SELECT loans.*, book.Title as Book_Title,client.Name as Client_Name \n" +
                    "FROM loans INNER JOIN book\n" +
                    "ON book.Id = loans.Book_Id\n" +
                    "INNER JOIN client\n" +
                    "ON client.Id = loans.Client_Id");

            rs = st.executeQuery();
            while(rs.next()){

                Book book = bookMap.get(rs.getInt("Book_Id"));
                Client client = clientMap.get(rs.getInt("Client_Id"));
                if(book == null){
                    book = bookDao.findById(rs.getInt("Book_Id"));
                    bookMap.put(rs.getInt("Book_Id"),book);
                }
                if (client == null) {
                    client = clientDao.findById(rs.getInt("Client_Id"));
                    clientMap.put(rs.getInt("Client_Id"),client);
                }
                Loan loan = instantiateLoan(rs,book,client);
                list.add(loan);
            }
            return list;
        }catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}