-- --------------------------------------------------------
-- 用户手机号账户表
-- 对应实体: [com.inlym.lifehelper.account.user.entity.UserAccountPhone]
-- --------------------------------------------------------

CREATE TABLE `user_account_phone`
(
    /* 下方是通用字段 */
    `id`              bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    `create_time`     datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `delete_time`     datetime                 DEFAULT NULL COMMENT '删除时间（逻辑删除标志）',
    /* 下方为业务字段 */
    `user_id`         bigint UNSIGNED NOT NULL COMMENT '对应的用户 ID',
    `login_counter`   bigint UNSIGNED NOT NULL COMMENT '登录次数',
    `last_login_time` datetime        NOT NULL COMMENT '最近一次登录时间',
    `last_login_ip`   char(15)        NOT NULL DEFAULT '' COMMENT '最近一次登录的 IP 地址',
    `phone`           char(11)        NOT NULL DEFAULT '' COMMENT '手机号（仅支持国内手机号）',
    `hashed_password` char(64)        NOT NULL DEFAULT '' COMMENT '使用 HmacSHA256 算法哈希化后的密码',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT ='用户手机号账户表';
