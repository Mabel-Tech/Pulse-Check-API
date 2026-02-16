package com.mabelowusu.pulse_check_api.service;

import com.mabelowusu.pulse_check_api.dto.AlertHistoryListResponse;
import com.mabelowusu.pulse_check_api.dto.AlertHistoryResponse;
import com.mabelowusu.pulse_check_api.model.AlertHistory;
import com.mabelowusu.pulse_check_api.repository.AlertHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertHistoryService {

    private static final Logger log = LoggerFactory.getLogger(AlertHistoryService.class);
    
    private final AlertHistoryRepository alertHistoryRepository;
    
    // Explicit constructor for dependency injection
    public AlertHistoryService(AlertHistoryRepository alertHistoryRepository) {
        this.alertHistoryRepository = alertHistoryRepository;
    }

    public AlertHistoryListResponse getAllAlertsResponse() {
        log.info("Getting all alert history");
        
        List<AlertHistory> alerts = alertHistoryRepository.findAll();
        List<AlertHistoryResponse> alertResponses = alerts.stream()
                .map(AlertHistoryResponse::from)
                .collect(Collectors.toList());
        
        AlertHistoryListResponse response = new AlertHistoryListResponse();
        response.setAlerts(alertResponses);
        response.setTotal(alertResponses.size());
        
        return response;
    }

    public AlertHistoryListResponse getAlertsByDeviceResponse(String deviceId) {
        log.info("Getting alert history for device: {}", deviceId);
        
        List<AlertHistory> alerts = alertHistoryRepository.findByDeviceIdOrderByTriggeredAtDesc(deviceId);
        List<AlertHistoryResponse> alertResponses = alerts.stream()
                .map(AlertHistoryResponse::from)
                .collect(Collectors.toList());
        
        AlertHistoryListResponse response = new AlertHistoryListResponse();
        response.setAlerts(alertResponses);
        response.setTotal(alertResponses.size());
        
        return response;
    }

    public AlertHistoryListResponse getRecentAlertsResponse() {
        log.info("Getting recent alerts");
        
        List<AlertHistory> alerts = alertHistoryRepository.findRecentAlerts();
        List<AlertHistoryResponse> alertResponses = alerts.stream()
                .map(AlertHistoryResponse::from)
                .collect(Collectors.toList());
        
        AlertHistoryListResponse response = new AlertHistoryListResponse();
        response.setAlerts(alertResponses);
        response.setTotal(alertResponses.size());
        
        return response;
    }

    public AlertHistoryListResponse getAlertsByPeriodResponse(LocalDateTime start, LocalDateTime end) {
        log.info("Getting alerts from {} to {}", start, end);
        
        List<AlertHistory> alerts = alertHistoryRepository.findByTriggeredAtBetween(start, end);
        List<AlertHistoryResponse> alertResponses = alerts.stream()
                .map(AlertHistoryResponse::from)
                .collect(Collectors.toList());
        
        AlertHistoryListResponse response = new AlertHistoryListResponse();
        response.setAlerts(alertResponses);
        response.setTotal(alertResponses.size());
        
        return response;
    }

    public long countAlertsSince(String deviceId, LocalDateTime since) {
        log.info("Counting alerts for device {} since {}", deviceId, since);
        return alertHistoryRepository.countAlertsSince(deviceId, since);
    }

    public long countAlertsForDevice(String deviceId) {
        log.info("Counting all alerts for device: {}", deviceId);
        List<AlertHistory> alerts = alertHistoryRepository.findByDeviceIdOrderByTriggeredAtDesc(deviceId);
        return alerts.size();
    }
}
