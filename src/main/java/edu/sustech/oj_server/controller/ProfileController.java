package edu.sustech.oj_server.controller;

import edu.sustech.oj_server.dao.LoginLogDao;
import edu.sustech.oj_server.dao.UserDao;
import edu.sustech.oj_server.entity.User;
import edu.sustech.oj_server.util.Authentication;
import edu.sustech.oj_server.util.LoginUtil;
import edu.sustech.oj_server.util.ReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Objects;

@RestController
public class ProfileController {
    @Autowired
    LoginLogDao loginLogDao;
    @Autowired
    UserDao userDao;

    class Profile{
        private User user;
        Integer accepted_number;
        Integer submission_number;

        public Profile(User user) {
            this.user = user;
            this.accepted_number=user.getSolved();
            this.submission_number=user.getSubmit();
        }

        public User getUser() {
            return user;
        }

        public Integer getAccepted_number() {
            return accepted_number;
        }

        public Integer getSubmission_number() {
            return submission_number;
        }
    }

    @RequestMapping("/api/profile")
    public ReturnType getprofile(@RequestParam(value = "username", required = false) String id, HttpServletRequest request, HttpServletResponse response) {
        User user = null;
        if (id == null) {
            user = Authentication.getUser(request);
        } else {
            user = userDao.getUser(id);
        }
        if (user == null) {
            return new ReturnType<>(null);
        } else {
            return new ReturnType<>(new Profile(user));
        }
    }

    @PostMapping("/api/change_password")
    public ReturnType changePassword(LinkedHashMap hashMap,HttpServletRequest request){
        String old_password=hashMap.get("old_password").toString();
        String new_password=hashMap.get("new_password").toString();
        User user=Authentication.getUser(request);
        if(user==null||user.getId()==null){
            return new ReturnType("error","Please login first");
        }
        if(LoginUtil.passwordCheck(old_password,userDao.getPassword(user.getId()))){
            userDao.updatePassword(user.getId(),LoginUtil.passwordGen(new_password));
            return new ReturnType(null);
        }
        else{
            return new ReturnType("error","Wrong password");
        }

    }

    @PostMapping("/api/change_email")
    public ReturnType changeEmail(LinkedHashMap hashMap){
        return new ReturnType("error","Not supported");
    }
}
