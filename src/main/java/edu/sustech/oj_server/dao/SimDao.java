package edu.sustech.oj_server.dao;

import edu.sustech.oj_server.entity.Sim;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SimDao {
    @Insert("insert into sim (s_id, sim_s_id, sim) values (#{s_id},#{sim_s_id},#{sim})")
    void insert(Integer s_id,Integer sim_s_id,Integer sim);

    @Select("select s_id, sim_s_id, s_username, solution.user_id as sim_username, problem, nt.language, per from (\n" +
            "  select sim.s_id, sim.sim_s_id, solution.user_id as s_username, solution.problem_id as problem, solution.language, sim.sim as per from sim join solution on s_id=solution_id\n" +
            "      ) as nt join solution on sim_s_id=solution.solution_id where s_id=#{s_id} and sim_s_id=#{sim_s_id}")
    Sim getSim(Integer s_id, Integer sim_s_id);

    @Select("select s_id, sim_s_id, s_username, solution.user_id as sim_username, problem, nt.language, per from (\n" +
            "  select sim.s_id, sim.sim_s_id, solution.user_id as s_username, solution.problem_id as problem, solution.language, sim.sim as per from sim join solution on s_id=solution_id\n" +
            "      ) as nt join solution on sim_s_id=solution.solution_id where contest_id=#{contest} order by per desc")
    List<Sim> getSimList(Integer contest);
}
