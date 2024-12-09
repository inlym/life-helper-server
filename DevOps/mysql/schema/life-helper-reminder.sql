create table `reminder_project`
(
    /* 下方是通用字段 */
    `id`                     bigint unsigned not null auto_increment comment '主键 ID',
    `create_time`            datetime        not null default current_timestamp comment '创建时间',
    `update_time`            datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
    `delete_time`            datetime                 default null comment '删除时间（逻辑删除标志）',

    /* 下方为业务字段 */
    `user_id`                bigint unsigned not null comment '所属用户 ID',
    `name`                   varchar(30)     not null default '' comment '项目名称',
    `color`                  tinyint         not null default 0 comment '标记颜色（枚举值）',
    `favorite_time`          datetime                 default null comment '收藏时间',
    `uncompleted_task_count` bigint unsigned not null default 0 comment '未完成的任务数',

    primary key (`id`)
) engine = InnoDB
  default character set = `utf8mb4` comment ='待办项目表';

create table `reminder_task`
(
    /* 下方是通用字段 */
    `id`            bigint unsigned not null auto_increment comment '主键 ID',
    `create_time`   datetime        not null default current_timestamp comment '创建时间',
    `update_time`   datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
    `delete_time`   datetime                 default null comment '删除时间（逻辑删除标志）',

    /* 下方为业务字段 */
    `user_id`       bigint unsigned not null comment '所属用户 ID',
    `project_id`    bigint unsigned not null default 0 comment '所属项目 ID',
    `name`          varchar(30)     not null default '' comment '任务名称',
    `content`       varchar(1000)   not null default '' comment '任务描述内容文本',
    `complete_time` datetime                 default null comment '任务完成时间',
    `priority`      tinyint         not null default 0 comment '任务优先级（枚举值）',
    `due_date`      date                     default null comment '截止日期',
    `due_time`      time                     default null comment '截止时间',

    primary key (`id`)
) engine = InnoDB
  default character set = `utf8mb4` comment ='待办任务表';

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
