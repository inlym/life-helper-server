package com.inlym.lifehelper.common.constant;

/**
 * 自定义角色
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022-01-21
 * @since 1.0.0
 **/
public abstract class Role {
    /**
     * 普通用户
     *
     * <h2>获取方式
     * <p>通过正常方式登录系统
     */
    public static final String USER = "ROLE_USER";

    /**
     * 开发者
     *
     * <h2>获取方式
     * <p>获取普通用户身份后再二次鉴权
     *
     * <h2>主要用途
     * <p>少量接口可获取系统非公开信息，方便开源学习者进行调试。
     */
    public static final String DEVELOPER = "ROLE_DEVELOPER";

    /**
     * 管理员
     *
     * <h2>获取方式
     * <p>特殊方式或手动生成鉴权信息
     *
     * <h2>主要用途
     * <p>用于调试部分对系统可能有一定破坏性的接口
     */
    public static final String ADMIN = "ROLE_ADMIN";
}
