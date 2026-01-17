项目主要技术栈：
后端：SpringBoot3 + MyBatis + Hutool
前端：Vue3 + Element-Plus + Vue-Router + Axios
数据库： MySQL
版本：JDK：17，MySQL：8.0，node.js：20.12.0，maven：3.9.4


第一天
登录页面
<img width="1919" height="903" alt="image" src="https://github.com/user-attachments/assets/9be75dde-8dce-48be-8f21-8258a49481cc" />
首页
<img width="1919" height="482" alt="image" src="https://github.com/user-attachments/assets/e88c553e-14e6-448e-ba5b-d8ac77559a44" />
可以对系统公告进行新增，批量删除，修改操作
<img width="1919" height="378" alt="image" src="https://github.com/user-attachments/assets/cfc7e7ee-fb26-43f5-b442-dcbb4c4f39cf" />
可以新增管理员，修改管理员信息，删除管理员等操作
<img width="1919" height="441" alt="image" src="https://github.com/user-attachments/assets/5fab9be1-92d8-49d0-997d-ddb07f440716" />


第二天
普通用户信息管理
数据库：沿用管理员的表
CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '账号',
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
  `role` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色',
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电话',
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='普通用户信息';

·用户表单验证
<img width="1919" height="898" alt="image" src="https://github.com/user-attachments/assets/a8cdb063-9b16-4449-8eba-3edd750aad53" />
先定义一个rules
<img width="789" height="258" alt="image" src="https://github.com/user-attachments/assets/0b8f3133-1b4a-4d40-85ce-b26b2a87c05f" />
绑定到表单上
<img width="1140" height="32" alt="image" src="https://github.com/user-attachments/assets/7915308d-d1ee-41c7-8e8c-9773b1bd52f0" />
定义一个formRef（注释的部分是简化版的表单提醒，只给出一个提示框）
<img width="518" height="384" alt="image" src="https://github.com/user-attachments/assets/12474486-cde1-4264-8a49-a7ba0547eec2" />

通过v-if来控制用户的访问权限
<img width="1919" height="394" alt="image" src="https://github.com/user-attachments/assets/2778ba8c-f662-4383-9603-d79d67a23d08" />

身体指标记录信息
SQL：
CREATE TABLE `body_records` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `height` double DEFAULT NULL COMMENT '身高',
  `weight` double DEFAULT NULL COMMENT '体重',
  `low_pressure` double DEFAULT NULL COMMENT '低压',
  `high_pressure` double DEFAULT NULL COMMENT '高压',
  `blood_sugar` double DEFAULT NULL COMMENT '血糖',
  `heart_rate` double DEFAULT NULL COMMENT '心率',
  `date` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '记录日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='身体指标记录';


·数据隔离：每个用户只能看到自己的身体指标数据，管理员可以查看所用用户的数据
<if test="userId != null"> and body_records.user_id = #{userId}</if>
<el-button type="primary" plain @click="handleAdd" v-if="data.user.role === 'USER'">新增</el-button>

·数据关联：左外连接，用户新增数据时将id也传入表中

身体指标记录
<img width="1919" height="529" alt="image" src="https://github.com/user-attachments/assets/18a26908-3099-49ed-8a5f-bc4d0c92d95b" />

第三天
运动记录管理
CREATE TABLE `sports_records` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '运动类型',
  `during` int(11) DEFAULT NULL COMMENT '运动时长',
  `distance` double DEFAULT NULL COMMENT '运动距离',
  `calorie` double DEFAULT NULL COMMENT '卡路里',
  `date` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '记录日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='运动记录';

<img width="1919" height="480" alt="image" src="https://github.com/user-attachments/assets/8a7f9c81-c6e3-4cf6-a9e6-2db40b00fb64" />
<img width="1919" height="459" alt="image" src="https://github.com/user-attachments/assets/e84addf7-5ec9-4184-b721-66595143b895" />

饮食记录管理
CREATE TABLE `eating_records` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `foods_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '食物名称',
  `intake` int(11) DEFAULT NULL COMMENT '摄入量',
  `calorie` int(11) DEFAULT NULL COMMENT '卡路里',
  `date` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '记录日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='饮食记录';
<img width="1919" height="531" alt="image" src="https://github.com/user-attachments/assets/4c9519dd-0e3e-4eee-9a5c-beff7edc0dc8" />



睡眠记录管理
CREATE TABLE `sleep_records` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `sleep_time` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '入睡时间',
  `wakeup_time` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '起床时间',
  `sleep_duration` double DEFAULT NULL COMMENT '睡眠时长',
  `sleep_quality` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '睡眠质量',
  `date` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '记录日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='睡眠记录';

<img width="1919" height="417" alt="image" src="https://github.com/user-attachments/assets/68afbf0b-ae6a-41fe-a5ce-4e4b3e0328f8" />
-如何计算睡眠的时间？
-统一计算公式
public void add(SleepRecords sleepRecords) {
        sleepRecords.setDate(DateUtil.today());
        // 统一计算的公式  入睡是昨天   起床是今天
        String sleepTime = DateUtil.formatDate(DateUtil.yesterday()) + " " + sleepRecords.getSleepTime() + ":00"; // 23:00   =>   2025-04-15 23:00:00
        String wakeupTime = DateUtil.today() + " " + sleepRecords.getWakeupTime() + ":00"; // 23:00   =>   2025-04-15 23:00:00
        long minutes = DateUtil.between(DateUtil.parseDateTime(sleepTime), DateUtil.parseDateTime(wakeupTime), DateUnit.MINUTE);// 计算分钟
        BigDecimal hours = BigDecimal.valueOf(minutes).divide(BigDecimal.valueOf(60), 1, RoundingMode.HALF_UP);
        sleepRecords.setSleepDuration(hours.doubleValue());
        sleepRecordsMapper.insert(sleepRecords);
    }
<img width="1919" height="460" alt="image" src="https://github.com/user-attachments/assets/925e7f93-daf8-4193-8273-ab94767ed064" />


第四天
开发帖子相关功能（富文本）
CREATE TABLE `forum` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '简介',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '封面',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '内容',
  `user_id` int DEFAULT NULL COMMENT '用户ID',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发帖时间',
  `read_count` int DEFAULT NULL COMMENT '浏览量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='帖子信息';

<img width="1902" height="895" alt="image" src="https://github.com/user-attachments/assets/066a13fe-dc7a-47e0-bcc0-24c944d2d317" />

新增审核状态字段
CREATE TABLE `forum` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '简介',
  `img` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '封面',
  `content` longtext COLLATE utf8mb4_unicode_ci COMMENT '内容',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `time` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发帖时间',
  `read_count` int(11) DEFAULT NULL COMMENT '浏览量',
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '审核状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='帖子信息';
<img width="1919" height="745" alt="image" src="https://github.com/user-attachments/assets/09f1b53b-51c1-41c9-afcc-54c6bb4e836d" />


更新浏览量
用户点击一次，readCount就执行一次update（自增一次）
@Update("update forum set read_count = read_count + 1 where id = #{id}")
void updateReadCountById(Integer id);


用户编辑帖子后触发重新审核流程
public void updateById(Forum forum) {
Account currentUser = TokenUtils.getCurrentUser();
// 用户编辑了内容  设置成待审核状态
if (RoleEnum.USER.name().equals(currentUser.getRole())) {
    forum.setStatus("待审核");
}
forumMapper.updateById(forum);
}
<img width="1919" height="520" alt="image" src="https://github.com/user-attachments/assets/2e9d77a2-d34a-47cb-8f33-86c02be228d8" />


第五天
评论表
CREATE TABLE `comment` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '内容',
  `user_id` int DEFAULT NULL COMMENT '用户ID',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '时间',
  `pid` int DEFAULT NULL COMMENT '父级ID',
  `fid` int DEFAULT NULL COMMENT '关联模块ID',
  `root_id` int DEFAULT NULL COMMENT '根节点ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='评论信息';

<img width="1910" height="895" alt="image" src="https://github.com/user-attachments/assets/2d471236-becf-425c-b66d-860b3505ae98" />




