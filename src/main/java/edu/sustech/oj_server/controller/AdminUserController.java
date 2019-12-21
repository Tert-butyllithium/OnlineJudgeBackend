package edu.sustech.oj_server.controller;

import edu.sustech.oj_server.dao.UserDao;
import edu.sustech.oj_server.entity.User;
import edu.sustech.oj_server.util.Authentication;
import edu.sustech.oj_server.util.LoginUtil;
import edu.sustech.oj_server.util.MailServer;
import edu.sustech.oj_server.util.ReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
public class AdminUserController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private MailServer mailServer;

    @PostMapping("/api/admin/user")
    public ReturnType importUser(@RequestBody LinkedHashMap body, HttpServletRequest request){
        User user = Authentication.getUser(request);
        boolean admin = Authentication.isAdministrator(user);
        if (user == null || !admin) {
            return new ReturnType("login-required", "Please login in first");
        }
        var list=new ArrayList<>(body.values());
        System.out.println(list.get(0));
        try {
            importUser((List<List>) list.get(0));
        }catch (Exception e){
            e.printStackTrace();
            return new ReturnType("error",e.getMessage());
        }

        return new ReturnType(null);
    }

    private String registerSubject="Subject";

    private String endOfMail="Lanranforces";

    private void importUser(List<List> list) throws InterruptedException {
        for(var l:list){
            if(userDao.exist(l.get(0).toString())>0){
                continue;
            }
            // to, subject, text
            String text="";
//            mailServer.sendEmail(l.get(2).toString(),registerSubject,text);
//            Thread.sleep(1000);
            // id, password,email
            userDao.insert(l.get(0).toString(), LoginUtil.passwordGen(l.get(1).toString()),l.get(2).toString(),true);

        }
    }
}
