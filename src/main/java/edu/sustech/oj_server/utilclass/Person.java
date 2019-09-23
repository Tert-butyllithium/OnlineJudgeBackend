package edu.sustech.oj_server.utilclass;

import org.springframework.context.annotation.Bean;



public class Person {
//    int id=1;
    int id;
    String username;
    String real_name;

    public Person(String id, String username) {
        this.id =1;
        this.username = id;
        this.real_name = username;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }
}
