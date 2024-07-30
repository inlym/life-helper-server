package com.weutil.reminder.model;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 任务标签关联关系视图对象
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
public class LinkTaskTagVO {
    /** 主键 ID */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 所属标签 ID */
    private Long tagId;

    /** 所属标签名称 */
    private String tagName;
}
