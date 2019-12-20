package edu.sustech.oj_server;

import edu.sustech.oj_server.moss.Plagiarism;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlagiarismTest {
    @Test
    public void run(){
        Plagiarism plagiarism = new Plagiarism();
        try {
            plagiarism.run(1076);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
