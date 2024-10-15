package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.ManagerDao;
import model.entities.Manager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManagerDaoJDBC implements ManagerDao {
    private static Connection conn;

    public ManagerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    private Manager instantiateManager(ResultSet rs) throws SQLException {
        Manager obj = new Manager();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setPhone(rs.getString("Phone"));
        obj.setPassword(rs.getString("Password"));
        return obj;
    }

    @Override
    public void insert(Manager manager) {
        PreparedStatement st = null;

        try{
            st = conn.prepareStatement("INSERT INTO manager " +
                    "(Name,Email,Phone,Password) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            st.setString(1,manager.getName());
            st.setString(2,manager.getEmail());
            st.setString(3,manager.getPhone());
            st.setString(4, manager.getPassword());

            int rowsAffected = st.executeUpdate();
            if(rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    manager.setId(id);
                }
                DB.closeResultSet(rs);
            }else{
                throw new DbException("ERROR : Insert Failed");
            }
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
            st = conn.prepareStatement("DELETE FROM manager " +
                    "WHERE Id = ?");

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
    public void deleteByEmail(String email) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("DELETE FROM manager " +
                    "WHERE Email = ?");

            st.setString(1,email);

            st.executeUpdate();
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Manager manager) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("UPDATE manager " +
                    "SET Name = ?, " +
                    "Email = ?," +
                    "Phone = ?," +
                    "Password = ?" +
                    "WHERE Id = ?");

            st.setString(1,manager.getName());
            st.setString(2,manager.getEmail());
            st.setString(3,manager.getPhone());
            st.setString(4, manager.getPassword());
            st.setInt(5,manager.getId());

            st.executeUpdate();

        } catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Manager> findAll() {
        List<Manager> list = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("SELECT * FROM manager");

            rs = st.executeQuery();
            while(rs.next()){
                Manager manager = instantiateManager(rs);
                list.add(manager);
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
    public Manager findByEmail(String email) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("SELECT * FROM manager " +
                    "WHERE Email = ?");

            st.setString(1,email);
            rs = st.executeQuery();
            if(rs.next()){
                Manager manager = instantiateManager(rs);
                return manager;
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
    public Manager findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("SELECT * FROM manager " +
                    "WHERE Id = ?");

            st.setInt(1,id);
            rs = st.executeQuery();
            if(rs.next()){
                Manager manager = instantiateManager(rs);
                return manager;
            }
            return null;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
