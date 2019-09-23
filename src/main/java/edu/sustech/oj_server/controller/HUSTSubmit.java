package edu.sustech.oj_server.controller;

import edu.sustech.oj_server.dao.LoginLogDao;
import edu.sustech.oj_server.dao.ProblemDao;
import edu.sustech.oj_server.dao.SolutionDao;
import edu.sustech.oj_server.dao.SourceCodeDao;
import edu.sustech.oj_server.entity.Solution;
import edu.sustech.oj_server.entity.User;
import edu.sustech.oj_server.util.Authentication;
import edu.sustech.oj_server.util.ReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.sql.Struct;

@RestController
public final class HUSTSubmit {
    @Autowired
    SolutionDao solutionDao;
    @Autowired
    SourceCodeDao sourceCodeDao;
    @Autowired
    LoginLogDao loginLogDao;
    @Autowired
    ProblemDao problemDao;

//    @Autowired
    private final CachedRank cachedRank;
    public HUSTSubmit(CachedRank cachedRank) {
        this.cachedRank = cachedRank;
    }

    private static class submitId{
        Integer submission_id;

        public void setSubmission_id(Integer submission_id) {
            this.submission_id = submission_id;
        }

        public Integer getSubmission_id() {
            return submission_id;
        }

        public submitId(Integer submission_id) {
            this.submission_id = submission_id;
        }
    }

    private static class Code{
        String code;
        Integer contest_id;
        String language;
        Integer problem_id;

        public Code(String code, Integer contest_id, String language, Integer problem_id) {
            this.code = code;
            this.contest_id = contest_id;
            this.language = language;
            this.problem_id = problem_id;
        }

        public Code(String code, String language, Integer problem_id) {
            this.code = code;
            this.language = language;
            this.problem_id = problem_id;
        }

        public Code() {
        }

        public String getCode() {
            return code;
        }

        public Integer getContest_id() {
            return contest_id;
        }

        public String getLanguage() {
            return language;
        }

        public Integer getProblem_id() {
            return problem_id;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setContest_id(Integer contest_id) {
            this.contest_id = contest_id;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public void setProblem_id(Integer problem_id) {
            this.problem_id = problem_id;
        }
    }

    @PostMapping("/api/submission")
    public ReturnType submitting(@RequestBody Code code, HttpServletRequest request){
        String myname=null;
        User user= Authentication.getUser(request);
        if(user!=null){
            myname=user.getId();
        }
        if(myname==null) return new ReturnType<>("error","Please login first");

        Solution tmp =new Solution(code.problem_id,myname,code.language,code.contest_id);
        if(code.contest_id!=null){
            Integer num=problemDao.getNumInContest(code.contest_id,code.problem_id);
            if(num==null){
                tmp.setNum(-1);
                tmp.setContestId(null);
            }
            else{
                tmp.setNum(num);
            }
        }
        else{
            tmp.setNum(-1);
        }
        System.out.println(solutionDao);
        solutionDao.submit(tmp);
        final Integer id=Integer.parseInt(tmp.getId());
        sourceCodeDao.submit(id,code.code);
        sourceCodeDao.submit2(id,code.code);
        solutionDao.update(id);
        if(code.contest_id!=null){
            cachedRank.refresh(code.contest_id);
        }
        return new ReturnType<>(new submitId(id));
    }

}
