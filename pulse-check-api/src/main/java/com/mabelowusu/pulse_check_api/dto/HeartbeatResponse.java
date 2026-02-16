package com.mabelowusu.pulse_check_api.dto;

import com.mabelowusu.pulse_check_api.model.Monitor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HeartbeatResponse {
    
    private String message;
    private String deviceId;
    private String status;
    private LocalDateTime nextExpiry;
    
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
    
    public LocalDateTime getNextExpiry() {
        return nextExpiry;
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
    
    public void setNextExpiry(LocalDateTime nextExpiry) {
        this.nextExpiry = nextExpiry;
    }
    
    public static HeartbeatResponse from(Monitor monitor) {
        HeartbeatResponse response = new HeartbeatResponse();
        response.setMessage("Heartbeat received successfully");
        response.setDeviceId(monitor.getId());
        response.setStatus(monitor.getStatus().toString());
        response.setNextExpiry(monitor.getExpiresAt());
        return response;
    }
}
