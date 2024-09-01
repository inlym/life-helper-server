-- --------------------------------------------------------
-- 短信验证码生命周期管理表
-- 对应实体: [com.weutil.sms.entity.PhoneCode]
-- --------------------------------------------------------

create table `phone_code`
(
    /* 下方是通用字段 */
    `id`                 bigint unsigned not null auto_increment comment '主键 ID',
    `create_time`        datetime        not null default current_timestamp comment '创建时间',
    `update_time`        datetime        not null default current_timestamp on update current_timestamp comment '更新时间',

    /* 下方为业务字段 */
    `phone`              char(11)        not null default '' comment '手机号',
    `code`               char(6)         not null default '' comment '短信验证码',
    `check_ticket`       char(32)        not null default '' comment '校验码',
    `ip`                 char(15)        not null default '' comment '客户端 IP 地址',
    `pre_send_time`      datetime                 default null comment '短信发送时间（发送前）',
    `res_code`           varchar(50)     not null default '' comment '请求状态码',
    `res_message`        varchar(200)    not null default '' comment '状态码的描述',
    `res_biz_id`         varchar(50)     not null default '' comment '发送回执 ID',
    `request_id`         varchar(50)     not null default '' comment '请求 ID',
    `post_send_time`     datetime                 default null comment '收到响应的时间',
    `first_attempt_time` datetime                 default null comment '用户首次尝试进行登录验证时间',
    `last_attempt_time`  datetime                 default null comment '用户最后一次尝试进行登录验证时间',
    `attempt_counter`    int             not null default 0 comment '用户尝试进行登录验证的次数',
    `succeed_time`       datetime                 default null comment '匹配成功时间',

    primary key (`id`)
) engine = InnoDB
  default character set = `utf8mb4` comment ='短信验证码生命周期管理表';
