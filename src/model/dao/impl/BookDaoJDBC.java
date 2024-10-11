package model.dao.impl;

import model.dao.BookDao;
import model.entities.Book;
import db.DB;
import db.DbException;
import model.entities.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookDaoJDBC implements BookDao {

    private Connection conn;

    private Genre instantiateGenre(ResultSet rs) throws SQLException {
        Genre obj = new Genre();
        int id = rs.getInt("Genre_Id");
        String name = rs.getString("Genre");
        obj.setId(id);
        obj.setName(name);
        return obj;
    }

    private Book instantiateBook(ResultSet rs,Genre genre) throws SQLException {
        Book obj = new Book();
        obj.setId(rs.getInt("Id"));
        obj.setTitle(rs.getString("Title"));
        obj.setAuthor(rs.getString("Author"));
        obj.setQuantity(rs.getInt("Quantity"));
        obj.setGenre(genre);
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
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("UPDATE book\n" +
                    "SET Title = ?,\n" +
                    "Author = ?,\n" +
                    "Genre_Id = ? ,\n" +
                    "Quantity = ?\n" +
                    "WHERE Id = ?");

            st.setString(1, book.getTitle());
            st.setString(2,book.getAuthor());
            st.setInt(3,book.getGenre().getId());
            st.setInt(4,book.getQuantity());
            st.setInt(5,book.getId());

            st.executeUpdate();

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(int id) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("DELETE FROM book" +
                    "WHERE Id = ?");


            st.setInt(1, id);


            st.executeUpdate();

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Book findById(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("SELECT FROM book" +
                            "WHERE Id = ?"
            );

            st.setInt(1, id);

            rs = st.executeQuery();
            if(rs.next()){
                Genre genre = instantiateGenre(rs);
                Book obj = instantiateBook(rs,genre);
                return obj;
            }
            return null;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public Book findByTitle(String title) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("SELECT book.*, genre.Name as Genre" +
                    "FROM book INNER JOIN genre" +
                    "ON book.Genre_Id = genre.Id" +
                    "WHERE book.Title = ?"
            );

            st.setString(1, title);

            rs = st.executeQuery();
            if(rs.next()){
                Genre genre = instantiateGenre(rs);
                Book obj = instantiateBook(rs,genre);
                return obj;
            }
            return null;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Book> findByAuthor(String author) {
        List<Book>list = new ArrayList<>();
        Map<Integer, Genre> map = new HashMap<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("SELECT book.*, genre.Name as Genre" +
                    "FROM book INNER JOIN genre" +
                    "ON book.Genre_Id = genre.Id" +
                    "WHERE Author = ?"
            );

            st.setString(1, author);

            rs = st.executeQuery();
            while(rs.next()){
                Genre genre = map.get(rs.getInt("Genre_Id"));
                if(genre == null){
                    genre = instantiateGenre(rs);
                    map.put(rs.getInt("Genre_Id"),genre);
                }
                Book obj = instantiateBook(rs,genre);
                list.add(obj);
            }
            return list;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Book> findByGenre(String name) {
        List<Book>list = new ArrayList<>();
        Map<Integer, Genre> map = new HashMap<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("SELECT book.*, genre.Name as Genre" +
                    "FROM book INNER JOIN genre" +
                    "ON book.Genre_Id = genre.Id" +
                    "WHERE genre.Name = ?"
            );

            st.setString(1, name);

            rs = st.executeQuery();
            while(rs.next()){
                Genre genre = map.get(rs.getInt("Genre_Id"));
                if(genre == null){
                    genre = instantiateGenre(rs);
                    map.put(rs.getInt("Genre_Id"),genre);
                }
                Book obj = instantiateBook(rs,genre);
                list.add(obj);
            }
            return list;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book>list = new ArrayList<>();
        Map<Integer, Genre> map = new HashMap<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("SELECT book.*, genre.Name as Genre" +
                    "FROM book INNER JOIN genre" +
                    "ON book.Genre_Id = genre.Id" +
                    "ORDER BY Title"
            );

            rs = st.executeQuery();
            while(rs.next()){
                Genre genre = map.get(rs.getInt("Genre_Id"));
                if(genre == null){
                    genre = instantiateGenre(rs);
                    map.put(rs.getInt("Genre_Id"),genre);
                }
                Book obj = instantiateBook(rs,genre);
                list.add(obj);
            }
            return list;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
