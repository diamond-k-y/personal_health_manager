package com.example.entity;


import lombok.Data;

/**
 * 睡眠记录实体类 (对应数据库表: sleep_records)
 */
@Data
public class SleepRecords {
    /**
     * ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 入睡时间 (格式: HH:mm:ss 或 HH:mm)
     */
    private String sleepTime;

    /**
     * 起床时间 (格式: HH:mm:ss 或 HH:mm)
     */
    private String wakeupTime;

    /**
     * 睡眠时长 (单位: 小时/h)
     */
    private Double sleepDuration;

    /**
     * 睡眠质量 (例如：差、一般、良好、优秀)
     */
    private String sleepQuality;

    /**
     * 记录日期
     */
    private String date;

    /**
     * 用户名
     */
    private String userName;
}
