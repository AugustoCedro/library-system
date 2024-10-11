package test;

import model.dao.ClientDao;
import model.dao.DaoFactory;
import model.entities.CSVReader;
import model.entities.Client;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ClientDaoJDBCTest {
    private static ClientDao clientDao = DaoFactory.createClientDao();

    @Test
    public void insert(){
//        List<Client> clientList = CSVReader.readClients("clients.csv");
//        for(Client c : clientList){
//            clientDao.insert(c);
//        }
//        assertEquals(clientList.size(),clientDao.findAll().size());
    }
    @Test
    public void findByEmail(){
        assertEquals("ana.silva@example.com",clientDao.findByEmail("ana.silva@example.com").getEmail());
    }
}
