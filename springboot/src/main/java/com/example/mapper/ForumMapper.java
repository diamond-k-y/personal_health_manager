package com.example.mapper;

import com.example.entity.Forum;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ForumMapper {

    int insert(Forum forum);

    void updateById(Forum forum);

    void deleteById(Integer id);

    @Select("select * from `forum` where id = #{id}")
    Forum selectById(Integer id);

    List<Forum> selectAll(Forum forum);

}
