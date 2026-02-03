package com.haritonov.vacation_pay.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Глобальный обработчик исключений (Exception Handler) для REST API.
 * Перехватывает исключения, возникающие в контроллерах, и формирует
 * стандартизированный JSON-ответ для клиента вместо страниц с ошибками.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Логгер для записи информации об ошибках.
     */
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Обрабатывает ошибки валидации входящих данных (@Valid).
     * Срабатывает, если поля DTO не прошли проверки (например, @NotNull, @Positive).
     * Обрабатывает как валидацию JSON тела (@RequestBody), так и GET параметров (@RequestParam).
     *
     * @param exception исключение валидации (MethodArgumentNotValidException или BindException)
     * @return ResponseEntity с картой ошибок (поле -> сообщение) и статусом 400 Bad Request
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        BindException bindException = (BindException) exception;
        bindException.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает ошибки бизнес-логики, выброшенные вручную.
     * Используется в сервисах для некорректных ситуаций (например, неверные даты).
     *
     * @param exception исключение аргумента
     * @return ResponseEntity с объектом ErrorResponse и статусом 400 Bad Request
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(exception.getMessage()));
    }

    /**
     * Обработчик непредвиденных исключений (Catch-all).
     * Срабатывает для всех остальных ошибок.
     *
     * @param exception любое необработанное исключение
     * @return ResponseEntity с объектом ErrorResponse и статусом 500 Internal Server Error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception exception) {
        logger.error("Непредвиденная ошибка", exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Внутренняя ошибка сервера"));
    }

}
