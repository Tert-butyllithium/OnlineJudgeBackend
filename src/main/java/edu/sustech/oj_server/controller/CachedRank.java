package edu.sustech.oj_server.controller;

import edu.sustech.oj_server.controller.ContestRankController;
import edu.sustech.oj_server.dao.ContestDao;
import edu.sustech.oj_server.dao.SolutionDao;
import edu.sustech.oj_server.dao.UserDao;
import edu.sustech.oj_server.entity.Solution;
import edu.sustech.oj_server.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CachedRank {
    @Autowired
    UserDao userDao;
    @Autowired
    SolutionDao solutionDao;
    @Autowired
    ContestDao contestDao;

    class Status{
        boolean is_ac;
        double ac_time;
        boolean is_first_ac;
        int error_number;

        Status() {
            this.is_ac=false;
            this.ac_time=0;
            this.is_first_ac=false;
            this.error_number=0;
        }

        public boolean isIs_ac() {
            return is_ac;
        }

        public double getAc_time() {
            return ac_time;
        }

        public boolean isIs_first_ac() {
            return is_first_ac;
        }

        public int getError_number() {
            return error_number;
        }

        public void setIs_ac(boolean is_ac) {
            this.is_ac = is_ac;
        }

        public void setAc_time(double ac_time) {
            this.ac_time = ac_time;
        }

        public void setIs_first_ac(boolean is_first_ac) {
            this.is_first_ac = is_first_ac;
        }

        public void setError_number(int error_number) {
            this.error_number = error_number;
        }
    }

    class Solve implements Comparable<Solve>{
        int id;
        User user;

        int submission_number;
        int accepted_number;
        int contest_id;
        double total_time;
        Map<String,Status> submission_info;

        Solve() {
            submission_info=new HashMap<>();

        }


        int contest;

        public void setId(int id) {
            this.id = id;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public void setSubmission_number(int submission_number) {
            this.submission_number = submission_number;
        }

        public void setAccepted_number(int accepted_number) {
            this.accepted_number = accepted_number;
        }

        public void setContest_id(int contest_id) {
            this.contest_id = contest_id;
        }

        public void setSubmission_info(Map<String, Status> submission_info) {
            this.submission_info = submission_info;
        }

        public void setContest(int contest) {
            this.contest = contest;
        }

        public void setTotal_time(double total_time) {
            this.total_time = total_time;
        }

        public int getId() {
            return id;
        }

        public User getUser() {
            return user;
        }

        public int getSubmission_number() {
            return submission_number;
        }

        public int getAccepted_number() {
            return accepted_number;
        }

        public int getContest_id() {
            return contest_id;
        }

        public Map<String, Status> getSubmission_info() {
            return submission_info;
        }

        public int getContest() {
            return contest;
        }

        public double getTotal_time() {
            return total_time;
        }

        public double penalty(){
            return this.total_time+20*(this.submission_number-this.accepted_number);
        }

        @Override
        public int compareTo(Solve solve) {
            if(accepted_number==solve.accepted_number){
                return Double.compare(this.penalty(),solve.penalty());
            }
            return Integer.compare(solve.accepted_number,this.accepted_number);
        }
    }


    @Cacheable("rank")
    public List<Solve> getRank(int contest_id){
        System.out.println("call");
        List<Solution> list=solutionDao.listSolutionsInContest(contest_id);
        Map<String, Solve> userSubmissions=new HashMap<>();
        Set<String> solved=new HashSet<>();
        long start=contestDao.getContest(contest_id).getStart_time().getTime();
        for(var s:list){
            userSubmissions.putIfAbsent(s.getUser_id(),new Solve());
            var mysolves=userSubmissions.get(s.getUsername());
            var sa=mysolves.submission_info;
            sa.putIfAbsent(s.getProblem(),new Status());
            var saa=sa.get(s.getProblem());
            if(saa.is_ac) continue;
            if(s.getResult()==0){
                saa.is_ac=true;
                saa.ac_time=(s.getCreate_time().getTime()-start)/1000.0;
                mysolves.accepted_number++;
                if(!solved.contains(s.getProblem())){
                    solved.add(s.getProblem());
                    saa.is_first_ac=true;
                }
                mysolves.total_time+=saa.ac_time;
            }
            else{
                saa.error_number++;
            }
        }
        int id=1;
        for(var p:userSubmissions.entrySet()){
            p.getValue().setUser(userDao.getUser(p.getKey()));
            p.getValue().setId(id++);
            p.getValue().setContest(contest_id);

            p.getValue().submission_info.values().stream().
                    filter(Status::isIs_ac).map(t->t.error_number+1).
                    reduce(Integer::sum).ifPresent(t->p.getValue().setSubmission_number(t));

        }
        List<Solve> res = new ArrayList<>(userSubmissions.values());
        Collections.sort(res);
        return res;
    }

    @CacheEvict("rank")
    public void refresh(int contest_id){
        System.out.println("cleared");
    }
}
