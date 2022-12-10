package com.inlym.lifehelper.greatday.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 获取 emoji 列表请求返回的响应数据格式
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/10
 * @since 1.8.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmojiListResponseVO {
    /** 数据列表 */
    List<String> list;
}
