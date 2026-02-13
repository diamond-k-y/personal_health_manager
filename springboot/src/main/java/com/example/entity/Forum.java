package com.example.entity;

import lombok.Data;

/**
 * 帖子信息实体类 (对应数据库表: forum)
 */
@Data
public class Forum {
    /**
     * ID
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 简介
     */
    private String description;

    /**
     * 封面图片链接
     */
    private String img;

    /**
     * 内容 (使用 longtext 存储，可能包含大量文本或HTML)
     */
    private String content;

    /**
     * 用户ID (发帖人)
     */
    private Integer userId;

    /**
     * 发帖时间 (建议在数据库和代码中使用更标准的日期类型如 DATETIME/TIMESTAMP，
     * 当前为 varchar，需注意格式转换)
     */
    private String time;

    /**
     * 浏览量
     */
    private Integer readCount;

    /**
     * 用户名 (发帖人)
     */
    private String userName;

    /**
     * 状态
     */
    private String status;

    /**
     * 用户头像
     */
    private String userAvatar;
}
