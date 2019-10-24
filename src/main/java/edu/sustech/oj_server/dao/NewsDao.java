package edu.sustech.oj_server.dao;

import edu.sustech.oj_server.entity.News;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NewsDao {

    @Select("select news_id, user_id, title, content, time, importance, defunct from news")
    List<News> listAllNews();

    @Select("select news_id, user_id, title, content, time, importance, defunct from news where defunct = 'N'")
    List<News> listAllVisibleNews();
}
