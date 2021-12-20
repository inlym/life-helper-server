package com.inlym.lifehelper.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DebugController {
    @GetMapping("/debug/session")
    public Object getSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, Object> map = new HashMap<>();

        map.put("Id", session.getId());
        map.put("CreationTime", session.getCreationTime());
        map.put("LastAccessedTime", session.getLastAccessedTime());

        return map;
    }
}
