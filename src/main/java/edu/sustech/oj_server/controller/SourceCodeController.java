package edu.sustech.oj_server.controller;

import edu.sustech.oj_server.dao.SourceCodeDao;
import edu.sustech.oj_server.entity.SourceCode;
import edu.sustech.oj_server.entity.User;
import edu.sustech.oj_server.util.Authentication;
import edu.sustech.oj_server.util.ReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SourceCodeController {

    @Autowired
    SourceCodeDao sourceCodeDao;

    @RequestMapping("/api/submission")
    public ReturnType getSourceCode(@RequestParam("id") Integer id, HttpServletRequest request){
        User user= Authentication.getUser(request);
        String author= sourceCodeDao.getName(id);
        if(user != null &&((author.equals(user.getId())||(Authentication.isAdministrator(user))))) {
            var res=sourceCodeDao.getSource(id);
            if(res==null){
                return new ReturnType<>("error","No such submission");
            }
            return new ReturnType<>(res);
        }
        return new ReturnType<>("error","You don't have permission to view this code");

    }
}
