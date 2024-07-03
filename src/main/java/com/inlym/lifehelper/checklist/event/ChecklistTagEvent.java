package com.inlym.lifehelper.checklist.event;

import com.inlym.lifehelper.checklist.entity.ChecklistTag;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 待办任务标签事件
 *
 * <h2>说明
 * <p>其他的待办任务标签事件均继承自当前事件。
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/4
 * @since 2.3.0
 **/
@Getter
public class ChecklistTagEvent extends ApplicationEvent {
    private final ChecklistTag checklistTag;
    private final Long userId;
    private final Long tagId;

    public ChecklistTagEvent(ChecklistTag entity) {
        super(entity);

        this.checklistTag = entity;
        this.userId = entity.getUserId();
        this.tagId = entity.getId();
    }
}
