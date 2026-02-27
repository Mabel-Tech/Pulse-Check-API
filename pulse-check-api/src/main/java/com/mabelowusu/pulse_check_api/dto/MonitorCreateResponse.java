package com.mabelowusu.pulse_check_api.dto;

import com.mabelowusu.pulse_check_api.model.Monitor;
import lombok.Data;

@Data
public class MonitorCreateResponse {
    
    private String message;
    private String deviceId;
    private String timeout;
    private String status;
    
    public static MonitorCreateResponse from(Monitor monitor) {
        MonitorCreateResponse response = new MonitorCreateResponse();
        response.setMessage("Monitor created successfully");
        response.setDeviceId(monitor.getId());
        response.setTimeout(monitor.getTimeout().toString());
        response.setStatus(monitor.getStatus().toString());
        return response;
    }
}
