package edu.sustech.oj_server.controller;

import edu.sustech.oj_server.dao.UserDao;
import edu.sustech.oj_server.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CachedUser {
    @Autowired
    UserDao userDao;

    @Cacheable("user")
    public User getUser(String id){
        return userDao.getUser(id);
    }

    @CacheEvict("user")
    public void clear(String id){
        System.out.println("cleared");
    }
}
