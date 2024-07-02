-- --------------------------------------------------------
-- 待办任务和标签关联表
-- 对应实体: [com.inlym.lifehelper.checklist.entity.ChecklistTaskTagLink]
-- --------------------------------------------------------

CREATE TABLE `checklist_link_task_tag`
(
    /* 下方是通用字段 */
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    `create_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `delete_time` DATETIME                 DEFAULT NULL COMMENT '删除时间（逻辑删除标志）',

    /* 下方为业务字段 */
    `user_id`     BIGINT UNSIGNED NOT NULL COMMENT '所属用户 ID',
    `task_id`     BIGINT UNSIGNED NOT NULL COMMENT '所属任务 ID',
    `tag_id`      BIGINT UNSIGNED NOT NULL COMMENT '所属标签 ID',

    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4 COMMENT ='待办任务和标签关联表';
