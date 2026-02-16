package com.mabelowusu.pulse_check_api.dto;

import com.mabelowusu.pulse_check_api.model.Monitor;
import lombok.Data;

@Data
public class MonitorCreateResponse {
    
    private String message;
    private String deviceId;
    private String timeout;
    private String status;
    
    // Explicit getters as fallback for Lombok @Data
    public String getMessage() {
        return message;
    }
    
    public String getDeviceId() {
        return deviceId;
    }
    
    public String getTimeout() {
        return timeout;
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
    
    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public static MonitorCreateResponse from(Monitor monitor) {
        MonitorCreateResponse response = new MonitorCreateResponse();
        response.setMessage("Monitor created successfully");
        response.setDeviceId(monitor.getId());
        response.setTimeout(monitor.getTimeout().toString());
        response.setStatus(monitor.getStatus().toString());
        return response;
    }
}
