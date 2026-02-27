package com.mabelowusu.pulse_check_api.dto;

import lombok.Data;

import java.util.List;

@Data
public class AllMonitorsResponse {

    private List<MonitorResponse> monitors;
    private long total;
}
