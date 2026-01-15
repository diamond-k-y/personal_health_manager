package com.example.entity;


import lombok.Data;

/**
 * 饮食记录实体类 (对应数据库表: eating_records)
 */
@Data
public class EatingRecords {
    /**
     * ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 食物名称
     */
    private String foodsName;

    /**
     * 摄入量 (单位: 克/g 或 毫升/ml，具体取决于食物类型)
     */
    private Integer intake;

    /**
     * 卡路里 (单位: 千卡/kcal)
     */
    private Integer calorie;

    /**
     * 记录日期
     */
    private String date;

    /**
     * 用户名
     */
    private String userName;
}
