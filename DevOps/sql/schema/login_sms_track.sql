-- --------------------------------------------------------
-- 登录短信验证码生命周期追踪表
-- 对应实体: [com.weutil.account.login.phone.entity.LoginSmsTrack]
-- --------------------------------------------------------

CREATE TABLE `login_sms_track`
(
    /* 下方是通用字段 */
    `id`                     BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    `create_time`            DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`            DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    /* 下方为业务字段 */
    `phone`                  CHAR(11)        NOT NULL DEFAULT '' COMMENT '手机号',
    `code`                   CHAR(6)         NOT NULL DEFAULT '' COMMENT '短信验证码',
    `check_ticket`           CHAR(32)        NOT NULL DEFAULT '' COMMENT '校验码',
    `ip`                     CHAR(15)        NOT NULL DEFAULT '' COMMENT '客户端 IP 地址',
    `pre_send_time`          DATETIME        NOT NULL COMMENT '短信发送时间（发送前）',
    `sending_status`         SMALLINT        NOT NULL COMMENT '发送状态',
    `res_code`               VARCHAR(50)     NOT NULL DEFAULT '' COMMENT '请求状态码',
    `res_message`            VARCHAR(200)    NOT NULL DEFAULT '' COMMENT '状态码的描述',
    `res_biz_id`             VARCHAR(50)     NOT NULL DEFAULT '' COMMENT '发送回执 ID',
    `request_id`             VARCHAR(50)     NOT NULL DEFAULT '' COMMENT '请求 ID',
    `post_send_time`         DATETIME                 DEFAULT NULL COMMENT '收到响应的时间',
    `first_attempt_time`     DATETIME                 DEFAULT NULL COMMENT '用户首次尝试进行登录验证时间',
    `last_attempt_time`      DATETIME                 DEFAULT NULL COMMENT '用户最后一次尝试进行登录验证时间',
    `attempt_counter`        INT             NOT NULL DEFAULT 0 COMMENT '用户尝试进行登录验证的次数',
    `succeed_time`           DATETIME                 DEFAULT NULL COMMENT '匹配成功时间',
    `phone_sms_login_log_id` BIGINT UNSIGNED          DEFAULT NULL COMMENT '登录成功后记录关联的手机号验证码登录日志表 ID',
    `user_account_phone_id`  BIGINT UNSIGNED          DEFAULT NULL COMMENT '登录成功后记录关联的用户手机号账户表 ID',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT ='登录短信验证码生命周期追踪表';
