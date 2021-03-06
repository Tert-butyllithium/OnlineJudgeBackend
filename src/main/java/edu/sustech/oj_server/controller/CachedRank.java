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
    public List<Solve> getRank(int contest_id, int frozen) {
        var method_start_time = System.currentTimeMillis();
        System.out.println("Query Database: " + (System.currentTimeMillis() - method_start_time));
        method_start_time = System.currentTimeMillis();
        Map<String, Solve> userSubmissions = getUserStatus(contest_id, frozen);
        System.out.println("Step 1 cost: " + (System.currentTimeMillis() - method_start_time));
        method_start_time = System.currentTimeMillis();
        int id = 1;
        for (var p : userSubmissions.entrySet()) {
            p.getValue().setId(id++);
            p.getValue().setContest(contest_id);
            p.getValue().setUser(cachedUser.getUser(p.getKey()));

            p.getValue().getSubmission_info().values().stream().
                    filter(Status::isIs_ac).map(t -> t.error_number + 1).
                    reduce(Integer::sum).ifPresent(t -> p.getValue().setSubmission_number(t));

        }
        List<Solve> res = new ArrayList<>(userSubmissions.values());
        Collections.sort(res);
        System.out.println("Step 2 cost: " + (System.currentTimeMillis() - method_start_time));
        method_start_time = System.currentTimeMillis();

        var problemInContest = contestDao.getProblemsID(contest_id);
        Map<Character, String> problemConverter = new HashMap<>();
        for (char i = 'A'; i < problemInContest.size() + 'A'; i++) {
            problemConverter.put(i, Integer.toString(problemInContest.get(i - 'A')));
        }
        int rank = 1;
        for (Solve re : res) {
            if (re.getUser() != null && re.getUser().getObserver() != null && re.getUser().getObserver()) {
                re.setRank("*");
            } else {
                re.setRank(Integer.toString(rank));
                rank++;
            }
            re.setProblemConvert(problemConverter);

        }
        System.out.println("Step 3 cost: " + (System.currentTimeMillis() - method_start_time));
        return res;
    }

    private Map<String, Solve> getUserStatus(int contest_id,int frozen) {
        Set<String> solved = new HashSet<>();
        long start = contestDao.getContest(contest_id).getStart_time().getTime();
        long end = contestDao.getContest(contest_id).getEnd_time().getTime() - frozen * 60 * 1000;
        List<Solution> list = solutionDao.listSolutionsInContest(contest_id);
        final List<Integer> contest_problem = contestDao.getProblemsID(contest_id);
        Map<String, Solve> userSubmissions = new HashMap<>();
        final var frozentime = new Timestamp(end);
        for (var s : list) {
//            if(s.getProblem())
            Integer problemid = Integer.parseInt(s.getProblem());
            if (!contest_problem.contains(problemid)) {
                continue;
            }
//            s.setProblem_display_id(String.valueOf((char)('A'+contest_problem.indexOf(Integer.parseInt(s.getProblem())))));
            if (s.getCreate_time().after(frozentime)) {
//                continue;
                s.setResult(0);
            }
            userSubmissions.putIfAbsent(s.getUser_id(), new Solve());
            var mysolves = userSubmissions.get(s.getUsername());
            if (mysolves.getUser() == null)
                mysolves.setUser(s.getUser());
            var sa = mysolves.getSubmission_info();
            sa.putIfAbsent(s.getProblem(), new Status());
            var saa = sa.get(s.getProblem());

            if (saa.is_ac)
                continue;
            if (s.getResult() == 0) {
                saa.is_ac = true;
                saa.ac_time = (s.getCreate_time().getTime() - start) / 1000.0;
                saa.penalty = saa.ac_time + saa.error_number * 20 * 60;
                mysolves.setAccepted_number(mysolves.getAccepted_number() + 1);
                if (!solved.contains(s.getProblem())) {
                    solved.add(s.getProblem());
                    saa.is_first_ac = true;
                }
                saa.solution_id=Integer.parseInt(s.getId());
                mysolves.setTotal_time(mysolves.getTotal_time() + saa.penalty);
                mysolves.setPenalty(mysolves.getPenalty() + saa.penalty);
                saa.checked=s.checked||saa.checked;
            } else if (s.getResult() == 6) {
                saa.try_number++;
            } else {
                saa.error_number++;
            }
        }
        return userSubmissions;
    }

    public static void writeCSV(String contest, Integer problem_count, List<Solve> list) throws FileNotFoundException {

        String path = "cachedrank/" + contest + ".csv";
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        PrintWriter out = new PrintWriter(path);
        StringBuilder header = new StringBuilder();
        header.append("Rank,User,Solved,TotalTime");
        for (char i = 'A'; i < problem_count + 'A'; i++) {
            header.append("," + i);
        }
        out.println(header);
        for (var c : list) {
            if (c.getRank().equals("*")) {
                continue;
            }
            out.println(c);
        }
        out.close();
    }

    @CacheEvict("rank")
    public void refresh(int contest_id, int frozen) {
        System.out.println("cleared");
    }
}
