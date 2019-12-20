package edu.sustech.oj_server.controller;

import edu.sustech.oj_server.dao.SimDao;
import edu.sustech.oj_server.dao.SourceCodeDao;
import edu.sustech.oj_server.entity.Sim;
import edu.sustech.oj_server.entity.SourceCode;
import edu.sustech.oj_server.moss.Plagiarism;
import edu.sustech.oj_server.util.ReturnListType;
import edu.sustech.oj_server.util.ReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class SimController {
    @Autowired
    private SimDao simDao;

    @Autowired
    private SourceCodeDao sourceCodeDao;

    @Autowired
    private Environment environment;

    @RequestMapping("api/sim")
    public String getSim(@RequestParam("s_id") int s_id,
                             @RequestParam("sim_s_id") int sim_s_id,
                             HttpServletRequest request,
                             Model model){
        SourceCode[] result = new SourceCode[]{sourceCodeDao.getSource(s_id), sourceCodeDao.getSource(sim_s_id)};
        model.addAttribute("pair", new ReturnType(new ReturnListType<SourceCode>(Arrays.asList(result),2)));
        return "sim";
    }

    @RequestMapping("api/simlist")
    public String getSimList(@RequestParam("contest") int contestId, Model model){
        ArrayList<Sim> sims = (ArrayList<Sim>) simDao.getSimList(contestId);
        model.addAttribute("list", sims);
        return "SimList";
    }

    @RequestMapping("api/dosim")
    public String doSim(@RequestParam("contest") int contestId, Model model) throws InterruptedException {
        Plagiarism plagiarism = new Plagiarism();
        String msg="";
        try {
            plagiarism.run(contestId);
        } catch (IOException e) {
            e.printStackTrace();
            msg="Connection refused, try again";
            model.addAttribute("msg", msg);
            return "dosim";
        }
        msg="Everything is done.";
        model.addAttribute("msg", msg);
        return "dosim";
    }
}
