package model.dao;

import model.entities.Client;

import java.util.List;

public interface ClientDao {
    List<Client> findAll();
    void insert(Client client);
}
