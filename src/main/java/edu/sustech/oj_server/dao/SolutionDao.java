package edu.sustech.oj_server.dao;

import edu.sustech.oj_server.entity.Solution;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SolutionDao {

    @Select("select * from solution where solution_id=#{id}")
    Solution getSolution(int id);

//    List<Solution> getSolutions(int limit,int offset);

    //select * from solution order by solution_id desc limit 20 offset 0;
    @Select({"<script>",
            "select * from solution",
            "where problem_id>0 and contest_id is NULL",
            "<when test='user!=null'>",
            "and  user_id= #{user}",
            "</when>",
            "<when test='result!=null'>",
            "and result= #{result}",
            "</when>",
            "<when test='problem_id!=null'>",
            "and problem_id= #{problem_id}",
            "</when>",
            "order by solution_id desc limit #{limit} offset #{offset}",
            "</script>"})
    List<Solution> getSolutionsBy(String user,Integer result,Integer problem_id,int limit,int offset);

    @Select({"<script>",
            "select * from solution",
            "where problem_id>0",
            "<when test='user!=null'>",
            "and  user_id= #{user}",
            "</when>",
            "<when test='result!=null'>",
            "and result= #{result}",
            "</when>",
            "<when test='problem_id!=null'>",
            "and problem_id= #{problem_id}",
            "</when>",
            "order by solution_id desc limit #{limit} offset #{offset}",
            "</script>"})
    List<Solution> getSolutionsForAdminBy(String user,Integer result,Integer problem_id,int limit,int offset);

    //select c.num, solution.problem_id, user_id, time, memory, in_date, result, language, ip, solution.contest_id, valid, solution.num, code_length, judgetime, pass_rate, lint_error, judger from solution join contest_problem c on solution.contest_id = c.contest_id where c.contest_id = '1054'
    @Select({"<script>",
            "select solution.solution_id, solution.problem_id as problem_id, user_id, time, memory, in_date, result, language, ip, solution.contest_id, valid, solution.num, code_length, judgetime, pass_rate, lint_error, judger, checked from solution join contest_problem c on solution.problem_id = c.problem_id and solution.contest_id =c.contest_id",
            "where c.contest_id=#{contest_id}",
            "<when test='user!=null'>",
            "and  user_id= #{user}",
            "</when>",
            "<when test='result!=null'>",
            "and result= #{result}",
            "</when>",
            "<when test='problem_id!=null'>",
            "and solution.problem_id= #{problem_id}",
            "</when>",
            "order by solution.solution_id desc limit #{limit} offset #{offset}",
            "</script>"})
    List<Solution> getSolutionsInContestBy(String user,Integer result,Integer problem_id,int contest_id,int limit,int offset);

    @Select("select * from solution "+
            "where contest_id =#{contest_id} and result!=11 order by solution_id")
    List<Solution> listSolutionsInContest(int contest_id);

    @Select({"<script>",
            "select count(*) from solution",
            "where problem_id>0 and contest_id is NULL",
            "<when test='user!=null'>",
            "and  user_id= #{user}",
            "</when>",
            "<when test='result!=null'>",
            "and result= #{result}",
            "</when>",
            "<when test='problem_id!=null'>",
            "and problem_id= #{problem_id}",
            "</when>",
            "</script>"})
    int getNum(String user,Integer result,Integer problem_id);

    @Select({"<script>",
            "select count(*) from solution",
            "where problem_id>0",
            "<when test='user!=null'>",
            "and  user_id= #{user}",
            "</when>",
            "<when test='result!=null'>",
            "and result= #{result}",
            "</when>",
            "<when test='problem_id!=null'>",
            "and problem_id= #{problem_id}",
            "</when>",
            "</script>"})
    int getNumForAdmin(String user,Integer result,Integer problem_id);

    @Select({"<script>",
            "select count(*) from solution ",
            "where contest_id=#{contest_id}",
            "<when test='user!=null'>",
            "and  user_id= #{user}",
            "</when>",
            "<when test='result!=null'>",
            "and result= #{result}",
            "</when>",
            "<when test='problem_id!=null'>",
            "and problem_id= #{problem_id}",
            "</when>",
            "</script>"})
    int getNumInContest(String user,Integer result,Integer problem_id,int contest_id);

    @Insert("insert into solution (problem_id,user_id, in_date, language, ip, contest_id, num) values (#{problem}," +
            "#{username},now()" +
            ",#{language},'172.18.1.122',#{contestId},#{num})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "solution_id")
    int submit(Solution solution);

    @Update("update solution set code_length = (select length(source) from source_code where source_code.solution_id=#{id}) where solution_id=#{id}")
    void update(Integer id);

    @Update("update solution set result = 0 where solution_id=#{id}")
    void rejugde(Integer id);

    @Select("select contest_id from solution where solution_id=#{solution_id}")
    Integer getContestId(Integer solution_id);
}
