package edu.sustech.oj_server.dao;

import edu.sustech.oj_server.entity.Clarification;
import edu.sustech.oj_server.entity.News;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NewsDao {

    @Select("select news_id, user_id, title, content, time, importance, defunct from news")
    List<News> listAllNews();

    @Select("select news_id, user_id, title, content, time, importance, defunct from news where defunct = 'N' and contest_id is NULL")
    List<News> listAllVisibleNews();

    @Select("select news_id, user_id, title, content, time, importance, defunct from news where defunct = 'N' and contest_id = #{contest_id}")
    List<News> listAllVisibleNewsForContest(Integer contest_id);

    @Select("select id, create_time, user_id, contest_id, message, reply from clarification where contest_id=#{contest_id} and user_id=#{user_id}")
    List<Clarification> listClarification(Integer contest_id,String user_id);

    @Insert("insert into clarification (id,create_time, user_id, contest_id, message, reply) values (default,default, #{user_id},#{contest_id},#{message},null);")
    void insertClarification(String user_id,Integer contest_id,String message);
}
