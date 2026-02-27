package com.mabelowusu.pulse_check_api.dto;

import com.mabelowusu.pulse_check_api.model.AlertHistory;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AlertHistoryResponse {
    
    private Long id;
    private String deviceId;
    private String alertEmail;
    private LocalDateTime triggeredAt;
    private Integer timeoutSeconds;
    private LocalDateTime lastHeartbeat;
    private LocalDateTime createdAt;

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
