package com.mabelowusu.pulse_check_api.service;

import com.mabelowusu.pulse_check_api.model.AlertHistory;
import com.mabelowusu.pulse_check_api.model.Monitor;
import com.mabelowusu.pulse_check_api.model.MonitorStatus;
import com.mabelowusu.pulse_check_api.model.RecoveryHistory;
import com.mabelowusu.pulse_check_api.repository.AlertHistoryRepository;
import com.mabelowusu.pulse_check_api.repository.MonitorRepository;
import com.mabelowusu.pulse_check_api.repository.RecoveryHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AlertService {

    private static final Logger log = LoggerFactory.getLogger(AlertService.class);
    
    private final AlertHistoryRepository alertHistoryRepository;
    private final RecoveryHistoryRepository recoveryHistoryRepository;
    private final MonitorRepository monitorRepository;
    private final EmailService emailService;

    public void triggerAlert(Monitor monitor) {
        log.warn("ALERT TRIGGERED: Device {} is down!", monitor.getId());

        monitor.setStatus(MonitorStatus.DOWN);
        monitor.setAlerted(true);

        String alertJson = String.format(
                "{\"ALERT\": \"Device %s is down!\", \"time\": \"%s\"}",
                monitor.getId(),
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
        
        System.out.println(alertJson);
        log.info("Alert logged: {}", alertJson);

        saveAlertToHistory(monitor);

        monitorRepository.save(monitor);

        sendEmailAlert(monitor);
    }
    
    private void sendEmailAlert(Monitor monitor) {
        try {
            String alertTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            emailService.sendAlertEmail(
                monitor.getAlertEmail(), 
                monitor.getId(), 
                alertTime,
                monitor.getTimeout(),
                monitor.getLastHeartbeat() != null ? monitor.getLastHeartbeat().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : "Never"
            );
            log.info("Email alert sent for device: {}", monitor.getId());
        } catch (Exception e) {
            log.error("Failed to send email alert for device {}: {}", monitor.getId(), e.getMessage());
        }
    }
    
    private void saveAlertToHistory(Monitor monitor) {
        AlertHistory alertHistory = new AlertHistory();
        alertHistory.setDeviceId(monitor.getId());
        alertHistory.setAlertEmail(monitor.getAlertEmail());
        alertHistory.setTriggeredAt(LocalDateTime.now());
        alertHistory.setTimeoutSeconds(monitor.getTimeout());
        alertHistory.setLastHeartbeat(monitor.getLastHeartbeat());
        
        alertHistoryRepository.save(alertHistory);
        log.info("Alert saved to history for device: {}", monitor.getId());
    }
    
    public void sendRecoveryNotification(Monitor monitor, LocalDateTime downtimeStart) {
        try {
            String recoveryTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            long downtimeMinutes = java.time.Duration.between(downtimeStart, LocalDateTime.now()).toMinutes();
            
            // Send email notification
            emailService.sendRecoveryEmail(
                monitor.getAlertEmail(), 
                monitor.getId(), 
                recoveryTime, 
                downtimeMinutes,
                downtimeStart.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            );
            
            // Save recovery to database
            saveRecoveryToHistory(monitor, downtimeStart, downtimeMinutes);
            
            log.info("Recovery notification sent for device: {} (downtime: {} minutes)", monitor.getId(), downtimeMinutes);
        } catch (Exception e) {
            log.error("Failed to send recovery notification for device {}: {}", monitor.getId(), e.getMessage());
        }
    }
    
    private void saveRecoveryToHistory(Monitor monitor, LocalDateTime downtimeStart, long downtimeMinutes) {
        RecoveryHistory recoveryHistory = new RecoveryHistory();
        recoveryHistory.setDeviceId(monitor.getId());
        recoveryHistory.setAlertEmail(monitor.getAlertEmail());
        recoveryHistory.setRecoveryTime(LocalDateTime.now());
        recoveryHistory.setDowntimeStart(downtimeStart);
        recoveryHistory.setDowntimeMinutes(downtimeMinutes);
        
        recoveryHistoryRepository.save(recoveryHistory);
        log.info("Recovery saved to history for device: {} (downtime: {} minutes)", monitor.getId(), downtimeMinutes);
    }
}
