package com.weutil.reminder.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 待办清单项目视图对象
 *
 * <h2>说明
 * <p>用于输出给客户端展示使用。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/29
 * @since 3.0.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectVO {
    /** 项目 ID */
    private Long id;

    /** 项目名称 */
    private String name;

    /**
     * 标记颜色
     *
     * <h3>说明
     * <p>将会转化为 0~10 的数字，前端自行映射对应值。
     */
    private Color color;

    /** 未完成的任务数 */
    private Long uncompletedTaskCount;
}
