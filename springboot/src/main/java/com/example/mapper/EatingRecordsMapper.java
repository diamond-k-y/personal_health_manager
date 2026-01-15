package com.example.mapper;

import com.example.entity. EatingRecords;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EatingRecordsMapper {

    int insert( EatingRecords eatingRecords);

    void updateById( EatingRecords eatingRecords);

    void deleteById(Integer id);

    @Select("select * from `eating_records` where id = #{id}")
     EatingRecords selectById(Integer id);

    List< EatingRecords> selectAll( EatingRecords eatingRecords);

}
