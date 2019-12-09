package edu.sustech.oj_server.moss;

import java.net.URL;

public class Info {
    private Integer solId;
    private Integer simSolId;
    private String username;
    private String simUsername;
    private Integer sim;
    private URL url;
    public Info(Integer solId, Integer simSolId, String username, String simUsername, Integer sim, URL url){
        this.solId=solId;
        this.simSolId=simSolId;
        this.simUsername=simUsername;
        this.username=username;
        this.url=url;
        this.sim=sim;
    }

    public Integer getSolId() {
        return solId;
    }

    public void setSolId(Integer solId) {
        this.solId = solId;
    }

    public Integer getSimSolId() {
        return simSolId;
    }

    public void setSimSolId(Integer simSolId) {
        this.simSolId = simSolId;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSimUsername() {
        return simUsername;
    }

    public void setSimUsername(String simUsername) {
        this.simUsername = simUsername;
    }

    public Integer getSim() {
        return sim;
    }

    public void setSim(Integer sim) {
        this.sim = sim;
    }
    public boolean isTheSameUser(){
        return (username.compareTo(simUsername)==0);
    }

    public String toString(){
        return String.format("u1: %d, u2: %d, un1: %s, un2: %s, sim: %d, url: %s\n",solId, simSolId, username, simUsername, sim, url);
    }
}
