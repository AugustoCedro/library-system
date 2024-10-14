package test;

import model.dao.ClientDao;
import model.dao.DaoFactory;
import model.entities.CSVReader;
import model.entities.Client;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ClientDaoJDBCTest  {
    private static ClientDao clientDao = DaoFactory.createClientDao();

    @Test
    public void insert(){
        Client c = clientDao.findById(1);
        c.setEmail("test@gmail.com");
        clientDao.insert(c);
        assertEquals("test@gmail.com",clientDao.findById(c.getId()).getEmail());
        clientDao.deleteByEmail(c.getEmail());
    }
    @Test
    public void findByEmail(){
        assertEquals("ana.silva.souza@example.com",clientDao.findByEmail("ana.silva.souza@example.com").getEmail());
    }

    @Test
    public void findById(){
        Client c = clientDao.findById(1);
        assertEquals("ana.silva.souza@example.com",c.getEmail());
    }

    @Test
    public void findByName(){
        List<Client> list = clientDao.findByName("Lucas Almeida");
        assertEquals("Lucas Almeida",list.get(0).getName());
    }

    @Test
    public void deleteById(){
        Client c = clientDao.findById(1);
        c.setEmail("test@gmail.com");
        clientDao.insert(c);
        clientDao.deleteByEmail("test@gmail.com");
        assertEquals(null,clientDao.findByEmail("test@gmail.com"));
    }

    @Test
    public void deleteByEmail(){
        Client c = clientDao.findById(1);
        c.setEmail("test@gmail.com");
        clientDao.insert(c);
        clientDao.deleteByEmail("test@gmail.com");
        assertEquals(null,clientDao.findByEmail("test@gmail.com"));
    }

    @Test
    public void update(){
        Client c = clientDao.findById(1);
        c.setEmail("test@gmail.com");
        clientDao.insert(c);
        c.setName("Teste");
        clientDao.update(c);
        assertEquals("Teste",clientDao.findById(c.getId()).getName());
        clientDao.deleteByEmail("test@gmail.com");
    }

    @Test
    public void findAll(){
        List<Client> list = clientDao.findAll();
        assertEquals(10,list.size());
    }


}
