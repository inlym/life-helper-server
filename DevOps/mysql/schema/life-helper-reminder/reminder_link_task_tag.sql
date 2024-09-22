-- --------------------------------------------------------
-- 任务和标签关联表
-- 对应实体: [com.weutil.reminder.entity.LinkTaskTag]
-- --------------------------------------------------------

create table `reminder_link_task_tag`
(
    /* 下方是通用字段 */
    `id`          bigint unsigned not null auto_increment comment '主键 ID',
    `create_time` datetime        not null default current_timestamp comment '创建时间',
    `update_time` datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
    `delete_time` datetime                 default null comment '删除时间（逻辑删除标志）',

    /* 下方为业务字段 */
    `user_id`     bigint unsigned not null comment '所属用户 ID',
    `task_id`     bigint unsigned not null comment '所属任务 ID',
    `tag_id`      bigint unsigned not null comment '所属标签 ID',

    primary key (`id`)
) engine = InnoDB
  default character set = `utf8mb4` comment ='任务和标签关联表';
