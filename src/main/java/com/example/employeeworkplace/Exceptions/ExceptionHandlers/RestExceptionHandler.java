package com.example.employeeworkplace.Exceptions.ExceptionHandlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Обработчик глобальных исключений для REST-контроллеров.
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        log.error("Неизвестная ошибка: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Внутренняя ошибка сервера: " + ex.getMessage());
    }
}
