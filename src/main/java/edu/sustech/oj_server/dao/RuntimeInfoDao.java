package edu.sustech.oj_server.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RuntimeInfoDao {

    @Select("select error from runtimeinfo where solution_id=#{id}")
    String getInfo(Integer id);

    @Select("select error from compileinfo where solution_id=#{id}")
    String getCompileInfo(Integer id);
}
