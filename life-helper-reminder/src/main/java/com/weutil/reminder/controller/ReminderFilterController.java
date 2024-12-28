package com.weutil.reminder.controller;

import com.weutil.common.annotation.UserId;
import com.weutil.common.annotation.UserPermission;
import com.weutil.common.model.SingleListResponse;
import com.weutil.reminder.model.ReminderFilterVO;
import com.weutil.reminder.service.ReminderFilterService;
import com.weutil.reminder.service.ReminderTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 待办任务过滤器控制器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/27
 * @since 3.0.0
 **/
@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
public class ReminderFilterController {
    private final ReminderTaskService reminderTaskService;
    private final ReminderFilterService reminderFilterService;

    /**
     * 获取过滤器列表
     *
     * @param userId 用户 ID
     *
     * @date 2024/12/25
     * @since 3.0.0
     */
    @GetMapping("/reminder/filters")
    @UserPermission
    public SingleListResponse<ReminderFilterVO> listFilters(@UserId long userId) {
        return new SingleListResponse<>(reminderFilterService.listFilters(userId));
    }
}
