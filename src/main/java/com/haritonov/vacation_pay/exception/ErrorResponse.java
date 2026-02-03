package com.haritonov.vacation_pay.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DTO для ответа с информацией об ошибке в REST API.
 * Используется в GlobalExceptionHandler для стандартизированного формата ошибок.
 */
public class ErrorResponse {

    /**
     * Текстовое описание ошибки.
     */
    private String message;

    /**
     * Время возникновения ошибки в формате ISO_LOCAL_DATE_TIME.
     */
    private String timestamp;

    /**
     * Конструктор с обязательным сообщением об ошибке.
     * Автоматически устанавливает текущее время как timestamp.
     * @param message описание ошибки
     */
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
