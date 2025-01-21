package com.weutil.reminder.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 更新待办任务的请求数据
 *
 * <h2>说明
 * <p>仅处理一些基础资料类，特殊操作放在 {@link OperateReminderTaskDTO} 中。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/25
 * @since 3.0.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateReminderTaskDTO {
    /** 任务名称 */
    @Size(max = 50, message = "任务名称最长为50个字")
    private String name;

    /**
     * 所属项目 ID
     *
     * <h3>说明
     * <p>该值为 {@code 0} 则表示不从属于任何项目。
     */
    @Min(value = 0, message = "你选择的项目不存在，请刷新后重试")
    private Long projectId;

    /** 任务描述内容文本 */
    private String content;

    /** 截止期限的日期部分（年月日） */
    private LocalDate dueDate;

    /** 截止期限的时间部分（时分秒） */
    private LocalTime dueTime;

    /** 优先级 */
    private Priority priority;

    /** 特定操作 */
    private ReminderTaskOperation operation;
}
