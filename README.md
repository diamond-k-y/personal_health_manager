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

·数据关联：左外连接，用户新增数据时将id也传入表中

身体指标记录
<img width="1919" height="529" alt="image" src="https://github.com/user-attachments/assets/18a26908-3099-49ed-8a5f-bc4d0c92d95b" />

第三天








