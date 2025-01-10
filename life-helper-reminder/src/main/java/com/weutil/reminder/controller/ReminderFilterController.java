package com.weutil.reminder.controller;

import com.weutil.common.annotation.UserId;
import com.weutil.common.annotation.UserPermission;
import com.weutil.common.model.SingleNumberResponse;
import com.weutil.reminder.model.ReminderFilter;
import com.weutil.reminder.service.ReminderFilterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final ReminderFilterService reminderFilterService;

    /**
     * 查看指定过滤器的未完成任务数
     *
     * @param userId     用户 ID
     * @param filterName 过滤器名称（前端可直接传小写字母）
     *
     * @date 2025/01/08
     * @since 3.0.0
     */
    @GetMapping("/reminder/filters/{filter_name}/count-uncompleted")
    @UserPermission
    public SingleNumberResponse countUncompletedTasks(@UserId long userId, @PathVariable("filter_name") String filterName) {
        ReminderFilter filter = ReminderFilter.valueOf(filterName.toUpperCase());
        long num = reminderFilterService.countUncompletedTasks(userId, filter);
        return new SingleNumberResponse(num);
    }
}
