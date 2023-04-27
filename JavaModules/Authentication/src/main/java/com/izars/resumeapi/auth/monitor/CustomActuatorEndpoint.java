package com.izars.resumeapi.auth.monitor;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Endpoint(id = "customEndpoint")
@Component
public class CustomActuatorEndpoint {
    @ReadOperation
    public Object customEndpoint() {
        Map<String, String> map = new HashMap<>();
        map.put("Status", "Up");
        return map;
    }
}
