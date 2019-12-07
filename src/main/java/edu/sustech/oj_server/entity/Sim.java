package edu.sustech.oj_server.entity;

public class Sim {
    private Integer s_id;
    private Integer sim_s_id;
    private Integer per;

    public Sim(int s_id, int sim_s_id, int per){
        this.s_id=s_id;
        this.sim_s_id=sim_s_id;
        this.per=per;
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
