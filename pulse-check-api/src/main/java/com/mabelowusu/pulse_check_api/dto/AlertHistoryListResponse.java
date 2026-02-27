package com.mabelowusu.pulse_check_api.dto;

import lombok.Data;

import java.util.List;

@Data
public class AlertHistoryListResponse {
    
    private List<AlertHistoryResponse> alerts;
    private long total;
}
