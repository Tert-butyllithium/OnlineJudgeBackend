package edu.sustech.oj_server.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SimDao {
    @Insert("insert into sim (s_id, sim_s_id, sim) values (#{s_id},#{sim_s_id},#{sim})")
    void insert(Integer s_id,Integer sim_s_id,Integer sim);
}
