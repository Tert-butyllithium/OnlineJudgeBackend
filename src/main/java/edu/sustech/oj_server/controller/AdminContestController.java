package edu.sustech.oj_server.controller;

import edu.sustech.oj_server.dao.BalloonDao;
import edu.sustech.oj_server.dao.ContestDao;
import edu.sustech.oj_server.dao.ProblemDao;
import edu.sustech.oj_server.entity.Balloon;
import edu.sustech.oj_server.entity.Contest;
import edu.sustech.oj_server.entity.Problem;
import edu.sustech.oj_server.entity.User;
import edu.sustech.oj_server.toolclass.ProblemInContest;
import edu.sustech.oj_server.toolclass.Status;
import edu.sustech.oj_server.util.Authentication;
import edu.sustech.oj_server.util.ReturnListType;
import edu.sustech.oj_server.util.ReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/admin/contest")
public class AdminContestController {
    @Autowired
    private BalloonDao balloonDao;
    @Autowired
    private ContestDao contestDao;
    @Autowired
    private ProblemDao problemDao;

    private final CachedRank cachedRank;

    public AdminContestController(CachedRank cachedRank) {
        this.cachedRank = cachedRank;
    }

    @GetMapping("acm_helper")
    public ReturnType listBalloons(@RequestParam("contest_id") int contest_id, HttpServletRequest request) {
        User user = Authentication.getUser(request);
        boolean admin = Authentication.isAdministrator(user);
        if (user == null || !admin) {
            return new ReturnType("login-required", "Please login in first");
        }
        Integer frozen=contestDao.getFrozen(contest_id);
        if(frozen==null){
            return new ReturnType("error","contest type not support");
        }

        var problem_list=contestDao.getProblemsID(contest_id);
        var userSubmissions=cachedRank.getRank(contest_id,frozen);
        ArrayList<Balloon> list=new ArrayList<>();
        for(var x:userSubmissions){
            for(var xx:x.getSubmission_info().entrySet()){
                if(xx.getValue().is_ac){
                    Balloon balloon=new Balloon();
                    balloon.setAc_info(xx.getValue());
                    balloon.setUsername(x.getUser().getUsername());
                    balloon.setReal_name("unknown");
                    balloon.setAc_time(xx.getValue().ac_time);
                    balloon.setProblem_id(xx.getKey());
                    balloon.setChecked(false);
                    balloon.setId(xx.getValue().solution_id);
                    list.add(balloon);
                }
            }
        }
        list.sort((a,b)->b.getAc_time().compareTo(a.getAc_time()));
        return new ReturnType(list);
    }

    @PutMapping("acm_helper")
    public ReturnType checkBalloon(HttpServletRequest request){
        User user = Authentication.getUser(request);
        boolean admin = Authentication.isAdministrator(user);
        if (user == null || !admin) {
            return new ReturnType("login-required", "Please login in first");
        }
        return new ReturnType("error","not supported");
    }

    @PostMapping("")
    public ReturnType createContest(@RequestBody Contest contest, HttpServletRequest request) {
//        System.out.println(contest);
        User user = Authentication.getUser(request);
        boolean admin = Authentication.isAdministrator(user);
        if (user == null || !admin) {
            return new ReturnType("login-required", "Please login in first");
        }
        try {
            contestDao.insert(contest.getTitle(), contest.getDescription(), contest.getStart_time(), contest.getEnd_time(),
                    contest.getPassword(), contest.getFrozen_time(), contest.getVisible() ? 0 : 1);
        } catch (Exception e) {
//            throw e;
            e.printStackTrace();
            return new ReturnType("error", e.getMessage());
        }
        return new ReturnType(null);
//        return new ReturnType(null);
    }

    @GetMapping("")
    public ReturnType getContests(@RequestParam(value = "id", required = false) Integer id,
                                  @RequestParam(value = "limit", required = false) Integer limit,
                                  @RequestParam(value = "offset", required = false) Integer offset,
                                  HttpServletRequest request) {
        User user = Authentication.getUser(request);
        boolean admin = Authentication.isAdministrator(user);
        if (user == null || !admin) {
            return new ReturnType("login-required", "Please login in first");
        }
        if (id == null) {
            var res = contestDao.listAllContest(offset, limit);
            return new ReturnType<>(new ReturnListType<Contest>(res, contestDao.getNum()));
        } else {
            return new ReturnType<>(contestDao.getContest(id));
        }
    }

    @GetMapping("/problem")
    public ReturnType getProblemsInContest(@RequestParam("limit") int limit, @RequestParam("offset") int offset,
                                           @RequestParam("contest_id") int contest_id,
                                           HttpServletRequest request) {
        User user = Authentication.getUser(request);
        boolean admin = Authentication.isAdministrator(user);
        if (user == null || !admin) {
            return new ReturnType("login-required", "Please login in first");
        }
        var res = contestDao.getProblemsID(contest_id);
        int len = res.size();
        res = res.subList(Math.max(0,offset), Math.max(0,Math.min(len, offset + limit)));
        ArrayList<Problem> list = new ArrayList<>();
        for (var r : res) {
            list.add(problemDao.getProblem(r));
        }
        for (int i=0;i<list.size();i++) {
            list.get(i).set_id(String.valueOf((char)('A'+i+offset)));
        }
        return new ReturnType<>(new ReturnListType(list, len));
    }


    @PostMapping("/add_problem_from_public")
    public ReturnType addProblemFromPublic(@RequestBody ProblemInContest problemInContest, HttpServletRequest request){
        User user = Authentication.getUser(request);
        boolean admin = Authentication.isAdministrator(user);
        if (user == null || !admin) {
            return new ReturnType("login-required", "Please login in first");
        }
        contestDao.insertProblemInContest(problemInContest.problem_id,problemInContest.contest_id);
        return new ReturnType(null);
    }

    @DeleteMapping("/problem")
    public ReturnType deleteInContest(@RequestParam("id") Integer id,@RequestParam("contest_id")Integer contest_id){
//        return new ReturnType("error","error code: 19260817");
        try {
            problemDao.deleteProblemInContest(id,contest_id);
        }catch (Exception e){
            return new ReturnType("error",e.getMessage());
        }
        return new ReturnType(null);
    }


}
