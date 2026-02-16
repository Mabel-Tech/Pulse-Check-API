package com.mabelowusu.pulse_check_api.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "recovery_history")
public class RecoveryHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String deviceId;
    
    @Column(nullable = false)
    private String alertEmail;
    
    @Column(nullable = false)
    private LocalDateTime recoveryTime;
    
    @Column(nullable = false)
    private LocalDateTime downtimeStart;
    
    @Column(nullable = false)
    private Long downtimeMinutes;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    // Explicit getters and setters as fallback for Lombok @Data
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getDeviceId() {
        return deviceId;
    }
    
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    
    public String getAlertEmail() {
        return alertEmail;
    }
    
    public void setAlertEmail(String alertEmail) {
        this.alertEmail = alertEmail;
    }
    
    public LocalDateTime getRecoveryTime() {
        return recoveryTime;
    }
    
    public void setRecoveryTime(LocalDateTime recoveryTime) {
        this.recoveryTime = recoveryTime;
    }
    
    public LocalDateTime getDowntimeStart() {
        return downtimeStart;
    }
    
    public void setDowntimeStart(LocalDateTime downtimeStart) {
        this.downtimeStart = downtimeStart;
    }
    
    public Long getDowntimeMinutes() {
        return downtimeMinutes;
    }
    
    public void setDowntimeMinutes(Long downtimeMinutes) {
        this.downtimeMinutes = downtimeMinutes;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
