package com.weutil.reminder.controller;

import com.weutil.reminder.service.ReminderProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * 待办项目控制器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/13
 * @since 3.0.0
 **/
@RestController
@Slf4j
@RequiredArgsConstructor
public class ReminderProjectController {
    private final ReminderProjectService reminderProjectService;
}
