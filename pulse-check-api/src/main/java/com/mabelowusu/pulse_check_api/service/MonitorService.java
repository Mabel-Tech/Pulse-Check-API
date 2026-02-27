package com.mabelowusu.pulse_check_api.service;

import com.mabelowusu.pulse_check_api.dto.*;
import com.mabelowusu.pulse_check_api.exception.MonitorNotFoundException;
import com.mabelowusu.pulse_check_api.model.Monitor;
import com.mabelowusu.pulse_check_api.model.MonitorStatus;
import com.mabelowusu.pulse_check_api.repository.MonitorRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MonitorService {

    private static final Logger log = LoggerFactory.getLogger(MonitorService.class);
    
    private final MonitorRepository monitorRepository;
    private final TimerService timerService;
    private final AlertService alertService;


    public Monitor createMonitor(String id, Integer timeout, String alertEmail) {
        log.info("Creating monitor: id={}, timeout={}, email={}", id, timeout, alertEmail);
        
        if (monitorRepository.existsById(id)) {
            throw new IllegalArgumentException("Monitor with ID '" + id + "' already exists");
        }
        
        Monitor monitor = Monitor.builder()
                .id(id)
                .timeout(timeout)
                .alertEmail(alertEmail)
                .status(MonitorStatus.ACTIVE)
                .lastHeartbeat(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusSeconds(timeout))
                .alerted(false)
                .build();
        monitor = monitorRepository.save(monitor);
        
        // Start the timer for this monitor
        timerService.startTimer(monitor);
        
        log.info("Monitor created successfully: {}", monitor.getId());
        return monitor;
    }

    public Monitor sendHeartbeat(String id) {
        log.info("Processing heartbeat for device: {}", id);
        
        Monitor monitor = getMonitorById(id);
        
        // Check if device was DOWN and is now recovering
        boolean wasDown = monitor.getStatus() == MonitorStatus.DOWN;
        LocalDateTime downtimeStart = monitor.getLastHeartbeat();
        
        // Reset timer and update heartbeat
        monitor.resetTimer();
        
        // Set status to ACTIVE only if heartbeat is received
        monitor.setStatus(MonitorStatus.ACTIVE);
        
        monitor = monitorRepository.save(monitor);
        
        // Send recovery notification if device was DOWN
        if (wasDown) {
            alertService.sendRecoveryNotification(monitor, downtimeStart);
        }
        
        // Restart the timer
        timerService.restartTimer(monitor);
        
        log.info("Heartbeat processed for device: {}", id);
        return monitor;
    }

    public Monitor pauseMonitor(String id) {
        log.info("Pausing monitor for device: {}", id);
        
        Monitor monitor = getMonitorById(id);
        monitor.pause();
        monitor = monitorRepository.save(monitor);
        
        // Stop the timer
        timerService.stopTimer(id);
        
        log.info("Monitor paused for device: {}", id);
        return monitor;
    }

    public Monitor getMonitor(String id) {
        return getMonitorById(id);
    }

    public List<Monitor> getAllMonitors() {
        return monitorRepository.findAll();
    }

    public long getMonitorCount() {
        return monitorRepository.count();
    }

    public List<Monitor> getExpiredMonitors() {
        return monitorRepository.findExpiredMonitors(LocalDateTime.now());
    }

    private Monitor getMonitorById(String id) {
        Optional<Monitor> monitor = monitorRepository.findById(id);
        if (monitor.isEmpty()) {
            throw new MonitorNotFoundException("Monitor with ID '" + id + "' not found");
        }
        return monitor.get();
    }

    // Response building methods
    public MonitorCreateResponse createMonitorResponse(MonitorRequest request) {
        log.info("Creating monitor for device: {}", request.getId());
        
        Monitor monitor = createMonitor(request.getId(), request.getTimeout(), request.getAlertEmail());
        
        return MonitorCreateResponse.from(monitor);
    }

    public HeartbeatResponse sendHeartbeatResponse(String id) {
        log.info("Received heartbeat for device: {}", id);
        
        Monitor monitor = sendHeartbeat(id);
        
        return HeartbeatResponse.from(monitor);
    }

    public PauseMonitorResponse pauseMonitorResponse(String id) {
        log.info("Pausing monitor for device: {}", id);
        
        Monitor monitor = pauseMonitor(id);
        
        return PauseMonitorResponse.from(monitor);
    }

    public MonitorResponse getMonitorResponse(String id) {
        log.info("Getting monitor for device: {}", id);
        
        Monitor monitor = getMonitor(id);
        
        return MonitorResponse.from(monitor);
    }

    public AllMonitorsResponse getAllMonitorsResponse() {
        log.info("Getting all monitors");
        
        AllMonitorsResponse response = new AllMonitorsResponse();
        response.setMonitors(getAllMonitors().stream()
                .map(MonitorResponse::from)
                .toList());
        response.setTotal(getMonitorCount());
        
        return response;
    }
}
