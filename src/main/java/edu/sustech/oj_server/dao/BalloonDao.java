package edu.sustech.oj_server.dao;

import edu.sustech.oj_server.entity.Balloon;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BalloonDao {

    @Select("select balloon_id, user_id, sid, cid, pid, status from balloon where cid=#{contest_id}")
    List<Balloon> listAllBalloons(int contest_id);

    @Update("update balloon set status = true where balloon_id=#{balloon_id}")
    void check(int balloon_id);

    @Insert("insert into balloon (balloon_id, user_id, sid, cid, pid, status) values" +
            " (default,#{user_id},#{solution_id},#{contest_id},#{problem_id},false)")
    void insert(String user_id,int solution_id,int contest_id,int problem_id);
}
