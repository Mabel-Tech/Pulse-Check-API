package com.mabelowusu.pulse_check_api.dto;

import com.mabelowusu.pulse_check_api.model.AlertHistory;

import java.time.LocalDateTime;

public class AlertHistoryResponse {
    
    private Long id;
    private String deviceId;
    private String alertEmail;
    private LocalDateTime triggeredAt;
    private Integer timeoutSeconds;
    private LocalDateTime lastHeartbeat;
    private LocalDateTime createdAt;
    
    // Explicit getters and setters as fallback for Lombok @Data
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getDeviceId() {
        return deviceId;
    }
    
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    
    public String getAlertEmail() {
        return alertEmail;
    }
    
    public void setAlertEmail(String alertEmail) {
        this.alertEmail = alertEmail;
    }
    
    public LocalDateTime getTriggeredAt() {
        return triggeredAt;
    }
    
    public void setTriggeredAt(LocalDateTime triggeredAt) {
        this.triggeredAt = triggeredAt;
    }
    
    public Integer getTimeoutSeconds() {
        return timeoutSeconds;
    }
    
    public void setTimeoutSeconds(Integer timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }
    
    public LocalDateTime getLastHeartbeat() {
        return lastHeartbeat;
    }
    
    public void setLastHeartbeat(LocalDateTime lastHeartbeat) {
        this.lastHeartbeat = lastHeartbeat;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public static AlertHistoryResponse from(AlertHistory alertHistory) {
        AlertHistoryResponse response = new AlertHistoryResponse();
        response.setId(alertHistory.getId());
        response.setDeviceId(alertHistory.getDeviceId());
        response.setAlertEmail(alertHistory.getAlertEmail());
        response.setTriggeredAt(alertHistory.getTriggeredAt());
        response.setTimeoutSeconds(alertHistory.getTimeoutSeconds());
        response.setLastHeartbeat(alertHistory.getLastHeartbeat());
        response.setCreatedAt(alertHistory.getCreatedAt());
        return response;
    }
}
