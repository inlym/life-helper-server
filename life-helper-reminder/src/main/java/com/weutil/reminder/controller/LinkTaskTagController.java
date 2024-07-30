package com.weutil.reminder.controller;

import com.weutil.common.annotation.UserId;
import com.weutil.common.annotation.UserPermission;
import com.weutil.reminder.entity.LinkTaskTag;
import com.weutil.reminder.model.LinkTaskTagVO;
import com.weutil.reminder.service.LinkTaskTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 待办任务标签管理控制器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/30
 * @since 3.0.0
 **/
@RestController
@RequiredArgsConstructor
public class LinkTaskTagController {
    private final LinkTaskTagService linkTaskTagService;

    /**
     * 将实体对象转化为视图对象
     *
     * @param entity 实体对象
     *
     * @date 2024/7/30
     * @since 3.0.0
     */
    private LinkTaskTagVO convert(LinkTaskTag entity) {
        return LinkTaskTagVO.builder().id(entity.getId()).createTime(entity.getCreateTime()).tagId(entity.getTagId()).build();
    }

    /**
     * 给任务添加一个标签
     *
     * <h3>说明
     * <p>虽然属于“新增”操作，但是当前方法比较特殊，可以做到幂等，因此请求方法使用 {@code PUT}。
     *
     * @param userId 用户 ID
     * @param taskId 任务 ID
     * @param tagId  标签 ID
     *
     * @date 2024/7/30
     * @since 3.0.0
     */
    @PutMapping("/reminder/tasks/{task_id}/tags/{tag_id}")
    @UserPermission
    public LinkTaskTag append(@UserId long userId, @PathVariable("task_id") long taskId, @PathVariable("tag_id") long tagId) {
        return linkTaskTagService.append(userId, taskId, tagId);
    }

//    @DeleteMapping("/reminder/link-task-tag/{id}")
}
