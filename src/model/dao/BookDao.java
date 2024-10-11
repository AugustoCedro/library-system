package model.dao;

import model.entities.Book;

import java.util.List;

public interface BookDao {

    void insert(Book book);
    void update(Book book);
    void deleteById(int id);
    void findById(int id);
    void findByTitle(String title);
    List<Book> findByAuthor(String author);
    List<Book> findByGenre(String genre);
    List<Book> findAll();
}
