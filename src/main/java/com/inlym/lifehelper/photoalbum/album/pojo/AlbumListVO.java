package com.inlym.lifehelper.photoalbum.album.pojo;

import com.inlym.lifehelper.photoalbum.album.entity.Album;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class AlbumListVO {
    private List<Album> list;
}
