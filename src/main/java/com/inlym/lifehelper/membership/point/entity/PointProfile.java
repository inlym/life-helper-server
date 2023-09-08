package com.inlym.lifehelper.membership.point.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 积分信息表
 * <p>
 * <h2>实体含义
 * <p>1. 每个用户仅1条记录，在该记录上改动。
 * <p>2. 表中所有字段数据均可以通过对“积分交易记录表”统计重新计算。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/8/14
 * @since 2.0.2
 **/
@Table("point_profile")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointProfile {
    /**
     * 主键 ID
     * <p>
     * <h2>字段说明
     * <p>目前无任何用途，仅用于自增，不直接使用用户 ID 的原因是，防止数据库页分裂。
     */
    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 用户 ID
     * <p>
     * <h2>字段说明
     * <p>1. 建立唯一索引。
     * <p>2. 仅使用当前字段查找行。
     */
    private Integer userId;

    /**
     * 当前积分余额
     * <p>
     * <h2>字段说明
     * <p>1. 余额 = 收入 - 支出，该条逻辑可用于校验。
     */
    private Long balance;

    /**
     * 积分支出总额
     */
    private Long expense;

    /**
     * 积分收入总额
     */
    private Long income;

    /**
     * 支出次数
     */
    private Long expenseCounter;

    /**
     * 收入次数
     */
    private Long incomeCounter;

    /**
     * 创建时间
     * <p>
     * <h2>字段说明
     * <p>1. 当前行记录的创建时间。
     */
    private LocalDateTime createTime;

    /**
     * 最近更新时间
     * <p>
     * <h2>字段说明
     * <p>1. 当前行记录的最后一次修改时间。
     */
    private LocalDateTime updateTime;
}
