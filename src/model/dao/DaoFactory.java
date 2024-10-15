package model.dao;

import model.dao.impl.*;
import db.DB;


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
    public static ManagerDao createManagerDao(){
        return new ManagerDaoJDBC(DB.getConnection());
    }
}
