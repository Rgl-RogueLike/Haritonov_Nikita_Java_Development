package com.haritonov.vacation_pay.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ErrorResponse {

    private String message;
    private String timestamp;

    public ErrorResponse(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
