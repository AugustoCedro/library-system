package test;

import model.dao.DaoFactory;
import model.dao.ManagerDao;
import model.entities.Manager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ManagerDaoTest {

    private static ManagerDao managerDao = DaoFactory.createManagerDao();
    private static Manager managerTest = new Manager(null,"teste","teste@gmail.com","21940931245","12345");

    @Test
    public void findByEmail(){
       assertEquals("augusto@gmail.com",managerDao.findByEmail("augusto@gmail.com").getEmail());
    }
    @Test
    public void findById(){
        assertEquals("augusto@gmail.com",managerDao.findById(1).getEmail());
    }
    @Test
    public void findAll(){
        List<Manager> list = new ArrayList<>();
         list = managerDao.findAll();
        assertEquals(list.size(), managerDao.findAll().size());
    }
    @Test
    public void insert(){
        managerDao.insert(managerTest);
        assertEquals("teste@gmail.com",managerDao.findByEmail("teste@gmail.com").getEmail());
        managerDao.deleteById(managerTest.getId());
    }
    @Test
    public void deleteById(){
        managerDao.insert(managerTest);
        List<Manager> list = new ArrayList<>();
        list = managerDao.findAll();
        managerDao.deleteById(managerTest.getId());
        assertEquals(list.size() - 1,managerDao.findAll().size());

    }
    @Test
    public void deleteByEmail(){
        managerDao.insert(managerTest);
        List<Manager> list = new ArrayList<>();
        list = managerDao.findAll();
        managerDao.deleteByEmail(managerTest.getEmail());
        assertEquals(list.size() - 1,managerDao.findAll().size());

    }
    @Test
    public void update(){
        managerDao.insert(managerTest);
        Manager managerUpdate = managerTest;
        managerUpdate.setName("testeUpdate");
        managerUpdate.setEmail("testeUpdate@gmail.com");
        managerDao.update(managerUpdate);
        assertEquals("testeUpdate@gmail.com",managerDao.findByEmail("testeUpdate@gmail.com").getEmail());
        managerDao.deleteById(managerUpdate.getId());
    }
}
