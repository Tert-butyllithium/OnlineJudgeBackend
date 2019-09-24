package edu.sustech.oj_server.controller;

import edu.sustech.oj_server.dao.LoginLogDao;
import edu.sustech.oj_server.dao.UserDao;
import edu.sustech.oj_server.entity.User;
import edu.sustech.oj_server.util.Authentication;
import edu.sustech.oj_server.utilclass.UserProfile;
import edu.sustech.oj_server.util.ReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
}
