package com.mabelowusu.pulse_check_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MonitorNotFoundException extends RuntimeException {
    
    public MonitorNotFoundException(String message) {
        super(message);
    }
    
    public MonitorNotFoundException(String deviceId, Throwable cause) {
        super("Monitor with ID '" + deviceId + "' not found", cause);
    }
}
