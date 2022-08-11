package com.inlym.lifehelper.photoalbum.album;

import com.inlym.lifehelper.common.annotation.UserId;
import com.inlym.lifehelper.common.annotation.UserPermission;
import com.inlym.lifehelper.photoalbum.album.pojo.AlbumListVO;
import com.inlym.lifehelper.photoalbum.album.pojo.AlbumVO;
import com.inlym.lifehelper.photoalbum.album.pojo.CreateAlbumDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

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

    @PostMapping("/album")
    @UserPermission
    public AlbumVO createAlbum(@UserId int userId, @Valid @RequestBody CreateAlbumDTO dto) {
        return null;
    }

    @GetMapping("/albums")
    @UserPermission
    public AlbumListVO listAlbums(@UserId int userId) {
        return null;
    }

    @DeleteMapping("/album/{id}")
    @UserPermission
    public AlbumVO deleteAlbum(@UserId int userId, @NotBlank @PathVariable String id) {
        return null;
    }

    @PutMapping("/album/{id}")
    @UserPermission
    public AlbumVO updateAlbum(@UserId int userId, @NotBlank @PathVariable String id, @Valid @RequestBody CreateAlbumDTO dto) {
        return null;
    }
}
