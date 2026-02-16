package com.mabelowusu.pulse_check_api.dto;

import lombok.Data;

import java.util.List;

@Data
public class AlertHistoryListResponse {
    
    private List<AlertHistoryResponse> alerts;
    private long total;
    
    // Explicit getters and setters as fallback for Lombok @Data
    public List<AlertHistoryResponse> getAlerts() {
        return alerts;
    }
    
    public void setAlerts(List<AlertHistoryResponse> alerts) {
        this.alerts = alerts;
    }
    
    public long getTotal() {
        return total;
    }
    
    public void setTotal(long total) {
        this.total = total;
    }
}
