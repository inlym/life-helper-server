package com.inlym.lifehelper.photoalbum.album.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 相册列表对象
 *
 * <h2>主要用途
 * <p>封装相册列表为一个对象
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/12
 * @since 1.4.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlbumListVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1885451840940649906L;

    /** 相册数据列表 */
    private List<AlbumVO> list;
}
