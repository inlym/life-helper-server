package com.weutil.todo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 过滤器视图对象
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/27
 * @since 3.0.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoFilterVO {
    /** 名称 */
    private String name;

    /** 类型 */
    private TodoTaskFilter type;

    /** 计数（除“已完成”过滤器外，其余均为未完成任务数） */
    private Long num;
}
