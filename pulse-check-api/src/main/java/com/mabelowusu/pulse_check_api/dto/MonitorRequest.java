package com.mabelowusu.pulse_check_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MonitorRequest {
    
    @NotBlank(message = "Device ID is required")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,50}$", 
             message = "Device ID must be 3-50 characters long and contain only letters, numbers, hyphens, and underscores")
    private String id;
    
    @NotNull(message = "Timeout is required")
    @Min(value = 10, message = "Timeout must be at least 10 seconds")
    @Max(value = 86400, message = "Timeout must not exceed 24 hours (86400 seconds)")
    private Integer timeout;
    
    @NotBlank(message = "Alert email is required")
    @Email(message = "Invalid email format")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
             message = "Email must be a valid format")
    private String alertEmail;
    
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
}
