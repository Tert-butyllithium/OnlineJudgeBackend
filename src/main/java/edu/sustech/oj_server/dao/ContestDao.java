package edu.sustech.oj_server.dao;

import edu.sustech.oj_server.entity.Contest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface ContestDao {

    @Select("select * from contest where contest_id = #{id}")
    Contest getContest(int id);

    @Select("select * from contest order by contest_id desc limit #{limit} offset #{offset}")
    List<Contest> listAllContest(int offset,int limit);

    @Select("select * from contest where private=0 order by contest_id desc limit #{limit} offset #{offset}")
    List<Contest> listAllVisibleContest(int offset,int limit);

    @Select("select count(*) from contest")
    int getNum();

    @Select("select problem_id from contest_problem where contest_id = #{id} order by num")
    List<Integer> getProblemsID(int id);

    @Insert("insert into contest (contest_id, title, start_time, end_time, defunct," +
            " description, private, langmask, password,frozen_time)" +
            "values (default,#{title},#{start},#{end},'N',#{description},#{private1},0,#{password},#{frozen_time});")
    void insert(String title, String description, Timestamp start,Timestamp end,String password,Integer frozen_time,int private1);

}
