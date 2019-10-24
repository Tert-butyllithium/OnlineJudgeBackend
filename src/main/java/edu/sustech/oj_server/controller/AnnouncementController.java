package edu.sustech.oj_server.controller;

import edu.sustech.oj_server.dao.NewsDao;
import edu.sustech.oj_server.entity.News;
import edu.sustech.oj_server.util.ReturnListType;
import edu.sustech.oj_server.util.ReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AnnouncementController {

    @Autowired
    private NewsDao newsDao;
    private static final News disclaimer=new News(0,"root","Lanranforces复制了SUSTech OJ的数据库，但是二者并不互通",
            "测试版本，不做任何保证",Timestamp.valueOf("2019-10-11 14:29:14.950009"),0,"N");
    @GetMapping("/api/announcement")
    public ReturnType<ReturnListType<News>> getAnnouncement(){
        ArrayList<News> list=new ArrayList<>();
        list.add(disclaimer);
        List<News> tmp=newsDao.listAllVisibleNews();
        list.addAll(tmp);
        return new ReturnType<>(new ReturnListType<>(list, list.size()));
    }
}
