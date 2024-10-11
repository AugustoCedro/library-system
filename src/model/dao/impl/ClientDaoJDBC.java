package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.ClientDao;
import model.entities.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoJDBC implements ClientDao {

    private static Connection conn = null;

    public ClientDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    private Client instantiateClient(ResultSet rs) throws SQLException {
        Client obj = new Client();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        return obj;
    }

    @Override
    public void insert(Client client) {
        PreparedStatement st = null;

        try{
            st = conn.prepareStatement("INSERT INTO client (Name,Email) " +
                    "VALUES (?,?)",Statement.RETURN_GENERATED_KEYS);


            st.setString(1,client.getName());
            st.setString(2,client.getEmail());
            int rowsAffected = st.executeUpdate();
            if(rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    client.setId(rs.getInt(1));
                }
                DB.closeResultSet(rs);
            }else{
                throw new DbException("Insert failed");
            }
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Client> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Client> list = new ArrayList<>();
        try{
            st = conn.prepareStatement("SELECT * FROM client");

            rs = st.executeQuery();
            while(rs.next()){
                Client client = instantiateClient(rs);
                list.add(client);
            }
            return list;

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public Client findByEmail(String email) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("SELECT * FROM client " +
                    "WHERE Email = ?");

            st.setString(1,email);

            rs = st.executeQuery();
            if(rs.next()){
                Client client = instantiateClient(rs);
                return client;
            }
            return null;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Client> findByName(String name) {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Client> list = new ArrayList<>();
        try{
            st = conn.prepareStatement("SELECT * FROM client " +
                    "WHERE Name = ?");

            st.setString(1,name);

            rs = st.executeQuery();
            while(rs.next()){
                Client client = instantiateClient(rs);
                list.add(client);
            }
            return list;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public Client findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("SELECT * FROM client " +
                    "WHERE Id = ?");

            st.setInt(1,id);

            rs = st.executeQuery();
            if(rs.next()){
                Client client = instantiateClient(rs);
                return client;
            }
            return null;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;

        try{
            st = conn.prepareStatement("DELETE FROM client " +
                    "WHERE Id = ?");

            st.setInt(1,id);
            st.executeUpdate();

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteByEmail(String email) {
        PreparedStatement st = null;

        try{
            st = conn.prepareStatement("DELETE FROM client " +
                    "WHERE Email = ?");

            st.setString(1,email);
            st.executeUpdate();

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {

            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Client client) {
        PreparedStatement st = null;

        try{
            st = conn.prepareStatement("UPDATE client\n" +
                            "SET Name = ?, Email = ? \n" +
                            "WHERE Id = ?;"
                    );

            st.setString(1,client.getName());
            st.setString(2,client.getEmail());
            st.setInt(3,client.getId());

            st.executeUpdate();

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {

            DB.closeStatement(st);
        }
    }
}
