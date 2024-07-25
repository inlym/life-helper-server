-- --------------------------------------------------------
-- 微信账户登录日志表
-- 对应实体: [com.weutil.account.login.wechat.entity.WeChatLoginLog]
-- --------------------------------------------------------

create table `login_wechat_log`
(
    /* 下方是通用字段 */
    `id`                      bigint unsigned not null auto_increment comment '主键 ID',
    `create_time`             datetime        not null default current_timestamp comment '创建时间',
    `update_time`             datetime        not null default current_timestamp on update current_timestamp comment '更新时间',

    /* 下方为业务字段 */
    `code`                    varchar(50)     not null default '' comment '微信小程序端通过 `wx.login` 获取的 code',
    `app_id`                  varchar(50)     not null default '' comment '小程序开发者 ID',
    `open_id`                 varchar(50)     not null default '' comment '小程序的用户唯一标识',
    `union_id`                varchar(50)     not null default '' comment '用户在微信开放平台的唯一标识符',
    `session_key`             varchar(100)    not null default '' comment '会话密钥',
    `user_account_we_chat_id` bigint unsigned not null comment '关联的用户微信账户表 ID',
    `user_id`                 bigint unsigned not null comment '对应的用户 ID',
    `token`                   char(32)        not null default '' comment '发放的鉴权令牌',
    `ip`                      char(15)        not null default '' comment '客户端 IP 地址',
    `login_time`              datetime        not null comment '登录时间',

    primary key (`id`)
) engine = InnoDB
  default character set = `utf8mb4`
    comment ='微信账户登录日志表';
