package edu.sustech.oj_server.toolclass;

import java.security.PublicKey;

/**
 * the status for a submission
 * used in RankList and Balloon
 */
public class Status{
    public Boolean is_ac;
    public Double ac_time;
    public Boolean is_first_ac;
    public Integer error_number;
    public Integer try_number;
    public Double penalty;
    public Boolean checked;
    public Integer solution_id;

    public Status() {
        this.is_ac=false;
        this.ac_time=0.00;
        this.is_first_ac=false;
        this.error_number=0;
        this.try_number=0;
        this.penalty=0.00;
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

    public Integer getTry_number() {
        return try_number;
    }

    public void setTry_number(Integer try_number) {
        this.try_number = try_number;
    }

    public Boolean getChecked() {
        return checked;
    }
}
