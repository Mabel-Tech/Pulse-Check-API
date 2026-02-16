package com.mabelowusu.pulse_check_api.dto;

import com.mabelowusu.pulse_check_api.model.Monitor;
import lombok.Data;

@Data
public class PauseMonitorResponse {
    
    private String message;
    private String deviceId;
    private String status;
    
    // Explicit getters as fallback for Lombok @Data
    public String getMessage() {
        return message;
    }
    
    public String getDeviceId() {
        return deviceId;
    }
    
    public String getStatus() {
        return status;
    }
    
    // Explicit setters as fallback for Lombok @Data
    public void setMessage(String message) {
        this.message = message;
    }
    
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public static PauseMonitorResponse from(Monitor monitor) {
        PauseMonitorResponse response = new PauseMonitorResponse();
        response.setMessage("Monitor paused successfully");
        response.setDeviceId(monitor.getId());
        response.setStatus(monitor.getStatus().toString());
        return response;
    }
}
