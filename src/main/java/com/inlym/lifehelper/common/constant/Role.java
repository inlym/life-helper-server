package com.inlym.lifehelper.common.constant;

/**
 * 自定义角色
 *
 * @author inlym
 * @since 2022-01-21 00:43
 **/
public final class Role {
    /**
     * 普通用户
     * <p>
     * [获取方式]
     * 通过正常方式登录系统。
     */
    public static final String USER = "ROLE_USER";

    /**
     * 开发者
     * <p>
     * [获取方式]
     * 获取普通用户身份后再二次鉴权。
     * <p>
     * [主要用途]
     * 少量接口可获取系统非公开信息，方便开源学习者进行调试。
     */
    public static final String DEVELOPER = "ROLE_DEVELOPER";

    /**
     * 管理员
     * <p>
     * [获取方式]
     * 特殊方式或手动生成鉴权信息
     * <p>
     * [主要用途]
     * 用于调试部分对系统可能有一定破坏性的接口。
     */
    public static final String ADMIN = "ROLE_ADMIN";
}
