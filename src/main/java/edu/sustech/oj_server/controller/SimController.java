package edu.sustech.oj_server.controller;

import edu.sustech.oj_server.dao.SimDao;
import edu.sustech.oj_server.dao.SourceCodeDao;
import edu.sustech.oj_server.entity.SourceCode;
import edu.sustech.oj_server.util.ReturnListType;
import edu.sustech.oj_server.util.ReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@RestController
public class SimController {
    @Autowired
    private SimDao simDao;

    @Autowired
    private SourceCodeDao sourceCodeDao;

    @RequestMapping("api/sim")
    public ReturnType getSim(@RequestParam("s_id") int s_id,
                             @RequestParam("sim_s_id") int sim_s_id,
                             HttpServletRequest request){
        SourceCode[] result = new SourceCode[]{sourceCodeDao.getSource(s_id), sourceCodeDao.getSource(sim_s_id)};
        return new ReturnType(new ReturnListType<SourceCode>(Arrays.asList(result),2));
    }
}
