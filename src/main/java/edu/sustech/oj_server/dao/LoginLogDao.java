package edu.sustech.oj_server.dao;

import edu.sustech.oj_server.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginLogDao {

    @Select("select password from loginlog where user_id = #{id} order by time desc limit 1")
    String getLastInfo(String id);

    @Insert("insert into loginlog values (#{id},#{password},#{ip},now())")
    void insertWithIp(String id,String password,String ip);

    @Insert("insert into loginlog values (#{id},#{password},'172.18.1.122',now())")
    void insert(String id,String password);

    @Select("select * from users where user_id =(select user_id from loginlog where password =#{sessionID} order by time desc limit 1);")
    User findUser(String sessionid);
}
