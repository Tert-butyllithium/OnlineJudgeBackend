package edu.sustech.oj_server.dao;
import edu.sustech.oj_server.entity.SourceCode;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
public interface SourceCodeDao {

    @Select("select sc.solution_id,problem_id,in_date,user_id,source,result,language,time*1000,memory*1024 from solution join source_code sc on solution.solution_id = sc.solution_id where sc.solution_id=#{id}")
    SourceCode getSource(int id);

    @Select("select user_id from solution  where solution_id = #{id}")
    String getName(int id);

    @Insert("insert into source_code (solution_id, source) values (#{solution_id},#{source})")
    Integer submit(Integer solution_id,String source);
}
