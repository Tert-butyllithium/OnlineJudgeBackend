package edu.sustech.oj_server.controller;

import edu.sustech.oj_server.util.ReturnListType;
import edu.sustech.oj_server.util.ReturnType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContestRankController {

    private final CachedRank cachedRank;

    public ContestRankController(CachedRank cachedRank) {
        this.cachedRank = cachedRank;
    }

    @RequestMapping("api/contest_rank")
    public ReturnType<ReturnListType> getContestRank(@RequestParam("offset") int offset,
                                                            @RequestParam("limit") int limit,
                                                            @RequestParam("contest_id")int contest_id,
                                                            @RequestParam("force_refresh") int force_refresh){
        if(force_refresh==1){
            cachedRank.refresh(contest_id);
        }
        var res=cachedRank.getRank(contest_id);
        int end=Math.min(res.size(),offset+limit);
        return new ReturnType<>(new ReturnListType<>(res.subList(offset,end),res.size()));
    }


}
