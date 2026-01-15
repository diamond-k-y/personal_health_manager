package com.example.entity;

import lombok.Data;

import java.time.LocalDate;
/**
 * 运动记录实体类 (对应数据库表: sports_records)
 */
@Data
public class SportsRecords {
    /**
     * ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 运动类型 (如：跑步、游泳、骑车等)
     */
    private String type;

    /**
     * 运动时长 (单位: 分钟)
     */
    private Integer during;

    /**
     * 运动距离 (单位: 公里/km)
     */
    private Double distance;

    /**
     * 消耗卡路里 (单位: 千卡/kcal)
     */
    private Double calorie;

    /**
     * 记录日期
     */
    private String date;

    /**
     * 用户名
     */
    private String userName;
}
