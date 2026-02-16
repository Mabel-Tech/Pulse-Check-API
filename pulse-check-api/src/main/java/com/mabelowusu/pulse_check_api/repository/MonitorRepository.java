package com.mabelowusu.pulse_check_api.repository;

import com.mabelowusu.pulse_check_api.model.Monitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MonitorRepository extends JpaRepository<Monitor, String> {
    
    @Query("SELECT m FROM Monitor m WHERE m.expiresAt < :now AND m.status = 'ACTIVE'")
    List<Monitor> findExpiredMonitors(LocalDateTime now);
    
    @Query("SELECT m FROM Monitor m WHERE m.status = 'ACTIVE'")
    List<Monitor> findActiveMonitors();
    
    @Query("SELECT m FROM Monitor m WHERE m.status = 'PAUSED'")
    List<Monitor> findPausedMonitors();
    
    @Query("SELECT m FROM Monitor m WHERE m.status = 'DOWN'")
    List<Monitor> findDownMonitors();
    
    boolean existsById(String id);
}
