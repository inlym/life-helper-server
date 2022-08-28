package com.inlym.lifehelper.photoalbum.album;

import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.photoalbum.album.entity.Album;
import com.inlym.lifehelper.photoalbum.album.pojo.AlbumListVO;
import com.inlym.lifehelper.photoalbum.album.pojo.AlbumVO;
import com.inlym.lifehelper.photoalbum.album.pojo.CreateAlbumDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * 相册模块控制器
 *
 * <h2>主要用途
 * <p>管理相册实体相关的 API 接口
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/8/12
 * @since 1.4.0
 **/
@RestController
@RequiredArgsConstructor
public class AlbumController {
    private final AlbumService albumService;

    /**
     * 创建相册
     *
     * @param userId 用户 ID
     * @param dto    请求数据
     *
     * @since 1.4.0
     */
    @PostMapping("/albums")
    @UserPermission
    public AlbumVO createAlbum(@UserId int userId, @Valid @RequestBody CreateAlbumDTO dto) {
        Album album = Album
            .builder()
            .userId(userId)
            .name(dto.getName())
            .description(dto.getDescription())
            .build();

        return albumService.convert(albumService.create(album));
    }

    /**
     * 获取用户的所有相册
     *
     * @param userId 用户 ID
     *
     * @since 1.4.0
     */
    @GetMapping("/albums")
    @UserPermission
    public AlbumListVO listAlbums(@UserId int userId) {
        List<AlbumVO> list = new ArrayList<>(256);
        for (Album album : albumService.list(userId)) {
            list.add(albumService.convert(album));
        }

        return AlbumListVO
            .builder()
            .list(list)
            .build();
    }

    /**
     * 删除相册
     *
     * @param userId 用户 ID
     * @param id     相册 ID
     *
     * @since 1.4.0
     */
    @DeleteMapping("/albums/{id}")
    @UserPermission
    public AlbumVO deleteAlbum(@UserId int userId, @NotBlank @PathVariable String id) {
        albumService.delete(userId, id);

        return AlbumVO
            .builder()
            .id(id)
            .build();
    }

    /**
     * 更新相册信息
     *
     * @param userId 用户 ID
     * @param id     相册 ID
     * @param dto    请求数据
     *
     * @since 1.4.0
     */
    @PutMapping("/albums/{id}")
    @UserPermission
    public AlbumVO updateAlbum(@UserId int userId, @NotBlank @PathVariable String id, @Valid @RequestBody CreateAlbumDTO dto) {
        Album album = Album
            .builder()
            .userId(userId)
            .albumId(id)
            .name(dto.getName())
            .description(dto.getDescription())
            .build();

        return albumService.convert(albumService.update(album));
    }
}
