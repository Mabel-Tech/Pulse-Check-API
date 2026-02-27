package com.mabelowusu.pulse_check_api.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
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
}