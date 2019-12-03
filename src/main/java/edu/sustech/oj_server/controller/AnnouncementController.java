package edu.sustech.oj_server.controller;

import edu.sustech.oj_server.dao.NewsDao;
import edu.sustech.oj_server.entity.News;
import edu.sustech.oj_server.util.ReturnListType;
import edu.sustech.oj_server.util.ReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AnnouncementController {

    @Autowired
    private NewsDao newsDao;

    @GetMapping("/api/announcement")
    public ReturnType<ReturnListType<News>> getAnnouncement(@RequestParam(value = "contest_id",required = false) Integer contest_id){
        if(contest_id==null) {
            List<News> tmp = newsDao.listAllVisibleNews();
            ArrayList<News> list = new ArrayList<>(tmp);
            return new ReturnType<>(new ReturnListType<>(list, list.size()));
        }
        else{
            return new ReturnType<>(null);
        }
    }

}
