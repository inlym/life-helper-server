-- ----------------------------------------------------------------------------------------------------------------
-- 待办项目表
-- 对应实体: [com.weutil.reminder.entity.ReminderProject]
-- 创建时间: 2024/12/12
-- ----------------------------------------------------------------------------------------------------------------

create table `reminder_project`
(
    /* 下方是通用字段 */
    `id`               bigint unsigned not null auto_increment comment '主键 ID',
    `create_time`      datetime        not null default current_timestamp comment '创建时间',
    `update_time`      datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
    `delete_time`      datetime                 default null comment '删除时间（逻辑删除标志）',
    `create_client_ip` varchar(15)     not null default '' comment '创建时的客户端 IP 地址',
    `update_client_ip` varchar(15)     not null default '' comment '最后一次更新时的客户端 IP 地址',
    `update_count`     bigint unsigned not null default 0 comment '更新次数',

    /* 下方为用户相关类数据表通用字段 */
    `user_id`          bigint unsigned not null comment '所属的用户 ID',

    /* 下方为业务字段 */
    `name`             varchar(30)     not null default '' comment '项目名称',

    primary key (`id`)
) engine = InnoDB
  default character set = `utf8mb4` comment ='待办项目表';

-- ----------------------------------------------------------------------------------------------------------------
-- 待办项目表
-- 对应实体: [com.weutil.reminder.entity.ReminderTask]
-- 创建时间: 2024/12/12
-- ----------------------------------------------------------------------------------------------------------------

create table `reminder_task`
(
    /* 下方是通用字段 */
    `id`               bigint unsigned not null auto_increment comment '主键 ID',
    `create_time`      datetime        not null default current_timestamp comment '创建时间',
    `update_time`      datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
    `delete_time`      datetime                 default null comment '删除时间（逻辑删除标志）',
    `create_client_ip` varchar(15)     not null default '' comment '创建时的客户端 IP 地址',
    `update_client_ip` varchar(15)     not null default '' comment '最后一次更新时的客户端 IP 地址',
    `update_count`     bigint unsigned not null default 0 comment '更新次数',

    /* 下方为用户相关类数据表通用字段 */
    `user_id`          bigint unsigned not null comment '所属的用户 ID',

    /* 下方为业务字段 */
    `project_id`       bigint unsigned not null default 0 comment '所属项目 ID',
    `name`             varchar(30)     not null default '' comment '任务名称',
    `content`          varchar(1000)   not null default '' comment '任务描述内容文本',
    `complete_time`    datetime                 default null comment '任务完成时间',
    `due_date_time`    datetime                 default null comment '截止期限（日期+时间）',
    `due_date`         date                     default null comment '截止期限的日期部分（年月日）',
    `due_time`         time                     default null comment '截止期限的时间部分（时分秒）',

    #     `priority`      tinyint         not null default 0 comment '任务优先级（枚举值）',

    primary key (`id`)
) engine = InnoDB
  default character set = `utf8mb4` comment ='待办任务表';
