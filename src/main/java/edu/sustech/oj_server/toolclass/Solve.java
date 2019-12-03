package edu.sustech.oj_server.toolclass;

import com.mysql.cj.exceptions.StreamingNotifiable;
import edu.sustech.oj_server.entity.Problem;
import edu.sustech.oj_server.entity.User;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;


public class Solve implements Comparable<Solve>{

    int id;
    User user;

    int submission_number;

    int accepted_number;

    int contest_id;

    String rank;

    double total_time;

    Map<String, Status> submission_info;

    Map<Character,String> problemConvert;


    public Solve() {
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

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setProblemConvert(Map<Character, String> problemConvert) {
        this.problemConvert = problemConvert;
    }

    public double getTotal_time() {
        return total_time;
    }

    public double penalty(){
        return this.total_time+20*60*(this.submission_number-this.accepted_number);
    }

    public int compareTo(Solve solve) {
        if(accepted_number==solve.accepted_number){
            return Double.compare(this.penalty(),solve.penalty());
        }
        return Integer.compare(solve.accepted_number,this.accepted_number);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(this.rank+"," + this.user.getId()+","+this.accepted_number + "," + ((long)this.total_time)/60);

        for(char i='A';i<'A'+this.submission_info.size();i++){
            res.append(", ");
            var x=problemConvert.get(i);
            if(this.submission_info.containsKey(x)&&this.submission_info.get(x).is_ac) {
                var seconds = this.submission_info.get(x).ac_time.longValue();
                res.append( seconds / 60);
            }
            if(this.submission_info.containsKey(x)&&this.submission_info.get(x).error_number>0){
                res.append(String.format(" (-%d)",this.submission_info.get(x).error_number));
            }
        }
        return res.toString();
    }
}
