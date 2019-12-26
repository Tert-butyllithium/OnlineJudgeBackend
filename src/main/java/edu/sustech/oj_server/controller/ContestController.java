package edu.sustech.oj_server.controller;

import edu.sustech.oj_server.dao.ContestDao;
import edu.sustech.oj_server.dao.PrivilegeDao;
import edu.sustech.oj_server.dao.ProblemDao;
import edu.sustech.oj_server.entity.Contest;
import edu.sustech.oj_server.entity.Problem;
import edu.sustech.oj_server.entity.User;
import edu.sustech.oj_server.util.Authentication;
import edu.sustech.oj_server.util.ReturnListType;
import edu.sustech.oj_server.util.ReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.sql.Timestamp;
import java.util.*;

@RestController
public class ContestController {

    @Autowired
    ContestDao contestDao;
    @Autowired
    ProblemDao problemDao;
    @Autowired
    PrivilegeDao privilegeDao;

    @RequestMapping("/api/contests")
    public ReturnType<ReturnListType<Contest>> list_contest(@RequestParam("limit") int limit,
                                                            @RequestParam("offset") int offset,
                                                            HttpServletRequest request){
        User user= Authentication.getUser(request);
        if((!Authentication.isAdministrator(user))) {
            var res = contestDao.listAllVisibleContest(offset, limit);
            return new ReturnType<>(new ReturnListType<Contest>(res, contestDao.getVisibleNum()));
        }
        else{
            var res = contestDao.listAllContest(offset, limit);
            return new ReturnType<>(new ReturnListType<Contest>(res, contestDao.getNum()));
        }
    }

    @RequestMapping("/api/contest/problem")
    public ReturnType<Object> listProblems(@RequestParam(value = "contest_id",required = false) Integer contest_id,
                                           @RequestParam(value = "problem_id",required = false) String problem_id, HttpServletRequest request){
        User user= Authentication.getUser(request);
        if(problem_id==null){
            if(contest_id==null){
                return new ReturnType<>("error","No such contest");
            }
            var res=contestDao.getProblemsID(contest_id);
            if(res==null){
                return new ReturnType<>("error","No such contest");
            }
            List<Problem> problems=new ArrayList<>();
            for(int i=0;i<res.size();i++){
                var p=problemDao.getProblem(res.get(i));
                Integer frozen=contestDao.getFrozen(contest_id);
                if(frozen==null) {
                    p.setAccepted_number(problemDao.getProblemACinContest(res.get(i), contest_id));
                    problemDao.updateProblem(p);
                }
                p.setSubmission_number(problemDao.getProblemSubmissionInContest(res.get(i),contest_id));
                p.set_id(String.valueOf((char)('A'+i)));
                if(user!=null){
                    if(problemDao.ACinContest(user.getId(),p.getId(),contest_id)>0){
                        p.setMy_status(0);
                    }
                    else if(problemDao.Was(user.getId(),p.getId(),null,contest_id)>0){
                        p.setMy_status(-2);
                    }
                }

                problems.add(p);
            }
            return new ReturnType<>(problems);
        }
        else{
            int num=problem_id.charAt(0)-'A';
//            var res=problemDao.getProblemInContest(contest_id,num);
            var list=contestDao.getProblemsID(contest_id);
            var res=problemDao.getProblem(list.get(num));
            Contest contest=contestDao.getContest(contest_id);
            var now=new Timestamp(System.currentTimeMillis());
            if(res==null||now.before(contest.getStart_time())){
                return new ReturnType<>("error","No such problem");
            }
            res.setTime_limit(res.getTime_limit());
            Objects.requireNonNull(res.getSamples()).addAll(problemDao.getExtraSamples(res.getId()));
//            res.setId();
            res.set_id(problem_id);
            return new ReturnType<>(res);
        }
    }

    @RequestMapping("/api/contest")
    public ReturnType getContest(@RequestParam("id") int id, HttpServletRequest request){
        User user= Authentication.getUser(request);
        var res=contestDao.getContest(id);
        if(!Authentication.isAdministrator(user)){
            if(res==null||res.getPrivate()!=0){
                return new ReturnType<>("error","No such contest");
            }
        }
        return new ReturnType<>(res);
    }

    @GetMapping("/api/contest/access")
    public ReturnType canAccess(@RequestParam("contest_id")int id,HttpServletRequest request){
        User user=Authentication.getUser(request);
        var res=new LinkedHashMap<>();
        if (user != null && user.getId() != null) {
            if(privilegeDao.getContestAccess(user.getId(),Integer.toString(id))>0){
                res.put("access",true);
            }
            else{
                res.put("access",false);
            }
        } else {
            res.put("access",false);
        }
        return new ReturnType(res);
    }

    @PostMapping("/api/contest/password")
    public ReturnType verifyPassword(@RequestBody LinkedHashMap map,HttpServletRequest request){
        User user=Authentication.getUser(request);
        if(user==null||user.getId()==null){
            return new ReturnType("error","Please login first");
        }
        try {
            Integer contest_id = Integer.parseInt(map.get("contest_id").toString());
            String password = map.get("password").toString();
            if(Objects.equals(password,privilegeDao.getContestAccess(user.getId(),Integer.toString(contest_id)))){
                return new ReturnType(Map.of("access",true));
            }
            else{
                return new ReturnType(Map.of("access",false));
            }
        }catch (Exception e){
            return new ReturnType("error",e.getMessage());
        }
    }

}
