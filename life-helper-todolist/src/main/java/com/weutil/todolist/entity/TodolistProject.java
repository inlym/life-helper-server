package com.weutil.todolist.entity;

import com.mybatisflex.annotation.Table;
import com.weutil.common.entity.BaseUserRelatedEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 待办项目实体
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/12
 * @since 3.0.0
 **/
@Table("reminder_project")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TodolistProject extends BaseUserRelatedEntity {
    /** 项目名称 */
    private String name;
}
