package com.inlym.lifehelper.checklist.event;

import com.inlym.lifehelper.checklist.entity.ChecklistProject;
import org.springframework.context.ApplicationEvent;

/**
 * 待办清单创建事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/7/1
 * @since 2.3.0
 */
public class ChecklistProjectCreatedEvent extends ApplicationEvent {
  public ChecklistProjectCreatedEvent(ChecklistProject source) {
    super(source);
  }
}
