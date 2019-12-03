package edu.sustech.oj_server.controller;

import edu.sustech.oj_server.dao.ContestDao;
import edu.sustech.oj_server.entity.Contest;
import edu.sustech.oj_server.entity.User;
import edu.sustech.oj_server.util.Authentication;
import edu.sustech.oj_server.util.ReturnListType;
import edu.sustech.oj_server.util.ReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;

@RestController
public class ContestRankController {

    @Autowired
    private ContestDao contestDao;

    private final CachedRank cachedRank;

    public ContestRankController(CachedRank cachedRank) {
        this.cachedRank = cachedRank;
    }

    @RequestMapping("api/contest_rank")
    public Object getContestRank(@RequestParam(value = "offset",defaultValue = "0") Integer offset,
                                 @RequestParam(value = "limit",required = false) Integer limit,
                                 @RequestParam("contest_id") Integer contest_id,
                                 @RequestParam(value = "force_refresh",required = false) Integer force_refresh,
                                 @RequestParam(value = "download_csv",required = false) Integer download_csv,
                                 HttpServletRequest request) {
        if(download_csv!=null&&download_csv!=0){
            try {
                User user = Authentication.getUser(request);
                boolean admin = Authentication.isAdministrator(user);
                if (user == null || !admin) {
                    return new ReturnType("error", "you are not administrator");
                }
                CachedRank.writeCSV(Integer.toString(contest_id),cachedRank.getRank(contest_id,0));
                File file = new File("cachedrank/"+contest_id+".csv");
                Path path = Paths.get(file.getAbsolutePath());
                ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+contestDao.getContest(contest_id).getTitle()+"_Rank.csv");

                return ResponseEntity.ok()
                        .headers(headers)
                        .contentLength(file.length())
                        .contentType(MediaType.parseMediaType("application/octet-stream"))
                        .body(resource);
            }catch (Exception e){
                e.printStackTrace();
                return new ReturnType<>("error",e.getMessage());
            }
        }
        Integer frozen=contestDao.getFrozen(contest_id);
        if(frozen==null){
            frozen=0;
        }
//        if (force_refresh !=null && force_refresh !=0) {
//            cachedRank.refresh(contest_id,frozen);
//            if(frozen!=0){
//                cachedRank.refresh(contest_id,0);
//            }
//        }
//        Contest contest=contestDao.getContest(contest_id);

        User user = Authentication.getUser(request);
        if(Authentication.isAdministrator(user)){
            frozen=0;
        }
        var res = cachedRank.getRank(contest_id,frozen);
        int end = Math.min(res.size(), offset + limit);
        return new ReturnType<>(new ReturnListType<>(res.subList(offset, end), res.size()));
    }

    @GetMapping("api/contest_rank/myrank")
    public ReturnType<ReturnListType> getMyRank(@RequestParam("id") Integer id,HttpServletRequest request){
        Integer frozen=contestDao.getFrozen(id);
        if(frozen==null){
            frozen=0;
        }
        User user = Authentication.getUser(request);
        if(user==null){
            return new ReturnType<>(new ReturnListType(new ArrayList(),0));
        }
        var ranklist=cachedRank.getRank(id,0);
        ArrayList res=new ArrayList();
        for(var c:ranklist){
            if(Objects.equals(c.getUser().getId(), user.getId())){
                long end=contestDao.getContest(id).getEnd_time().getTime();
                if(end-frozen*60*1000<System.currentTimeMillis()){
                    c.setRank("?");
                }
                res.add(c);
                return new ReturnType<>(new ReturnListType(res,1));
            }
        }
        return new ReturnType<>(new ReturnListType(new ArrayList(),0));
    }


}
