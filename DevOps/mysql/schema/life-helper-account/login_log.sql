-- --------------------------------------------------------
-- 登录日志表
-- 对应实体: [com.weutil.login.common.entity.LoginLog]
-- --------------------------------------------------------

create table `login_log`
(
    /* 下方是通用字段 */
    `id`               bigint unsigned not null auto_increment comment '主键 ID',
    `create_time`      datetime        not null default current_timestamp comment '创建时间',
    `update_time`      datetime        not null default current_timestamp on update current_timestamp comment '更新时间',

    /* 下方为业务字段 */
    `type`             int             not null default 0 comment '登录方式类型（枚举值）',
    `channel`          int             not null default 0 comment '登录渠道（枚举值）',
    `user_id`          bigint unsigned not null comment '对应的用户 ID',
    `token`            char(32)        not null default '' comment '发放的鉴权令牌',
    `ip`               char(15)        not null default '' comment '客户端 IP 地址',
    `login_time`       datetime        not null comment '登录时间',
    `phone_account_id` bigint unsigned not null default 0 comment '关联的用户手机号账户表 ID',

    primary key (`id`)
) engine = InnoDB
  default character set = `utf8mb4`
    comment ='登录日志';
