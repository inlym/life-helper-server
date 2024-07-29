-- --------------------------------------------------------
-- 待办任务表
-- 对应实体: [com.weutil.reminder.entity.Task]
-- --------------------------------------------------------

create table `reminder_task`
(
    /* 下方是通用字段 */
    `id`            bigint unsigned not null auto_increment comment '主键 ID',
    `create_time`   datetime        not null default current_timestamp comment '创建时间',
    `update_time`   datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
    `delete_time`   datetime                 default null comment '删除时间（逻辑删除标志）',

    /* 下方为业务字段 */
    `user_id`       bigint unsigned not null comment '所属用户 ID',
    `project_id`    bigint unsigned not null comment '所属项目 ID',
    `name`          varchar(30)     not null default '' comment '任务名称',
    `content`       varchar(1000)   not null default '' comment '任务描述内容文本',
    `complete_time` datetime                 default null comment '任务完成时间',
    `priority`      tinyint         not null default 0 comment '任务优先级（枚举值）',
    `due_date`      date                     default null comment '截止日期',
    `due_time`      time                     default null comment '截止时间',

    primary key (`id`)
) engine = InnoDB
  default character set = `utf8mb4` comment ='待办任务表';
