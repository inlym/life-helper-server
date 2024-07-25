-- --------------------------------------------------------
-- 用户账户表
-- 对应实体: [com.weutil.account.entity.User]
-- --------------------------------------------------------

CREATE TABLE `user`
(
    /* 下方是通用字段 */
    `id`              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    `create_time`     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    /* 下方为业务字段 */
    `nick_name`       VARCHAR(20)     NOT NULL DEFAULT '' COMMENT '昵称',
    `avatar_path`     VARCHAR(100)    NOT NULL DEFAULT '' COMMENT '头像路径',
    `register_time`   DATETIME        NOT NULL COMMENT '注册时间',
    `login_counter`   BIGINT UNSIGNED NOT NULL COMMENT '登录次数',
    `last_login_time` DATETIME        NOT NULL COMMENT '最近一次登录时间',
    `last_login_ip`   CHAR(15)        NOT NULL DEFAULT '' COMMENT '最近一次登录的 IP 地址',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT ='用户账户表';
