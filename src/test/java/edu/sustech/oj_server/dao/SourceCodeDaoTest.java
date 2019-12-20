package edu.sustech.oj_server.dao;

import edu.sustech.oj_server.entity.SourceCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SourceCodeDaoTest {

    @Autowired
    private SourceCodeDao sourceCodeDao;

    @Test
    public void getAcceptedByCTL() {
        ArrayList<SourceCode> sourceCodes = (ArrayList<SourceCode>) sourceCodeDao.getAcceptedByCTL(1076, 1295, 3);
        System.out.println(sourceCodes.size());
    }
}
