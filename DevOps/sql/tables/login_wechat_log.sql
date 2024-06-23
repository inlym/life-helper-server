-- --------------------------------------------------------
-- 微信账户登录日志表
-- 对应实体: [com.inlym.lifehelper.account.login.wechat.entity.WeChatLoginLog]
-- --------------------------------------------------------

CREATE TABLE `login_wechat_log`
(
    /* 下方是通用字段 */
    `id`                      bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    `create_time`             datetime        NOT NULL COMMENT '创建时间',
    `update_time`             datetime        NOT NULL COMMENT '更新时间',
    `auto_create_time`        datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '由数据库维护的创建时间',
    `auto_update_time`        datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '由数据库维护的更新时间',
    /* 下方为业务字段 */
    `code`                    varchar(50)     NOT NULL DEFAULT '' COMMENT '微信小程序端通过 `wx.login` 获取的 code',
    `app_id`                  varchar(50)     NOT NULL DEFAULT '' COMMENT '小程序开发者 ID',
    `open_id`                 varchar(50)     NOT NULL DEFAULT '' COMMENT '小程序的用户唯一标识',
    `union_id`                varchar(50)     NOT NULL DEFAULT '' COMMENT '用户在微信开放平台的唯一标识符',
    `session_key`             varchar(100)    NOT NULL DEFAULT '' COMMENT '会话密钥',
    `user_account_we_chat_id` bigint UNSIGNED NOT NULL COMMENT '关联的用户微信账户表 ID',
    `user_id`                 bigint UNSIGNED NOT NULL COMMENT '对应的用户 ID',

    `token`                   char(32)        NOT NULL DEFAULT '' COMMENT '发放的鉴权令牌',
    `ip`                      char(15)        NOT NULL DEFAULT '' COMMENT '客户端 IP 地址',
    `login_time`              datetime        NOT NULL COMMENT '登录时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT ='微信账户登录日志表';
