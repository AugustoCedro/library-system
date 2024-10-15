package model.dao;

import model.entities.Manager;

import java.io.Serializable;
import java.util.List;

public interface ManagerDao {
    void insert(Manager manager);
    void deleteById(Integer id);
    void deleteByEmail(String email);
    void update(Manager manager);
    List<Manager> findAll();
    Manager findByEmail(String email);
    Manager findById(Integer id);

}
