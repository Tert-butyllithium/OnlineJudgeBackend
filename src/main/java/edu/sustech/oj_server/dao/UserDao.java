package edu.sustech.oj_server.dao;

import edu.sustech.oj_server.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDao {

    @Select("select password from users where user_id = #{id} ")
    String getPassword(String id);

    @Select("select * from users where user_id = #{id} ")
    User getUser(String id);

    @Select("select * from users order by solved desc limit #{limit} offset #{offset}")
    List<User> getRank(Integer offset,Integer limit);

    @Select("select count(*) from users where submit>0")
    Integer getUserNumber();
}
