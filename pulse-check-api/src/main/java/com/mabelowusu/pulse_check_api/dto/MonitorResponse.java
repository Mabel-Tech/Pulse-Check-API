package com.mabelowusu.pulse_check_api.dto;

import com.mabelowusu.pulse_check_api.model.Monitor;
import com.mabelowusu.pulse_check_api.model.Monitor.MonitorStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MonitorResponse {
    
    private String id;
    private Integer timeout;
    private String alertEmail;
    private MonitorStatus status;
    private LocalDateTime lastHeartbeat;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Explicit getters as fallback for Lombok @Data
    public String getId() {
        return id;
    }
    
    public Integer getTimeout() {
        return timeout;
    }
    
    public String getAlertEmail() {
        return alertEmail;
    }
    
    public MonitorStatus getStatus() {
        return status;
    }
    
    public LocalDateTime getLastHeartbeat() {
        return lastHeartbeat;
    }
    
    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    // Explicit setters as fallback for Lombok @Data
    public void setId(String id) {
        this.id = id;
    }
    
    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
    
    public void setAlertEmail(String alertEmail) {
        this.alertEmail = alertEmail;
    }
    
    public void setStatus(MonitorStatus status) {
        this.status = status;
    }
    
    public void setLastHeartbeat(LocalDateTime lastHeartbeat) {
        this.lastHeartbeat = lastHeartbeat;
    }
    
    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public static MonitorResponse from(Monitor monitor) {
        MonitorResponse response = new MonitorResponse();
        response.setId(monitor.getId());
        response.setTimeout(monitor.getTimeout());
        response.setAlertEmail(monitor.getAlertEmail());
        response.setStatus(monitor.getStatus());
        response.setLastHeartbeat(monitor.getLastHeartbeat());
        response.setExpiresAt(monitor.getExpiresAt());
        response.setCreatedAt(monitor.getCreatedAt());
        response.setUpdatedAt(monitor.getUpdatedAt());
        return response;
    }
}
