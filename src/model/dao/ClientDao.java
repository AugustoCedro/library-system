package model.dao;

import model.entities.Client;

import java.util.List;

public interface ClientDao {
    List<Client> findAll();
    Client findByEmail(String email);
    List<Client> findByName(String name);
    Client findById(Integer id);
    void insert(Client client);
    void deleteById(Integer id);
    void deleteByEmail(String email);
    void update(Client client);
}
