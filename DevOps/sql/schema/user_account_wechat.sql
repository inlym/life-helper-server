-- --------------------------------------------------------
-- 用户手机号账户表
-- 对应实体: [com.weutil.account.user.entity.UserAccountWeChat]
-- --------------------------------------------------------

CREATE TABLE `user_account_wechat`
(
    /* 下方是通用字段 */
    `id`              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    `create_time`     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `delete_time`     DATETIME                 DEFAULT NULL COMMENT '删除时间（逻辑删除标志）',
    /* 下方为业务字段 */
    `user_id`         BIGINT UNSIGNED NOT NULL COMMENT '对应的用户 ID',
    `login_counter`   BIGINT UNSIGNED NOT NULL COMMENT '登录次数',
    `last_login_time` DATETIME        NOT NULL COMMENT '最近一次登录时间',
    `last_login_ip`   CHAR(15)        NOT NULL DEFAULT '' COMMENT '最近一次登录的 IP 地址',
    `app_id`          VARCHAR(50)     NOT NULL DEFAULT '' COMMENT '小程序开发者 ID',
    `open_id`         VARCHAR(50)     NOT NULL DEFAULT '' COMMENT '小程序的用户唯一标识',
    `union_id`        VARCHAR(50)     NOT NULL DEFAULT '' COMMENT '用户在微信开放平台的唯一标识符',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT ='用户微信账户表';
