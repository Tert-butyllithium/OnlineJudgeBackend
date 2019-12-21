package edu.sustech.oj_server.toolclass;

import edu.sustech.oj_server.entity.User;
import edu.sustech.oj_server.util.MTRandom;

import java.util.Random;

public class UserProfile {
    int id;
    User user;

    public UserProfile(User user) {
        Random random=new MTRandom(System.currentTimeMillis());
        this.id = Math.abs(random.nextInt());
        this.user = user;
//        this.user;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
