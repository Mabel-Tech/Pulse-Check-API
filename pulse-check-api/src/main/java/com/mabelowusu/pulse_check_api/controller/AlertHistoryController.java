package com.mabelowusu.pulse_check_api.controller;

import com.mabelowusu.pulse_check_api.dto.AlertHistoryListResponse;
import com.mabelowusu.pulse_check_api.dto.AlertHistoryResponse;
import com.mabelowusu.pulse_check_api.model.AlertHistory;
import com.mabelowusu.pulse_check_api.service.AlertHistoryService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/alerts")
public class AlertHistoryController {

    private AlertHistoryService alertHistoryService;

    public AlertHistoryController(AlertHistoryService alertHistoryService) {
        this.alertHistoryService = alertHistoryService;
    }

    @GetMapping
    public ResponseEntity<AlertHistoryListResponse> getAllAlerts() {
        AlertHistoryListResponse response = alertHistoryService.getAllAlertsResponse();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/device/{deviceId}")
    public ResponseEntity<AlertHistoryListResponse> getAlertsByDevice(@PathVariable String deviceId) {
        AlertHistoryListResponse response = alertHistoryService.getAlertsByDeviceResponse(deviceId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/recent")
    public ResponseEntity<AlertHistoryListResponse> getRecentAlerts() {
        AlertHistoryListResponse response = alertHistoryService.getRecentAlertsResponse();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/period")
    public ResponseEntity<AlertHistoryListResponse> getAlertsByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        
        if (start.isAfter(end)) {
            return ResponseEntity.badRequest().build();
        }
        
        AlertHistoryListResponse response = alertHistoryService.getAlertsByPeriodResponse(start, end);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/device/{deviceId}/count")
    public ResponseEntity<Long> getAlertCountForDevice(
            @PathVariable String deviceId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime since) {
        
        long count;
        if (since != null) {
            count = alertHistoryService.countAlertsSince(deviceId, since);
        } else {
            count = alertHistoryService.countAlertsForDevice(deviceId);
        }
        
        return ResponseEntity.ok(count);
    }
}
