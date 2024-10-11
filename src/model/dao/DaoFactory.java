package model.dao;

import model.dao.impl.BookDaoJDBC;
import model.dao.impl.GenreDaoJDBC;
import db.DB;


public class DaoFactory {
    public static BookDao createBookDao() {
        return new BookDaoJDBC(DB.getConnection());
    }
    public static GenreDao createGenreDao() {
        return new GenreDaoJDBC(DB.getConnection());
    }
}
