package edu.sustech.oj_server.controller;

import edu.sustech.oj_server.dao.LoginLogDao;
import edu.sustech.oj_server.dao.UserDao;
import edu.sustech.oj_server.util.LoginUtil;
import edu.sustech.oj_server.util.ReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginController {

    @PostMapping("/api/tfa_required")
    public String tfaRequired(String username){
        return "{\n" +
                "    \"error\": null,\n" +
                "    \"data\": {\n" +
                "        \"result\": false\n" +
                "    }\n" +
                "}";
    }

    @Autowired
    UserDao userDao;
    @Autowired
    LoginLogDao loginLogDao;

    static class tmpPerson{
        String username;
        String password;

        public tmpPerson(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    @PostMapping("/api/login")
    public ReturnType<String> login(@RequestBody tmpPerson person,HttpServletResponse response){
        String saved=userDao.getPassword(person.username);
        if(saved == null){
            return new ReturnType<>("error","Invalid username");
        }
        boolean res= LoginUtil.passwordCheck(person.password,saved);
        if(res){
            String sessionId=LoginUtil.passwordGen(person.password);
            Cookie session = new Cookie("sessionid",sessionId);
            session.setMaxAge(24*60*60);
            loginLogDao.insert(person.username,sessionId);
            response.addCookie(session);
            return new ReturnType<>("Succeeded");
        }
        else {
            return new ReturnType<>("error", "Invalid password");
        }
    }

    @GetMapping("/api/logout")
    public ReturnType logout(HttpServletResponse response){
        Cookie session=new Cookie("sessionid",null);
        session.setMaxAge(0);
        response.addCookie(session);
        return new ReturnType(null);
    }


}
