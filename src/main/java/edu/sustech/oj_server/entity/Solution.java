package edu.sustech.oj_server.entity;

import edu.sustech.oj_server.util.HUSTToQDU;
import edu.sustech.oj_server.toolclass.ErrorStatistic;
import edu.sustech.oj_server.toolclass.SuccessStatistic;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Solution {

    private Integer id;

    private String problem;

    private String user_id;

    private Timestamp create_time;

    private Integer result;

    private String language;

    private Integer contestId;
    private Integer num;
    private User user;


    private Object statistic_info;
    private String username;
    private boolean show_link=false;

    public boolean isShow_link() {
        return show_link;
    }

    public void setShow_link(boolean show_link) {
        this.show_link = show_link;
    }
    //    public void setLanguage(String language) {
//        this.language = language;
//    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public final String getId() {
        return String.valueOf(this.id);
    }

    public final void setId( Integer var1) {
        this.id = var1;
    }


    public final String getProblem() {
        return this.problem;
    }

    public final void setProblem( String var1) {
        this.problem = var1;
    }


    public final String getUser_id() {
        return this.user_id;
    }

    public final void setUser_id( String var1) {
        this.user_id = var1;
    }


    public final Timestamp getCreate_time() {
        return this.create_time;
    }

    public final void setCreate_time( Timestamp var1) {
        this.create_time = var1;
    }


    public final Integer getResult() {
        return this.result;
    }

    public final void setResult( Integer var1) {
        this.result = HUSTToQDU.translateStatus(var1);
    }


    public final String getLanguage() {
        return this.language;
    }

    public final void setLanguage( Integer var1) {
        this.language = HUSTToQDU.translateLanguage(var1);
    }


    public final Integer getContestId() {
        return this.contestId;
    }

    public final void setContestId( Integer var1) {
        this.contestId = var1;
    }


    public final Object getStatistic_info() {
        return this.statistic_info;
    }

    public final void setStatistic_info( Object var1) {
        this.statistic_info = var1;
    }

    public User getUser() {
        return user;
    }

    public Solution(Integer id, Integer problem, String user_id, Integer time, Integer memory, Timestamp inDate, int result, Integer language, String ip, Integer contestId, Byte valid, Integer num, Integer codeLength, Timestamp judgetime, BigDecimal passRate, Integer lintError, String judger) {
        this.id = id;
        this.problem = problem.toString();
        this.user_id = user_id;
        this.create_time = inDate;
        this.result=HUSTToQDU.translateStatus(result);
        this.language = HUSTToQDU.translateLanguage(language);
        this.contestId = contestId;
        this.username=this.user_id;
        this.num=num;
        if(result==4){
            this.statistic_info=new SuccessStatistic(time,memory*1024);
        }
        else{
            this.statistic_info=new ErrorStatistic("sorry, not available");
        }
    }

    public Solution( Integer id,  Integer problem,  String user_id,String nick, Integer time,  Integer memory,  Timestamp inDate,
                     int result, Integer language,  String ip,  Integer contestId,  Byte valid,  Integer num,
                     Integer codeLength,  Timestamp judgetime,  BigDecimal passRate,  Integer lintError,  String judger) {
        this(id,problem,user_id,time,memory,inDate,result,language,ip,contestId,valid,num,codeLength,judgetime,passRate,lintError,judger);
        this.user=new User(user_id,null,0,0,null,null,null,null,null,null,null,nick,null);
    }

    public Solution(Integer problem,String username,String language,Integer contestId){
        this(null,problem,username,null,null,new Timestamp(System.currentTimeMillis()),1,null,"172.18.1.122",contestId,null,null,null,null,null,null,null);
        this.language=String.valueOf(HUSTToQDU.translateLanguage(language));
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getNum() {
        return num;
    }

    @Override
    public String toString() {
        return this.problem + " "+this.language;
    }
}
