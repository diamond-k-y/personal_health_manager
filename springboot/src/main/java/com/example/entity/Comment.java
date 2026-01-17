package com.example.entity;
import lombok.Data;

import java.util.List;

@Data
public class Comment {
    /**
     * ID
     */
    private Integer id;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 用户ID (评论人)
     */
    private Integer userId;

    /**
     * 评论时间
     * 建议在数据库和代码中使用更标准的日期类型，当前为 varchar。
     */
    private String time;

    /**
     * 父级ID
     * - 如果 pid 为 null 或 0，则此评论是直接对主帖 (fid) 的一级评论。
     * - 如果 pid 不为 null，则此评论是回复某一条特定评论 (pid 指向那条评论的 id)。
     */
    private Integer pid;

    /**
     * 关联模块ID (例如：关联的 forum 帖子的 id)
     * 用于标识这条评论属于哪个帖子或文章。
     */
    private Integer fid;

    /**
     * 根节点ID
     * 指向这条评论所在讨论链的最顶层父评论的 id。
     * - 对于一级评论 (pid 为空)，rootId 通常为它自身的 id 或 null/0。
     * - 对于嵌套回复，rootId 指向它所回复的那条“根”评论的 id。
     * 这个字段极大地方便了按“根评论”来分组和查询整个讨论线程。
     */
    private Integer rootId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 帖子标题
     */
    private String forumTitle;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 子评论列表
     */
    private List<Comment> children;
}
