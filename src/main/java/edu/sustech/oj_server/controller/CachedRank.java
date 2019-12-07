package edu.sustech.oj_server.controller;

import edu.sustech.oj_server.dao.ContestDao;
import edu.sustech.oj_server.dao.SolutionDao;
import edu.sustech.oj_server.entity.Solution;
import edu.sustech.oj_server.toolclass.Solve;
import edu.sustech.oj_server.toolclass.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;


@Service
public class CachedRank {
    @Autowired
    SolutionDao solutionDao;
    @Autowired
    ContestDao contestDao;

    private final CachedUser cachedUser;

    public CachedRank(CachedUser cachedUser) {
        this.cachedUser = cachedUser;
    }


    @Cacheable("rank")
    public List<Solve> getRank(int contest_id,int frozen){
        var method_start_time = System.currentTimeMillis();
        List<Solution> list=solutionDao.listSolutionsInContest(contest_id);
        final Set<Integer> contest_problem=new HashSet<>(contestDao.getProblemsID(contest_id));
        Map<String, Solve> userSubmissions=new HashMap<>();
        Set<String> solved=new HashSet<>();
        long start=contestDao.getContest(contest_id).getStart_time().getTime();
        long end=contestDao.getContest(contest_id).getEnd_time().getTime()-frozen*60*1000;
        System.out.println("Query Database: "+(System.currentTimeMillis()-method_start_time));
        method_start_time = System.currentTimeMillis();
        final var frozentime=new Timestamp(end);
        for(var s:list){
//            if(s.getProblem())
            Integer problemid=Integer.parseInt(s.getProblem());
            if(!contest_problem.contains(problemid)){
                continue;
            }
            if(s.getCreate_time().after(frozentime)){
                continue;
            }
            userSubmissions.putIfAbsent(s.getUser_id(),new Solve());
            var mysolves=userSubmissions.get(s.getUsername());
            if(mysolves.getUser()==null)
                mysolves.setUser(s.getUser());
            var sa=mysolves.getSubmission_info();
            sa.putIfAbsent(s.getProblem(),new Status());
            var saa=sa.get(s.getProblem());
            if(saa.is_ac) continue;
            if(s.getResult()==0){
                saa.is_ac=true;
                saa.ac_time=(s.getCreate_time().getTime()-start)/1000.0;
                mysolves.setAccepted_number(mysolves.getAccepted_number()+1);
                if(!solved.contains(s.getProblem())){
                    solved.add(s.getProblem());
                    saa.is_first_ac=true;
                }
                mysolves.setTotal_time(mysolves.getTotal_time()+saa.ac_time);
            }
            else if(s.getResult()==6){
                saa.try_number++;
            }
            else{
                saa.error_number++;
            }
        }
        System.out.println("Step 1 cost: "+(System.currentTimeMillis()-method_start_time));
        method_start_time = System.currentTimeMillis();
        int id=1;
        for(var p:userSubmissions.entrySet()){
            p.getValue().setId(id++);
            p.getValue().setContest(contest_id);
            p.getValue().setUser(cachedUser.getUser(p.getKey()));

            p.getValue().getSubmission_info().values().stream().
                    filter(Status::isIs_ac).map(t->t.error_number+1).
                    reduce(Integer::sum).ifPresent(t->p.getValue().setSubmission_number(t));

        }
        List<Solve> res = new ArrayList<>(userSubmissions.values());
        Collections.sort(res);
        System.out.println("Step 2 cost: "+(System.currentTimeMillis()-method_start_time));
        method_start_time = System.currentTimeMillis();

        var problemInContest = contestDao.getProblemsID(contest_id);
        Map<Character,String> problemConverter=new HashMap<>();
        for(char i='A';i<problemInContest.size()+'A';i++){
            problemConverter.put(i,Integer.toString(problemInContest.get(i-'A')));
        }
        int rank=1;
        for(int i=0;i<res.size();i++){
            if(res.get(i).getUser()!=null&&res.get(i).getUser().getObserver()!=null&&res.get(i).getUser().getObserver()){
                res.get(i).setRank("*");
            }
            else {
                res.get(i).setRank(Integer.toString(rank));
                rank++;
            }
           res.get(i).setProblemConvert(problemConverter);

        }
        System.out.println("Step 3 cost: "+(System.currentTimeMillis()-method_start_time));
        return res;
    }

    public static void writeCSV(String contest,Integer problem_count, List<Solve> list) throws FileNotFoundException {


        String path="cachedrank/"+contest+".csv";
        File file=new File(path);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        PrintWriter out=new PrintWriter(path);
        StringBuilder header=new StringBuilder();
        header.append("Rank,User,Solved,TotalTime");
        for(char i='A';i<problem_count+'A';i++){
            header.append(","+i);
        }
        out.println(header);
        for(var c:list){
            if(c.getRank().equals("*")){
                continue;
            }
            out.println(c);
        }
        out.close();
    }

    @CacheEvict("rank")
    public void refresh(int contest_id,int frozen){
        System.out.println("cleared");
    }
}
