package com.example.mapper;

import com.example.entity.BodyRecords;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BodyRecordsMapper {

    int insert(BodyRecords bodyRecords);

    void updateById(BodyRecords bodyRecords);

    void deleteById(Integer id);

    @Select("select * from `body_records` where id = #{id}")
    BodyRecords selectById(Integer id);

    List<BodyRecords> selectAll(BodyRecords bodyRecords);

}
