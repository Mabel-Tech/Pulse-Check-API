# Pulse-Check-API ("Watchdog" Sentinel)

A Dead Man's Switch API for critical infrastructure monitoring.

## Architecture

![Architecture Diagram](architecture-diagram.png)

A Spring Boot REST API with PostgreSQL database for device monitoring and alerting.

## Setup Instructions

### Prerequisites
- Java 21 or higher
- PostgreSQL 12 or higher
- Maven 3.8 or higher

### Installation

1. **Clone the repository:**
```bash
git clone https://github.com/Mabel-Tech/Pulse-Check-API.git
cd Pulse-Check-API/pulse-check-api
```

2. **Configure PostgreSQL:**
```sql
CREATE DATABASE pulse_check;
CREATE USER postgres WITH PASSWORD 'YOUR_PASSWORD_HERE';
GRANT ALL PRIVILEGES ON DATABASE pulse_check TO postgres;
```

3. **Update application.yaml:**
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/pulse_check
    username: postgres
    password: YOUR_PASSWORD_HERE
```

4. **Configure Environment Variables:**
Create a `.env` file or set environment variables:
```bash
# Gmail SMTP Configuration
GMAIL_USERNAME=your-email@gmail.com
GMAIL_PASSWORD=your-app-password
```

5. **Run the application:**
```bash
./mvnw.cmd spring-boot:run
```

The API will start on `http://localhost:8081`

## API Documentation

### Base URL
```
http://localhost:8081
```

### Endpoints

#### 1. Create Monitor
**POST** `/monitors`

Create a new monitor with timeout and alert configuration.

**Request Body:**
```json
{
  "id": "device-123",
  "timeout": 60,
  "alertEmail": "admin@critmon.com"
}
```

**Validation Rules:**
- `id`: 3-50 characters, alphanumeric + hyphens/underscores
- `timeout`: 10-86400 seconds (10 seconds to 24 hours)
- `alertEmail`: Valid email format

**Response (201 Created):**
```json
{
  "message": "Monitor created successfully",
  "deviceId": "device-123",
  "timeout": "60",
  "status": "ACTIVE"
}
```

#### 2. Send Heartbeat
**POST** `/monitors/{id}/heartbeat`

Reset the countdown timer for an existing monitor.

**Path Parameters:**
- `id`: Device identifier

**Response (200 OK):**
```json
{
  "message": "Heartbeat received successfully",
  "deviceId": "device-123",
  "status": "ACTIVE",
  "nextExpiry": "2026-02-14T10:05:30"
}
```

**Error Responses:**
- `404 Not Found`: Monitor with specified ID doesn't exist

#### 3. Pause Monitor
**POST** `/monitors/{id}/pause`

Stop monitoring for a device (maintenance mode).

**Path Parameters:**
- `id`: Device identifier

**Response (200 OK):**
```json
{
  "message": "Monitor paused successfully",
  "deviceId": "device-123",
  "status": "PAUSED"
}
```

#### 4. Get Monitor Status
**GET** `/monitors/{id}`

Retrieve current status and configuration of a monitor.

**Path Parameters:**
- `id`: Device identifier

**Response (200 OK):**
```json
{
  "id": "device-123",
  "timeout": 60,
  "alertEmail": "admin@critmon.com",
  "status": "ACTIVE",
  "lastHeartbeat": "2026-02-14T10:04:30",
  "expiresAt": "2026-02-14T10:05:30",
  "createdAt": "2026-02-14T10:03:30",
  "updatedAt": "2026-02-14T10:04:30"
}
```

#### 5. Get All Monitors
**GET** `/monitors`

Retrieve all monitors with summary statistics.

**Response (200 OK):**
```json
{
  "monitors": [
    {
      "id": "device-123",
      "timeout": 60,
      "alertEmail": "admin@critmon.com",
      "status": "ACTIVE",
      "lastHeartbeat": "2026-02-14T10:04:30",
      "expiresAt": "2026-02-14T10:05:30",
      "createdAt": "2026-02-14T10:03:30",
      "updatedAt": "2026-02-14T10:04:30"
    }
  ],
  "total": 1
}
```

## Error Handling

### Validation Errors (400 Bad Request)
```json
{
  "timestamp": "2026-02-14T10:05:00",
  "status": 400,
  "error": "Validation Failed",
  "message": "Request validation failed",
  "validationErrors": {
    "id": "Device ID must be 3-50 characters long...",
    "timeout": "Timeout must be at least 10 seconds",
    "alertEmail": "Email must be a valid format"
  },
  "path": "uri=/monitors"
}
```

### Not Found Errors (404 Not Found)
```json
{
  "timestamp": "2026-02-14T10:05:00",
  "status": 404,
  "error": "Monitor Not Found",
  "message": "Monitor with ID 'device-456' not found",
  "path": "uri=/monitors/device-456/heartbeat"
}
```

### Server Errors (500 Internal Server Error)
```json
{
  "timestamp": "2026-02-14T10:05:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An unexpected error occurred. Please try again later.",
  "path": "uri=/monitors"
}
```

## Monitoring Gap Fix

### Problem
Original system had monitoring gaps where:
- Timers stopped after first alert
- No repeated alerts for extended outages  
- Inaccurate downtime tracking

### Solution
- **Continuous Monitoring**: Timers keep running after alerts
- **Repeated Alerts**: Every 60 seconds during outages
- **Accurate Tracking**: Precise downtime measurement
- **Immediate Recovery**: Heartbeat instantly stops alerts

### Behavior
```
Device expires → Alert #1 → Timer resets → Alert #2 → Timer resets → ...
Heartbeat received → Status ACTIVE → Alerts stop
```

## Testing

### Example Test Sequence

1. **Create a monitor:**
```bash
curl -X POST http://localhost:8081/monitors \
  -H "Content-Type: application/json" \
  -d '{"id": "test-device", "timeout": 30, "alertEmail": "test@example.com"}'
```

2. **Send heartbeat:**
```bash
curl -X POST http://localhost:8081/monitors/test-device/heartbeat
```

3. **Check status:**
```bash
curl http://localhost:8081/monitors/test-device
```

4. **Pause monitoring:**
```bash
curl -X POST http://localhost:8081/monitors/test-device/pause
```

## Developer's Choice Challenge

### Problem Identification
The original system had several critical gaps that made it unsuitable for production use:
- **No Audit Trail**: Alerts only logged to console, lost on restart
- **No Recovery Visibility**: Administrators couldn't tell when devices recovered
- **Monitoring Gaps**: Timers stopped after first alert, missing extended outages

### Solutions Implemented

#### 1. Alert History System
**What was missing**: Persistent alert tracking for compliance and analysis
**Solution**: Database storage with comprehensive API endpoints
**Impact**: Transforms system from simple alerting to observability platform

#### 2. Recovery Notifications  
**What was missing**: Complete incident lifecycle visibility
**Solution**: Automatic "Device is UP" notifications with downtime calculation
**Impact**: Eliminates manual status checking, improves operational efficiency

#### 3. Monitoring Gap Fix
**What was missing**: Continuous monitoring during extended outages
**Solution**: Repeated alerts every 60 seconds with timer reset logic
**Impact**: Ensures no extended downtime goes unnoticed

### Business Value
These features address real-world production needs:
- **Compliance**: Complete audit trails for regulations
- **Operations**: Automated notifications reduce manual work
- **Reliability**: Continuous monitoring prevents missed incidents
- **Analytics**: Data-driven device performance analysis

## Alert History System

### Features
- **Database Storage**: All alerts persisted to `alert_history` table
- **API Endpoints**: Retrieve alerts by device, time range, or recent activity
- **Audit Trail**: Complete incident tracking for compliance
- **Analytics**: Device performance and reliability analysis

### API Endpoints
- `GET /alerts` - All alert history
- `GET /alerts/device/{deviceId}` - Device-specific alerts
- `GET /alerts/recent` - Latest system alerts
- `GET /alerts/period?start={start}&end={end}` - Time-range filtering

### Business Value
- **Incident Analysis**: Identify problematic devices and patterns
- **Compliance Reporting**: Generate audit trails for regulations  
- **Performance Monitoring**: Track system reliability over time
- **SLA Tracking**: Monitor alert frequency against service levels

## Email Alert System

### Features
- **Professional HTML Emails**: Rich formatting with device details
- **Dual Channels**: Email + console logging + database storage
- **Retry Logic**: 3 attempts with exponential backoff
- **Graceful Degradation**: System continues if email fails

### Supported Providers
- **Gmail**: Use App Passwords (recommended)
- **Outlook**: SMTP with authentication
- **Corporate SMTP**: Custom server configuration
- **Amazon SES**: Simple Email Service

#### 3. Email Configuration
Configure email settings using environment variables:

```yaml
spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: ${GMAIL_USERNAME}
    password: ${GMAIL_PASSWORD}
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
            required: true
          connectiontimeout: 30000
          timeout: 30000
          writetimeout: 30000
          ssl:
            trust: smtp.gmail.com
            enable: false
          socketFactory:
            port: 587
            fallback: true
    from: noreply@pulsecheck.com
```

#### 4. Supported Email Providers
- **Gmail**: Use App Passwords (recommended)

### Gmail Setup
1. Enable 2-Factor Authentication on your Gmail account
2. Generate an App Password:
   - Go to Google Account settings
   - Security → 2-Step Verification → App passwords
   - Create new app password
3. Use App Password in configuration (not regular password)

#### 4. Error Handling
- Failed email attempts are logged but don't stop alert processing
- Console logging continues regardless of email status
- Database history is always maintained
- Graceful degradation if email service is unavailable

### Setup Instructions

1. **Configure SMTP Settings**:
   - Update `application.yaml` with your email provider
   - For Gmail: Generate an App Password
   - Set `test-connection: true` to verify configuration

2. **Test Email Alerts**:
   ```bash
   curl -X POST http://localhost:8081/monitors/test-device \
     -H "Content-Type: application/json" \
     -d '{"id": "test-device", "timeout": 10, "alertEmail": "your-email@test.com"}'
   ```

3. **Monitor Email Delivery**:
   - Check application logs for email delivery status
   - Verify email receipt in inbox
   - Check spam folder if email not received

### Security Notes

- Use App Passwords for Gmail instead of regular passwords
- Store email credentials securely (environment variables recommended)
- Consider using email service APIs for production deployments
- Test email configuration before production use

This email alert system ensures administrators are immediately notified of device failures through multiple channels while maintaining system reliability and audit capabilities.

## Recovery Notifications

### Features
- **Automatic Detection**: Monitors DOWN → ACTIVE transitions
- **Downtime Calculation**: Precise outage duration in minutes
- **Recovery Emails**: Professional "Device is UP" notifications
- **History Tracking**: All recoveries saved to database
