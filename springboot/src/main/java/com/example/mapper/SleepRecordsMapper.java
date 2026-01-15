package com.example.mapper;

import com.example.entity.SleepRecords;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SleepRecordsMapper {

    int insert( SleepRecords sleepRecords);

    void updateById( SleepRecords sleepRecords);

    void deleteById(Integer id);

    @Select("select * from `sleep_records` where id = #{id}")
     SleepRecords selectById(Integer id);

    List< SleepRecords> selectAll( SleepRecords sleepRecords);

}
