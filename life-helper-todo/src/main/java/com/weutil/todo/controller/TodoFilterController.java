package com.weutil.todo.controller;

import com.weutil.common.annotation.UserId;
import com.weutil.common.annotation.UserPermission;
import com.weutil.common.model.SingleNumberResponse;
import com.weutil.todo.model.TodoFilter;
import com.weutil.todo.service.TodoFilterService;
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
public class TodoFilterController {
    private final TodoFilterService todoFilterService;

    /**
     * 查看指定过滤器的未完成任务数
     *
     * @param userId     用户 ID
     * @param filterName 过滤器名称（前端可直接传小写字母）
     *
     * @date 2025/01/08
     * @since 3.0.0
     */
    @GetMapping("/todo/filters/{filter_name}/count-uncompleted")
    @UserPermission
    public SingleNumberResponse countUncompletedTasks(@UserId long userId, @PathVariable("filter_name") String filterName) {
        TodoFilter filter = TodoFilter.valueOf(filterName.toUpperCase());
        long num = todoFilterService.countUncompletedTasks(userId, filter);
        return new SingleNumberResponse(num);
    }
}
