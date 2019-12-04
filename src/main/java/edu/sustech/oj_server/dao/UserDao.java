package edu.sustech.oj_server.dao;

import edu.sustech.oj_server.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    @Insert("insert into users (user_id, email, submit, solved, defunct, ip, accesstime, volume, language, password, reg_time, nick, school)\n" +
            "values (#{id},#{email},0,0,'N',default,default,default,default,#{password},now(),#{id},default)")
    void insert(String id,String password,String email);

    @Update("update users set password = #{password} where user_id=#{id}")
    void updatePassword(String id,String password);
}
