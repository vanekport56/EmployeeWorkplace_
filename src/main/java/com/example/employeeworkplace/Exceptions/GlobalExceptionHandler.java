package com.example.employeeworkplace.Exceptions;

import freemarker.core.NonBooleanException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Обработчик глобальных исключений, перехватывающий различные ошибки и
 * предоставляющий соответствующие сообщения об ошибках для отображения пользователю.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Обрабатывает исключения, связанные с ошибками в шаблоне FreeMarker.
     *
     * @param ex Исключение, содержащее информацию об ошибке
     * @param model Модель для передачи данных в представление
     * @return Имя шаблона для отображения страницы ошибки
     */
    @ExceptionHandler(NonBooleanException.class)
    public String handleNonBooleanException(NonBooleanException ex, Model model) {
        logger.error("Ошибка в шаблоне: {}", ex.getMessage());
        model.addAttribute("errorMessage", "Произошла ошибка при работе с шаблоном: " + ex.getMessage());
        return "error";
    }

    /**
     * Обрабатывает исключения, связанные с занятым именем пользователя.
     *
     * @param ex Исключение, содержащее информацию об ошибке
     * @param model Модель для передачи данных в представление
     * @return Имя шаблона для отображения страницы ошибки
     */
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public String handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex, Model model) {
        logger.warn("Имя пользователя уже занято: {}", ex.getMessage());
        model.addAttribute("errorMessage", "Имя пользователя уже занято: " + ex.getMessage());
        return "error";
    }

    /**
     * Обрабатывает исключения, связанные с использованием уже существующего адреса электронной почты.
     *
     * @param ex Исключение, содержащее информацию об ошибке
     * @param model Модель для передачи данных в представление
     * @return Имя шаблона для отображения страницы ошибки
     */
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public String handleEmailAlreadyExistsException(EmailAlreadyExistsException ex, Model model) {
        logger.warn("Электронная почта уже используется: {}", ex.getMessage());
        model.addAttribute("errorMessage", "Электронная почта уже используется: " + ex.getMessage());
        return "error";
    }

    /**
     * Обрабатывает все остальные исключения.
     *
     * @param ex Исключение, содержащее информацию об ошибке
     * @param model Модель для передачи данных в представление
     * @return Имя шаблона для отображения страницы ошибки
     */
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, Model model) {
        logger.error("Неизвестная ошибка: {}", ex.getMessage(), ex);
        model.addAttribute("errorMessage", "Внутренняя ошибка сервера: " + ex.getMessage());
        return "error";
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Ошибка целостности данных: " );
        response.put("message", ex.getMostSpecificCause().getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
