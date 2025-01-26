package com.weutil.todolist.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 创建待办任务操作的请求数据
 *
 * <h2>说明
 * <p>该对象仅用于“新增”和情况。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/13
 * @since 3.0.0
 **/
@Data
public class CreateTodolistTaskDTO {
    /** 任务名称 */
    @NotEmpty(message = "项目名称不能为空！")
    private String name;

    /**
     * 所属项目 ID
     *
     * <h3>说明
     * <p>该值为 {@code 0} 则表示不从属于任何项目。
     */
    @Min(value = 0, message = "你选择的项目不存在，请刷新后重试！")
    @NotNull
    private Long projectId;
}
