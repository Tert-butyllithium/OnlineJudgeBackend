package edu.sustech.oj_server.controller;

import edu.sustech.oj_server.dao.UserDao;
import edu.sustech.oj_server.entity.User;
import edu.sustech.oj_server.util.ReturnListType;
import edu.sustech.oj_server.util.ReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RankController {
    class UserInRank{
        User user;
        String id;
        Integer accepted_number;
        Integer submission_number;
        String mood;

        public UserInRank(User user) {
            this.user=user;
            this.id=user.getId();
            this.accepted_number=user.getSolved();
            this.submission_number=user.getSubmit();
            this.mood=user.getUsername();
            this.user.setUsername(this.id);
        }

        public User getUser() {
            return user;
        }

        public String getId() {
            return id;
        }

        public Integer getAccepted_number() {
            return accepted_number;
        }

        public Integer getSubmission_number() {
            return submission_number;
        }

        public String getMood() {
            return mood;
        }

    }

    @Autowired
     private UserDao userDao;

    @GetMapping("/api/user_rank")
    public ReturnType getRank(@RequestParam("offset") Integer offset,@RequestParam("limit") Integer limit){
        var list=userDao.getRank(offset,limit);
        List<UserInRank> arrayList=new ArrayList<>();
//        System.out.println(arrayList);
        for(var e:list){
            arrayList.add(new UserInRank(e));
        }
        return new ReturnType(new ReturnListType<>(arrayList,userDao.getUserNumber()));
    }
}
