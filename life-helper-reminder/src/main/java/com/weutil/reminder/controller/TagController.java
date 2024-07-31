package com.weutil.reminder.controller;

import com.weutil.reminder.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * 待办标签控制器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/31
 * @since 3.0.0
 **/
@RestController
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
}
