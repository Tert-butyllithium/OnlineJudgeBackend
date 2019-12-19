package edu.sustech.oj_server.dao;

import edu.sustech.oj_server.entity.Problem;
import edu.sustech.oj_server.toolclass.Sample;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProblemDao {
    @Select("select problem_id, title, description, input, output, sample_input, sample_output, spj, hint, source, in_date, time_limit*1000, memory_limit, defunct, accepted, submit, solved from problem where problem_id = #{id}")
    Problem getProblem(int id);

    @Select("select problem_id, title, description, input, output, sample_input, sample_output, spj, hint, source, in_date, time_limit, memory_limit, defunct, accepted, submit, solved\n" +
            "from problem where problem_id=(select problem_id from contest_problem where contest_problem.num=#{num} and contest_id=#{contest_id})")
    Problem getProblemInContest(int contest_id,int num);

    @Select("select num from contest_problem where contest_id=#{contest_id} and problem_id=#{problem_id}")
    Integer getNumInContest(int contest_id,int problem_id);

    @Select({"<script>",
            "select problem_id, title, description, input, output, sample_input, sample_output, spj, hint, source, in_date, time_limit*1000, memory_limit, defunct, accepted, submit, solved from problem",
            " where defunct='N'" ,
            "<when test='keywords !=null'> ",
            "and (problem_id like  CONCAT('%',#{keywords},'%') or title like CONCAT('%',#{keywords},'%'))",
            "</when>",
            " order by problem_id limit #{limit} offset #{offset}",
            "</script>"})
    List<Problem> listAllProblems(String keywords,Integer offset, Integer limit);

    @Select({"<script>",
            "select problem_id, title, description, input, output, sample_input, sample_output, spj, hint, source, in_date, time_limit*1000, memory_limit, defunct, accepted, submit, solved from problem " ,
            "<when test='keywords !=null'> ",
            "where (problem_id like CONCAT('%',#{keywords},'%')  or title like CONCAT('%',#{keywords},'%'))",
            "</when>",
            "order by problem_id limit #{limit} offset #{offset}",
            "</script>"})
    List<Problem> listAllProblemsForAdmin(String keywords, Integer offset, Integer limit);


    @Select({"<script>",
            "select count(*) from problem where defunct='N' ",
            "<when test='keywords !=null'> ",
            "and (problem_id like CONCAT('%',#{keywords},'%')  or title like CONCAT('%',#{keywords},'%'))",
            "</when>",
            "</script>"})
    int getNum(String keywords);

    @Select({"<script>",
            "select count(*) from problem",
            "<when test='keywords !=null'> ",
            "where (problem_id like CONCAT('%',#{keywords},'%')  or title like CONCAT('%',#{keywords},'%'))",
            "</when>",
            "</script>"})
    int getNumForAdmin(String keywords);

    @Select("select count(*) from solution where solution.user_id=#{user_id} and solution.problem_id = #{problem_id} and solution.result=4 order by solution_id limit 1")
    Integer ACinProblems(String user_id,Integer problem_id);

    @Select({"<script>",
            "select count(*) from solution where " ,
            "solution.user_id=#{user_id} " ,
            "and solution.problem_id=#{problem_id} " ,
            "and result!=4",
            "<when test='solution_id !=null'> ",
            "and solution_id &lt; #{solution_id}",
            "and result !=11",
            "and result !=2",
            "and result !=3",
            "</when>",
            "<when test='contest_id !=null'> ",
            "and contest_id=#{contest_id}",
            "</when>",
            "</script>"})
    Integer Was(String user_id,Integer problem_id,Integer solution_id,Integer contest_id);

    @Select("select count(*) from solution where solution.user_id=#{user_id} and solution.problem_id = #{problem_id} and solution.result=4 and solution.contest_id=#{contest_id} order by solution_id limit 1")
    Integer ACinContest(String user_id,Integer problem_id,Integer contest_id);

    @Select("select count(*) from solution where solution.problem_id = #{problem_id} and solution.result=4 and solution.contest_id=#{contest_id}")
    Integer getProblemACinContest(Integer problem_id,Integer contest_id);

    @Select("select count(*) from solution where solution.problem_id = #{problem_id} and solution.contest_id=#{contest_id}")
    Integer getProblemSubmissionInContest(Integer problem_id,Integer contest_id);

    @Select("select count(*) from solution where solution.problem_id = #{problem_id} and solution.result=4")
    Integer getProblemAC(Integer problem_id);

    @Select("select count(*) from solution where solution.problem_id = #{problem_id}")
    Integer getProblemSubmission(Integer problem_id);

    @Update("update problem set accepted = #{ac}, submit = #{total} where problem_id=#{problem_id}")
    void updateSubmissionInfo(Integer problem_id,Integer ac,Integer total);

    @Delete("delete from contest_problem where contest_id=#{contest_id} and problem_id=#{id}")
    void deleteProblemInContest(Integer id,Integer contest_id);

    @Select("select input,output from extra_samples where problem_id = #{problem_id} order by id")
    List<Sample> getExtraSamples(Integer problem_id);

    @Select("select AUTO_INCREMENT from information_schema.TABLES where  TABLE_SCHEMA='jol' and TABLE_NAME='problem'")
    Integer getNextId();

    @Insert("insert into problem (title, description, input, output, sample_input, sample_output, spj, hint, source, in_date, time_limit, memory_limit, defunct) " +
            "values (#{title},#{description},#{input_description},#{output_description},'${samples.get(0).getInput()}','${samples.get(0).getOutput()}'," +
            "#{spj},#{hint},#{source},now(),#{time_limit}/1000,#{memory_limit},#{defunct})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    Integer insertProblem(Problem problem);

    @Insert("insert into extra_samples (problem_id,input,output) values (#{problem_id},#{input},#{output})")
    void InsertExtraSamples(Integer problem_id,String input,String output);

    @Update("replace into problem (problem_id,title, description, input, output, sample_input, sample_output, spj, hint, source, in_date, time_limit, memory_limit, defunct) " +
            "values (#{id},#{title},#{description},#{input_description},#{output_description},'${samples.get(0).getInput()}','${samples.get(0).getOutput()}'," +
            "#{spj},#{hint},#{source},now(),#{time_limit}/1000,#{memory_limit},#{defunct})")
    void updateProblem(Problem problem);

    @Delete("delete from extra_samples where problem_id=#{id}")
    void clearExtraSamples(Integer id);
}
