package com.weutil.todolist.model;

import com.weutil.common.validation.group.CreateGroup;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 保存待办项目操作的请求数据
 *
 * <h2>说明
 * <p>该对象同时用于“新增”和“编辑”情况。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/13
 * @since 3.0.0
 **/
@Data
public class TodolistProjectDTO {
    /** 项目名称 */
    @NotEmpty(message = "项目名称不能为空", groups = {CreateGroup.class})
    @Size(max = 20, message = "项目名称最多20个字")
    private String name;

    /**
     * emoji 图标
     *
     * <h3>说明
     * <p>单字符。
     */
    @Size(max = 1, message = "你最多只能选择1个emoji图标")
    private String emoji;

    /** 颜色名称 */
    private String color;

    /** 是否收藏 */
    private Boolean favorite;
}
