package com.inlym.lifehelper.example.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 仓鼠
 *
 * <h2>说明
 * <p>这个实体类只是用来作为演示功能的，不是实际的业务实体类。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/4/19
 * @since 2.3.0
 **/
@Table("example_hamster")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hamster {
    // ============================ 通用字段 ============================

    /** 主键 ID */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /** 创建时间 */
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    /** 更新时间 */
    @Column(onInsertValue = "now()", onUpdateValue = "now()")
    private LocalDateTime updateTime;

    /** 乐观锁（修改次数） */
    @Column(version = true)
    private Long version;

    /** 删除时间（逻辑删除标志） */
    @Column(isLogicDelete = true)
    private LocalDateTime deleteTime;

    // ============================ 业务字段 ============================

    /** 名字 */
    private String name;

    /** 年龄 */
    private Integer age;

    /** 是否可爱 */
    private Boolean cute;

    /** 生日日期 */
    private LocalDate birthday;

    /** 计数器 */
    private Long counter;
}
