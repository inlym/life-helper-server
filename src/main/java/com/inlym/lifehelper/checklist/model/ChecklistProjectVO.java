package com.inlym.lifehelper.checklist.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 待办清单项目视图对象
 *
 * <h2>说明
 *
 * <p>用于输出给客户端展示使用。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/11
 * @since 2.3.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChecklistProjectVO {
  /** 项目 ID */
  private Long id;

  /** 创建时间 */
  private LocalDateTime createTime;

  /** 项目名称 */
  private String name;

  /**
   * 标记颜色
   *
   * <h3>说明
   *
   * <p>用于标记项目名称旁的圆点颜色。
   *
   * <h3>示例值
   *
   * <p>{@code #2ba245}, {@code #282a36}, ...
   */
  private String color;

  /** 置顶时间 */
  private LocalDateTime pinTime;

  /** 任务数 */
  private Long taskCount;
}
