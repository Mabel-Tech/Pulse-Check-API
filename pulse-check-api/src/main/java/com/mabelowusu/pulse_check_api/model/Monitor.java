package com.mabelowusu.pulse_check_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "monitors")
public class Monitor {

    @Id
    private String id;
    
    @Column(nullable = false)
    private Integer timeout;
    
    @Column(nullable = false)
    private String alertEmail;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private MonitorStatus status = MonitorStatus.ACTIVE;
    
    @Column(nullable = false)
    private LocalDateTime lastHeartbeat;
    
    @Column(nullable = false)
    private LocalDateTime expiresAt;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    @Column(nullable = false)
    @Builder.Default
    private boolean alerted = false;
    
    public void resetTimer() {
        this.lastHeartbeat = LocalDateTime.now();
        this.expiresAt = LocalDateTime.now().plusSeconds(timeout);
        this.alerted = false; // Reset alert status on heartbeat
        // Don't automatically change status - let the business logic handle it
    }
    
    public void pause() {
        this.status = MonitorStatus.PAUSED;
    }
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt) && 
               (status == MonitorStatus.ACTIVE || status == MonitorStatus.DOWN);
    }
}
