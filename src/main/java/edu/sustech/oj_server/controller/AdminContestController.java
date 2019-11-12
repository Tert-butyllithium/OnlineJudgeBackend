package edu.sustech.oj_server.controller;

import edu.sustech.oj_server.dao.BalloonDao;
import edu.sustech.oj_server.entity.User;
import edu.sustech.oj_server.util.Authentication;
import edu.sustech.oj_server.util.ReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/admin/contest")
public class AdminContestController {
    @Autowired
    private BalloonDao balloonDao;

    @GetMapping("acm_helper")
    public ReturnType listBalloons(@RequestParam("contest_id") int contest_id, HttpServletRequest request){
        User user= Authentication.getUser(request);
        boolean admin=Authentication.isAdministrator(user);
        if(user==null||!admin){
            return new ReturnType("login-required","Please login in first");
        }
        return new ReturnType(balloonDao.listAllBalloons(contest_id));
    }


}
