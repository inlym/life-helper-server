package com.inlym.lifehelper.todo.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * “新增或编辑待办事项清单”接口请求数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/10/10
 * @since 2.0.3
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveTodoProjectDTO {
    /** 项目名称 */
    @NotBlank
    private String name;
}
