package com.mabelowusu.pulse_check_api.exception;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.Data;

@Data
public class ErrorResponse {
    
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private Map<String, String> validationErrors;
    private String path;

    public ErrorResponse() {}

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private LocalDateTime timestamp;
        private int status;
        private String error;
        private String message;
        private Map<String, String> validationErrors;
        private String path;
        
        public Builder timestamp(LocalDateTime timestamp) { this.timestamp = timestamp; return this; }
        public Builder status(int status) { this.status = status; return this; }
        public Builder error(String error) { this.error = error; return this; }
        public Builder message(String message) { this.message = message; return this; }
        public Builder validationErrors(Map<String, String> validationErrors) { this.validationErrors = validationErrors; return this; }
        public Builder path(String path) { this.path = path; return this; }
        
        public ErrorResponse build() {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.timestamp = this.timestamp;
            errorResponse.status = this.status;
            errorResponse.error = this.error;
            errorResponse.message = this.message;
            errorResponse.validationErrors = this.validationErrors;
            errorResponse.path = this.path;
            return errorResponse;
        }
    }
}
