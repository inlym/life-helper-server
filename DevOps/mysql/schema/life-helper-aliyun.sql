-- ----------------------------------------------------------------------------------------------------------------
-- 短信发送日志
-- 对应实体: [com.weutil.sms.entity.SmsLog]
-- 创建时间: 2024/11/04
-- ----------------------------------------------------------------------------------------------------------------

create table `sms_log`
(
    /* 下方是通用字段 */
    `id`             bigint unsigned not null auto_increment comment '主键 ID',
    `create_time`    datetime        not null default current_timestamp comment '创建时间',
    `update_time`    datetime        not null default current_timestamp on update current_timestamp comment '更新时间',

    /* 下方为业务字段 */
    `phone`          char(11)        not null default '' comment '手机号',
    `code`           char(6)         not null default '' comment '短信验证码',
    `ip`             char(15)        not null default '' comment '客户端 IP 地址',
    `pre_send_time`  datetime        not null comment '短信发送时间（发送前）',
    `res_code`       varchar(50)     not null default '' comment '请求状态码',
    `res_message`    varchar(200)    not null default '' comment '状态码的描述',
    `res_biz_id`     varchar(50)     not null default '' comment '发送回执 ID',
    `request_id`     varchar(50)     not null default '' comment '请求 ID',
    `post_send_time` datetime        not null comment '收到响应的时间',

    primary key (`id`)
) engine = InnoDB
  default character set = `utf8mb4` comment ='短信发送日志';
