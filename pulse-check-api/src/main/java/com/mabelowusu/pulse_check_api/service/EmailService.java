package com.mabelowusu.pulse_check_api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;
import org.springframework.beans.factory.annotation.Value;

import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private static final int MAX_EMAIL_RETRIES = 3;
    
    private final JavaMailSender mailSender;
    private final ResourceLoader resourceLoader;
    
    @Value("${spring.mail.from}")
    private String from;
    
    // Explicit constructor for dependency injection
    public EmailService(JavaMailSender mailSender, ResourceLoader resourceLoader) {
        this.mailSender = mailSender;
        this.resourceLoader = resourceLoader;
    }

    public void sendAlertEmail(String toEmail, String deviceId, String alertTime, int timeoutSeconds, String lastHeartbeat) {
        try {
            log.info("Sending alert email to {} for device {}", toEmail, deviceId);
            
            String htmlContent = readTemplate("templates/alert-email.html")
                .replace("{{deviceId}}", deviceId)
                .replace("{{alertEmail}}", toEmail)
                .replace("{{triggeredAt}}", alertTime)
                .replace("{{timeoutSeconds}}", String.valueOf(timeoutSeconds))
                .replace("{{lastHeartbeat}}", lastHeartbeat);
            
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(from);
            helper.setTo(toEmail);
            helper.setSubject("🚨 CRITICAL ALERT: Device " + deviceId + " is DOWN");
            helper.setText(htmlContent, true);
            
            // Retry logic for sending email
            sendEmailWithRetry(message);
            
            log.info("Alert email sent successfully to {} for device {}", toEmail, deviceId);
            
        } catch (Exception e) {
            log.error("Failed to send alert email to {} for device {}: {}", 
                     toEmail, deviceId, e.getMessage(), e);
            // Don't rethrow - we don't want email failures to break the monitoring system
        }
    }

    public void sendRecoveryEmail(String toEmail, String deviceId, String recoveryTime, long downtimeMinutes, String downtimeStart) {
        try {
            log.info("Sending recovery email to {} for device {}", toEmail, deviceId);
            
            String htmlContent = readTemplate("templates/recovery-email.html")
                .replace("{{deviceId}}", deviceId)
                .replace("{{alertEmail}}", toEmail)
                .replace("{{recoveryTime}}", recoveryTime)
                .replace("{{downtimeStart}}", downtimeStart)
                .replace("{{downtimeMinutes}}", String.valueOf(downtimeMinutes));
            
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(from);
            helper.setTo(toEmail);
            helper.setSubject("✅ RECOVERY: Device " + deviceId + " is BACK ONLINE");
            helper.setText(htmlContent, true);
            
            // Retry logic for sending email
            sendEmailWithRetry(message);
            
            log.info("Recovery email sent successfully to {} for device {}", toEmail, deviceId);
            
        } catch (Exception e) {
            log.error("Failed to send recovery email to {} for device {}: {}", 
                     toEmail, deviceId, e.getMessage(), e);
        }
    }

    private String readTemplate(String templatePath) {
        try {
            Resource resource = resourceLoader.getResource("classpath:" + templatePath);
            try (java.io.Reader reader = new java.io.InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
                return FileCopyUtils.copyToString(reader);
            }
        } catch (IOException e) {
            log.error("Failed to read template: {}", templatePath, e);
            return "<html><body><h1>Email Template Error</h1><p>Could not load email template.</p></body></html>";
        }
    }
    
    private void sendEmailWithRetry(MimeMessage message) {
        int attempt = 0;
        boolean success = false;
        
        while (!success && attempt < MAX_EMAIL_RETRIES) {
            attempt++;
            try {
                mailSender.send(message);
                success = true;
                log.info("Email sent successfully on attempt {}", attempt);
            } catch (Exception e) {
                log.warn("Email send attempt {} failed: {}", attempt, e.getMessage());
                
                if (attempt >= MAX_EMAIL_RETRIES) {
                    log.error("Failed to send email after {} attempts", MAX_EMAIL_RETRIES);
                    throw new RuntimeException("Email delivery failed after " + MAX_EMAIL_RETRIES + " attempts", e);
                }
                
                try {
                    Thread.sleep(2000 * attempt); // Exponential backoff: 2s, 4s, 6s
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Email retry interrupted", ie);
                }
            }
        }
    }
}
