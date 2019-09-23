package edu.sustech.oj_server.util;

import edu.sustech.oj_server.dao.LoginLogDao;
import edu.sustech.oj_server.dao.PrivilegeDao;
import edu.sustech.oj_server.dao.UserDao;
import edu.sustech.oj_server.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
public final class Authentication {

    @Autowired
    private LoginLogDao Dao;
    @Autowired
    private PrivilegeDao dao2;

    private static LoginLogDao loginLogDao;
    private static PrivilegeDao privilegeDao;

    @PostConstruct
    public void init() {
        loginLogDao = Dao;
        privilegeDao =dao2;
    }


    public static boolean isAdministrator(User user){
//        User user=getUser(request);
        if(user==null){
            return false;
        }
        int res=privilegeDao.getPrivilege(user.getId());
        return res>=1;
    }
    public static boolean isAdministrator(String userid){
//        User user=getUser(request);
        int res=privilegeDao.getPrivilege(userid);
        return res==1;
    }

    public static User getUser(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        Cookie sessionId=null;
        User user=null;
        if(cookies==null){
            return null;
        }
        for(Cookie c:cookies){
            if(c.getName().equals("sessionid")){
                sessionId = c;
                break;
            }
        }
        if(sessionId!=null){
            user=loginLogDao.findUser(sessionId.getValue());
            return user;
        }
        return null;
    }

}
