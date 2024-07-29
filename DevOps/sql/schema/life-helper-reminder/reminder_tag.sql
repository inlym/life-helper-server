-- --------------------------------------------------------
-- 待办任务标签表
-- 对应实体: [com.weutil.reminder.entity.Tag]
-- --------------------------------------------------------

create table `reminder_tag`
(
    /* 下方是通用字段 */
    `id`                     bigint unsigned not null auto_increment comment '主键 ID',
    `create_time`            datetime        not null default current_timestamp comment '创建时间',
    `update_time`            datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
    `delete_time`            datetime                 default null comment '删除时间（逻辑删除标志）',

    /* 下方为业务字段 */
    `user_id`                bigint unsigned not null comment '所属用户 ID',
    `name`                   varchar(30)     not null default '' comment '标签名称',
    `color`                  tinyint         not null default 0 comment '标记颜色（枚举值）',
    `favorite_time`          datetime                 default null comment '收藏时间',
    `uncompleted_task_count` bigint unsigned not null default 0 comment '未完成的任务数',

    primary key (`id`)
) engine = InnoDB
  default character set = `utf8mb4` comment ='待办任务标签表';
