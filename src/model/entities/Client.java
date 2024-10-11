package model.entities;

public class Client {
    private Integer id;
    private String name;
    private String email;
    private Integer phoneNumber;
    private Book rentedBook;

    public Client(Integer id, String name, String email, Integer phoneNumber, Book rentedBook) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.rentedBook = rentedBook;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Book getRentedBook() {
        return rentedBook;
    }

    public void setRentedBook(Book rentedBook) {
        this.rentedBook = rentedBook;
    }
}
