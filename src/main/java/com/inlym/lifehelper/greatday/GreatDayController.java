package com.inlym.lifehelper.greatday;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * 纪念日模块控制器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/12/7
 * @since 1.8.0
 **/
@RestController
@Slf4j
@RequiredArgsConstructor
public class GreatDayController {
    private final GreatDayService greatDayService;
}
