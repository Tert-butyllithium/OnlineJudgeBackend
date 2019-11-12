package edu.sustech.oj_server.controller;

import com.alibaba.fastjson.JSONObject;
import edu.sustech.oj_server.dao.LoginLogDao;
import edu.sustech.oj_server.dao.ProblemDao;
import edu.sustech.oj_server.dao.SolutionDao;
import edu.sustech.oj_server.dao.SourceCodeDao;
import edu.sustech.oj_server.entity.Solution;
import edu.sustech.oj_server.entity.User;
import edu.sustech.oj_server.util.Authentication;
import edu.sustech.oj_server.util.ReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.sql.Struct;
import java.util.Collections;

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

    @Value("${judge.server}")
    private String judge_server;

    @Value("${judge.token}")
    private String token;

    //    @Autowired
    private final CachedRank cachedRank;

    public HUSTSubmit(CachedRank cachedRank) {
        this.cachedRank = cachedRank;
    }

    private static class submitId {
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

    private static class Code {
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

    @GetMapping("/api/admin/submission/rejudge")
    public ReturnType rejudge(@RequestParam Integer id,HttpServletRequest request){
        User user=Authentication.getUser(request);
        if(user==null){
            return new ReturnType<>("error", "Please login first");
        }
        if(!Authentication.isAdministrator(user)){
            return new ReturnType<>("error","You are not Administrator");
        }
        submitJudger("http://"+judge_server, id);
        return new ReturnType<>(null);

    }

    @PostMapping("/api/submission")
    public ReturnType submitting(@RequestBody Code code, HttpServletRequest request) {
        String myname = null;
        User user = Authentication.getUser(request);
        if (user != null) {
            myname = user.getId();
        }
        if (myname == null) return new ReturnType<>("error", "Please login first");

        Solution tmp = new Solution(code.problem_id, myname, code.language, code.contest_id);
        if (code.contest_id != null) {
            Integer num = problemDao.getNumInContest(code.contest_id, code.problem_id);
            if (num == null) {
                tmp.setNum(-1);
                tmp.setContestId(null);
            } else {
                tmp.setNum(num);
            }
        } else {
            tmp.setNum(-1);
        }
//        System.out.println(solutionDao);
        solutionDao.submit(tmp);
        final Integer id = Integer.parseInt(tmp.getId());
        sourceCodeDao.submit(id, code.code);
//        sourceCodeDao.submit2(id, code.code);
        solutionDao.update(id);
        if (code.contest_id != null) {
            cachedRank.refresh(code.contest_id);
        }

        // Ask judge_server to judge.
        boolean judging = submitJudger("http://"+judge_server, id);
//        if (!judging) {
//            throw new RuntimeException("The judge sevice has not been started yep. Please start the service before transferring the solution id.");
//        }

        return new ReturnType<>(new submitId(id));
    }

    @PostMapping("/finishjudge")
    public void finishJudge(Integer solution_id,String token){
        if(!token.equals(this.token)){
            return;
        }
        //TO-DO
    }


    /**
     * @param url The restful API of judger
     * @param solutionId The solution id which will be judged.
     * @return boolean value with true representing adding successfully while false representing fail
     * Note that this api is asked to run the Judger first.
     * So that it would throw an exception if the judger is not running.
     */
    private boolean submitJudger(String url, Integer solutionId) {
        RestTemplate client = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        header.setContentType(MediaType.APPLICATION_JSON);

        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap();
        requestBody.put("solutionId", Collections.singletonList((solutionId.intValue())));
        HttpEntity<MultiValueMap> requestEntity = new HttpEntity<>(requestBody, header);

        try {
            ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);
            JSONObject body = JSONObject.parseObject(response.getBody());
            if (body.get("result").equals(1)) {
                return true;
            }
        } catch (org.springframework.web.client.ResourceAccessException e) {
//            throw new RuntimeException("Please running the code of judger first. Please check the input url.");
            return true;
        }
        return false;
    }



}
