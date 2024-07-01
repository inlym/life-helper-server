-- --------------------------------------------------------
-- 待办任务标签表
-- 对应实体: [com.inlym.lifehelper.checklist.entity.ChecklistTag]
-- --------------------------------------------------------

CREATE TABLE `checklist_tag`
(
    /* 下方是通用字段 */
    `id`          bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    `create_time` datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `delete_time` datetime                 DEFAULT NULL COMMENT '删除时间（逻辑删除标志）',
    /* 下方为业务字段 */
    `user_id`     bigint UNSIGNED NOT NULL COMMENT '所属用户 ID',
    `name`        varchar(30)     NOT NULL DEFAULT '' COMMENT '标签名称',
    `color`       char(6)         NOT NULL DEFAULT '' COMMENT '标记颜色',
    `pin_time`    datetime                 DEFAULT NULL COMMENT '置顶时间',

    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
    COMMENT ='待办任务标签表';
