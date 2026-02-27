package com.mabelowusu.pulse_check_api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Data
@Configuration
@ConfigurationProperties(prefix = "")
public class EnvConfig {

    // Server Configuration
    private String serverPort;

    // Database Configuration
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;

    // Email Configuration
    private String gmailUsername;
    private String gmailPassword;

    // Logging Configuration
    private String logLevelSpringWeb;
    private String logLevelComMabel;
    private String logLevelRoot;

    // Timer Configuration
    private String timerCheckIntervalMs;

    // Alert Configuration
    private String alertIntervalSeconds;
    private String maxEmailRetries;

    // Validation Configuration
    private String monitorIdMinLength;
    private String monitorIdMaxLength;
    private String monitorTimeoutMinSeconds;
    private String monitorTimeoutMaxSeconds;
}
