package com.mabelowusu.pulse_check_api.dto;

import lombok.Data;

import java.util.List;

@Data
public class AllMonitorsResponse {
    
    private List<MonitorResponse> monitors;
    private long total;
    
    // Explicit getters as fallback for Lombok @Data
    public List<MonitorResponse> getMonitors() {
        return monitors;
    }
    
    public long getTotal() {
        return total;
    }
    
    // Explicit setters as fallback for Lombok @Data
    public void setMonitors(List<MonitorResponse> monitors) {
        this.monitors = monitors;
    }
    
    public void setTotal(long total) {
        this.total = total;
    }
}
