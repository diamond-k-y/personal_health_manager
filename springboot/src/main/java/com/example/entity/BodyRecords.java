package com.example.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BodyRecords {
    /**
     * ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 身高 (单位: cm)
     */
    private Double height;

    /**
     * 体重 (单位: kg)
     */
    private Double weight;

    /**
     * 低压 (舒张压, 单位: mmHg)
     */
    private Double lowPressure;

    /**
     * 高压 (收缩压, 单位: mmHg)
     */
    private Double highPressure;

    /**
     * 血糖 (通常指空腹血糖, 单位: mmol/L 或 mg/dL)
     */
    private Double bloodSugar;

    /**
     * 心率 (单位: bpm, beats per minute)
     */
    private Double heartRate;

    /**
     * 记录日期
     */
    private String date;

    /**
     * 用户名称 (关联查询字段)
     */
    private String userName;
}
