package com.mabelowusu.pulse_check_api.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "monitors")
public class Monitor {

    // Default constructor for JPA
    public Monitor() {}

    @Id
    private String id;
    
    @Column(nullable = false)
    private Integer timeout;
    
    @Column(nullable = false)
    private String alertEmail;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
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
    private boolean alerted = false;
    
    public Monitor(String id, Integer timeout, String alertEmail) {
        this.id = id;
        this.timeout = timeout;
        this.alertEmail = alertEmail;
        this.lastHeartbeat = LocalDateTime.now();
        this.expiresAt = LocalDateTime.now().plusSeconds(timeout);
        this.status = MonitorStatus.ACTIVE;
    }
    
    public void resetTimer() {
        this.lastHeartbeat = LocalDateTime.now();
        this.expiresAt = LocalDateTime.now().plusSeconds(timeout);
        this.alerted = false; // Reset alert status on heartbeat
        // Don't automatically change status - let the business logic handle it
    }
    
    public void pause() {
        this.status = MonitorStatus.PAUSED;
    }
    
    public void resume() {
        this.status = MonitorStatus.ACTIVE;
        resetTimer();
    }
    
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt) && 
               (status == MonitorStatus.ACTIVE || status == MonitorStatus.DOWN);
    }
    
    // Explicit getters as fallback for Lombok @Data
    public String getId() {
        return id;
    }
    
    public Integer getTimeout() {
        return timeout;
    }
    
    public String getAlertEmail() {
        return alertEmail;
    }
    
    public MonitorStatus getStatus() {
        return status;
    }
    
    public LocalDateTime getLastHeartbeat() {
        return lastHeartbeat;
    }
    
    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    // Explicit setters as fallback for Lombok @Data
    public void setId(String id) {
        this.id = id;
    }
    
    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
    
    public void setAlertEmail(String alertEmail) {
        this.alertEmail = alertEmail;
    }
    
    public void setStatus(MonitorStatus status) {
        this.status = status;
    }
    
    public void setLastHeartbeat(LocalDateTime lastHeartbeat) {
        this.lastHeartbeat = lastHeartbeat;
    }
    
    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
    
    public boolean isAlerted() {
        return alerted;
    }
    
    public void setAlerted(boolean alerted) {
        this.alerted = alerted;
    }
    
    public enum MonitorStatus {
        ACTIVE,
        PAUSED,
        DOWN
    }
}
