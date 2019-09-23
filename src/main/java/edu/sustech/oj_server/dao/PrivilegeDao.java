package edu.sustech.oj_server.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PrivilegeDao {

    @Select("select count(*) from privilege where user_id =#{id} and rightstr='administrator'")
    Integer getPrivilege(String id);
}
