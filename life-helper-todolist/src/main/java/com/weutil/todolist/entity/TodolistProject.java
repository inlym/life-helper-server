package com.weutil.todolist.entity;

import com.mybatisflex.annotation.Table;
import com.weutil.common.entity.BaseUserRelatedEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

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

    /**
     * emoji 图标
     *
     * <h3>说明
     * <p>单字符。
     */
    private String emoji;

    /**
     * 颜色名称
     *
     * <h3>说明
     * <p>服务端只存储颜色名称，颜色的呈现均由前端处理。
     */
    private String color;

    /**
     * 收藏时间
     *
     * <h3>说明
     * <p>前端只处理“是否收藏”操作。
     */
    private LocalDateTime favoriteTime;
}
