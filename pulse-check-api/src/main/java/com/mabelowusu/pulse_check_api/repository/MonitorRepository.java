package com.mabelowusu.pulse_check_api.repository;

import com.mabelowusu.pulse_check_api.model.Monitor;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MonitorRepository extends JpaRepository<Monitor, String> {
    
    @Query("SELECT m FROM Monitor m WHERE m.expiresAt < :now AND m.status = 'ACTIVE'")
    List<Monitor> findExpiredMonitors(@Nonnull LocalDateTime now);
    
    boolean existsById(@Nonnull String id);
}
