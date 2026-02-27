package com.mabelowusu.pulse_check_api.dto;

import com.mabelowusu.pulse_check_api.model.Monitor;
import lombok.Data;

@Data
public class PauseMonitorResponse {
    
    private String message;
    private String deviceId;
    private String status;
    
    public static PauseMonitorResponse from(Monitor monitor) {
        PauseMonitorResponse response = new PauseMonitorResponse();
        response.setMessage("Monitor paused successfully");
        response.setDeviceId(monitor.getId());
        response.setStatus(monitor.getStatus().toString());
        return response;
    }
}
