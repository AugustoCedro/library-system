package model.entities;

import java.io.Serializable;

public class Book implements Serializable {
    private Integer id;
    private String title;
    private String author;
    private Genre genre;
    private Integer quantity;

    public Book() {
    }

    public Book(Integer id, String title, String author, Genre genre, Integer quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre=" + genre +
                ", quantity=" + quantity +
                '}';
    }
}
