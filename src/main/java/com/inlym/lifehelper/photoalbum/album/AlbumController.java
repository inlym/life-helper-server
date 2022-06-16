package com.inlym.lifehelper.photoalbum.album;

import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.photoalbum.album.entity.Album;
import com.inlym.lifehelper.photoalbum.album.pojo.CreateAlbumDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 相册模块控制器
 *
 * <h2>说明
 * <p>当前控制器仅负责处理项目实体的增删改查逻辑
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/6/16
 * @since 1.3.0
 **/
@RestController
@RequiredArgsConstructor
public class AlbumController {
    private final AlbumService albumService;

    /**
     * 创建相册
     *
     * @since 1.3.0
     */
    @PostMapping("/album")
    @UserPermission
    public Album create(@UserId int userId, @Valid @RequestBody CreateAlbumDTO dto) {
        Album album = new Album();
        album.setName(dto.getName());
        album.setDescription(dto.getDescription());
        album.setUserId(userId);

        String albumId = albumService.create(album);
        album.setAlbumId(albumId);

        return album;
    }
}
