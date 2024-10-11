package test;

import model.dao.DaoFactory;
import model.dao.GenreDao;
import model.entities.CSVReader;
import model.entities.Genre;
import org.junit.Test;


import java.util.List;

import static org.junit.Assert.assertEquals;

public class GenreDaoJDBCTest {

    private static GenreDao genreDao = DaoFactory.createGenreDao();

    @Test
    public void findAll(){
        List<Genre> list = CSVReader.readGenres("genres.csv");
        assertEquals(list.size(),genreDao.findAll().size());
    }
    @Test
    public void findById(){;
        assertEquals("Historical",genreDao.findById(7).getName());
    }
    @Test
    public void findByName(){
        assertEquals("Horror",genreDao.findByName("Horror").getName());
    }
    @Test
    public void deleteById(){
        Genre test = new Genre(null,"Teste");
        genreDao.insert(test);
        int id = genreDao.findByName("Teste").getId();
        genreDao.deleteById(id);
        test = genreDao.findByName("Teste");
        assertEquals(null,test);
    }
    @Test
    public void insert(){
        Genre test = new Genre(null,"Teste");
        genreDao.insert(test);
        assertEquals("Teste",genreDao.findByName("Teste").getName());
        int id = genreDao.findByName("Teste").getId();
        genreDao.deleteById(id);
    }
    @Test
    public void update(){
        Genre test = new Genre(null,"Teste");
        genreDao.insert(test);
        test = genreDao.findByName("Teste");
        test.setName("TesteUpdate");
        genreDao.update(test);
        assertEquals("TesteUpdate",genreDao.findByName("TesteUpdate").getName());
        int id = genreDao.findByName("TesteUpdate").getId();
        genreDao.deleteById(id);
    }

}
