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

    
    public static HeartbeatResponse from(Monitor monitor) {
        HeartbeatResponse response = new HeartbeatResponse();
        response.setMessage("Heartbeat received successfully");
        response.setDeviceId(monitor.getId());
        response.setStatus(monitor.getStatus().toString());
        response.setNextExpiry(monitor.getExpiresAt());
        return response;
    }
}
