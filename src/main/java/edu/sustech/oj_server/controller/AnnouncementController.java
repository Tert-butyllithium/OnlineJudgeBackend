package edu.sustech.oj_server.controller;

import edu.sustech.oj_server.dao.ContestDao;
import edu.sustech.oj_server.dao.NewsDao;
import edu.sustech.oj_server.entity.Contest;
import edu.sustech.oj_server.entity.News;
import edu.sustech.oj_server.entity.User;
import edu.sustech.oj_server.util.Authentication;
import edu.sustech.oj_server.util.ReturnListType;
import edu.sustech.oj_server.util.ReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
public class AnnouncementController {

    @Autowired
    private NewsDao newsDao;
    @Autowired
    private ContestDao contestDao;


    @GetMapping("/api/announcement")
    public ReturnType<?> getAnnouncement(@RequestParam(value = "contest_id",required = false) Integer contest_id){
        if(contest_id==null) {
            List<News> tmp = newsDao.listAllVisibleNews();
            ArrayList<News> list = new ArrayList<>(tmp);
            return new ReturnType<>(new ReturnListType<>(list, list.size()));
        }
        else{
            List<News> tmp = newsDao.listAllVisibleNewsForContest(contest_id);
            ArrayList<News> list = new ArrayList<>(tmp);
            return new ReturnType<>(list);
        }
    }

    @GetMapping("/api/contest/announcement")
    public ReturnType<?> getContestAnnouncement(@RequestParam(value = "contest_id",required = false) Integer contest_id){
        return getAnnouncement(contest_id);
    }

    @GetMapping("/api/contest/clarification")
    public ReturnType<?> getContestClarification(@RequestParam("contest_id") Integer contest_id, HttpServletRequest request){
        User user= Authentication.getUser(request);
        if(user==null){
            return new ReturnType<>(new ArrayList<>());
        }
        return new ReturnType<>(newsDao.listClarification(contest_id,user.getId()));
    }

    @PostMapping("/api/contest/clarification")
    public ReturnType<?> sendClarification(@RequestBody LinkedHashMap map, HttpServletRequest request){
        Integer contest_id=Integer.parseInt(map.get("contest_id").toString());
        String message = map.get("message").toString();
        User user=Authentication.getUser(request);
        if(user==null){
            return new ReturnType<>("error","Please login first");
        }
        Timestamp now=new Timestamp(System.currentTimeMillis());
        Contest contest=contestDao.getContest(contest_id);
        if(contest==null|| !contest.getVisible()){
            return new ReturnType<>("error","No such contest");
        }
        if(now.after(contest.getEnd_time())||now.before(contest.getStart_time())){
            return new ReturnType<>("error","error");
        }
        newsDao.insertClarification(user.getId(),contest_id,message);
        return new ReturnType<>(null);
    }
}
