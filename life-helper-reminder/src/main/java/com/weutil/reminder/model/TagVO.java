package com.weutil.reminder.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 待办任务标签视图对象
 *
 * <h2>说明
 * <p>用于输出给客户端展示使用。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/30
 * @since 3.0.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagVO {
    /** 标签 ID */
    private Long id;

    /** 标签名称 */
    private String name;

    /** 标记颜色（枚举值） */
    private Color color;

    /** 未完成的任务数 */
    private Long uncompletedTaskCount;
}
