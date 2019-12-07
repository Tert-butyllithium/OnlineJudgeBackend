package edu.sustech.oj_server.dao;
import edu.sustech.oj_server.entity.SourceCode;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface SourceCodeDao {

    @Select("select sc.solution_id,problem_id,in_date,user_id,source,result,language,time*1000,memory*1024 from solution join source_code sc on solution.solution_id = sc.solution_id where sc.solution_id=#{id}")
    SourceCode getSource(int id);

    @Select("select user_id from solution  where solution_id = #{id}")
    String getName(int id);

    @Insert("insert into source_code (solution_id, source) values (#{solution_id},#{source})")
    Integer submit(Integer solution_id,String source);

    @Select("select solution.solution_id, problem_id, in_date, user_id, source_code.source, result, language, time*1000, memory*1024\n" +
            "        from solution join source_code on source_code.solution_id=solution.solution_id\n" +
            "        where contest_id=#{contestId} and problem_id =#{problemId} and language=#{languageId}")
    List<SourceCode> getSourceCodeByCTL(int contestId, int problemId, int languageId);
}
