package model.entities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    private static String filePath = "D:\\projetos java\\library-system\\src\\application\\resources\\";
    public static List<Genre> readGenres(String fileName) {
        String file = filePath + fileName;
        List<Genre> genres = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line = br.readLine();
            while (line != null){
                Genre genre = new Genre(null,line);
                genres.add(genre);
                line = br.readLine();
            }
            return genres;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Book> readBooks(String fileName) {
        String file = filePath + fileName;
        List<Book> books = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line = br.readLine();
            while(line != null){
                String[] fields = line.split(",");
                Genre genre = new Genre(null, fields[2]);
                Book book = new Book(null,fields[0],fields[1],genre,Integer.parseInt(fields[3]));
                books.add(book);
                line = br.readLine();
            }
            return books;
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Client> readClients(String fileName){
        String file = filePath + fileName;
        List<Client> clients = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line = br.readLine();
            while(line != null) {
                String[] fields = line.split(",");
                Client client = new Client(null, fields[0], fields[1]);
                clients.add(client);
                line = br.readLine();
            }
            return clients;
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

