package edu.sustech.oj_server.entity;

public class Sim {
    private Integer s_id;
    private Integer sim_s_id;
    private String s_username;
    private String sim_username;
    private int problem;
    private int language;
    private Integer per;

    public Sim(int s_id, int sim_s_id, int per){
        this.s_id=s_id;
        this.sim_s_id=sim_s_id;
        this.per=per;
    }
    public Sim(int s_id, int sim_s_id, String s_username, String sim_username, int problem, int language, int per){
        this(s_id, sim_s_id, per);
        this.s_username=s_username;
        this.sim_username=sim_username;
        this.problem=problem;
        this.language=language;
    }


    public Integer getS_id() {
        return s_id;
    }

    public void setS_id(Integer s_id) {
        this.s_id = s_id;
    }

    public Integer getSim_s_id() {
        return sim_s_id;
    }

    public void setSim_s_id(Integer sim_s_id) {
        this.sim_s_id = sim_s_id;
    }

    public String getS_username() {
        return s_username;
    }

    public void setS_username(String s_username) {
        this.s_username = s_username;
    }

    public String getSim_username() {
        return sim_username;
    }

    public void setSim_username(String sim_username) {
        this.sim_username = sim_username;
    }

    public int getProblem() {
        return problem;
    }

    public void setProblem(int problem) {
        this.problem = problem;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public Integer getPer() {
        return per;
    }

    public void setPer(Integer per) {
        this.per = per;
    }

    public String toString(){
        return String.format("%d %d %d",s_id, sim_s_id, per);
    }
}
