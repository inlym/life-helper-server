package com.weutil.reminder.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 待办项目视图对象
 *
 * <h2>说明
 * <p>用于客户端展示使用。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/13
 * @since 3.0.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReminderProjectVO {
    /** 主键 ID */
    private Long id;

    /** 项目名称 */
    private String name;

    /** 未完成的任务数 */
    private Long uncompletedTaskCount;
}
