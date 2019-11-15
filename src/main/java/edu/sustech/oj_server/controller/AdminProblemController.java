package edu.sustech.oj_server.controller;

import edu.sustech.oj_server.dao.ProblemDao;
import edu.sustech.oj_server.entity.Contest;
import edu.sustech.oj_server.util.ReturnListType;
import edu.sustech.oj_server.util.ReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/problem")
public class AdminProblemController {

    @Autowired
    private ProblemDao problemDao;

    @GetMapping("")
    public ReturnType getContests(@RequestParam(value = "id", required = false) Integer id,
                                  @RequestParam(value = "limit", required = false) Integer limit,
                                  @RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                  @RequestParam(value = "keyword",required = false) String keyword) {
        if(id==null) {
            var res = problemDao.listAllProblemsForAdmin(keyword,offset, limit);
            return new ReturnType<>(new ReturnListType<>(res, problemDao.getNum(keyword)));
        }
        else{
            return new ReturnType<>(problemDao.getProblem(id));
        }
    }
}
