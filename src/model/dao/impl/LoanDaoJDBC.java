package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.BookDao;
import model.dao.DaoFactory;
import model.dao.LoanDao;
import model.entities.Book;
import model.entities.Loan;

import java.sql.*;

public class LoanDaoJDBC implements LoanDao {
    private static Connection conn = null;

    public LoanDaoJDBC(Connection conn) {
        this.conn = conn;
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
}
