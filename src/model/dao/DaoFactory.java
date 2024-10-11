package model.dao;

import model.dao.impl.BookDaoJDBC;
import model.dao.impl.ClientDaoJDBC;
import model.dao.impl.GenreDaoJDBC;
import db.DB;
import model.dao.impl.LoanDaoJDBC;


public class DaoFactory {
    public static BookDao createBookDao() {
        return new BookDaoJDBC(DB.getConnection());
    }
    public static GenreDao createGenreDao() {
        return new GenreDaoJDBC(DB.getConnection());
    }
    public static ClientDao createClientDao(){
        return new ClientDaoJDBC(DB.getConnection());
    }
    public static LoanDao createLoanDao(){
        return new LoanDaoJDBC(DB.getConnection());
    }
}
