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
        var list=new ArrayList<>(body.values());;
        System.out.println(list.get(0));
        try {
            importUser((List<List>) list.get(0));
        }catch (Exception e){
            return new ReturnType("error",e.getMessage());
        }

        return new ReturnType(null);
    }

    private String registerSubject="南方科技大学程序设计竞赛网络赛账号";

    private String endOfMail="Lanranforces";

    private void importUser(List<List> list){
        for(var l:list){
            // to, subject, text
            String text="同学你好！\n" +
                    "    欢迎参加第二届南方科技大学程序设计竞赛，网络赛将于12月7日举行，比赛采用的OJ：https://ac.lanran.club/ (可以校内访问，也可以在校外使用ipv6访问，" +
                    "或者可能更慢的https://lanran.club/)，你的账号为："+ l.get(0) +"，密码为："+ l.get(1) +"，请妥善保管。\n" +
                    "    《数据结构与算法分析》班上的学生参与网络赛根据通过的题数有额外加分，了解详情请进qq群376962191。\n" +
                    "    网络赛会进行查重，请自觉遵守比赛规定，祝你在网络赛中取得好成绩！\n" +
                    "\n" +
                    "祝好\n" +
                    "南方科技大学程序设计竞赛出题组";
            mailServer.sendEmail(l.get(2).toString(),registerSubject,text);
            // id, password,email
            userDao.insert(l.get(0).toString(), LoginUtil.passwordGen(l.get(1).toString()),l.get(2).toString());
        }
    }
}
