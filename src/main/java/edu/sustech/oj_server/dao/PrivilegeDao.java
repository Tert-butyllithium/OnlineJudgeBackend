package edu.sustech.oj_server.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PrivilegeDao {

    @Select("select count(*) from privilege where user_id =#{id} and rightstr='administrator'")
    Integer getPrivilege(String id);

    @Select("select count(*) from privilege where user_id =#{id} and rightstr='c${contest}'")
    Integer getContestAccess(String id,String contest);

    @Insert("insert into privilege (user_id, rightstr, defunct)\n" +
            "values (#{id},'c${contest}','N');\n")
    void addContestAccess(String id,String contest);
}
