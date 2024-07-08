package com.weutil.weather.place.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 新增天气地点请求数据
 *
 * <h2>说明
 * <p>数据来源于调用微信小程序 API，未对数据做任何改动。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/10/21
 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api/location/wx.chooseLocation.html">wx.chooseLocation</a>
 * @since 1.5.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeChatChooseLocationDTO {
    /** 位置名称 */
    @NotEmpty
    private String name;

    /** 详细地址 */
    @NotEmpty
    private String address;

    /** 经度 */
    @NotNull
    private Double longitude;

    /** 纬度 */
    @NotNull
    private Double latitude;
}
