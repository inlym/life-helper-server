package com.inlym.lifehelper.photoalbum.album.exception;

import com.inlym.lifehelper.common.model.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 相册模块异常处理器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/9/4
 * @since 1.4.0
 **/
@RestControllerAdvice
@Slf4j
@Order(10)
@RequiredArgsConstructor
public class AlbumExceptionHandler {
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AlbumNotExistException.class)
    public ExceptionResponse handleAlbumNotExistException(AlbumNotExistException e) {
        return new ExceptionResponse(1, "你操作的相册不存在，刷新后再试！");
    }
}
