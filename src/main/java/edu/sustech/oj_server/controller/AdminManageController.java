package edu.sustech.oj_server.controller;

import edu.sustech.oj_server.dao.AdminDao;
import edu.sustech.oj_server.util.ReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminManageController {

    @Autowired
    private AdminDao adminDao;

    @GetMapping("versions")
    public ReturnType getVersions(){
        return new ReturnType(null);
    }

    @GetMapping("dashboard_info")
    public ReturnType getDashBoardInfo(){
        return new ReturnType(adminDao.getDashBoard());
    }

}
