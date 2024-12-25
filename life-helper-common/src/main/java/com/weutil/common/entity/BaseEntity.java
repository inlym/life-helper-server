package com.weutil.common.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 实体基类
 *
 * <h2>说明
 * <p>所有的实体都需要继承当前实体。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/25
 * @since 3.0.0
 **/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity {
    /** 主键 ID */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 创建时间
     *
     * <h3>说明
     * <p>该字段由 MySQL 的触发器维护。
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     *
     * <h3>说明
     * <p>该字段由 MySQL 的触发器维护。
     */
    private LocalDateTime updateTime;

    /**
     * 删除时间
     *
     * <h3>说明
     * <p>该字段为逻辑删除标志。
     */
    @Column(isLogicDelete = true)
    private LocalDateTime deleteTime;

    /**
     * 创建时的客户端 IP 地址
     *
     * <h3>说明
     * <p>该字段由 MyBatis-Flex 框架监听器维护。
     */
    private String createClientIp;

    /**
     * 最后一次更新时的客户端 IP 地址
     *
     * <h3>说明
     * <p>该字段由 MyBatis-Flex 框架监听器维护。
     */
    private String updateClientIp;

    /**
     * 更新次数
     *
     * <h3>说明
     * <p>（1）默认值：0
     */
    @Column(onUpdateValue = "update_count + 1")
    private Long updateCount;
}
