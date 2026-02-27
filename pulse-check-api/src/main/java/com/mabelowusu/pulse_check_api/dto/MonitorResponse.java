package com.mabelowusu.pulse_check_api.dto;

import com.mabelowusu.pulse_check_api.model.Monitor;
import com.mabelowusu.pulse_check_api.model.MonitorStatus;
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
