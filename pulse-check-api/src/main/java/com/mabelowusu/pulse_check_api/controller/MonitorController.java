package com.mabelowusu.pulse_check_api.controller;

import com.mabelowusu.pulse_check_api.dto.*;
import com.mabelowusu.pulse_check_api.service.MonitorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/monitors")
public class MonitorController {

    private MonitorService monitorService;

    public MonitorController(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @PostMapping
    public ResponseEntity<MonitorCreateResponse> createMonitor(@Valid @RequestBody MonitorRequest request) {
        MonitorCreateResponse response = monitorService.createMonitorResponse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{id}/heartbeat")
    public ResponseEntity<HeartbeatResponse> sendHeartbeat(@PathVariable String id) {
        HeartbeatResponse response = monitorService.sendHeartbeatResponse(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/pause")
    public ResponseEntity<PauseMonitorResponse> pauseMonitor(@PathVariable String id) {
        PauseMonitorResponse response = monitorService.pauseMonitorResponse(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonitorResponse> getMonitor(@PathVariable String id) {
        MonitorResponse response = monitorService.getMonitorResponse(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<AllMonitorsResponse> getAllMonitors() {
        AllMonitorsResponse response = monitorService.getAllMonitorsResponse();
        return ResponseEntity.ok(response);
    }
}
