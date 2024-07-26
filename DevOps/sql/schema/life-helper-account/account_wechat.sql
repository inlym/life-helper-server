-- --------------------------------------------------------
-- 用户手机号账户表
-- 对应实体: [com.weutil.account.user.entity.UserAccountWeChat]
-- --------------------------------------------------------

create table `account_wechat`
(
    /* 下方是通用字段 */
    `id`              bigint unsigned not null auto_increment comment '主键 ID',
    `create_time`     datetime        not null default current_timestamp comment '创建时间',
    `update_time`     datetime        not null default current_timestamp on update current_timestamp comment '更新时间',
    `delete_time`     datetime                 default null comment '删除时间（逻辑删除标志）',

    /* 下方为业务字段 */
    `user_id`         bigint unsigned not null comment '对应的用户 ID',
    `login_counter`   bigint unsigned not null default 0 comment '登录次数',
    `last_login_time` datetime                 default null comment '最近一次登录时间',
    `last_login_ip`   char(15)        not null default '' comment '最近一次登录的 IP 地址',
    `app_id`          varchar(50)     not null default '' comment '小程序开发者 ID',
    `open_id`         varchar(50)     not null default '' comment '小程序的用户唯一标识',
    `union_id`        varchar(50)     not null default '' comment '用户在微信开放平台的唯一标识符',

    primary key (`id`)
) engine = InnoDB
  default character set = `utf8mb4`
    comment ='用户微信账户表';
