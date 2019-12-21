package edu.sustech.oj_server.toolclass;

public class ErrorStatistic {
    final int score=0;
    String err_info;

    public ErrorStatistic(String err_info) {
        this.err_info = err_info;
    }

    public void setErr_info(String err_info) {
        this.err_info = err_info;
    }

    public int getScore() {
        return score;
    }

    public String getErr_info() {
        return err_info;
    }
}
