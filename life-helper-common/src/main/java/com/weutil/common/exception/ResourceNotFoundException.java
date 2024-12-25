package com.weutil.common.exception;

import lombok.Getter;

/**
 * 资源未找到异常
 *
 * <h2>触发条件
 * <p>（1）使用主键 ID 查找资源时，未找到资源（可能是不存在或者已被删除）。
 * <p>（2）能够找到对应 ID 的资源，但并不归属于当前用户。
 *
 * <h2>使用说明
 * <p>不要直接使用这个类，而是在各个模块内继承这个类，抛出子类。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/14
 * @since 3.0.0
 **/
@Getter
public class ResourceNotFoundException extends RuntimeException {
    /** 主键 ID */
    private final Long pkId;

    /** 用户 ID */
    private final Long userId;

    public ResourceNotFoundException(long pkId, long userId) {
        this.pkId = pkId;
        this.userId = userId;
    }
}
