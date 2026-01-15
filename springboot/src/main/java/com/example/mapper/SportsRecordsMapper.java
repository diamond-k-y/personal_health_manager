package com.example.mapper;

import com.example.entity.SportsRecords;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SportsRecordsMapper {

    int insert(SportsRecords sportsRecords);

    void updateById(SportsRecords sportsRecords);

    void deleteById(Integer id);

    @Select("select * from `sports_records` where id = #{id}")
    SportsRecords selectById(Integer id);

    List<SportsRecords> selectAll(SportsRecords sportsRecords);

}
