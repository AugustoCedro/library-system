package model.dao;

import model.entities.Genre;

import java.util.List;

public interface GenreDao {
    void insert(Genre genre);
    void update(Genre genre);
    void deleteById(Integer id);
    Genre findById(Integer id);
    Genre findByName(String name);
    List<Genre> findAll();
}
