package edu.sustech.oj_server.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimDaoTest {
    @Autowired
    private SimDao simDao;
    @Test
    public void getSimList() {
        System.out.println(simDao.getSimList(1076).size());
    }
}
