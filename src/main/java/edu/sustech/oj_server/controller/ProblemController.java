package edu.sustech.oj_server.controller;

import edu.sustech.oj_server.dao.ProblemDao;
import edu.sustech.oj_server.entity.Problem;
import edu.sustech.oj_server.entity.User;
import edu.sustech.oj_server.util.Authentication;
import edu.sustech.oj_server.util.ReturnListType;
import edu.sustech.oj_server.util.ReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProblemController {

    @Autowired
    ProblemDao problemDao;
    @GetMapping("/api/problem")
    public ReturnType<Object> getProblem(@RequestParam(value = "offset",required = false) Integer offset,
                                         @RequestParam(value ="limit",required = false) Integer limit,
                                         @RequestParam(value = "paging",required = false) Boolean paging,
                                         @RequestParam(value = "page",required = false) Integer page,
                                         @RequestParam(value = "problem_id",defaultValue = "0") int id, HttpServletRequest request){
        if(id==0){
            if(limit == null){
                return new ReturnType<>(null);
            }
            User user= Authentication.getUser(request);
            List<Problem> res = null;
            int num=0;
            if(Authentication.isAdministrator(user)){
                res=problemDao.listAllProblemsForAdmin(offset,limit);
                num=problemDao.getNumForAdmin();
            }
            else {
                res = problemDao.listAllProblems(offset, limit);
                num=problemDao.getNum();
            }
            for(var r:res){
                if(user!=null){
                    if(problemDao.ACinProblems(user.getId(),r.getId())>0)
                        r.setMy_status(0);
                    else if(problemDao.Was(user.getId(),r.getId(),null,null)>0){
                        r.setMy_status(-2);
                    }
                }

            }
            return new ReturnType<>(new ReturnListType<>(res,num));
        }
        else{
            var res= problemDao.getProblem(id);
            return new ReturnType<>(res);
        }
    }

    @GetMapping("/api/problem/tags")
    public ReturnType<List> getTags(){

        return new ReturnType<>(new ArrayList());
    }
}
