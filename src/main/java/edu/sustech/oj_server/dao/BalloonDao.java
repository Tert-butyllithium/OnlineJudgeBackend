package edu.sustech.oj_server.dao;

import edu.sustech.oj_server.entity.Balloon;
import edu.sustech.oj_server.entity.Solution;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BalloonDao {

    @Select("select s.solution_id, problem_id, user_id, time, memory, in_date, result, language," +
            " ip, contest_id, valid, num, code_length, judgetime, pass_rate," +
            " lint_error, judger from balloon join solution s on " +
            "balloon.solution_id = s.solution_id where s.contest_id=${contest_id} and checked=0")
    List<Solution> listAllBalloons(int contest_id);

    @Update("update balloon set status = true where balloon_id=#{balloon_id}")
    void check(int balloon_id);

    @Insert("insert into balloon (balloon_id, user_id, sid, cid, pid, status) values" +
            " (default,#{user_id},#{solution_id},#{contest_id},#{problem_id},false)")
    void insert(String user_id,int solution_id,int contest_id,int problem_id);
}
