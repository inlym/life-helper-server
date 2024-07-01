-- --------------------------------------------------------
-- 待办任务表
-- 对应实体: [com.inlym.lifehelper.checklist.entity.ChecklistProject]
-- --------------------------------------------------------

CREATE TABLE `user_account_wechat`
(
    /* 下方是通用字段 */
    `id`            bigint UNSIGNED  NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    `create_time`   datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `delete_time`   datetime                  DEFAULT NULL COMMENT '删除时间（逻辑删除标志）',
    /* 下方为业务字段 */
    `user_id`       bigint UNSIGNED  NOT NULL COMMENT '所属用户 ID',
    `project_id`    bigint UNSIGNED  NOT NULL COMMENT '所属项目 ID',
    `title`         varchar(100)     NOT NULL DEFAULT '' COMMENT '任务标题',
    `content_type`  tinyint UNSIGNED NOT NULL COMMENT '内容类型（枚举值，默认为0）',
    `content`       varchar(1000)    NOT NULL DEFAULT '' COMMENT '内容文本',
    `complete_time` datetime                  DEFAULT NULL COMMENT '完成时间',
    `priority`      tinyint UNSIGNED NOT NULL COMMENT '任务优先级（枚举值，默认为0）',
    `due_date`      date                      DEFAULT NULL COMMENT '截止日期',

    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT ='待办任务表';
