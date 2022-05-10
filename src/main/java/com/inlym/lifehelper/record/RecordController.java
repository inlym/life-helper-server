package com.inlym.lifehelper.record;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.Map;

/**
 * 数据上报模块控制器
 *
 * @author <a href="https://www.inlym.com">inlym</a>
 * @date 2022/5/10
 * @since 1.2.3
 **/
@RestController
public class RecordController {
    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping("/report/miniprogram/launch")
    public Map<String, Integer> recordLaunchInfo(@RequestBody String data) {
        recordService.saveMiniProgramLaunchInfo(Base64
            .getEncoder()
            .encodeToString(data.getBytes()));
        return Map.of("code", 0);
    }
}
