-- --------------------------------------------------------
-- 待办任务表
-- 对应实体: [com.weutil.checklist.entity.ChecklistProject]
-- --------------------------------------------------------

CREATE TABLE `checklist_task`
(
    /* 下方是通用字段 */
    `id`            BIGINT UNSIGNED  NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    `create_time`   DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `delete_time`   DATETIME                  DEFAULT NULL COMMENT '删除时间（逻辑删除标志）',

    /* 下方为业务字段 */
    `user_id`       BIGINT UNSIGNED  NOT NULL COMMENT '所属用户 ID',
    `project_id`    BIGINT UNSIGNED  NOT NULL COMMENT '所属项目 ID',
    `name`          VARCHAR(100)     NOT NULL DEFAULT '' COMMENT '任务名称',
    `content_type`  TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '任务描述内容类型（枚举值）',
    `content`       VARCHAR(1000)    NOT NULL DEFAULT '' COMMENT '任务描述内容文本',
    `status`        TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '任务状态（枚举值）',
    `complete_time` DATETIME                  DEFAULT NULL COMMENT '任务完成时间',
    `priority`      TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '任务优先级（枚举值）',
    `due_date`      DATE                      DEFAULT NULL COMMENT '截止日期',
    `due_time`      TIME                      DEFAULT NULL COMMENT '截止时间',
    `prev_id`       BIGINT UNSIGNED  NOT NULL DEFAULT 0 COMMENT '（在排序中的）前一个 ID',

    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT ='待办任务表';
