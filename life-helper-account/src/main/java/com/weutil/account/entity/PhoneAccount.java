package com.weutil.account.entity;

import com.mybatisflex.annotation.Table;
import com.weutil.common.entity.BaseUserRelatedEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 用户手机号账户关联表
 *
 * <h2>说明
 * <p>通过手机号关联到用户账户实体
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/22
 * @since 3.0.0
 **/

@Table("account_phone")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneAccount extends BaseUserRelatedEntity {
    
    // ---------- 账户关联表差异项 ----------

    /** 手机号（仅支持国内手机号） */
    private String phone;
}
