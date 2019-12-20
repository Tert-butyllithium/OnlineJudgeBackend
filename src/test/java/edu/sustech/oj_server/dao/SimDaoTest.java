package edu.sustech.oj_server.dao;

import edu.sustech.oj_server.entity.Sim;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimDaoTest {
    @Autowired
    private SimDao simDao;

    @Test
    public void getSimList() {
        List<Sim> simList =simDao.getSimList(1076);
        simList.forEach(System.out::println);
    }

    @Test
    public void getSim() {

    }
}
