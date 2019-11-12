package edu.sustech.oj_server.toolclass;

public class DashBoard{
    Integer user_count;
    Integer recent_contest_count;
    Integer today_submission_count;
    Integer judge_server_count;
    final Object env=new Object(){
        boolean FORCE_HTTPS=true;
        String STATIC_CDN_HOST="";

        public boolean isFORCE_HTTPS() {
            return FORCE_HTTPS;
        }

        public String getSTATIC_CDN_HOST() {
            return STATIC_CDN_HOST;
        }
    };

    public DashBoard(Integer user_count, Integer recent_contest_count, Integer today_submission_count) {
        this.user_count = user_count;
        this.recent_contest_count = recent_contest_count;
        this.today_submission_count = today_submission_count;
        this.judge_server_count=1;
    }

    public Integer getUser_count() {
        return user_count;
    }

    public Integer getRecent_contest_count() {
        return recent_contest_count;
    }

    public Integer getToday_submission_count() {
        return today_submission_count;
    }

    public Integer getJudge_server_count() {
        return judge_server_count;
    }

    public Object getEnv() {
        return env;
    }
}
