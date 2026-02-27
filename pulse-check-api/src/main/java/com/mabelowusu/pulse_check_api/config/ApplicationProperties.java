package com.mabelowusu.pulse_check_api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "pulse-check")
public class ApplicationProperties {

    private Timer timer;
    private Alert alert;
    private Validation validation;

    public static class Timer {
        private long checkIntervalMs;

        public long getCheckIntervalMs() { return checkIntervalMs; }
        public void setCheckIntervalMs(long checkIntervalMs) { this.checkIntervalMs = checkIntervalMs; }
    }

    public static class Alert {
        private int intervalSeconds;
        private int maxRetries;

        public int getIntervalSeconds() { return intervalSeconds; }
        public void setIntervalSeconds(int intervalSeconds) { this.intervalSeconds = intervalSeconds; }

        public int getMaxRetries() { return maxRetries; }
        public void setMaxRetries(int maxRetries) { this.maxRetries = maxRetries; }
    }

    public static class Validation {
        private int monitorIdMinLength;
        private int monitorIdMaxLength;
        private int monitorTimeoutMinSeconds;
        private int monitorTimeoutMaxSeconds;

        public int getMonitorIdMinLength() { return monitorIdMinLength; }
        public void setMonitorIdMinLength(int monitorIdMinLength) { this.monitorIdMinLength = monitorIdMinLength; }

        public int getMonitorIdMaxLength() { return monitorIdMaxLength; }
        public void setMonitorIdMaxLength(int monitorIdMaxLength) { this.monitorIdMaxLength = monitorIdMaxLength; }

        public int getMonitorTimeoutMinSeconds() { return monitorTimeoutMinSeconds; }
        public void setMonitorTimeoutMinSeconds(int monitorTimeoutMinSeconds) { this.monitorTimeoutMinSeconds = monitorTimeoutMinSeconds; }

        public int getMonitorTimeoutMaxSeconds() { return monitorTimeoutMaxSeconds; }
        public void setMonitorTimeoutMaxSeconds(int monitorTimeoutMaxSeconds) { this.monitorTimeoutMaxSeconds = monitorTimeoutMaxSeconds; }
    }

    // Getters and Setters
    public Timer getTimer() { return timer; }
    public void setTimer(Timer timer) { this.timer = timer; }

    public Alert getAlert() { return alert; }
    public void setAlert(Alert alert) { this.alert = alert; }

    public Validation getValidation() { return validation; }
    public void setValidation(Validation validation) { this.validation = validation; }
}
