package com.inlym.lifehelper.photoalbum.album.exception;

/**
 * 相册不存在异常
 *
 * <h2>主要用途
 * <p>操作一个不存在的相册时，抛出这个异常。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/9/4
 * @since 1.4.0
 **/
public class AlbumNotExistException extends RuntimeException {
    public AlbumNotExistException(String message) {
        super(message);
    }

    public static AlbumNotExistException create(String albumId) {
        String message = "Invalid albumId：" + albumId;
        return new AlbumNotExistException(message);
    }
}
