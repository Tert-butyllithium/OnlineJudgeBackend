package edu.sustech.oj_server.controller;

import edu.sustech.oj_server.util.ReturnType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {
    @PostMapping("/error")
    public ReturnType getError(@RequestBody Object o){
//        System.out.println(o);
        return new ReturnType(null);
    }

    @PostMapping("/api/judge_server_heartbeat/")
    public ReturnType postHeartBeat(@RequestBody Object o){
//        System.out.println(o);
        return new ReturnType(null);
    }
}
