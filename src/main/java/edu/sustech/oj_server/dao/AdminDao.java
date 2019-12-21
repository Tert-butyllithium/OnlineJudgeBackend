package edu.sustech.oj_server.dao;

import edu.sustech.oj_server.toolclass.DashBoard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminDao {

    @Select("select * from (select count(*) as user_count from users where submit>5) as a ,\n" +
            "              (select count(*) as recent_contest_count from contest where end_time>now() and start_time <now()) as b,\n" +
            "              (select count(*) as today_submission_count from solution where in_date>curdate()) as c")
    DashBoard getDashBoard();
}
