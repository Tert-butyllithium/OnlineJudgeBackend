package edu.sustech.oj_server.controller;

import edu.sustech.oj_server.dao.LoginLogDao;
import edu.sustech.oj_server.entity.User;
import edu.sustech.oj_server.util.Authentication;
import edu.sustech.oj_server.utilclass.UserProfile;
import edu.sustech.oj_server.util.ReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ProfileController {
    @Autowired
    LoginLogDao loginLogDao;
//    @Autowired
//    UserDao userDao;

    @RequestMapping("/api/profile")
    public ReturnType<UserProfile> getprofile(HttpServletRequest request, HttpServletResponse response){
//        if(sessionId==null)
//            return new ReturnType<>(null);
//        else{
//            User user=loginLogDao.findUser(sessionId.getValue());
//            if(user == null){
//                return new ReturnType<>(null);
//            }
//            else{
//                return new ReturnType<>(new UserProfile(user));
//            }
//        }
        User user= Authentication.getUser(request);
        if(user==null){
            return new ReturnType<>(null);
        }
        else{
            return new ReturnType<>(new UserProfile(user));
        }
    }
}
