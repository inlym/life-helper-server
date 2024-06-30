package com.inlym.lifehelper.checklist.model;

import com.inlym.lifehelper.common.validation.group.CreateGroup;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 保存（新增、修改）项目操作请求数据
 *
 * <h2>说明
 *
 * <p>「新增」和「修改」共用当前数据模型。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/11
 * @since 2.3.0
 */
@Data
public class SavingProjectDTO {
  /** 项目名称 */
  @NotEmpty(
      message = "清单名称不能为空！",
      groups = {CreateGroup.class})
  private String name;

  /** 标记颜色（不带 `#`，示例值：`efefef`） */
  private String color;
}
