-- --------------------------------------------------------
-- 手机号（短信验证码）登录日志表
-- 对应实体: [com.inlym.lifehelper.account.login.phone.entity.PhoneSmsLoginLog]
-- --------------------------------------------------------

CREATE TABLE `login_phone_sms_log`
(
    /* 下方是通用字段 */
    `id`                    bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    `create_time`           datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`           datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    /* 下方为业务字段 */
    `phone`                 char(11)        NOT NULL DEFAULT '' COMMENT '手机号',
    `user_account_phone_id` bigint UNSIGNED NOT NULL COMMENT '关联的用户手机号账户表 ID',
    `user_id`               bigint UNSIGNED NOT NULL COMMENT '对应的用户 ID',
    `token`                 char(32)        NOT NULL DEFAULT '' COMMENT '发放的鉴权令牌',
    `ip`                    char(15)        NOT NULL DEFAULT '' COMMENT '客户端 IP 地址',
    `login_time`            datetime        NOT NULL COMMENT '登录时间',
    `code`                  char(6)         NOT NULL DEFAULT '' COMMENT '短信验证码',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT ='手机号（短信验证码）登录日志';
