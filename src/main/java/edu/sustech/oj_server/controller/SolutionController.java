package edu.sustech.oj_server.controller;

import edu.sustech.oj_server.dao.BalloonDao;
import edu.sustech.oj_server.dao.LoginLogDao;
import edu.sustech.oj_server.dao.SolutionDao;
import edu.sustech.oj_server.entity.*;
import edu.sustech.oj_server.util.Authentication;
import edu.sustech.oj_server.util.HUSTToQDU;
import edu.sustech.oj_server.util.ReturnListType;
import edu.sustech.oj_server.util.ReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
public class SolutionController {

    @Autowired
    SolutionDao solutionDao;
    @Autowired
    LoginLogDao loginLogDao;

    @RequestMapping("/api/submissions")
    public ReturnType<ReturnListType<Solution>> getSolutions( @RequestParam(value = "myself") Integer myself,
                                                             @RequestParam(value = "result",required = false) Integer result,
                                                             @RequestParam(value = "username",required = false) String username,
                                                              @RequestParam(value = "page",required = false)Integer page,
                                                              @RequestParam(value = "problem_id",required = false)Integer problem_id,
                                                              @RequestParam(value = "limit")Integer limit,
                                                              @RequestParam(value = "offset")Integer offset,
                                                             HttpServletRequest request
    ){
        String myname=null;
        User user= Authentication.getUser(request);
        boolean isAdmin = Authentication.isAdministrator(user);
        if(user!=null){
            myname=user.getId();
        }
        if(myself!=null&&myself.equals(1)){
            username=myname;
            if(username==null){
                return new ReturnType<>(null);
            }
        }
        if(result!=null){
            result= HUSTToQDU.translateStatusInverse(result);
        }
        if(username==null||username.equals("")) username=null;
        var res=solutionDao.getSolutionsBy(username,result,problem_id,limit,offset);
        for(Solution sol:res){
            if(isAdmin||sol.getUsername().equals(myname)){
                sol.setShow_link(true);
            }
        }
        return new ReturnType<>(new ReturnListType<>(res,solutionDao.getNum(username,result,problem_id)));
    }

    @RequestMapping("/api/contest_submissions")
    public ReturnType<ReturnListType<Solution>> getContestSolutions(@RequestParam(value = "myself") Integer myself,
            @RequestParam(value = "result",required = false) Integer result,
            @RequestParam(value = "username",required = false) String username,
            @RequestParam(value = "page",required = false)Integer page,
            @RequestParam(value = "problem_id",required = false)String problem_id,
            @RequestParam(value ="contest_id") Integer contest_id,
            @RequestParam(value = "limit")Integer limit,
            @RequestParam(value = "offset")Integer offset,
            HttpServletRequest request
    ){
            String myname=null;
            User user= Authentication.getUser(request);
            boolean isAdmin = Authentication.isAdministrator(user);
            if(user!=null){
                myname=user.getId();
            }
            if(myself!=null&&myself.equals(1)){
                username=myname;
                if(username==null){
                    return new ReturnType<>(null);
                }
            }
            if(result!=null){
                result= HUSTToQDU.translateStatusInverse(result);
            }
            if(username==null||username.equals("")) username=null;
            Integer real_num=null;
            if(problem_id!=null)
                real_num=problem_id.charAt(0)-'A';
            var res =solutionDao.getSolutionsInContestBy(username,result,real_num,contest_id,limit,offset);
            for(Solution sol:res){
                if(isAdmin||sol.getUsername().equals(myname)){
                    sol.setShow_link(true);
                }
                char tmp=sol.getProblem().charAt(0);
                sol.setProblem(String.valueOf((char)(tmp-'0'+'A')));
            }
            return new ReturnType<>(new ReturnListType<>(res,solutionDao.getNumInContest(username,result,real_num,contest_id)));

    }

}
