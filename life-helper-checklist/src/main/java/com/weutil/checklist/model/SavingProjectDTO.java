package com.weutil.checklist.model;

import com.weutil.common.validation.group.CreateGroup;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 保存（新增、修改）项目操作请求数据
 *
 * <h2>说明
 * <p>「新增」和「修改」共用当前数据模型。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/6/11
 * @since 2.3.0
 */
@Data
public class SavingProjectDTO {
    /** 项目名称 */
    @NotEmpty(message = "清单名称不能为空！", groups = {CreateGroup.class})
    private String name;

    /** 标记颜色的 code */
    @NotNull(message = "请选择颜色！", groups = {CreateGroup.class})
    private Integer colorCode;

    /** 是否收藏 */
    @NotNull(message = "请勾选是否收藏！", groups = {CreateGroup.class})
    private Boolean favorite;
}
