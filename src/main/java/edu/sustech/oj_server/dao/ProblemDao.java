package edu.sustech.oj_server.dao;

import edu.sustech.oj_server.entity.Problem;
import edu.sustech.oj_server.toolclass.Sample;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ProblemDao {
    @Select("select problem_id, title, description, input, output, sample_input, sample_output, spj, hint, source, in_date, time_limit*1000, memory_limit, defunct, accepted, submit, solved from problem where problem_id = #{id}")
    Problem getProblem(int id);

    @Select("select problem_id, title, description, input, output, sample_input, sample_output, spj, hint, source, in_date, time_limit, memory_limit, defunct, accepted, submit, solved\n" +
            "from problem where problem_id=(select problem_id from contest_problem where contest_problem.num=#{num} and contest_id=#{contest_id})")
    Problem getProblemInContest(int contest_id,int num);

    @Select("select problem_id, title, description, input, output, sample_input, sample_output, spj, hint, source, in_date, time_limit, memory_limit, defunct, accepted, submit, solved\n" +
            "from problem where problem_id in (select problem_id from contest_problem where contest_id=#{contest_id})")
    List<Problem> getProblemsInContest(int contest_id);

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

    @Delete("delete from contest_problem where contest_id=#{contest_id} and problem_id=#{id}")
    void deleteProblemInContest(Integer id,Integer contest_id);

    @Select("select input,output from extra_samples where problem_id = #{problem_id} order by id")
    List<Sample> getExtraSamples(Integer problem_id);

}
