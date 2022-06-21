package com.inlym.lifehelper.photoalbum.album;

import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.common.base.aliyun.tablestore.TableStoreUtils;
import com.inlym.lifehelper.photoalbum.album.entity.Album;
import com.inlym.lifehelper.photoalbum.album.pojo.CreateAlbumDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;

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
@Validated
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
        album.setHashedUserId(TableStoreUtils.getHashedId(userId));

        String albumId = albumService.create(album);
        album.setAlbumId(albumId);

        return album;
    }

    /**
     * 删除相册
     *
     * @since 1.3.0
     */
    @DeleteMapping("/album/{id}")
    @UserPermission
    public Object delete(@UserId int userId, @NotBlank @PathVariable String id) {
        Album album = new Album();
        album.setHashedUserId(TableStoreUtils.getHashedId(userId));
        album.setAlbumId(id);

        albumService.delete(album);
        return Map.of("id", id);
    }

    /**
     * 获取相册列表
     *
     * @since 1.3.0
     */
    @GetMapping("/albums")
    @UserPermission
    public Object list(@UserId int userId) {
        return Map.of("list", albumService.list(userId));
    }
}
