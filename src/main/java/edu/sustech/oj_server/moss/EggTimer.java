package edu.sustech.oj_server.moss;


import edu.sustech.oj_server.dao.ContestDao;
import edu.sustech.oj_server.dao.SimDao;
import edu.sustech.oj_server.entity.Contest;
import edu.sustech.oj_server.entity.Sim;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class EggTimer implements Job {
    @Autowired
    private SimDao simDao;

    @Autowired
    private ContestDao contestDao;

    private static SimDao sd;
    private static ContestDao cd;

    @PostConstruct
    void init(){
        sd=simDao;
        cd=contestDao;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<Contest> contestList= cd.listAll();
        for (Contest contest : contestList){
            List<Sim> simList = sd.getSimList(contest.getContest_id());
            if (simList.isEmpty()){
                Plagiarism plagiarism = new Plagiarism();
                try {
                    plagiarism.run(contest.getContest_id());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()));
    }
}