package com.weutil.reminder.event;

import com.weutil.reminder.entity.ReminderProject;
import lombok.Getter;

/**
 * 待办项目重命名事件
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2024/12/25
 * @since 3.0.0
 **/
@Getter
public class ReminderProjectRenamedEvent extends ReminderProjectEvent {
    /** 改名前的项目名称 */
    private final String originProjectName;

    /** 改名后的项目名称 */
    private final String targetProjectName;

    public ReminderProjectRenamedEvent(ReminderProject project, String originProjectName, String targetProjectName) {
        super(project);

        this.originProjectName = originProjectName;
        this.targetProjectName = targetProjectName;
    }
}
