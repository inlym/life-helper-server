package com.inlym.lifehelper.photoalbum.media.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 获取相册的媒体列表请求的响应数据
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/9/4
 * @since 1.4.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaListVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 6526163732254748661L;

    private List<MediaVO> list;
}
