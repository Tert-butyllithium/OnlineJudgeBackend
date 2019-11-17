package edu.sustech.oj_server.controller;

import edu.sustech.oj_server.dao.ContestDao;
import edu.sustech.oj_server.util.ReturnListType;
import edu.sustech.oj_server.util.ReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
                                                     @RequestParam(value = "download_csv",required = false) Integer download_csv) {
        if(download_csv!=null&&download_csv!=0){
            try {
                CachedRank.writeCSV(Integer.toString(contest_id),cachedRank.getRank(contest_id));
                File file = new File("cachedrank"+contest_id+".csv");
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
                return new ReturnType<>("error",e.getMessage());
            }
        }
        if (force_refresh !=null && force_refresh !=0) {
            cachedRank.refresh(contest_id);
        }
        var res = cachedRank.getRank(contest_id);
        int end = Math.min(res.size(), offset + limit);
        return new ReturnType<>(new ReturnListType<>(res.subList(offset, end), res.size()));
    }


}
