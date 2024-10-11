package model.dao.impl;

import model.dao.GenreDao;
import model.entities.Genre;
import db.DB;
import db.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreDaoJDBC implements GenreDao {
    private Connection conn;

    public GenreDaoJDBC(Connection conn) {
        this.conn = conn;
    }


    private Genre instantiateGenre(ResultSet rs) throws SQLException {
        Genre obj = new Genre();
        int id = rs.getInt("Id");
        String name = rs.getString("Name");
        obj.setId(id);
        obj.setName(name);
        return obj;
    }
    @Override
    public void insert(Genre genre) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("INSERT INTO genre(Name)\n" +
                    "VALUES(?)", Statement.RETURN_GENERATED_KEYS);

            st.setString(1,genre.getName());
            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    genre.setId(id);
                }
                DB.closeResultSet(rs);
            }else{
                throw new DbException("Insert failed");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Genre genre) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("UPDATE genre\n" +
                    "SET Name = ?\n" +
                    "WHERE Id = ?");
            st.setString(1,genre.getName());
            st.setInt(2,genre.getId());

            st.executeUpdate();

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
       PreparedStatement st = null;
       try{
           st = conn.prepareStatement(" DELETE FROM genre\n" +
                   "        WHERE genre.id = ?;");

           st.setInt(1,id);
           st.executeUpdate();
       }
       catch (SQLException e){
            throw new DbException(e.getMessage());
       }finally {
            DB.closeStatement(st);
       }
    }

    @Override
    public Genre findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("SELECT * FROM genre\n" +
                    "WHERE genre.Id = ?");

            st.setInt(1,id);
            rs = st.executeQuery();

            if(rs.next()){
                Genre obj = instantiateGenre(rs);
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
    public Genre findByName(String name) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("SELECT * FROM genre\n" +
                    "WHERE genre.Name = ?");

            st.setString(1,name);
            rs = st.executeQuery();
            if(rs.next()){
                Genre obj = instantiateGenre(rs);
                return obj;
            }
            return null;
        }catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Genre> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Genre> list = new ArrayList<>();
        try{
            st = conn.prepareStatement("SELECT * FROM genre");

            rs = st.executeQuery();

            while(rs.next()){
                list.add(instantiateGenre(rs));
            }
            return list;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }


    }
}
