package edu.sustech.oj_server.util;

import edu.sustech.oj_server.dao.LoginLogDao;
import edu.sustech.oj_server.dao.PrivilegeDao;
import edu.sustech.oj_server.dao.RuntimeInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RuntimeInfo {
    @Autowired
    private RuntimeInfoDao dao;

    private static RuntimeInfoDao runtimeInfoDao;

    @PostConstruct
    public void init() {
        runtimeInfoDao=dao;
    }

    public static String getInfo(Integer id){
        if(id==null) return null;
        return runtimeInfoDao.getInfo(id);
    }

    public static String getCompileInfo(Integer id){
        if(id==null) return null;
        return runtimeInfoDao.getCompileInfo(id);
    }
}
