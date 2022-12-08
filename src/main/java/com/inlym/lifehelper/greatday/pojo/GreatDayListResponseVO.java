package com.inlym.lifehelper.greatday.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 获取纪念日列表请求的专用相应数据格式
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/7
 * @since 1.8.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GreatDayListResponseVO {
    /** 数据列表 */
    private List<GreatDayVO> list;
}
