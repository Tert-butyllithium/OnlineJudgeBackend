package edu.sustech.oj_server.entity;

import edu.sustech.oj_server.util.HUSTToQDU;
import edu.sustech.oj_server.utilclass.ErrorStatistic;
import edu.sustech.oj_server.utilclass.SuccessStatistic;

import java.sql.Timestamp;

public class SourceCode {
    private int id;
    private int problem;
    private Timestamp create_name;
    private String user_id;
    private String username;
    private String code;
    private int result;
    private String language;
    private Object statistic_info;
    private final boolean shared= false;
    private final boolean can_unshared=false;

    public SourceCode(int id, int problem, Timestamp create_name, String user_id, String code, int result, int language,Integer time,Integer mem) {
        this.id = id;
        this.problem = problem;
        this.create_name = create_name;
        this.username=user_id;
        this.user_id = user_id;
        this.code = code;
        this.result = HUSTToQDU.translateStatus(result);
        this.language = HUSTToQDU.translateLanguage(language);
        if(result==4){
            this.statistic_info=new SuccessStatistic(time/1000,mem);
        }
        else{
            this.statistic_info=new ErrorStatistic("Error message is not visible");
        }
    }

    public String getUsername() {
        return username;
    }

    public Object getStatistic_info() {
        return statistic_info;
    }

    public int getId() {
        return id;
    }

    public int getProblem() {
        return problem;
    }

    public Timestamp getCreate_name() {
        return create_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getCode() {
        return code;
    }

    public int getResult() {
        return result;
    }

    public String getLanguage() {
        return language;
    }

    public boolean isShared() {
        return shared;
    }

    public boolean isCan_unshared() {
        return can_unshared;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProblem(int problem) {
        this.problem = problem;
    }

    public void setCreate_name(Timestamp create_name) {
        this.create_name = create_name;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setResult(int result) {
        this.result = HUSTToQDU.translateStatus(result);
    }

    public void setLanguage(Integer language) {
        this.language = HUSTToQDU.translateLanguage(language);
    }
}
