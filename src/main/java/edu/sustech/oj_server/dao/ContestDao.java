package edu.sustech.oj_server.dao;

import edu.sustech.oj_server.entity.Contest;
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

    @Select("select count(*) from contest")
    int getNum();

    @Select("select problem_id from contest_problem where contest_id = #{id} order by num")
    List<Integer> getProblemsID(int id);

    void insert(String title, String sescription, Timestamp start,Timestamp end);

}
