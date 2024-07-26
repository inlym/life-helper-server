-- --------------------------------------------------------
-- 用户账户表
-- 对应实体: [com.weutil.account.entity.User]
-- --------------------------------------------------------

create table `user`
(
    /* 下方是通用字段 */
    `id`              bigint unsigned not null auto_increment comment '主键 ID',
    `create_time`     datetime        not null default current_timestamp comment '创建时间',
    `update_time`     datetime        not null default current_timestamp on update current_timestamp comment '更新时间',

    /* 下方为业务字段 */
    `nick_name`       varchar(20)     not null default '' comment '昵称',
    `avatar_path`     varchar(100)    not null default '' comment '头像路径',
    `register_time`   datetime        not null comment '注册时间',
    `login_counter`   bigint unsigned not null default 0 comment '登录次数',
    `last_login_time` datetime                 default null comment '最近一次登录时间',
    `last_login_ip`   char(15)        not null default '' comment '最近一次登录的 IP 地址',

    primary key (`id`)
) engine = InnoDB
  default character set = `utf8mb4`
    comment ='用户账户表';
