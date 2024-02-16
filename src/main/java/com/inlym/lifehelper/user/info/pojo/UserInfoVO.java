package com.inlym.lifehelper.user.info.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户信息视图对象
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/11/26
 * @since 1.7.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO {
    // ============================== 数据源来自用户账户实体 =============================

    /** 注册时间 */
    private LocalDateTime registerTime;

    /** 已注册天数 */
    private Integer registeredDays;

    // ============================== 数据源来自用户信息实体 =============================

    /** 用户昵称 */
    private String nickName;

    /** 头像图片的 URL 地址 */
    private String avatarUrl;

    /** 性别：男、女、未知 */
    private String gender;

    /** 用户所在地区 */
    private List<String> region;

    /** 用户所在地区名称 */
    private String regionDisplayName;
}
