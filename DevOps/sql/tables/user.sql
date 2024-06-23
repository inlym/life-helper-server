-- --------------------------------------------------------
-- 用户账户表
-- 对应实体: [com.inlym.lifehelper.account.user.entity.User]
-- --------------------------------------------------------

CREATE TABLE `user`
(
    /* 下方是通用字段 */
    `id`               bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    `create_time`      datetime        NOT NULL COMMENT '创建时间',
    `update_time`      datetime        NOT NULL COMMENT '更新时间',
    `auto_create_time` datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '由数据库维护的创建时间',
    `auto_update_time` datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '由数据库维护的更新时间',
    `version`          int             NOT NULL COMMENT '乐观锁（修改次数）',
    /* 下方为业务字段 */
    `nick_name`        varchar(20)     NOT NULL DEFAULT '' COMMENT '昵称',
    `avatar_path`      varchar(100)    NOT NULL DEFAULT '' COMMENT '头像路径',
    `register_time`    datetime        NOT NULL COMMENT '注册时间',
    `login_counter`    bigint UNSIGNED NOT NULL COMMENT '登录次数',
    `last_login_time`  datetime        NOT NULL COMMENT '最近一次登录时间',
    `last_login_ip`    char(15)        NOT NULL DEFAULT '' COMMENT '最近一次登录的 IP 地址',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT ='用户账户表';
