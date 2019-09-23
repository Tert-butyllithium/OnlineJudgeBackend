package edu.sustech.oj_server.dao;

import edu.sustech.oj_server.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

    @Select("select password from users where user_id = #{id} ")
    String getPassword(String id);

    @Select("select * from users where user_id = #{id} ")
    User getUser(String id);
}
