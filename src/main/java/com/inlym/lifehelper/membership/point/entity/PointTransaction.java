package com.inlym.lifehelper.membership.point.entity;

import com.inlym.lifehelper.membership.point.constant.PointTransactionType;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 积分交易记录
 * <p>
 * <h2>实体含义
 * <p>任何积分变动将生成1条记录。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2023/8/14
 * @since 2.0.2
 **/
@Table("point_transaction")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointTransaction {
    /**
     * 主键 ID
     * <p>
     * <h2>字段说明
     * <p>目前无任何用途，仅用于自增。
     */
    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 交易单号
     * <p>
     * <h2>字段说明
     * <p>1. 建立唯一索引。
     * <p>2. 仅使用当前字段查找行。
     */
    private Long number;

    /**
     * 归属的用户 ID
     * <p>
     * <h2>字段说明
     * <p>1. 建立索引。
     */
    private Integer userId;

    /**
     * 交易标题
     */
    private String title;

    /**
     * 交易类型
     * <p>
     * <h2>字段说明
     * <p>注意存入数据库的是类型的数字编码，不是字符串。
     */
    private PointTransactionType type;

    /**
     * 变动的积分数值
     * <p>
     * <h2>字段说明
     * <p>1. 该值可能为负数、0、正数。
     */
    private Long amount;
}