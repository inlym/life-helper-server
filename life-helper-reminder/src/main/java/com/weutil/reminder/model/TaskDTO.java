package com.weutil.reminder.model;

import com.weutil.common.validation.group.CreateGroup;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * 保存任务操作请求数据
 *
 * <h2>说明
 * <p>「新增」和「修改」共用当前数据模型。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/30
 * @since 3.0.0
 **/
@Data
public class TaskDTO {
    /** 任务名称 */
    @NotEmpty(groups = CreateGroup.class, message = "任务名称不能为空！")
    private String name;

    /** 任务描述内容文本 */
    private String content;

    /** 所属项目 ID */
    @PositiveOrZero(groups = CreateGroup.class, message = "请选择正确的项目！")
    private Long projectId;

    /** 任务优先级 */
    private Integer priority;

    /** 截止日期 */
    private LocalDate dueDate;

    /** 截止时间 */
    private LocalTime dueTime;

    /** 标签列表 */
    @Positive
    private List<Long> tags;
}
