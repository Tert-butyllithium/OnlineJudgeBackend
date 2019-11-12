package edu.sustech.oj_server.toolclass;

/**
 * the status for a submission
 * used in RankList and Balloon
 */
public class Status{
    public boolean is_ac;
    public double ac_time;
    public boolean is_first_ac;
    public int error_number;

    public Status() {
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
