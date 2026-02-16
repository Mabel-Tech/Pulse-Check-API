package com.mabelowusu.pulse_check_api.repository;

import com.mabelowusu.pulse_check_api.model.RecoveryHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RecoveryHistoryRepository extends JpaRepository<RecoveryHistory, Long> {
    
    List<RecoveryHistory> findByDeviceIdOrderByRecoveryTimeDesc(String deviceId);
    
    List<RecoveryHistory> findByRecoveryTimeBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT COUNT(r) FROM RecoveryHistory r WHERE r.deviceId = ?1 AND r.recoveryTime >= ?2")
    long countRecoveriesSince(String deviceId, LocalDateTime since);
    
    @Query("SELECT AVG(r.downtimeMinutes) FROM RecoveryHistory r WHERE r.deviceId = ?1")
    Double getAverageDowntimeForDevice(String deviceId);
    
    @Query("SELECT r FROM RecoveryHistory r ORDER BY r.recoveryTime DESC")
    List<RecoveryHistory> findRecentRecoveries();
}
