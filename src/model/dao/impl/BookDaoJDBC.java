package model.dao.impl;

import model.dao.BookDao;
import model.entities.Book;
import db.DB;
import db.DbException;
import model.entities.Genre;

import java.sql.*;
import java.util.List;

public class BookDaoJDBC implements BookDao {

    private Connection conn;

    private Genre instantiateGenre(ResultSet rs) throws SQLException {
        Genre obj = new Genre();
        int id = rs.getInt("Id");
        String name = rs.getString("Name");
        obj.setId(id);
        obj.setName(name);
        return obj;
    }

    private Book instantiateBook(ResultSet rs) throws SQLException {
        Book obj = new Book();
        obj.setId(rs.getInt("Id"));
        obj.setTitle(rs.getString("Title"));
        obj.setAuthor(rs.getString("Author"));
        obj.setQuantity(rs.getInt("Quantity"));
        obj.getGenre().setId(rs.getInt("Genre_id"));
        return obj;
    }

    public BookDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Book book) {
        PreparedStatement st = null;

        try{
            st = conn.prepareStatement("INSERT INTO book (Title, Author, Genre_Id, Quantity)\n" +
                    "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            st.setString(1, book.getTitle());
            st.setString(2, book.getAuthor());
            st.setInt(3,book.getGenre().getId());
            st.setInt(4,book.getQuantity());

            int rowsAffected = st.executeUpdate();

            if(rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    book.setId(id);
                }
                DB.closeResultSet(rs);
            }else{
                throw new DbException("Insert failed");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Book book) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void findById(int id) {

    }

    @Override
    public void findByTitle(String title) {

    }

    @Override
    public List<Book> findByAuthor(String author) {
        return List.of();
    }

    @Override
    public List<Book> findByGenre(String genre) {
        return List.of();
    }

    @Override
    public List<Book> findAll() {
        return List.of();
    }
}
