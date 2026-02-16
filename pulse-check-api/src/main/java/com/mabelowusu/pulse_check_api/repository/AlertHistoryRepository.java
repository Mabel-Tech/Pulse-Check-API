package com.mabelowusu.pulse_check_api.repository;

import com.mabelowusu.pulse_check_api.model.AlertHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AlertHistoryRepository extends JpaRepository<AlertHistory, Long> {
    
    List<AlertHistory> findByDeviceIdOrderByTriggeredAtDesc(String deviceId);
    
    List<AlertHistory> findByTriggeredAtBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT COUNT(a) FROM AlertHistory a WHERE a.deviceId = ?1 AND a.triggeredAt >= ?2")
    long countAlertsSince(String deviceId, LocalDateTime since);
    
    @Query("SELECT a FROM AlertHistory a ORDER BY a.triggeredAt DESC")
    List<AlertHistory> findRecentAlerts();
}
