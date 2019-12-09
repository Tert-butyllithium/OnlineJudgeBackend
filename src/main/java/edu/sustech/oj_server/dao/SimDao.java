package edu.sustech.oj_server.dao;

import edu.sustech.oj_server.entity.Sim;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SimDao {
    @Insert("insert into sim (s_id, sim_s_id, sim) values (#{s_id},#{sim_s_id},#{sim})")
    void insert(Integer s_id,Integer sim_s_id,Integer sim);

    @Select("select s_id, sim_s_id, sim from sim where s_id=#{s_id} and sim_s_id=#{sim_s_id}")
    Sim getSim(Integer s_id, Integer sim_s_id);
}
