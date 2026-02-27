package com.mabelowusu.pulse_check_api.service;

import com.mabelowusu.pulse_check_api.model.Monitor;
import com.mabelowusu.pulse_check_api.repository.MonitorRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class TimerService {

    private static final Logger log = LoggerFactory.getLogger(TimerService.class);
    
    private final AlertService alertService;
    private final MonitorRepository monitorRepository;

    private final ConcurrentHashMap<String, Monitor> activeTimers = new ConcurrentHashMap<>();

    public void startTimer(Monitor monitor) {
        log.info("Starting timer for device: {}", monitor.getId());
        activeTimers.put(monitor.getId(), monitor);
    }

    public void restartTimer(Monitor monitor) {
        log.info("Restarting timer for device: {}", monitor.getId());
        activeTimers.put(monitor.getId(), monitor);
    }

    public void stopTimer(String deviceId) {
        log.info("Stopping timer for device: {}", deviceId);
        activeTimers.remove(deviceId);
    }

    @Scheduled(fixedRate = 1000) // Check every second
    public void checkTimers() {
        log.debug("Checking {} active timers", activeTimers.size());
        
        LocalDateTime now = LocalDateTime.now();
        
        // Update expired monitors and send repeated alerts
        activeTimers.entrySet().forEach(entry -> {
            Monitor monitor = entry.getValue();
            
            if (monitor.isExpired()) {
                log.warn("Timer expired for device: {}", monitor.getId());
                alertService.triggerAlert(monitor);
                
                // Reset timer for next alert cycle (every 60 seconds)
                monitor.setExpiresAt(LocalDateTime.now().plusSeconds(monitor.getTimeout()));
                monitor.setAlerted(true);
                
                // Update the timer in the map with the new expiresAt
                activeTimers.put(monitor.getId(), monitor);
            }
        });
    }

    public int getActiveTimerCount() {
        return activeTimers.size();
    }
}
